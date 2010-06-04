package gui;

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

public class GraphUtils {
	
	public static void displayStatisticalBarChart(String title, float[] x, String xLabel, double[] y, String legendLabel, double[] confidence) {
		DefaultStatisticalCategoryDataset dataset = new DefaultStatisticalCategoryDataset();

		for(int i=0; i<x.length;i++){
			dataset.add(y[i], confidence[i], legendLabel, xLabel+x[i]);
		}
		
		CategoryAxis xAxis = new CategoryAxis("Type");
        xAxis.setLowerMargin(0.01d); // percentage of space before first bar
        xAxis.setUpperMargin(0.01d); // percentage of space after last bar
        xAxis.setCategoryMargin(0.05d); // percentage of space between categories
        ValueAxis yAxis = new NumberAxis("Value");

        CategoryItemRenderer renderer = new StatisticalBarRenderer();
        CategoryPlot plot = new CategoryPlot(dataset, xAxis, yAxis, renderer);

        JFreeChart chart = new JFreeChart(title,
                                          new Font("Helvetica", Font.BOLD, 14),
                                          plot,
                                          true);

		ChartFrame f = new ChartFrame("Chart", chart);
		f.pack();
		f.setVisible(true);
	}
}
