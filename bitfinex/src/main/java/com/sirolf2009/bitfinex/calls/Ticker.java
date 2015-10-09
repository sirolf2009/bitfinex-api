package com.sirolf2009.bitfinex.calls;

import java.io.Serializable;

import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpGet;

import com.sirolf2009.bitfinex.Config;
import com.sirolf2009.bitfinex.Symbols;
import com.sirolf2009.bitfinex.exceptions.BitfinexCallException;
import com.sirolf2009.bitfinex.util.CallUtils;

public class Ticker extends HttpGet implements BitfinexCall {

	public Ticker(Symbols symbol) {
		super(Config.baseUrl+"/pubticker/"+symbol.toString());
	}
	
	@Override
	public Object parseResponse(HttpResponse response) throws BitfinexCallException {
		return CallUtils.parseResponse(response, TickerResponse.class);
	}
	
	public static class TickerResponse implements Serializable {
		
		private static final long serialVersionUID = 5047747554543619843L;
		
		private double mid;
		private double bid;
		private double ask;
		private double last_price;
		private double low;
		private double high;
		private double volume;
		private double timestamp;
		
		public double getMid() {
			return mid;
		}
		public void setMid(double mid) {
			this.mid = mid;
		}
		public double getBid() {
			return bid;
		}
		public void setBid(double bid) {
			this.bid = bid;
		}
		public double getAsk() {
			return ask;
		}
		public void setAsk(double ask) {
			this.ask = ask;
		}
		public double getLast_price() {
			return last_price;
		}
		public void setLast_price(double last_price) {
			this.last_price = last_price;
		}
		public double getLow() {
			return low;
		}
		public void setLow(double low) {
			this.low = low;
		}
		public double getHigh() {
			return high;
		}
		public void setHigh(double high) {
			this.high = high;
		}
		public double getVolume() {
			return volume;
		}
		public void setVolume(double volume) {
			this.volume = volume;
		}
		public double getTimestamp() {
			return timestamp;
		}
		public void setTimestamp(double timestamp) {
			this.timestamp = timestamp;
		}
		
		@Override
		public String toString() {
			StringBuilder builder = new StringBuilder();
			builder.append("TickerResponse [mid=");
			builder.append(mid);
			builder.append(", bid=");
			builder.append(bid);
			builder.append(", ask=");
			builder.append(ask);
			builder.append(", last_price=");
			builder.append(last_price);
			builder.append(", low=");
			builder.append(low);
			builder.append(", high=");
			builder.append(high);
			builder.append(", volume=");
			builder.append(volume);
			builder.append(", timestamp=");
			builder.append(timestamp);
			builder.append("]");
			return builder.toString();
		}
		
	}

}
