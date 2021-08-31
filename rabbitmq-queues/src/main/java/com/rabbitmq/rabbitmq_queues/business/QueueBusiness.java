package com.rabbitmq.rabbitmq_queues.business;

import java.util.List;

import com.rabbitmq.rabbitmq_queues.dto.QueueDto;
import com.rabbitmq.rabbitmq_queues.dto.ResponseQueueDto;
import com.rabbitmq.rabbitmq_queues.exception.RabbitException;
import com.rabbitmq.rabbitmq_queues.manager.QueueManager;

public class QueueBusiness {
	
	public ResponseQueueDto createSampleQueues() throws RabbitException {
		List<QueueDto> listQueueDto = QueueManager.createListQueues(true, false, false, null);		
		return new ResponseQueueDto(listQueueDto.size(),listQueueDto);
	}
	
}
