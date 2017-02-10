package com.ychan.controller;

import javax.ws.rs.core.Response;

public interface BaseController {
  default public Response sendError() {
    return sendError(500, "Internal Server Error");
  }

  default public Response sendError(final String errorMessage) {
    return sendError(500, errorMessage);
  }

  default public Response sendError(final int statusCode, final String errorMessage) {
    final String message = "{\"error\":\"".concat(errorMessage).concat("\"}");
    return Response.status(statusCode).entity(message).build();
  }
}
