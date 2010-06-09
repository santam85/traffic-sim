package simulator;

import org.apache.commons.math.MathException;
import org.apache.commons.math.distribution.TDistributionImpl;

public class Utils {

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
	
	public static double[][] testConfidenceIntervalWithVariableConfidence(Provider provider) {
		int N = 6;
		int numberPerRun = 1000;
		double[] nd = new double[]{0.5,0.75,0.9,0.95,0.975};
		
		double[] means = new double[N];
		double[] vars = new double[N];

		            
		RandomProvider rnd = new RandomProvider(provider,1);
		Distribution d = new UniformDistribution(rnd);
		
		for(int i = 0;i<N;i++){
			double[] run = new double[numberPerRun];
			
			for (int j = 0; j<numberPerRun;j++){
				run[j]=d.nextValue();
			}
			
			means[i] = Utils.mean(run);
			vars[i] = Utils.cvar(run,means[i]);
		}
		
		double[][] delta = new double[2][nd.length];
		for(int i = 0; i<nd.length;i++){
			delta[1][i] = Utils.confidenceInterval(numberPerRun,nd[i],vars[i]);
			delta[0][i] = nd[i];
		}
		return delta;
	}
	
	public static double[][] testConfidenceIntervalWithVariableRuns(Provider provider) {
		int[] nr = new int[]{25,50,100,250,500,1000};
		
		double[] means = new double[nr.length];
		double[] vars = new double[nr.length];

		            
		RandomProvider rnd = new RandomProvider(provider,1);
		Distribution d = new UniformDistribution(rnd);
		
		for(int i = 0;i<nr.length;i++){
			double[] run = new double[nr[i]];
			
			for (int j = 0; j<nr[i];j++){
				run[j]=d.nextValue();
			}
			
			means[i] = Utils.mean(run);
			vars[i] = Utils.cvar(run,means[i]);
		}
		
		double[][] delta = new double[2][nr.length];
		
		for(int i = 0; i<nr.length;i++){
			delta[1][i] = Utils.confidenceInterval(nr[i],0.975,vars[i]);
			delta[0][i] = nr[i];
		}
		return delta;

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
	
	public static double computeSPPLambda0(double lambda, double p0, double p1, int k) {
		return (double)(lambda/(p0+p1*k));
	}
	
	public static double computeParetoBeta(double lambda, double alfa) {
		return (double) (((1/lambda) * (alfa - 1))/alfa);
	}
	
	public static double[][] compareMG1Simulations(double rho, double mu, int N) {

		double[][] res = new double[3][N];
		Distribution[] dists = new Distribution[]{new DeterministicDistribution(mu),
				new ExponentialDistribution(mu),
				new ParetoDistribution(2.5,computeParetoBeta(mu,2.5)),
				new ParetoDistribution(1.2,computeParetoBeta(mu,1.2))
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
	
	public static double[] simulateMG1(Distribution dist, double rho, double mu, int N) {
		double[] res = new double[3];
		double lambda = rho*mu;
		double confLevel = 0.975;
		
		double[] run = new double[N];
		for(int j=0;j<N;j++){
			Simulator s = new Simulator(new Distribution[]{new ExponentialDistribution(lambda)},dist);
			s.run();
			run[j]=s.getEtaByClass(0);
		}
		
		res[0] = Utils.mean(run);
		res[1] = Utils.cvar(run, res[0]);
		res[2] = Utils.confidenceInterval(N, confLevel, res[1]);
		return res;
	}
	
	public static double[][] simulateMG1WithVariableRho(Distribution dist, double[] rhos, double mu, int N) {
		double[][] res = new double[3][rhos.length];
		
		for(int i=0; i<rhos.length;i++){
			double[] vals = simulateMG1(dist,rhos[i],mu,N);
			res[0][i] = vals[0];
			res[1][i] = vals[1];
			res[2][i] = vals[2];
		}
		
		return res;
	}
	
	public static double[][] simulateMG1Prio(Distribution dist, double[] rho, double mu, int N) {
		double[][] res = new double[rho.length][3];
		double[] lambda = new double[rho.length];
		Distribution[] arrivalDists = new Distribution[rho.length];
		
		for(int i=0; i<rho.length;i++){
			lambda[i] = rho[i]*mu;
			arrivalDists[i] = new ExponentialDistribution(lambda[i]);
		}
		double confLevel = 0.975;
		
		
		
		double[][] run = new double[rho.length][N];
		for(int j=0;j<N;j++){
			Simulator s = new Simulator(arrivalDists,dist);
			s.run();
			for (int x = 0; x < rho.length; x ++) {
				run[x][j]=s.getEtaByClass(x);
			}
		}
		
		for (int x = 0; x < rho.length; x ++) {
			res[x][0] = Utils.mean(run[x]);
			res[x][1] = Utils.cvar(run[x], res[x][0]);
			res[x][2] = Utils.confidenceInterval(N, confLevel, res[x][1]);
		}
		return res;
	}
	
	public static double[][][] simulateMG1PrioWithVariableRhos(double mu, String type) {
		double[][][] res = new double[99][type.equals("2")?2:3][4];
		double[] rhos = null;
		double rho = 0.8;
		int N = 10;
		ExponentialDistribution dist = new ExponentialDistribution(mu);
		
		for (int i = 0; i < 99; i ++) {	
			double x= (i+1)*1.0/100;
			if (type.equals("2")) {
				rhos = new double[2];
				rhos[0] = x*rho;
				rhos[1] = (1 - x)*rho;
			}
			else if (type.equals("3a")) {
				rhos = new double[3];
				rhos[0] = 0.5*x*rho;
				rhos[1] = 0.5*x*rho;
				rhos[2] = (1 - x)*rho;
			}
			else if (type.equals("3b")) {
				rhos = new double[3];
				rhos[0] = 0.1*x*rho;
				rhos[1] = 0.9*x*rho;
				rhos[2] = (1 - x)*rho;
			}
			else if (type.equals("3c")) {
				rhos = new double[3];
				rhos[0] = x*rho;
				rhos[1] = (1 - x)*0.5*rho;
				rhos[2] = (1 - x)*0.5*rho;
			}
			
			double[][] partial_res = Utils.simulateMG1Prio(dist,rhos,mu,N);
			
			for (int j = 0; j < rhos.length; j ++) {
				res[i][j][0] = x;
				res[i][j][1] = partial_res[j][0];
				res[i][j][2] = partial_res[j][1];
				res[i][j][3] = partial_res[j][2];
			}
		}
		
		return res;
	}
}
