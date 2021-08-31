package com.rabbitmq.rabbitmq_queues.resource;


import com.rabbitmq.rabbitmq_queues.business.QueueBusiness;
import com.rabbitmq.rabbitmq_queues.exception.RabbitException;
import com.rabbitmq.rabbitmq_queues.utils.ResponseUtils;

import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

@Path("/queue")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class QueueResource {
	
	@GET
	@Path("/create-sample")
	public Response createSampleQueues() {
		QueueBusiness bussines = new QueueBusiness();		
		try {
			 return ResponseUtils.buildOkResponse(bussines.createSampleQueues());
		} catch (RabbitException e) {
			e.printStackTrace();
			return Response.serverError().entity(e.getMessage()).build();
		} catch (IllegalArgumentException e) {
			return ResponseUtils.buildIllegalArgumentResponse(e);
		}
	}
}
