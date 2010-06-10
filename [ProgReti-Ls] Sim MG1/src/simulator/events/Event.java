package simulator.events;

public abstract class Event {
	
	protected static int idc = 0;
	
	protected int id;
	protected double occurrenceTime;
	protected double serviceTime;
	protected int priorityClass;
	
	public Event(double occurrencetime,int priorityClass) {
		this(occurrencetime,0,priorityClass);
	}
	
	public Event(double occurrencetime, double servicetime, int priorityClass) {
		this.occurrenceTime = occurrencetime;
		this.serviceTime = servicetime;
		this.priorityClass = priorityClass;
	}
	
	public int getId() {
		return id;
	}
	
	public double getOccurrenceTime() {
		return occurrenceTime;
	}
	
	public double getServiceTime() {
		return serviceTime;
	}
	
	public int getPriorityClass() {
		return priorityClass;
	}
	
	public abstract String toString();
	
}
