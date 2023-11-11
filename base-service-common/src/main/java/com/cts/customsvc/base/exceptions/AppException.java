package com.cts.customsvc.base.exceptions;

public class AppException extends RuntimeException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public AppException(String message) {
		super(message);
	}

	public AppException(String message, Throwable t) {
		super(message, t);
	}

	public AppException(Throwable t) {
		super(t);
	}

}
