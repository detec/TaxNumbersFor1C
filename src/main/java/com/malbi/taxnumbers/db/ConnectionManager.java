package com.malbi.taxnumbers.db;

import java.io.Serializable;
import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.SQLException;

import javax.annotation.Resource;
import javax.enterprise.context.ApplicationScoped;
import javax.inject.Named;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;

import org.jvnet.hk2.annotations.Service;

@Named("ConnectionManager")
@ApplicationScoped
@Service
public class ConnectionManager implements Serializable, IConnectionManager {

	@Override
	public Connection getDBConnection() throws SQLException, NamingException {
		Connection con = null;

		if (DataSource == null) {
			// trying without injection, because it doesn't work with JAX-RS 2.0
			InitialContext initContext = new InitialContext();
			Context envContext = (Context) initContext.lookup("java:/comp/env");
			DataSource = (DataSource) envContext.lookup("jdbc/oraDbProdDataSource");
		}

		if (DataSource != null) {
			con = DataSource.getConnection();

			DatabaseMetaData dbmd = con.getMetaData();
			if (dbmd.supportsTransactionIsolationLevel(Connection.TRANSACTION_SERIALIZABLE)) {
				con.setTransactionIsolation(Connection.TRANSACTION_SERIALIZABLE);
			}
		} else {
			throw new SQLException("Datasource from Tomcat returned as null!");
		}
		return con;
	}

	// @Override
	// public DataSource getDataSource() {
	// return DataSource;
	// }
	//
	// @Override
	// public void setDataSource(DataSource dataSource) {
	// DataSource = dataSource;
	// }

	private static final long serialVersionUID = 1593353288174396962L;

	// Agreed to change datasource name
	// @Resource(name = "jdbc/oracle")
	@Resource(name = "jdbc/oraDbProdDataSource")
	private DataSource DataSource;
}