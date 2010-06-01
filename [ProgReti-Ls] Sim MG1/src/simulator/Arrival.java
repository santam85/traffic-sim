package simulator;

public class Arrival extends Event {
	public float serviceTime;
	public int priorityClass;
	
	public Arrival(float occurrenceTime,float serviceTime, int priorityClass) {
		super(occurrenceTime);
		this.id = idc++;
		this.serviceTime = serviceTime;
		this.priorityClass = priorityClass;
	}
	
	@Override
	public String toString(){
		return "["+ id + "] " + super.occurrenceTime + " Arrival " + serviceTime + " " + priorityClass;
	}
}
