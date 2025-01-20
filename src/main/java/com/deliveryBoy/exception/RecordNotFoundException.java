package com.deliveryBoy.exception;

import org.springframework.web.bind.annotation.ResponseStatus;

import org.springframework.http.HttpStatus;


@ResponseStatus(HttpStatus.BAD_REQUEST)
public class RecordNotFoundException extends Exception {

	
	private static final long serialVersionUID = 1L;

    public RecordNotFoundException() {
        super();
    }

    public RecordNotFoundException(final String message, final Throwable cause) {
        super(message, cause);
    }

    public RecordNotFoundException(final String message) {
        super(message);
    }

    public RecordNotFoundException(final Throwable cause) {
        super(cause);
    }	
	
	
}
