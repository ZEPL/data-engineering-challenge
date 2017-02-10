package com.nflabs.controller;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.nflabs.service.MaskService;

@Component
@Produces(MediaType.TEXT_PLAIN)
@Path("/mask")
public class MaskController {
	
	@Autowired
	private MaskService maskService;
	
	@GET
	public String getMaskedFields() {
		String maskedFields = maskService.getMaskedFieldNames();
		if(maskedFields == null) {
			return "";
		}
		
		return maskedFields;
	}
	
	@POST
	@Consumes(MediaType.TEXT_PLAIN)
	public String mask(String fieldNames) {
		if(fieldNames == null || fieldNames.isEmpty()) {
			return "";
		}

		maskService.mask(fieldNames);
		return maskService.getMaskedFieldNames();
	}
}
