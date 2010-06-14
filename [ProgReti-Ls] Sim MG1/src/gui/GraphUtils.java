package gui;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Font;
import java.awt.Rectangle;
import java.awt.geom.Ellipse2D;
import java.util.LinkedList;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartFrame;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.axis.CategoryAxis;
import org.jfree.chart.axis.NumberAxis;
import org.jfree.chart.axis.ValueAxis;
import org.jfree.chart.plot.CategoryPlot;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.plot.XYPlot;
import org.jfree.chart.renderer.category.LineAndShapeRenderer;
import org.jfree.chart.renderer.category.StatisticalLineAndShapeRenderer;
import org.jfree.chart.renderer.xy.DeviationRenderer;
import org.jfree.chart.renderer.xy.XYLineAndShapeRenderer;
import org.jfree.chart.renderer.xy.XYStepRenderer;
import org.jfree.data.category.DefaultCategoryDataset;
import org.jfree.data.statistics.DefaultStatisticalCategoryDataset;
import org.jfree.data.xy.DefaultXYDataset;
import org.jfree.data.xy.XYSeries;
import org.jfree.data.xy.XYSeriesCollection;
import org.jfree.data.xy.YIntervalSeries;
import org.jfree.data.xy.YIntervalSeriesCollection;
import org.jfree.ui.RectangleInsets;

import simulator.events.Arrival;
import simulator.events.ComparableEvent;

/**
 * @author Andrea Zagnoli, Marco Santarelli, Michael Gattavecchia. 
 *
 */
public class GraphUtils {

	/**
	 * Displays a plain XY LineChart
	 * 
	 * @param frameTitle Title to display on the chart frame
	 * @param chartTitle The chart title
	 * @param xLabel The x axis label
	 * @param yLabel The y axis label
	 * @param seriesTitle The names displayed in the legend
	 * @param values The XY data points
	 */
	public static void displayLineChart(String frameTitle, String chartTitle,
			String xLabel, String yLabel, String[] seriesTitle,
			LinkedList<double[][]> values) {
		DefaultXYDataset dataset = new DefaultXYDataset();
		for (int i = 0; i < seriesTitle.length; i++)
			dataset.addSeries(seriesTitle[i], values.get(i));

		JFreeChart chart1 = ChartFactory.createXYLineChart(chartTitle, // chart
																		// title
				xLabel, // domain axis label
				yLabel, // range axis label
				dataset, // data
				PlotOrientation.VERTICAL, // orientation
				true, // include legend
				true, // tooltips
				false // urls
				);

		// Graphic layout chart1
		XYPlot cp = (XYPlot) chart1.getPlot();
		cp.setBackgroundPaint(Color.white);
		cp.setRangeGridlinePaint(Color.gray);
		XYLineAndShapeRenderer renderer = (XYLineAndShapeRenderer) cp
				.getRenderer();
		renderer.setSeriesShapesVisible(0, true);
		renderer.setDrawOutlines(true);
		renderer.setUseFillPaint(true);
		renderer.setSeriesStroke(0, new BasicStroke(2));
		renderer.setSeriesShape(0, new Rectangle(-2, -2, 4, 4));

		ChartFrame f1 = new ChartFrame(frameTitle, chart1);
		f1.pack();
		f1.setVisible(true);
	}

