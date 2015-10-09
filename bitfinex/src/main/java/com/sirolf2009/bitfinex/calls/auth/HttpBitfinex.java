package com.sirolf2009.bitfinex.calls.auth;

import static com.sirolf2009.bitfinex.Config.keys;

import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.Map;

import javax.crypto.Mac;

import org.apache.commons.codec.binary.Base64;
import org.apache.commons.codec.binary.Hex;
import org.apache.commons.codec.digest.HmacUtils;
import org.apache.http.Header;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.HttpPost;

import com.google.gson.Gson;
import com.sirolf2009.bitfinex.Config;
import com.sirolf2009.bitfinex.calls.BitfinexCall;

public abstract class HttpBitfinex extends HttpPost implements BitfinexCall {

	public HttpBitfinex(String request) {
		super(Config.baseUrl+request);
		addHeader("request", Config.basePath+request);
		addHeader("nonce", getNonce());
		updatePayload();
		int timeout = 10*1000;
		setConfig(RequestConfig.custom().setConnectionRequestTimeout(timeout).setConnectTimeout(timeout).setSocketTimeout(timeout).build());
	}
	
	public void updatePayload() {
		try {
			Map<String, String> headers = new HashMap<String, String>();
			for(Header header : getAllHeaders()) {
				headers.put(header.getName(), header.getValue());
			}
			byte[] payload = new Base64().encode(new Gson().toJson(headers).getBytes("UTF-8"));
			Mac mac = HmacUtils.getHmacSha384(keys.getPrivateKey().getBytes("UTF-8"));
			byte[] signature = mac.doFinal(payload);
			addHeader("X-BFX-APIKEY", keys.getPublicKey());
			addHeader("X-BFX-PAYLOAD", new String(payload));
			addHeader("X-BFX-SIGNATURE", Hex.encodeHexString(signature));
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
	}
	
	public String getNonce() {
		return System.currentTimeMillis()+"";
	}

}
