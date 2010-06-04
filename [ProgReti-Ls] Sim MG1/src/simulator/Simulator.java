package simulator;

import java.util.LinkedList;
import java.util.Vector;
import java.util.concurrent.ConcurrentSkipListSet;

public class Simulator implements Runnable {

	private Vector<ConcurrentSkipListSet<Arrival>> eventList;
	private ConcurrentSkipListSet<Event> futureEventList;
	private int[] arrivalsByClass, departuresByClass;
	private int arrivals, departures;
	private float now, freeTime, k ;
	private float[] waitTime;
	
	private Distribution[] arrivalTimeDistribution;
	private Distribution serviceTimeDistribution;
	
	
	private float[] etaByClass;
	private float eta, eps;
	private int priorityClasses;
	
	private LinkedList<Event> history;
	
	public Simulator(Distribution[] arrivalTimeDistribution, Distribution serviceTimeDistribution) {
		this(arrivalTimeDistribution,serviceTimeDistribution,1);
	}
	
	public Simulator(Distribution[] arrivalTimeDistribution, Distribution serviceTimeDistribution, int priorityClasses){
		history = new LinkedList<Event>();
		eventList = new Vector<ConcurrentSkipListSet<Arrival>>(priorityClasses);
		futureEventList = new ConcurrentSkipListSet<Event>();
		
		this.arrivalTimeDistribution = arrivalTimeDistribution;
		this.serviceTimeDistribution = serviceTimeDistribution;
		this.priorityClasses = priorityClasses;
	}
	
	public float getEta() {
		return eta;
	}
	
	public float getEps() {
		return eps;
	}
	
	public LinkedList<Event> getHistory() {
		return history;
	}
	
	@Override
	public void run() {
		init();

		while (futureEventList.size() > 0){
			Event e = futureEventList.pollFirst();
			history.add(e);
			
			now = e.occurrenceTime;
			
			// System.out.println(e);
			
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
					// waitTime = waitTime + (freeTime - now);
					// waitTimeQueue += freeTime - now;
				}	
			}else if(e.getClass() == Departure.class){
				k--; departures++;
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
	}

	private void init() {
		k=0; freeTime = 0; now = 0;
		this.etaByClass = new float[priorityClasses];
		this.arrivalsByClass = new int[priorityClasses];
		this.departuresByClass = new int[priorityClasses];
		this.waitTime = new float[priorityClasses];
		for (int i = 0; i < priorityClasses; i ++) {
			eventList.add(i,new ConcurrentSkipListSet<Arrival>());
			arrivalsByClass[i] = 0; departuresByClass[i] = 0;
			futureEventList.add(generateArrival(i));
		}
	}
	
	private float generateServiceTime(){
		return serviceTimeDistribution.nextValue();
	}
	
	private float generateOccurrenceTime(int priorityClass) {
		return now + arrivalTimeDistribution[priorityClass].nextValue();
	}
	
	private Event generateArrival(int priorityClass){
		return new Arrival(generateOccurrenceTime(priorityClass),generateServiceTime(),priorityClass);
	}

	private boolean checkStopCondition() {
		return arrivals < 100;
	}
	
	private Arrival serviceEvent() {
		Arrival e = null;
		for (int i = 0; i < this.priorityClasses && e == null; i ++) {
			e = eventList.get(i).pollFirst();
		}
		return e;
	}
}
