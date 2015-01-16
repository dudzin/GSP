package pl.pw.elka.gsp.algorithm;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Set;

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
		
		System.out.println(offset + "Leaf level: " + getLevel() + " check: " + this.isCheck() + " hashes " + this.toCheckToString());
		for(int i=0; i<candidates.size();i++){
			System.out.println(offset + "|- candidate:" + i);
			System.out.println(offset + "  " + candidates.get(i));
		}
	
	}

	@Override
	public ArrayList<Series> getCandidateSeries(int minSupp) {
		ArrayList<Series> filtered = new ArrayList<Series>();
		int cnt;
		for (Series s : candidates) {
			cnt = s.getsupportedByHash().size();
		//if(s.getSupport()>=minSupp){
			if(cnt>=minSupp){
				filtered.add(s);
			}
		}
		
		return filtered;
	}
	
	@Override
	public ArrayList<Series> getCandidateSeries() {
		return candidates;
	}
	
	@Override
	public boolean checkNode() {
		if(candidates.size() ==0){
			this.setCheck(false);
			return false;
		}
		int[] last;
		int val, h;
		
		boolean[] hashesToCheck = new boolean[this.getHash()];
		for (Series series : candidates) {
			last =series.getLastItem();
			val =last[last.length-1];
			h = hash(val);
			hashesToCheck[h] = true;
		}
		this.setHashesToCheck(hashesToCheck);
		this.setCheck(true);
		return true;
	}
	
	
	
}
