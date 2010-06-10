package simulator;

import simulator.distribution.Distribution;
import simulator.events.Arrival;
import simulator.events.ComparableEvent;
import simulator.events.Departure;
import simulator.events.Event;
import simulator.events.OccurrenceTimeComparedEvent;
import simulator.events.ServiceTimeComparedEvent;

public class SJNSimulator extends Simulator {

	public SJNSimulator(Distribution[] arrivalTimeDistribution, Distribution serviceTimeDistribution){
		super(arrivalTimeDistribution,serviceTimeDistribution);
	}
	
	public SJNSimulator(Distribution[] arrivalTimeDistribution, Distribution serviceTimeDistribution, int totalArrivals){
		super(arrivalTimeDistribution,serviceTimeDistribution,totalArrivals);
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

}
