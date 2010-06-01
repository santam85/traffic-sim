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
		RandomProvider rnd = new RandomProvider(Provider.Java,1);
		Distribution d = new ExponentialDistribution(0,rnd);
		
		int N = 1000;
		int K = 1;
		
		double[] ii = new double[N];
		double[] ik = new double[N];
		
		for(int i = 0 ; i< N; i++){
			ii[i] = d.nextValue();
			for(int j = 1 ; j< K; j++){
				d.nextValue();
			}
			ik[i] = d.nextValue();
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
