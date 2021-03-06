package com.malbi.taxnumbers.util;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.util.Properties;

import org.dbunit.database.DatabaseConfig;
import org.dbunit.database.QueryDataSet;
import org.dbunit.dataset.xml.FlatXmlDataSet;
import org.dbunit.ext.oracle.Oracle10DataTypeFactory;
import org.dbunit.ext.oracle.OracleConnection;

public class RealDatabaseExport {

	// http://dbunit.sourceforge.net/faq.html#extract
	public static void main(String[] args) throws Exception {
		System.out.println("Extracting dataset work.xml to project root - start");

		Properties property = new Properties();

		try (InputStream in = RealDatabaseExport.class.getResourceAsStream("/dbunit/work_connection.properties")) {
			property.load(in);
		} catch (IOException e) {

			e.printStackTrace();
		}

		// org.dbunit.ext.oracle.Oracle10DataTypeFactory
		Class driverClass = Class.forName(property.getProperty("database.driver"));
		OracleConnection dbUnitCon = null;
		try (Connection jdbcConnection = DriverManager.getConnection(property.getProperty("database.url"),
				property.getProperty("database.user"), property.getProperty("database.password"));) {

			dbUnitCon = new OracleConnection(jdbcConnection, property.getProperty("database.password"));
			DatabaseConfig dbUnitConConfig = dbUnitCon.getConfig();
			dbUnitConConfig.setProperty(DatabaseConfig.PROPERTY_DATATYPE_FACTORY, new Oracle10DataTypeFactory());
			dbUnitConConfig.setProperty(DatabaseConfig.FEATURE_CASE_SENSITIVE_TABLE_NAMES, false);

			// IDatabaseConnection connection = new
			// DatabaseConnection(dbUnitCon);

			// partial database export
			QueryDataSet partialDataSet = new QueryDataSet(dbUnitCon);
			partialDataSet.addTable("tax_inv_numeration",
					"select t.document_id, t.number_generation_date, t.serial_number, "
							+ "t.okpo from xxa_om_tax_inv_numeration_1c t where t.number_generation_date between "
							+ "to_date('01.03.2015', 'dd.mm.yyyy') and to_date('30.04.2015' , 'dd.mm.yyyy') and t.okpo = '37900238'");
			partialDataSet.addTable("request_params",
					"select t.document_id, t.number_generation_date, t.okpo "
							+ "from xxa_om_tax_inv_numeration_1c t where t.number_generation_date between "
							+ "to_date('01.03.2015', 'dd.mm.yyyy') and to_date('30.04.2015' , 'dd.mm.yyyy') and t.okpo = '37900238'"
							+ " and rownum < 6");

			FlatXmlDataSet.write(partialDataSet, new FileOutputStream("work.xml"));
		} finally {
			if (dbUnitCon != null) {
				dbUnitCon.close();

			}
		}

		System.out.println("Extracting dataset work.xml to project root - end");

	}
}
