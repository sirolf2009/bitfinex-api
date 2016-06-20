package com.sirolf2009.bitfinex;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.stream.Collectors;

import com.sirolf2009.bitfinex.calls.Trades.TradesResponse;

public class CandlestickMapReduce {

	public static List<CandleStick> mapReduce(Timeframe timeFrame, List<TradesResponse> trades) {
		Map<Long, List<CandleStick>> candles = trades.stream().map(trade -> new CandleStick(trade, timeFrame)).collect(Collectors.groupingBy((CandleStick candle) -> candle.getIndex().getPositionInChart()));
		Map<Long, CandleStick> reducedCandles = new HashMap<Long, CandleStick>();
		for(Entry<Long, List<CandleStick>> entry : candles.entrySet()) {
			CandleStick reducedCandle = null;
			for(CandleStick candle : entry.getValue()) {
				if(reducedCandle == null) {
					reducedCandle = candle;
				} else {
					reducedCandle = merge(reducedCandle, candle);
				}
			}
			reducedCandles.put(reducedCandle.getIndex().getPositionInChart(), reducedCandle);
		}
		reducedCandles.values().forEach(candle -> calculateOHLCV(candle));

		return reducedCandles.values().stream().sorted((trade1, trade2) -> new Long(trade1.open.getTimestamp()).compareTo(trade2.open.getTimestamp())).collect(Collectors.toList());
	}
	
	public static CandleStick merge(CandleStick candle1, CandleStick candle2) {
		CandleStick candle = new CandleStick();
		candle.setIndex(new Index(Math.min(candle1.index.getMillis(), candle2.index.getMillis()), candle1.getIndex().getTimeframe()));
		candle.setTrades(candle1.getTrades());
		candle.getTrades().addAll(candle2.getTrades());
		calculateOHLCV(candle);
		return candle;
	}

	public static void calculateOHLCV(CandleStick candle) {
		candle.open = candle.getTrades().stream().min((trade1, trade2) -> new Long(trade1.getTimestamp()).compareTo(trade2.getTimestamp())).get();
		candle.high = candle.getTrades().stream().max((trade1, trade2) -> new Double(trade1.getPrice()).compareTo(trade2.getPrice())).get();
		candle.low = candle.getTrades().stream().min((trade1, trade2) -> new Double(trade1.getPrice()).compareTo(trade2.getPrice())).get();
		candle.close = candle.getTrades().stream().max((trade1, trade2) -> new Long(trade1.getTimestamp()).compareTo(trade2.getTimestamp())).get();
		candle.volume = candle.getTrades().stream().mapToDouble(trade -> trade.getAmount()).sum();
	}

	public static class CandleStick {
		
		private List<TradesResponse> trades;
		private TradesResponse open;
		private TradesResponse high;
		private TradesResponse low;
		private TradesResponse close;
		private double volume;
		private Index index;

		public CandleStick() {
		}

		public CandleStick(TradesResponse trade, Timeframe timeframe) {
			trades = new ArrayList<TradesResponse>();
			trades.add(trade);
			open = trade;
			high = trade;
			low = trade;
			close = trade;
			this.index = new Index(trade.getTimestamp(), timeframe);
		}
		
		@Override
		public String toString() {
			return "CandleStick [trades=" + trades + ", open=" + open + ", high=" + high + ", low=" + low + ", close="
					+ close + ", volume=" + volume + ", index=" + index + "]";
		}

		public double getOHLC4() {
			return (open.getPrice()+high.getPrice()+low.getPrice()+close.getPrice())/4;
		}

		public List<TradesResponse> getTrades() {
			return trades;
		}

		public void setTrades(List<TradesResponse> trades) {
			this.trades = trades;
		}

		public TradesResponse getOpen() {
			return open;
		}

		public void setOpen(TradesResponse open) {
			this.open = open;
		}

		public TradesResponse getHigh() {
			return high;
		}

		public void setHigh(TradesResponse high) {
			this.high = high;
		}

		public TradesResponse getLow() {
			return low;
		}

		public void setLow(TradesResponse low) {
			this.low = low;
		}

		public TradesResponse getClose() {
			return close;
		}

		public void setClose(TradesResponse close) {
			this.close = close;
		}

		public double getVolume() {
			return volume;
		}

		public void setVolume(double volume) {
			this.volume = volume;
		}
		
		public Index getIndex() {
			return index;
		}
		
		public void setIndex(Index index) {
			this.index = index;
		}

	}

}
