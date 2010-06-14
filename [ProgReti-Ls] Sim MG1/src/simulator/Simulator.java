package simulator;

import java.util.LinkedList;
import java.util.PriorityQueue;
import java.util.Vector;

import simulator.distribution.Distribution;
import simulator.events.Arrival;
import simulator.events.ComparableEvent;
import simulator.events.Departure;
import simulator.events.Event;
import simulator.events.OccurrenceTimeComparedEvent;

/**
 * Class implementing the simulator
 * 
 * @author Andrea Zagnoli, Marco Santarelli, Michael Gattavecchia. 
 *
 */
public abstract class Simulator {

	protected Vector<PriorityQueue<ComparableEvent>> eventList;
	protected PriorityQueue<ComparableEvent> futureEventList;
	protected LinkedList<Double> states;
	
	protected int[] arrivalsByClass, departuresByClass;
	protected int arrivals, departures, k, totalArrivals;
	protected double now, freeTime ;
	protected double[] waitTime;
	
	protected Distribution[] arrivalTimeDistribution;
	protected Distribution serviceTimeDistribution;
	
	
	protected double[] etaByClass;
	protected double eta, eps;
	protected int priorityClasses;
	
	protected LinkedList<ComparableEvent> history;
	
	public Simulator(Distribution[] arrivalTimeDistribution, Distribution serviceTimeDistribution){
		this(arrivalTimeDistribution,serviceTimeDistribution,1000);
	}
	
	/**
	 * Creates a new simulator
	 * 
	 * @param arrivalTimeDistribution The arrival time distribution
	 * @param serviceTimeDistribution The service time distribution
	 * @param totalArrivals The number of arrivals to generate before stopping the simulation
	 */
	public Simulator(Distribution[] arrivalTimeDistribution, Distribution serviceTimeDistribution, int totalArrivals){
		this.history = new LinkedList<ComparableEvent>();
		this.priorityClasses = arrivalTimeDistribution.length;
		
		this.eventList = new Vector<PriorityQueue<ComparableEvent>>(priorityClasses);
		this.futureEventList = new PriorityQueue<ComparableEvent>();
		this.states = new LinkedList<Double>();
		
		this.arrivalTimeDistribution = arrivalTimeDistribution;
		this.serviceTimeDistribution = serviceTimeDistribution;
		this.totalArrivals = totalArrivals;
		
		init();
	}
	
	/**
	 * A getter for the simulation arrival limit
	 * 
	 * @return The arrival limit
	 */
	public int getTotalArrivals() {
		return totalArrivals;
	}
	
	/**
	 * A getter for the general mean eta
	 * 
	 * @return The general mean eta
	 */
	public double getEta() {
		return eta;
	}
	
	/**
	 * A getter for fetching the mean eta for each class
	 * 
	 * @param priorityClass The priority class of interes
	 * @return The mean eta
	 */
	public double getEtaByClass(int priorityClass) {
		if (priorityClass > this.etaByClass.length)
			return -1;
		return etaByClass[priorityClass];
	}
	
	public double getEps() {
		return eps;
	}
	
	/**
	 * A getter for the simulation history
	 * @return A list of events, ordered as the servant processed them
	 */
	public LinkedList<ComparableEvent> getHistory() {
		return history;
	}
	
