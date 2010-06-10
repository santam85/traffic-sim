package simulator;

import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.PriorityQueue;
import java.util.Vector;

public class SimulatorSJF implements Runnable {

	private Vector<PriorityQueue<Arrival>> eventList;
	private PriorityQueue<Event> futureEventList;
	private HashMap<Integer,Double> states;
	
	private int[] arrivalsByClass, departuresByClass;
	private int arrivals, departures, k;
	private double now, freeTime ;
	private double[] waitTime;
	
	private Distribution[] arrivalTimeDistribution;
	private Distribution serviceTimeDistribution;
	
	
	private double[] etaByClass;
	private double eta, eps;
	private int priorityClasses;
	
	private LinkedList<Event> history;
	
	public SimulatorSJF(Distribution[] arrivalTimeDistribution, Distribution serviceTimeDistribution){
		history = new LinkedList<Event>();
		this.priorityClasses = arrivalTimeDistribution.length;
		
		eventList = new Vector<PriorityQueue<Arrival>>(priorityClasses);
		futureEventList = new PriorityQueue<Event>();
		states = new HashMap<Integer,Double>();
		
		this.arrivalTimeDistribution = arrivalTimeDistribution;
		this.serviceTimeDistribution = serviceTimeDistribution;
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
	
	public LinkedList<Event> getHistory() {
		return history;
	}
	
	public HashMap<Integer,Double> getStatesProbobility() {
		HashMap<Integer,Double> probabilities = new HashMap<Integer,Double>();
		Iterator<Integer> it = states.keySet().iterator();
		while (it.hasNext()) {
			int i = it.next();
			probabilities.put(i,states.get(i)/now);
		}
		return probabilities;
	}
	
	@Override
	public void run() {
		init();
		
		//System.out.println("---------------------------------------------");
		while (futureEventList.size() > 0){
			Event e = futureEventList.poll();
			history.add(e);
			
			updateState(e);
			
			now = e.occurrenceTime;
			
			//System.out.println(e);
			
			if (e.getClass() == Arrival.class){
				Arrival a = (Arrival)e;
				k++; arrivals++; arrivalsByClass[e.getPriorityClass()]++;
				int id = a.id;
				if (k==1){
					freeTime = now + a.serviceTime;
					futureEventList.add(new Departure(freeTime,id,a.getPriorityClass()));
				} else {
					freeTime = freeTime + a.serviceTime;
					eventList.get(a.getPriorityClass()).add(a);
				}
				
				if (checkStopCondition()){
					futureEventList.add(generateArrival(e.getPriorityClass()));
				}	
			}else if(e.getClass() == Departure.class){
				k--; departures++; departuresByClass[e.getPriorityClass()]++;
				if (k > 0) {
					Arrival a = serviceEvent();
					waitTime[a.getPriorityClass()] += (now - a.getOccurrenceTime());
					futureEventList.add(new Departure(now + a.serviceTime,a.getId(),a.getPriorityClass()));
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
			eventList.add(i,new PriorityQueue<Arrival>());
			arrivalsByClass[i] = 0; departuresByClass[i] = 0;
			futureEventList.add(generateArrival(i));
		}
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
		return arrivals < 1000;
	}
	
	private void updateState(Event e) {
		double elapsedTime = e.occurrenceTime - now;
		if (!states.containsKey(k)) {
			states.put(k,elapsedTime);
		}
		else {
			states.put(k,states.get(k) + elapsedTime);
		}
	}
	
	private Arrival serviceEvent() {
		Arrival e = null;
		for (int i = 0; i < this.priorityClasses && e == null; i ++) {
			e = eventList.get(i).poll();
		}
		return e;
	}
	
	private void checkConsistency() {
		Iterator<Double> it = states.values().iterator();
		double sum = 0;
		while (it.hasNext()) 
			sum += it.next();
		System.out.println(sum + " " + now + " " + (now - sum));
		if (sum != now) {
			System.out.println("consistency check failed");
		}
	}
}