package com.rabbitmq.rabbitmq_queues.rabbitmq;

import com.rabbitmq.client.ConnectionFactory;


public class RabbitConnectionFactory {	
	private RabbitConnectionFactory() {}	
	
	public static ConnectionFactory getConnectionFactory() {		
		ConnectionFactory factory = new ConnectionFactory();
        factory.setHost(RabbitConnectionConstants.HOST);
        factory.setPort(RabbitConnectionConstants.PORT);
        factory.setUsername(RabbitConnectionConstants.USER);
        factory.setPassword(RabbitConnectionConstants.PASSWD);  
        return factory;
	}	
	
}
