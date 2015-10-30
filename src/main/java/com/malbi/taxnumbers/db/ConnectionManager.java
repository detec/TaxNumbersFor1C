package com.malbi.taxnumbers.db;

import java.io.Serializable;
import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.SQLException;

import javax.annotation.Resource;
import javax.enterprise.context.ApplicationScoped;
import javax.inject.Named;
import javax.sql.DataSource;

@Named("ConnectionManager")
@ApplicationScoped
public class ConnectionManager implements Serializable {

	public Connection getDBConnection() throws SQLException {
		Connection con = null;
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

	private static final long serialVersionUID = 1593353288174396962L;

	@Resource(name = "jdbc/oracle")
	private DataSource DataSource;
}