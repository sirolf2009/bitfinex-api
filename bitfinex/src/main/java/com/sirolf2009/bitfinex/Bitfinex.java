package com.sirolf2009.bitfinex;

import java.io.IOException;
import java.util.Date;
import java.util.List;
import java.util.function.Consumer;

import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.impl.client.HttpClients;

import com.sirolf2009.bitfinex.CandlestickMapReduce.CandleStick;
import com.sirolf2009.bitfinex.calls.BitfinexCall;
import com.sirolf2009.bitfinex.calls.Lendbook;
import com.sirolf2009.bitfinex.calls.Lendbook.LendbookResponse;
import com.sirolf2009.bitfinex.calls.Lends;
import com.sirolf2009.bitfinex.calls.Lends.LendsResponse;
import com.sirolf2009.bitfinex.calls.Pubticker;
import com.sirolf2009.bitfinex.calls.Pubticker.TickerResponse;
import com.sirolf2009.bitfinex.calls.Stats;
import com.sirolf2009.bitfinex.calls.Stats.StatsResponse;
import com.sirolf2009.bitfinex.calls.Trades;
import com.sirolf2009.bitfinex.calls.Trades.TradesResponse;
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
	
	/* MID LEVEL FUNCTIONS */
	
	public void ticker(Symbols symbols, long refreshRate, Consumer<TradesResponse> tradeconsumer) {
		new Thread(new Ticker(this, symbols, refreshRate, tradeconsumer)).start();
	}
	
	public void candleTicker(Symbols symbols, long refreshRate, Timeframe timeframe, Consumer<CandleStick> candleConsumer) {
		new Thread(new Ticker(this, symbols, refreshRate, new CandlestickTicker(timeframe, candleConsumer))).start();
	}
	
	public void heikenAshiTicker(Symbols symbols, long refreshRate, Timeframe timeframe, Consumer<CandleStick> candleconsumer) {
		new Thread(new Ticker(this, symbols, refreshRate, new CandlestickTicker(timeframe, new HACandleTicker(candleconsumer)))).start();
	}
	
	public void historyTicker(Consumer<TradesResponse> tradeconsumer) {
		new HistoryTicker(tradeconsumer);
	}
	
	public void historyCandleTicker(Timeframe timeframe, Consumer<CandleStick> candleConsumer) {
		new HistoryTicker(new CandlestickTicker(timeframe, candleConsumer));
	}
	
	public void historyHeikenAshiTicker(Timeframe timeframe, Consumer<CandleStick> candleconsumer) {
		new HistoryTicker(new CandlestickTicker(timeframe, new HACandleTicker(candleconsumer)));
	}
	
	public List<CandleStick> getCandlesticks(Symbols symbol, Timeframe timeframe) throws BitfinexCallException {
		return CandlestickMapReduce.mapReduce(timeframe, trades(symbol));
	}
	
	public List<CandleStick> getCandlesticks(Symbols symbol, Timeframe timeframe, int count) throws BitfinexCallException {
		return CandlestickMapReduce.mapReduce(timeframe, trades(symbol, count));
	}
	
	public List<CandleStick> getCandlesticks(Symbols symbol, Timeframe timeframe, long timestamp) throws BitfinexCallException {
		return CandlestickMapReduce.mapReduce(timeframe, trades(symbol, timestamp));
	}
	
	public List<CandleStick> getCandlesticks(Symbols symbol, Timeframe timeframe, int count, long timestamp) throws BitfinexCallException {
		return CandlestickMapReduce.mapReduce(timeframe, trades(symbol, count, timestamp));
	}
	
	/* LOW LEVEL FUNCTIONS */
	
	public TickerResponse pubticker(Symbols symbol) throws BitfinexCallException {
		return (TickerResponse) call(new Pubticker(symbol));
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
	
	@SuppressWarnings("unchecked")
	public List<LendsResponse> lends(Currencies currency) throws BitfinexCallException {
		return (List<LendsResponse>) call(new Lends(currency));
	}
	
	@SuppressWarnings("unchecked")
	public List<LendsResponse> lends(Currencies currency, Date date, int count) throws BitfinexCallException {
		return (List<LendsResponse>) call(new Lends(currency, date, count));
	}

	@SuppressWarnings("unchecked")
	public List<TradesResponse> trades(Symbols symbol) throws BitfinexCallException {
		return (List<TradesResponse>) call(new Trades(symbol));
	}

	@SuppressWarnings("unchecked")
	public List<TradesResponse> trades(Symbols symbol, int count) throws BitfinexCallException {
		return (List<TradesResponse>) call(new Trades(symbol, count));
	}

	@SuppressWarnings("unchecked")
	public List<TradesResponse> trades(Symbols symbol, long timestamp) throws BitfinexCallException {
		return (List<TradesResponse>) call(new Trades(symbol, timestamp));
	}

	@SuppressWarnings("unchecked")
	public List<TradesResponse> trades(Symbols symbol, int count, long timestamp) throws BitfinexCallException {
		return (List<TradesResponse>) call(new Trades(symbol, timestamp, count));
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
