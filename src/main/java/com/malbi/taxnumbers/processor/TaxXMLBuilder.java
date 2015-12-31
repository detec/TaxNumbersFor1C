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

@Named("TaxXMLBuilder")
@ApplicationScoped
@Service
public class TaxXMLBuilder implements Serializable {

	public String getTaxNumberXML(String firmokpo, String docnum, String docdate) {
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

		// Taken from http://www.vogella.com/tutorials/JAXB/article.html

		// create JAXB context and instantiate marshaller
		// JAXBContext context = null;
		// It is too expensive to create it every time
		// Marshaller marshaller = null;
		//
		// try {
		// context = JAXBContext.newInstance(Result.class);
		// marshaller = context.createMarshaller();
		// marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT,
		// Boolean.TRUE);
		// marshaller.setProperty(Marshaller.JAXB_ENCODING, "UTF-8");
		// marshaller.marshal(response, outputBuffer);
		// } catch (JAXBException e1) {
		// outputBuffer.append("\n" + e1.getMessage());
		// }

		// http://stackoverflow.com/questions/31637729/jaxb-marshall-unmarshall?rq=1

		JAXB.marshal(response, outputBuffer);

		// http://stackoverflow.com/questions/18668569/how-to-change-jaxb-marshaller-line-separator

		// return outputBuffer.toString();

		String str = outputBuffer.toString();
		str = str.replace("\n", "\r\n");

		return str;
	}

	private static final long serialVersionUID = 3500992059905548393L;

	@Inject
	private ITaxNumberService taxNumberService;

	public ITaxNumberService getTaxNumberService() {
		return taxNumberService;
	}

	public void setTaxNumberService(ITaxNumberService taxNumberService) {
		this.taxNumberService = taxNumberService;
	}

}
