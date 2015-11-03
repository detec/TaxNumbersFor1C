package com.malbi.taxnumbers.model;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;

import org.junit.BeforeClass;
import org.junit.Test;

public class TaxNumberParameterTest {

	private static Validator validator;

	private static final String EMPTY = "may not be empty";

	private static final String NULLMSG = "may not be null";

	private static final String SIZEMSG = "size must be between 8 and 10";

	private static final String REGEXMSG810 = "must match \"[\\d]{8,10}\"";

	private static final String REGEXMSGDATE = "must match \"[\\d]{2}\\.[\\d]{2}\\.20[\\d]{2}\"";

	// public TaxNumberParameter(String firmokpo, String docnum, String docdate)

	@BeforeClass
	public static void setUpValidator() {
		ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
		validator = factory.getValidator();
	}

	// We need to look for messages in array because sequence of violation
	// messages changes from compilation to compilation.

	// private List<String> getMessagesList() {
	// List<String> valuesmap = new ArrayList<String>();
	// valuesmap.add(EMPTY);
	// valuesmap.add(REGEXMSG810);
	// valuesmap.add(REGEXMSGDATE);
	// valuesmap.add(SIZEMSG);
	// valuesmap.add(NULLMSG);
	//
	// return valuesmap;
	// }

	private void loopIterator(Iterator<ConstraintViolation<TaxNumberParameter>> Iterator,
			ConstraintViolation<TaxNumberParameter> violation, List<String> valuesmap) {
		while (Iterator.hasNext()) {
			violation = Iterator.next();
			assertTrue(valuesmap.contains(violation.getMessage()));
		}
	}

	@Test
	public void firmokpoShouldNotBeNull() {
		TaxNumberParameter par = new TaxNumberParameter(null, "00006", "01.01.2015");

		Set<ConstraintViolation<TaxNumberParameter>> constraintViolations = validator.validate(par);
		Iterator<ConstraintViolation<TaxNumberParameter>> Iterator = constraintViolations.iterator();

		assertEquals(2, constraintViolations.size());

		ConstraintViolation<TaxNumberParameter> violation = null;

		List<String> valuesmap = new ArrayList<String>();
		valuesmap.add(NULLMSG);
		valuesmap.add(EMPTY);

		loopIterator(Iterator, violation, valuesmap);

		// violation = Iterator.next();
		// assertEquals(NULLMSG, violation.getMessage());
		//
		// violation = Iterator.next();
		// assertEquals(EMPTY, violation.getMessage());
	}

	@Test
	public void firmokpoShouldNotBeEmpty() {
		TaxNumberParameter par = new TaxNumberParameter("", "00006", "27.10.2015");

		Set<ConstraintViolation<TaxNumberParameter>> constraintViolations = validator.validate(par);
		Iterator<ConstraintViolation<TaxNumberParameter>> Iterator = constraintViolations.iterator();
		assertEquals(3, constraintViolations.size());

		ConstraintViolation<TaxNumberParameter> violation = null;

		List<String> valuesmap = new ArrayList<String>();
		valuesmap.add(REGEXMSG810);
		valuesmap.add(SIZEMSG);
		valuesmap.add(EMPTY);

		loopIterator(Iterator, violation, valuesmap);

		// violation = Iterator.next();
		// assertEquals(REGEXMSG810, violation.getMessage());
		//
		// violation = Iterator.next();
		// assertEquals(SIZEMSG, violation.getMessage());
		//
		// violation = Iterator.next();
		// assertEquals(EMPTY, violation.getMessage());
	}

	@Test
	public void docnumShouldNotBeNull() {
		TaxNumberParameter par = new TaxNumberParameter("48534290", null, "01.12.2000");
		Set<ConstraintViolation<TaxNumberParameter>> constraintViolations = validator.validate(par);
		Iterator<ConstraintViolation<TaxNumberParameter>> Iterator = constraintViolations.iterator();

		assertEquals(2, constraintViolations.size());

		ConstraintViolation<TaxNumberParameter> violation = null;

		List<String> valuesmap = new ArrayList<String>();
		valuesmap.add(EMPTY);
		valuesmap.add(NULLMSG);

		loopIterator(Iterator, violation, valuesmap);

		// violation = Iterator.next();
		// assertEquals(EMPTY, violation.getMessage());
		//
		// violation = Iterator.next();
		// assertEquals(NULLMSG, violation.getMessage());

	}

	@Test
	public void docnumShouldNotBeEmpty() {
		TaxNumberParameter par = new TaxNumberParameter("48534290", "", "01.01.2015");
		Set<ConstraintViolation<TaxNumberParameter>> constraintViolations = validator.validate(par);

		assertEquals(1, constraintViolations.size());
		assertEquals(EMPTY, constraintViolations.iterator().next().getMessage());

	}

