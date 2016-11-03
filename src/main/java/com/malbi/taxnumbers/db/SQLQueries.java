package com.malbi.taxnumbers.db;

import java.io.IOException;
import java.io.InputStream;
import java.io.Serializable;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Named;

/**
 * Loading of properties.
 *
 * @author Andrii Duplyk
 *
 */
@Named("SQLQueries")
@ApplicationScoped
public class SQLQueries implements Serializable {

	private static final long serialVersionUID = 6628491051063758349L;

	private static final Logger LOGGER = Logger.getLogger(SQLQueries.class.getName());

	private Properties property = new Properties();

	public Properties getProperty() {
		if (property.isEmpty()) {
			loadProperties();
		}
		return property;
	}

	public void setProperty(Properties property) {
		this.property = property;
	}

	// http://stackoverflow.com/questions/8740234/postconstruct-checked-exceptions
	private void loadProperties() {

		// using try with resources
		try (InputStream in = getClass().getResourceAsStream("/SQLQueries.properties")) {
			property.load(in);
		} catch (IOException e) {
			LOGGER.log(Level.SEVERE, "Error reading properties - " + e.getMessage(), e);
		}

	}

}
