package test;

import simulator.Distribution;
import simulator.ExponentialDistribution;
import simulator.SPPDistribution;
import simulator.ParetoDistribution;
import simulator.Provider;
import simulator.RandomProvider;

public class TestTrafficWithDifferentVariability {

	/**
	 * @param args
	 */
	public static void main(String[] args) {

		int N = 20;
		float lambda = 3;
		float k = 10;
		float q01 = 0.1f;
		float q10 = 0.9f;
		float lambda0 = (lambda*(q01+q10))/(q10+q01*k);
		float lambda1 = k*lambda0;
		float alfa = 0;
		float beta = 0;
		
		Distribution[] ds = new Distribution[]{new ExponentialDistribution(lambda), new SPPDistribution(lambda0,lambda1,q01,q10), new ParetoDistribution(alfa,beta)};
		double cmeans[][] = new double[ds.length][N];
		double cvars[][] = new double[ds.length][N];
		
		for (int i = 0; i < ds.length; i ++)
			for (int j = 0; j < N; j ++) {
				
				double[] runs = new double[N];
				//runs[i][j];
			}
		
	}
	// (mu * alfa - 1)/alfa = K
	// 

}
