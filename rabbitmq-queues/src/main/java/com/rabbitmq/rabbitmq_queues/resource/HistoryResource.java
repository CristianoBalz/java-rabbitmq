package com.rabbitmq.rabbitmq_queues.resource;

import java.util.List;

import com.rabbitmq.rabbitmq_queues.dto.ReceiveHistoryDto;
import com.rabbitmq.rabbitmq_queues.entity.ReceiveHistory;
import com.rabbitmq.rabbitmq_queues.manager.ReceiveHistoryManager;
import com.rabbitmq.rabbitmq_queues.utils.ResponseUtils;

import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

@Path("/history")
public class HistoryResource {

	@Path("/receive")
	@Produces(MediaType.APPLICATION_JSON)
	@GET
	public Response getReceiveHistory() {
		List<ReceiveHistory> receiveHistoryList = ReceiveHistoryManager.getReceiveHistoryList();
		try {			
			return ResponseUtils.buildOkResponse(new ReceiveHistoryDto(receiveHistoryList.size(), receiveHistoryList));		
		} catch (IllegalArgumentException e) {
			return ResponseUtils.buildIllegalArgumentResponse(e);
		}
	}
	
	@Path("/clear")
	@Produces(MediaType.TEXT_PLAIN)
	@GET
	public Response clearReceiveHistory() {
		ReceiveHistoryManager.clearReceiveHistoryList();
		try {			
			return ResponseUtils.buildOkResponse("OK");		
		} catch (IllegalArgumentException e) {
			return ResponseUtils.buildIllegalArgumentResponse(e);
		}
	}
}
