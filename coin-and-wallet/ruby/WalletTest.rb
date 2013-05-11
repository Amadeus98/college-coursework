require "./Wallet"
require "test/unit"

class TestWallet < Test::Unit::TestCase
  
  def setup
    @testWallet = Wallet.new
    @testCoin = "Quarter"
    @testValue = "25 cents"
    @nickelName = "nickel"
    @nickelValue = "5 cents"
  end
  
  def test_initializer
    assert_instance_of(Hash, @testWallet.coin_quantity, "Failed to initialize coin_quantity")
    assert(@testWallet.coin_quantity.empty?, "coin_quantity not empty")
    assert_instance_of(Hash, @testWallet.coin_values, "Failed to initialize coin_values")
    assert(@testWallet.coin_values.empty?, "coin_values not empty")
  end
  
  def test_add_coin
    @testWallet.add_coin(@testCoin, @testValue)
    assert(@testWallet.coin_quantity.has_key?(@testCoin), "Coin not added to wallet (coin_quantity)")
    assert_equal(1, @testWallet.coin_quantity[@testCoin], "Coin quantity not properly set")
    assert(@testWallet.coin_values.has_key?(@testCoin), "Coin not added to wallet (coin_values)")
    assert_equal(@testValue, @testWallet.coin_values[@testCoin], "Coin value not properly set")
  end
  
  def test_add_coin_duplicates
    @testWallet.add_coin(@testCoin, @testValue)
    @testWallet.add_coin(@testCoin, @testValue)
    assert_equal(2, @testWallet.coin_quantity[@testCoin], "Coin quantity not properly incrementing")
    assert_equal(@testValue, @testWallet.coin_values[@testCoin], "Coin value not properly set")
  end
  
  def test_add_coin_multiple
    @testWallet.add_coin(@testCoin, @testValue)
    @testWallet.add_coin(@nickelName, @nickelValue)
    assert(@testWallet.coin_quantity.has_key?(@testCoin), "Coin not added to wallet (coin_quantity)")
    assert_equal(1, @testWallet.coin_quantity[@testCoin], "Coin quantity not properly set")
    assert(@testWallet.coin_quantity.has_key?(@nickelName), "Coin not added to wallet (coin_quantity)")
    assert_equal(1, @testWallet.coin_quantity[@nickelName], "Coin quantity not properly set")
  end
  
end