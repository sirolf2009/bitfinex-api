package com.sirolf2009.bitfinex.calls.auth;

import java.io.Serializable;

import org.apache.http.HttpResponse;

import com.sirolf2009.bitfinex.exceptions.BitfinexCallException;
import com.sirolf2009.bitfinex.util.CallUtils;

import lombok.Data;

public class OrderStatus extends HttpBitfinex {
	

	public OrderStatus(int orderID) {
		super("/order/status");
		addHeader("order_id", orderID+"");
		updatePayload();
	}

	@Override
	public Object parseResponse(HttpResponse response)	throws BitfinexCallException {
		return CallUtils.parseResponse(response, OrderStatusResponse.class);
	}
	
	@Data
	public static class OrderStatusResponse implements Serializable {
		
		private static final long serialVersionUID = 6975204613450694042L;
		
		private String symbol;
		private String exchange;
		private double price;
		private double avg_execution_price;
		private String side;
		private String type;
		private double timestamp;
		private boolean is_live;
		private boolean is_cancelled;
		private boolean is_hidden;
		private boolean was_forced;
		private double executed_amount;
		private double remaining_amount;
		private double original_amount;
		
	}

}
