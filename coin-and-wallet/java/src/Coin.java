
public class Coin implements Comparable<Coin>{

	private String name; 
	private String value; 
	
	public Coin(String newCoinName, String newCoinValue){
		name = newCoinName; 
		value = newCoinValue; 
	}
	
	public void setCoinName(String newCoinName){
		name = newCoinName; 
	}
	
	public void setCoinValue(String newCoinValue){
		value = newCoinValue; 
	}
	
	public String getCoinName(){
		return name;
	}
	
	public String getCoinValue(){
		return value; 
	}
	
	public int compareTo(Coin newCoin){ 
		if (name.equals(newCoin.getCoinName()) && value.equals(newCoin.getCoinValue())) return 0; 
		return -1; 
	}
	
}
