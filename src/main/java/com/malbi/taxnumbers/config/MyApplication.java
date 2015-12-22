package com.malbi.taxnumbers.config;

import javax.ws.rs.ApplicationPath;

import org.glassfish.jersey.server.ResourceConfig;
import org.glassfish.jersey.server.ServerProperties;

@ApplicationPath("/services/")
public class MyApplication extends ResourceConfig {

	public MyApplication() {
		packages("com.malbi.taxnumbers.controllers");
		// for dependency injection

		register(new MyApplicationBinder());

		property(ServerProperties.APPLICATION_NAME, "Jersey 2 XML service for tax numbers");
	}

}
