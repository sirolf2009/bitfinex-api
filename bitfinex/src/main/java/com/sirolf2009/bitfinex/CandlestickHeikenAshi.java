package com.sirolf2009.bitfinex;

import java.util.ArrayList;
import java.util.List;

import com.sirolf2009.bitfinex.CandlestickMapReduce.CandleStick;
import com.sirolf2009.bitfinex.calls.Trades.TradesResponse;

public strictfp class CandlestickHeikenAshi {
	
	public static List<CandleStick> heikenAshi(List<CandleStick> candlesticks) {
		List<CandleStick> HACandles = new ArrayList<CandleStick>(candlesticks.size());
		for(int i = 0; i < candlesticks.size(); i++) {
			HACandles.add(asHeikenAshi(candlesticks.get(i), HACandles.get(i-1)));
		}
		return HACandles;
	}
	
	public static CandleStick asHeikenAshi(CandleStick candle, CandleStick previousHACandle) {
		TradesResponse close = trade(candle.getClose(), previousHACandle == null ? candle.getClose().getPrice() : candle.getOHLC4());
		TradesResponse open = trade(candle.getOpen(), previousHACandle == null ? candle.getOpen().getPrice() : (previousHACandle.getOpen().getPrice()+previousHACandle.getClose().getPrice())/2);
		TradesResponse high = trade(candle.getHigh(), previousHACandle == null ? candle.getHigh().getPrice() : Math.max(candle.getHigh().getPrice(), open.getPrice()));
		TradesResponse low = trade(candle.getLow(), previousHACandle == null ? candle.getLow().getPrice() : Math.min(candle.getLow().getPrice(), open.getPrice()));
		CandleStick HACandle = new CandleStick(open, candle.getIndex().getTimeframe());
		HACandle.setOpen(open);
		HACandle.setClose(close);
		HACandle.setHigh(high);
		HACandle.setLow(low);
		return HACandle;
	}
	
	private static TradesResponse trade(TradesResponse response, double price) {
		TradesResponse trade = new TradesResponse();
		trade.setPrice(price);
		trade.setTimestamp(response.getTimestamp());
		trade.setExchange(response.getExchange());
		return trade;
	}

}
