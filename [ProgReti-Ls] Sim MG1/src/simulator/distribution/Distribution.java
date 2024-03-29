package simulator.distribution;

import simulator.random.RandomProvider;

public abstract class Distribution {
	
	protected String distributionName;
	protected RandomProvider rp;
	
	public Distribution(String distributionName) {
		this(distributionName, new RandomProvider());
	}

	public Distribution(String distributionName, RandomProvider rp) {
		this.distributionName = distributionName;
		this.rp = rp;
	}
	
	public abstract double nextValue();
	
	public String getDistributionName() {
		return distributionName;
	}
	
	public abstract String toString();
	
	public String getProviderName(){
		return rp.toString();
	}
}
