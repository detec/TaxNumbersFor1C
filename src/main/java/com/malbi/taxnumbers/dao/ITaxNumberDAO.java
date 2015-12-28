package com.malbi.taxnumbers.dao;

import javax.naming.NamingException;

import org.jvnet.hk2.annotations.Contract;

import com.malbi.taxnumbers.model.TaxNumberParameter;

@Contract
public interface ITaxNumberDAO {
	public String getTaxNumber(TaxNumberParameter params) throws NamingException;

	public String getExceptionString();
}
