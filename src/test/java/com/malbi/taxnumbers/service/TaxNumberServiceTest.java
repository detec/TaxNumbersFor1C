package com.malbi.taxnumbers.service;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.runners.MockitoJUnitRunner;

import com.malbi.taxnumbers.dao.TaxNumberDAO;
import com.malbi.taxnumbers.db.ConnectionManager;
import com.malbi.taxnumbers.model.TaxNumberParameter;

@RunWith(MockitoJUnitRunner.class)
public class TaxNumberServiceTest {

	@Mock
	private TaxNumberDAO TaxNumberDAO;

	@Before
	public void setUp() {
		ConnectionManager cm = Mockito.mock(ConnectionManager.class);
		TaxNumberDAO.setCm(cm);
	}

	@Test
	public void getTaxNumberTest() {
		// Let's test, that getTaxNumber method in TaxNumberDAO can be called
		TaxNumberParameter params = new TaxNumberParameter("48534290", "00006", "27.10.2015");
		String taxNumber = TaxNumberDAO.getTaxNumber(params);

		// verify(TaxNumberDAO).getTaxNumber(params);

	}
}
