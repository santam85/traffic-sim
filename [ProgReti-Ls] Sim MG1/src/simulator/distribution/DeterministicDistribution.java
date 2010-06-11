package simulator.distribution;

public class DeterministicDistribution extends Distribution {

	private double mu;
	private double val;
	
	public DeterministicDistribution(double mu) {
		super("Deterministic");
		this.mu = mu;
		this.val = 1.0/mu;
	}
	
	@Override
	public double nextValue() {
		return val;
	}

	@Override
	public String toString() {
		return this.distributionName + " (mu="+mu+")";
	}
	
	

}
