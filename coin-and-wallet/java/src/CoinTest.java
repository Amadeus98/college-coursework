import static org.junit.Assert.*;
import org.junit.Test;


public class CoinTest {

	@Test
	public void testNewCoinName() {
		Coin testCoinNameLowerCase = new Coin("dime", "10 cents"); 
		Coin testCoinNameUpperCase = new Coin("Dime", "10 cents"); 
		assertEquals("Lower case coin name error", "dime", testCoinNameLowerCase.getCoinName()); 
		assertEquals("Upper case coin name error", "Dime", testCoinNameUpperCase.getCoinName()); 
	}
	
	@Test
	public void testNewCoinValue() { 
		Coin testCoinNickel = new Coin("nickel", "5 cents");
		Coin testCoinQuarter = new Coin("quarter", "25 cents"); 
		assertEquals("Nickel coin value error", "5 cents", testCoinNickel.getCoinValue()); 
		assertEquals("Quater coin value error", "25 cents", testCoinQuarter.getCoinValue()); 
	}
	
	@Test
	public void testCompareToFunction() { 
		Coin testCoinNickel = new Coin("nickel", "5 cents"); 
		Coin testCoinNameLowerCase = new Coin("nickel", "5 cents"); 
		Coin testCoinNameUpperCase = new Coin("Nickel", "5 cents"); 
		assertEquals("Compare to lower case failure", 0, testCoinNickel.compareTo(testCoinNameLowerCase)); 
		assertEquals("Compare to upper case failure", -1, testCoinNickel.compareTo(testCoinNameUpperCase)); 
	}
	
}
