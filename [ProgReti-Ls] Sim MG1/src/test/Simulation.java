package test;

import java.awt.Font;

import org.jfree.chart.ChartFrame;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.axis.CategoryAxis;
import org.jfree.chart.axis.NumberAxis;
import org.jfree.chart.axis.ValueAxis;
import org.jfree.chart.plot.CategoryPlot;
import org.jfree.chart.renderer.category.CategoryItemRenderer;
import org.jfree.chart.renderer.category.StatisticalBarRenderer;
import org.jfree.data.statistics.DefaultStatisticalCategoryDataset;

import simulator.ExponentialDistribution;
import simulator.Provider;
import simulator.RandomProvider;
import simulator.Simulator;
import simulator.Utils;

public class Simulation {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		int N = 100;
		float mu = 10;
		double confLevel = 0.975;
		
		float[] rhos = new float[]{0.1f,0.2f,0.3f,0.4f,0.5f,0.6f,0.7f,0.8f,0.9f};
		float[] meanetas = new float[rhos.length];
		float[] varetas = new float[rhos.length];
		double[] confint = new double[rhos.length];
		float[] runetas = new float[N];
		
		RandomProvider r = new RandomProvider(Provider.Ran0,3);
		
		for(int i=0; i<rhos.length;i++){
			float lambda = rhos[i]*mu;
			for(int j=0;j<N;j++){
				Simulator s = new Simulator(new ExponentialDistribution(lambda,r),new ExponentialDistribution(mu,r));
				s.run();
				runetas[j]=s.geEta();
			}
			meanetas[i] = Utils.mean(runetas);
			varetas[i] = Utils.cvar(runetas, meanetas[i]);
			confint[i] = Utils.confidenceInterval(N, confLevel, varetas[i]);
		}
		
		DefaultStatisticalCategoryDataset dataset = new DefaultStatisticalCategoryDataset();

		for(int i=0; i<rhos.length;i++){
			dataset.add(meanetas[i], varetas[i], "Mean eta", "rho="+rhos[i]);
		}
		
		CategoryAxis xAxis = new CategoryAxis("Type");
        xAxis.setLowerMargin(0.01d); // percentage of space before first bar
        xAxis.setUpperMargin(0.01d); // percentage of space after last bar
        xAxis.setCategoryMargin(0.05d); // percentage of space between categories
        ValueAxis yAxis = new NumberAxis("Value");

        CategoryItemRenderer renderer = new StatisticalBarRenderer();
        CategoryPlot plot = new CategoryPlot(dataset, xAxis, yAxis, renderer);

        JFreeChart chart = new JFreeChart("Eta",
                                          new Font("Helvetica", Font.BOLD, 14),
                                          plot,
                                          true);

		ChartFrame f = new ChartFrame("Chart", chart);
		f.pack();
		f.setVisible(true);
		
		/* GRAFICO K
		 * 
		 *
		int k=0;
		XYSeries series = new XYSeries("Series 1");

		LinkedList<Event> history = s.getHistory();
		for ( int i=0; i< history.size();i++){
			series.add(history.get(i).occurrenceTime,history.get(i).getClass()==Arrival.class?k++:k--);
		}
		
		XYSeriesCollection dataset = new XYSeriesCollection(); 
        dataset.addSeries(series); 

		JFreeChart chart = ChartFactory.createXYLineChart(
				"Titolo", 
				"Time", 
				"Clients", 
				dataset, 
				PlotOrientation.VERTICAL, 
				true, 
				true, 
				true);
		
		XYPlot plot = (XYPlot) chart.getPlot(); 
        XYStepRenderer renderer = new XYStepRenderer(); 
        renderer.setSeriesStroke(0,new BasicStroke(1.2f));

        renderer.setSeriesPaint(0, Color.BLACK);
        renderer.setBaseToolTipGenerator(new StandardXYToolTipGenerator()); 
        renderer.setDefaultEntityRadius(6);
        plot.setRenderer(renderer);
        
		ChartFrame f = new ChartFrame("Title", chart);
		f.pack();
		f.setVisible(true);
		*/
		
	}

}
