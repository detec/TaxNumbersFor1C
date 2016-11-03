package com.malbi.taxnumbers.service;

import java.io.Serializable;
import java.util.Set;
import java.util.stream.Collectors;

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

/**
 * Implementation of {@link ITaxNumberService}
 *
 * @author Andrii Duplyk
 *
 */
@Named("TaxNumberService")
@ApplicationScoped
@Service
public class TaxNumberService implements Serializable, ITaxNumberService {

	private static final long serialVersionUID = 5985218860122022636L;

	@Inject
	private ITaxNumberDAO taxNumberDAO;

	private String exceptionString = "";

	@Override
	public String getTaxNumber(String firmokpo, String docnum, String docdate) throws Exception {
		TaxNumberParameter params = new TaxNumberParameter(firmokpo, docnum, docdate);

		StringBuilder outputBuffer = new StringBuilder();

		// here we should validate parameters, that have been passed with query
		ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
		Validator validator = factory.getValidator();
		Set<ConstraintViolation<TaxNumberParameter>> constraintViolations = validator.validate(params);

		StringBuilder validationBuffer = new StringBuilder();
		// constraintViolations.stream().forEach(t -> {
		// validationBuffer.append(t.getMessage() + "\n");
		// });

		constraintViolations.stream().map(ConstraintViolation::getMessage).collect(Collectors.joining("\n"));

		String validationMessage = validationBuffer.toString();
		if (!validationMessage.isEmpty()) {

			outputBuffer.append(validationMessage);
		}

		String taxNumber = "";
		try {
			taxNumber = taxNumberDAO.getTaxNumber(params);
		} catch (NamingException e) {
			outputBuffer.append("\n " + e.getMessage());
		}

		// Let's read exceptions from DAO
		String daoException = taxNumberDAO.getExceptionString();
		if (!daoException.isEmpty()) {
			outputBuffer.append("\n " + daoException);
		}

		// collect all errors into one string
		if (!outputBuffer.toString().isEmpty()) {
			this.exceptionString = outputBuffer.toString();
		}

		return taxNumber;
	}

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
		return taxNumberDAO;
	}

	@Override
	public void setTaxNumberDAO(ITaxNumberDAO taxNumberDAO) {
		this.taxNumberDAO = taxNumberDAO;
	}

}
