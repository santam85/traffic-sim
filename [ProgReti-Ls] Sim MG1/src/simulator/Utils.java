package simulator;

import org.apache.commons.math.MathException;
import org.apache.commons.math.distribution.TDistributionImpl;

public class Utils {
	public static float mean(float[] vals){
		float mean = 0;
		for (int i = 0; i < vals.length;i++){
			mean+=vals[i];
		}
		return mean/vals.length;
	}
	
	public static float cvar(float[] vals, float mean){
		float var = 0;
		for (int i = 0; i < vals.length;i++){
			var+=Math.pow(vals[i]-mean,2);	
		}
		return var/(vals.length-1);
	}

	public static double mean(double[] vals){
		double mean = 0;
		for (int i = 0; i < vals.length;i++){
			mean+=vals[i];
		}
		return mean/vals.length;
	}
	
	public static double cvar(double[] vals, double mean){
		double var = 0;
		for (int i = 0; i < vals.length;i++){
			var+=Math.pow(vals[i]-mean,2);	
		}
		return var/(vals.length-1);
	}
	
	public static double confidenceInterval(int runs, double levelOfConfidence, double cvar){
		TDistributionImpl td = new TDistributionImpl(runs-1);
		double ts = 0;
		try {
			ts = td.inverseCumulativeProbability(levelOfConfidence);
		} catch (MathException e) {
			e.printStackTrace();
		}
		return ts*Math.sqrt(cvar/runs);
	}
	
	public static void generateTraffic(double lambda, Distribution dist, int N) {
		double T = lambda*30;
		double[] runs = new double[N];
		double cmean, cvar, idc;
		
		for (int j = 0; j < N; j ++) {
			int counter = 0;
			for (double now = 0; now <= T; ) {
				now += dist.nextValue();
				if (now <= T)
					counter ++;
			}
			
			runs[j] = counter;
		}
		
		cmean = Utils.mean(runs);
		cvar = Utils.cvar(runs,cmean);
		idc = cvar/(cmean);
		System.out.println(dist.getDistributionName() + " [ MEAN: " + cmean + " VAR: " + cvar + " IDC: " + idc + " ]");

	}
	
	public static float computeSPPLambda0(double lambda, double p0, double p1, int k) {
		return (float)(lambda/(p0+p1*k));
	}
	
	public static float computeParetoBeta(float lambda, float alfa) {
		return (float) (((1/lambda) * (alfa - 1))/alfa);
	}
	
	public static double[][] compareMG1Simulations(float rho, float mu, int N) {

		double[][] res = new double[3][N];
		Distribution[] dists = new Distribution[]{new DeterministicDistribution(mu),
				new ExponentialDistribution(mu),
				new ParetoDistribution(2.5f,computeParetoBeta(mu,2.5f)),
				new ParetoDistribution(1.2f,computeParetoBeta(mu,1.2f))
		};
		
		for(int i=0; i<dists.length;i++){
			double[] values = simulateMG1(dists[i],rho,mu,N);
			res[0][i] = values[0];
			res[1][i] = values[1];
			res[2][i] = values[2];
			System.out.println(dists[i].getDistributionName() + " [ MEAN: " + res[0][i] + " VAR: " + res[1][i] + " CONF: " + res[2][i] + " ]");
		}
		return res;
	}
	
	public static double[] simulateMG1(Distribution dist, float rho, float mu, int N) {
		double[] res = new double[3];
		float lambda = rho*mu;
		double confLevel = 0.975;
		
		double[] run = new double[N];
		for(int j=0;j<N;j++){
			Simulator s = new Simulator(new Distribution[]{new ExponentialDistribution(lambda)},dist);
			s.run();
			run[j]=s.getEta();
		}
		
		res[0] = Utils.mean(run);
		res[1] = Utils.cvar(run, res[0]);
		res[2] = Utils.confidenceInterval(N, confLevel, res[1]);
		return res;
	}
	
	public static double[] simulateMG1Prio(Distribution dist, float[] rho, float mu, int N) {
		double[] res = new double[3];
		float[] lambda = new float[rho.length];
		Distribution[] arrivalDists = new Distribution[rho.length];
		
		for(int i=0; i<rho.length;i++){
			lambda[i] = rho[i]*mu;
			arrivalDists[i] = new ExponentialDistribution(lambda[i]);
		}
		double confLevel = 0.975;
		
		
		
		double[] run = new double[N];
		for(int j=0;j<N;j++){
			Simulator s = new Simulator(arrivalDists,dist);
			s.run();
			run[j]=s.getEta();
		}
		
		res[0] = Utils.mean(run);
		res[1] = Utils.cvar(run, res[0]);
		res[2] = Utils.confidenceInterval(N, confLevel, res[1]);
		return res;
	}
	
	public static double[][] simulateMG1PrioWithVariableRhos(float mu, String type) {
		
		double[][] res = new double[100][4];
		float[] rhos = null;
		float rho = 0.8f;
		int N = 100;
		ExponentialDistribution dist = new ExponentialDistribution(mu);
		
		for (float x = 0.01f, i = 0; x < 1; x += 0.01, i ++) {	
			if (type.equals("2")) {
				rhos = new float[2];
				rhos[0] = x*rho;
				rhos[1] = (1 - x)*rho;
			}
			else if (type.equals("3a")) {
				rhos = new float[3];
				rhos[0] = x/2*rho;
				rhos[1] = x/2*rho;
				rhos[2] = (1 - x)*rho;
			}
			else if (type.equals("3b")) {
				rhos = new float[3];
				rhos[0] = x/10*rho;
				rhos[1] = 9*x/10*rho;
				rhos[2] = (1 - x)*rho;
			}
			else if (type.equals("3c")) {
				rhos = new float[3];
				rhos[0] = x*rho;
				rhos[1] = (1 - x)/2*rho;
				rhos[2] = (1 - x)/2*rho;
			}
			
			double[] partial_res = Utils.simulateMG1Prio(dist,rhos,mu,N);
			res[(int)i][0] = x;
			res[(int)i][1] = partial_res[0];
			res[(int)i][2] = partial_res[1];
			res[(int)i][3] = partial_res[2];
		}
		
		return res;
	}
	
	public static double[][] simulateMG1WithVariableRho(Distribution dist, float[] rhos, float mu, int N) {
		double[][] res = new double[3][rhos.length];
		
		for(int i=0; i<rhos.length;i++){
			double[] vals = simulateMG1(dist,rhos[i],mu,N);
			res[0][i] = vals[0];
			res[1][i] = vals[1];
			res[2][i] = vals[2];
		}
		
		return res;
	}
}
