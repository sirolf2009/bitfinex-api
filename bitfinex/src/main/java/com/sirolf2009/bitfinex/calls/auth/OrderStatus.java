package com.sirolf2009.bitfinex.calls.auth;

import java.io.Serializable;

import org.apache.http.HttpResponse;

import com.sirolf2009.bitfinex.exceptions.BitfinexCallException;
import com.sirolf2009.bitfinex.util.CallUtils;

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
		
		@Override
		public String toString() {
			return "OrderStatusResponse [symbol=" + symbol + ", exchange=" + exchange + ", price=" + price
					+ ", avg_execution_price=" + avg_execution_price + ", side=" + side + ", type=" + type
					+ ", timestamp=" + timestamp + ", is_live=" + is_live + ", is_cancelled=" + is_cancelled
					+ ", is_hidden=" + is_hidden + ", was_forced=" + was_forced + ", executed_amount=" + executed_amount
					+ ", remaining_amount=" + remaining_amount + ", original_amount=" + original_amount + "]";
		}
		
		public String getSymbol() {
			return symbol;
		}
		public void setSymbol(String symbol) {
			this.symbol = symbol;
		}
		public String getExchange() {
			return exchange;
		}
		public void setExchange(String exchange) {
			this.exchange = exchange;
		}
		public double getPrice() {
			return price;
		}
		public void setPrice(double price) {
			this.price = price;
		}
		public double getAvg_execution_price() {
			return avg_execution_price;
		}
		public void setAvg_execution_price(double avg_execution_price) {
			this.avg_execution_price = avg_execution_price;
		}
		public String getSide() {
			return side;
		}
		public void setSide(String side) {
			this.side = side;
		}
		public String getType() {
			return type;
		}
		public void setType(String type) {
			this.type = type;
		}
		public double getTimestamp() {
			return timestamp;
		}
		public void setTimestamp(double timestamp) {
			this.timestamp = timestamp;
		}
		public boolean isIs_live() {
			return is_live;
		}
		public void setIs_live(boolean is_live) {
			this.is_live = is_live;
		}
		public boolean isIs_cancelled() {
			return is_cancelled;
		}
		public void setIs_cancelled(boolean is_cancelled) {
			this.is_cancelled = is_cancelled;
		}
		public boolean isIs_hidden() {
			return is_hidden;
		}
		public void setIs_hidden(boolean is_hidden) {
			this.is_hidden = is_hidden;
		}
		public boolean isWas_forced() {
			return was_forced;
		}
		public void setWas_forced(boolean was_forced) {
			this.was_forced = was_forced;
		}
		public double getExecuted_amount() {
			return executed_amount;
		}
		public void setExecuted_amount(double executed_amount) {
			this.executed_amount = executed_amount;
		}
		public double getRemaining_amount() {
			return remaining_amount;
		}
		public void setRemaining_amount(double remaining_amount) {
			this.remaining_amount = remaining_amount;
		}
		public double getOriginal_amount() {
			return original_amount;
		}
		public void setOriginal_amount(double original_amount) {
			this.original_amount = original_amount;
		}
		
	}

}
