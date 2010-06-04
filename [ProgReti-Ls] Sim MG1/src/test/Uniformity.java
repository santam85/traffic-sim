package test;

import java.awt.Color;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartFrame;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.plot.XYPlot;
import org.jfree.data.xy.DefaultXYDataset;

import simulator.Distribution;
import simulator.Provider;
import simulator.RandomProvider;
import simulator.UniformDistribution;

public class Uniformity {


	/**
	 * @param args
	 */
	public static void main(String[] args) {
		
		RandomProvider rnd = new RandomProvider(Provider.Java,1);
		Distribution d = new UniformDistribution(rnd);
		
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
			System.out.println(ii[i]+" "+ik[i]);
		}
	
		double[][] data = new double[][]{ii,ik};
		
		DefaultXYDataset dataset = new DefaultXYDataset();
		dataset.addSeries("Scatter", data);
		
		
		JFreeChart chart = ChartFactory.createScatterPlot("", "", "", dataset, PlotOrientation.VERTICAL, false, false, false);
		
		XYPlot p=chart.getXYPlot();
		p.setBackgroundPaint(Color.white);
		p.setDomainCrosshairVisible(true);
		p.setRangeGridlinePaint(Color.gray);
		p.setDomainGridlinePaint(Color.gray);
		//p.setDomainCrosshairPaint(Color.green);
		
		

		ChartFrame f = new ChartFrame("Random number generator", chart);
		f.setBounds(0, 0, 1000, 600);
		f.setVisible(true);
	}

}
