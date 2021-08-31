package com.rabbitmq.rabbitmq_queues.entity;

public class Domain {
	private Integer cod;
	private String name;	
	
	public Domain(Integer cod, String name) {
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
		return "Domain [cod=" + cod + ", name=" + name + "]";
	}	
}
