package simulator.misc;

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
		if (vals.length == 1)
			return 0;
		for (int i = 0; i < vals.length;i++){
			var+=Math.pow(vals[i]-mean,2);	
		}
		return var/(vals.length-1);
	}
	
	public static double confidenceInterval(int runs, double levelOfConfidence, double cvar){
		if (runs == 1)
			return 0;
		TDistributionImpl td = new TDistributionImpl(runs-1);
		double ts = 0;
		try {
			ts = td.inverseCumulativeProbability(levelOfConfidence);
		} catch (MathException e) {
			e.printStackTrace();
		}
		return ts*Math.sqrt(cvar/runs);
	}
	
	public static double computeSPPLambda0(double lambda, double p0, double p1, int k) {
		return (lambda/(p0+p1*k));
	}
	
	public static double computeParetoBeta(double lambda, double alfa) {
		return  (((1/lambda) * (alfa - 1))/alfa);
	}
}
