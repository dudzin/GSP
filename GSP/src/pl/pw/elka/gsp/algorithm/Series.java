package pl.pw.elka.gsp.algorithm;


import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.rmi.CORBA.Util;


public class Series {
	
	private String seriesName;
	private HashMap<Long, ItemSet> dataSeq;
	private int support;
	
	public Series(){
		dataSeq = new HashMap<Long, ItemSet>();
		support=0;
	}
	
	public Series(Series orig){
		
		dataSeq = new HashMap<Long, ItemSet>();
		seriesName = orig.seriesName;
		Set<Long> keys = orig.getDataSeq().keySet();
		
		
		for (Long key : keys) {
			dataSeq.put(key, new ItemSet(orig.getDataSeq().get(key)));
		}
		
	}
	
	public int[] getLastItem(){
		long[] dates = getDatesOrdered();
		
		ItemSet last = dataSeq.get(dates[dates.length-1]);
		return last.getItems();
		
	} 
	
	public int[] getFirstItem(){
		long[] dates = getDatesOrdered();
		
		ItemSet last = dataSeq.get(dates[0]);
		return last.getItems();
		
	} 
	
	public int[] getItemsOrdered(){
		Collection<Long> keys = dataSeq.keySet();
		List<Long> sorted = asSortedList(keys);
		
		ArrayList<Integer> list = new ArrayList<Integer>();
		
		for (Long key : sorted) {
			ItemSet itemSet = dataSeq.get(key);
			int[] itemsInSet = itemSet.getItems();
			for (int j : itemsInSet) {
				list.add(j);
				
			}
			
		}
		
		int[] array = new int[list.size()];
		for (int i=0; i<array.length; i++) {
			array[i] =list.get(i);
		}
		return array;
	}
	
	public int[] getItemsOrderedMinusOne(){
		Collection<Long> keys = dataSeq.keySet();
		List<Long> sorted = asSortedList(keys);
		
		ArrayList<Integer> list = new ArrayList<Integer>();
		sorted.remove(sorted.size()-1);
		
		for (Long key : sorted) {
			ItemSet itemSet = dataSeq.get(key);
			int[] itemsInSet = itemSet.getItems();
			for (int j : itemsInSet) {
				list.add(j);
				
			}
			
		}
		
		int[] array = new int[list.size()];
		for (int i=0; i<array.length; i++) {
			array[i] =list.get(i);
		}
		return array;
	}
	
	public boolean shouldCheck(int[] other){
		int[] thisItems = getItemsOrdered();
		int[] thisItemsMinusOne = getItemsOrderedMinusOne();
		if(thisItems.length != other.length && (thisItemsMinusOne.length != other.length)){
			return false;
		}
		for (int i = 0; i < other.length; i++) {
			if(thisItems[i] != other[i]){
				return false;
			}
		}
		return true;
	}
	
	public long getLastDate(){
		long[] dates = getDatesOrdered();
		return dates[dates.length-1];
	}
	
	public long[] getDatesOrdered(){
		Collection<Long> keys = dataSeq.keySet();
		List<Long> sorted = asSortedList(keys);
		
		long[] array = new long[sorted.size()];
		for (int i=0; i<array.length; i++) {
			array[i] =sorted.get(i);
		}
		return array;
	}
	
	public ArrayList<Series> getSeriesByRemovingLast(){
		
		long[] dates = getDatesOrdered();
		if(dates.length <1){
			return null;
		}
		
		Series orig = new Series(this);
		orig.removeLast();
		
		ArrayList<Series> newseries = new ArrayList<Series>();
		ArrayList<Integer> newitems;
		int[] items = this.getLastItem();
		Series news;
		ItemSet newis;
		if(items.length!=1){
			for (int i : items) {
				news = new Series(orig);
				newis = new ItemSet();
				newitems = new ArrayList<Integer>();
				for (int j : items) {
					if(i!=j){
						newitems.add(j);
					}
				}
				if(newitems.size()!= 0){
					newis.setItems(newitems);
					
					news.addItemSet(dates[dates.length-1], newis);
					news.setSeriesName(""+i);
					newseries.add(news);
				}
			}
		}else {
			
			news = new Series(orig);
			
			news.setSeriesName(""+items[0]);
			newseries.add(news);
		}
		
		
		return newseries;
	}
	
