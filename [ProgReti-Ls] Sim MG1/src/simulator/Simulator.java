package simulator;

import java.util.LinkedList;
import java.util.Vector;
import java.util.concurrent.ConcurrentSkipListSet;

public class Simulator implements Runnable {

	private Vector<ConcurrentSkipListSet<Arrival>> eventList;
	private ConcurrentSkipListSet<Event> futureEventList;
	
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
	
	public Simulator(Distribution[] arrivalTimeDistribution, Distribution serviceTimeDistribution){
		history = new LinkedList<Event>();
		this.priorityClasses = arrivalTimeDistribution.length;
		
		eventList = new Vector<ConcurrentSkipListSet<Arrival>>(priorityClasses);
		futureEventList = new ConcurrentSkipListSet<Event>();
		
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
	
	@Override
	public void run() {
		init();
		
		//System.out.println("---------------------------------------------");
		while (futureEventList.size() > 0){
			Event e = futureEventList.pollFirst();
			history.add(e);
			
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
					if (a != null) { // ?!?
						waitTime[a.getPriorityClass()] += (now - a.getOccurrenceTime());
						futureEventList.add(new Departure(now + a.serviceTime,a.getId(),a.getPriorityClass()));
					}
					else {
						System.out.println("[c� qualcosa che non va]\n" + k + " " + arrivals + " " + departures);
					}
				}
			}
		}
		
		for (int i = 0; i < priorityClasses; i ++) {
			eta += waitTime[i];
			etaByClass[i] = waitTime[i]/arrivalsByClass[i];
			
		}
		eta = eta/arrivals;
		//System.out.println("Eta mean" + eta);
		//System.out.println("---------------------------------------------");
	}

	private void init() {
		k=0; freeTime = 0; now = 0;
		this.etaByClass = new double[priorityClasses];
		this.arrivalsByClass = new int[priorityClasses];
		this.departuresByClass = new int[priorityClasses];
		this.waitTime = new double[priorityClasses];
		for (int i = 0; i < priorityClasses; i ++) {
			eventList.add(i,new ConcurrentSkipListSet<Arrival>());
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
