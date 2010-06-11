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
	
	public SJNSimulator(Distribution[] arrivalTimeDistribution, ExponentialDistribution serviceTimeDistribution){
		this(arrivalTimeDistribution,serviceTimeDistribution,1000);
	}
	
	public SJNSimulator(Distribution[] arrivalTimeDistribution, ExponentialDistribution serviceTimeDistribution, int totalArrivals){
		super(arrivalTimeDistribution,serviceTimeDistribution,totalArrivals);
		
		quantizator = new LogaritmicQuantizator(1.0/serviceTimeDistribution.getMu());
		this.waitTime = new double[quantizator.getIntervalNumber()];
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
	protected int generatePriorityClass(Arrival a) {
		System.out.println(a.getServiceTime());
		return quantizator.getDiscretizationClass(a.getServiceTime());
	}

}
