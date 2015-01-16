package pl.pw.elka.gsp.algorithm;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Set;

public class CandidateHashTree {

	private int hash, threshold;
	private InteriorNode root;
	private int windowSize, maxGap, wsPlusGap;
	private HashMap<Long, ItemSet> elements ;
	
	public CandidateHashTree(int hash, int threshold){
		root = new InteriorNode(hash, threshold);
		this.hash = hash;
		this.threshold = threshold;
		this.windowSize =0;
	}
	
	public void clear(){
		root = new InteriorNode(hash, threshold);
	}
	
	
	public void add(Series series) {
				
		//System.out.println(series);
		root.addSeries(series);
		
	}

	public void checkSeries(Series series) {
		long[] dates = series.getDatesOrdered();
		elements = series.getDataSeq();
		Node node = root;
		for (long date : dates) {
			int[] items = elements.get(date).getItems();
			for (int i : items) {
				if(node.getHashesToCheck()[node.hash(i)]){
					//System.out.println("it is " + i + " t= " + date);
					checkNode(node, series, date, i);
				}
			}			
		}
	}
	
	
	private void checkNode(Node node, Series series, long t, int i) {
		
		if(node instanceof Leaf){
			ArrayList<Series> candidates = node.getCandidateSeries();
			for (Series s : candidates) {
				//inaczej
				ItemSet is = new ItemSet(i);
				if(s.getDataSeq().get(s.getLastDate()).contains(is)){
					//s.supportIncr();
					s.addSupportedByHash(series.getSeriesName());
					//System.out.println("increase for " + s);
					
				}
			}
		}else{
			InteriorNode interNode = (InteriorNode) node;
			if(interNode.getHashesToCheck()[i]){
				node =interNode.getChildren().get(i);
				int hash = node.hash(i);
				long[] dates = series.getDatesOrdered();
				
				for (long date : dates) {
					if(date >= (t-windowSize) && date<= (t+wsPlusGap)){
						
						int[] items = elements.get(date).getItems();
						for (int j : items) {
							if(node.getHashesToCheck()[node.hash(j)]){
								//System.out.println("it is internal for " + j + " t= " + date);
								checkNode(node, series, date, j);
							}
						}	
					}
					
				}
				
			}
		}	
	}

	public int getThreshold() {
		return threshold;
	}

	public void setThreshold(int threshold) {
		this.threshold = threshold;
	}

	public int getHash() {
		return hash;
	}

	public void setHash(int hash) {
		this.hash = hash;
	}

	public InteriorNode getRoot() {
		return root;
	}

	public void setRoot(InteriorNode root) {
		this.root = root;
	}

	public void checkTree() {
		root.checkNode();
		
	}

	public int getWindowSize() {
		return windowSize;
	}

	public void setWindowSize(int windowSize) {
		this.windowSize = windowSize;
		this.wsPlusGap = Math.max(this.windowSize, this.maxGap);
	}

	public int getMaxGap() {
		return maxGap;
	}

	public void setMaxGap(int maxGap) {
		this.maxGap = maxGap;
		this.wsPlusGap = Math.max(this.windowSize, this.maxGap);
	}

	public int getWsPlusGap() {
		return wsPlusGap;
	}

	public void setWsPlusGap(int wsPlusGap) {
		this.wsPlusGap = wsPlusGap;
	}

	
	


}
