package pl.pw.elka.gsp.algorithm;

import java.util.ArrayList;

public abstract class  Node {

	private int level, threshold, hash;
	private InteriorNode parent;
	private boolean check;
	private boolean[] hashesToCheck;
	
	public Node(int hash, int threshold){
		this.hash = hash;
		this.threshold = threshold;
	}
	
	public abstract void addSeries(Series series);
	
	public abstract void print(String offset);
	
	public abstract ArrayList<Series> getCandidateSeries(int minSupp);
	public abstract ArrayList<Series> getCandidateSeries();
	
	public abstract boolean checkNode();
	
	public String toCheckToString(){
		if(!check){
			return "do not check";
		}
		String s = "[";
		for (int i=0; i< hashesToCheck.length; i++) {
			if(hashesToCheck[i]){
				s += i+",";
			}
		}
		s += "]";
		return s;
	}
	
	
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

	public boolean isCheck() {
		return check;
	}

	public void setCheck(boolean check) {
		this.check = check;
	}

	public boolean[] getHashesToCheck() {
		return hashesToCheck;
	}

	public void setHashesToCheck(boolean[] hashesToCheck) {
		this.hashesToCheck = hashesToCheck;
	}
}
