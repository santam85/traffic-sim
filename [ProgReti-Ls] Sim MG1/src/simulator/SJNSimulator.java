package simulator;

import simulator.distribution.Distribution;
import simulator.distribution.ExponentialDistribution;
import simulator.events.Arrival;
import simulator.events.ComparableEvent;
import simulator.events.Departure;
import simulator.events.Event;
import simulator.events.OccurrenceTimeComparedEvent;
import simulator.events.ServiceTimeComparedEvent;
import simulator.misc.LogaritmicQuantizator;

public class SJNSimulator extends Simulator {

	private LogaritmicQuantizator quantizator;
	private int classes;
	
	public SJNSimulator(Distribution[] arrivalTimeDistribution, ExponentialDistribution serviceTimeDistribution){
		this(arrivalTimeDistribution,serviceTimeDistribution,1000);
	}
	
	public SJNSimulator(Distribution[] arrivalTimeDistribution, ExponentialDistribution serviceTimeDistribution, int totalArrivals){
		this(arrivalTimeDistribution,serviceTimeDistribution,totalArrivals,20);
	}
	
	public SJNSimulator(Distribution[] arrivalTimeDistribution, ExponentialDistribution serviceTimeDistribution, int totalArrivals, int discretizationStep){
		this(arrivalTimeDistribution,serviceTimeDistribution,totalArrivals,discretizationStep,5.0);
	}
	
	public SJNSimulator(Distribution[] arrivalTimeDistribution, ExponentialDistribution serviceTimeDistribution, int totalArrivals, int discretizationStep, double multiplier){
		super(arrivalTimeDistribution,serviceTimeDistribution,totalArrivals);
		
		quantizator = new LogaritmicQuantizator(1.0/serviceTimeDistribution.getMu(),discretizationStep,multiplier);
		classes = quantizator.getIntervalNumber();
		this.waitTime = new double[classes];
		
		this.etaByClass = new double[classes];
		this.arrivalsByClass = new int[classes];
		this.departuresByClass = new int[classes];
		this.waitTime = new double[classes];
	}
	
	protected ComparableEvent generateNewDepartureInFutureEventList(Event e,double occurrenceTime) {
		return new OccurrenceTimeComparedEvent(new Departure(occurrenceTime,e.getId(),e.getPriorityClass()));
	}
	
	protected ComparableEvent generateNewArrivalInEventList(Arrival a) {
		return new ServiceTimeComparedEvent(a);
	}
	
	protected ComparableEvent generateNewArrivalInFutureEventList(Arrival a) {
		return new OccurrenceTimeComparedEvent(generateArrival(a.getPriorityClass()));
	}

	@Override
	protected int generatePriorityClass(Event a) {
		// System.out.println(a.getServiceTime());
		return quantizator.getDiscretizationClass(a.getServiceTime());
	}
	
	public int getNumberOfClasses() {
		return this.classes;
	}
	
	public double getDiscretizetionValueByClass(int c) {
		return quantizator.getDiscretizationValueByClass(c);
	}
}
