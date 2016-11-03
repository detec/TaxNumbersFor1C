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
import javax.ws.rs.core.Response;

import com.malbi.taxnumbers.model.Result;
import com.malbi.taxnumbers.processor.TaxXMLBuilder;

// http://docs.oracle.com/javaee/7/tutorial/jaxrs-advanced004.htm
// 31.4 Integrating JAX-RS with EJB Technology and CDI
// The beans must be proxyable, so the Employee class requires a nonprivate constructor with no parameters

/**
 * Controller for REST
 *
 * @author Andrii Duplyk
 *
 */
@Named
@RequestScoped
@Produces(MediaType.APPLICATION_XML)
@Path("/taxinvoicenumber")
public class TaxNumberController implements Serializable {

	@Inject
	private TaxXMLBuilder taxXMLBuilder;

	private static final long serialVersionUID = -2604171208172260144L;

	/**
	 * Required empty constructor.
	 */
	public TaxNumberController() {

	}

	/**
	 *
	 * @param firmokpo
	 * @param docnum
	 * @param docdate
	 * @return
	 * @throws Exception
	 */
	// @GET
	// @Deprecated
	// @Produces(MediaType.APPLICATION_XML)
	// public String getTaxNumberByParams(@QueryParam("firmokpo") String
	// firmokpo, @QueryParam("docnum") String docnum,
	// @QueryParam("docdate") String docdate) throws Exception {
	//
	// return taxXMLBuilder.getTaxNumberXML(firmokpo, docnum, docdate);
	//
	// }

	@GET
	@Produces(MediaType.APPLICATION_XML)
	// @Path("test")
	public Response getTaxNumberByParamsObject(@QueryParam("firmokpo") String firmokpo,
			@QueryParam("docnum") String docnum, @QueryParam("docdate") String docdate) throws Exception {

		Result result = taxXMLBuilder.getTaxNumberObject(firmokpo, docnum, docdate);
		return Response.status(Response.Status.OK).entity(result).build();
	}
}
