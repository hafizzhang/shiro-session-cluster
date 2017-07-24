package com.hafiz.www.exception;

/**
 * Desc:自定义异常类
 * Created by hafiz.zhang on 2016/8/27.
 */
public class CustomException extends Exception{
	private String message;
	
	public CustomException(String message) {
		super(message);
		this.message = message;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}
	
}
