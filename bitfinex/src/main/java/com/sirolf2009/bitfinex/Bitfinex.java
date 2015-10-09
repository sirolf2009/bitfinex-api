package com.sirolf2009.bitfinex;

import java.io.IOException;

import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.impl.client.HttpClients;

import com.sirolf2009.bitfinex.calls.BitfinexCall;
import com.sirolf2009.bitfinex.calls.Lendbook;
import com.sirolf2009.bitfinex.calls.Lendbook.LendbookResponse;
import com.sirolf2009.bitfinex.calls.Stats;
import com.sirolf2009.bitfinex.calls.Stats.StatsResponse;
import com.sirolf2009.bitfinex.calls.Ticker;
import com.sirolf2009.bitfinex.calls.Ticker.TickerResponse;
import com.sirolf2009.bitfinex.calls.auth.MarginInfos;
import com.sirolf2009.bitfinex.calls.auth.MarginInfos.MarginInfosResponse;
import com.sirolf2009.bitfinex.calls.auth.NewOrder;
import com.sirolf2009.bitfinex.calls.auth.NewOrder.NewOrderResponse;
import com.sirolf2009.bitfinex.calls.auth.OrderStatus;
import com.sirolf2009.bitfinex.calls.auth.OrderStatus.OrderStatusResponse;
import com.sirolf2009.bitfinex.exceptions.BitfinexCallException;
import com.sirolf2009.bitfinex.exceptions.BitfinexInitializationException;

public class Bitfinex {
	
	public final HttpClient client;

	public Bitfinex() {
		client = HttpClients.createDefault();
	}
	
	public TickerResponse ticker(Symbols symbol) throws BitfinexCallException {
		return (TickerResponse) call(new Ticker(symbol));
	}
	
	public StatsResponse stats(Symbols symbol) throws BitfinexCallException {
		return (StatsResponse) call(new Stats(symbol));
	}
	
	public LendbookResponse lendbook(Currencies currency) throws BitfinexCallException {
		return (LendbookResponse) call(new Lendbook(currency));
	}
	
	public LendbookResponse lendbook(Currencies currency, int limit_bids, int limit_asks) throws BitfinexCallException {
		return (LendbookResponse) call(new Lendbook(currency, limit_bids, limit_asks));
	}
	
	public MarginInfosResponse marginInfos() throws BitfinexCallException {
		return (MarginInfosResponse) call(new MarginInfos());
	}

	public NewOrderResponse marginBuyMarket(Symbols symbol, double amount, double price) throws BitfinexCallException {
		return newOrder(symbol, amount, price, Sides.BUY, Types.MARKET);
	}

	public NewOrderResponse marginSellMarket(Symbols symbol, double amount, double price) throws BitfinexCallException {
		return newOrder(symbol, amount, price, Sides.SELL, Types.MARKET);
	}

	public NewOrderResponse newOrder(Symbols symbol, double amount, double price, Sides side, Types type) throws BitfinexCallException {
		return newOrder(symbol, amount, price, side, type, false);
	}

	public NewOrderResponse newOrder(Symbols symbol, double amount, double price, Sides side, Types type, boolean isHidden) throws BitfinexCallException {
		return (NewOrderResponse) call(new NewOrder(symbol, amount, price, side, type, isHidden));
	}

	public OrderStatusResponse orderStatus(int orderID) throws BitfinexCallException {
		return (OrderStatusResponse) call(new OrderStatus(orderID));
	}
	
	private Object call(BitfinexCall call) throws BitfinexCallException {
		try {
			return call.parseResponse(client.execute((HttpUriRequest) call));
		} catch (IOException e) {
			throw new BitfinexCallException("Could not send call using: "+call, e);
		}
	}
	
	public static void main(String[] args) {
		try {
			Config.loadConfig("bitfinex_api.properties");
		} catch (BitfinexInitializationException e) {
			e.printStackTrace();
		}
	}

}
