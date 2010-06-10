package simulator.distribution;

import simulator.random.RandomProvider;

public class SPPDistribution extends Distribution {
	
	private double mu0, mu1, p0, p1;
	private boolean isState0;
	private double untilNewState;
	
	public SPPDistribution(double mu0, double mu1, double q01, double q10) {
		this(mu0,mu1,q01,q10,new RandomProvider());
	}
	
	public SPPDistribution(double mu0, double mu1, double p0, double p1, RandomProvider rp) {
		super("SPP");
		this.mu0 = mu0;
		this.mu1 = mu1;
		this.p0 = p0;
		this.p1 = p1;
		this.rp = rp;
		this.isState0 = true;
		this.untilNewState =  -Math.log(rp.nextRandom()*1.0)/p1;
	}
	
	
	@Override
	public double nextValue() {
		if (this.isState0) {
			return handleState0();
		}
		else {
			return handleState1();
		}
	}
	
	private double handleState0() {
		double untilNewOccurrence =  -Math.log(rp.nextRandom()*1.0)/mu0;
		if (untilNewState > untilNewOccurrence) {
			untilNewState = untilNewState - untilNewOccurrence;
			return untilNewOccurrence;
		}
		else {
			isState0 = false;
			double tmp = untilNewState;
			this.untilNewState =  -Math.log(rp.nextRandom()*1.0)/p0;
			return tmp + handleState1();
		}
	}
	
	private double handleState1() {
		double untilNewOccurrence =  -Math.log(rp.nextRandom()*1.0)/mu1;
		if (untilNewState > untilNewOccurrence) {
			untilNewState = untilNewState - untilNewOccurrence;
			return untilNewOccurrence;
		}
		else {
			isState0 = true;
			double tmp = untilNewState;
			this.untilNewState =  -Math.log(rp.nextRandom()*1.0)/p1;
			return tmp + handleState0();
		}
	}
	
	@Override
	public String toString() {
		return this.distributionName+" (mu0="+mu0+",mu1="+mu1+",p0="+p0+",p1="+p1+")";
	}

}
