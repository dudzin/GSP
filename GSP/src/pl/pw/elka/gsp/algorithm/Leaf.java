package pl.pw.elka.gsp.algorithm;

import java.util.ArrayList;
import java.util.HashMap;

public class Leaf extends Node{
	
	private ArrayList< Series> candidates;
	
	public Leaf(int hash, int threshold){
		super(hash, threshold);
		candidates = new ArrayList< Series>();
	}

	public boolean canStoreMore() {
		if(candidates.size() <getThreshold()){
			return true;
		}
		return false;
	}

	@Override
	public void addSeries(Series series) {
		candidates.add( series);
		
	}
	
	public ArrayList<Series> getCandidates(){
		return candidates;
	}
	
	public void print(String offset){
		
		System.out.println(offset + "Leaf level: " + getLevel());
		for(int i=0; i<candidates.size();i++){
			System.out.println(offset + "|- child:" + i);
			System.out.println(offset + "  " + candidates.get(i));
		}
	
	}

	@Override
	public ArrayList<Series> getCandidateSeries() {
		//ArrayList<Series> filtered = new ArrayList<Series>();
		// TO DO jak będzie sprawdzanie wsparcia z użyciem tablicy hashującej, 
		// 		 to dodać filtrowanie po nie 0 supporcie
		return candidates;
	}
}
