
public class ImprovedRandomTester {

	public static void main(String[] args) { 
		randomStringOfNumbers(); 
		randomStringOfNumbersWithinRange(); 
	}
	
	public static void randomStringOfNumbers(){
		ImprovedRandom randomGenerator = new ImprovedRandom(); 
		System.out.println("Random string of numbers:"); 
		for (int i = 0; i < 10; i++) System.out.print(randomGenerator.nextInt() + " ");
	}

	public static void randomStringOfNumbersWithinRange(){
		int min = 0; int max = 10; 
		ImprovedRandom randomGenerator = new ImprovedRandom(min, max); 
		System.out.println("\n\nRandom string of numbers between " + min + " and " + max +":"); 
		for (int i = 0; i < 10; i++) System.out.print(randomGenerator.nextInt() + " ");
	}
	
}
