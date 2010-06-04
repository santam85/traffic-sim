package test;

import simulator.Distribution;
import simulator.DeterministicDistribution;
import simulator.ExponentialDistribution;
import simulator.SPPDistribution;
import simulator.ParetoDistribution;
import simulator.Utils;

public class TestTrafficWithDifferentVariability {

	/**
	 * @param args
	 */
	public static void main(String[] args) {

		int N = 100;
		float lambda = 2;
		double T = 30/lambda;
		float k = 10;
		float q01 = 0.1f;
		float q10 = 0.9f;
		float lambda0 = lambda/(q01+q10*k);
		float lambda1 = k*lambda0;
		float shape = 3f; // impostato a piacere
		float mode = ((1/lambda) * (shape - 1))/shape;
		
		String[] distNames = new String[]{"Deterministic","Exponential","SPP","Pareto"};
		Distribution[] ds = new Distribution[]{
				new DeterministicDistribution(lambda),
				new ExponentialDistribution(lambda), 
				new SPPDistribution(lambda0,lambda1,q01,q10), 
				new ParetoDistribution(shape,mode)};
		
		double runs[][] = new double[ds.length][N];
		double cmeans[] = new double[ds.length];
		double cvars[] = new double[ds.length];
		double idcs[] = new double[ds.length];
		
		for (int i = 0; i < ds.length; i ++) {
			for (int j = 0; j < N; j ++) {
				
				int counter = 0;
				for (double now = 0; now <= T; ) {
					now += ds[i].nextValue();
					if (now <= T)
						counter ++;
				}
				
				runs[i][j] = counter;
			}
			
			cmeans[i] = Utils.mean(runs[i]);
			cvars[i] = Utils.cvar(runs[i],cmeans[i]);
			idcs[i] = cvars[i]/(cmeans[i]);
			System.out.println(distNames[i] + " [ MEAN: " + cmeans[i] + " VAR: " + cvars[i] + " IDC: " + idcs[i] + " ]");
		}
		
	}
}
