package com.infinity.pusher.model;

/**
 * 
 * @author infinity labs
 *
 */
public class Response {

	private String code;
	private String message;
	private Object responseObj;

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public Object getResponseObj() {
		return responseObj;
	}

	public void setResponseObj(Object responseObj) {
		this.responseObj = responseObj;
	}

}
