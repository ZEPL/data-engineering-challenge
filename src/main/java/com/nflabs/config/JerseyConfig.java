package com.nflabs.config;

import org.glassfish.jersey.server.ResourceConfig;
import org.springframework.context.annotation.Configuration;

import com.nflabs.controller.ErrorController;
import com.nflabs.controller.LogController;
import com.nflabs.controller.MaskController;

@Configuration
public class JerseyConfig extends ResourceConfig {
	public JerseyConfig() {
		register(MaskController.class);
		register(LogController.class);
		register(ErrorController.class);
	}
}
