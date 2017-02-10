package com.nflabs.controller;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.core.Response;

import org.springframework.stereotype.Component;

@Component
@Path("/error")
public class ErrorController {
	@GET
	public Response error() {
		return Response.status(400).entity("").build();
	}
}
