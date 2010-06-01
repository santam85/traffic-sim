package simulator;

import java.util.Random;

public class Rnd {
	private long a = 16807,
				 c = 0,
				 m = 2147483647,
				 z = -1;
	
	private RandomnessProvider p = RandomnessProvider.Java;
	private Random rd;
	
	
	public Rnd(){
		this.p = RandomnessProvider.Ran0;
	}
	
	public Rnd(RandomnessProvider p){
		this.p = p;
	}
	
	public Rnd(RandomnessProvider p,long seed){
		this.p=p;
		this.z=seed;
	}
	
	public void setRandomnessProvider(RandomnessProvider p){
		this.p=p;
	}
	
	private double rnd(){
		switch(p){
		case Ran0: 
			if (z == -1)
				z=System.currentTimeMillis()%m;
			return rnd(z);
		default: 
			if(rd==null)
				rd=new Random();
			return rd.nextDouble();
		}
		
	}
	
	private double rnd(long seed){
		
		if(seed==0)
			seed = 1;
		
		switch(p){
		case Ran0: 
			z = (a * seed + c) % m;
			return (z*1.0)/m;
		default: 
			if(rd==null)
				rd=new Random(seed);
			return rd.nextDouble();
		}
	}
	
	public float nextRandom(Distribution d){
		switch(d.type){
			case Uniform: return (float) (rnd()*1.0);
			case Exponential: return (float) -Math.log(rnd()*1.0)/d.mu;		
		}
		return 0;
	}
}