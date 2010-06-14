package test;

import gui.GraphUtils;

import java.util.LinkedList;

import simulator.FCFSSimulator;
import simulator.Simulator;
import simulator.distribution.Distribution;
import simulator.distribution.ExponentialDistribution;
import simulator.events.ComparableEvent;
import simulator.random.Provider;
import simulator.random.RandomProvider;

public class Simulation {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		RandomProvider r = new RandomProvider(Provider.Java);
		Simulator s = new FCFSSimulator(new Distribution[]{new ExponentialDistribution(10)},new ExponentialDistribution(11,r));
		s.run();
		LinkedList<ComparableEvent> history = s.getHistory();
		
		GraphUtils.displayStateStepChart("State chart", "K(t)", "Time", "Clients #", "Exponential service time", history);
	}

}
