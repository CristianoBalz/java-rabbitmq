package com.rabbitmq.rabbitmq_queues.business;

import java.io.IOException;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.TimeoutException;
import java.util.function.Function;
import java.util.stream.Collectors;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.rabbitmq_queues.dto.CreateQueueDto;
import com.rabbitmq.rabbitmq_queues.exception.RabbitException;
import com.rabbitmq.rabbitmq_queues.manager.ConnectionManager;
import com.rabbitmq.rabbitmq_queues.manager.QueueManager;

public class ConsumerBusiness {
	
	private static ConsumerBusiness instance;	
	
	private Map<String, Channel> consumers = new ConcurrentHashMap<>();	
	
	public static synchronized ConsumerBusiness getInstance()  {
		if (instance == null) {
			instance = new ConsumerBusiness();
		}
		return instance;
	}
	
	public void startSampleConsumers() throws RabbitException {
		Map<String, CreateQueueDto> map = QueueManager.LIST_SAMPLE_QUEUES.stream()
				.collect(Collectors.toMap(CreateQueueDto::getQueueName, Function.identity()));		
		for(Map.Entry<String, CreateQueueDto> entry : map.entrySet())	
		{
			String domainName = entry.getValue().getDomain().getName();
			Connection conn = ConnectionManager.getInstance().getConnection(domainName);
			if(!consumers.containsKey(entry.getValue().getQueueName())) {
				consumers.put(entry.getValue().getQueueName(),QueueManager.receive(conn, entry.getValue()));
			}
		}		
	}
	
	public String stopConsumers() throws RabbitException {
		if(consumers == null || consumers.isEmpty()) {
			return "Consumers are not running";
		}
		
		for(Map.Entry<String, Channel> entry : consumers.entrySet()) {
			if(entry.getValue() == null) {
				continue;
			}
			
			try {
				entry.getValue().close();				
			} catch (IOException | TimeoutException e) {
				throw new RabbitException("Failed to close consumer '"+entry.getKey()+"'."
						+ " Details: "+ e.getMessage(), e);
			}
			
		}
		consumers.clear();
		ConnectionManager.getInstance().closeAllConnections();
		return "Stopped successfully";
	}
	

}
