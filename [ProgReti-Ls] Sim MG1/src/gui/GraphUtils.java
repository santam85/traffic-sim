package gui;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Font;

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
import org.jfree.data.statistics.DefaultStatisticalCategoryDataset;
import org.jfree.data.xy.YIntervalSeries;
import org.jfree.data.xy.YIntervalSeriesCollection;
import org.jfree.ui.RectangleInsets;

public class GraphUtils {
	
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
	    chart.setBackgroundPaint(Color.white);
         
        // get a reference to the plot for further customisation...
        XYPlot plot = (XYPlot) chart.getPlot();
        plot.setBackgroundPaint(Color.lightGray);
        plot.setAxisOffset(new RectangleInsets(5.0, 5.0, 5.0, 5.0));
        plot.setDomainGridlinePaint(Color.white);
        plot.setRangeGridlinePaint(Color.white);
        
        DeviationRenderer renderer = new DeviationRenderer(true, false);
        renderer.setSeriesStroke(0, new BasicStroke(3.0f, BasicStroke.CAP_ROUND,
                 BasicStroke.JOIN_ROUND));
        renderer.setSeriesStroke(0, new BasicStroke(3.0f));
        renderer.setSeriesStroke(1, new BasicStroke(3.0f));
        renderer.setSeriesFillPaint(0, new Color(200, 200, 255));
        renderer.setSeriesFillPaint(1, new Color(255, 200, 200));
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
