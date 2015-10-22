package com.sirolf2009.bitfinex.calls.auth;

import java.io.Serializable;
import java.util.List;

import org.apache.http.HttpResponse;

import com.google.gson.reflect.TypeToken;
import com.sirolf2009.bitfinex.Symbols;
import com.sirolf2009.bitfinex.exceptions.BitfinexCallException;
import com.sirolf2009.bitfinex.util.CallUtils;

import lombok.Data;

public class Trades extends HttpBitfinex {

	public Trades(Symbols symbols) {
		super("/trades/"+symbols);
	}

	public Trades(Symbols symbols, long timestamp) {
		super("/trades/"+symbols+"?timestamp="+timestamp);
	}

	public Trades(Symbols symbols, int count) {
		super("/trades/"+symbols+"?limit_trades="+count);
	}

	public Trades(Symbols symbols, long timestamp, int count) {
		super("/trades/"+symbols+"?timestamp="+timestamp+"&limit_trades="+count);
	}

	@Override
	public Object parseResponse(HttpResponse response) throws BitfinexCallException {
		return CallUtils.parseResponse(response, new TypeToken<List<TradesResponse>>() {}.getType());
	}
	
	@Data
	public static class TradesResponse implements Serializable {

		private static final long serialVersionUID = -4037190389844866185L;
		
		private int tid;
		private long time;
		private double price;
		private double amount;
		private String exchange;
		private String type;
		
	}
	
}
