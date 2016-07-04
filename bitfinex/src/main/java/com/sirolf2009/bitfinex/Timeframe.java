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

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + (int) (seconds ^ (seconds >>> 32));
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Timeframe other = (Timeframe) obj;
		if (seconds != other.seconds)
			return false;
		return true;
	}

	public long getSeconds() {
		return seconds;
	}

	public void setSeconds(long seconds) {
		this.seconds = seconds;
	}
	
}
