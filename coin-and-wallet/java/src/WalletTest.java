import static org.junit.Assert.*;
import org.junit.Test;
import java.util.*;

public class WalletTest {
	
	private static Scanner userInput = new Scanner(System.in); 
	private Coin nickel = new Coin("nickel", "5 cents"); 
	private Coin Nickel = new Coin("Nickel", "5 cents"); 
	
	public static void main(String[] args) {
		Wallet testWallet = new Wallet(); 
		promptForNewCoin(testWallet); 
	}
	
	private static void promptForNewCoin(Wallet testWallet) {
		while(true) {
			String newCoinName = promptForNewCoinName(); 
			if (newCoinName.equals("")) break; 
			String newCoinValue = promptForNewCoinValue();
			addCoinToWallet(testWallet, newCoinName, newCoinValue); 
		}
	}
	
	private static String promptForNewCoinName() {
		System.out.println("Enter a new coin name: "); 
		String newName = userInput.nextLine(); 
		return newName; 
	}
	
	private static String promptForNewCoinValue() {
		System.out.println("Enter a new coin value: "); 
		String newValue = userInput.nextLine(); 
		return newValue; 
	}
	
	private static void addCoinToWallet(Wallet testWallet, String newCoinName, String newCoinValue) {
		Coin newCoin = new Coin(newCoinName, newCoinValue); 
		testWallet.add(newCoin); 
		testWallet.printContent(); 
		System.out.println(); 
	}
	
	@Test
	public void testAddingNewCoinToWallet() {
		Wallet testWallet = new Wallet(); 
		testWallet.add(nickel); 
		assertEquals("Coin addition error", true, testWallet.checkCoinInWallet(nickel)); 
	}

	@Test 
	public void testUpperAndLoweCaseCoinsInWallet() {
		Wallet testWallet = new Wallet(); 
		testWallet.add(Nickel); 
		assertEquals("Coin upper case error", true, testWallet.checkCoinInWallet(Nickel)); 
		assertEquals("Coin lower case error", false, testWallet.checkCoinInWallet(nickel)); 
	}
	
	@Test
	public void testInitialCoinCount() { 
		Wallet testWallet = new Wallet(); 
		testWallet.add(nickel); 
		testWallet.add(Nickel); 
		assertEquals("Initial coin value error", 1, testWallet.checkCoinCount(nickel)); 
		assertEquals("Initial coin value error", 1, testWallet.checkCoinCount(Nickel)); 
	}
	
	@Test 
	public void testMultipleCoinCount() {
		Wallet testWallet = new Wallet(); 
		for (int i = 0; i < 5; i++) testWallet.add(nickel); 
		for (int i = 0; i < 3; i++) testWallet.add(Nickel); 
		assertEquals("Multiple coin count error", 5, testWallet.checkCoinCount(nickel)); 
		assertEquals("Multiple coin count error", 3, testWallet.checkCoinCount(Nickel)); 
	}
	
	@Test
	public void testMultipleCoinCountWithDifferentDuplicates() { 
		Wallet testWallet = new Wallet(); 
		Coin newCoin = new Coin("nickel", "5 cents"); 
		for (int i = 0; i < 5; i++) testWallet.add(nickel); 
		testWallet.add(newCoin); 
		assertEquals("Multiple coin count with duplicates error", 6, testWallet.checkCoinCount(nickel)); 
	}
	
	@Test
	public void testWalletPrint() {
		Wallet testWallet = new Wallet();
		testWallet.add(nickel); 
		testWallet.add(Nickel);
		testWallet.printContent();
		System.out.println(); 
	}
	
	@Test
	public void testWalletPrintWithMultiple() {
		Wallet testWallet = new Wallet(); 
		for (int i = 0; i < 5; i++) testWallet.add(nickel); 
		for (int i = 0; i < 3; i++) testWallet.add(Nickel); 
		testWallet.printContent();
	}
	
}
