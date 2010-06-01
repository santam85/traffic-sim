package simulator;

public abstract class Distribution {
	
	protected RandomProvider rp;
	
	public Distribution() {
		this(new RandomProvider());
	}

	public Distribution(RandomProvider rp) {
		this.rp = rp;
	}
	
	public abstract float nextValue();
}
