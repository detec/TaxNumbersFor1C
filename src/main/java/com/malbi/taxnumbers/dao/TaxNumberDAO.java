package com.malbi.taxnumbers.dao;

import java.io.Serializable;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.inject.Named;
import javax.naming.NamingException;

import org.jvnet.hk2.annotations.Service;

import com.malbi.taxnumbers.db.IConnectionManager;
import com.malbi.taxnumbers.db.SQLQueries;
import com.malbi.taxnumbers.model.TaxNumberParameter;

/*This function accepts standard string values to execute Oracle query*/

@Named("TaxNumberDAO")
@ApplicationScoped
@Service
public class TaxNumberDAO implements Serializable, ITaxNumberDAO {

	@Override
	public String getTaxNumber(TaxNumberParameter params) throws NamingException {
		String storedQuery = sqlQueries.getProperty().getProperty("tax.number.select");

		// String queryString = String.format(sqlString, params.getFirmokpo(),
		// "to_date('" + params.getDocdate() + "', 'dd.mm.yy')",
		// params.getDocnum());

		// That does not work as H2 does not support yet to_date function
		// String queryString = String.format(storedQuery, params.getFirmokpo(),
		// "to_date('" + params.getDocdate() + "', 'dd.mm.yy')",
		// params.getDocnum());

		String queryString = String.format(storedQuery, params.getFirmokpo(), params.getDocdate(), params.getDocnum());

		StringBuffer errorBuffer = new StringBuffer();

		String taxInvoiceNumber = "";

		// 22.12.2015, Andrei Duplik. Let's use try with resources and close
		// resultset
		try (Connection conn = cm.getDBConnection();
				Statement stmt = conn.createStatement();
				ResultSet rs = stmt.executeQuery(queryString);) {
			if (rs.next()) {
				taxInvoiceNumber = rs.getString(1);
			}

		} catch (SQLException e) {
			errorBuffer.append(e.getMessage());
		}

		String errorBufferString = errorBuffer.toString();
		if (!errorBufferString.isEmpty()) {
			this.exceptionString = errorBufferString;
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

	// private static final String sqlString = "select
	// apps.xxa_om_tax_inv_pkg.GetSerialNumberTaxInv1c( " + "'%s', " // [ОКПО]
	// + "%s, " // [Дата]
	// + "'%s') " // [Ид документа])
	// + "from dual ";

	private static final long serialVersionUID = 4695183924489794739L;

	@Inject
	private IConnectionManager cm;

	@Inject
	private SQLQueries sqlQueries;

	@Override
	public SQLQueries getSqlQueries() {
		return sqlQueries;
	}

	@Override
	public void setSqlQueries(SQLQueries sqlQueries) {
		this.sqlQueries = sqlQueries;
	}

	private String exceptionString = "";

	@Override
	public String getExceptionString() {
		return exceptionString;
	}

	public void setExceptionString(String exceptionString) {
		this.exceptionString = exceptionString;
	}

}
