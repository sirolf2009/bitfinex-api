package com.sirolf2009.bitfinex;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.function.Consumer;
import java.util.zip.GZIPInputStream;

import org.apache.commons.io.IOUtils;

import com.sirolf2009.bitfinex.calls.Trades.TradesResponse;

public class HistoryTicker {
	
	private BufferedReader reader;
	
	public HistoryTicker(Consumer<TradesResponse> tradeConsumer) {
		File file = new File(System.getProperty("java.io.tmpdir"), "bitfinexUSD.csv.gz");
		if(!file.exists()) {
			try {
			FileOutputStream out = new FileOutputStream(file);
			IOUtils.copy(new URL("http://api.bitcoincharts.com/v1/csv/bitfinexUSD.csv.gz").openStream(), out);
			} catch(IOException e) {
				e.printStackTrace();
			}
		}
		try {
			reader = new BufferedReader(new InputStreamReader(new GZIPInputStream(new FileInputStream(file))));
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
