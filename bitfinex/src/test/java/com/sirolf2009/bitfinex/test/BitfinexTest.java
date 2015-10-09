package com.sirolf2009.bitfinex.test;

import static org.junit.Assert.assertTrue;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.sirolf2009.bitfinex.Bitfinex;
import com.sirolf2009.bitfinex.Currencies;
import com.sirolf2009.bitfinex.Symbols;
import com.sirolf2009.bitfinex.calls.Lendbook.LendbookResponse;
import com.sirolf2009.bitfinex.calls.Lendbook.Loan;
import com.sirolf2009.bitfinex.calls.Stats.Stat;
import com.sirolf2009.bitfinex.calls.Stats.StatsResponse;
import com.sirolf2009.bitfinex.calls.Ticker.TickerResponse;
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
	public void testTicker() throws BitfinexCallException {
		TickerResponse response = bitfinex.ticker(Symbols.BTCUSD);
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

}
