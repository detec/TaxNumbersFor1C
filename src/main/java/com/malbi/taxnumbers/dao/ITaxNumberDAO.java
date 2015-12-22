package com.malbi.taxnumbers.dao;

import javax.naming.NamingException;

import com.malbi.taxnumbers.model.TaxNumberParameter;

public interface ITaxNumberDAO {
	public String getTaxNumber(TaxNumberParameter params) throws NamingException;

	public String getExceptionString();
}
