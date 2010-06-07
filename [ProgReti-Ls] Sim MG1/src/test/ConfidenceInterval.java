package test;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Rectangle;

import org.apache.commons.math.MathException;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartFrame;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.plot.XYPlot;
import org.jfree.chart.renderer.xy.XYLineAndShapeRenderer;
import org.jfree.data.xy.DefaultXYDataset;

import simulator.Distribution;
import simulator.Provider;
import simulator.RandomProvider;
import simulator.UniformDistribution;
import simulator.Utils;

public class ConfidenceInterval {

	/**
	 * @param args
	 * @throws MathException 
	 */
	public static void main(String[] args){
		int[] nr = new int[]{25,50,100,250,500,1000};
		double[] nd = new double[]{0.5,0.75,0.9,0.95,0.975};
		
		double[] means = new double[nr.length];
		double[] vars = new double[nr.length];

		            
		RandomProvider rnd = new RandomProvider(Provider.Java,1);
		Distribution d = new UniformDistribution(rnd);
		
		for(int i = 0;i<nr.length;i++){
			double[] run = new double[nr[i]];
			
			for (int j = 0; j<nr[i];j++){
				run[j]=d.nextValue();
			}
			
			means[i] = Utils.mean(run);
			vars[i] = Utils.cvar(run,means[i]);
			System.out.println(means[i] + " " + vars[i]);
		}
		
		double[][] delta1 = new double[2][nr.length];
		double[][] delta2 = new double[2][nd.length];
		
		DefaultXYDataset dataset1 = new DefaultXYDataset();
		DefaultXYDataset dataset2 = new DefaultXYDataset();
		
		for(int i = 0; i<nr.length;i++){
			delta1[1][i] = Utils.confidenceInterval(nr[i],0.975,vars[i]);
			delta1[0][i] = nr[i];
		}
		
		dataset1.addSeries("Fixed confidence level",delta1);
		
		for(int i = 0; i<nd.length;i++){
			delta2[1][i] = Utils.confidenceInterval(nr[1],nd[i],vars[i]);
			delta2[0][i] = nd[i];
		}

		dataset2.addSeries("Fixed values number", delta2);
		
		JFreeChart chart1 = ChartFactory.createXYLineChart("Confidence interval", // chart title 
				"Values", // domain axis label 
				"Confidence interval size", // range axis label 
				dataset1, // data
				PlotOrientation.VERTICAL, // orientation 
				true, // include legend 
				true, // tooltips 
				false // urls
		);
		
		JFreeChart chart2 = ChartFactory.createXYLineChart("Confidence interval", // chart title 
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
		f1.setBounds(0, 0, 1000, 600);
		f2.setBounds(0, 0, 1000, 600);
		
		// Graphic layout chart1
		XYPlot cp=(XYPlot)chart1.getPlot();
		cp.setBackgroundPaint(Color.white);
		cp.setRangeGridlinePaint(Color.gray);
		XYLineAndShapeRenderer renderer = (XYLineAndShapeRenderer) cp.getRenderer();
		renderer.setSeriesShapesVisible(0,true);
		renderer.setDrawOutlines(true); 
		renderer.setUseFillPaint(true);
		renderer.setSeriesStroke(0, new BasicStroke(2));
		renderer.setSeriesShape(0, new Rectangle(-2,-2,4,4));

		// Graphic layout chart1
		cp=(XYPlot)chart2.getPlot();
		cp.setBackgroundPaint(Color.white);
		cp.setRangeGridlinePaint(Color.gray);
		renderer = (XYLineAndShapeRenderer) cp.getRenderer();
		renderer.setSeriesShapesVisible(0,true);
		renderer.setDrawOutlines(true); 
		renderer.setUseFillPaint(true);
		renderer.setSeriesStroke(0, new BasicStroke(2));
		renderer.setSeriesShape(0, new Rectangle(-2,-2,4,4));
		
		f1.setVisible(true);
		f2.setVisible(true);
		
	}

}
