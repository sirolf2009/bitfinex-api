package com.sirolf2009.bitfinex.util;

import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Type;

import org.apache.commons.io.IOUtils;
import org.apache.http.HttpResponse;
import org.apache.http.client.utils.URIBuilder;

import com.google.gson.Gson;
import com.google.gson.JsonIOException;
import com.google.gson.JsonSyntaxException;
import com.sirolf2009.bitfinex.Config;
import com.sirolf2009.bitfinex.exceptions.BitfinexCallException;

import lombok.extern.log4j.Log4j2;

@Log4j2
public class CallUtils {
	
	public static URIBuilder createUriBuilder(String path) {
		return new URIBuilder().setScheme(Config.scheme).setHost(Config.host).setPath(Config.basePath+path);
	}
	
	public static <R> R parseResponse(HttpResponse response, Type responseType) throws BitfinexCallException {
		try {
			InputStream stream = response.getEntity().getContent();
			String content = IOUtils.toString(stream);
			log.trace("Received\n"+content);
			stream.close();
			return new Gson().fromJson(content, responseType);
		} catch (JsonSyntaxException e) {
			throw new BitfinexCallException("Could not parse the response as JSon", e);
		} catch (JsonIOException e) {
			throw new BitfinexCallException("Could not read the response", e);
		} catch (IllegalStateException e) {
			throw new BitfinexCallException("Could not create the content stream", e);
		} catch (IOException e) {
			throw new BitfinexCallException("Could not create the stream", e);
		}
	}

}
