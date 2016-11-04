package com.malbi.taxnumbers.service;

import java.io.Serializable;
import java.util.Set;
import java.util.stream.Collectors;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.inject.Named;
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

	@Override
	public String getTaxNumber(String firmokpo, String docnum, String docdate) {
		TaxNumberParameter params = new TaxNumberParameter(firmokpo, docnum, docdate);

		StringBuilder outputBuffer = new StringBuilder();

		// here we should validate parameters, that have been passed with query
		ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
		Validator validator = factory.getValidator();
		Set<ConstraintViolation<TaxNumberParameter>> constraintViolations = validator.validate(params);

		StringBuilder validationBuffer = new StringBuilder();

		validationBuffer.append(
				constraintViolations.stream().map(ConstraintViolation::getMessage).collect(Collectors.joining("\n")));

		String validationMessage = validationBuffer.toString();
		if (!validationMessage.isEmpty()) {

			// outputBuffer.append(validationMessage);
			// Throwing Exception with contents.
			throw new IllegalArgumentException("Invalid input parameters: " + validationMessage);
		}

		String taxNumber = taxNumberDAO.getTaxNumber(params);

		return taxNumber;
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
