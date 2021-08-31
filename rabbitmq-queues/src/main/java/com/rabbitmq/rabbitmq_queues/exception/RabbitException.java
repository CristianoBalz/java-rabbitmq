package com.rabbitmq.rabbitmq_queues.exception;

public class RabbitException extends Exception {	
	private static final long serialVersionUID = -3181269947857256505L;
	
	@SuppressWarnings("unused")
	private RabbitException() {}
	
	public RabbitException(String message, Throwable e) {
		super(message, e);
	}
}
