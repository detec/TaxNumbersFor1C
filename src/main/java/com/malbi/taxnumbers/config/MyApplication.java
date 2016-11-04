package com.malbi.taxnumbers.config;

import javax.ws.rs.ApplicationPath;

import org.glassfish.jersey.server.ResourceConfig;
import org.glassfish.jersey.server.ServerProperties;

/**
 * Jersey configuration.
 *
 * @author Andrii Duplyk
 *
 */
@ApplicationPath("/services/")
public class MyApplication extends ResourceConfig {

	/**
	 * Default config constructor.
	 */
	public MyApplication() {
		packages("com.malbi.taxnumbers.controllers");

		// config classes
		packages("com.malbi.taxnumbers.config");

		packages("com.malbi.taxnumbers.exception");

		// we will try to use Jersey 2 HK injection component
		// without manual binding it does not work
		// There is an option to generate a file with map for interfaces and
		// implementations.
		// http://www.justinleegrant.com/?p=516
		// But the boilerplate code for this is huge and in Eclipse this tool
		// is not launched, only with maven plugin.
		register(new MyApplicationBinder());

		property(ServerProperties.APPLICATION_NAME, "Jersey 2 XML service for tax numbers");
	}

}
