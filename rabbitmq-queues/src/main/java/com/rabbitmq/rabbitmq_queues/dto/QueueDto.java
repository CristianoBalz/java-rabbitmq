package com.rabbitmq.rabbitmq_queues.dto;

import java.util.Date;

public class QueueDto {	
	private CreateQueueDto createQueueDto;
	private Date created;	
	
	public QueueDto(CreateQueueDto createQueueDto, Date created) {
		this.createQueueDto = createQueueDto;
		this.created = created;
	}

	public CreateQueueDto getCreateQueueDto() {
		return createQueueDto;
	}
	
	public Date getCreated() {
		return created;
	}

	@Override
	public String toString() {
		return "QueueDto [createQueueDto=" + createQueueDto + ", created=" + created + "]";
	}	
	
}
