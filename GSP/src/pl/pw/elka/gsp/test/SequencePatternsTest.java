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
			seqPatt.setMinSupp(4);
			
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
	
	@Test
	public void testRemovingLastFromItemSet(){
		
		Series s = new Series();
		ItemSet itemSet = new ItemSet(0);
		s.addItemSet(0L, itemSet);
		itemSet = new ItemSet();
		int[] list = new int[2];
		list[0] =3;
		list[1] =1;
		itemSet.setItems(list);
		s.addItemSet(1L, itemSet);
		
		ArrayList<Series> newseries = s.getSeriesByRemovingLast();
		
		assertEquals(2, newseries.size());
		assertEquals(3, newseries.get(0).getDataSeq().get(1L).getItems()[0]);
		assertEquals(1, newseries.get(1).getDataSeq().get(1L).getItems()[0]);
		
		list = new int[3];
		list[0] =3;
		list[1] =1;
		list[2] =7;
		itemSet.setItems(list);
		s.addItemSet(1L, itemSet);
		newseries = s.getSeriesByRemovingLast();
		assertEquals(3, newseries.size());
		assertEquals(3, newseries.get(0).getDataSeq().get(1L).getItems()[0]);
		assertEquals(7, newseries.get(0).getDataSeq().get(1L).getItems()[1]);
		
		assertEquals(1, newseries.get(1).getDataSeq().get(1L).getItems()[0]);
		assertEquals(7, newseries.get(1).getDataSeq().get(1L).getItems()[1]);
		
		assertEquals(1, newseries.get(2).getDataSeq().get(1L).getItems()[0]);
		assertEquals(3, newseries.get(2).getDataSeq().get(1L).getItems()[1]);
		
		s = new Series();
		list = new int[3];
		list[0] =3;
		list[1] =1;
		list[2] =7;
		itemSet.setItems(list);
		s.addItemSet(0L, itemSet);
		
		newseries = s.getSeriesByRemovingLast();
		assertEquals(3, newseries.size());
		assertEquals(3, newseries.get(0).getDataSeq().get(0L).getItems()[0]);
		assertEquals(7, newseries.get(0).getDataSeq().get(0L).getItems()[1]);
		
		assertEquals(1, newseries.get(1).getDataSeq().get(0L).getItems()[0]);
		assertEquals(7, newseries.get(1).getDataSeq().get(0L).getItems()[1]);
		
		assertEquals(1, newseries.get(2).getDataSeq().get(0L).getItems()[0]);
		assertEquals(3, newseries.get(2).getDataSeq().get(0L).getItems()[1]);
		
	}
	
	@Test
	public void testRemovingFirstFromItemSet(){
		
		Series s = new Series();
		ItemSet itemSet = new ItemSet(0);
		s.addItemSet(1L, itemSet);
		itemSet = new ItemSet();
		int[] list = new int[2];
		list[0] =3;
		list[1] =1;
		itemSet.setItems(list);
		s.addItemSet(0L, itemSet);
		
		ArrayList<Series> newseries = s.getSeriesByRemovingFirst();
		
		assertEquals(2, newseries.size());
		assertEquals(3, newseries.get(0).getDataSeq().get(1L).getItems()[0]);
		assertEquals(1, newseries.get(1).getDataSeq().get(1L).getItems()[0]);
		
		list = new int[3];
		list[0] =3;
		list[1] =1;
		list[2] =7;
		itemSet.setItems(list);
		s.addItemSet(0L, itemSet);
		newseries = s.getSeriesByRemovingFirst();
		assertEquals(3, newseries.size());
		assertEquals(3, newseries.get(0).getDataSeq().get(1L).getItems()[0]);
		assertEquals(7, newseries.get(0).getDataSeq().get(1L).getItems()[1]);
		
		assertEquals(1, newseries.get(1).getDataSeq().get(1L).getItems()[0]);
		assertEquals(7, newseries.get(1).getDataSeq().get(1L).getItems()[1]);
		
		assertEquals(1, newseries.get(2).getDataSeq().get(1L).getItems()[0]);
		assertEquals(3, newseries.get(2).getDataSeq().get(1L).getItems()[1]);
		
		s = new Series();
		list = new int[3];
		list[0] =3;
		list[1] =1;
		list[2] =7;
		itemSet.setItems(list);
		s.addItemSet(0L, itemSet);
		
		newseries = s.getSeriesByRemovingFirst();
		assertEquals(3, newseries.size());
		assertEquals(3, newseries.get(0).getDataSeq().get(0L).getItems()[0]);
		assertEquals(7, newseries.get(0).getDataSeq().get(0L).getItems()[1]);
		
		assertEquals(1, newseries.get(1).getDataSeq().get(0L).getItems()[0]);
		assertEquals(7, newseries.get(1).getDataSeq().get(0L).getItems()[1]);
		
		assertEquals(1, newseries.get(2).getDataSeq().get(0L).getItems()[0]);
		assertEquals(3, newseries.get(2).getDataSeq().get(0L).getItems()[1]);
		
		//
	}
	
	@Test
	public void seriesEqualsTest(){
		
		Series s1 = new Series();
		ItemSet itemSet = new ItemSet();
		int[] list = new int[3];
		list[0] =3;
		list[1] =1;
		list[2] =7;
		itemSet.setItems(list);
		s1.addItemSet(0L, itemSet);
		//itemSet = new ItemSet(0);
		//s1.addItemSet(0L, itemSet);
	
		Series s2 = new Series();
		list = new int[3];
		list[0] =3;
		list[1] =1;
		list[2] =7;
		itemSet.setItems(list);
		s2.addItemSet(0L, itemSet);
		//itemSet = new ItemSet(3);
		//s2.addItemSet(1L, itemSet);
		//itemSet = new ItemSet();
		
		assertEquals(s1,s2);
		
		s1 = new Series();
		itemSet = new ItemSet();
		list = new int[3];
		list[0] =3;
		list[1] =1;
		list[2] =7;
		itemSet.setItems(list);
		s1.addItemSet(0L, itemSet);
		
		itemSet = new ItemSet(4);
		s1.addItemSet(1L, itemSet);
	
		 s2 = new Series();
		 itemSet = new ItemSet();
		list = new int[3];
		list[0] =3;
		list[1] =1;
		list[2] =7;
		itemSet.setItems(list);
		s2.addItemSet(1L, itemSet);
		itemSet = new ItemSet(0);
		s2.addItemSet(0L, itemSet);
		
		assertNotEquals(s1,s2);
		
		ArrayList<Series> s1ser = s1.getSeriesByRemovingLast();
		ArrayList<Series> s2ser = s2.getSeriesByRemovingFirst();
		
		assertEquals(s1ser.get(0), s2ser.get(0));
		assertEquals(1, s1ser.size());
		assertEquals(1, s2ser.size());
		assertEquals("0", s2ser.get(0).getSeriesName());		
		
	}
	
	@Test
	public void generateByRemovingFirst(){
		
		Series s1 = new Series();
		ItemSet itemSet = new ItemSet();
		int[] list = new int[1];
		list[0] =1;
		//list[1] =2;
		//list[2] =3;
		itemSet.setItems(list);
		s1.addItemSet(0L, itemSet);
		
		itemSet = new ItemSet();
		list = new int[3];
		list[0] =4;
		list[1] =5;
		list[2] =6;
		itemSet.setItems(list);
		s1.addItemSet(1L, itemSet);
	
		Series s2 = new Series();
		itemSet = new ItemSet();
		list = new int[3];
		list[0] =4;
		list[1] =5;
		list[2] =6;
		itemSet.setItems(list);
		s2.addItemSet(0L, itemSet);
		
		System.out.println(s1);
		System.out.println(s2);
		
		ArrayList<Series> newseries = new ArrayList<Series>();
		newseries = s1.getSeriesByRemovingFirst();
		
		assertEquals(1, newseries.size());
		assertEquals(s2, newseries.get(0));
		
		///////////////////////////////////////////////////////////////////////////
		System.out.println("second stage");
		
		
		s1 = new Series();
		 itemSet = new ItemSet();
		 list = new int[3];
		list[0] =1;
		list[1] =2;
		list[2] =3;
		itemSet.setItems(list);
		s1.addItemSet(0L, itemSet);
		
		itemSet = new ItemSet();
		list = new int[3];
		list[0] =4;
		list[1] =5;
		list[2] =6;
		itemSet.setItems(list);
		s1.addItemSet(1L, itemSet);
	
		System.out.println("s1: " + s1);
		newseries = s1.getSeriesByRemovingFirst();
		assertEquals(3, newseries.size());
		
		s2 = new Series();
		itemSet = new ItemSet();
		list = new int[2];
		list[0] =2;
		list[1] =3;
		itemSet.setItems(list);
		s2.addItemSet(0L, itemSet);

		itemSet = new ItemSet();
		list = new int[3];
		list[0] =4;
		list[1] =5;
		list[2] =6;
		itemSet.setItems(list);
		s2.addItemSet(1L, itemSet);
		
		System.out.println("s2: " + s2);
		assertEquals(s2, newseries.get(0));
		
		s2 = new Series();
		itemSet = new ItemSet();
		list = new int[2];
		list[0] =1;
		list[1] =3;
		itemSet.setItems(list);
		s2.addItemSet(0L, itemSet);

		itemSet = new ItemSet();
		list = new int[3];
		list[0] =4;
		list[1] =5;
		list[2] =6;
		itemSet.setItems(list);
		s2.addItemSet(1L, itemSet);
		
		System.out.println("s2: " + s2);
		assertEquals(s2, newseries.get(1));
		
		s2 = new Series();
		itemSet = new ItemSet();
		list = new int[2];
		list[0] =1;
		list[1] =2;
		itemSet.setItems(list);
		s2.addItemSet(0L, itemSet);

		itemSet = new ItemSet();
		list = new int[3];
		list[0] =4;
		list[1] =5;
		list[2] =6;
		itemSet.setItems(list);
		s2.addItemSet(1L, itemSet);
		
		System.out.println("s2: " + s2);
		assertEquals(s2, newseries.get(2));
		
	}
	
}
