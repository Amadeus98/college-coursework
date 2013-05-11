import java.util.Random;


public class ImprovedRandom extends Random{

	private Random generator; 
	private int min = -1; 
	private int max = -1;  
	
	public ImprovedRandom(){ 
		super(); 
	}
	
	public ImprovedRandom(long seed){
		super(seed); 
	}
	
	public ImprovedRandom(int newMin, int newMax){
		 if(checkMinMax(newMin, newMax)){
			 min = newMin; 
			 max = newMax; 
			 generator = new Random();
		 }
		 else System.out.println("Error: range must be greater than 1");
	}
	
	private boolean checkMinMax(int newMin, int newMax){
		if (newMin == newMax) return false; 
		if (newMin < newMax) return true; 
		else return false; 
	}
	
	public int nextInt(){
		if (min == -1 || max == -1) return super.nextInt(); 
		else return generator.nextInt(max - min + 1) + min; 
	}
}
