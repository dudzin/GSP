package pl.pw.elka.gsp.algorithm;

import java.util.ArrayList;

public abstract class  Node {

	private int level, threshold, hash;
	private InteriorNode parent;
	
	public Node(int hash, int threshold){
		this.hash = hash;
		this.threshold = threshold;
	}
	
	public abstract void addSeries(Series series);
	
	public abstract void print(String offset);
	
	public abstract ArrayList<Series> getCandidateSeries();
	
	public int getLevel() {
		return level;
	}

	public void setLevel(int level) {
		this.level = level;
	}

	public InteriorNode getParent() {
		return parent;
	}

	public void setParent(InteriorNode parent) {
		this.parent = parent;
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
	
	public int hash(int key){
		return key % getHash();
	}
}
