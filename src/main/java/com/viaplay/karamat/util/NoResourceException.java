package com.viaplay.karamat.util;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class NoResourceException extends RuntimeException {
	private static final long serialVersionUID = 1L;
	
	public NoResourceException(String msg){
		super(msg);
	}
	

}
