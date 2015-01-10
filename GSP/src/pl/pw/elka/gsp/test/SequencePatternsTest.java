package pl.pw.elka.gsp.test;

import static org.junit.Assert.*;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Set;

import org.junit.Test;

import pl.pw.elka.gsp.algorithm.CSVReader;
import pl.pw.elka.gsp.algorithm.CandidateHashTree;
import pl.pw.elka.gsp.algorithm.InteriorNode;
import pl.pw.elka.gsp.algorithm.ItemSet;
import pl.pw.elka.gsp.algorithm.Leaf;
import pl.pw.elka.gsp.algorithm.Node;
import pl.pw.elka.gsp.algorithm.SequencePatterns;
import pl.pw.elka.gsp.algorithm.Series;

public class SequencePatternsTest {

	
	@Test
	public void initDataSeriesTest() throws ParseException{
		
		System.out.println("\n initTreeTest() \n");
		

		SequencePatterns seqPatt = initSeqPatt("testdata/test1.csv", true);
		
		seqPatt.generateCandidates();
		
		HashMap<String, Series> candidates = seqPatt.getCandidates();
		Set<String> keys = candidates.keySet();	
		assertEquals(36,keys.size());
		
	}
	
	
	@Test
	public void buildCandidateHashTree() throws ParseException{
		
		System.out.println("\n buildCandidateHashTree() \n");
		
		SequencePatterns seqPatt = initSeqPatt("testdata/test1.csv", false);
		seqPatt.generateCandidates();
		
		seqPatt.buildCandidateHashTree();
		
		Leaf l ;
		HashMap<Integer, Node> children = seqPatt.getCandidateTree().getRoot().getChildren();
		Set<Integer> keys = children.keySet();
		for (Integer integer : keys) {
			l = (Leaf) children.get(integer);
			assertEquals(1, l.getCandidates().get(0).getDataSeq().size() );
		}
		

		System.out.println("\n validateSupport() \n");
		
		System.out.println("\n checkSupport() \n");
		seqPatt.checkSupport();
		System.out.println("\n checkSupport() end\n");
		
		ArrayList<Series> supported = seqPatt.getSupportedCandidates();
		
		System.out.println("suported series:");
		for (Series series : supported) {
			System.out.println(series);
		}
		assertEquals(1, supported.size());
		
	}
	
	@Test
	public void buildCandidateHashTree2() throws ParseException{
		
		System.out.println("\n buildCandidateHashTree2() \n");
		
		SequencePatterns seqPatt = initSeqPatt("testdata/test1.csv", true);
		seqPatt.generateCandidates();
		
		seqPatt.buildCandidateHashTree();
		
		Leaf l ;
		HashMap<Integer, Node> children = seqPatt.getCandidateTree().getRoot().getChildren();
		Set<Integer> keys = children.keySet();
		for (Integer integer : keys) {
			l = (Leaf) children.get(integer);
			assertEquals(1, l.getCandidates().get(0).getDataSeq().size() );
		}
		

		System.out.println("\n validateSupport() \n");
		
		System.out.println("\n checkSupport() \n");
		seqPatt.checkSupport();
		System.out.println("\n checkSupport() end\n");
		
		ArrayList<Series> supported = seqPatt.getSupportedCandidates();
		
		System.out.println("suported series:");
		for (Series series : supported) {
			System.out.println(series);
		}
		assertEquals(6, supported.size());
		
	}
	
	@Test
	public void buildCandidateHashTree2Lev() throws ParseException{
		
		System.out.println("\n buildCandidateHashTree2() \n");
		
		SequencePatterns seqPatt = initSeqPatt("testdata/test1.csv", true);
		seqPatt.generateCandidates();
		seqPatt.buildCandidateHashTree();
		
		Leaf l ;
		HashMap<Integer, Node> children = seqPatt.getCandidateTree().getRoot().getChildren();
		Set<Integer> keys = children.keySet();
		
		for (Integer integer : keys) {
			l = (Leaf) children.get(integer);
			assertEquals(1, l.getCandidates().get(0).getDataSeq().size() );
		}
		
		
		
		
		System.out.println("\n checkSupport() \n");
		seqPatt.checkSupport();
		System.out.println("\n checkSupport() end\n");
		
		//System.out.println("\n printtree() \n");
		//seqPatt.getCandidateTree().getRoot().print("");
		//System.out.println("\n printtree()  end\n");
		
		ArrayList<Series> supported = seqPatt.getSupportedCandidates();
		System.out.println("suported series:");
		for (Series series : supported) {
			System.out.println(series);
		}
		
		System.out.println("\n Level 2 \n");
		
		seqPatt.generateCandidates();
		assertEquals(51,seqPatt.getCandidates().size());
		seqPatt.buildCandidateHashTree();
		
		System.out.println("\n checkSupport() \n");
		seqPatt.checkSupport();
		System.out.println("\n checkSupport() end\n");
		
		//System.out.println("\n printtree2() \n");
		//seqPatt.getCandidateTree().getRoot().print("");
		//System.out.println("\n printtree2()  end\n");
		
		children = seqPatt.getCandidateTree().getRoot().getChildren();
		supported = seqPatt.getSupportedCandidates();
		System.out.println("suported series:");
		for (Series series : supported) {
			System.out.println(series);
		}
		//assertEquals(15, supported.size());
		assertEquals(27, supported.size());
		
		System.out.println("\n Level 3 \n");
		seqPatt.generateCandidates();
		seqPatt.buildCandidateHashTree();
		
		System.out.println("\n checkSupport() \n");
		seqPatt.checkSupport();
		System.out.println("\n checkSupport() end\n");
		
		//System.out.println("\n printtree3() \n");
		//seqPatt.getCandidateTree().getRoot().print("");
		//System.out.println("\n printtree3()  end\n");
		
		children = seqPatt.getCandidateTree().getRoot().getChildren();
		
		
		
		supported = seqPatt.getSupportedCandidates();
		System.out.println("suported series:");
		for (Series series : supported) {
			System.out.println(series);
		}
		assertEquals(56, supported.size());
		
		System.out.println("\n Level 4 \n");
		seqPatt.generateCandidates();
		seqPatt.buildCandidateHashTree();
		
		System.out.println("\n checkSupport() \n");
		seqPatt.checkSupport();
		System.out.println("\n checkSupport() end\n");
				
		children = seqPatt.getCandidateTree().getRoot().getChildren();
		
		supported = seqPatt.getSupportedCandidates();
		System.out.println("suported series:");
		for (Series series : supported) {
			System.out.println(series);
		}
		assertEquals(70, supported.size());
		
		
		supported = seqPatt.getResultSeries();
		System.out.println("all  series:");
		for (Series series : supported) {
			System.out.println(series);
		}
	}

	
	private SequencePatterns initSeqPatt(String path, boolean hierarchy) throws ParseException{
		CSVReader reader = new CSVReader();
		HashMap<String, Series>  testSeries = reader.read(path, null, hierarchy);
		
				
		SequencePatterns seqPatt = new SequencePatterns(path);
		seqPatt.setSeries(testSeries);
		seqPatt.setMinSupp(2);
		seqPatt.setDictionary(reader.getDictionary());
		seqPatt.setMinGap(1);
		seqPatt.setMaxGap(8);//6
		
		return seqPatt;
	}
	
	
	
