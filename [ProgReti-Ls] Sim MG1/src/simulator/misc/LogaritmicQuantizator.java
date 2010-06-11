package simulator.misc;

import java.util.LinkedList;

public class LogaritmicQuantizator {

	private double mean, step, a, b, multiplier;
	private LinkedList<QuantizationInterval> intervals;
	
	public LogaritmicQuantizator(double mean) {
		this(mean,0);
	}
	
	public LogaritmicQuantizator(double mean, double discretizationStep) {
		this(mean,discretizationStep,10);
	}
	
	public LogaritmicQuantizator(double mean, double discretizationStep, double multiplier) {
		this.mean = mean;
		this.step = mean/discretizationStep;
		this.multiplier = multiplier;
		intervals = new LinkedList<QuantizationInterval>();
		
		double tmp = Math.pow(10,- mean);
		this.a = mean*tmp/(1 - tmp);
		this.b = Math.log10(a);
		
		init();
	}
	
	private void init() {
		double y = step;
		double x = 0;
		for (; y < mean; y += step) {
			double tmp = mean + a - Math.pow(10,- y + b + mean);
			intervals.add(new QuantizationInterval(x,tmp,x + ((tmp - x)/2.0)));
			x = tmp;
		}
		for (; x < multiplier*mean; y += step) {
			double tmp = mean - a + Math.pow(10,y + b - mean);
			intervals.add(new QuantizationInterval(x,tmp,x + ((tmp - x)/2.0)));
			x = tmp;
		}
		
		/*java.util.Iterator<QuantizationInterval> it = intervals.iterator();
		while (it.hasNext()) {
			QuantizationInterval q = it.next();
			System.out.println("[ " + q.x1 + "," + q.x2 + " ] -> " + q.yc + "[ " + (q.x2 - q.x1) + " ]");
		}*/
	}
	
	public int getDiscretizationClass(double value) {
		if (value <= 0)
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
	
	public double getDiscretizationValueByClass(int c) {
		if (c < 0 || c >= intervals.size()) {
			return -1;
		}
		return intervals.get(c).yc;
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
			return "[ " + x1 + "," + x2 + "] -> " + yc + " [D: " + (x2 - x1) + " ]";
		}
		
	}

}
