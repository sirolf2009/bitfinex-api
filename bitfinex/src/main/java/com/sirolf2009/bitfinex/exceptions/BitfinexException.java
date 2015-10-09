package com.sirolf2009.bitfinex.exceptions;

public class BitfinexException extends Exception {

	private static final long serialVersionUID = -413604366120357640L;

	public BitfinexException() {
		super();
	}

	public BitfinexException(String message) {
		super(message);
	}

	public BitfinexException(Throwable cause) {
		super(cause);
	}

	public BitfinexException(String message, Throwable cause) {
		super(message, cause);
	}

	public BitfinexException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
	}

}
