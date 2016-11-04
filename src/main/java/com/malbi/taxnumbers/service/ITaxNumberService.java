package com.malbi.taxnumbers.service;

import org.jvnet.hk2.annotations.Contract;

import com.malbi.taxnumbers.dao.ITaxNumberDAO;

/**
 * Interface for getting tax numbers
 *
 * @author Andrii Duplyk
 *
 */
@Contract
public interface ITaxNumberService {

	/**
	 * Main method for getting tax number
	 *
	 * @param firmokpo
	 * @param docnum
	 * @param docdate
	 * @return
	 */
	public String getTaxNumber(String firmokpo, String docnum, String docdate) throws Exception;

	/**
	 *
	 * @return
	 */
	public ITaxNumberDAO getTaxNumberDAO();

	/**
	 *
	 * @param taxNumberDAO
	 */
	public void setTaxNumberDAO(ITaxNumberDAO taxNumberDAO);

}
