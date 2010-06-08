package simulator;

public class Arrival extends Event {
	public double serviceTime;
	public int priorityClass;
	
	public Arrival(double occurrenceTime,double serviceTime, int priorityClass) {
		super(occurrenceTime,priorityClass);
		this.id = idc++;
		this.serviceTime = serviceTime;
		this.priorityClass = priorityClass;
	}
	
	@Override
	public String toString(){
		return "["+ id + "] " + super.occurrenceTime + " Arrival " + serviceTime + " " + priorityClass;
	}
}
