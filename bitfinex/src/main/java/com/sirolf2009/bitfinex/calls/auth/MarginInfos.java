package com.sirolf2009.bitfinex.calls.auth;

import java.io.Serializable;

import org.apache.http.HttpResponse;

import com.sirolf2009.bitfinex.exceptions.BitfinexCallException;
import com.sirolf2009.bitfinex.util.CallUtils;

import lombok.Data;

public class MarginInfos extends HttpBitfinex {

	public MarginInfos() {
		super("/margin_infos");
	}
	
	@Override
	public Object parseResponse(HttpResponse response) throws BitfinexCallException {
		return CallUtils.parseResponse(response, MarginInfosResponse.class);
	}
	
	@Data
	public static class MarginInfosResponse implements Serializable {
		
		private static final long serialVersionUID = -8211497276524489351L;
		
		private double margin_balance;
		private double tradable_balance;
		private double unrealized_pl;
		private double unrealized_swap;
		private double net_value;
		private double required_margin;
		private double leverage;
		private double margin_requirements;
		private MarginInfosLimit[] margin_limits;
		
	}
	
	@Data
	public static class MarginInfosLimit implements Serializable {
		
		private static final long serialVersionUID = -9036282357145432508L;
		
		private String on_pair;
		private double initial_margin;
		private double margin_requirement;
		private double tradable_balance;
		
	}

}
