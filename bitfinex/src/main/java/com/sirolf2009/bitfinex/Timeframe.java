package com.sirolf2009.bitfinex;

public class Timeframe {
	
	long millis;
	
	public Timeframe(long millis) {
		this.millis = millis;
	}
	

	public static Timeframe minute() {
		return new Timeframe(1000*60);
	}
	
	public static Timeframe minutes(int count) {
		return new Timeframe(1000*60*count);
	}
	
	public static Timeframe hour() {
		return minutes(60);
	}
	
	public static Timeframe hours(int count) {
		return minutes(60*count);
	}
	

	public long getMillis() {
		return millis;
	}

	public void setMillis(long millis) {
		this.millis = millis;
	}

}
