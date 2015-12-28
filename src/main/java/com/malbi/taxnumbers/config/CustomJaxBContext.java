package com.malbi.taxnumbers.config;

import javax.ws.rs.ext.ContextResolver;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;

import com.malbi.taxnumbers.model.Result;

//@Provider
// We create custom marshaller in CustomMarshaller
public class CustomJaxBContext implements ContextResolver<JAXBContext> {

	// http://stackoverflow.com/questions/18436782/specifying-jaxb-2-context-in-jersey-1-17

	// private final JAXBContext context;
	// private final Set<Class<?>> types;
	// private final Class<?>[] cTypes = { Result.class };
	//
	// public CustomJaxBContext() throws Exception {
	// this.types = new HashSet<Class<?>>(Arrays.asList(cTypes));
	// // this.context = new JettisonJaxbContext(JettisonConfig.DEFAULT,
	// // cTypes);
	// }
	//
	// @Override
	// public JAXBContext getContext(Class<?> objectType) {
	// return (types.contains(objectType)) ? context : null;
	// }

	private JAXBContext context = null;

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
			}
		}
		return context;
	}

}
