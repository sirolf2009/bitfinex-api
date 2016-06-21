package com.sirolf2009.bitfinex;

public class Timeframe {
	
	private long seconds;
	
	public Timeframe() {
	}
	
	public Timeframe(long seconds) {
		this.seconds = seconds;
	}
	

	public static Timeframe minute() {
		return new Timeframe(60);
	}
	
	public static Timeframe minutes(int count) {
		return new Timeframe(60*count);
	}
	
	public static Timeframe hour() {
		return minutes(60);
	}
	
	public static Timeframe hours(int count) {
		return minutes(60*count);
	}
	

	public long getSeconds() {
		return seconds;
	}

	public void setSeconds(long seconds) {
		this.seconds = seconds;
	}
	
}
