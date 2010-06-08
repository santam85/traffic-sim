package simulator;

public class UniformDistribution extends Distribution {
	
	public UniformDistribution() {
		this(new RandomProvider());
	}
	
	public UniformDistribution(RandomProvider rp) {
		super("Uniform",rp);
	}
	
	@Override
	public double nextValue() {
		return (double) (rp.nextRandom()*1.0);
	}

}
