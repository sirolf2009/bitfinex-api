package com.sirolf2009.bitfinex.calls;

import java.io.Serializable;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.Arrays;

import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpGet;

import com.sirolf2009.bitfinex.Config;
import com.sirolf2009.bitfinex.Currencies;
import com.sirolf2009.bitfinex.exceptions.BitfinexCallException;
import com.sirolf2009.bitfinex.util.CallUtils;

public class Lendbook extends HttpGet implements BitfinexCall {

	public Lendbook(Currencies currency) {
		super(Config.baseUrl+"/lendbook/"+currency);
	}
	
	public Lendbook(Currencies currency, int limit_bids, int limit_asks) throws BitfinexCallException {
		super(getUri(currency, limit_bids, limit_asks));
	}
	
	private static URI getUri(Currencies currency, int limit_bids, int limit_asks) throws BitfinexCallException {
		try {
			return CallUtils.createUriBuilder("/lendbook/"+currency).addParameter("limit_bids", limit_bids+"").addParameter("limit_asks", limit_asks+"").build();
		} catch (URISyntaxException e) {
			throw new BitfinexCallException("Could not create the uri", e);
		}
	}

	@Override
	public Object parseResponse(HttpResponse response) throws BitfinexCallException {
		return CallUtils.parseResponse(response, LendbookResponse.class);
	}
	
	public static class LendbookResponse implements Serializable {

		private static final long serialVersionUID = 5526073961306355928L;

		private Loan[] bids;
		private Loan[] asks;
		
		public Loan[] getBids() {
			return bids;
		}
		public void setBids(Loan[] bids) {
			this.bids = bids;
		}
		public Loan[] getAsks() {
			return asks;
		}
		public void setAsks(Loan[] asks) {
			this.asks = asks;
		}
		
		@Override
		public String toString() {
			StringBuilder builder = new StringBuilder();
			builder.append("LendbookResponse [bids=");
			builder.append(Arrays.toString(bids));
			builder.append(", asks=");
			builder.append(Arrays.toString(asks));
			builder.append("]");
			return builder.toString();
		}
		
	}
	
	public static class Loan implements Serializable {
		
		private static final long serialVersionUID = -994840290609093870L;
		private double rate;
		private double amount;
		private int period;
		private double timestamp;
		private String frr;
		
		public double getRate() {
			return rate;
		}
		public void setRate(double rate) {
			this.rate = rate;
		}
		public double getAmount() {
			return amount;
		}
		public void setAmount(double amount) {
			this.amount = amount;
		}
		public int getPeriod() {
			return period;
		}
		public void setPeriod(int period) {
			this.period = period;
		}
		public double getTimestamp() {
			return timestamp;
		}
		public void setTimestamp(double timestamp) {
			this.timestamp = timestamp;
		}
		public String getFrr() {
			return frr;
		}
		public void setFrr(String frr) {
			this.frr = frr;
		}
		
		@Override
		public String toString() {
			StringBuilder builder = new StringBuilder();
			builder.append("Loan [rate=");
			builder.append(rate);
			builder.append(", amount=");
			builder.append(amount);
			builder.append(", period=");
			builder.append(period);
			builder.append(", timestamp=");
			builder.append(timestamp);
			builder.append(", frr=");
			builder.append(frr);
			builder.append("]");
			return builder.toString();
		}
		
	}

}
