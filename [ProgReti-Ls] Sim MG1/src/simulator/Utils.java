package simulator;

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
	
}
