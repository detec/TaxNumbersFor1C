package com.malbi.taxnumbers.config;

import java.util.logging.Level;
import java.util.logging.Logger;

import javax.ws.rs.ext.ContextResolver;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;

import com.malbi.taxnumbers.model.Result;

/**
 * Try for JAXB context.
 *
 * @author Andrii Duplyk
 *
 */
public class CustomJaxbContext implements ContextResolver<JAXBContext> {

	private JAXBContext context = null;

	private static final Logger LOGGER = Logger.getLogger(CustomJaxbContext.class.getName());

	@Override
	public JAXBContext getContext(Class<?> type) {
		if (type != Result.class) {
			return null; // we don't support nothing else than Result
		}

		if (context == null) {
			try {
				context = JAXBContext.newInstance(Result.class);
			} catch (JAXBException e) {
				// log warning/error; null will be returned which indicates that
				// this
				// provider won't/can't be used.
				LOGGER.log(Level.SEVERE, "Error with JAXB context - " + e.getMessage(), e);
			}
		}
		return context;
	}

}
