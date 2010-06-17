package simulator.misc;

import org.apache.commons.math.MathException;
import org.apache.commons.math.distribution.TDistributionImpl;

/**
 * Some utility functions
 * 
 * @author andreazagnoli
 */
public class Utils {

	/**
	 * compute the means of the values passed as input
	 * 
	 * @param vals input values
	 * @return values' mean
	 */
	public static double mean(double[] vals) {
		double mean = 0;
		for (int i = 0; i < vals.length; i++) {
			mean += vals[i];
		}
		return mean / vals.length;
	}

	/**
	 * computes the variance of the given values, with the given mean value
	 * 
	 * @param vals input values
	 * @param mean mean of the input values
	 * @return computed variance
	 */
	public static double cvar(double[] vals, double mean) {
		double var = 0;
		if (vals.length == 1)
			return 0;
		for (int i = 0; i < vals.length; i++) {
			var += Math.pow(vals[i] - mean, 2);
		}
		return var / (vals.length - 1);
	}

	/**
	 * computes the confidence interval
	 * 
	 * @param runs Number of samples
	 * @param levelOfConfidence the desired level of confidence
	 * @param cvar the variance of the samples
	 * @return the computed confidence interval
	 */
	public static double confidenceInterval(int runs, double levelOfConfidence,
			double cvar) {
		if (runs == 1)
			return 0;
		TDistributionImpl td = new TDistributionImpl(runs - 1);
		double ts = 0;
		try {
			ts = td.inverseCumulativeProbability(1-((1-levelOfConfidence)/2));
		} catch (MathException e) {
			e.printStackTrace();
		}
		return ts * Math.sqrt(cvar / runs);
	}

	/**
	 * Computes the lambda0 value of a Switched Poisson Process given the lambda mean value, 
	 * the states' probability and the factor lambda1/lambda0
	 * 
	 * @param lambda mean lambda of the SPP
	 * @param p0 state-0 probability
	 * @param p1 state-1 probability
	 * @param k the factor lambda1/lambda0
	 * @return the computed lambda0 
	 */
	public static double computeSPPLambda0(double lambda, double p0, double p1,
			int k) {
		return (lambda / (p0 + p1 * k));
	}

	/**
	 * 
	 * @param lambda
	 * @param alfa
	 * @return
	 */
	public static double computeParetoBeta(double lambda, double alfa) {
		return (((1 / lambda) * (alfa - 1)) / alfa);
	}
}
