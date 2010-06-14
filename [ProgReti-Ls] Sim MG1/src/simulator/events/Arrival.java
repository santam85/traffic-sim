package simulator.events;

/**
 * Class implementing an arrival event 
 * 
 * @author Andrea Zagnoli, Marco Santarelli, Michael Gattavecchia. 
 *
 */
public class Arrival extends Event {
	
	public Arrival(double occurrenceTime,double serviceTime, int priorityClass) {
		super(occurrenceTime,priorityClass);
		this.id = idc++;
		this.serviceTime = serviceTime;
	}
	
	@Override
	public String toString(){
		return "["+ id + "] " + super.occurrenceTime + " Arrival " + serviceTime + " " + priorityClass;
	}
}
