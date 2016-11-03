package com.malbi.taxnumbers.db;

import java.sql.Connection;

/**
 * Gets connection.
 *
 * @author Andrii Duplyk
 *
 */
@FunctionalInterface
public interface IConnectionManager {

	/**
	 *
	 * @return DB Connection
	 * @throws Exception
	 */
	public Connection getDBConnection() throws Exception;
}
