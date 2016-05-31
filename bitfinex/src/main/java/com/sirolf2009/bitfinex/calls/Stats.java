package com.sirolf2009.bitfinex.calls;

import java.io.Serializable;
import java.util.ArrayList;

import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpGet;

import com.sirolf2009.bitfinex.Config;
import com.sirolf2009.bitfinex.Symbols;
import com.sirolf2009.bitfinex.exceptions.BitfinexCallException;
import com.sirolf2009.bitfinex.util.CallUtils;

public class Stats extends HttpGet implements BitfinexCall {

	public Stats(Symbols symbol) {
		super(Config.baseUrl+"/stats/"+symbol);
	}

	@Override
	public Object parseResponse(HttpResponse response) throws BitfinexCallException {
		return CallUtils.parseResponse(response, StatsResponse.class);
	}
	
	public static class StatsResponse extends ArrayList<Stat> {

		private static final long serialVersionUID = -6541091815381106988L;
		
	}
	
	public static class Stat implements Serializable {
		
		private static final long serialVersionUID = -4762474386056714327L;
		
		private int period;
		private double volume;
		
		@Override
		public String toString() {
			return "Stat [period=" + period + ", volume=" + volume + "]";
		}
		
		public int getPeriod() {
			return period;
		}
		public void setPeriod(int period) {
			this.period = period;
		}
		public double getVolume() {
			return volume;
		}
		public void setVolume(double volume) {
			this.volume = volume;
		}
		
	}

}
