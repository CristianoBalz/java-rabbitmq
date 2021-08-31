package com.rabbitmq.rabbitmq_queues.manager;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeoutException;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import com.rabbitmq.client.BuiltinExchangeType;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.DeliverCallback;
import com.rabbitmq.client.MessageProperties;
import com.rabbitmq.rabbitmq_queues.dto.CreateQueueDto;
import com.rabbitmq.rabbitmq_queues.dto.QueueDto;
import com.rabbitmq.rabbitmq_queues.entity.Domain;
import com.rabbitmq.rabbitmq_queues.entity.Queue;
import com.rabbitmq.rabbitmq_queues.entity.ReceiveHistory;
import com.rabbitmq.rabbitmq_queues.exception.RabbitException;

public class QueueManager {

	private QueueManager() {
		
	}
	
	public static final List<CreateQueueDto> LIST_SAMPLE_QUEUES;
	
	static {
		LIST_SAMPLE_QUEUES = createSampleListQueues(); 
	}	


	public static QueueDto createSingleQueue(CreateQueueDto createQueueDto, boolean durable, boolean exclusive,
			boolean autoDelete, Map<String, Object> arguments) throws RabbitException {
		try (Connection connection = ConnectionManager.getInstance().createNewConnection(); 
				Channel channel = connection.createChannel()) {
			addQueue(durable, exclusive, autoDelete, arguments, channel, createQueueDto);

			return new QueueDto(createQueueDto, new Date());
		} catch (IOException | TimeoutException e) {
			throw new RabbitException("Unable to create queue.", e);
		}
	}

	public static List<QueueDto> createListQueues(boolean durable, boolean exclusive,
			boolean autoDelete, Map<String, Object> arguments) throws RabbitException {
		ArrayList<QueueDto> list = new ArrayList<>();

		try (Connection connection = ConnectionManager.getInstance().createNewConnection(); 
				Channel channel = connection.createChannel()) {
			for (CreateQueueDto createQueueDto : LIST_SAMPLE_QUEUES) {
				addQueue(durable, exclusive, autoDelete, arguments, channel, createQueueDto);
				list.add(new QueueDto(createQueueDto, new Date()));
			}
		} catch (IOException | TimeoutException e) {
			throw new RabbitException("Unable to create queues.", e);
		}
		return list;
	}

	public static void send(CreateQueueDto queue, String message) throws RabbitException {
		try (Connection connection = ConnectionManager.getInstance().createNewConnection(); Channel channel = connection.createChannel()) {
			System.out.println(" [x] Send '" + message + "' by Queue: " + queue.getQueueName());
			channel.basicPublish(queue.getDomain().getName(), queue.getQueueName(),
					MessageProperties.PERSISTENT_TEXT_PLAIN, message.getBytes());
			
		} catch (IOException | TimeoutException e) {
			throw new RabbitException("Failed to send message to queue '"+queue.getQueueName()+"'", e);
		}
	}

	public static Channel receive(Connection connection, CreateQueueDto queue) throws RabbitException {		
		try {
			Channel channel = connection.createChannel();			
			channel.basicQos(1); 
			
			DeliverCallback deliverCallback = (consumerTag, delivery) -> {
				String message = new String(delivery.getBody(), "UTF-8");
				try {
					doWork(message);
				} catch (InterruptedException e) {					
					e.printStackTrace();
				} finally {
					ReceiveHistoryManager.addReceiveHistoryList(new ReceiveHistory(queue.getQueueName(), queue.getDomain().getName(), message));
					channel.basicAck(delivery.getEnvelope().getDeliveryTag(), false);
				}
			};

			boolean autoAck = false;
			channel.basicConsume(queue.getQueueName(), autoAck, deliverCallback, consumerTag -> {
			});
			
			return channel;
		} catch (IOException e) {
			throw new RabbitException("Failed to receive message from queue '"+queue.getQueueName()+"'", e);
		}
	}

	private static List<CreateQueueDto> createSampleListQueues() {
		List<CreateQueueDto> list = new ArrayList<>();

		List<Domain> domains = IntStream.range(0, QueueConstants.TOT_DOMAINS).boxed()
				.map(i -> new Domain(i, "Domain_" + i)).collect(Collectors.toList());

		List<Queue> queues = IntStream.range(0, QueueConstants.TOT_QUEUES).boxed()
				.map(i -> new Queue(i, "Queue_" + i)).collect(Collectors.toList());

		for (Domain domain : domains) {
			for (Queue queue : queues) {
				list.add(new CreateQueueDto(domain, queue, domain.getName() + "_" + queue.getName()));
			}
		}
		return list;
	}

	private static void addQueue(boolean durable, boolean exclusive, boolean autoDelete, Map<String, Object> arguments,
			Channel channel, CreateQueueDto createQueueDto) throws IOException {
		channel.exchangeDeclare(createQueueDto.getDomain().getName(), BuiltinExchangeType.DIRECT);

		channel.queueDeclare(createQueueDto.getQueueName(), durable, exclusive, autoDelete, arguments);

		channel.queueBind(createQueueDto.getQueueName(), createQueueDto.getDomain().getName(),
				createQueueDto.getQueueName());
	}

	private static void doWork(String task) throws InterruptedException {
		for (char ch : task.toCharArray()) {
			if (ch == '.')
				Thread.sleep(1000);
		}
	}	
}
