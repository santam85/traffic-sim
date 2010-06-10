package simulator.events;

public class OccurrenceTimeComparedEvent extends ComparableEvent {

	public OccurrenceTimeComparedEvent(Event event) {
		super(event);
	}
	
	@Override
	public int compareTo(ComparableEvent o) {
		if(event.occurrenceTime < o.event.occurrenceTime)
			return -1;
		else if(event.occurrenceTime == o.event.occurrenceTime)
			return 0;
		else //if(occurrencetime > o.occurrencetime)
			return 1;
	}
}
