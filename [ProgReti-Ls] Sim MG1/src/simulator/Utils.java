package simulator;

import java.util.HashMap;
import java.util.Iterator;

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
	
	public static void meshMaps(HashMap<Integer,double[]> result, HashMap<Integer,Double> partial) {
		Iterator<Integer> it = partial.keySet().iterator();
		while (it.hasNext()) {
			int i = it.next();
			if (!result.containsKey(i)) {
				result.put(i,new double[]{1,partial.get(i)});
			}
			else {
				double[] tmp = result.get(i);
				result.put(i,new double[]{tmp[0] + 1, tmp[1] + partial.get(i)});
			}
		}
	}
	
	public static String mapToString(HashMap<Integer,Double> map) {
		String res = "";
		Iterator<Integer> it = map.keySet().iterator();
		while (it.hasNext()) {
			int i = it.next();
			res += "[ P" + i + " ]: " + map.get(i) + "  ";
		}
		return res;
	}
	
	public static HashMap<Integer,Double> computeMeanOnMap(HashMap<Integer,double[]> values) {
		HashMap<Integer,Double> res = new HashMap<Integer,Double>();
		Iterator<Integer> it = values.keySet().iterator();
		while (it.hasNext()) {
			int i = it.next();
			res.put(i,values.get(i)[1]/values.get(i)[0]);
		}
		return res;
	}
}
