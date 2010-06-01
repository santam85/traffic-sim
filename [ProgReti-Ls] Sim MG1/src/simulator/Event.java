package simulator;

public abstract class Event implements Comparable<Event>{
	
	protected static int idc = 0;
	
	public int id;
	public float occurrenceTime;
	
	public Event(float occurrencetime) {
		this.occurrenceTime = occurrencetime;
	}

	@Override
	public int compareTo(Event o) {
		if(occurrenceTime < o.occurrenceTime)
			return -1;
		else if(occurrenceTime == o.occurrenceTime)
			return 0;
		else //if(occurrencetime > o.occurrencetime)
			return 1;

	}
	
	@Override
	public abstract String toString();
	
}
