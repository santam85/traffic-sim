package simulator;

public abstract class Event implements Comparable<Event>{
	
	protected static int idc = 0;
	
	protected int id;
	protected float occurrenceTime;
	protected int priorityClass;
	
	
	public Event(float occurrencetime,int priorityClass) {
		this.occurrenceTime = occurrencetime;
		this.priorityClass = priorityClass;
	}
	
	public float getOccurrenceTime() {
		return occurrenceTime;
	}
	
	public int getPriorityClass() {
		return priorityClass;
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
