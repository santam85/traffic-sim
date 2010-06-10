package gui;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Font;
import java.awt.Rectangle;
import java.awt.geom.Ellipse2D;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartFrame;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.axis.CategoryAxis;
import org.jfree.chart.axis.NumberAxis;
import org.jfree.chart.axis.ValueAxis;
import org.jfree.chart.plot.CategoryPlot;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.plot.XYPlot;
import org.jfree.chart.renderer.category.StatisticalLineAndShapeRenderer;
import org.jfree.chart.renderer.xy.DeviationRenderer;
import org.jfree.chart.renderer.xy.XYLineAndShapeRenderer;
import org.jfree.data.statistics.DefaultStatisticalCategoryDataset;
import org.jfree.data.xy.DefaultXYDataset;
import org.jfree.data.xy.YIntervalSeries;
import org.jfree.data.xy.YIntervalSeriesCollection;
import org.jfree.ui.RectangleInsets;

public class GraphUtils {
	
	public static void displayLineChart(String frameTitle, String chartTitle, String xLable, String yLabel, double[][] values) {
		DefaultXYDataset dataset = new DefaultXYDataset();
		dataset.addSeries("Fixed confidence level",values);
		
		JFreeChart chart1 = ChartFactory.createXYLineChart(chartTitle, // chart title 
				xLable, // domain axis label 
				yLabel, // range axis label 
				dataset, // data
				PlotOrientation.VERTICAL, // orientation 
				true, // include legend 
				true, // tooltips 
				false // urls
		);
		
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
		
		ChartFrame f1 = new ChartFrame(frameTitle, chart1);
		f1.pack();
		f1.setVisible(true);
	}
	
	public static void displayStatisticalBarChart(String title, double[] x, String xLabel, double[] y, String legendLabel, double[] confidence) {
		DefaultStatisticalCategoryDataset dataset = new DefaultStatisticalCategoryDataset();

		for(int i=0; i<x.length;i++){
			dataset.add(y[i], confidence[i], legendLabel, xLabel+x[i]);
		}
		
		CategoryAxis xAxis = new CategoryAxis("Type");
        xAxis.setLowerMargin(0.01d); // percentage of space before first bar
        xAxis.setUpperMargin(0.01d); // percentage of space after last bar
        xAxis.setCategoryMargin(0.05d); // percentage of space between categories
        ValueAxis yAxis = new NumberAxis("Value");

        StatisticalLineAndShapeRenderer renderer = new StatisticalLineAndShapeRenderer();
        CategoryPlot plot = new CategoryPlot(dataset, xAxis, yAxis, renderer);
        renderer.setErrorIndicatorPaint(Color.DARK_GRAY);
        renderer.setSeriesShape(0, new Ellipse2D.Double(-2,-2,4,4));
        
        JFreeChart chart = new JFreeChart(title,
                                          new Font("Helvetica", Font.BOLD, 14),
                                          plot,
                                          true);

		ChartFrame f = new ChartFrame("Chart", chart);
		f.pack();
		f.setVisible(true);
	}
	
	public static void displayDevRendererGraph(String title, String legend, String xLabel, String yLabel, double[][][] values) {
		YIntervalSeriesCollection dataset = new YIntervalSeriesCollection();
		
		for (int x = 0; x < values[0].length; x ++) {
			YIntervalSeries series1 = new YIntervalSeries(legend+x);
		
			for (int i = 0; i < values.length; i++) {
				series1.add(values[i][x][0],values[i][x][1],values[i][x][1] - values[i][x][3]/2, values[i][x][1] + values[i][x][3]/2);
			}
		
			dataset.addSeries(series1);
		}
		
	    JFreeChart chart = ChartFactory.createXYLineChart(
	                title,      // chart title
	                xLabel,                      // x axis label
	                yLabel,                      // y axis label
	                dataset,                  // data
	                PlotOrientation.VERTICAL,
	                true,                     // include legend
	                true,                     // tooltips
	                false                     // urls
	            );
	         
        // get a reference to the plot for further customisation...

	    XYPlot plot = (XYPlot) chart.getPlot();
        plot.setBackgroundPaint(Color.WHITE);
        plot.setAxisOffset(new RectangleInsets(5.0, 5.0, 5.0, 5.0));
        plot.setDomainGridlinePaint(Color.LIGHT_GRAY);
        plot.setRangeGridlinePaint(Color.LIGHT_GRAY);
        
        DeviationRenderer renderer = new DeviationRenderer(true, false);
        
        renderer.setSeriesFillPaint(0, new Color(255, 200, 200));
        renderer.setSeriesFillPaint(1, new Color(200, 200, 255));
        renderer.setSeriesFillPaint(2, new Color(200, 255, 200));
        
        // Sets black color for eta serie
        renderer.setSeriesPaint(values[0].length-1, Color.BLACK);
        
        plot.setRenderer(renderer);
                
        
        // change the auto tick unit selection to integer units only...
        NumberAxis yAxis = (NumberAxis) plot.getRangeAxis();
        yAxis.setAutoRangeIncludesZero(false);
        yAxis.setStandardTickUnits(NumberAxis.createIntegerTickUnits());
         
        ChartFrame f = new ChartFrame(title, chart);
        f.pack();
        f.setVisible(true);
	}
}
