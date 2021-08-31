package com.rabbitmq.rabbitmq_queues.entity;

public class ReceiveHistory {
	String queueName;
	String domainName;
	String message;
	
	public ReceiveHistory(String queueName, String domainName, String message) {
		this.queueName = queueName;
		this.domainName = domainName;
		this.message = message;
	}

	public String getQueueName() {
		return queueName;
	}

	public String getDomainName() {
		return domainName;
	}
	
	public String getMessage() {
		return message;
	}

	@Override
	public String toString() {
		return "ReceiveHistory [queueName=" + queueName + ", domainName=" + domainName + ", message=" + message + "]";
	}
	
}
