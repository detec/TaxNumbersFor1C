package com.malbi.taxnumbers.db;

import java.io.InputStream;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.text.SimpleDateFormat;

import org.dbunit.DBTestCase;
import org.dbunit.IDatabaseTester;
import org.dbunit.JdbcDatabaseTester;
import org.dbunit.PropertiesBasedJdbcDatabaseTester;
import org.dbunit.dataset.CachedDataSet;
import org.dbunit.dataset.Column;
import org.dbunit.dataset.DataSetException;
import org.dbunit.dataset.IDataSet;
import org.dbunit.dataset.ITable;
import org.dbunit.dataset.ITableMetaData;
import org.dbunit.dataset.xml.FlatXmlDataSet;
import org.dbunit.dataset.xml.FlatXmlDataSetBuilder;
import org.dbunit.operation.DatabaseOperation;
import org.junit.Before;
import org.junit.BeforeClass;

import com.malbi.taxnumbers.dao.ITaxNumberDAO;
import com.malbi.taxnumbers.dao.TaxNumberDAO;
import com.malbi.taxnumbers.processor.TaxXMLBuilder;
import com.malbi.taxnumbers.service.ITaxNumberService;
import com.malbi.taxnumbers.service.TaxNumberService;
import com.malbi.taxnumbers.util.AcceptedDateFormat;

// http://devcolibri.com/3575

public class DBUnitConfig extends DBTestCase {

	protected IDatabaseTester tester;
	protected IDataSet beforeData;

	public static final String JDBC_DRIVER = "org.h2.Driver";
	// we initialize database after each test
	// private static final String JDBC_URL =
	// "jdbc:h2:mem:test;DB_CLOSE_DELAY=-1";
	public static final String JDBC_URL = "jdbc:h2:mem:test";
	// public static final String JDBC_URL =
	// "jdbc:h2:tcp://localhost/~/websync";
	public static final String USER = "sa";
	public static final String PASSWORD = "";

	public MockConnectionManager mockCM = new MockConnectionManager();
	public SQLQueries SQLQueriesTest = new SQLQueries();
	public ITaxNumberDAO testDAO = new TaxNumberDAO();
	public ITaxNumberService testNumberService = new TaxNumberService();
	public TaxXMLBuilder taxXMLBuilder = new TaxXMLBuilder();

	// we should create schema!!
	// http://www.marcphilipp.de/blog/2012/03/13/database-tests-with-dbunit-part-1/

	// http://stackoverflow.com/questions/1531324/is-there-any-way-for-dbunit-to-automatically-create-tables

	public DBUnitConfig() {

	}

	@Override
	@Before
	public void setUp() throws Exception {
		tester = new JdbcDatabaseTester(JDBC_DRIVER, JDBC_URL, USER, PASSWORD);

		// this is an analogue to BeforeClass
		createSchema();

		// setting up mock classes
		testDAO.setCm(mockCM);
		testDAO.setSqlQueries(SQLQueriesTest);

		testNumberService.setTaxNumberDAO(testDAO);
		taxXMLBuilder.setTaxNumberService(testNumberService);
	}

	public DBUnitConfig(String name) {
		super(name);

		System.setProperty(PropertiesBasedJdbcDatabaseTester.DBUNIT_DRIVER_CLASS, JDBC_DRIVER);
		System.setProperty(PropertiesBasedJdbcDatabaseTester.DBUNIT_CONNECTION_URL, JDBC_URL);
		System.setProperty(PropertiesBasedJdbcDatabaseTester.DBUNIT_USERNAME, USER);
		System.setProperty(PropertiesBasedJdbcDatabaseTester.DBUNIT_PASSWORD, PASSWORD);
	}

	@Override
	protected IDataSet getDataSet() throws Exception {

		return beforeData;
	}

	@Override
	protected DatabaseOperation getTearDownOperation() throws Exception {
		return DatabaseOperation.DELETE_ALL;
	}

