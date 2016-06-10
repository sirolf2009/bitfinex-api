package com.sirolf2009.bitfinex;

import java.util.function.Consumer;

import com.sirolf2009.bitfinex.CandlestickMapReduce.CandleStick;
import com.sirolf2009.bitfinex.calls.Trades.TradesResponse;

public class CandlestickTicker {
	
	private CandleStick candlestick;

	public CandlestickTicker(Bitfinex bitfinex, Symbols symbol, long refreshRate, Timeframe timeframe, Consumer<CandleStick> tradeConsumer) {
		new Thread(new Ticker(bitfinex, symbol, refreshRate, new Consumer<TradesResponse>() {
			@Override
			public void accept(TradesResponse t) {
				if(candlestick == null) {
					candlestick = new CandleStick(t, timeframe);
				} else {
					CandleStick newCandle = new CandleStick(t, timeframe);
					if(newCandle.getIndex().getPositionInChart() == candlestick.getIndex().getPositionInChart()) {
						candlestick = CandlestickMapReduce.merge(candlestick, newCandle);
					} else {
						tradeConsumer.accept(candlestick);
						candlestick = newCandle;
					}
				}
			}
		})).start();
	}
		
}