	/**
	 * Displays a statistical line chart with confidence/uncertainty range marks
	 * 
	 * @param frameTitle Title to display on the chart frame
	 * @param chartTitle The chart title
	 * @param xLabel The x axis label
	 * @param yLabel The y axis label
	 * @param legendLabel The names displayed in the legend
	 * @param categoryKeys The comparable category keys for the x axis
	 * @param y The y value
	 * @param confidence The uncertainty range to be displayed on the chart
	 */
	public static void displayStatisticalLineChart(String frameTitle,
			String chartTitle, String xLabel, String yLabel,
			String[] legendLabel, String[] categoryKeys,
			LinkedList<double[]> y, LinkedList<double[]> confidence) {
		DefaultStatisticalCategoryDataset dataset = new DefaultStatisticalCategoryDataset();
		for (int j = 0; j < legendLabel.length; j++) {
			for (int i = 0; i < categoryKeys.length; i++) {
				dataset.add(y.get(j)[i], confidence.get(j)[i], legendLabel[j],
						categoryKeys[i]);
			}
		}

		CategoryAxis xAxis = new CategoryAxis(xLabel);
		xAxis.setCategoryMargin(0.001);
		xAxis.setMaximumCategoryLabelLines(2);
		xAxis.setMaximumCategoryLabelWidthRatio(1);
		ValueAxis yAxis = new NumberAxis(yLabel);
		StatisticalLineAndShapeRenderer renderer = new StatisticalLineAndShapeRenderer();
		CategoryPlot plot = new CategoryPlot(dataset, xAxis, yAxis, renderer);
		renderer.setErrorIndicatorPaint(Color.DARK_GRAY);

		for (int i = 0; i < dataset.getRowCount(); i++) {
			renderer.setSeriesShape(i, new Ellipse2D.Double(-1.5, -1.5, 3, 3));
		}

		JFreeChart chart = new JFreeChart(chartTitle, new Font("Helvetica",
				Font.BOLD, 14), plot, true);

		ChartFrame f = new ChartFrame(frameTitle, chart);
		chart.setBackgroundPaint(Color.WHITE);
		f.pack();
		f.setVisible(true);
	}

