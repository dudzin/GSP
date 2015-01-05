package pl.pw.elka.gsp.algorithm;

import java.util.HashMap;

public class Row {
	
	private String seriesName;
	private String date;
	private HashMap<String, Double> values;
	
	public Row(){
		values = new HashMap<String, Double>();
	}
	
	public String getSeriesName() {
		return seriesName;
	}
	
	public void setSeriesName(String seriesName) {
		this.seriesName = seriesName;
	}
	
	public String getDate() {
		return date;
	}
	
	public void setDate(String date) {
		this.date = date;
	}
	
	public void add(String fieldName, double fieldValue){
		values.put(fieldName, new Double(fieldValue));	
	}
	
	public double get(String fieldName){
		return values.get(fieldName);
	}
	
	public  HashMap<String, Double> get(){
		return values;
	}
	
	@Override
    public boolean equals(Object obj) {
       if (!(obj instanceof Row))
            return false;
        if (obj == this)
            return true;

        Row other = (Row) obj;
        
       
        
        for (String key : values.keySet()) {
			if(!values.get(key).equals(other.get(key))){
				return false;
			}
		}
        
        return true;
	}
	
}
