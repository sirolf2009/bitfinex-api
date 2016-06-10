package com.sirolf2009.bitfinex;

public class Index {
	
	private final long millis;
	private final long positionInChart;
	private final Timeframe timeframe;

	public Index(long millis, Timeframe timeframe) {
		this.millis = millis;
		this.positionInChart = Math.floorDiv(millis, timeframe.getSeconds());
		this.timeframe = timeframe;
	}

	@Override
	public String toString() {
		return "Index [millis=" + millis + ", positionInChart=" + positionInChart + ", timeframe=" + timeframe + "]";
	}

	public long getMillis() {
		return millis;
	}

	public long getPositionInChart() {
		return positionInChart;
	}

	public Timeframe getTimeframe() {
		return timeframe;
	}

}
