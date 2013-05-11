import java.util.*;


public class Wallet {
	
	private Map<Coin, Integer> coinsInWallet; 
	
	public Wallet(){ 
		coinsInWallet = new TreeMap<Coin, Integer>();
	}
	
	public void add(Coin newCoin) {
		checkCoinPresence(newCoin);
	}
	
	public void printContent() {
		for (Map.Entry<Coin, Integer> e : coinsInWallet.entrySet()) {
			printCoinInformation(e.getKey()); 
			System.out.println(e.getValue()); 
		}
	}
	
	public boolean checkCoinInWallet(Coin coinToCheck) { 
		return coinsInWallet.containsKey(coinToCheck); 
	}
	
	public int checkCoinCount(Coin coinToCheck) {
		return coinsInWallet.get(coinToCheck);
	}
	
	private void checkCoinPresence(Coin newCoin) {
		boolean coinIsPresent = coinsInWallet.containsKey(newCoin); 
		if (coinIsPresent) incrementCoinCount(newCoin); 
		else addCoinToWallet(newCoin); 
	}
	
	private void incrementCoinCount(Coin newCoin) {
		int currentCoinCount = coinsInWallet.get(newCoin).intValue();
		Integer incrementedCoinCount = new Integer(currentCoinCount + 1); 
		coinsInWallet.put(newCoin, incrementedCoinCount); 
	}
	
	private void addCoinToWallet(Coin newCoin) {
		Integer newCoinCount = new Integer(1); 
		coinsInWallet.put(newCoin, newCoinCount); 
	}
	
	private void printCoinInformation(Coin currentCoin) {
		System.out.print(currentCoin.getCoinName() + " "); 
		System.out.print("(" + currentCoin.getCoinValue() + "): "); 
	}
	
}
