package simulator.distribution;

import simulator.random.RandomProvider;

public class ExponentialDistribution extends Distribution {

	private double mu;
	
	public ExponentialDistribution(double mu) {
		this(mu,new RandomProvider());
	}
	
	public ExponentialDistribution(double mu, RandomProvider rp) {
		super("Exponential",rp);
		this.mu = mu;
	}
	
	public double getMu() {
		return mu;
	}
	
	@Override
	public double nextValue() {
		return  -Math.log(rp.nextRandom()*1.0)/mu;
	}

	@Override
	public String toString() {
		return this.distributionName+" (mu="+mu+")";
	}

}
