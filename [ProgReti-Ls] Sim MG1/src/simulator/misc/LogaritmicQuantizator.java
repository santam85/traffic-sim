package simulator.misc;

import java.util.LinkedList;

public class LogaritmicQuantizator {

	private double mean, step, a, b;
	private LinkedList<QuantizationInterval> intervals;
	
	public LogaritmicQuantizator(double mean) {
		this(mean,0.01);
	}
	
	public LogaritmicQuantizator(double mean, double step) {
		this.mean = mean;
		this.step = step;
		intervals = new LinkedList<QuantizationInterval>();
		
		double tmp = Math.pow(10,- mean);
		this.a = mean*tmp/(1 - tmp);
		this.b = Math.log10(a);
		
		init();
	}
	
	private void init() {
		double y = step/2.0;
		double x = 0;
		for (; y < mean; y += step) {
			double tmp = mean + a - Math.pow(10,- y + b + mean);
			intervals.add(new QuantizationInterval(x,tmp,y - step/2.0));
			x = tmp;
		}
		for (; y < 2*mean; y += step) {
			double tmp = mean - a + Math.pow(10,y + b - mean);
			intervals.add(new QuantizationInterval(x,tmp,y - step/2.0));
			x = tmp;
		}
		
		java.util.Iterator<QuantizationInterval> it = intervals.iterator();
		while (it.hasNext()) {
			System.out.println(it.next());
		}
	}
	
	public int getDiscretizationClass(double value) {
		if (value < 0)
			return 0;
		int i = 0;
		for (; i < intervals.size(); i ++) {
			if (intervals.get(i).x1 >= value)
				break;
		}
		return i - 1;
	}
	
	public double discretize(double value) {
		if (value < 0)
			return 0;
		int i = 0;
		for (; i < intervals.size(); i ++) {
			if (intervals.get(i).x1 >= value)
				break;
		}
		return intervals.get(i - 1).yc;
	}
	
	public int getIntervalNumber() {
		return intervals.size();
	}
	
	private class QuantizationInterval {

		double x1;
		double x2;
		double yc;
		
		QuantizationInterval(double x1, double x2, double yc) {
			this.x1 = x1; this.x2 = x2; this.yc = yc;
		}
		
		public String toString() {
			return "[ " + x1 + "," + x2 + "] -> " + yc;
		}
		
	}

}
