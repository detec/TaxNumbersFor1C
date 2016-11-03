package com.malbi.taxnumbers.processor;

import java.io.Serializable;
import java.io.StringWriter;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.inject.Named;
import javax.xml.bind.JAXB;

import org.jvnet.hk2.annotations.Service;

import com.malbi.taxnumbers.model.Result;
import com.malbi.taxnumbers.service.ITaxNumberService;

/**
 * Constructs XML template to return to clients.
 *
 * @author Andrii Duplyk
 *
 */
@Named("TaxXMLBuilder")
@ApplicationScoped
@Service
public class TaxXMLBuilder implements Serializable {

	private static final long serialVersionUID = 3500992059905548393L;

	StringWriter outputBuffer = new StringWriter();

	@Inject
	private ITaxNumberService taxNumberService;

	/**
	 *
	 * @param firmokpo
	 * @param docnum
	 * @param docdate
	 * @return
	 * @throws Exception
	 */
	public String getTaxNumberXML(String firmokpo, String docnum, String docdate) throws Exception {
		// String taxNumber = taxNumberService.getTaxNumber(firmokpo, docnum,
		// docdate);
		//
		// StringWriter outputBuffer = new StringWriter();
		// String exceptionString = this.taxNumberService.getExceptionString();
		//
		// if (!exceptionString.isEmpty()) {
		// outputBuffer.append(exceptionString);
		// }
		//
		// if (taxNumber.isEmpty()) {
		// outputBuffer.append("\n Налоговый номер не найден!");
		// taxNumber = outputBuffer.toString();
		// }
		//
		// // building response
		// Result.Taxinvoicenumber numberHolder = new Result.Taxinvoicenumber();
		// numberHolder.setNumber(taxNumber);
		// // probably we need empty value to have closing tag.
		// numberHolder.setValue("");
		//
		// // building root element
		// Result response = new Result();
		// response.setTaxinvoicenumber(numberHolder);

		Result response = getResultTaxInvoiceNumber(firmokpo, docnum, docdate);

		// http://stackoverflow.com/questions/31637729/jaxb-marshall-unmarshall?rq=1

		JAXB.marshal(response, outputBuffer);

		String str = outputBuffer.toString();
		str = str.replace("\n", "\r\n");

		return str;
	}

	/**
	 * Returns object instead of XML
	 *
	 * @param firmokpo
	 * @param docnum
	 * @param docdate
	 * @return
	 * @throws Exception
	 */
	public Result getTaxNumberObject(String firmokpo, String docnum, String docdate) throws Exception {
		return getResultTaxInvoiceNumber(firmokpo, docnum, docdate);
	}

	private Result getResultTaxInvoiceNumber(String firmokpo, String docnum, String docdate) throws Exception {

		String taxNumber = taxNumberService.getTaxNumber(firmokpo, docnum, docdate);

		StringWriter outputBuffer = new StringWriter();
		String exceptionString = this.taxNumberService.getExceptionString();

		if (!exceptionString.isEmpty()) {
			outputBuffer.append(exceptionString);
		}

		if (taxNumber.isEmpty()) {
			outputBuffer.append("\n Налоговый номер не найден!");
			taxNumber = outputBuffer.toString();
		}

		// building response
		Result.Taxinvoicenumber numberHolder = new Result.Taxinvoicenumber();
		numberHolder.setNumber(taxNumber);
		// probably we need empty value to have closing tag.
		numberHolder.setValue("");

		// building root element
		Result response = new Result();
		response.setTaxinvoicenumber(numberHolder);

		return response;
	}

	/**
	 *
	 * @return
	 */
	public ITaxNumberService getTaxNumberService() {
		return taxNumberService;
	}

	/**
	 *
	 * @param taxNumberService
	 */
	public void setTaxNumberService(ITaxNumberService taxNumberService) {
		this.taxNumberService = taxNumberService;
	}

}
