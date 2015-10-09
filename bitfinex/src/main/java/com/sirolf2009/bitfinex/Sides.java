package com.sirolf2009.bitfinex;

public enum Sides {
	
	BUY, SELL;
	
	@Override
	public String toString() {
		return super.toString().toLowerCase();
	}

}
