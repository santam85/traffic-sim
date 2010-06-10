package simulator;

import java.util.HashMap;

import org.apache.log4j.Level;
import org.apache.log4j.Logger;

public class SimulationRunners {

	private static final Logger log = Logger.getLogger("simulation");
	static {
		log.setLevel(Level.INFO);
	}
	
	private static final SimulationProgress progress = SimulationProgress.getInstance();
	
	public static double[][] testConfidenceIntervalWithVariableConfidence(Provider provider, int N) {
		int numberPerRun = 1000;
		double[] nd = new double[]{0.5,0.75,0.9,0.95,0.975};
		
		log.info("--------------------------------------------");
		log.info("Test confidence interval with variable level of confidence");
		String s = "";
		for (int i = 0; i < nd.length; i ++)
			s += nd[i] + ((i == nd.length - 1)?"":",");
		log.info("Tested level of confidence: " + s);
		
		double[] means = new double[N];
		double[] vars = new double[N];
	
		            
		RandomProvider rnd = new RandomProvider(provider,1);
		Distribution d = new UniformDistribution(rnd);
		
		progress.reset();
		progress.updateTotalAmmount(N + 1);
		for(int i = 0;i<N;i++){
			double[] run = new double[numberPerRun];
			
			for (int j = 0; j<numberPerRun;j++){
				run[j]=d.nextValue();
			}
			
			means[i] = Utils.mean(run);
			vars[i] = Utils.cvar(run,means[i]);
			progress.updateCurrentAmmount(1);

		}
		
		double[][] delta = new double[2][nd.length];
		for(int i = 0; i<nd.length;i++){
			delta[1][i] = Utils.confidenceInterval(numberPerRun,nd[i],vars[i]);
			delta[0][i] = nd[i];
			log.info("[Level of confidence: " + nd[i] + " ] " + " [ MEAN: " + means[i] + " VAR: " + vars[i] + " CONF: " + delta[1][i] + " ]");
		}
		progress.updateCurrentAmmount(1);
		return delta;
	}

	public static double[][] testConfidenceIntervalWithVariableRuns(Provider provider, double confidenceLevel) {
		log.info("--------------------------------------------");
		log.info("Test confidence interval with variable runs");
		
		int[] nr = new int[]{25,50,100,250,500,1000};
		String s = "";
		for (int i = 0; i < nr.length; i ++)
			s += nr[i] + ((i == nr.length - 1)?"":",");
		log.info("Number of runs: " + s);
		
		double[] means = new double[nr.length];
		double[] vars = new double[nr.length];
	
		            
		RandomProvider rnd = new RandomProvider(provider,1);
		Distribution d = new UniformDistribution(rnd);
		
		progress.reset();
		progress.updateTotalAmmount(nr.length + 1);
		for(int i = 0;i<nr.length;i++){
			double[] run = new double[nr[i]];
			
			for (int j = 0; j<nr[i];j++){
				run[j]=d.nextValue();
			}
			
			means[i] = Utils.mean(run);
			vars[i] = Utils.cvar(run,means[i]);
			progress.updateCurrentAmmount(1);
		}
		
		double[][] delta = new double[2][nr.length];
		
		for(int i = 0; i<nr.length;i++){
			delta[1][i] = Utils.confidenceInterval(nr[i],confidenceLevel,vars[i]);
			delta[0][i] = nr[i];
			log.info("[ run " + nr[i] + "]  [ MEAN: " + means[i] + " VAR: " + vars[i] + " CONF: " + delta[1][i] + " ]");
		}
		progress.updateCurrentAmmount(1);
		log.info("--------------------------------------------");
		return delta;
	}

	public static void generateTraffic(double lambda, Distribution dist, int N) {
		double T = lambda*30;
		double[] runs = new double[N];
		double cmean, cvar, idc;
		
		log.info("--------------------------------------------");
		log.info("Generate traffic with " + dist.getDistributionName() + " distribution and " + N + " runs");
		
		progress.reset();
		progress.updateTotalAmmount(N + 1);
		for (int j = 0; j < N; j ++) {
			int counter = 0;
			for (double now = 0; now <= T; ) {
				now += dist.nextValue();
				if (now <= T)
					counter ++;
			}
			
			runs[j] = counter;
			progress.updateCurrentAmmount(1);
		}
		
		cmean = Utils.mean(runs);
		cvar = Utils.cvar(runs,cmean);
		idc = cvar/(cmean);
		log.info(dist.getDistributionName() + " [ MEAN: " + cmean + " VAR: " + cvar + " IDC: " + idc + " ]");
		
		progress.updateCurrentAmmount(1);
		log.info("--------------------------------------------");
	}
	
	private static double[] simulateMG1(Distribution dist, double rho, double mu, int N) {
		double[] res = new double[3];
		double lambda = rho*mu;
		double confLevel = 0.975;
		
		double[] run = new double[N];
		for(int j=0;j<N;j++){
			Simulator s = new Simulator(new Distribution[]{new ExponentialDistribution(lambda)},dist);
			s.run();
			run[j]=s.getEtaByClass(0);
			progress.updateCurrentAmmount(1);
		}
		
		res[0] = Utils.mean(run);
		res[1] = Utils.cvar(run, res[0]);
		res[2] = Utils.confidenceInterval(N, confLevel, res[1]);
		return res;
	}

