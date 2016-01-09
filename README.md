# Tax invoice numbers exchange (between Oracle middle-tier application and 1C:Enterprise databases) #

## Business purpose ##

This JAX-RS 2.0 web service is a customization of exchange between Oracle E-Business Suite 11 and 1C:Enterprise 8.2 accounting databases. OEBS is the main ERP system where sales transactions are recorded. In order to manage incoming and outgoing VAT volumes, originated from sales and purchases, 1C:Enterprise 8 accounting databases are used. Outgoing VAT is counted with the help of VAT invoices, deriving from sales, that should be sequentially numbered. OEBS database manages sequences of VAT invoice numbers for all LLCs in the holding. 

This is a corporate project in production.

## Technical details ##

When storing a new VAT invoice, regardless of the way it has been created, 1C:Enterprise 8 database sends an HTTP-query like <http://servername:port/appcontext/services/taxinvoicenumber/?firmokpo=value&docnum=value&docdate=value> . These parameters are validated by the web service with regular expressions, transformed to a composite key and submitted to OEBS database via JDBC. In every concrete case OEBS stored function either generates and stores a new invoice number to the composite key sent or retrieves an existing number from database view if the key is found. The answer is returned in XML, that is built by converting JAXB-annotated POJO with default JAXB implementation.

## Tests notice ##

JUnit and DBUnit libraries are used, together with H2 in-memory database.  As OEBS database is too complicated to reproduce, real and test data have been unloaded to DBUnit XML datasets. Different SQL queries are used for production and test use. As a result, these all measures provided the verification of JAXB output with its reference one, saved in XML files. Additionally, Hibernate Validator is tested for input parameters.

##System requirements: ##

- Apache Tomcat 8;
- Java 8;
- configured JDBC datasource named "jdbc/oraDbProdDataSource".

## Technologies ##

Following technologies and components are used in this web service:

- CDI 1.2 (Jersey built-in HK2 implementation);
- JAX-RS 2.0 (Jersey 2);
- Hibernate Validator 5.2;
- JUnit 4.12;
- DBUnit 2.5;
- JDBC (Oracle and H2);
- Maven 3.3 with plugins compiler, surefire, resources, war;
- Apache Tomcat 8;
- Java 8.

The project can be built either with Maven (3.3 or higher) or Eclipse (4.5 or higher).