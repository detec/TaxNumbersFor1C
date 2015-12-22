package com.malbi.taxnumbers.service;

public interface ITaxNumberService {

	public String getTaxNumber(String firmokpo, String docnum, String docdate);

	public String getExceptionString();

	public void setExceptionString(String exceptionString);

}
