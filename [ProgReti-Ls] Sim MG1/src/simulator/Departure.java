package simulator;

public class Departure extends Event {
	public int id;

	public Departure(float freeTime, int id, int priorityClass) {
		super(freeTime, priorityClass);
		this.id = id;
	}
	
	@Override
	public String toString(){
		return "["+id+"] " + super.occurrenceTime + " Departure";
	}
}
