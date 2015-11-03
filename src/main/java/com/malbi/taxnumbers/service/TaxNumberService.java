package com.malbi.taxnumbers.service;

import java.io.Serializable;
import java.util.Set;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.inject.Named;
import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;

import com.malbi.taxnumbers.dao.ITaxNumberDAO;
import com.malbi.taxnumbers.model.TaxNumberParameter;

@Named("TaxNumberService")
@ApplicationScoped
public class TaxNumberService implements Serializable {

	public String getTaxNumber(String firmokpo, String docnum, String docdate) {
		TaxNumberParameter params = new TaxNumberParameter(firmokpo, docnum, docdate);

		// here we should validate parameters, that have been passed with query
		ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
		Validator validator = factory.getValidator();
		Set<ConstraintViolation<TaxNumberParameter>> constraintViolations = validator.validate(params);

		StringBuffer validationBuffer = new StringBuffer();
		constraintViolations.stream().forEach(t -> {
			validationBuffer.append(t.getMessage() + "\n");
		});

		// 03.11.2015, Andrei Duplik, it is strange but validation falls on
		// database where everything was OK.
		String validationMessage = validationBuffer.toString();
		if (!validationMessage.isEmpty()) {
			// throw new Exception(validationMessage);
			this.exceptionString = validationMessage;
		}

		String taxNumber = TaxNumberDAO.getTaxNumber(params);

		// Let's read exceptions from DAO
		String DAOException = TaxNumberDAO.getExceptionString();
		if (!DAOException.isEmpty()) {
			this.exceptionString = DAOException;
		}

		return taxNumber;
	}

	private String exceptionString = "";

	public String getExceptionString() {
		return exceptionString;
	}

	public void setExceptionString(String exceptionString) {
		this.exceptionString = exceptionString;
	}

	private static final long serialVersionUID = 5985218860122022636L;

	@Inject
	private ITaxNumberDAO TaxNumberDAO;

}
