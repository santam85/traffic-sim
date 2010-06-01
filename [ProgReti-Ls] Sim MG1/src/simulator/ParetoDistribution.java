package simulator;

public class ParetoDistribution extends Distribution {

	private float shape, mode;
	
	public ParetoDistribution(float shape, float mode) {
		this.shape = shape;
		this.mode = mode;
	}
	
	@Override
	public float nextValue() {
		return (float)Math.pow((1 - rp.nextRandom()),-1/shape)*mode;
		// r=mode*pow(1-u,-1/shape)
	}

}
