package com.rabbitmq.rabbitmq_queues.form;

public class QueueForm {
	private String domain;
	private String queue;
	private String message;

	public QueueForm() {
	}

	public QueueForm(String domain, String queue, String message) {
		this.domain = domain;
		this.queue = queue;
		this.message = message;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public String getQueue() {
		return queue;
	}

	public void setQueue(String queue) {
		this.queue = queue;
	}

	public String getDomain() {
		return domain;
	}

	public void setDomain(String domain) {
		this.domain = domain;
	}

	@Override
	public String toString() {
		return "SendMessageForm [domain=" + domain + ", message=" + message + ", queue=" + queue + "]";
	}
}
