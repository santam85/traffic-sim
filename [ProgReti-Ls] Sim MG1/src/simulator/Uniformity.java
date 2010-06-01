package simulator;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartFrame;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.data.xy.DefaultXYDataset;

public class Uniformity {


	/**
	 * @param args
	 */
	public static void main(String[] args) {
		Distribution d = new Distribution(DistributionType.Uniform, 0);
		Rnd rnd = new Rnd(RandomnessProvider.Java,1);
		
		int N = 1000;
		int K = 1;
		
		double[] ii = new double[N];
		double[] ik = new double[N];
		
		for(int i = 0 ; i< N; i++){
			ii[i] = rnd.nextRandom(d);
			for(int j = 1 ; j< K; j++){
				rnd.nextRandom(d);
			}
			ik[i] = rnd.nextRandom(d);
		}
	
		double[][] data = new double[][]{ii,ik};
		
		DefaultXYDataset dataset = new DefaultXYDataset();
		dataset.addSeries("Scatter", data);
		
		JFreeChart chart = ChartFactory.createScatterPlot("", "", "", dataset, PlotOrientation.VERTICAL, false, false, false);
		

		ChartFrame f = new ChartFrame("Random number generator", chart);
		f.pack();
		f.setVisible(true);
	}

}
