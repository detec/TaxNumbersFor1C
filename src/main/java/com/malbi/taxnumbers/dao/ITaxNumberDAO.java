package com.malbi.taxnumbers.dao;

import org.jvnet.hk2.annotations.Contract;

import com.malbi.taxnumbers.db.IConnectionManager;
import com.malbi.taxnumbers.db.SQLQueries;
import com.malbi.taxnumbers.model.TaxNumberParameter;

/**
 * DAO interface
 *
 * @author Andrii Duplyk
 *
 */
@Contract
public interface ITaxNumberDAO {

	/**
	 * Main method, getting tax number.
	 *
	 * @param params
	 * @return
	 * @throws Exception
	 */
	public String getTaxNumber(TaxNumberParameter params) throws Exception;

	/**
	 *
	 * @return
	 */
	public String getExceptionString();

	/**
	 *
	 * @param cm
	 */
	public void setCm(IConnectionManager cm);

	/**
	 *
	 * @return
	 */
	public IConnectionManager getCm();

	/**
	 * get queries
	 *
	 * @return
	 */
	public SQLQueries getSqlQueries();

	/**
	 *
	 * @param sqlQueries
	 */
	public void setSqlQueries(SQLQueries sqlQueries);
}
