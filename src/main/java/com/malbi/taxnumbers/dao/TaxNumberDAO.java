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

import com.malbi.taxnumbers.db.ConnectionManager;
import com.malbi.taxnumbers.model.TaxNumberParameter;

/*This function accepts standard string values to execute Oracle query*/

@Named("TaxNumberDAO")
@ApplicationScoped
@Service
public class TaxNumberDAO implements Serializable, ITaxNumberDAO {

	@Override
	public String getTaxNumber(TaxNumberParameter params) throws NamingException {
		String queryString = String.format(sqlString, params.getFirmokpo(),
				"to_date('" + params.getDocdate() + "', 'dd.mm.yy')", params.getDocnum());

		// Connection conn = null;
		// Statement stmt = null;
		StringBuffer errorBuffer = new StringBuffer();

		String taxInvoiceNumber = "";
		// try {
		// conn = cm.getDBConnection();
		//
		// if (conn != null) {
		// stmt = conn.createStatement();
		//
		// ResultSet rs = stmt.executeQuery(queryString);
		// while (rs.next()) {
		// taxInvoiceNumber = rs.getString(1);
		// break;
		// }
		//
		// }
		// } catch (SQLException e) {
		// errorBuffer.append(e.getMessage());
		// } finally {
		// if (stmt != null) {
		// try {
		// stmt.close();
		// } catch (SQLException e) {
		// errorBuffer.append(e.getMessage());
		// }
		// }
		// if (conn != null) {
		// try {
		// conn.close();
		// } catch (SQLException e) {
		// errorBuffer.append(e.getMessage());
		// }
		// }
		// }

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

	public ConnectionManager getCm() {
		return cm;
	}

	public void setCm(ConnectionManager cm) {
		this.cm = cm;
	}

	private static final String sqlString = "select apps.xxa_om_tax_inv_pkg.GetSerialNumberTaxInv1c( " + "'%s', " // [ОКПО]
			+ "%s, " // [Дата]
			+ "'%s') " // [Ид документа])
			+ "from dual ";

	private static final long serialVersionUID = 4695183924489794739L;

	@Inject
	private ConnectionManager cm;

	private String exceptionString = "";

	@Override
	public String getExceptionString() {
		return exceptionString;
	}

	public void setExceptionString(String exceptionString) {
		this.exceptionString = exceptionString;
	}

}
