/*
	Created by Emmanuel Marcha
	CS445: Homework 2 Part 4
	Spring 2013
*/ 

var coins = [], wallet = []; 

function addCoin() { 
	var coinName = document.coinForm.coinName.value; 
	var coinValue = document.coinForm.coinValue.value; 
	
	if (coins[coinName] === undefined) {
		coins[coinName] = coinValue; 
		wallet[coinName] = 1; 
	} 
	else wallet[coinName]++;  
	
	document.getElementById("wallet").innerHTML = ""; 
	
	for (var coin in wallet) { 
		var temp = coin + " (" + coins[coin] + "): " + wallet[coin]; 
		document.getElementById("wallet").innerHTML += '<p>' + temp + '</p>';
	}
	
	document.getElementById("coinForm").reset(); 
};