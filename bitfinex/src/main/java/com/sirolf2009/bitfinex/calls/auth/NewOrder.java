package com.sirolf2009.bitfinex.calls.auth;

import java.io.Serializable;

import org.apache.http.HttpResponse;

import com.sirolf2009.bitfinex.Sides;
import com.sirolf2009.bitfinex.Symbols;
import com.sirolf2009.bitfinex.Types;
import com.sirolf2009.bitfinex.exceptions.BitfinexCallException;
import com.sirolf2009.bitfinex.util.CallUtils;

import lombok.Data;

public class NewOrder extends HttpBitfinex {

	public NewOrder(Symbols symbol, double amount, double price, Sides side, Types type, boolean isHidden) {
		super("/order/new");
		addHeader("symbol", symbol.toString());
		addHeader("amount", amount+"");
		addHeader("price", price+"");
		addHeader("side", side.toString());
		addHeader("type", type.toString());
		addHeader("isHidden", isHidden+"");
		addHeader("exchange", "bitfinex");
		updatePayload();
	}

	@Override
	public NewOrderResponse parseResponse(HttpResponse response) throws BitfinexCallException {
		return CallUtils.parseResponse(response, NewOrderResponse.class);
	}
	
	@Data
	public static class NewOrderResponse implements Serializable {
		
		private static final long serialVersionUID = -2816062237655659945L;
		
		private int order_id;
		private String symbol;
		private String exchange;
		private double price;
		private double avg_execution_price;
		private String side;
		private String type;
		private String timestamp;
		private boolean is_live;
		private boolean is_cancelled;
		private boolean is_hidden;
		private boolean was_forced;
		private double original_amount;
		private double remaining_amount;
		private double executed_amount;
		
	}

}
