package simulator;

public class UniformDistribution extends Distribution {
	
	public UniformDistribution() {
		super(new RandomProvider());
	}
	
	public UniformDistribution(RandomProvider rp) {
		super(rp);
	}
	
	@Override
	public float nextValue() {
		return (float) (rp.nextRandom()*1.0);
	}

}
