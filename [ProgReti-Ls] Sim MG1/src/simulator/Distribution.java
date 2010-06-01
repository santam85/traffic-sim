package simulator;

public class Distribution {
	public float mu;
	public DistributionType type;
	
	public Distribution(DistributionType type, float mu) {
		this.mu = mu;
		this.type = type;
	}

}
