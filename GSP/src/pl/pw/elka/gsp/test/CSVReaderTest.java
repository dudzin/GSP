package pl.pw.elka.gsp.test;

import static org.junit.Assert.*;

import java.util.ArrayList;

import org.junit.Test;

import pl.pw.elka.gsp.algorithm.CSVReader;
import pl.pw.elka.gsp.algorithm.Row;

public class CSVReaderTest {

	@Test
	public void test() {
		
		CSVReader reader = new CSVReader();
		ArrayList<Row> testData = reader.read("testdata/test1.csv");
				
		Row row1 = new Row();
		row1.setSeriesName("AA");
		row1.setDate("1/7/2012");
		row1.add("close",16.42d);
		row1.add("close_change",3.79267d);
		row1.add("volume",239655616d);
		row1.add("volume_change",0);
		
		Row row2 = new Row();
		row2.setSeriesName( "AA");
		row2.setDate("1/14/2012");
		row2.add("close",15.97d);
		row2.add("close_change",-4.42849d);
		row2.add("volume",242963398d);
		row2.add("volume_change",1.380223028d);
		
		assertTrue(row1.equals(testData.get(0)));
		assertTrue(row2.equals(testData.get(1)));
	}

}
