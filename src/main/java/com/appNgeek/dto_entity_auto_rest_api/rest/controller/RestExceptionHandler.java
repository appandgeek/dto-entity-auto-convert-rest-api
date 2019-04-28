package com.appNgeek.dto_entity_auto_rest_api.rest.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.appNgeek.dto_entity_auto_rest_api.exception.BlogAppException;

@RestControllerAdvice
public class RestExceptionHandler {

	@ExceptionHandler(value = {BlogAppException.class})
	@ResponseBody
	public ResponseEntity<String> blogError(BlogAppException ex) {
		return new ResponseEntity<String>(ex.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
	}
}