	/**
	 * A getter for the evaluated state probability.
	 * @return An array of the probability, ordered by state number;
	 */
	public double[] getStatesProbability() {
		double[] probabilities = new double[states.size()];
		for (int i = 0; i < states.size(); i ++) {
			probabilities[i] = states.get(i)/now;
		}
		return probabilities;
	}
	
	
	/**
	 *	Starts the simulation, executing it on the caller thread. 
	 */
	public void run() {
		while (futureEventList.size() > 0){
			Event e = futureEventList.poll().getEvent();
			history.add(new OccurrenceTimeComparedEvent(e));
			
			updateState(e);
			
			now = e.getOccurrenceTime();
			
			if (e.getClass() == Arrival.class){
				Arrival a = (Arrival)e;
				k++; arrivals++; arrivalsByClass[generatePriorityClass(a)]++;
				if (k==1){
					freeTime = now + a.getServiceTime();
					futureEventList.add(generateNewDepartureInFutureEventList(a,freeTime)); // specialization point
				} else {
					freeTime = freeTime + a.getServiceTime();
					eventList.get(a.getPriorityClass()).add(generateNewArrivalInEventList(a)); // specialization point
				}
				
				if (checkStopCondition()){
					futureEventList.add(generateNewArrivalInFutureEventList(a)); // specialization point
				}	
			}else if(e.getClass() == Departure.class){
				k--; departures++; departuresByClass[generatePriorityClass(e)]++;
				if (k > 0) {
					Arrival a = serviceEvent();
					// System.out.println("ST: " + a.getServiceTime() + " C: " + generatePriorityClass(a));
					waitTime[generatePriorityClass(a)] += (now - a.getOccurrenceTime());
					// waitTime[a.getPriorityClass()] += (now - a.getOccurrenceTime());
					futureEventList.add(generateNewDepartureInFutureEventList(a,now + a.getServiceTime())); // specialization point
					// futureEventList.add(new OccurrenceTimeComparedEvent(new Departure(now + a.getServiceTime(),a.getId(),a.getPriorityClass()))); 
				}
			}
		}
		
		for (int i = 0; i < waitTime.length; i ++) {
			eta += waitTime[i];
			// System.out.println("------------WT: " + waitTime[i] + " AbC: " + arrivalsByClass[i]);
			etaByClass[i] = waitTime[i]/(arrivalsByClass[i] > 0?arrivalsByClass[i]:1);
		}
		eta = eta/arrivals;
	}

	/**
	 * Inits the simulation parameters
	 */
	private void init() {
		k=0; freeTime = 0; now = 0;
		this.etaByClass = new double[priorityClasses];
		this.arrivalsByClass = new int[priorityClasses];
		this.departuresByClass = new int[priorityClasses];
		this.waitTime = new double[priorityClasses];
		for (int i = 0; i < priorityClasses; i ++) {
			eventList.add(i,new PriorityQueue<ComparableEvent>());
			arrivalsByClass[i] = 0; departuresByClass[i] = 0;
			futureEventList.add(new OccurrenceTimeComparedEvent(generateArrival(i)));
		}
	}
	
	abstract protected ComparableEvent generateNewDepartureInFutureEventList(Event e,double occurrenceTime) ;
	
	abstract protected ComparableEvent generateNewArrivalInEventList(Arrival a) ;
	
	abstract protected ComparableEvent generateNewArrivalInFutureEventList(Arrival a) ;
	
	abstract protected int generatePriorityClass(Event a) ;
	
	public int getNumberOfClasses() {
		return this.priorityClasses;
	}
	
	/**
	 * The service time generator, using the simulation service time distribution
	 * @return A random service time
	 */
	protected double generateServiceTime(){
		return serviceTimeDistribution.nextValue();
	}
	
	/**
	 * The occurrence time generator, using the simulation arrival time distribution
	 * @param priorityClass The priority class of event to be generated
	 * @return A random occurrence time
	 */
	protected double generateOccurrenceTime(int priorityClass) {
		return now + arrivalTimeDistribution[priorityClass].nextValue();
	}
	
	/**
	 * Generates a new arrival
	 * 
	 * @param priorityClass The priority class of arrival to be generated
	 * @return
	 */
	protected Event generateArrival(int priorityClass){
		return new Arrival(generateOccurrenceTime(priorityClass),generateServiceTime(),priorityClass);
	}

	protected boolean checkStopCondition() {
		return arrivals < totalArrivals;
	}
	
	/**
	 * A method for updating the system state
	 * 
	 * @param e Updates the system state based on the occurred event
	 */
	protected void updateState(Event e) {
		double elapsedTime = e.getOccurrenceTime() - now;
		if (states.size() <= k) {
			states.add(k,elapsedTime);
		}
		else {
			states.set(k,states.get(k) + elapsedTime);
		}
	}
	
	/**
	 * This method returns the correct event to be processed from the various queues
	 * @return The event to be serviced
	 */
	protected Arrival serviceEvent() {
		ComparableEvent e = null;
		for (int i = 0; i < this.priorityClasses && e == null; i ++) {
			e = eventList.get(i).poll();
		}
		return (Arrival) e.getEvent();
	}
}
