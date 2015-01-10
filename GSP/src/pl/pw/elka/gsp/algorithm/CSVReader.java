package pl.pw.elka.gsp.algorithm;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Set;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;


public class CSVReader {

	private HashMap<Integer,String> dictionary;
	private HashMap<String, Series> series;
	ArrayList<String> columnList;
	SimpleDateFormat sdf ;
		
	public HashMap<String, Series> read(String path, String dateformat, boolean hierarchy ) throws ParseException {
		
		BufferedReader br = null;
		String line = "";
		
		
		dictionary = new HashMap<Integer, String>();
		columnList = new ArrayList<String>();		
		
		setSeries(new HashMap<String, Series>());
		
		if(dateformat == null){
			sdf = new SimpleDateFormat("MM/dd/yyyy");
		}else {
			sdf = new SimpleDateFormat(dateformat);
		}
		
		try {
	 
			br = new BufferedReader(new FileReader(path));
			line = br.readLine();
			String[] fields = line.split(",");
			for(int i =0; i< fields.length ; i++){
				columnList.add(fields[i]);
			}
			
			while ((line = br.readLine()) != null) {
			       processLine(line, hierarchy);
			}
	 
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if (br != null) {
				try {
					br.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}

		return series;
	}

	private void processLine(String line, boolean hierarchy) throws ParseException{
		
		String[] fields = line.split(",");
		ArrayList<Integer> items = new ArrayList<Integer>();
		ItemSet itemset = new ItemSet();
		itemset.setDate(parseDate(fields[1]));

		for (int i=2; i< fields.length; i++) {
			String item ;
			if(fields[i] != null){
				
				addItem(items,makeItem(columnList.get(i), fields[i].trim()));
				if(hierarchy ==true){
					addItem(items, makeItem(columnList.get(i) + ".round", "" + Math.round(Double.parseDouble(fields[i].trim()))));
					addItem(items, makeItem(columnList.get(i) + ".sign", "" + Math.round(Math.signum(Double.parseDouble(fields[i].trim()))) ));
				}
			}else {
				item = makeItem(columnList.get(i), "");
				if(getItemKey(item)!=-1){
					items.add(getItemKey(item));
				}
			}
			
			
		}
		itemset.setItems(items);
		
		Series ser = series.get(fields[0]);
		if(ser == null){
			ser = new Series();
			ser.setSeriesName(fields[0]);
			ser.addItemSet(itemset.getDate(), itemset);
			series.put(fields[0], ser);
		}else {
			ser.addItemSet(itemset.getDate(), itemset);
		}
	}
	
	public long parseDate(String date) throws ParseException{
		return sdf.parse(date).getTime()/(1000 * 60 * 60 * 24);
	}
	
	private String makeItem(String string1, String string2) {
		
		return string1 + ":" + string2;
	}

	private void addItem(ArrayList<Integer> items, String item){
		
		if(getItemKey(item)!=-1){
			items.add(getItemKey(item));
		}else {
			int size = dictionary.size();
			dictionary.put(size, item);
			items.add(size);
		}
		
	}
	
	
	
	
	public int getItemKey(String text){
		if(!dictionary.containsValue(text)){
			return -1;
		}
		
		Set<Integer> keys = dictionary.keySet();
		
		for (Integer key : keys) {
			if(dictionary.get(key).equals(text)){
				return key;
			}
		}
		return -1;
	}
	
	
	

	public HashMap<Integer,String> getDictionary() {
		return dictionary;
	}

	public void setDictionary(HashMap<Integer,String> dictionary) {
		this.dictionary = dictionary;
	}

	public HashMap<String, Series> getSeries() {
		return series;
	}

	public void setSeries(HashMap<String, Series> series) {
		this.series = series;
	}

}
