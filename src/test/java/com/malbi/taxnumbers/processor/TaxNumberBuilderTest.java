package com.malbi.taxnumbers.processor;

import static org.assertj.core.api.Assertions.assertThat;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;

import org.dbunit.dataset.xml.FlatXmlDataSetBuilder;
import org.dbunit.operation.DatabaseOperation;
import org.junit.Before;
import org.junit.Test;

import com.malbi.taxnumbers.db.DBUnitConfig;
import com.malbi.taxnumbers.util.AcceptedDateFormat;

public class TaxNumberBuilderTest extends DBUnitConfig {

	private String XML_File = "";

	@Test
	public void testShouldMatchWithReferenceResponses()
			throws UnsupportedEncodingException, FileNotFoundException, IOException, SQLException, URISyntaxException {
		// String paramQuery = "select OKPO, TO_CHAR(number_generation_date,
		// 'DD.MM.YYYY') number_generation_date, DOCUMENT_ID from
		// request_params";
		String paramQuery = "select OKPO, number_generation_date, DOCUMENT_ID from request_params";
		SimpleDateFormat sdf = new SimpleDateFormat(AcceptedDateFormat.OracleDateFormat);

		try (Connection conn = mockCM.getDBConnection();
				Statement stmt = conn.createStatement();
				ResultSet rs = stmt.executeQuery(paramQuery);) {

			while (rs.next()) {
				String firmokpo = rs.getString(1);
				String docnum = rs.getString(3);
				Timestamp docdate = rs.getTimestamp(2);

				String docdateStr = sdf.format(docdate);

				// sending request to the tested class
				String resultXML = taxXMLBuilder.getTaxNumberXML(firmokpo, docnum, docdateStr);

				// // temporarily write to files
				// File file = new File("f:\\temp\\" + docnum + ".xml");
				//
				// //
				// http://tutorials.jenkov.com/java-io/outputstreamwriter.html
				// try (OutputStreamWriter writer = new OutputStreamWriter(new
				// FileOutputStream(file), "UTF-8");) {
				// writer.write(resultXML);
				// }

				// String content = new
				// String(Files.readAllBytes(Paths.get("duke.java")));
				// ()),Charset.forName("UTF-8"));
				// http://www.adam-bien.com/roller/abien/entry/java_8_reading_a_file
				// URL responseFile =
				// Thread.currentThread().getContextClassLoader()
				// .getResource("/responses/" + docnum + ".xml");
				//
				// assertThat(responseFile).isNotNull();
				//
				// URI uri = responseFile.toURI();
				// assertThat(uri).isNotNull();

				// http://www.adam-bien.com/roller/abien/entry/java_8_reading_a_file
				// http://stackoverflow.com/questions/15713119/java-nio-file-path-for-a-classpath-resource
				// !!!!!!!! without "/" in front of responses folder name!!!!
				URL responseFile = ClassLoader.getSystemResource("responses/" + docnum + ".xml");
				assertThat(responseFile).isNotNull();

				URI uri = responseFile.toURI();
				assertThat(uri).isNotNull();

				String content = new String(Files.readAllBytes(Paths.get(uri)), Charset.forName("UTF-8"));

				assertEquals(content, resultXML);
			}
		}

	}

	@Override
	@Before
	public void setUp() throws Exception {
		super.setUp();

		// we will load whole 2 month database
		XML_File = "/dbunit/work.xml";

		InputStream in = getClass().getResourceAsStream(XML_File);
		beforeData = new FlatXmlDataSetBuilder().build(in);

		// from
		// http://www.marcphilipp.de/blog/2012/03/13/database-tests-with-dbunit-part-1/
		tester.setSetUpOperation(DatabaseOperation.CLEAN_INSERT);

		tester.setDataSet(beforeData);
		tester.onSetup();

	}

	public TaxNumberBuilderTest(String name) {
		super(name);

	}
}
