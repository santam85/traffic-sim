package simulator;

import java.util.LinkedList;
import java.util.Vector;
import java.util.concurrent.ConcurrentSkipListSet;

public class Simulator implements Runnable {

	private Vector<ConcurrentSkipListSet<Event>> eventList;
	private int[] arrivals, queuedArrivals, departures;
	private int cumulativeArrivals, cumulativeDepartures;
	private float now, freeTime, k ;
	private float waitTime,waitTimeQueue;
	
	private Distribution[] arrivalTimeDistribution;
	private Distribution serviceTimeDistribution;
	private Object priorityDistribution;
	
	
	private float eta;
	private float eps;
	private int priorityClasses;
	
	private LinkedList<Event> history;
	
	public Simulator(Distribution[] arrivalTimeDistribution, Distribution serviceTimeDistribution) {
		this(arrivalTimeDistribution,serviceTimeDistribution,1);
	}
	
	public Simulator(Distribution[] arrivalTimeDistribution, Distribution serviceTimeDistribution, int priorityClasses){
		history = new LinkedList<Event>();
		eventList = new Vector<ConcurrentSkipListSet<Event>>(priorityClasses);
		
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

		while (eventList.size() > 0){
			Event e = serviceEvent();
			history.add(e);
			
			now = e.occurrenceTime;
			
			// System.out.println(e);
			
			if (e.getClass() == Arrival.class){
				Arrival a = (Arrival)e;
				k++; cumulativeArrivals++; arrivals[e.getPriorityClass()]++;
				int id = a.id;
				if (k==1){
					freeTime = now + a.serviceTime;
					generateArrival(a.getPriorityClass());
				} else {
					waitTime = waitTime + (freeTime - now);
					waitTimeQueue += freeTime - now;
					queuedArrivals++;
					
					freeTime = freeTime + a.serviceTime;
				}
				eventList.add(new Departure(freeTime,id));
				if (checkStopCondition()){
					eventList.add(generateArrival());
				}	
			}else if(e.getClass() == Departure.class){
				k--;
				departures++;
			}
		}
		
		eta = waitTime/arrivals;
		eps = waitTimeQueue/queuedArrivals;
	}

	private void init() {
		k=0; freeTime = 0; now = 0;
		for (int i = 0; i < priorityClasses; i ++) {
			arrivals[i] = 0; departures[i] = 0;
			ConcurrentSkipListSet<Event> s;
			eventList.add(s = new ConcurrentSkipListSet<Event>());
			s.add(generateArrival(i));
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
		return arrivals < 50;
	}
	
	private Event serviceEvent() {
		Event e = null;
		for (int i = 0; i < this.priorityClasses && e == null; i ++) {
			e = eventList.get(i).pollFirst();
		}
		return e;
	}
}