	/**
	 * Displays a chart for statistical data, representing the confidence range with shaded areas, capable of charting
	 * a very high number of points and several series of indipendent data points.
	 * 
	 * @param chartTitle The chart title
	 * @param xLabel The x axis label
	 * @param yLabel The y axis label
	 * @param seriesKeys The series names to be displayed in the legend
	 * @param values A list of array matrixes containing the x values, the y values and the y ranges
	 */
	public static void displayDevRendererGraph(String chartTitle,
			String xLabel, String yLabel, String[] seriesKeys,
			LinkedList<double[][]> values) {
		YIntervalSeriesCollection dataset = new YIntervalSeriesCollection();

		for (int x = 0; x < seriesKeys.length; x++) {
			YIntervalSeries series1 = new YIntervalSeries(seriesKeys[x]);
			double[][] v = values.get(x);
			for (int i = 0; i < v.length; i++) {
				series1.add(v[i][0], v[i][1], v[i][1] - v[i][3] / 2, v[i][1]
						+ v[i][3] / 2);
			}

			dataset.addSeries(series1);
		}

		JFreeChart chart = ChartFactory.createXYLineChart(chartTitle, // chart
																		// title
				xLabel, // x axis label
				yLabel, // y axis label
				dataset, // data
				PlotOrientation.VERTICAL, true, // include legend
				true, // tooltips
				false // urls
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
		renderer.setSeriesPaint(seriesKeys.length - 1, Color.BLACK);

		plot.setRenderer(renderer);

		// change the auto tick unit selection to integer units only...
		NumberAxis yAxis = (NumberAxis) plot.getRangeAxis();
		yAxis.setAutoRangeIncludesZero(false);
		yAxis.setStandardTickUnits(NumberAxis.createIntegerTickUnits());

		ChartFrame f = new ChartFrame(chartTitle, chart);
		f.pack();
		f.setVisible(true);
	}

	/**
	 * Displays a chart representing the evolution of K(t)
	 * 
	 * @param frameTitle The frame title
	 * @param chartTitle The chart title
	 * @param xLabel The x axis label
	 * @param yLabel The y axis label
	 * @param legendKey The series names to be displayed in the legend
	 * @param history The simulation history
	 */
	public static void displayStateStepChart(String frameTitle,
			String chartTitle, String xAxisLabel, String yAxisLabel,
			String legendKey, LinkedList<ComparableEvent> history) {
		int k = 0;
		XYSeries series = new XYSeries("Series 1");

		for (int i = 0; i < history.size(); i++) {
			series
					.add(
							history.get(i).getEvent().getOccurrenceTime(),
							(history.get(i).getEvent().getClass() == Arrival.class) ? k++
									: k--);
		}

		XYSeriesCollection dataset = new XYSeriesCollection();
		dataset.addSeries(series);

		JFreeChart chart = ChartFactory.createXYLineChart(chartTitle,
				xAxisLabel, yAxisLabel, dataset, PlotOrientation.VERTICAL,
				true, true, true);

		XYPlot plot = (XYPlot) chart.getPlot();
		XYStepRenderer renderer = new XYStepRenderer();
		renderer.setSeriesStroke(0, new BasicStroke(1.2f));

		renderer.setSeriesPaint(0, Color.BLACK);
		// renderer.setBaseToolTipGenerator(new StandardXYToolTipGenerator());
		// renderer.setDefaultEntityRadius(6);
		plot.setRenderer(renderer);

		ChartFrame f = new ChartFrame(frameTitle, chart);
		f.pack();
		f.setVisible(true);
	}

	/**
	 * Displays a scatter plot representing the dispersion of pseudo-random numbers
	 * 
	 * @param frameTitle The frame title
	 * @param chartTitle The chart title
	 * @param xLabel The x axis label
	 * @param yLabel The y axis label
	 * @param seriesKeys The series names to be displayed in the legend
	 * @param values A list of array matrixes containing the x and the the y values
	 */
	public static void displayScatterPlot(String frameTitle, String chartTitle,
			String xLabel, String yLabel, String[] seriesKeys,
			LinkedList<double[][]> values) {

		DefaultXYDataset dataset = new DefaultXYDataset();
		for (int i = 0; i < seriesKeys.length; i++)
			dataset.addSeries(seriesKeys[i], values.get(i));

		JFreeChart chart = ChartFactory.createScatterPlot(chartTitle, xLabel,
				yLabel, dataset, PlotOrientation.VERTICAL, false, false, false);

		XYPlot p = chart.getXYPlot();
		p.setBackgroundPaint(Color.white);
		p.setDomainCrosshairVisible(true);
		p.setRangeGridlinePaint(Color.gray);
		p.setDomainGridlinePaint(Color.gray);

		ChartFrame f = new ChartFrame(frameTitle, chart);
		f.pack();
		f.setVisible(true);
	}

	/**
	 * Display a LineChart in which the datapoint series are divided in categories.
	 * 
	 * @param frameTitle The frame title
	 * @param chartTitle The chart title
	 * @param xLabel The x axis label
	 * @param yLabel The y axis label
	 * @param seriesKeys The series names to be displayed in the legend
	 * @param values A list of array matrixes containing the x and the y values
	 */
	public static void displayCategoryLineChart(String frameTitle,
			String chartTitle, String xLabel, String yLabel,
			String[] seriesTitle, LinkedList<double[][]> values) {
		DefaultCategoryDataset dataset = new DefaultCategoryDataset();
		for (int i = 0; i < seriesTitle.length; i++) {
			double[][] v = values.get(i);
			for (int j = 0; j < v[0].length; j++)
				dataset.addValue(v[1][j], seriesTitle[i], "" + v[0][j]);
		}

		JFreeChart chart1 = ChartFactory.createLineChart(chartTitle, // chart
																		// title
				xLabel, // domain axis label
				yLabel, // range axis label
				dataset, // data
				PlotOrientation.VERTICAL, // orientation
				true, // include legend
				true, // tooltips
				false // urls
				);

		// Graphic layout chart1
		CategoryPlot cp = (CategoryPlot) chart1.getPlot();
		cp.setBackgroundPaint(Color.white);
		cp.setRangeGridlinePaint(Color.gray);
		LineAndShapeRenderer renderer = (LineAndShapeRenderer) cp.getRenderer();
		renderer.setSeriesShapesVisible(0, true);
		renderer.setDrawOutlines(true);
		renderer.setUseFillPaint(true);
		renderer.setSeriesStroke(0, new BasicStroke(2));
		renderer.setSeriesShape(0, new Rectangle(-2, -2, 4, 4));

		ChartFrame f1 = new ChartFrame(frameTitle, chart1);
		f1.pack();
		f1.setVisible(true);

	}
}
