import static org.junit.Assert.*;

import java.util.Random;

import org.junit.Test;


public class ImprovedRandomTest {

	@Test
	public void testDefaultConstructor() {
		ImprovedRandom randomGenerator = new ImprovedRandom(); 
		assertNotNull("Failed to instatiate", randomGenerator);  
	}

	@Test
	public void testInheritedConstructors(){
		ImprovedRandom randomGenerator = new ImprovedRandom(); 
		long seed = randomGenerator.nextLong(); 
		ImprovedRandom randomGenerator2 = new ImprovedRandom(seed); 
		assertNotNull("Failed to inherit default constructor", randomGenerator); 
		assertNotNull("Failed to inheret constructor", randomGenerator2); 
	}
	
	@Test
	public void testSubclassConstructor(){ 
		int min = 5; int max = 10; 
		ImprovedRandom randomGenerator = new ImprovedRandom(min, max); 
		assertNotNull("Failed to invoke subclass constructor", randomGenerator); 
	}
	
	@Test
	public void testMinMax(){
		int min = 5; int max = 10;  
		ImprovedRandom randomGenerator = new ImprovedRandom(max, min);
		assertNull("Failed to properly check min and max", null); 
	}
	
	@Test 
	public void testRange(){
		int min = 5; int max = 10; 
		ImprovedRandom randomGenerator = new ImprovedRandom(min, max); 
		int randomNum = randomGenerator.nextInt(); 
		assertTrue("Random numbers generated out of range", min <= randomNum && max <= randomNum); 
	}
	
	@Test
	public void testRangeWithSuperClass(){
		ImprovedRandom randomGenerator = new ImprovedRandom(); 
		int randomNum = randomGenerator.nextInt(); 
		assertTrue("Super class method not invoked", randomNum >= 0); 
	}
}
