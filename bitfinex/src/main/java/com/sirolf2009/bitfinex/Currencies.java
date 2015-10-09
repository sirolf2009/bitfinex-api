package com.sirolf2009.bitfinex;

public enum Currencies {
	
	BTC,USD,LTC;
	
	@Override
	public String toString() {
		return super.toString().toLowerCase();
	}
	
}
