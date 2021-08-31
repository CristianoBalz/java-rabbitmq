package com.rabbitmq.rabbitmq_queues.dto;

import com.rabbitmq.rabbitmq_queues.entity.Domain;
import com.rabbitmq.rabbitmq_queues.entity.Queue;

public class CreateQueueDto {
	private String queueName;
	private Domain domain;
	private Queue queue;

	public CreateQueueDto() {
	}

	public CreateQueueDto(Domain domain, Queue queue, String queueName) {
		this.domain = domain;
		this.queue = queue;
		this.queueName = queueName;
	}

	public Queue getQueue() {
		return queue;
	}

	public void setQueue(Queue queue) {
		this.queue = queue;
	}

	public Domain getDomain() {
		return domain;
	}

	public void setDomain(Domain domain) {
		this.domain = domain;
	}
	
	public String getQueueName() {
		return queueName;
	}

	public void setQueueName(String queueName) {
		this.queueName = queueName;
	}

	@Override
	public String toString() {
		return "CreateQueueDto [queueName=" + queueName + ", domain=" + domain + ", queue=" + queue + "]";
	}
}
