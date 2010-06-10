package simulator.events;

public class ServiceTimeComparedEvent extends ComparableEvent {

	public ServiceTimeComparedEvent(Event event) {
		super(event);
	}
	
	@Override
	public int compareTo(ComparableEvent o) {
		if(event.serviceTime < o.event.serviceTime)
			return -1;
		else if(event.serviceTime == o.event.serviceTime)
			return 0;
		else //if(occurrencetime > o.occurrencetime)
			return 1;
	}

}
