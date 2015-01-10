package pl.pw.elka.gsp.algorithm;

import java.util.Set;

public class CandidateHashTree {

	private int hash, threshold;
	private InteriorNode root;
	
	public CandidateHashTree(int hash, int threshold){
		root = new InteriorNode(hash, threshold);
		this.hash = hash;
		this.threshold = threshold;
	}
	
	public void clear(){
		root = new InteriorNode(hash, threshold);
	}
	
	
	public void add(Series series) {
				
		//System.out.println(series);
		root.addSeries(series);
		
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
	


}
