package simulator.misc;

import java.util.LinkedList;

/**
 * The LogaritmicQuantizator class serves as an interval discretizator following a logaritmic function;
 * it subdivides the x-axis in intervals of different sizes (the nearer to the mean parameter, the smaller) 
 * and assignes to each one a class (an increasing number starting for zero) and a representative value
 * (the mid-one of the interval)
 * 
 * @author Andrea Zagnoli, Marco Santarelli, Michael Gattavecchia
 */
public class LogaritmicQuantizator {

	private double mean, step, a, b, multiplier;
	private LinkedList<QuantizationInterval> intervals;

	/**
	 * Basic constructor 
	 * 
	 * @param mean The value around which intervals are concentrated 
	 */
	public LogaritmicQuantizator(double mean) {
		this(mean, 0);
	}

	/**
	 * 
	 * 
	 * @param mean The value around which intervals are concentrated 
	 * @param discretizationStep The number of desired intervals between 0 and the mean value
	 */
	public LogaritmicQuantizator(double mean, double discretizationStep) {
		this(mean, discretizationStep, 10);
	}

	/**
	 * 
	 * @param mean The value around which intervals are concentrated 
	 * @param discretizationStep The number of desired intervals between 0 and the mean value
	 * @param multiplier The multiplier value which defines the upper limit of discretizations [0,multiplier*mean]
	 */
	public LogaritmicQuantizator(double mean, double discretizationStep,
			double multiplier) {
		this.mean = mean;
		this.step = mean / discretizationStep;
		this.multiplier = multiplier;
		intervals = new LinkedList<QuantizationInterval>();

		double tmp = Math.pow(10, -mean);
		this.a = mean * tmp / (1 - tmp);
		this.b = Math.log10(a);

		init();
	}

	private void init() {
		double y = step;
		double x = 0;
		for (; y < mean; y += step) {
			double tmp = mean + a - Math.pow(10, -y + b + mean);
			intervals.add(new QuantizationInterval(x, tmp, x
					+ ((tmp - x) / 2.0)));
			x = tmp;
		}
		for (; x < multiplier * mean; y += step) {
			double tmp = mean - a + Math.pow(10, y + b - mean);
			intervals.add(new QuantizationInterval(x, tmp, x
					+ ((tmp - x) / 2.0)));
			x = tmp;
		}

		/*
		 * java.util.Iterator<QuantizationInterval> it = intervals.iterator();
		 * while (it.hasNext()) { QuantizationInterval q = it.next();
		 * System.out.println("[ " + q.x1 + "," + q.x2 + " ] -> " + q.yc + "[ "
		 * + (q.x2 - q.x1) + " ]"); }
		 */
	}

	/**
	 * Returns the class to which the given value belongs 
	 * 
	 * @param value The value to discretize
	 * @return The discretization class
	 */
	public int getDiscretizationClass(double value) {
		if (value <= 0)
			return 0;
		int i = 0;
		for (; i < intervals.size(); i++) {
			if (intervals.get(i).x1 >= value)
				break;
		}
		return i - 1;
	}

	/**
	 * Associates the given value to the representative value of its belonging interval
	 * 
	 * @param value The value to discretize
	 * @return Its discretized value
	 */
	public double discretize(double value) {
		if (value < 0)
			return 0;
		int i = 0;
		for (; i < intervals.size(); i++) {
			if (intervals.get(i).x1 >= value)
				break;
		}
		return intervals.get(i - 1).yc;
	}

	/**
	 * Returns the representative value for the given class
	 * 
	 * @param c The class identifying the interval
	 * @return The representative value of the interval
	 */
	public double getDiscretizationValueByClass(int c) {
		if (c < 0 || c >= intervals.size()) {
			return -1;
		}
		return intervals.get(c).yc;
	}

	/**
	 * Returns the number of intervals 
	 * 
	 * @return
	 */
	public int getIntervalNumber() {
		return intervals.size();
	}

	private class QuantizationInterval {

		double x1;
		double x2;
		double yc;

		QuantizationInterval(double x1, double x2, double yc) {
			this.x1 = x1;
			this.x2 = x2;
			this.yc = yc;
		}

		public String toString() {
			return "[ " + x1 + "," + x2 + "] -> " + yc + " [D: " + (x2 - x1)
					+ " ]";
		}

	}

}
