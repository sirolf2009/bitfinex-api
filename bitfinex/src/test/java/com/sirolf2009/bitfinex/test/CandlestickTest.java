package com.sirolf2009.bitfinex.test;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

import org.junit.Assert;
import org.junit.Test;

import com.sirolf2009.bitfinex.CandlestickTicker;
import com.sirolf2009.bitfinex.Timeframe;
import com.sirolf2009.bitfinex.CandlestickMapReduce.CandleStick;
import com.sirolf2009.bitfinex.calls.Trades.TradesResponse;

public class CandlestickTest {
	
	int i = 0;
	
	@Test
	public void testCandlestickCreation() {
		List<TradesResponse> trades = new ArrayList<>();
		trades.add(t(0, 100));
		trades.add(t(1000*60, 200));
		trades.add(t(1000*60+1, 2000));
		trades.add(t(1000*60+2, 0.1));
		trades.add(t(1000*60+3, 201));
		trades.add(t(1000*60*2, 201));
		
		CandlestickTicker ticker = new CandlestickTicker(Timeframe.minutes(1), new Consumer<CandleStick>() {
			@Override
			public void accept(CandleStick t) {
				if(i == 0) {
					Assert.assertEquals(0, t.getOpen().getTimestamp());
					Assert.assertEquals(t.getOpen().getPrice(), 100, 0);
					Assert.assertEquals(t.getHigh().getTimestamp(), 0);
					Assert.assertEquals(t.getHigh().getPrice(), 100, 0);
					Assert.assertEquals(t.getLow().getTimestamp(), 0);
					Assert.assertEquals(t.getLow().getPrice(), 100, 0);
					Assert.assertEquals(t.getClose().getTimestamp(), 0);
					Assert.assertEquals(t.getClose().getPrice(), 100, 0);
				} else if(i == 1) {
					Assert.assertEquals(t.getOpen().getTimestamp(), 1000*60);
					Assert.assertEquals(t.getOpen().getPrice(), 200, 0);
					Assert.assertEquals(t.getHigh().getTimestamp(), 1000*60+1);
					Assert.assertEquals(t.getHigh().getPrice(), 2000, 0);
					Assert.assertEquals(t.getLow().getTimestamp(), 1000*60+2);
					Assert.assertEquals(t.getLow().getPrice(), 0.1, 0);
					Assert.assertEquals(t.getClose().getTimestamp(), 1000*60+3);
					Assert.assertEquals(t.getClose().getPrice(), 201, 0);
				} else {
					Assert.fail("There shouldn't be more than 2 finished candlesticks");
				}
				i++;
			}
		});
		
		trades.forEach(ticker);
	}
	
	public TradesResponse t(long timestamp, double price) {
		TradesResponse t = new TradesResponse();
		t.setTimestamp(timestamp);
		t.setPrice(price);
		return t;
	}

}