	@Test
	public void testHashTreeLevel1(){
		
		System.out.println("\n testHashTreeLevel1() \n");
		
		CandidateHashTree tree = initTree();
		
		Leaf l = (Leaf)tree.getRoot().getChildren().get(0);
		
		
		assertEquals(1, l.getCandidates().get(0).getDataSeq().size() );
		assertEquals(2, l.getCandidates().get(1).getDataSeq().size() );
		assertEquals(2, l.getCandidates().get(2).getDataSeq().size() );
		
		l = (Leaf)tree.getRoot().getChildren().get(1);
		
		assertEquals(1, l.getCandidates().get(0).getDataSeq().size() );
		
		l = (Leaf)tree.getRoot().getChildren().get(2);
		
		assertEquals(2, l.getCandidates().get(0).getDataSeq().size() );
		
	}
	
	@Test
	public void testHashTreeLevel2(){
		
		System.out.println("\n testHashTreeLevel2() \n");
		
		CandidateHashTree tree = initTree();
		
		Series s = new Series();
		ItemSet itemSet = new ItemSet();
		int[] list = {0,3};
		itemSet.setItems(list);
		s.addItemSet(1L, itemSet);
		tree.add(s);
		
		InteriorNode n = (InteriorNode)tree.getRoot().getChildren().get(0);
		
		Leaf l = (Leaf)n.getChildren().get(0);
		
		assertEquals(2, l.getCandidates().get(0).getDataSeq().size() );
		assertEquals(1, l.getCandidates().get(1).getDataSeq().size() );
		
		l = (Leaf)n.getChildren().get(1);
		
		assertEquals(1, l.getCandidates().get(0).getDataSeq().size() );
		
		l = (Leaf)n.getChildren().get(2);
		
		assertEquals(2, l.getCandidates().get(0).getDataSeq().size() );
		
		l = (Leaf)tree.getRoot().getChildren().get(1);
		
		assertEquals(1, l.getCandidates().get(0).getDataSeq().size() );
		
		l = (Leaf)tree.getRoot().getChildren().get(2);
		
		assertEquals(2, l.getCandidates().get(0).getDataSeq().size() );
		
	}
	
	@Test
	public void runTest() throws ParseException{			
			System.out.println("\n runTest() \n");
			
			
			SequencePatterns seqPatt = initSeqPatt("testdata/test3.csv", true);
			seqPatt.setMaxGap(8);
			seqPatt.setMinSupp(2);
			
			seqPatt.runAlgorithm();
			
			
			
			ArrayList<Series> result = seqPatt.getResultSeries();
			/* statistics:
			 * max gap 8
			 * min sup 2
			 * file 3
			 * hierarchies: true
			 * time: 239290ms
			 * steps: 17
			 * sequences: 85604
			 * */
			
			
			//for (Series series : result) {
			//	System.out.println(series);
			//}
	}
	
	
	private CandidateHashTree initTree(){
		CandidateHashTree tree = new CandidateHashTree(3,3);
				
				Series s = new Series();
				ItemSet itemSet = new ItemSet();
				int[] list = {0,1};
				itemSet.setItems(list);
				s.addItemSet(0L, itemSet);
				tree.add(s);
				
				s = new Series();
				itemSet = new ItemSet(0);
				s.addItemSet(0L, itemSet);
				itemSet = new ItemSet(2);
				s.addItemSet(2L, itemSet);
				tree.add(s);
				
				s = new Series();
				itemSet = new ItemSet(0);
				s.addItemSet(0L, itemSet);
				itemSet = new ItemSet(3);
				s.addItemSet(3L, itemSet);
				tree.add(s);
				
				s = new Series();
				itemSet = new ItemSet();
				list = new int[2];
				list[0] =3;
				list[1] =1;
				itemSet.setItems(list);
				
				s.addItemSet(1L, itemSet);
				tree.add(s);
				
				s = new Series();
				itemSet = new ItemSet(1);
				s.addItemSet(3L, itemSet);
				itemSet = new ItemSet(2);
				list = new int[2];
				list[0] =3;
				list[1] =2;
				itemSet.setItems(list);
				s.addItemSet(1L, itemSet);
				tree.add(s);
				
				return tree;
			}
}