	public ArrayList<Series> getSeriesByRemovingFirst(){
		
		long[] dates = getDatesOrdered();
		if(dates.length <1){
			return null;
		}
		
		Series orig = new Series(this);
		orig.removeFirst();
		
		ArrayList<Series> newseries = new ArrayList<Series>();
		ArrayList<Integer> newitems;
		int[] items = this.getFirstItem();
		Series news;
		ItemSet newis;
		if(items.length!=1){
			for (int i : items) {
				news = new Series(orig);
				newis = new ItemSet();
				newitems = new ArrayList<Integer>();
				for (int j : items) {
					if(i!=j){
						newitems.add(j);
					}
				}
				///move by dates
				newis.setItems(newitems);
				news.addItemSet(dates[dates.length-1], newis);
				news.setSeriesName(""+i);
				newseries.add(news);
			}
		}else {
			
			news = new Series(orig);
			
			news.setSeriesName(""+items[0]);
			newseries.add(news);
		}
		return newseries;
	}
	
	/*public boolean canJoinGSP(Series other){
		
		if(this.equals(other)){
			return true;
		}
		return false;
	}
	*/
	
	
	public boolean canJoin(Series other){
		
		if(dataSeq.size() != other.getDataSeq().size()){
			return false;
		}

		long[] dates = getDatesOrdered();
		for(int i=0; i< dates.length-1; i++){
			if(!dataSeq.get(dates[i]).equals(other.getDataSeq().get(dates[i]))){
				return false;
			}
		}
		
		return true;
	}
	public boolean canJoinCondition1(Series other){
		if(dataSeq.size() <2){
			return false;
		}
		if(dataSeq.size() != other.getDataSeq().size()){
			return false;
		}
		
		long[] dates = getDatesOrdered();
		
		// czy ostatni itemset jest jednoelementowy
		if((dataSeq.get(dates[dates.length-1]).getItems().length!= 1) 
				&& (other.getDataSeq().get(dates[dates.length-1]).getItems().length!= 1) 
				){
			return false;
		}
		
		// czy G(P) jest takie samo w obu przypadkach
		for(int i=0; i< dates.length-1; i++){
			if(!dataSeq.get(dates[i]).equals(other.getDataSeq().get(dates[i]))){
				return false;
			}
		}
		
		return true;
	}
	
	public boolean canJoinCondition2(Series other){

		if(dataSeq.size() != other.getDataSeq().size()){
			return false;
		}
		
		long[] dates = getDatesOrdered();
		
		// czy ostatni itemset jest nie jednoelementowy i są tej samej długości
		if((dataSeq.get(dates[dates.length-1]).getItems().length <= 1) 
				|| (other.getDataSeq().get(dates[dates.length-1]).getItems().length != 
				dataSeq.get(dates[dates.length-1]).getItems().length) 
				){
			return false;
		}
		
		// czy G(P) jest takie samo w obu przypadkach
		for(int i=0; i< dates.length-1; i++){
			if(!dataSeq.get(dates[i]).equals(other.getDataSeq().get(dates[i]))){
				return false;
			}
		}
		
		// ostatni element G(P)
		Integer[] thislast = dataSeq.get(dates[dates.length-1]).getItemsInteger();
		Integer[] otherlast = other.getDataSeq().get(dates[dates.length-1]).getItemsInteger();
		
		Set<Integer> s1 = new HashSet<Integer>(Arrays.asList(thislast));
		Set<Integer> s2 = new HashSet<Integer>(Arrays.asList(otherlast));
		s1.retainAll(s2);

		Integer[] result = s1.toArray(new Integer[s1.size()]);
		
		if((result.length) != thislast.length-1){
			return false;
		}
		
		return true;
	}
	
