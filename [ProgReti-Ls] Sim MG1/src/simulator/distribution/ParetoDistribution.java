package simulator.distribution;

public class ParetoDistribution extends Distribution {

	private double shape, mode;
	
	public ParetoDistribution(double shape, double mode) {
		super("Pareto");
		this.shape = shape;
		this.mode = mode;
	}
	
	@Override
	public double nextValue() {
		return Math.pow((1 - rp.nextRandom()),-1/shape)*mode;
		// r=mode*pow(1-u,-1/shape)
	}

}
