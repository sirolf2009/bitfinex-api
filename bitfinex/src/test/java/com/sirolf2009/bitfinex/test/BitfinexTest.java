package com.sirolf2009.bitfinex.test;

import static org.junit.Assert.assertTrue;

import java.io.IOException;
import java.util.List;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.sirolf2009.bitfinex.Bitfinex;
import com.sirolf2009.bitfinex.CandlestickMapReduce.CandleStick;
import com.sirolf2009.bitfinex.Currencies;
import com.sirolf2009.bitfinex.Symbols;
import com.sirolf2009.bitfinex.Timeframe;
import com.sirolf2009.bitfinex.calls.Lendbook.LendbookResponse;
import com.sirolf2009.bitfinex.calls.Lendbook.Loan;
import com.sirolf2009.bitfinex.calls.Lends.LendsResponse;
import com.sirolf2009.bitfinex.calls.Pubticker.TickerResponse;
import com.sirolf2009.bitfinex.calls.Stats.Stat;
import com.sirolf2009.bitfinex.calls.Stats.StatsResponse;
import com.sirolf2009.bitfinex.calls.Trades.TradesResponse;
import com.sirolf2009.bitfinex.exceptions.BitfinexCallException;

public class BitfinexTest {
	
	private Bitfinex bitfinex;

	@Before
	public void setUp() throws Exception {
		bitfinex = new Bitfinex();
	}

	@After
	public void tearDown() throws Exception {
	}

	@Test
	public void testCandlestick() throws BitfinexCallException, NumberFormatException, IOException {
		List<CandleStick> candles = bitfinex.getCandlesticks(Symbols.BTCUSD, Timeframe.hours(4));
		CandleStick stick = candles.get(candles.size()-1);
		System.out.println(stick.toString());
		assertTrue("open > 0", stick.getOpen().getPrice() > 0);
		assertTrue("high > 0", stick.getHigh().getPrice() > 0);
		assertTrue("low > 0", stick.getLow().getPrice() > 0);
		assertTrue("close > 0", stick.getClose().getPrice() > 0);
		assertTrue("OHLC4 > 0", stick.getOHLC4() > 0);
		assertTrue("trades > 0", stick.getTrades().size() > 0);
	}

	@Test
	public void testTrades() throws BitfinexCallException {
		List<TradesResponse> response = bitfinex.trades(Symbols.BTCUSD);
		System.out.println(response.toString());
		assertTrue("response > 0", response.size() > 0);
		assertTrue("amount > 0", response.get(0).getAmount() > 0);
		assertTrue("price > 0", response.get(0).getPrice() > 0);
		assertTrue("tid > 0", response.get(0).getTid() > 0);
		assertTrue("time > 0", response.get(0).getTimestamp() > 0);
		assertTrue("type != null", response.get(0).getType() != null);
	}

	@Test
	public void testTicker() throws BitfinexCallException {
		TickerResponse response = bitfinex.pubticker(Symbols.BTCUSD);
		System.out.println(response.toString());
		assertTrue("mid > 0", response.getMid() > 0);
		assertTrue("bid > 0", response.getBid() > 0);
		assertTrue("ask > 0", response.getAsk() > 0);
		assertTrue("last_price > 0", response.getLast_price() > 0);
		assertTrue("low > 0", response.getLow() > 0);
		assertTrue("high > 0", response.getHigh() > 0);
		assertTrue("volume > 0", response.getVolume() > 0);
		assertTrue("timestamp > 0", response.getTimestamp() > 0);
	}
	
	@Test
	public void testStats() throws BitfinexCallException {
		StatsResponse response = bitfinex.stats(Symbols.BTCUSD);
		System.out.println(response.toString());
		for(Stat stat : response) {
			assertTrue("period > 0", stat.getPeriod() > 0);
			assertTrue("volume > 0", stat.getVolume() > 0);
		}
	}
	
	@Test
	public void testLendbook() throws BitfinexCallException {
		LendbookResponse response = bitfinex.lendbook(Currencies.BTC);
		System.out.println(response);
		for(Loan loan : response.getAsks()) {
			testLoan(loan);
		}
		for(Loan loan : response.getBids()) {
			testLoan(loan);
		}
		response = bitfinex.lendbook(Currencies.BTC, 1, 1);
		System.out.println(response);
		for(Loan loan : response.getAsks()) {
			testLoan(loan);
		}
		for(Loan loan : response.getBids()) {
			testLoan(loan);
		}
	}
	
	public void testLoan(Loan loan) {
		assertTrue("amount > 0", loan.getAmount() > 0);
		assertTrue("period > 0", loan.getPeriod() > 0);
		assertTrue("rate > 0", loan.getRate() > 0);
		assertTrue("frr == yes || frr == no", loan.getFrr().equals("Yes") || loan.getFrr().equals("No"));
	}
	
	@Test
	public void testLends() throws BitfinexCallException {
		List<LendsResponse> responses = bitfinex.lends(Currencies.BTC);
		System.out.println(responses);
		assertTrue(responses.size() > 0);
		responses.forEach(response -> assertTrue(response.getAmount_lent() > 0));
		responses.forEach(response -> assertTrue(response.getAmount_used() > 0));
		responses.forEach(response -> assertTrue(response.getRate() > 0));
		responses.forEach(response -> assertTrue(response.getTimestamp() > 0));
	}
	
	@Test
	public void testSentiment() throws BitfinexCallException {
		double sentiment = bitfinex.getSentiment(Symbols.BTCUSD);
		System.out.println(sentiment);
		assertTrue(sentiment >= 0);
		assertTrue(sentiment <= 100);
	}
 
}
