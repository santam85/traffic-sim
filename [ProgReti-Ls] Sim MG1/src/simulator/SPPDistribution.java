package simulator;

public class SPPDistribution extends Distribution {
	
	private float mu0, mu1, q01, q10;
	private boolean isState0;
	private float untilNewState;
	
	public SPPDistribution(float mu0, float mu1, float q01, float q10) {
		this(mu0,mu1,q01,q10,new RandomProvider());
	}
	
	public SPPDistribution(float mu0, float mu1, float q01, float q10, RandomProvider rp) {
		this.mu0 = mu0;
		this.mu1 = mu1;
		this.q01 = q01;
		this.q10 = q10;
		this.rp = rp;
		this.isState0 = true;
		this.untilNewState = (float) -Math.log(rp.nextRandom()*1.0)/q01;
	}
	
	
	@Override
	public float nextValue() {
		if (this.isState0) {
			return handleState0();
		}
		else {
			return handleState1();
		}
	}
	
	private float handleState0() {
		float untilNewOccurrence = (float) -Math.log(rp.nextRandom()*1.0)/mu0;
		if (untilNewState > untilNewOccurrence) {
			untilNewState = untilNewState - untilNewOccurrence;
			return untilNewOccurrence;
		}
		else {
			isState0 = false;
			float tmp = untilNewState;
			this.untilNewState = (float) -Math.log(rp.nextRandom()*1.0)/q01;
			return tmp + handleState1();
		}
	}
	
	private float handleState1() {
		float untilNewOccurrence = (float) -Math.log(rp.nextRandom()*1.0)/mu1;
		if (untilNewState > untilNewOccurrence) {
			untilNewState = untilNewState - untilNewOccurrence;
			return untilNewOccurrence;
		}
		else {
			isState0 = true;
			float tmp = untilNewState;
			this.untilNewState = (float) -Math.log(rp.nextRandom()*1.0)/q10;
			return tmp + handleState0();
		}
	}

}
