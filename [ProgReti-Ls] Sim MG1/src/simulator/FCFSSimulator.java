package simulator;

import simulator.distribution.Distribution;
import simulator.events.Arrival;
import simulator.events.ComparableEvent;
import simulator.events.Departure;
import simulator.events.Event;
import simulator.events.OccurrenceTimeComparedEvent;

public class FCFSSimulator extends Simulator {

	public FCFSSimulator(Distribution[] arrivalTimeDistribution, Distribution serviceTimeDistribution){
		super(arrivalTimeDistribution,serviceTimeDistribution);
	}
	
	public FCFSSimulator(Distribution[] arrivalTimeDistribution, Distribution serviceTimeDistribution, int totalArrivals){
		super(arrivalTimeDistribution,serviceTimeDistribution,totalArrivals);
	}
	
	protected ComparableEvent generateNewDepartureInFutureEventList(Event e,double occurrenceTime) {
		return new OccurrenceTimeComparedEvent(new Departure(occurrenceTime,e.getId(),e.getPriorityClass()));
	}
	
	protected ComparableEvent generateNewArrivalInEventList(Arrival a) {
		return new OccurrenceTimeComparedEvent(a);
	}
	
	protected ComparableEvent generateNewArrivalInFutureEventList(Arrival a) {
		return new OccurrenceTimeComparedEvent(generateArrival(a.getPriorityClass()));
	}

	@Override
	protected int generatePriorityClass(Event a) {
		return a.getPriorityClass();
	}
}
