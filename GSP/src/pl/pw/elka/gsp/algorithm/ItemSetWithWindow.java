package pl.pw.elka.gsp.algorithm;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class ItemSetWithWindow {

	private HashMap<Long, ItemSet> itemSets;
	
	public ItemSetWithWindow(HashMap<Long, ItemSet> itemSets){
		this.itemSets = itemSets;
	}
	
	
	public boolean contains(ItemSet other){
		
		Set<Long> dates = itemSets.keySet();
		HashSet<Integer> items = new HashSet<Integer>();
		
		for (Long date : dates) {
			Integer[] itemsIn = itemSets.get(date).getItemsInteger();
			for (Integer integer : itemsIn) {
				items.add(integer);
			}
			
		}
				
		if(other.getItems().length > items.size()){
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
	
	public long getMinDate(ItemSet other){
		
		Collection<Long> keys = itemSets.keySet();
		List<Long> sorted = asSortedList(keys);
		
		long[] sorteddates = new long[sorted.size()];
		for (int i=0; i<sorteddates.length; i++) {
			sorteddates[i] =sorted.get(i);
		}
		
		int[] values = other.getItems();
		HashMap<Integer, Long> valMap = new HashMap<Integer, Long>();
		
		for (int val : values) {
			for (int i =sorteddates.length-1; i>=0 ; i--){
				int[] datevalues = itemSets.get(sorteddates[i]).getItems();
				for (int j : datevalues) {
					if(val == j){
						valMap.put(val, sorteddates[i]);
					}
				}
				
			}
		}
		Collection<Long> valkeys = valMap.values();
		List<Long> sortedValKeys = asSortedList(valkeys);
		return sortedValKeys.get(0);
		
	}
	
	public long getMaxDate(ItemSet other){
		
		Collection<Long> keys = itemSets.keySet();
		List<Long> sorted = asSortedList(keys);
		
		long[] sorteddates = new long[sorted.size()];
		for (int i=0; i<sorteddates.length; i++) {
			sorteddates[i] =sorted.get(i);
		}
		
		int[] values = other.getItems();
		HashMap<Integer, Long> valMap = new HashMap<Integer, Long>();
		

		for (int val : values) {
			for (long date : sorteddates) {
				int[] datevalues = itemSets.get(date).getItems();
				for (int i : datevalues) {
					if(val == i){
						valMap.put(val, date);
					}
				}
				
			}
		}		
		
		Collection<Long> valkeys = valMap.values();
		List<Long> sortedValKeys = asSortedList(valkeys);
		return sortedValKeys.get(sortedValKeys.size()-1);
		
	}

	public static
	<T extends Comparable<? super T>> List<T> asSortedList(Collection<T> c) {
	  List<T> list = new ArrayList<T>(c);
	  java.util.Collections.sort(list);
	  return list;
	}
	
	public HashMap<Long, ItemSet> getItemSets() {
		return itemSets;
	}

	public void setItemSets(HashMap<Long, ItemSet> itemSets) {
		this.itemSets = itemSets;
	}
}
