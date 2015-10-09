package com.sirolf2009.bitfinex;

public enum Symbols {

	BTCUSD, LTCUSD, LTCBTC;
	
	@Override
	public String toString() {
		return super.toString().toLowerCase();
	}
	
}
