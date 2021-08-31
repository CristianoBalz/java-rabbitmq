package com.rabbitmq.rabbitmq_queues.business;

import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.Map;
import java.util.Random;
import java.util.function.Function;
import java.util.stream.Collectors;

import com.rabbitmq.rabbitmq_queues.dto.CreateQueueDto;
import com.rabbitmq.rabbitmq_queues.exception.RabbitException;
import com.rabbitmq.rabbitmq_queues.form.QueueForm;
import com.rabbitmq.rabbitmq_queues.manager.QueueConstants;
import com.rabbitmq.rabbitmq_queues.manager.QueueManager;
import com.rabbitmq.rabbitmq_queues.utils.StringUtils;

public class ProductorBusiness {

	private static ProductorBusiness instance;

	private Thread sampleProducer;

	private ProductorBusiness() {
	}

	public static synchronized ProductorBusiness getInstance() {
		if (instance == null) {
			instance = new ProductorBusiness();
		}
		return instance;
	}

	public void sendMessage(QueueForm sendMessageForm) {
		validateForm(sendMessageForm);

	}

	public String sendSampleMessages() {
		if(sampleProducer != null && sampleProducer.isAlive()) {
			return "Producer is already running";
		}	
		
		sampleProducer = new Thread(() -> {
			Map<String, CreateQueueDto> map = QueueManager.LIST_SAMPLE_QUEUES.stream()
					.collect(Collectors.toMap(CreateQueueDto::getQueueName, Function.identity()));

			Random random = getRandom();
			try {
				while (!Thread.interrupted()) {
					if (random == null) {
						break;
					}
					String queueName = getQueue(random);
					if (map.containsKey(queueName)) {
						String message = createMessage(random);
						try {
							QueueManager.send(map.get(queueName), message);
						} catch (RabbitException e) {
							e.printStackTrace();							
						}
					}
					Thread.sleep(500);
				}
			} catch (InterruptedException e) {
				Thread.currentThread().interrupt();
			}
		});
		sampleProducer.start();
		return "Producer is running";
	}

	public String stopSampleProducer() {
		if (sampleProducer != null && sampleProducer.isAlive()) {
			sampleProducer.interrupt();
			return "Stopped successfully";
		}
		return "Producer is not running"; 
	}

	private Random getRandom() {
		Random random = null;
		try {
			random = SecureRandom.getInstanceStrong();
		} catch (NoSuchAlgorithmException e1) {
			e1.printStackTrace();
		}
		return random;
	}

	private void validateForm(QueueForm sendMessageForm) {
		if (sendMessageForm == null) {
			throw new IllegalArgumentException("Parameters not informed.");
		}

		if (StringUtils.isNullOrEmpty(sendMessageForm.getMessage())) {
			throw new IllegalArgumentException("Parameter \"message\" cannot be null.");
		}

		if (StringUtils.isNullOrEmpty(sendMessageForm.getQueue())) {
			throw new IllegalArgumentException("Parameter \"queue\" cannot be null.");
		}

	}

	private String createMessage(Random random) {
		StringBuilder value = new StringBuilder();
		value.append("Message Queue ");

		int qt = random.nextInt(8);
		if (qt == 0) {
			return value.append(".").toString();
		}

		for (int i = 0; i < qt; i++) {
			value.append(".");
		}
		return value.toString();
	}

	private String getQueue(Random random) {
		int codQueue = random.nextInt(QueueConstants.TOT_QUEUES);
		int codDomain = random.nextInt(QueueConstants.TOT_DOMAINS);
		return "Domain_" + codDomain + "_Queue_" + codQueue;
	}

}
