package com.sirolf2009.bitfinex;
import static com.sirolf2009.bitfinex.Currencies.*;

public enum Symbols {

	BTCUSD(BTC, USD), LTCUSD(LTC, USD), LTCBTC(LTC, USD);

	private Currencies currency1;
	private Currencies currency2;
	
	private Symbols(Currencies currency1, Currencies currency2) {
		this.currency1 = currency1;
		this.currency2 = currency2;
	}
	
	@Override
	public String toString() {
		return super.toString().toLowerCase();
	}

	public Currencies getCurrency1() {
		return currency1;
	}

	public void setCurrency1(Currencies currency1) {
		this.currency1 = currency1;
	}

	public Currencies getCurrency2() {
		return currency2;
	}

	public void setCurrency2(Currencies currency2) {
		this.currency2 = currency2;
	}
	
}
