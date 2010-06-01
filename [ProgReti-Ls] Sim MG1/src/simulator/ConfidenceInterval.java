package simulator;

import org.apache.commons.math.MathException;
import org.apache.commons.math.distribution.TDistributionImpl;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartFrame;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.data.category.DefaultCategoryDataset;

public class ConfidenceInterval {

	/**
	 * @param args
	 * @throws MathException 
	 */
	public static void main(String[] args){
		int[] nr = new int[]{5,10,25,50,100,1000,10000};
		double[] nd = new double[]{0.5,0.75,0.9,0.95,0.975};
		
		double[] means = new double[nr.length];
		double[] vars = new double[nr.length];

		            
		RandomProvider rnd = new RandomProvider(Provider.Java,1);
		Distribution d = new ExponentialDistribution(0,rnd);
		
		for(int i = 0;i<nr.length;i++){
			double[] run = new double[nr[i]];
			
			for (int j = 0; j<nr[i];j++){
				run[j]=d.nextValue();
			}
			
			means[i] = mean(run);
			vars[i] = cvar(run,means[i]);
		}
		
		double[] delta1 = new double[nr.length];
		double[] delta2 = new double[nr.length];
		
		DefaultCategoryDataset dataset1 = new DefaultCategoryDataset();
		DefaultCategoryDataset dataset2 = new DefaultCategoryDataset();
		
		for(int i = 0; i<nr.length;i++){
			delta1[i] = confidenceInterval(nr[i],0.975,vars[i]);
			dataset1.addValue(delta1[i], "Fixed confidence level", nr[i]+" values");
		}
		
		for(int i = 0; i<nd.length;i++){
			delta2[i] = confidenceInterval(nr[1],nd[i],vars[i]);
			dataset2.addValue(delta2[i], "Fixed values number", nd[i]*100+"%");
		}

		JFreeChart chart1 = ChartFactory.createLineChart("Confidence interval", // chart title 
				"Values", // domain axis label 
				"Confidence interval size", // range axis label 
				dataset1, // data
				PlotOrientation.VERTICAL, // orientation 
				true, // include legend 
				true, // tooltips 
				false // urls
		);
		
		JFreeChart chart2 = ChartFactory.createLineChart("Confidence interval", // chart title 
				"Confidence level", // domain axis label 
				"Confidence interval size", // range axis label 
				dataset2, // data
				PlotOrientation.VERTICAL, // orientation 
				true, // include legend 
				true, // tooltips 
				false // urls
		);
		
		ChartFrame f1 = new ChartFrame("Title", chart1);
		ChartFrame f2 = new ChartFrame("Title", chart2);
		f1.pack();
		f1.setVisible(true);
		f2.pack();
		f2.setVisible(true);
		
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
