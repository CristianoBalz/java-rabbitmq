package com.rabbitmq.rabbitmq_queues.dto;

import java.util.List;

public class ResponseQueueDto {
	private int tot;
	private List<QueueDto> list;
	
	public ResponseQueueDto(int tot, List<QueueDto> list) {
		this.tot = tot;
		this.list = list;
	}

	public int getTot() {
		return tot;
	}

	public List<QueueDto> getList() {
		return list;
	}

	@Override
	public String toString() {
		return "ResponseQueueDto [tot=" + tot + ", list=" + list + "]";
	}
	
	
}
