package pl.pw.elka.gsp.test;

import static org.junit.Assert.*;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;



import java.util.Set;

import org.junit.Test;

import pl.pw.elka.gsp.algorithm.CSVReader;
import pl.pw.elka.gsp.algorithm.ItemSet;
import pl.pw.elka.gsp.algorithm.Series;

public class CSVReaderTest {

	@Test
	public void createRowsTest() throws ParseException {
		
		CSVReader reader = new CSVReader();
		HashMap<String, Series>  testData = reader.read("testdata/test1.csv", null, false);
		
		SimpleDateFormat sdf = new SimpleDateFormat("mm/dd/yyyy");
		String date1 = "1/7/2011";
		String date2 = "1/14/2011";
		
		Date date = sdf.parse(date1);
		
	
		int[] items1 = {0,1,2,3};
		ItemSet itemset1 = new ItemSet();
		itemset1.setDate(date.getTime() /(1000 * 60 * 60 * 24));
		itemset1.setItems(items1);
		
		assertEquals(itemset1, testData.get("AA").getDataSeq().get(itemset1.getDate()));
		
		
		 date = sdf.parse(date2);
		int[] items2 = {4,5,6,7};
		ItemSet itemset2 = new ItemSet();
		itemset2.setDate(date.getTime() /(1000 * 60 * 60 * 24));
		itemset2.setItems(items2);
		
		
		assertEquals(itemset2, testData.get("AA").getDataSeq().get(itemset2.getDate()));

		assertEquals(15, reader.getDictionary().size());
		
		
	}
	
	@Test
	public void createRowsRoundTest() throws ParseException {
		
		CSVReader reader = new CSVReader();
		HashMap<String, Series>  testData = reader.read("testdata/test1.csv", null, true);
		SimpleDateFormat sdf = new SimpleDateFormat("mm/dd/yyyy");
		String date1 = "1/7/2011";
		String date2 = "1/14/2011";
		
		Date date = sdf.parse(date1);
		
		int[] items1 = {0,1,2,3,4,5,6,7,8,9,10,11};
		ItemSet itemset1 = new ItemSet();
		itemset1.setDate(date.getTime() /(1000 * 60 * 60 * 24));
		itemset1.setItems(items1);
		
		date = sdf.parse(date2);
		int[] items2 = {1,2,8,12,13,14,15,16,17,18,19,20};
		ItemSet itemset2 = new ItemSet();
		itemset2.setDate(date.getTime() /(1000 * 60 * 60 * 24));
		itemset2.setItems(items2);
				
		assertEquals(itemset1, testData.get("AA").getDataSeq().get(itemset1.getDate()));
		assertEquals(itemset2, testData.get("AA").getDataSeq().get(itemset2.getDate()));
		assertEquals(36, reader.getDictionary().size());
		
		// print dictionary
		
		Set<Integer> dict = reader.getDictionary().keySet();
		for (Integer integer : dict) {
			System.out.println(integer + " " + reader.getDictionary().get(integer));
		}
	}
	
}
