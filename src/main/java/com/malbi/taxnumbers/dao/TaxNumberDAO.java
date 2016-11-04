package com.malbi.taxnumbers.dao;

import java.io.Serializable;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.inject.Named;

import org.jvnet.hk2.annotations.Service;

import com.malbi.taxnumbers.config.CustomMarshaller;
import com.malbi.taxnumbers.db.IConnectionManager;
import com.malbi.taxnumbers.db.SQLQueries;
import com.malbi.taxnumbers.model.TaxNumberParameter;

/**
 * This class accepts standard string values to execute Oracle query
 *
 */

@Named("TaxNumberDAO")
@ApplicationScoped
@Service
public class TaxNumberDAO implements Serializable, ITaxNumberDAO {

	private static final long serialVersionUID = 4695183924489794739L;

	private static final Logger LOGGER = Logger.getLogger(CustomMarshaller.class.getName());

	@Inject
	private IConnectionManager cm;

	@Inject
	private SQLQueries sqlQueries;

	@Override
	public String getTaxNumber(TaxNumberParameter params) {
		String storedQuery = sqlQueries.getProperty().getProperty("tax.number.select");

		String queryString = String.format(storedQuery, params.getFirmokpo(), params.getDocdate(), params.getDocnum());

		StringBuilder errorBuffer = new StringBuilder();

		String taxInvoiceNumber = "";

		// 22.12.2015, Andrei Duplik. Let's use try with resources and close
		// resultset
		try (Connection conn = cm.getDBConnection();
				Statement stmt = conn.createStatement();
				ResultSet rs = stmt.executeQuery(queryString);) {
			if (rs.next()) {
				taxInvoiceNumber = rs.getString(1);
			}

		} catch (Exception e) {
			String errMsg = "Error getting tax number: " + e.getMessage();
			LOGGER.log(Level.SEVERE, errMsg, e);
			errorBuffer.append(e.getMessage());

			throw new IllegalStateException(errMsg);
		}

		return taxInvoiceNumber;
	}

	@Override
	public IConnectionManager getCm() {
		return cm;
	}

	@Override
	public void setCm(IConnectionManager cm) {
		this.cm = cm;
	}

	@Override
	public SQLQueries getSqlQueries() {
		return sqlQueries;
	}

	@Override
	public void setSqlQueries(SQLQueries sqlQueries) {
		this.sqlQueries = sqlQueries;
	}

}
