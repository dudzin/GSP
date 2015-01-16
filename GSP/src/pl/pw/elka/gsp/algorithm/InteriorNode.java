package pl.pw.elka.gsp.algorithm;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Set;

public class InteriorNode extends Node{

	private HashMap<Integer, Node> children;
	
	public InteriorNode(int hash, int threshold){
		super(hash, threshold);
		children = new HashMap<Integer, Node>();

		for (int i=0; i<hash ;i++) {
			Leaf l = new Leaf(hash, threshold);
			l.setLevel(getLevel()+1);
			children.put(i, l);
		}
		
	}
	
	public HashMap<Integer, Node> getChildren() {
		return children;
	}

	public void setChildren(HashMap<Integer, Node> children) {
		this.children = children;
	}

	public void addSeries(Series series) {
		
		int[] keys = series.getItemsOrdered();
		int hashValue = hash(keys[getLevel()]);
		
		if(children.get(hashValue) instanceof Leaf){
			Leaf leaf = (Leaf) children.get(hashValue);
			if(leaf.canStoreMore()){
				leaf.addSeries(series);
			}else{
				InteriorNode newnode = new InteriorNode(getHash(), getThreshold());
				newnode.setLevel(getLevel()+1);
				newnode.setParent(this);
				ArrayList<Series> candidatesFromLeaf = leaf.getCandidates();
				for (Series candidate : candidatesFromLeaf) {
					newnode.addSeries(candidate);
				}
				newnode.addSeries(series);
				children.put(hashValue,newnode);
			}
		}else {
			children.get(hashValue).addSeries(series);
		}
		
	}
	
	public void print(String offset){
		
		System.out.println(offset + "InteriorNode level: " + getLevel() + " check: " + this.isCheck() + " hashes " + this.toCheckToString());
		for(int i=0; i<children.size();i++){
			System.out.println(offset + "|- child (hash):" + i);
			children.get(i).print(offset + "  ");
		}
	
	}
	

	public ArrayList<Series> getCandidateSeries(int minSupp){
		ArrayList<Series> all = new ArrayList<Series>();
		
		Set<Integer> keys = children.keySet();
		
		for (Integer key : keys) {
			all.addAll(children.get(key).getCandidateSeries(minSupp));
		}
		
		return all;
	}
	
	public ArrayList<Series> getCandidateSeries(){
		ArrayList<Series> all = new ArrayList<Series>();
		
		Set<Integer> keys = children.keySet();
		
		for (Integer key : keys) {
			all.addAll(children.get(key).getCandidateSeries());
		}
		
		return all;
	}

	@Override
	public boolean checkNode() {
		Set<Integer> keys = children.keySet();
		boolean[] hashes =  new boolean[this.getHash()];
		for (Integer key : keys) {
			if(children.get(key).checkNode()){
				hashes[key] = true;
			}
		}
		this.setCheck(true);
		this.setHashesToCheck(hashes);
		return true;
	}

	
}
