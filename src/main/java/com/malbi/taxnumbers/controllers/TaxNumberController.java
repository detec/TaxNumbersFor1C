package com.malbi.taxnumbers.controllers;

import java.io.Serializable;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.inject.Named;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;

import com.malbi.taxnumbers.processor.TaxXMLBuilder;

// http://docs.oracle.com/javaee/7/tutorial/jaxrs-advanced004.htm
// 31.4 Integrating JAX-RS with EJB Technology and CDI
// The beans must be proxyable, so the Employee class requires a nonprivate constructor with no parameters

@Named
@RequestScoped
@Produces(MediaType.APPLICATION_XML)
@Path("/taxinvoicenumber")
public class TaxNumberController implements Serializable {

	public TaxNumberController() {

	}

	// different types of params
	// https://www-01.ibm.com/support/knowledgecenter/SS7K4U_8.5.5/com.ibm.websphere.base.doc/ae/twbs_jaxrs_defresource_parmexchdata.html
	// @Path("?firmokpo={firmokpo}&docnum={docnum}&docdate={docdate}&vatcheck=y")
	// @GET
	// @Produces("application/xml")
	// public String getTaxNumberByParams(@QueryParam("firmokpo") String
	// firmokpo, @QueryParam("docnum") String docnum,
	// @QueryParam("docdate") String docdate) {
	//
	// String taxNumber = taxNumberService.getTaxNumber(firmokpo, docnum,
	// docdate);
	//
	// StringBuffer outputBuffer = new StringBuffer();
	// String exceptionString = this.taxNumberService.getExceptionString();
	//
	// if (!exceptionString.isEmpty()) {
	// outputBuffer.append(exceptionString);
	// }
	//
	// else {
	// outputBuffer.append("<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n" +
	// "<result>\n");
	// if (!taxNumber.isEmpty()) {
	// outputBuffer.append("<taxinvoicenumber number=\"" + taxNumber +
	// "\"></taxinvoicenumber> ");
	// }
	// outputBuffer.append("</result>");
	// }
	//
	// return outputBuffer.toString();
	// }

	@GET
	@Produces(MediaType.APPLICATION_XML)
	public String getTaxNumberByParams(@QueryParam("firmokpo") String firmokpo, @QueryParam("docnum") String docnum,
			@QueryParam("docdate") String docdate) {

		String result = taxXMLBuilder.getTaxNumberXML(firmokpo, docnum, docdate);
		return result;
	}

	@Inject
	private TaxXMLBuilder taxXMLBuilder;

	private static final long serialVersionUID = -2604171208172260144L;

}
