class Wallet
  attr_accessor :coin_quantity, :coin_values
  
  def initialize
    @coin_quantity = Hash.new
    @coin_values = Hash.new
  end 
  
  def add_coin(newCoin, newValue)
    if @coin_quantity.has_key?(newCoin)
      @coin_quantity[newCoin] = @coin_quantity[newCoin] + 1
    else
      @coin_quantity[newCoin] = 1
      @coin_values[newCoin] = newValue
    end 
  end
  
  def print_contents 
    @coin_quantity.each do |coin, quantity| 
      value = "(" + @coin_values[coin].to_s + ")"
      puts coin + " " + value + ": " + quantity.to_s 
    end 
  end 
  
end