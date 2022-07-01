package com.gluwa.sdk;

public class GluwaSDKException extends RuntimeException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	public GluwaSDKException() {
		super();
	}

	
	public GluwaSDKException(Exception ex) {
		super(ex);
	}
	
	public GluwaSDKException(String message) {
		super(message);
	}
}
