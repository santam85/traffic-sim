package simulator;

import java.util.Random;

public class RandomProvider {
	private long a = 16807,
				 c = 0,
				 m = 2147483647,
				 z = -1;
	
	private Provider p = Provider.Java;
	private Random rd;
	
	
	public RandomProvider(){
		this.p = Provider.Ran0;
	}
	
	public RandomProvider(Provider p){
		this.p = p;
	}
	
	public RandomProvider(Provider p,long seed){
		this.p=p;
		this.z=seed;
	}
	
	public void setRandomnessProvider(Provider p){
		this.p=p;
	}
	
	public double nextRandom(){
		switch(p){
		case Ran0: 
			if (z == -1)
				z=System.currentTimeMillis()%m;
			return nextRandom(z);
		default: 
			if(rd==null)
				rd=new Random();
			return rd.nextDouble();
		}
		
	}
	
	public double nextRandom(long seed){
		
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
}