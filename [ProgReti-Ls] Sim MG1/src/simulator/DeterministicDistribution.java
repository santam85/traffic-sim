package simulator;

public class DeterministicDistribution extends Distribution {

	private float mu;
	
	public DeterministicDistribution(float mu) {
		this.mu = mu;
	}
	
	@Override
	public float nextValue() {
		return mu;
	}

}