	public char canJoinCondition3(Series other){
		if(Math.abs(dataSeq.size() - other.getDataSeq().size()) !=1){
			return 'n';
		}
		
		long[] dates = getDatesOrdered();
		char singleLast;
		
		// sprawdza która seria zawiera jednoelementowy itemset
		if((dataSeq.get(dates[dates.length-1]).getItems().length == 1)){
			singleLast = 't';
		}else {
			singleLast = 'o';
		}
		
		int checkTill = Math.max(dates.length, other.getDatesOrdered().length)-2;
		for(int i=0; i< checkTill; i++){
			if(!dataSeq.get(dates[i]).equals(other.getDataSeq().get(dates[i]))){
				return 'n';
			}
		}
		
		if(singleLast =='t'){
			// czy G(P) jest takie samo w obu przypadkach do max -2 elementu
			if((other.getDataSeq().get(dates[checkTill]).getItems().length -
					dataSeq.get(dates[checkTill]).getItems().length  ) != 1){
				return 'n';
			}
			
			// ostatni element G(P)
			Integer[] thislast = dataSeq.get(dates[dates.length-2]).getItemsInteger();
			dates =other.getDatesOrdered();
			Integer[] otherlast = other.getDataSeq().get(dates[dates.length-1]).getItemsInteger();
			
			Set<Integer> s1 = new HashSet<Integer>(Arrays.asList(thislast));
			Set<Integer> s2 = new HashSet<Integer>(Arrays.asList(otherlast));
			s1.retainAll(s2);

			Integer[] result = s1.toArray(new Integer[s1.size()]);
			
			if((result.length) != otherlast.length-1){
				return 'n';
			}
		
		}
		
		if(singleLast =='o'){
			// czy G(P) jest takie samo w obu przypadkach do max -2 elementu
			
			if((other.getDataSeq().get(dates[checkTill]).getItems().length -
					dataSeq.get(dates[checkTill]).getItems().length  ) != -1){
				return 'n';
			}
			
			// ostatni element G(P)
			Integer[] thislast = dataSeq.get(dates[dates.length-1]).getItemsInteger();
			dates =other.getDatesOrdered();
			Integer[] otherlast = other.getDataSeq().get(dates[dates.length-2]).getItemsInteger();
			
			Set<Integer> s1 = new HashSet<Integer>(Arrays.asList(thislast));
			Set<Integer> s2 = new HashSet<Integer>(Arrays.asList(otherlast));
			s1.retainAll(s2);

			Integer[] result = s1.toArray(new Integer[s1.size()]);
			
			if((result.length) != thislast.length-1){
				return 'n';
			}
		
		}
		
		return singleLast;
	}
	
	public void removeLast(){
		long[] dates =getDatesOrdered();
		dataSeq.remove(dates[dates.length-1]);
		

	}  
	
	public void removeFirst(){
		long[] dates =getDatesOrdered();
		for (int i = 1; i < dates.length; i++) {
			dataSeq.put(dates[i-1], dataSeq.get(dates[i]));
		}
		dataSeq.remove(dates[dates.length-1]);
		
	}  
	
	@Override
	public String toString(){
		
		String s=" supp: " + support + " , series :";
		Set<Long> keys = getDataSeq().keySet();
		for (Long string : keys) {
			
			s += string + ":" + getDataSeq().get(string).toString() + " ";
		}
		
		//System.out.println(s);
		return s;
	}
	
	@Override
	public boolean equals(Object object){
		
		if(!(object instanceof Series)){
			return false;
		}
		Series other = (Series) object;
		
		long[] thisdates = this.getDatesOrdered();
		long[] otherdates = other.getDatesOrdered();
		
		if(thisdates.length != otherdates.length){
			return false;
		}
		
		for (int i = 0; i < otherdates.length; i++) {
			if(thisdates[i] != otherdates[i]){
				return false;
			}
			if(!this.getDataSeq().get(thisdates[i]).equals(other.getDataSeq().get(thisdates[i]))){
				return false;
			}
		}
		return true;
	}
	
	
	public static
	<T extends Comparable<? super T>> List<T> asSortedList(Collection<T> c) {
	  List<T> list = new ArrayList<T>(c);
	  java.util.Collections.sort(list);
	  return list;
	}

	public int getSupport() {
		return support;
	}

	public void setSupport(int support) {
		this.support = support;
	}
	
	public void supportIncr(){
		support++;
	}
	
	public String getSeriesName() {
		return seriesName;
	}
	public void setSeriesName(String seriesName) {
		this.seriesName = seriesName;
	}
 
	public void addItemSet(Long date, ItemSet itemSet){
		dataSeq.put(date, itemSet);
		
	}
	public HashMap<Long, ItemSet> getDataSeq() {
		return dataSeq;
	}
	public void setDataSeq(HashMap<Long, ItemSet> dataSeq) {
		this.dataSeq = dataSeq;
	}
}
