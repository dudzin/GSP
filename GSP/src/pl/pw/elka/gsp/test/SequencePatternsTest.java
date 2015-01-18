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
import pl.pw.elka.gsp.algorithm.ItemSetWithWindow;
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
		seqPatt.checkSupport(false);
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
		seqPatt.checkSupport(false);
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
		seqPatt.checkSupport(false);
		System.out.println("\n checkSupport() end\n");
		
		
		seqPatt.checkTree();
		System.out.println("\n printtree() \n");
		seqPatt.getCandidateTree().getRoot().print("");
		System.out.println("\n printtree()  end\n");
		
		ArrayList<Series> supported = seqPatt.getSupportedCandidates();
		System.out.println("suported series:");
		for (Series series : supported) {
			System.out.println(series);
		}
		
		
		System.out.println("\n Level 2 \n");
		
		seqPatt.generateCandidates();
		HashMap<String, Series> candidates = seqPatt.getCandidates();
		System.out.println("generated series:");
		Set<String> keySet = candidates.keySet();
		
		for (String key : keySet) {
			System.out.println(key );
		}
		
		assertEquals(51,seqPatt.getCandidates().size());
		seqPatt.buildCandidateHashTree();
		
		System.out.println("\n checkSupport() \n");
		seqPatt.checkSupport(false);
		System.out.println("\n checkSupport() end\n");
		
		seqPatt.checkTree();
		System.out.println("\n printtree() \n");
		seqPatt.getCandidateTree().getRoot().print("");
		System.out.println("\n printtree()  end\n");
		
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
		seqPatt.checkSupport(false);
		System.out.println("\n checkSupport() end\n");
		
		seqPatt.checkTree();
		System.out.println("\n printtree() \n");
		seqPatt.getCandidateTree().getRoot().print("");
		System.out.println("\n printtree()  end\n");
		
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
		seqPatt.checkSupport(false);
		System.out.println("\n checkSupport() end\n");
				
		children = seqPatt.getCandidateTree().getRoot().getChildren();
		
		supported = seqPatt.getSupportedCandidates();
		System.out.println("suported series:");
		for (Series series : supported) {
			System.out.println(series);
		}
		assertEquals(70, supported.size());
		
		seqPatt.checkTree();
		System.out.println("\n printtree() \n");
		seqPatt.getCandidateTree().getRoot().print("");
		System.out.println("\n printtree()  end\n");
		
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
		seqPatt.setHierarchy(hierarchy);
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
			
			
			SequencePatterns seqPatt = initSeqPatt("testdata/test2.csv", true); //taxonomies
			seqPatt.setMaxGap(49);
			seqPatt.setMinGap(14);
			seqPatt.setMinSupp(20);
			seqPatt.setWindowSize(2);
			seqPatt.setWithHashTree(true);
			seqPatt.runAlgorithm();// use hash tree
			
			
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
			
			/*for (Series s : seqPatt.getResultSeries() ) {
				System.out.println(seqPatt.translateSeries(s));
			}
			
			/*
			Set<String> keys = seqPatt.getSeries().keySet();
			for (String k : keys  ) {
				System.out.println(seqPatt.translateSeries(seqPatt.getSeries().get(k)));
			}
			*/
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
		assertEquals(s1, newseries.get(0).getParent());
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
		assertEquals(s1, newseries.get(0).getParent());
		assertEquals(s1, newseries.get(1).getParent());
		assertEquals(s1, newseries.get(2).getParent());
	}
	
	@Test
	public void generateByRemovingLast(){
		
		Series s1 = new Series();
		ItemSet itemSet = new ItemSet();
		int[] list = new int[3];
		list[0] =1;
		list[1] =2;
		list[2] =3;
		itemSet.setItems(list);
		s1.addItemSet(0L, itemSet);
		
		itemSet = new ItemSet();
		list = new int[1];
		list[0] =4;
		itemSet.setItems(list);
		s1.addItemSet(1L, itemSet);
	
		Series s2 = new Series();
		itemSet = new ItemSet();
		list = new int[3];
		list[0] =1;
		list[1] =2;
		list[2] =3;
		itemSet.setItems(list);
		s2.addItemSet(0L, itemSet);
		
		System.out.println(s1);
		System.out.println(s2);
		
		ArrayList<Series> newseries = new ArrayList<Series>();
		newseries = s1.getSeriesByRemovingLast();
		
		assertEquals(1, newseries.size());
		assertEquals(s2, newseries.get(0));
		assertEquals(s1, newseries.get(0).getParent());
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
		newseries = s1.getSeriesByRemovingLast();
		assertEquals(3, newseries.size());
		
		s2 = new Series();
		itemSet = new ItemSet();
		list = new int[3];
		list[0] =1;
		list[1] =2;
		list[2] =3;
		itemSet.setItems(list);
		s2.addItemSet(0L, itemSet);
		
		itemSet = new ItemSet();
		list = new int[2];
		list[0] =5;
		list[1] =6;
		itemSet.setItems(list);
		s2.addItemSet(1L, itemSet);
		
		System.out.println("s2: " + s2);
		assertEquals(s2, newseries.get(0));
		
		s2 = new Series();
		itemSet = new ItemSet();
		list = new int[3];
		list[0] =1;
		list[1] =2;
		list[2] =3;
		itemSet.setItems(list);
		s2.addItemSet(0L, itemSet);
		
		itemSet = new ItemSet();
		list = new int[2];
		list[0] =4;
		list[1] =6;
		itemSet.setItems(list);
		s2.addItemSet(1L, itemSet);
		
		System.out.println("s2: " + s2);
		assertEquals(s2, newseries.get(1));
		
		s2 = new Series();
		itemSet = new ItemSet();
		list = new int[3];
		list[0] =1;
		list[1] =2;
		list[2] =3;
		itemSet.setItems(list);
		s2.addItemSet(0L, itemSet);
		
		itemSet = new ItemSet();
		list = new int[2];
		list[0] =4;
		list[1] =5;
		itemSet.setItems(list);
		s2.addItemSet(1L, itemSet);
		
		System.out.println("s2: " + s2);
		assertEquals(s2, newseries.get(2));
		assertEquals(s1, newseries.get(0).getParent());
		assertEquals(s1, newseries.get(1).getParent());
		assertEquals(s1, newseries.get(2).getParent());
	}
	
	@Test
	public void canJoinTest(){
		
		Series s1 = new Series();
		ItemSet itemSet = new ItemSet();
		int[] list = new int[1];
		list[0] =1;	
		itemSet.setItems(list);
		s1.addItemSet(0L, itemSet);
		
		itemSet = new ItemSet();
		list = new int[3];
		list[0] =1;
		list[1] =2;
		list[2] =3;
		itemSet.setItems(list);
		s1.addItemSet(1L, itemSet);
	
		Series s2 = new Series();
		itemSet = new ItemSet();
		list = new int[3];
		list[0] =1;
		list[1] =2;
		list[2] =3;
		itemSet.setItems(list);
		s2.addItemSet(0L, itemSet);
		
		itemSet = new ItemSet();
		list = new int[1];
		list[0] =6;
		itemSet.setItems(list);
		s2.addItemSet(1L, itemSet);
		
		System.out.println(s1);
		System.out.println(s2);
		
		ArrayList<Series> newseries1 = new ArrayList<Series>();
		ArrayList<Series> newseries2 = new ArrayList<Series>();
		newseries1 = s1.getSeriesByRemovingFirst();
		newseries2 = s2.getSeriesByRemovingLast();
		
		assertEquals(newseries1.get(0), newseries2.get(0));
		
		////////////////////////////////////////
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
	
		s2 = new Series();
		itemSet = new ItemSet();
		list = new int[2];
		list[0] =2;
		list[1] =3;
		//list[2] =3;
		itemSet.setItems(list);
		s2.addItemSet(0L, itemSet);
		
		itemSet = new ItemSet();
		list = new int[3];
		list[0] =4;
		list[1] =5;
		list[2] =6;
		itemSet.setItems(list);
		s2.addItemSet(1L, itemSet);
		
		itemSet = new ItemSet();
		list = new int[1];
		list[0] =10;
		itemSet.setItems(list);
		s2.addItemSet(3L, itemSet);
		
		System.out.println(s1);
		System.out.println(s2);
		
		newseries1 = s1.getSeriesByRemovingFirst();
		newseries2 = s2.getSeriesByRemovingLast();
		
		assertEquals(3, newseries1.size());
		assertEquals(1, newseries2.size());
		
		assertEquals(newseries1.get(0), newseries2.get(0));
		assertNotEquals(newseries1.get(1), newseries2.get(0));
		assertNotEquals(newseries1.get(2), newseries2.get(0));
		
		////////////////////////////////////////
		
		System.out.println("third stage");
		
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
	
		s2 = new Series();
		itemSet = new ItemSet();
		list = new int[2];
		list[0] =2;
		list[1] =3;
		//list[2] =3;
		itemSet.setItems(list);
		s2.addItemSet(0L, itemSet);
		
		itemSet = new ItemSet();
		list = new int[4];
		list[0] =4;
		list[1] =5;
		list[2] =6;
		list[3] =7;
		itemSet.setItems(list);
		s2.addItemSet(1L, itemSet);
		
		
		System.out.println(s1);
		System.out.println(s2);
		
		newseries1 = s1.getSeriesByRemovingFirst();
		newseries2 = s2.getSeriesByRemovingLast();
		
		assertEquals(3, newseries1.size());
		assertEquals(4, newseries2.size());
		
		assertNotEquals(newseries1.get(0), newseries2.get(0));
		assertNotEquals(newseries1.get(1), newseries2.get(0));
		assertNotEquals(newseries1.get(2), newseries2.get(0));
		
		assertNotEquals(newseries1.get(0), newseries2.get(1));
		assertNotEquals(newseries1.get(1), newseries2.get(1));
		assertNotEquals(newseries1.get(2), newseries2.get(1));
		
		assertNotEquals(newseries1.get(0), newseries2.get(2));
		assertNotEquals(newseries1.get(1), newseries2.get(2));
		assertNotEquals(newseries1.get(2), newseries2.get(2));
		
		assertEquals(newseries1.get(0), newseries2.get(3));
		assertNotEquals(newseries1.get(1), newseries2.get(3));
		assertNotEquals(newseries1.get(2), newseries2.get(3));
		
	}
	
	@Test
	public void mergeTest(){
		
		Series s1 = new Series();
		ItemSet itemSet = new ItemSet();
		int[] list = new int[3];
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
	
		Series s2 = new Series();
		itemSet = new ItemSet();
		list = new int[2];
		list[0] =2;
		list[1] =3;
		//list[2] =3;
		itemSet.setItems(list);
		s2.addItemSet(0L, itemSet);
		
		itemSet = new ItemSet();
		list = new int[3];
		list[0] =4;
		list[1] =5;
		list[2] =7;
		itemSet.setItems(list);
		s2.addItemSet(1L, itemSet);
		
		
		System.out.println(s1);
		System.out.println(s2);
		
		ArrayList<Series> serieslist = s1.merge(s2);
		
		assertNull(serieslist);
		
		itemSet = new ItemSet();
		list = new int[4];
		list[0] =4;
		list[1] =5;
		list[2] =6;
		list[3] =7;
		itemSet.setItems(list);
		s2.addItemSet(1L, itemSet);
		
		
		System.out.println(s2);
		
		Series s4 = new Series();
		itemSet = new ItemSet();
		list = new int[3];
		list[0] =1;
		list[1] =2;
		list[2] =3;
		itemSet.setItems(list);
		s4.addItemSet(0L, itemSet);
		
		itemSet = new ItemSet();
		list = new int[4];
		list[0] =4;
		list[1] =5;
		list[2] =6;
		list[3] =7;
		itemSet.setItems(list);
		s4.addItemSet(1L, itemSet);
		
		
		serieslist = s1.merge(s2);
		Series s3 = serieslist.get(0);
		assertEquals(s4, s3);
		System.out.println(s3);
		
		///////////////////////////////
		System.out.println("stage second");
		
		
		s1 = new Series();
		itemSet = new ItemSet();
		list = new int[2];
		list[0] =1;
		list[1] =2;
		itemSet.setItems(list);
		s1.addItemSet(0L, itemSet);
		
		itemSet = new ItemSet();
		list = new int[1];
		list[0] =3;
		itemSet.setItems(list);
		s1.addItemSet(1L, itemSet);
	
		s2 = new Series();
		itemSet = new ItemSet();
		list = new int[1];
		list[0] =2;
		itemSet.setItems(list);
		s2.addItemSet(0L, itemSet);
		
		itemSet = new ItemSet();
		list = new int[2];
		list[0] =3;
		list[1] =4;
		itemSet.setItems(list);
		s2.addItemSet(1L, itemSet);
		
		
		System.out.println(s1);
		System.out.println(s2);
		
		s4 = new Series();
		itemSet = new ItemSet();
		list = new int[2];
		list[0] =1;
		list[1] =2;
		itemSet.setItems(list);
		s4.addItemSet(0L, itemSet);
		
		itemSet = new ItemSet();
		list = new int[2];
		list[0] =3;
		list[1] =4;
		itemSet.setItems(list);
		s4.addItemSet(1L, itemSet);
		
		serieslist = s1.merge(s2);
		s3 = serieslist.get(0);
		assertEquals(s4, s3);
		System.out.println(s3);
		
///////////////////////////////
		System.out.println("stage third");
		
		
		s1 = new Series();
		itemSet = new ItemSet();
		list = new int[2];
		list[0] =1;
		list[1] =2;
		itemSet.setItems(list);
		s1.addItemSet(0L, itemSet);
		
		itemSet = new ItemSet();
		list = new int[1];
		list[0] =3;
		itemSet.setItems(list);
		s1.addItemSet(1L, itemSet);
		
		s2 = new Series();
		itemSet = new ItemSet();
		list = new int[1];
		list[0] =2;
		itemSet.setItems(list);
		s2.addItemSet(0L, itemSet);
		
		itemSet = new ItemSet();
		list = new int[1];
		list[0] =3;
		itemSet.setItems(list);
		s2.addItemSet(1L, itemSet);
		
		itemSet = new ItemSet();
		list = new int[1];
		list[0] =5;
		itemSet.setItems(list);
		s2.addItemSet(2L, itemSet);
		
		System.out.println(s1);
		System.out.println(s2);
		
		s4 = new Series();
		itemSet = new ItemSet();
		list = new int[2];
		list[0] =1;
		list[1] =2;
		itemSet.setItems(list);
		s4.addItemSet(0L, itemSet);
		
		itemSet = new ItemSet();
		list = new int[1];
		list[0] =3;
		itemSet.setItems(list);
		s4.addItemSet(1L, itemSet);
		
		itemSet = new ItemSet();
		list = new int[1];
		list[0] =5;
		itemSet.setItems(list);
		s4.addItemSet(2L, itemSet);
		
		serieslist = s1.merge(s2);
		 s3 = serieslist.get(0);
		assertEquals(s4, s3);
		System.out.println(s3);
		
		//to test :0:[8] 1:[8]  &  supp: 2 , series :0:[5,8]  [ supp: 0 , series :0:[5,8] 1:[8] ,  supp: 0 , series :0:[5,8] 1:[8] ]
		
///////////////////////////////
		System.out.println("stage 4");
		
		
		s1 = new Series();
		itemSet = new ItemSet();
		list = new int[1];
		list[0] =11;
		itemSet.setItems(list);
		s1.addItemSet(0L, itemSet);
		
		itemSet = new ItemSet();
		list = new int[1];
		list[0] =8;
		itemSet.setItems(list);
		s1.addItemSet(1L, itemSet);
		
		s2 = new Series();
		itemSet = new ItemSet();
		list = new int[2];
		list[0] =5;
		list[1] =8;
		itemSet.setItems(list);
		s2.addItemSet(0L, itemSet);
		
		System.out.println(s1);
		System.out.println(s2);
		
		s4 = new Series();
		itemSet = new ItemSet();
		list = new int[1];
		list[0] =11;
		itemSet.setItems(list);
		s4.addItemSet(0L, itemSet);
		
		itemSet = new ItemSet();
		list = new int[2];
		list[0] =5;
		list[1] =8;
		itemSet.setItems(list);
		s4.addItemSet(1L, itemSet);

		
		serieslist = s1.merge(s2);
		s3 = serieslist.get(0);
		assertEquals(s4, s3);
		System.out.println(s3);

		//from  supp: 2 , series :0:[10,11]  &  supp: 2 , series :0:[9,11]  [ supp: 0 , series :0:[9,11] ,  
		                                                                    
		System.out.println("stage 5");
		
		
		s1 = new Series();
		itemSet = new ItemSet();
		list = new int[2];
		list[0] =10;
		list[1] =11;
		itemSet.setItems(list);
		s1.addItemSet(0L, itemSet);
		
		s2 = new Series();
		itemSet = new ItemSet();
		list = new int[2];
		list[0] =9;
		list[1] =11;
		itemSet.setItems(list);
		s2.addItemSet(0L, itemSet);
		
		System.out.println(s1);
		System.out.println(s2);
		
		s4 = new Series();
		itemSet = new ItemSet();
		list = new int[3];
		list[0] =9;
		list[1] =10;
		list[2] =11;
		itemSet.setItems(list);
		s4.addItemSet(0L, itemSet);
		

		
		serieslist = s1.merge(s2);
		s3 = serieslist.get(0);
		assertEquals(s4, s3);
		System.out.println(s3);
		
		////////////////////
		System.out.println("stage 6");
		
		
		s1 = new Series();
		itemSet = new ItemSet();
		list = new int[2];
		list[0] =9;
		list[1] =11;
		itemSet.setItems(list);
		s1.addItemSet(0L, itemSet);
		
		s2 = new Series();
		itemSet = new ItemSet();
		list = new int[2];
		list[0] =9;
		list[1] =11;
		itemSet.setItems(list);
		s2.addItemSet(0L, itemSet);
		
		System.out.println(s1);
		System.out.println(s2);
		
		serieslist = s1.merge(s2);
		
		assertEquals(null, serieslist);
		System.out.println(s3);

	}
	
	@Test
	public void checkHashTree(){
		
		Series s1 = new Series();
		ItemSet itemSet = new ItemSet();
		int[] list = new int[2];
		list[0] =1;
		list[1] =2;
		itemSet.setItems(list);
		s1.addItemSet(0L, itemSet);
		
		itemSet = new ItemSet();
		list = new int[3];
		list[0] =3;
		list[1] =4;
		list[2] =5;
		itemSet.setItems(list);
		s1.addItemSet(1L, itemSet);
		
		itemSet = new ItemSet();
		list = new int[1];
		list[0] =6;
		itemSet.setItems(list);
		s1.addItemSet(2L, itemSet);
		
		itemSet = new ItemSet();
		list = new int[1];
		list[0] =7;
		itemSet.setItems(list);
		s1.addItemSet(3L, itemSet);
			
		Series s2 = new Series();
		itemSet = new ItemSet();
		list = new int[2];
		list[0] =0;
		list[1] =2;
		itemSet.setItems(list);
		s2.addItemSet(0L, itemSet);
		
		itemSet = new ItemSet();
		list = new int[1];
		list[0] =1;
		itemSet.setItems(list);
		s2.addItemSet(1L, itemSet);
		
		itemSet = new ItemSet();
		list = new int[1];
		list[0] =2;
		itemSet.setItems(list);
		s2.addItemSet(2L, itemSet);
		
		itemSet = new ItemSet();
		list = new int[1];
		list[0] =4;
		itemSet.setItems(list);
		s2.addItemSet(3L, itemSet);
		
		HashMap<String, Series>  testSeries = new HashMap<String, Series>();
		s1.setSeriesName("A");
		testSeries.put("A", s1);
		
		HashMap<Integer, String> dictionary = new HashMap<Integer, String>();
		dictionary.put(0, "A");
		dictionary.put(1, "B");
		dictionary.put(2, "C");
		dictionary.put(3, "D");
		dictionary.put(4, "E");
		dictionary.put(5, "F");
		dictionary.put(6, "G");
		dictionary.put(7, "H");
		
		SequencePatterns seqPatt = new SequencePatterns("");
		testSeries.put("A", s1);
		seqPatt.setSeries(testSeries);
		seqPatt.setMinSupp(1);
		seqPatt.setDictionary(dictionary);
		seqPatt.setMinGap(0);
		seqPatt.setMaxGap(1);//6
		
		seqPatt.generateCandidates();
		seqPatt.buildCandidateHashTree();
		seqPatt.checkTree();
		seqPatt.checkCandidateTree();
		seqPatt.checkSupport(true);
		
		System.out.println("\n printtree() \n");
		seqPatt.getCandidateTree().getRoot().print("");
		System.out.println("\n printtree()  end\n");
				
		
		ArrayList<Series> supported =seqPatt.getSupportedCandidates();
		  
		assertEquals(7, supported.size());
		
		
		
		System.out.println("Stage 2:");
		s2.setSeriesName("B");
		testSeries.put("B", s2);
		seqPatt = new SequencePatterns("");
		seqPatt.setSeries(testSeries);
		seqPatt.setMinSupp(2);
		seqPatt.setDictionary(dictionary);
		seqPatt.setMinGap(0);
		seqPatt.setMaxGap(1);//6
		
		
		seqPatt.generateCandidates();
		seqPatt.buildCandidateHashTree();
		seqPatt.checkTree();
		seqPatt.checkSupport(true);	
		System.out.println("\n printtree() \n");
		seqPatt.getCandidateTree().getRoot().print("");
		System.out.println("\n printtree()  end\n");
		seqPatt.checkCandidateTree();	
		
			
		supported =seqPatt.getSupportedCandidates();
		assertEquals(3, supported.size());	
		
		System.out.println("suported series:");
		for (Series series : supported) {
			System.out.println(series);
		}
		
		
		seqPatt.generateCandidates();
		seqPatt.buildCandidateHashTree();
		seqPatt.checkSupport(true);	
		System.out.println("\n printtree() \n");
		seqPatt.getCandidateTree().getRoot().print("");
		System.out.println("\n printtree()  end\n");
		seqPatt.checkCandidateTree();	
		
			
		supported =seqPatt.getSupportedCandidates();
		//assertEquals(3, supported.size());	
		
		System.out.println("suported series:");
		for (Series series : supported) {
			System.out.println(series);
		}
		
	}
	
	
	@Test 
	public void itemSetWithWindowTest() throws ParseException{
		
		System.out.println("\n itemSetWithWindowTest() \n");
		
		
		SequencePatterns seqPatt = initSeqPatt("testdata/test3.csv", true);
		seqPatt.setMaxGap(8);
		seqPatt.setMinSupp(2);
		seqPatt.setWindowSize(8);
		
		Series s1 = seqPatt.getSeries().get("AA");
		System.out.println(s1);
		
		
		long[] dates = s1.getDatesOrdered();
		long t = 0;
		long windowsize = 8;
		
		ArrayList<Long> datesToCheck = new ArrayList<Long>();
		
		for (Long date : dates) {
			if(date >= (t-windowsize) && date <= (t+windowsize) ){
				datesToCheck.add(date);
			}
		}
		
		assertEquals(0, datesToCheck.size());
				
		t = 15001;
		windowsize = 8;
		
		datesToCheck = new ArrayList<Long>();
		
		for (Long date : dates) {
			if(date >= (t-windowsize) && date <= (t+windowsize) ){
				datesToCheck.add(date);
			}
		}
		
		assertEquals(2, datesToCheck.size());
				
		HashMap<Long, ItemSet> itemSetsToCheck = new HashMap<Long, ItemSet>();
		for (Long date : datesToCheck) {
			itemSetsToCheck.put(date, s1.getDataSeq().get(date));
		}
		
		ItemSet itemSet = new ItemSet();
		int[] list = new int[2];
		list[0] =17;
		list[1] =18;
		itemSet.setItems(list);
		
		
		ItemSetWithWindow isWW = new ItemSetWithWindow(itemSetsToCheck);
		System.out.println("check " + itemSet);
		assertTrue(isWW.contains(itemSet));
		assertEquals(15008, isWW.getMinDate(itemSet));
		
		itemSet = new ItemSet();
		list = new int[2];
		list[0] =17;
		list[1] =27;
		itemSet.setItems(list);
		System.out.println("check " + itemSet);
		assertFalse(isWW.contains(itemSet));
		assertEquals(15008, isWW.getMinDate(itemSet));
		
		itemSet = new ItemSet();
		list = new int[2];
		list[0] =0;
		list[1] =17;
		itemSet.setItems(list);
		System.out.println("check " + itemSet);
		assertTrue(isWW.contains(itemSet));
		assertEquals(15001, isWW.getMinDate(itemSet));
		
		itemSet = new ItemSet();
		list = new int[1];
		list[0] =27;
		itemSet.setItems(list);
		System.out.println("check " + itemSet);
		assertFalse(isWW.contains(itemSet));
	}
}
