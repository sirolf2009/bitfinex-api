package com.sirolf2009.bitfinex.exceptions;

public class BitfinexInitializationException extends BitfinexException {

	private static final long serialVersionUID = -5993925614331898034L;

	public BitfinexInitializationException() {
		super();
	}

	public BitfinexInitializationException(String message) {
		super(message);
	}

	public BitfinexInitializationException(Throwable cause) {
		super(cause);
	}

	public BitfinexInitializationException(String message, Throwable cause) {
		super(message, cause);
	}

	public BitfinexInitializationException(String message, Throwable cause,	boolean enableSuppression, boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
	}

}