	@Test
	public void docdateShouldNotBeNull() {
		TaxNumberParameter par = new TaxNumberParameter("48534290", "00006", null);
		Set<ConstraintViolation<TaxNumberParameter>> constraintViolations = validator.validate(par);
		Iterator<ConstraintViolation<TaxNumberParameter>> Iterator = constraintViolations.iterator();

		assertEquals(2, constraintViolations.size());

		ConstraintViolation<TaxNumberParameter> violation = null;

		List<String> valuesmap = new ArrayList<String>();
		valuesmap.add(EMPTY);
		valuesmap.add(NULLMSG);

		loopIterator(Iterator, violation, valuesmap);

		// violation = Iterator.next();
		// assertEquals(NULLMSG, violation.getMessage());
		//
		// violation = Iterator.next();
		// assertEquals(EMPTY, violation.getMessage());

	}

	@Test
	public void docdateShouldNotBeEmpty() {
		TaxNumberParameter par = new TaxNumberParameter("48534290", "00006", "");
		Set<ConstraintViolation<TaxNumberParameter>> constraintViolations = validator.validate(par);
		Iterator<ConstraintViolation<TaxNumberParameter>> Iterator = constraintViolations.iterator();

		assertEquals(2, constraintViolations.size());

		ConstraintViolation<TaxNumberParameter> violation = null;

		List<String> valuesmap = new ArrayList<String>();

		valuesmap.add(REGEXMSGDATE);
		valuesmap.add(EMPTY);
		loopIterator(Iterator, violation, valuesmap);

		// violation = Iterator.next();
		// assertEquals(REGEXMSGDATE, violation.getMessage());
		//
		// violation = Iterator.next();
		// assertEquals(EMPTY, violation.getMessage());

	}

	@Test
	public void firmokpoShouldComplyWithSizeShorter() {
		TaxNumberParameter par = new TaxNumberParameter("48534", "00006", "02.01.2015");
		Set<ConstraintViolation<TaxNumberParameter>> constraintViolations = validator.validate(par);
		Iterator<ConstraintViolation<TaxNumberParameter>> Iterator = constraintViolations.iterator();

		assertEquals(2, constraintViolations.size());

		ConstraintViolation<TaxNumberParameter> violation = null;

		List<String> valuesmap = new ArrayList<String>();
		valuesmap.add(SIZEMSG);
		valuesmap.add(REGEXMSG810);
		loopIterator(Iterator, violation, valuesmap);

		// violation = Iterator.next();
		// assertEquals(SIZEMSG, violation.getMessage());
		//
		// violation = Iterator.next();
		// assertEquals(REGEXMSG810, violation.getMessage());

	}

	@Test
	public void firmokpoShouldComplyWithSizeLonger() {
		TaxNumberParameter par = new TaxNumberParameter("48534239967", "00006", "02.01.2015");
		Set<ConstraintViolation<TaxNumberParameter>> constraintViolations = validator.validate(par);
		Iterator<ConstraintViolation<TaxNumberParameter>> Iterator = constraintViolations.iterator();

		assertEquals(2, constraintViolations.size());

		ConstraintViolation<TaxNumberParameter> violation = null;

		List<String> valuesmap = new ArrayList<String>();
		valuesmap.add(REGEXMSG810);
		valuesmap.add(SIZEMSG);

		loopIterator(Iterator, violation, valuesmap);

		// violation = Iterator.next();
		// assertEquals(REGEXMSG810, violation.getMessage());
		//
		// violation = Iterator.next();
		// assertEquals(SIZEMSG, violation.getMessage());

	}

	@Test
	public void firmokpoShouldContainOnlyDigits() {
		TaxNumberParameter par = new TaxNumberParameter("sd843476", "00006", "02.01.2015");
		Set<ConstraintViolation<TaxNumberParameter>> constraintViolations = validator.validate(par);
		Iterator<ConstraintViolation<TaxNumberParameter>> Iterator = constraintViolations.iterator();

		assertEquals(1, constraintViolations.size());

		ConstraintViolation<TaxNumberParameter> violation = null;
		violation = Iterator.next();
		assertEquals(REGEXMSG810, violation.getMessage());

	}

	@Test
	public void validateRealExample() {
		TaxNumberParameter par = new TaxNumberParameter("32140004", "OШКМОк000000000304", "02.01.2015");

		Set<ConstraintViolation<TaxNumberParameter>> constraintViolations = validator.validate(par);
		Iterator<ConstraintViolation<TaxNumberParameter>> Iterator = constraintViolations.iterator();

		assertEquals(0, constraintViolations.size());
	}
}
