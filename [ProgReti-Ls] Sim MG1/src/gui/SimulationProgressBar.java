package gui;

import javax.swing.JProgressBar;
import javax.swing.SwingUtilities;

import simulator.ISimulationProgressListener;

/**
 * Class that realizes a progress bar for monitoring the simulation progress.
 * 
 * @author Andrea Zagnoli, Marco Santarelli, Michael Gattavecchia. 
 */
public class SimulationProgressBar extends JProgressBar implements ISimulationProgressListener {

	private static final long serialVersionUID = -2485099479730879601L;
	
	/**
	 * Creates a new IndexingProgressBar object.
	 */
	public SimulationProgressBar() {
		this.setMaximum(100);
	}
	
	public void progressMade(float prog) {
		final float progress = prog;
		
		SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				setValue((int)(progress * 100));
				setIndeterminate(false);
			}
		});
	}
	
}
