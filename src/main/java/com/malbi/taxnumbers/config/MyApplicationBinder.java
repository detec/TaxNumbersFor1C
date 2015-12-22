package com.malbi.taxnumbers.config;

import javax.sql.DataSource;

import org.glassfish.hk2.utilities.binding.AbstractBinder;

import com.malbi.taxnumbers.dao.ITaxNumberDAO;
import com.malbi.taxnumbers.dao.TaxNumberDAO;
import com.malbi.taxnumbers.db.ConnectionManager;
import com.malbi.taxnumbers.processor.TaxXMLBuilder;
import com.malbi.taxnumbers.service.ITaxNumberService;
import com.malbi.taxnumbers.service.TaxNumberService;

// http://stackoverflow.com/questions/16216759/dependency-injection-with-jersey-2-0
// http://blog.denevell.org/java-jersey-dependency-injection.html

public class MyApplicationBinder extends AbstractBinder {

	@Override
	protected void configure() {
		// bind(MyService.class).to(MyService.class);
		// bind(AddListModelImpl.class).to(AddListModel.class);

		bind(TaxNumberService.class).to(ITaxNumberService.class);
		bind(TaxNumberDAO.class).to(ITaxNumberDAO.class);
		bind(ConnectionManager.class).to(ConnectionManager.class);
		bind(DataSource.class).to(DataSource.class);
		bind(TaxXMLBuilder.class).to(TaxXMLBuilder.class);

	}

}
