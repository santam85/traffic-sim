package simulator.distribution;

import simulator.random.RandomProvider;

public class UniformDistribution extends Distribution {
	
	public UniformDistribution() {
		this(new RandomProvider());
	}
	
	public UniformDistribution(RandomProvider rp) {
		super("Uniform",rp);
	}
	
	@Override
	public double nextValue() {
		return  (rp.nextRandom());
	}

	@Override
	public String toString() {
		return this.distributionName;
	}
}
