package test;

import simulator.SimulationRunners;
import simulator.misc.LogaritmicQuantizator;

public class TestQuantization {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		
		// LogaritmicQuantizator q = new LogaritmicQuantizator(2);
		// System.out.println(q.discretize(4) + " " + q.getDiscretizationClass(4));
		
		SimulationRunners.simulateMG1SJN();
	}

}
