package simulator;

public class ExponentialDistribution extends Distribution {

	private float mu;
	
	public ExponentialDistribution(float mu) {
		this(mu,new RandomProvider());
	}
	
	public ExponentialDistribution(float mu, RandomProvider rp) {
		super("Exponential",rp);
		this.mu = mu;
	}
	
	@Override
	public float nextValue() {
		return (float) -Math.log(rp.nextRandom()*1.0)/mu;
	}

}
