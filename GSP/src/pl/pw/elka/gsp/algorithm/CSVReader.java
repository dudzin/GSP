package pl.pw.elka.gsp.algorithm;

import java.util.ArrayList;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

public class CSVReader {

	public ArrayList<Row> read(String path) {
		
		BufferedReader br = null;
		String line = "";
		String cvsSplitBy = ",";
		ArrayList<Row> list = new ArrayList<Row>();
		ArrayList<String> columnList = new ArrayList<String>();
	 
		try {
	 
			br = new BufferedReader(new FileReader(path));
			line = br.readLine();
			String[] fields = line.split(cvsSplitBy);
			for(int i =0; i< fields.length ; i++){
				columnList.add(fields[i]);
			}
			
			Row row;
			while ((line = br.readLine()) != null) {
			        // use comma as separator
				fields = line.split(cvsSplitBy);
				row = new Row();
				row.setSeriesName(fields[0]);
				row.setDate(fields[1]);
				
				
				for (int i=2; i< fields.length; i++) {
					if(fields[i] != null){
						row.add(columnList.get(i), Double.parseDouble(fields[i]));
					}else {
						row.add(columnList.get(i), 0);
					}
					
				}
				
				list.add(row);
	 
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

		return list;
	}

}
