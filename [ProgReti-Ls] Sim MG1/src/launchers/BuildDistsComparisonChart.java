package launchers;

import gui.GraphUtils;

import java.util.LinkedList;

import org.apache.log4j.Level;
import org.apache.log4j.Logger;
import org.apache.log4j.SimpleLayout;

import simulator.SimulationRunners;
import simulator.distribution.DeterministicDistribution;
import simulator.distribution.Distribution;
import simulator.distribution.ExponentialDistribution;
import simulator.distribution.ParetoDistribution;
import simulator.misc.Utils;

public class BuildDistsComparisonChart {

	private static final Logger log = Logger.getLogger("simulation");
	static {
		log.setLevel(Level.INFO);
	}
	
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		Logger.getLogger("simulation").addAppender(new org.apache.log4j.ConsoleAppender(new SimpleLayout(),org.apache.log4j.ConsoleAppender.SYSTEM_OUT));
		
		int runs = 100;
		int arrivals = 1000;
		double mu = 5;
		
		Distribution[] dists = new Distribution[]{new DeterministicDistribution(mu),
				new ExponentialDistribution(mu),
				new ParetoDistribution(2.5,Utils.computeParetoBeta(mu,2.5)),
		};
		
		double[] rhos = new double[]{0.1,0.2,0.3,0.4,0.5,0.6,0.7,0.8,0.9};
		String[] keys = new String[]{"0.1","0.2","0.3","0.4","0.5","0.6","0.7","0.8","0.9"};
		String[] legends = new String[dists.length+2];
		LinkedList<double[]> y = new LinkedList<double[]>();
		LinkedList<double[]> c = new LinkedList<double[]>();

		for(int l=0;l<dists.length;l++){
			final double[][] res = SimulationRunners.simulateMG1WithVariableRho(dists[l],rhos,mu,runs,arrivals);
			y.add(res[0]);
			c.add(res[2]);
			
			legends[l]=dists[l].toString();
		}
		
		
		double[] t1= new double[rhos.length];
		double[] t2= new double[rhos.length];
		//Generazione delle funz. teoriche
		for(int i=0; i<rhos.length;i++){
			t1[i]=(1.0/mu)*(rhos[i]/(2*(1-rhos[i])));
			t2[i]=0;
		}
		y.add(t1);
		c.add(t2);
		legends[legends.length-2] = "Deterministic (Theoretical)";
		
		t1= new double[rhos.length];
		t2= new double[rhos.length];
		//Generazione delle funz. teoriche
		for(int i=0; i<rhos.length;i++){
			t1[i]=(1.0/mu)*(rhos[i]/((1-rhos[i])));
			t2[i]=0;
		}
		y.add(t1);
		c.add(t2);
		legends[legends.length-1] = "Exponential (Theoretical)";
		
		GraphUtils.displayStatisticalLineChart("MG1Sim","Variable rho","Rho","Time",legends,keys,y,c);
	}	
		
}
