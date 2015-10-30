# Tax numbers exchange between Oracle middle-tier application and 1C databases #

This JAX-RS web service is a customization of exchange between Oracle E-Business Suite 11 and 1C 8.2 accounting databases. OEBS is the main ERP system where sales transactions are recorded. In order to manage incoming and outgoing VAT volumes, originated from sales and purchases, 1C 8 accounting databases are used. Outgoing VAT is counted with the help of VAT invoices, deriving from sales, that should be sequentially numbered. OEBS database manages sequences of VAT invoice numbers for all LLCs in the holding. 

When storing a new VAT invoice, regardless of the way it has been created, 1C 8 database sends an HTTP-query like <http://servername:port/appcontext/services/taxinvoicenumber/?firmokpo=value&docnum=value&docdate=value> . These parameters are validated by the web service, transformed to a composite key and submitted to OEBS database via JDBC. In every concrete case OEBS stored procedure either generates and stores a new invoice number to the composite key sent or retrieves an existing number from database view if the key is present.

##System requirements: ##

- Apache Tomcat 8;
- Java 8;
- configured JDBC datasource named "jdbc/oracle".

## Technologies ##

Following technologies and components are used in this web service:

- CDI 1.2 (Weld implementation);
- JAX-RS (Jersey 1.19 implementation);
- Hibernate Validator 5.2.1;
- JUnit 4.12;
- JDBC;
- Apache Tomcat 8;
- Java 8.

The project can be built either with Maven (3.3 or higher) or Eclipse 4.5 or higher).