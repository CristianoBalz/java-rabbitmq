package com.rabbitmq.rabbitmq_queues.resource;

import com.rabbitmq.rabbitmq_queues.business.ConsumerBusiness;
import com.rabbitmq.rabbitmq_queues.exception.RabbitException;
import com.rabbitmq.rabbitmq_queues.utils.ResponseUtils;

import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

@Path("/consumer")
public class ConsumerResource {

	@Path("/start-sample")
	@GET
	@Produces(MediaType.TEXT_PLAIN)
	public Response consumerSampleMessages() {
		try {
			ConsumerBusiness business = ConsumerBusiness.getInstance();		
			business.startSampleConsumers();
			return ResponseUtils.buildOkResponse("Ok");
		} catch (RabbitException e) {
			e.printStackTrace();
			return Response.serverError().entity(e.getMessage()).build();
		} catch (IllegalArgumentException e) {
			return ResponseUtils.buildIllegalArgumentResponse(e);
		}
	}
	
	@Path("/stop-sample")
	@GET
	@Produces(MediaType.TEXT_PLAIN)
	public Response stopSampleConsumers() {
		try {			
			ConsumerBusiness business = ConsumerBusiness.getInstance();		
			return ResponseUtils.buildOkResponse(business.stopConsumers());
		} catch (RabbitException e) {
			e.printStackTrace();
			return Response.serverError().entity(e.getMessage()).build();
		} catch (IllegalArgumentException e) {
			return ResponseUtils.buildIllegalArgumentResponse(e);
		}
	}
}
