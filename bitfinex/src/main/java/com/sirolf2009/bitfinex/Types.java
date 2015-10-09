package com.sirolf2009.bitfinex;

public enum Types {

	MARKET("market"), LIMIT("limit"), STOP("stop"), 
	TRAILING_STOP("trailing-stop"), FILL_OR_KILL("fill-or-kill"), 
	EXCHANGE_MARKET("exchange market"), EXCHANGE_LIMIT("exchange limit"), EXCHANGE_STOP("exchange stop"),
	EXCHANGE_TRAILING_STOP("exchange trailing-stop"), EXCHANGE_FILL_OR_KILL("exchange fill-or-kill");
	
	private String value;
	
	private Types(String value) {
		this.value = value;
	}
	
	@Override
	public String toString() {
		return value;
	}
	
}
