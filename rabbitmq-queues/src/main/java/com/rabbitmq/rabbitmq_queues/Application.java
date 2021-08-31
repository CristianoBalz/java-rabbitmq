package com.rabbitmq.rabbitmq_queues;

import java.io.IOException;
import java.net.URI;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.glassfish.grizzly.http.server.HttpServer;
import org.glassfish.jersey.grizzly2.httpserver.GrizzlyHttpServerFactory;
import org.glassfish.jersey.server.ResourceConfig;

import com.rabbitmq.rabbitmq_queues.resource.ConsumerResource;
import com.rabbitmq.rabbitmq_queues.resource.HistoryResource;
import com.rabbitmq.rabbitmq_queues.resource.ProductorResource;
import com.rabbitmq.rabbitmq_queues.resource.QueueResource;

public class Application
{	 
	public static final URI BASE_URI = URI.create("http://localhost:8080/rabbitmq-queues/rest");
	
	public static HttpServer startHttpServer() {
		final ResourceConfig config = new ResourceConfig();
		config.register(QueueResource.class);
		config.register(ProductorResource.class);
		config.register(ConsumerResource.class);
		config.register(HistoryResource.class);
		
		return GrizzlyHttpServerFactory.createHttpServer(BASE_URI, config);
	}

	public static void main(String[] args) {
		try {
			final HttpServer server = startHttpServer();
			server.start();
			Runtime.getRuntime().addShutdownHook(new Thread(server::shutdownNow));
			System.out.println(String.format("Application started.%nStop the application using CTRL+C"));
			Thread.currentThread().join();
		} catch (InterruptedException | IOException ex) {
			Logger.getLogger(Application.class.getName()).log(Level.SEVERE, null, ex);
		}
	}
}