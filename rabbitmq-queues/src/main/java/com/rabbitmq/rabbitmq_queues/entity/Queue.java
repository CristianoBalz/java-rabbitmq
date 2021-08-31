package com.rabbitmq.rabbitmq_queues.entity;

public class Queue {
	private Integer cod;
	private String name;
		
	public Queue(Integer cod, String name) {
		this.cod = cod;
		this.name = name;
	}

	public Integer getCod() {
		return cod;
	}
	
	public String getName() {
		return name;
	}

	@Override
	public String toString() {
		return "Queue [cod=" + cod + ", queue=" + name + "]";
	}	
	
}
