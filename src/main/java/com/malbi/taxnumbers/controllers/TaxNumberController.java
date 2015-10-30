package com.malbi.taxnumbers.controllers;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.inject.Named;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;

import com.malbi.taxnumbers.service.TaxNumberService;

@Named
@RequestScoped
@Path("/taxinvoicenumber")
public class TaxNumberController {

	// different types of params
	// https://www-01.ibm.com/support/knowledgecenter/SS7K4U_8.5.5/com.ibm.websphere.base.doc/ae/twbs_jaxrs_defresource_parmexchdata.html
	// @Path("?firmokpo={firmokpo}&docnum={docnum}&docdate={docdate}&vatcheck=y")
	@GET
	@Produces("application/xml")
	public String getTaxNumberByParams(@QueryParam("firmokpo") String firmokpo, @QueryParam("docnum") String docnum,
			@QueryParam("docdate") String docdate) {

		String taxNumber = taxNumberService.getTaxNumber(firmokpo, docnum, docdate);

		StringBuffer outputBuffer = new StringBuffer();
		String exceptionString = this.taxNumberService.getExceptionString();

		if (!exceptionString.isEmpty()) {
			outputBuffer.append(exceptionString);
		}

		else {
			outputBuffer.append("<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n" + "<result>\n");
			if (!taxNumber.isEmpty()) {
				outputBuffer.append("<taxinvoicenumber number=\"" + taxNumber + "\"></taxinvoicenumber> ");
			}
			outputBuffer.append("</result>");
		}

		return outputBuffer.toString();
	}

	// @PostConstruct
	// public void init() {
	//
	// // Get the FacesContext
	// FacesContext facesContext = FacesContext.getCurrentInstance();
	//
	// // Get HTTP response
	// HttpServletResponse response = (HttpServletResponse)
	// facesContext.getExternalContext().getResponse();
	//
	// // Set response headers
	// response.reset(); // Reset the response in the first place
	// response.setHeader("Content-Type", "text/xml"); // Set only the content
	// // type
	//
	// String taxNumber = taxNumberService.getTaxNumber(firmokpo, docnum,
	// docdate);
	//
	// // if there is not empty Exception string - we will output it.
	//
	// StringBuffer outputBuffer = new StringBuffer();
	// String exceptionString = this.taxNumberService.getExceptionString();
	// OutputStream responseOutputStream = null;
	//
	// if (!exceptionString.isEmpty()) {
	// outputBuffer.append(exceptionString);
	// }
	//
	// else {
	// outputBuffer.append("<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n" +
	// "<result>\n");
	// if (!taxNumber.isEmpty()) {
	// outputBuffer.append("<taxinvoicenumber number=\"" + taxNumber + "\"
	// ></taxinvoicenumber> ");
	// }
	// outputBuffer.append("</result>");
	// }
	//
	// try {
	// responseOutputStream = response.getOutputStream();
	// responseOutputStream.write(outputBuffer.toString().getBytes(Charset.forName("UTF-8")),
	// 0,
	// outputBuffer.length());
	//
	// } catch (IOException e) {
	// // TODO Auto-generated catch block
	// e.printStackTrace();
	// }
	//
	// finally {
	// if (responseOutputStream != null) {
	// // Make sure that everything is out
	//
	// try {
	//
	// responseOutputStream.flush();
	// responseOutputStream.close();
	// } catch (IOException e) {
	// // TODO Auto-generated catch block
	// e.printStackTrace();
	// }
	// }
	// facesContext.responseComplete();
	// }
	// }

	@Inject
	private TaxNumberService taxNumberService;

	// private static final long serialVersionUID = -2604171208172260144L;

}
