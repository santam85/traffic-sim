package simulator.random;

import java.security.SecureRandom;
import java.util.Random;

import org.apache.commons.math.random.MersenneTwister;

/**
 * The source for the pseudo-random numbers.
 * 
 * @author Andrea Zagnoli, Marco Santarelli, Michael Gattavecchia. 
 *
 */
public class RandomProvider {
	private long a = 16807,
				 c = 0,
				 m = 2147483647,
				 z = -1;
	
	private Provider p = Provider.Java;
	private Random rd;
	private MersenneTwister mt;
	private SecureRandom sr;
	
	
	/**
	 * Default costructor. Uses Java.Random provider by default.
	 */
	public RandomProvider(){
		this(Provider.Java);
	}
	
	/**
	 * Costructs a random provided with the specified source for randomness.
	 * 
	 * @param p
	 */
	public RandomProvider(Provider p){
		this.p = p;
		sr=new SecureRandom();
		mt=new MersenneTwister();
		rd=new Random();
	}
	
	/**
	 * Costructs a random provided with the specified source for randomness and seed.
	 * 
	 * @param p The 
	 * @param seed
	 */
	public RandomProvider(Provider p,long seed){
		this.p=p;
		this.z=seed;
		sr=new SecureRandom();
		mt=new MersenneTwister(seed);
		rd=new Random(seed);
	}
	
	/**
	 * Setter for the randomness provider type
	 * 
	 * @param p The provider type
	 */
	public void setRandomnessProvider(Provider p){
		this.p=p;
	}
	
	/**
	 * Method for pseudo-random number generation
	 * 
	 * @return A random number between 0 and 1 (excluded)
	 */
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