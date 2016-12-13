package com.ychan.controller;

import javax.ws.rs.core.Response;

public interface BaseController {
  default public Response sendError() {
    return sendError(500, "Internal Server Error");
  }

  default public Response sendError(String message) {
    return sendError(500, message);
  }

  default public Response sendError(int statusCode, String message) {
    return Response.status(statusCode).entity(message).build();
  }
}
