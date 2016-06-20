package com.sirolf2009.bitfinex;

public class Index {
	
	private long millis;
	private long positionInChart;
	private Timeframe timeframe;
	
	public Index() {
	}

	public Index(long millis, Timeframe timeframe) {
		this.millis = millis;
		this.positionInChart = millisToPosition(millis, timeframe);
		this.timeframe = timeframe;
	}
	
	public static long millisToPosition(long millis, Timeframe timeframe) {
		return Math.floorDiv(millis, timeframe.getSeconds());
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

	public void setMillis(long millis) {
		this.millis = millis;
	}

	public void setPositionInChart(long positionInChart) {
		this.positionInChart = positionInChart;
	}

	public void setTimeframe(Timeframe timeframe) {
		this.timeframe = timeframe;
	}


}
