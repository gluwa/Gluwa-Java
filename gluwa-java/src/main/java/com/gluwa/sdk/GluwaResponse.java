package com.gluwa.sdk;

import java.io.Serializable;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class GluwaResponse implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = -188235700342848177L;
	
	int code;
	String reason;
	String contentType;
	
	String body;
	List<Map<String, Object>> mapList;
	
	public int getCode() {
		return code;
	}
	public void setCode(int code) {
		this.code = code;
	}
	public String getReason() {
		return reason;
	}
	public void setReason(String reason) {
		this.reason = reason;
	}
	public String getBody() {
		return body;
	}
	public void setBody(String body) {
		this.body = body;
	}
	public String getContentType() {
		return contentType;
	}
	public void setContentType(String contentType) {
		this.contentType = contentType;
	}
	public List<Map<String, Object>> getMapList() {
		return mapList;
	}
	public void setMapList(List<Map<String, Object>> mapList) {
		this.mapList = mapList;
	}
	@Override
	public String toString() {
		return "GluwaResponse [code=" + code + ", reason=" + reason + ", contentType=" + contentType + ", body=" + body
				+ "]";
	}
	
	
}
