package com.malbi.taxnumbers.db;

import java.sql.Connection;
import java.sql.SQLException;

import javax.naming.NamingException;

public interface IConnectionManager {

	// public void setDataSource(DataSource dataSource);
	//
	// public DataSource getDataSource();

	public Connection getDBConnection() throws SQLException, NamingException;
}