	private static void createH2Tables(IDataSet dataSet, Connection connection) throws DataSetException, SQLException {
		String[] tableNames = dataSet.getTableNames();

		String sql = "";
		for (String tableName : tableNames) {
			ITable table = dataSet.getTable(tableName);
			ITableMetaData metadata = table.getTableMetaData();
			Column[] columns = metadata.getColumns();
			sql += "\n" + "DROP TABLE " + tableName + " IF EXISTS;";
			sql += "\n" + "create table " + tableName + "( ";
			boolean first = true;
			for (Column column : columns) {
				if (!first) {
					sql += ", ";
				}
				String columnName = column.getColumnName();
				String type = resolveType((String) table.getValue(0, columnName));
				sql += columnName + " " + type;
				// set primary key for NODE_ID

				if (columnName.equals("NODE_ID") && !first) {
					// sql += " primary key";
				}

				if (first) {
					// parent_id in hierarchy table should not be primary key!
					if (columnName.equals("PARENT_ID")) {
						sql += " ,";
					} else {
						// sql += " primary key";
						first = false;
					}
				}
			}
			sql += "); ";
		}
		PreparedStatement pp = connection.prepareStatement(sql);
		pp.executeUpdate();

		// 23.12.2015 added
		pp.close();
		connection.close();

	}

	private static String resolveType(String str) {
		SimpleDateFormat sdf = new SimpleDateFormat(AcceptedDateFormat.TimeStampFormat);

		try {
			// there is no conversion for date
			if (sdf.format(sdf.parse(str)).equals(str)) {
				return "timestamp";
			}

			else if (new Double(str).toString().equals(str)) {
				return "double";
			}

			else if (new Integer(str).toString().equals(str)) {
				return "int";
			}

		} catch (Exception e) {

		}

		return "varchar";
	}

	@BeforeClass // Junit 3 and DBUnit do not understand it
	public static void createSchema() throws Exception {

		// it is the default dataset, later it will be overridden by another
		// dataset in ancestors.
		// InputStream in =
		// DBUnitConfig.class.getResourceAsStream("/dbunit/schemabuilder.xml");
		InputStream in = DBUnitConfig.class.getResourceAsStream("/dbunit/schemabuilder.xml");
		FlatXmlDataSet DBData = new FlatXmlDataSetBuilder().build(in);

		MockConnectionManager cm = new MockConnectionManager();
		createH2Tables(DBData, cm.getDBConnection());

		// RunScript.execute(JDBC_URL, USER, PASSWORD, "schema.sql",
		// Charset.forName("UTF-8"), false);
	}

	public void compareDatasets(String expectedDatasetPath) {
		try {

			// comparing solution is here
			// http://stackoverflow.com/questions/12508270/compare-2-datasets-with-dbunit

			// Load expected data from an XML dataset
			IDataSet expectedDataSet = new FlatXmlDataSetBuilder()
					.build(getClass().getResourceAsStream(expectedDatasetPath));

			String[] tablenames = expectedDataSet.getTableNames();

			// read dataset from database table using the same tables as from
			// the xml
			IDataSet tmpDataset = getConnection().createDataSet(tablenames);
			IDataSet databaseDataSet = new CachedDataSet(tmpDataset);

			for (int i = 0; i < tablenames.length; i++) {

				ITable expectedTable = expectedDataSet.getTable(tablenames[i]);
				ITable actualTable = databaseDataSet.getTable(tablenames[i]);

				// http://jasalguero.com/ledld/development/dbunit-introduction-ii/
				assertEquals(expectedTable.getRowCount(), actualTable.getRowCount());

				// Comparing data
				Column[] columns = expectedTable.getTableMetaData().getColumns();
				int columnCount = columns.length;
				// let's compare data cell by cell
				for (int h = 0; h < expectedTable.getRowCount(); h++) {
					for (int j = 0; j < columnCount; j++) {
						// compare values from cells, converted to string as we
						// get different types of the same value
						assertEquals(expectedTable.getValue(h, columns[j].getColumnName()).toString(),
								actualTable.getValue(h, columns[j].getColumnName()).toString());
					}
				}

			}

		} catch (Exception e) {

			e.printStackTrace();
		}

	}

}
