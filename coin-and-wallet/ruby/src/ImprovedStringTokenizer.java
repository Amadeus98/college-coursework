import java.util.StringTokenizer;


public class ImprovedStringTokenizer extends StringTokenizer {

	public ImprovedStringTokenizer(String str) {
		super(str); 
	}
	
	public ImprovedStringTokenizer(String str, String delim) { 
		super(str, delim); 
	}
	
	public ImprovedStringTokenizer(String str, String delim, boolean returnDelims) {
		super(str, delim, returnDelims); 
	}
	
	public String[] getWordArray() { 
		int numTokens = super.countTokens(); 
		String[] result = new String[numTokens]; 
		for (int i = 0; i < numTokens; i++) result[i] = super.nextToken(); 
		return result; 
	}
	
}
