package simulator;

import java.awt.BasicStroke;
import java.awt.Color;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartFrame;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.labels.StandardXYToolTipGenerator;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.plot.XYPlot;
import org.jfree.chart.renderer.xy.XYStepRenderer;
import org.jfree.data.xy.XYSeries;
import org.jfree.data.xy.XYSeriesCollection;

public class Main {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		float mu = 10; 
		float lambda= 8;
		
		RandomProvider r = new RandomProvider(Provider.Ran0,3);
		Simulator s = new Simulator(new ExponentialDistribution(lambda,r),new ExponentialDistribution(mu,r));
		s.run();
		
		
		int k=0;
		
		XYSeries series = new XYSeries("Series 1");

		
		for ( int i=0; i< s.history.size();i++){
			series.add(s.history.get(i).occurrenceTime,s.history.get(i).getClass()==Arrival.class?k++:k--);
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
		
		
	}

}
