package com.malbi.taxnumbers.service;

import org.jvnet.hk2.annotations.Contract;

@Contract
public interface ITaxNumberService {

	public String getTaxNumber(String firmokpo, String docnum, String docdate);

	public String getExceptionString();

	public void setExceptionString(String exceptionString);

}
