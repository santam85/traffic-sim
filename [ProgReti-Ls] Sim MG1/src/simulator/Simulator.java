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

public class Simulator {

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
		this(arrivalTimeDistribution,serviceTimeDistribution,100);
	}
	
	public Simulator(Distribution[] arrivalTimeDistribution, Distribution serviceTimeDistribution, int totalArrivals){
		this.history = new LinkedList<ComparableEvent>();
		this.priorityClasses = arrivalTimeDistribution.length;
		
		this.eventList = new Vector<PriorityQueue<ComparableEvent>>(priorityClasses);
		this.futureEventList = new PriorityQueue<ComparableEvent>();
		this.states = new LinkedList<Double>();
		
		this.arrivalTimeDistribution = arrivalTimeDistribution;
		this.serviceTimeDistribution = serviceTimeDistribution;
		this.totalArrivals = totalArrivals;
	}
	
	public int getTotalArrivals() {
		return totalArrivals;
	}
	
	public double getEta() {
		return eta;
	}
	
	public double getEtaByClass(int priorityClass) {
		if (priorityClass > this.priorityClasses)
			return -1;
		return etaByClass[priorityClass];
	}
	
	public double getEps() {
		return eps;
	}
	
	public LinkedList<ComparableEvent> getHistory() {
		return history;
	}
	
	public double[] getStatesProbobility() {
		double[] probabilities = new double[states.size()];
		for (int i = 0; i < states.size(); i ++) {
			probabilities[i] = states.get(i)/now;
		}
		return probabilities;
	}
	
	public void run() {
		init();
		
		//System.out.println("---------------------------------------------");
		while (futureEventList.size() > 0){
			Event e = futureEventList.poll().getEvent();
			history.add(new OccurrenceTimeComparedEvent(e));
			
			updateState(e);
			
			now = e.getOccurrenceTime();
			
			//System.out.println(e);
			
			if (e.getClass() == Arrival.class){
				Arrival a = (Arrival)e;
				k++; arrivals++; arrivalsByClass[e.getPriorityClass()]++;
				if (k==1){
					freeTime = now + a.getServiceTime();
					futureEventList.add(generateNewDepartureInFutureEventList(a)); // 
				} else {
					freeTime = freeTime + a.getServiceTime();
					eventList.get(a.getPriorityClass()).add(generateNewArrivalInEventList(a));
				}
				
				if (checkStopCondition()){
					futureEventList.add(new OccurrenceTimeComparedEvent(generateArrival(e.getPriorityClass())));
				}	
			}else if(e.getClass() == Departure.class){
				k--; departures++; departuresByClass[e.getPriorityClass()]++;
				if (k > 0) {
					Arrival a = serviceEvent();
					waitTime[a.getPriorityClass()] += (now - a.getOccurrenceTime());
					futureEventList.add(new OccurrenceTimeComparedEvent(new Departure(now + a.getServiceTime(),a.getId(),a.getPriorityClass())));
				}
			}
		}
		
		for (int i = 0; i < priorityClasses; i ++) {
			eta += waitTime[i];
			etaByClass[i] = waitTime[i]/arrivalsByClass[i];
		}
		eta = eta/arrivals;
		
		// checkConsistency();
	}

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
	
	protected ComparableEvent generateNewDepartureInFutureEventList(Event e) {
		return new OccurrenceTimeComparedEvent(new Departure(freeTime,e.getId(),e.getPriorityClass()));
	}
	
	protected ComparableEvent generateNewArrivalInEventList(Arrival a) {
		return new OccurrenceTimeComparedEvent(a);
	}
	
	private double generateServiceTime(){
		return serviceTimeDistribution.nextValue();
	}
	
	private double generateOccurrenceTime(int priorityClass) {
		return now + arrivalTimeDistribution[priorityClass].nextValue();
	}
	
	private Event generateArrival(int priorityClass){
		return new Arrival(generateOccurrenceTime(priorityClass),generateServiceTime(),priorityClass);
	}

	private boolean checkStopCondition() {
		return arrivals < totalArrivals;
	}
	
	private void updateState(Event e) {
		double elapsedTime = e.getOccurrenceTime() - now;
		if (states.size() <= k) {
			states.add(k,elapsedTime);
		}
		else {
			states.set(k,states.get(k) + elapsedTime);
		}
	}
	
	private Arrival serviceEvent() {
		Arrival e = null;
		for (int i = 0; i < this.priorityClasses && e == null; i ++) {
			e = (Arrival)eventList.get(i).poll().getEvent();
		}
		return e;
	}
	
	/*private void checkConsistency() {
		Iterator<Double> it = states.iterator();
		double sum = 0;
		while (it.hasNext()) 
			sum += it.next();
		System.out.println(sum + " " + now + " " + (now - sum));
		if (sum != now) {
			System.out.println("consistency check failed");
		}
	}*/
}
