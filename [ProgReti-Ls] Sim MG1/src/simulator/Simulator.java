package simulator;

import java.util.LinkedList;
import java.util.Vector;
import java.util.concurrent.ConcurrentSkipListSet;

public class Simulator implements Runnable {

	private Vector<ConcurrentSkipListSet<Event>> eventList;
	private int arrivals, queuedArrivals, departures, k;
	private float now, freeTime;
	private float waitTime,waitTimeQueue;
	
	private Distribution arrivalTimeDistribution, serviceTimeDistribution;
	private Object priorityDistribution;
	
	
	private float eta;
	private float eps;
	
	private LinkedList<Event> history;
	
	public Simulator(Distribution arrivalTimeDistribution, Distribution serviceTimeDistribution){
		this(arrivalTimeDistribution,serviceTimeDistribution,1);
	}
	
	public Simulator(Distribution arrivalTimeDistribution, Distribution serviceTimeDistribution, int priorityClasses){
		history = new LinkedList<Event>();
		eventList = new Vector<ConcurrentSkipListSet<Event>>(priorityClasses);
		
		this.arrivalTimeDistribution = arrivalTimeDistribution;
		this.serviceTimeDistribution = serviceTimeDistribution;
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
			Event e = eventList.pollFirst();
			history.add(e);
			
			now = e.occurrenceTime;
			
			// System.out.println(e);
			
			if (e.getClass() == Arrival.class){
				Arrival a = (Arrival)e;
				k++; arrivals++;
				int id = a.id;
				if (k==1){
					freeTime = now + a.serviceTime;
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
		arrivals = 0; departures = 0;
		eventList.add(generateArrival());
	}
	
	private float generateServiceTime(){
		return serviceTimeDistribution.nextValue();
	}
	
	private float generateOccurrenceTime() {
		return now + arrivalTimeDistribution.nextValue();
	}
	
	private Event generateArrival(){
		return new Arrival(generateOccurrenceTime(),generateServiceTime(),generatePriority(priorityDistribution));
	}

	private int generatePriority(Object priorityDistribution) {
		// TODO Auto-generated method stub
		return 0;
	}

	private boolean checkStopCondition() {
		return arrivals < 50;
	}
}
