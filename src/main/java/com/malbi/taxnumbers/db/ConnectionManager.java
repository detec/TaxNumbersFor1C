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
import javax.sql.DataSource;

import org.jvnet.hk2.annotations.Service;

/**
 * Class for managing connections, {@link IConnectionManager} implementation.
 *
 * @author Andrii Duplyk
 *
 */
@Named("ConnectionManager")
@ApplicationScoped
@Service
public class ConnectionManager implements Serializable, IConnectionManager {

	private static final long serialVersionUID = 1593353288174396962L;

	@Resource(name = "jdbc/oraDbProdDataSource")
	private DataSource dataSource;

	@Override
	public Connection getDBConnection() throws Exception {
		Connection con;

		if (dataSource == null) {
			// trying without injection, because it doesn't work with JAX-RS 2.0
			InitialContext initContext = new InitialContext();
			Context envContext = (Context) initContext.lookup("java:/comp/env");
			dataSource = (DataSource) envContext.lookup("jdbc/oraDbProdDataSource");
		}

		if (dataSource != null) {
			con = dataSource.getConnection();

			DatabaseMetaData dbmd = con.getMetaData();
			if (dbmd.supportsTransactionIsolationLevel(Connection.TRANSACTION_SERIALIZABLE)) {
				con.setTransactionIsolation(Connection.TRANSACTION_SERIALIZABLE);
			}
		} else {
			throw new SQLException("Datasource from Tomcat returned as null!");
		}
		return con;
	}

}