package com.rabbitmq.rabbitmq_queues.dto;

import java.util.List;

import com.rabbitmq.rabbitmq_queues.entity.ReceiveHistory;

public class ReceiveHistoryDto {
	private int tot;
	private List<ReceiveHistory> list;
	
	public ReceiveHistoryDto(int tot, List<ReceiveHistory> list) {
		this.tot = tot;
		this.list = list;
	}

	public int getTot() {
		return tot;
	}
	
	public List<ReceiveHistory> getList() {
		return list;
	}
	
	@Override
	public String toString() {
		return "ReceiveHistoryDto [tot=" + tot + ", list=" + list + "]";
	}
	
	
}
