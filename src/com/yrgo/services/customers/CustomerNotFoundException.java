package com.yrgo.services.customers;

public class CustomerNotFoundException extends Exception {
	// this is just to stop the annoying warning in Eclipse.
	private static final long serialVersionUID = 1L;

	public CustomerNotFoundException(String erromessage) {
		super(erromessage);

	}

	public CustomerNotFoundException() {

	}
}
