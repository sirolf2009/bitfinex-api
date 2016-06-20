package com.sirolf2009.bitfinex;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.function.Consumer;
import java.util.zip.GZIPInputStream;

import com.sirolf2009.bitfinex.calls.Trades.TradesResponse;

public class HistoryTicker {
	
	private BufferedReader reader;
	
	public HistoryTicker(Consumer<TradesResponse> tradeConsumer) {
		try {
			reader = new BufferedReader(new InputStreamReader(new GZIPInputStream(new URL("http://api.bitcoincharts.com/v1/csv/bitfinexUSD.csv.gz").openStream())));
			String line;
			while((line = reader.readLine()) != null) {
				String[] data = line.split(",");
				long time = Long.parseLong(data[0]);
				double price = Double.parseDouble(data[1]);
				double volume = Double.parseDouble(data[2]);
				TradesResponse trade = new TradesResponse();
				trade.setTimestamp(time);
				trade.setPrice(price);
				trade.setAmount(volume);
				tradeConsumer.accept(trade);
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}
