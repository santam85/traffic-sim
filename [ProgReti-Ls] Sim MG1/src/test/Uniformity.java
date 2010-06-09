package test;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Rectangle;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartFrame;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.CategoryPlot;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.plot.XYPlot;
import org.jfree.chart.renderer.category.LineAndShapeRenderer;
import org.jfree.data.category.DefaultCategoryDataset;
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
		
		RandomProvider rnd = new RandomProvider(Provider.Java);
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
			//System.out.println(ii[i]+" "+ik[i]);
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

		ChartFrame f = new ChartFrame("Random number generator", chart);
		f.setBounds(0, 0, 1000, 600);
		f.setVisible(true);
		
		//Media e varianza
		
		int [] nexp={5,10,20,50,100,500,1000,5000,10000,100000,1000000,1000000};
			
		DefaultCategoryDataset dataset1 = new DefaultCategoryDataset();
		double[][] values=new double[2][nexp.length];
		for (int j=0;j<nexp.length;j++){
			double s=0;
			
			for (int i=0;i<nexp[j];i++){
				s+=d.nextValue();
			}
			values[0][j]=nexp[j];
			values[1][j]=s/nexp[j];
			System.out.println(s);
			dataset1.addValue(values[1][j], "Average", ""+(int)(values[0][j]));
		}
		
		
		JFreeChart chart1 = ChartFactory.createLineChart("Average value", // chart title 
				"n. of experiments", // domain axis label 
				"Average value", // range axis label 
				dataset1, // data
				PlotOrientation.VERTICAL, // orientation 
				true, // include legend 
				true, // tooltips 
				false // urls
		);
		
		ChartFrame f1 = new ChartFrame("Average", chart1);
		f1.setBounds(0, 0, 1000, 500);
		
		// Sugar
		CategoryPlot cp=(CategoryPlot)chart1.getPlot();
		cp.setBackgroundPaint(Color.white);
		cp.setRangeGridlinePaint(Color.gray);
		LineAndShapeRenderer renderer = (LineAndShapeRenderer) cp.getRenderer();
		renderer.setSeriesShapesVisible(0,true);
		renderer.setDrawOutlines(true); 
		renderer.setUseFillPaint(true);
		renderer.setSeriesStroke(0, new BasicStroke(2));
		renderer.setSeriesShape(0, new Rectangle(-2,-2,4,4));
		// sugar end
		
		f1.setVisible(true);
		
	}

}
