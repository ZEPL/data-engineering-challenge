package com.nflabs.controller;

import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import com.nflabs.service.StorageService;

@Component
@Produces(MediaType.APPLICATION_JSON)
@Path("/log")
public class LogController {
	private static Logger log = LoggerFactory.getLogger(LogController.class);
	
	@Autowired
	@Qualifier("fileSystem")
	private StorageService storageService;
	
	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	public Response store(String json) {
		try {
			storageService.store(json);
		}
		catch(Exception e) {
			log.warn(e.getMessage(), e);
			return Response.status(499).entity(new Error(Result.Status.FAILS, e.getMessage())).build();
		}
		
		return Response.status(200).entity(new Result(Result.Status.SUCCESS)).build();
	}
}
