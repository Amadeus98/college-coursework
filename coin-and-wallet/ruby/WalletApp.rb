require "./Wallet"

wallet = Wallet.new

loop do 
  
  puts "Enter a new coin name:"
  newCoin = gets.chomp

  if newCoin.empty? 
    break 
  else
    puts "Enter its value:"
    newValue = gets.chomp
    wallet.add_coin(newCoin, newValue)
    wallet.print_contents
  end 
  
end