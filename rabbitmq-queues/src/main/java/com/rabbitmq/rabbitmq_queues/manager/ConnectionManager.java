package com.rabbitmq.rabbitmq_queues.manager;

import java.io.IOException;
import java.util.Map;
import java.util.Map.Entry;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.TimeoutException;

import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.rabbitmq_queues.exception.RabbitException;
import com.rabbitmq.rabbitmq_queues.rabbitmq.RabbitConnectionFactory;

public class ConnectionManager {
	
	private static Map<String, Connection> mapConnection = new ConcurrentHashMap<>();	 
	private static ConnectionManager instance;
	
	private ConnectionManager() {}
	
	public static synchronized ConnectionManager getInstance() {
		if(instance == null) {
			instance = new ConnectionManager();
		}
		return instance;
	}
	
	public void closeConnection(String domain) throws RabbitException {
		synchronized (instance) {
			Connection conn = mapConnection.get(domain);
			if (conn != null 
					&& conn.isOpen()) {
				try {
					conn.close();
					mapConnection.remove(domain);
				} catch (IOException e) {
					throw new RabbitException("Failed to close connection."
							+ " Details: "+ e.getMessage(), e);
				}
			}
		}		
	}
	
	public void closeAllConnections() throws RabbitException {
		synchronized (instance) {
			for(Entry<String, Connection> records : mapConnection.entrySet()) {
				closeConnection(records.getKey());
			}
		}		
	}
	
	public Connection getConnection(String domain) throws RabbitException {
		synchronized (instance) {
			Connection conn = mapConnection.get(domain);			
			if(conn == null || !conn.isOpen()) {
				conn = createNewConnection(domain);
				conn.setId(domain);
				mapConnection.put(domain, conn);
			}
			return conn;
		}		
	}
	
	public Connection createNewConnection() throws RabbitException {
		return createNewConnection(null);
	}

	private Connection createNewConnection(String name) throws RabbitException {
		ConnectionFactory factory = RabbitConnectionFactory.getConnectionFactory();
		try {
			if(name == null || name.isEmpty()) {
				return factory.newConnection();
			}
			return factory.newConnection(name);
		} catch (IOException | TimeoutException e) {
			throw new RabbitException("Failed to connect with the RabbitMq server.", e);
		}
	}

}
