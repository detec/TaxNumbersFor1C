package com.malbi.taxnumbers.service;

import java.io.Serializable;
import java.util.Set;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.inject.Named;
import javax.naming.NamingException;
import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;

import org.jvnet.hk2.annotations.Service;

import com.malbi.taxnumbers.dao.ITaxNumberDAO;
import com.malbi.taxnumbers.model.TaxNumberParameter;

@Named("TaxNumberService")
@ApplicationScoped
@Service
public class TaxNumberService implements Serializable, ITaxNumberService {

	@Override
	public String getTaxNumber(String firmokpo, String docnum, String docdate) {
		TaxNumberParameter params = new TaxNumberParameter(firmokpo, docnum, docdate);

		StringBuffer outputBuffer = new StringBuffer();

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
			// this.exceptionString = validationMessage;
			outputBuffer.append(validationMessage);
		}

		String taxNumber = "";
		try {
			taxNumber = TaxNumberDAO.getTaxNumber(params);
		} catch (NamingException e) {
			outputBuffer.append("\n " + e.getMessage());
		}

		// Let's read exceptions from DAO
		String DAOException = TaxNumberDAO.getExceptionString();
		if (!DAOException.isEmpty()) {
			// this.exceptionString = DAOException;
			outputBuffer.append("\n " + DAOException);
		}

		// collect all errors into one string
		if (!outputBuffer.toString().isEmpty()) {
			this.exceptionString = outputBuffer.toString();
		}

		return taxNumber;
	}

	private String exceptionString = "";

	@Override
	public String getExceptionString() {
		return exceptionString;
	}

	@Override
	public void setExceptionString(String exceptionString) {
		this.exceptionString = exceptionString;
	}

	@Override
	public ITaxNumberDAO getTaxNumberDAO() {
		return TaxNumberDAO;
	}

	@Override
	public void setTaxNumberDAO(ITaxNumberDAO taxNumberDAO) {
		TaxNumberDAO = taxNumberDAO;
	}

	private static final long serialVersionUID = 5985218860122022636L;

	@Inject
	private ITaxNumberDAO TaxNumberDAO;

}
