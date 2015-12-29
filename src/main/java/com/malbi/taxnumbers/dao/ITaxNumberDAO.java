package com.malbi.taxnumbers.dao;

import javax.naming.NamingException;

import org.jvnet.hk2.annotations.Contract;

import com.malbi.taxnumbers.db.IConnectionManager;
import com.malbi.taxnumbers.db.SQLQueries;
import com.malbi.taxnumbers.model.TaxNumberParameter;

@Contract
public interface ITaxNumberDAO {
	public String getTaxNumber(TaxNumberParameter params) throws NamingException;

	public String getExceptionString();

	public void setCm(IConnectionManager cm);

	public IConnectionManager getCm();

	public SQLQueries getSqlQueries();

	public void setSqlQueries(SQLQueries sqlQueries);
}
