package com.appNgeek.dto_entity_auto_rest_api.exception;

public class BlogAppException extends Exception {

	private static final long serialVersionUID = -6561747157347815458L;

	public BlogAppException(String message) {
		super(message);
	}

	public BlogAppException(String message, Throwable ex) {
		super(message, ex);
	}

}
