package com.sirolf2009.bitfinex;

import java.util.function.Consumer;

import com.sirolf2009.bitfinex.CandlestickMapReduce.CandleStick;

public class HACandleTicker {

	private CandleStick previousHACandle;

	public HACandleTicker(Bitfinex bitfinex, Symbols symbol, long refreshRate, Timeframe timeframe,	Consumer<CandleStick> haCandleConsumer) {
		new CandlestickTicker(bitfinex, symbol, refreshRate, timeframe, new Consumer<CandleStick>() {
			@Override
			public void accept(CandleStick t) {
				CandleStick haCandle = CandlestickHeikenAshi.asHeikenAshi(t, previousHACandle);
				haCandleConsumer.accept(haCandle);
				previousHACandle = haCandle;
			}
		});
	}

}
