package simulator;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * The object of this class, created as a singleton and returned using the getInstance method, is used to trace the progress of the indexing progress.
 * Using two main methods, <code>updateTotalAmmountToIndex()</code> and <code>updateAmmountCurrentlyIndexed()</code>, is possible to update 
 * respectively (in byte or whatever consistent unit) the total ammount to index and the ammount currently indexed. Every time one of this two operation is invoked, an event is generated 
 * notifying the current progress of the process.
 * 
 * @author Andrea Zagnoli, Marco Santarelli, Michael Gattavecchia. 
 */
public class SimulationProgress {
	
	private final static SimulationProgress obj = new SimulationProgress();
	
	private long total;
	private long currently;
	
	private final Lock lock = new ReentrantLock(true);
	
	private LinkedList<ISimulationProgressListener> listeners;
	
	private SimulationProgress() {
		this.total = 0;
		this.currently = 0;
		
		this.listeners = new LinkedList<ISimulationProgressListener>();
	}
	
	/**
	 * It returns the only instance of IndexingProgress
	 * 
	 * @return IndexingProgress instance
	 */
	public static SimulationProgress getInstance() {
		return obj;
	}
	
	/**
	 * Update the total ammount to index
	 * An event indicating the percentage of the progress is generated.
	 * 
	 * @param value Value indicating the sum to add to the total ammount to index
	 */
	public void updateTotalAmmount(long value) {
		long total, currently;
		try {
			lock.lock();
			this.total += value;
			
			total = this.total;
			currently = this.currently;
		}
		finally {
			lock.unlock();
		}
		signal(total,currently);
	}
	
	/**
	 * Update the ammount currently indexed.
	 * An event indicating the percentage of the progress is generated.
	 * 
	 * @param value Value indicating the sum to add to the ammount currently indexed
	 */
	public void updateCurrentAmmount(long value) {
		long total, currently;
		try {
			lock.lock();
			this.currently += value;
			total = this.total;
			currently = this.currently;
		}
		finally {
			lock.unlock();
		}
		signal(total,currently);
	}
	
	/**
	 * Reset the values.
	 * An event indicating the percentage of the progress is generated.
	 */
	public void reset() {
		try {
			lock.lock();
			this.total = 0;
			this.currently = 0;
		}
		finally {
			lock.unlock();
		}
		signal(0,0);
	}
	
	/**
	 * Add a listener to the list
	 * 
	 * @param l Listener to add
	 */
	public void addIndexingProgressListener(ISimulationProgressListener l) {
		this.listeners.add(l);
	}
	
	/**
	 * Remove a listener from the list
	 * 
	 * @param l Listener to remove
	 */
	public void removeIndexingProgressListener(ISimulationProgressListener l) {
		this.listeners.remove(l);
	}
	
	// signal the event
	private void signal(long totalToIndex, long currentlyIndexed) {
		Iterator<ISimulationProgressListener> it = listeners.iterator();
		while (it.hasNext()) {
			it.next().progressMade((float)currentlyIndexed/(float)totalToIndex);
		}
	}
}
