package com.sirolf2009.bitfinex.calls;

import org.apache.http.HttpResponse;

import com.sirolf2009.bitfinex.exceptions.BitfinexCallException;

public interface BitfinexCall {
	
	public Object parseResponse(HttpResponse response) throws BitfinexCallException;

}
