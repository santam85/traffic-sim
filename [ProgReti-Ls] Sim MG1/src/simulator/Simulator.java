package simulator;

import java.util.LinkedList;
import java.util.concurrent.ConcurrentSkipListSet;

public class Simulator implements Runnable {

	private ConcurrentSkipListSet<Event> eventList;
	private int arrivals, departures, k;
	private float now, freeTime;
	private float waitTime;
	
	private Rnd rnd;
	
	private Distribution arrivalTimeDistribution, serviceTimeDistribution;
	private Object priorityDistribution;
	
	public float mwt;
	public LinkedList<Event> history;
	
	
	public Simulator(Distribution arrivalTimeDistribution, Distribution serviceTimeDistribution, Rnd rnd){
		history = new LinkedList<Event>();
		eventList = new ConcurrentSkipListSet<Event>();
		
		this.rnd = rnd;
		
		this.arrivalTimeDistribution = arrivalTimeDistribution;
		this.serviceTimeDistribution = serviceTimeDistribution;
	}
	
	@Override
	public void run() {
		init();

		while (eventList.size() > 0){
			Event e = eventList.pollFirst();
			history.add(e);
			
			now = e.occurrenceTime;
			
			System.out.println(e);
			
			if (e.getClass() == Arrival.class){
				Arrival a = (Arrival)e;
				k++; arrivals++;
				int id = a.id;
				if (k==1){
					freeTime = now + a.serviceTime;
				} else {
					waitTime = waitTime + (freeTime - now);
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
		mwt = waitTime/arrivals;
		System.out.println(mwt);
	}

	private void init() {
		k=0; freeTime = 0; now = 0;
		arrivals = 0; departures = 0;
		eventList.add(generateArrival());
	}
	
	private float generateServiceTime(Distribution d){
		return rnd.nextRandom(d);
	}
	
	private float generateOccurrenceTime() {
		return now + rnd.nextRandom(arrivalTimeDistribution);
	}
	
	private Event generateArrival(){
		return new Arrival(generateOccurrenceTime(),generateServiceTime(serviceTimeDistribution),generatePriority(priorityDistribution));
	}

	private int generatePriority(Object priorityDistribution) {
		// TODO Auto-generated method stub
		return 0;
	}

	private boolean checkStopCondition() {
		return arrivals < 50;
	}
}
