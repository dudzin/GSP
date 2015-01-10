package pl.pw.elka.gsp.algorithm;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

public class ItemSet {
	
	private long date;
	private int[] items;
	
	public ItemSet(){
		
	}
	
	public ItemSet(ItemSet orig){
		date = orig.getDate();
		items = orig.getItems().clone();
	}
	
	public ItemSet(int i){
		items = new int[1];
		items[0] = i;
	}
	
	public void sort(){
		Arrays.sort(items);
	}
	
	public long getDate() {
		return date;
	}
	public void setDate(long date) {
		this.date = date;
	}
	public int[] getItems() {
		return items;
	}
	
	public Integer[] getItemsInteger() {
		Integer[] array = new Integer[items.length];
		for (int i =0; i<items.length ;i++) {
			array[i]= items[i];
		}
		return array;
	}
	
	public void setItems(int[] items) {
		this.items = items;
		sort();
	}
	
	public void setItems(int[] items1, int[] items2) {
		
		ArrayList<Integer> itemslist = new ArrayList<Integer>();
		for(int i=0; i<items1.length; i++){
			itemslist.add(items1[i]);
		}
		for(int i=0; i<items2.length; i++){
			if(!itemslist.contains(items2[i])){
				itemslist.add(items2[i]);
			}
		}
		int[] array = itemslist.stream().mapToInt(i->i).toArray();
		this.items = array;
		sort();
	}
	
	public void setItems(ArrayList<Integer> items) {
		this.items = new int[items.size()];
		for (int i=0; i< items.size(); i++) {
			this.items[i] = (int)items.get(i);
		}
		sort();
	}
	
	@Override
	public boolean equals(Object obj){
		 
		if (!(obj instanceof ItemSet))
	            return false;
	        if (obj == this)
	            return true;

	        ItemSet other = (ItemSet) obj;
	        if(this.items.length != other.getItems().length){
	        	return false;
	        }
	        
	        sort();
	        other.sort();
	        
	        for(int i =0; i<items.length; i++){
	        	if(items[i] != other.getItems()[i]){
	        		return false;
	        	}
	        }
	        return true;
	}
	
	public boolean contains(ItemSet other){
		if(other.getItems().length > items.length){
			return false;
		}
		int[] otherItems = other.getItems();
		int cnt =0;
		for (int l1 : otherItems) {
			for (int l2 : items) {
				if(l1==l2) {
					cnt++;
				}
			}
		}
		
		if(cnt == other.getItems().length){
			return true;
		}else {
			return false;
		}
		
	}

	public String toString(){
		
		String s =  "[";
		for (int i : items) {
			s += i + ",";
		}
		s = s.substring(0, s.length()-1) + "]";
		return s;
	}
	
}
