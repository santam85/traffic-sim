package simulator.distribution;

public class DeterministicDistribution extends Distribution {

	private double mu;
	
	public DeterministicDistribution(double mu) {
		super("Deterministic");
		this.mu = mu;
	}
	
	@Override
	public double nextValue() {
		return 1/mu;
	}

	@Override
	public String toString() {
		return this.distributionName + " (mu="+mu+")";
	}
	
	

}
