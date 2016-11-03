package com.malbi.taxnumbers.model;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

import org.hibernate.validator.constraints.NotEmpty;

/** This class is needed to apply Validator checks */
public class TaxNumberParameter {

	@NotNull
	@NotEmpty
	@Size(min = 8, max = 10)
	@Pattern(regexp = "[\\d]{8,10}")
	private String firmokpo;

	@NotNull
	@NotEmpty
	private String docnum;

	@NotNull
	@NotEmpty
	@Pattern(regexp = "[\\d]{2}\\.[\\d]{2}\\.20[\\d]{2}") // example 27.10.2015
	private String docdate;

	/**
	 *
	 * @param firmokpo
	 * @param docnum
	 * @param docdate
	 */
	public TaxNumberParameter(String firmokpo, String docnum, String docdate) {

		this.firmokpo = firmokpo;
		this.docnum = docnum;
		this.docdate = docdate;
	}

	public String getFirmokpo() {
		return firmokpo;
	}

	public void setFirmokpo(String firmokpo) {
		this.firmokpo = firmokpo;
	}

	public String getDocnum() {
		return docnum;
	}

	public void setDocnum(String docnum) {
		this.docnum = docnum;
	}

	public String getDocdate() {
		return docdate;
	}

	public void setDocdate(String docdate) {
		this.docdate = docdate;
	}

}
