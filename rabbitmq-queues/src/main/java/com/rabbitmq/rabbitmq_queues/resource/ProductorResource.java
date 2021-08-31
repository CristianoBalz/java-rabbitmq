package com.rabbitmq.rabbitmq_queues.resource;

import com.rabbitmq.rabbitmq_queues.business.ProductorBusiness;
import com.rabbitmq.rabbitmq_queues.utils.ResponseUtils;

import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

@Path("/productor")
public class ProductorResource {

	@Path("/start-sample")
	@GET
	@Produces(MediaType.TEXT_PLAIN)
	public Response sendSampleMessages() {
		ProductorBusiness bussines = ProductorBusiness.getInstance();		
		try {			
			return ResponseUtils.buildOkResponse(bussines.sendSampleMessages());		
		} catch (IllegalArgumentException e) {
			return ResponseUtils.buildIllegalArgumentResponse(e);
		}
	}
	
	@Path("/stop-sample")
	@GET
	@Produces(MediaType.TEXT_PLAIN)
	public Response stopSampleProducer() {
		ProductorBusiness bussines = ProductorBusiness.getInstance();		
		try {			
			return ResponseUtils.buildOkResponse(bussines.stopSampleProducer());		
		} catch (IllegalArgumentException e) {
			return ResponseUtils.buildIllegalArgumentResponse(e);
		}
	}
	
}
