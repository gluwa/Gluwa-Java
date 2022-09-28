package com.gluwa.sdk;

import org.json.JSONObject;

public class GluwaSDKNetworkException extends GluwaSDKException {

	private static final long serialVersionUID = 1L;

	private int statusCode;
	private String body;

	public GluwaSDKNetworkException(int statusCode, String body) {
		super("A network exception occured with the GluwaAPI. (Status code: " + String.valueOf(statusCode) +  ")");

		this.statusCode = statusCode;
		this.body = body;
	}

	public int getStatusCode() {
		return statusCode;
	}

	public String getBody() {
		return body;
	}

	public Boolean isBadRequest() {
		return this.getStatusCode() == 300;
	}

	public String extractBadRequestMessage() {
		JSONObject badRequestJson = new JSONObject(this.getBody());
		String badRequestMessage = badRequestJson.getString("Message");
		return badRequestMessage;
	}

	public JSONObject getResponseContents() {
		JSONObject content = new JSONObject(this.getBody());
		return content;
	}
}