	public static double[][] compareMG1Simulations(double rho, double mu, int N) {
	
		Distribution[] dists = new Distribution[]{new DeterministicDistribution(mu),
				new ExponentialDistribution(mu),
				new ParetoDistribution(2.5,Utils.computeParetoBeta(mu,2.5)),
				new ParetoDistribution(1.2,Utils.computeParetoBeta(mu,1.2))
		};
		double[][] res = new double[3][dists.length];
		
		log.info("--------------------------------------------");
		log.info("Compare M/G/1 simulation");
		log.info("Simulation parameters [ RHO: " + rho + " MU: " + mu + "#runs: " + N + " ]");
		
		progress.reset();
		progress.updateTotalAmmount(dists.length * N);
		for(int i=0; i<dists.length;i++){
			double[] values = SimulationRunners.simulateMG1(dists[i],rho,mu,N);
			res[0][i] = values[0];
			res[1][i] = values[1];
			res[2][i] = values[2];
			log.info("[ " + dists[i].getDistributionName() + " ] [ MEAN: " + res[0][i] + " VAR: " + res[1][i] + " CONF: " + res[2][i] + " ]");
			progress.updateCurrentAmmount(1);
		}
		log.info("--------------------------------------------");
		
		return res;
	}

	public static double[][] simulateMG1WithVariableRho(Distribution dist, double[] rhos, double mu, int N) {
		double[][] res = new double[3][rhos.length];
		
		log.info("--------------------------------------------");
		log.info("Simulate M/G/1 with variable rho");
		String s = "";
		for (int i = 0; i < rhos.length; i ++)
			s += rhos[i] + ((i == rhos.length - 1)?"":",");
		log.info("Tested rho: " + s);
		
		progress.reset();
		progress.updateTotalAmmount(rhos.length * N);
		for(int i=0; i<rhos.length;i++){
			double[] vals = simulateMG1(dist,rhos[i],mu,N);
			res[0][i] = vals[0];
			res[1][i] = vals[1];
			res[2][i] = vals[2];
			log.info("[rho " + rhos[i] + " ]  [ MEAN: " + res[0][i] + " VAR: " + res[1][i] + " CONF: " + res[2][i] + " ]");
			progress.updateCurrentAmmount(1);
		}
		
		log.info("--------------------------------------------");
		
		return res;
	}
	
	public static void simulateMG1EvaluatingProbability(Distribution dist, double rho, double mu, int N) {
		log.info("--------------------------------------------");
		log.info("Evaluating M/G/1 state's probability");
		
		double lambda = rho*mu;
		
		progress.reset();
		progress.updateTotalAmmount(N);
		HashMap<Integer,double[]> prob = new HashMap<Integer,double[]>();
		for(int j=0;j<N;j++){
			Simulator s = new Simulator(new Distribution[]{new ExponentialDistribution(lambda)},dist);
			s.run();
			HashMap<Integer,Double> temp = s.getStatesProbobility();
			Utils.meshMaps(prob,temp);
			log.info("[run " + j + "] " + Utils.mapToString(temp));
			progress.updateCurrentAmmount(1);
		}
		log.info("[MEAN of runs] " + Utils.mapToString(Utils.computeMeanOnMap(prob)));
	}

	private static double[][] simulateMG1Prio(Distribution dist, double[] rho, double mu, int N) {
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
			
			progress.updateCurrentAmmount(1);
		}
		
		for (int x = 0; x < rho.length; x ++) {
			res[x][0] = Utils.mean(run[x]);
			res[x][1] = Utils.cvar(run[x], res[x][0]);
			res[x][2] = Utils.confidenceInterval(N, confLevel, res[x][1]);
		}
		return res;
	}

	public static double[][][] simulateMG1PrioWithVariableRhos(double mu, int N, String type) {
		double[][][] res = new double[99][type.equals("2")?2 + 1:3 + 1][4];
		double[] rhos = null;
		double rho = 0.8;
		ExponentialDistribution dist = new ExponentialDistribution(mu);
		
		log.info("--------------------------------------------");
		log.info("Simulate M/G/1//PRIO with variable rho [TYPE: " + type + "]");
		log.info("Simulation parameters [RHO: " + rho + " MU: " + mu + "]");
		
		progress.reset();
		progress.updateTotalAmmount(N * 99);
		
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
			
			
			double[][] partial_res = simulateMG1Prio(dist,rhos,mu,N);
			
			String s = "";
			for (int j = 0; j < rhos.length; j ++) {
				res[i][j][0] = x;
				res[i][j][1] = partial_res[j][0];
				res[i][j][2] = partial_res[j][1];
				res[i][j][3] = partial_res[j][2];
				s += "MEAN-" + j + " " + partial_res[j][0] + " ";
			}
			log.info("[x= " + x + "] " + " [ " + s + "]");
			
			res[i][res[0].length - 1][0] = x;
			res[i][res[0].length - 1][1] = 1.0/mu*(rho/(1.0 - rho));
			res[i][res[0].length - 1][2] = 0;
			res[i][res[0].length - 1][3] = 0;
			
			progress.updateCurrentAmmount(1);
		}
		
		log.info("--------------------------------------------");
		
		return res;
	}

}
