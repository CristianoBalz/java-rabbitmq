package com.rabbitmq.rabbitmq_queues.manager;

import java.util.ArrayList;
import java.util.List;

import com.rabbitmq.rabbitmq_queues.entity.ReceiveHistory;

public class ReceiveHistoryManager {
	
	private static List<ReceiveHistory> receiveHistoryList = new ArrayList<>();
	
	private ReceiveHistoryManager() {}
	
	public static synchronized List<ReceiveHistory> getReceiveHistoryList(){
		return receiveHistoryList;
	}
	
	public static synchronized void addReceiveHistoryList(ReceiveHistory rec) {
		receiveHistoryList.add(rec);
	}
	
	public static synchronized void clearReceiveHistoryList() {
		receiveHistoryList.clear();
	}
	
}
