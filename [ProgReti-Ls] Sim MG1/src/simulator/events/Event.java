package simulator.events;

/**
 * Abstract class implementing a generic traffic-related event
 * 
 * @author Andrea Zagnoli, Marco Santarelli, Michael Gattavecchia. 
 *
 */
public abstract class Event {
	
	protected static int idc = 0;
	
	protected int id;
	protected double occurrenceTime;
	protected double serviceTime;
	protected int priorityClass;
	
	/**
	 * Constructor for Event class
	 * 
	 * @param occurrencetime The event occurrence time
	 * @param priorityClass The event priority class
	 */
	public Event(double occurrencetime,int priorityClass) {
		this(occurrencetime,0,priorityClass);
	}
	/**
	 * Constructor for Event class
	 * 
	 * @param occurrencetime The event occurrence time
	 * @param priorityClass The event priority class
	 * @param servicetime The event service time
	 */
	public Event(double occurrencetime, double servicetime, int priorityClass) {
		this.occurrenceTime = occurrencetime;
		this.serviceTime = servicetime;
		this.priorityClass = priorityClass;
	}
	
	/**
	 * Getter for event id
	 * @return The event id
	 */
	public int getId() {
		return id;
	}
	
	/**
	 * Getter for event occurrence time
	 * @return The occurrence time
	 */
	public double getOccurrenceTime() {
		return occurrenceTime;
	}
	
	/**
	 * Getter for event service time
	 * @return The service time
	 */
	public double getServiceTime() {
		return serviceTime;
	}
	
	public int getPriorityClass() {
		return priorityClass;
	}
	
	public abstract String toString();
	
}
