package com.malbi.taxnumbers.config;

import java.util.logging.Level;
import java.util.logging.Logger;

import javax.ws.rs.ext.ContextResolver;
import javax.ws.rs.ext.Provider;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;

import com.malbi.taxnumbers.model.Result;

@Provider
public class CustomMarshaller implements ContextResolver<Marshaller> {

	// http://stackoverflow.com/questions/18436782/specifying-jaxb-2-context-in-jersey-1-17

	private Logger LOGGER = Logger.getLogger(CustomMarshaller.class.getName());

	private JAXBContext context = null;

	private Marshaller marshaller = null;

	@Override
	public Marshaller getContext(Class<?> type) {
		if (type != Result.class) {
			return null; // we don't support nothing else than Result
		}

		if (marshaller == null) {

			if (context == null) {
				try {
					context = JAXBContext.newInstance(Result.class);
					marshaller = context.createMarshaller();
					marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, Boolean.TRUE);
					marshaller.setProperty(Marshaller.JAXB_ENCODING, "UTF-8");

				} catch (JAXBException e) {
					// log warning/error; null will be returned which indicates
					// that
					// this
					// provider won't/can't be used.
					LOGGER.log(Level.SEVERE, "CustomMarshaller - " + e.getMessage());
				}
			}
		}

		return marshaller;
	}

}
