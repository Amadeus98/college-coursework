import static org.junit.Assert.*;

import org.junit.Test;


public class ImprovedStringTokenizerTest {

	String testString = "This is a test"; 
	String[] testArray = {"This", "is", "a", "test"}; 
	
	@Test
	public void testSimpleInheritance() {
		ImprovedStringTokenizer testTokenizer = new ImprovedStringTokenizer(testString); 
		assertNotNull("Fails to inherit simple constructor", testTokenizer); 
	}
	
	@Test
	public void testTokenCount() { 
		ImprovedStringTokenizer testTokenizer = new ImprovedStringTokenizer(testString); 
		assertEquals("Fails to return proper token count", testTokenizer.countTokens(), 4); 
	}
	
	@Test
	public void testGetWordArray() { 
		ImprovedStringTokenizer testTokenizer = new ImprovedStringTokenizer(testString); 
		assertArrayEquals("Fails to return proper tokenized array", testTokenizer.getWordArray(), testArray); 
	}

}
