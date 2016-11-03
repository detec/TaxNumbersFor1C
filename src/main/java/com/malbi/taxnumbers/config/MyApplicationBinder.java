package com.malbi.taxnumbers.config;

import org.glassfish.hk2.utilities.binding.AbstractBinder;

import com.malbi.taxnumbers.dao.ITaxNumberDAO;
import com.malbi.taxnumbers.dao.TaxNumberDAO;
import com.malbi.taxnumbers.db.ConnectionManager;
import com.malbi.taxnumbers.db.IConnectionManager;
import com.malbi.taxnumbers.db.SQLQueries;
import com.malbi.taxnumbers.processor.TaxXMLBuilder;
import com.malbi.taxnumbers.service.ITaxNumberService;
import com.malbi.taxnumbers.service.TaxNumberService;

/**
 * Currently not used DI setup class
 * 
 * @author Andrii Duplyk
 *
 */
public class MyApplicationBinder extends AbstractBinder {

	@Override
	protected void configure() {

		bind(TaxNumberService.class).to(ITaxNumberService.class);
		bind(TaxNumberDAO.class).to(ITaxNumberDAO.class);
		bind(ConnectionManager.class).to(IConnectionManager.class);

		bind(TaxXMLBuilder.class).to(TaxXMLBuilder.class);
		bind(SQLQueries.class).to(SQLQueries.class);

	}

}
