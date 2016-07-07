package com.sirolf2009.bitfinex;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.TimeZone;

public class Index {

	private long seconds;
	private long positionInChart;
	private Timeframe timeframe;
	
	public Index() {
	}

	public Index(long seconds, Timeframe timeframe) {
		this.seconds = seconds;
		this.positionInChart = secondsToPosition(seconds, timeframe);
		this.timeframe = timeframe;
	}
	
	public static long secondsToPosition(long seconds, Timeframe timeframe) {
		return Math.floorDiv(seconds, timeframe.getSeconds());
	}
	
	public long positionInTimeframe(Timeframe timeframe) {
		return secondsToPosition(seconds, timeframe);
	}
	
	public Date getDate() {
		Calendar cal = Calendar.getInstance(TimeZone.getTimeZone("UTC"));
		cal.setTimeInMillis(seconds*1000);
		return cal.getTime();
	}
	
	public String getDateUTC() {
		return getDate("UTC");
	}
	
	public String getDate(String timezone) {
		SimpleDateFormat format = new SimpleDateFormat("EEE MMM dd HH:mm:ss zzz yyyy");
		format.setTimeZone(TimeZone.getTimeZone(timezone));
		return format.format(getDate());
	}

	@Override
	public String toString() {
		return "Index [seconds=" + seconds + ", positionInChart=" + positionInChart + ", timeframe=" + timeframe + "]";
	}
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + (int) (positionInChart ^ (positionInChart >>> 32));
		result = prime * result + (int) (seconds ^ (seconds >>> 32));
		result = prime * result + ((timeframe == null) ? 0 : timeframe.hashCode());
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
		Index other = (Index) obj;
		if (positionInChart != other.positionInChart)
			return false;
		if (seconds != other.seconds)
			return false;
		if (timeframe == null) {
			if (other.timeframe != null)
				return false;
		} else if (!timeframe.equals(other.timeframe))
			return false;
		return true;
	}

	public long getSeconds() {
		return seconds;
	}

	public long getPositionInChart() {
		return positionInChart;
	}

	public Timeframe getTimeframe() {
		return timeframe;
	}

	public void setSeconds(long seconds) {
		this.seconds = seconds;
	}

	public void setPositionInChart(long positionInChart) {
		this.positionInChart = positionInChart;
	}

	public void setTimeframe(Timeframe timeframe) {
		this.timeframe = timeframe;
	}


}
