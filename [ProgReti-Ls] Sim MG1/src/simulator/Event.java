package simulator;

public abstract class Event implements Comparable<Event>{
	
	protected static int idc = 0;
	
	protected int id;
	protected double occurrenceTime;
	protected int priorityClass;
	
	
	public Event(double occurrencetime,int priorityClass) {
		this.occurrenceTime = occurrencetime;
		this.priorityClass = priorityClass;
	}
	
	public double getOccurrenceTime() {
		return occurrenceTime;
	}
	
	public int getPriorityClass() {
		return priorityClass;
	}
	
	public int getId() {
		return id;
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
