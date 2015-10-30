package com.malbi.taxnumbers.dao;

import com.malbi.taxnumbers.model.TaxNumberParameter;

public interface ITaxNumberDAO {
	public String getTaxNumber(TaxNumberParameter params);

	public String getExceptionString();
}
