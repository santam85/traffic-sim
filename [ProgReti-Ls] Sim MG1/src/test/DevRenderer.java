package test;

import java.awt.BasicStroke;
import java.awt.Color;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartFrame;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.axis.NumberAxis;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.plot.XYPlot;
import org.jfree.chart.renderer.xy.DeviationRenderer;
import org.jfree.data.xy.YIntervalSeries;
import org.jfree.data.xy.YIntervalSeriesCollection;
import org.jfree.ui.RectangleInsets;

public class DevRenderer {

	public static void main(String[] args){
		YIntervalSeries series1 = new YIntervalSeries("Series 1");
        YIntervalSeries series2 = new YIntervalSeries("Series 2");

        double y1 = 100.0;
        double y2 = 100.0;
        for (int i = 0; i <= 100; i++) {
            y1 = y1 + Math.random() - 0.48;
            double dev1 = (0.05 * i);
            series1.add(i, y1, y1 - dev1, y1 + dev1);
            
            y2 = y2 + Math.random() - 0.50;
            double dev2 = (0.07 * i);
            series2.add(i, y2, y2 - dev2, y2 + dev2);
        }


        YIntervalSeriesCollection dataset = new YIntervalSeriesCollection();
        dataset.addSeries(series1);
        dataset.addSeries(series2);
        
        JFreeChart chart = ChartFactory.createXYLineChart(
                "DeviationRenderer - Demo 1",      // chart title
                "X",                      // x axis label
                "Y",                      // y axis label
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
            
            ChartFrame f = new ChartFrame("title", chart);
            f.pack();
            f.show();
	}
}
