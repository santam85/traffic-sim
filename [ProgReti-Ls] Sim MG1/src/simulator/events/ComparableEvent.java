package simulator.events;

public abstract class ComparableEvent implements Comparable<ComparableEvent> {
	
	protected Event event;
	
	public ComparableEvent(Event event) {
		this.event = event;
	}
	
	public Event getEvent() {
		return event;
	}
}
