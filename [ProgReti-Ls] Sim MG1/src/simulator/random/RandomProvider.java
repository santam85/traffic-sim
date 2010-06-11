package simulator.random;

import java.security.SecureRandom;
import java.util.Random;

import org.apache.commons.math.random.MersenneTwister;

public class RandomProvider {
	private long a = 16807,
				 c = 0,
				 m = 2147483647,
				 z = -1;
	
	private Provider p = Provider.Java;
	private Random rd;
	private MersenneTwister mt;
	private SecureRandom sr;
	
	
	public RandomProvider(){
		this(Provider.Java);
	}
	
	public RandomProvider(Provider p){
		this.p = p;
		sr=new SecureRandom();
		mt=new MersenneTwister();
		rd=new Random();
	}
	
	public RandomProvider(Provider p,long seed){
		this.p=p;
		this.z=seed;
		sr=new SecureRandom();
		mt=new MersenneTwister(seed);
		rd=new Random(seed);
	}
	
	public void setRandomnessProvider(Provider p){
		this.p=p;
	}
	
	public double nextRandom(){
		switch(p){
		case Ran0: 
			if (z == -1)
				z=System.currentTimeMillis()%m;
			return nextRan0Random(z);
		case SecureRandom:	
			return sr.nextDouble();
		case MersenneTwister:
			return mt.nextDouble();
		default: 
			return rd.nextDouble();
		}
		
	}
	
	private double nextRan0Random(long seed){
			z = (a * seed + c) % m;
			return (z*1.0)/m;
	}
	
	@Override
	public String toString(){
		return p.toString();
		
	}
}