package simulator;

public class DeterministicDistribution extends Distribution {

	private float mu;
	
	public DeterministicDistribution(float mu) {
		super("Deterministic");
		this.mu = mu;
	}
	
	@Override
	public float nextValue() {
		return 1/mu;
	}

}
