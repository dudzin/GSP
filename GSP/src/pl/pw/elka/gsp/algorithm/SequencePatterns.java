package pl.pw.elka.gsp.algorithm;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Set;

public class SequencePatterns {

	private HashMap<String, Series> series, candidates;
	private HashMap<Integer,String> dictionary;
	private CandidateHashTree candidateTree;
	private int minSupp;
	private int timeConstraint = 365;
	private int treeLevel;
	private int minGap, maxGap;
	private CSVReader csvReader;
	private String fileName;
	
	private ArrayList<Series> newCandidates ;
	private Series lparent, rparent, newseries;
	private int[] llast, rlast;
	private ArrayList<Series> supportedCandidates, resultSeries;
	
	private int candidateSize;
	private long lsearched;
	private Long si =0L, tsi=0L, ts1 =0L;
	private long[] dates ;
	private long t,l;
	
	public SequencePatterns(String fileName){
		candidateTree = new CandidateHashTree(-1, -1);
		supportedCandidates = new ArrayList<Series>();
		resultSeries = new ArrayList<Series>();
		csvReader = new CSVReader();
		this.fileName = fileName;
	}
	
	public void readData(String dateFormat, boolean hierarchy) throws ParseException{
		series = csvReader.read(fileName,dateFormat, hierarchy);
	}
	
	public void runAlgorithm(){
		int i=1, cnt=0;
		long startTime = System.currentTimeMillis();
		do {
			generateCandidates();
			System.out.println("Step: " + i);
			System.out.println("generated candidates :" + candidates.size());
			buildCandidateHashTree();
			checkSupport();
			System.out.println("confirmed sequences  :" + (resultSeries.size()- cnt));
			cnt =  resultSeries.size();
			i++;
		} while (supportedCandidates.size() !=0);
		long endTime = System.currentTimeMillis();
		
		
		System.out.println("Summary:");
		System.out.println("execTime: " + (endTime - startTime) + "ms");
		System.out.println("file:     " + fileName);
		System.out.println("minSupp:  " + minSupp);
		System.out.println("minGap:   " + minGap);
		System.out.println("maxGap:   " + maxGap);
		System.out.println("Pattern Sequence found: " + resultSeries.size());
		System.out.println("Longest: " + (treeLevel-1));
	}
	
	
	public int getMinSupp() {
		return minSupp;
	}

	public void setMinSupp(int minSupp) {
		this.minSupp = minSupp;
	}

	public boolean generateCandidates() {

		if(minSupp != 0){
			
			/*
			if(treeLevel ==0 ){
				initialCandidateGeneration();
			}else if(treeLevel ==1){
				candidateGeneration2();
			}else {
				candidateGenerationMoreThan2();
			}
			*/
			
			if(treeLevel ==0 ){
				initialCandidateGeneration();
			
			}else {
				candidateGenerationGSP();
			}
			
			treeLevel++;
		}else {
			return false;
		}
		return false;
	}


	
	private void initialCandidateGeneration() {
		
		 candidates = new HashMap<String, Series>();
		 HashMap<Long, ItemSet> dataSeq;
		 for(int key : dictionary.keySet()){
			 Series ser = new Series();
			 dataSeq = new HashMap<Long, ItemSet>();
			 dataSeq.put(0L, new ItemSet(key));
			 ser.setDataSeq(dataSeq);
			 candidates.put(""+key, ser);
		 }
		
		 

	}
	
	private void candidateGeneration2() {
		
		newCandidates = new ArrayList<Series>();

		for(int i =0; i< supportedCandidates.size(); i++){
			
			lparent = supportedCandidates.get(i);
			newseries = new Series(lparent);
			llast = newseries.getLastItem();
			
			for(int j = i+1; j< supportedCandidates.size() ; j++){
				rparent = supportedCandidates.get(j);
				rlast = rparent.getLastItem();
				
				if(llast == rlast){
					newseries = createNewCandidate(lparent, rlast);
					newCandidates.add(newseries);
				}else {
					newseries = createNewCandidate(lparent, rlast);
					newCandidates.add(newseries);
					newseries = createNewCandidate(rparent, llast);
					newCandidates.add(newseries);
					newseries = createNewCandidate(lparent, rlast , llast);
					newCandidates.add(newseries);
				}
			}
			newseries = createNewCandidateAdd(lparent, llast);
			newCandidates.add(newseries);
		}
		fillCandidates(newCandidates);
	}
	
	private Series createNewCandidate(Series parent, int[] items) {
		ItemSet newItemSet = new ItemSet();
		Series newseries = new Series(parent);
		newItemSet.setItems(items);
		newseries.addItemSet(parent.getLastDate()+1, newItemSet);
		return newseries;
	}
	
	private Series createNewCandidate(Series parent, int[] items1, int[] items2) {
		ItemSet newItemSet = new ItemSet();
		Series newseries = new Series(parent);
		newItemSet.setItems(items1 , items2);
		newseries.addItemSet(parent.getLastDate(), newItemSet);
		return newseries;
	}
	
	private Series createNewCandidateAdd(Series parent, int[] items) {
		ItemSet newItemSet = new ItemSet();
		Series newseries = new Series(parent);
		newItemSet.setItems(items);
		newseries.addItemSet(parent.getLastDate()+1, newItemSet);
		return newseries;
	}
	
	private Series createNewCandidateAdd(Series parent, int[] items1, int[] items2) {
		ItemSet newItemSet = new ItemSet();
		Series newseries = new Series(parent);
		newItemSet.setItems(items1, items2);
		newseries.addItemSet(parent.getLastDate(), newItemSet);
		return newseries;
	}
	
	private void fillCandidates(ArrayList<Series> newCandidates2) {
		candidates = new HashMap<String, Series>();
		for (int i =0; i<newCandidates.size() ; i++) {
			candidates.put(""+newCandidates.get(i), newCandidates.get(i));
		}
		
	}
	
	private void candidateGenerationMoreThan2() {

		newCandidates = new ArrayList<Series>();
		
		for(int i =0; i< supportedCandidates.size(); i++){
			
			lparent = supportedCandidates.get(i);
			newseries = new Series(lparent);
			llast = newseries.getLastItem();
			
			for(int j = i; j< supportedCandidates.size() ; j++){
				rparent = supportedCandidates.get(j);
				rlast = rparent.getLastItem();
				//System.out.println("from " + lparent + " & " + rparent);
				char cond3 = lparent.canJoinCondition3(rparent);
				if(lparent.canJoinCondition1(rparent)){	
					condition1(i,j);
				}else if(lparent.canJoinCondition2(rparent)){
					condition2(i,j);
				}else if(cond3 != 'n'){
					condition3(i, j, cond3);
				}
			}
		}
		fillCandidates(newCandidates);
	}
	
	private void candidateGenerationGSP() {

		newCandidates = new ArrayList<Series>();
		
		for(int i =0; i< supportedCandidates.size(); i++){
			
			lparent = supportedCandidates.get(i);
			newseries = new Series(lparent);
			llast = newseries.getLastItem();
			
			for(int j = 0; j< supportedCandidates.size() ; j++){
				rparent = supportedCandidates.get(j);

				
				
				 ArrayList<Series> newserieslist = lparent.merge(rparent);
				if(newserieslist != null){
					newCandidates.addAll(newserieslist);
				}
				
				System.out.println("from " + lparent + " & " + rparent + " "+ newserieslist);
			}
		}
		fillCandidates(newCandidates);
	}
	
	
	private void condition1(int i, int j){
		//System.out.println("generate condition 1" );
		
		if(i==j){
			//G(P)(x)(x)
			newseries = createNewCandidateAdd(lparent, llast);
			newCandidates.add(newseries);
			//System.out.println("generate " + newseries);
		}else {
			//G(P)(x)(y)
			newseries = createNewCandidate(lparent, rlast);
			newCandidates.add(newseries);
			//System.out.println("generate " + newseries);
			//G(P)(y)(x)
			newseries = createNewCandidate(rparent, llast);
			newCandidates.add(newseries);
			//System.out.println("generate " + newseries);
			//G(P)(x,y)
			newseries = createNewCandidate(lparent, rlast , llast);
			newCandidates.add(newseries);
			//System.out.println("generate " + newseries);
			
		}	
	}
	
	private void condition2(int i, int j ){
		//System.out.println("generate condition 2" );
		newseries = createNewCandidateAdd(lparent, llast, rlast);
		newCandidates.add(newseries);
		//System.out.println("generate " + newseries);
	}
	
	private void condition3(int i, int j, char cond3){
		if(cond3 == 'o'){
			
			//System.out.println("generate condition 3" );
			newseries = createNewCandidate(lparent, rlast);
			newCandidates.add(newseries);
			//System.out.println("generate " + newseries);
		}else if(cond3 == 't'){	
			
			newseries = createNewCandidate(rparent, llast);
			newCandidates.add(newseries);
			//System.out.println("generate " + newseries);
		}
	}
	
	

	public void buildCandidateHashTree() {
		
		candidateTree.clear();
		Set<String> keys = candidates.keySet();
		for (String key : keys) {
			candidateTree.add(candidates.get(key));
		}
		
	}
	
	
	
	public void checkSupport() {

		ArrayList<Series> candidatesInit = candidateTree.getRoot().getCandidateSeries();
		ArrayList<Series> candidatesToCheck = new ArrayList<Series>();
		Set<String> keys = series.keySet();
		Series analysedSeries;
		
		int size = candidatesInit.get(0).getDataSeq().size();
		for(Series candidate : candidatesInit){
			//System.out.println("candidate " + candidate);
			
			if(size ==1){
				candidatesToCheck.add(candidate);
				//System.out.println("found candidate ");
			}else {
				//Series candidateMinusOne = new Series(candidate);
				//candidateMinusOne.removeLast();
				//candidatesToCheck.add(candidate);
				int[] candidateMinusOne = candidate.getItemsOrderedMinusOne();
				
				//System.out.println("candidate rem last" + candidateMinusOne);
				for(Series suppcandidate : supportedCandidates){
					//System.out.println("suppcandidate " + suppcandidate);
					int[] suppCandidateMinusOne = suppcandidate.getItemsOrderedMinusOne();
					if(suppcandidate.shouldCheck(candidateMinusOne)){
						candidatesToCheck.add(candidate);
						//System.out.println("found candidate ");
						break;
					}
				}
			}
		}		
		
		supportedCandidates = new ArrayList<Series>();
		for (Series candidate : candidatesToCheck) {
			si =0L;
			tsi=0L;
			ts1 =0L;
			candidateSize =candidate.getDataSeq().size();
			for (String serriesname : keys) {
				analysedSeries = series.get(serriesname);
				dates = analysedSeries.getDatesOrdered();
				
				for (int i=0 ; i< dates.length ;i++) {
					l = dates[i];
					t =l;
					if(analysedSeries.getDataSeq().get(l).contains(candidate.getDataSeq().get(si))){

						if(tsi==0L){
							if(si == candidateSize-1){
								candidate.supportIncr();
								break;
							}else {
								tsi =l;
								ts1 =l;
								si++;
							}
						}else {
						
							if(((tsi +maxGap) > t) ){
								if(((t - ts1) <= timeConstraint)){
									if(si==candidateSize-1){
										candidate.supportIncr();
										break;
									}else{
										si++;
									}
								}
							}else {
								if(moveBackward(i)){
									break;
								}
							}
						}
					}
				}
			}
			if(candidate.getSupport()>= minSupp){
				supportedCandidates.add(candidate);
				resultSeries.add(candidate);
			}
		}
	}
	
	
	private boolean moveBackward(int i){
		si--;
		lsearched = l -maxGap +1;
		while(l>lsearched){
			i--;
			l = dates[i];
		}
		if(si ==0){
			return true;
		}
		return  false;
	}
	
	
	public ArrayList<Series> getSupportedCandidates(){
		return supportedCandidates;
	}

	public int datesDiff(String date1, String date2) throws ParseException{
		SimpleDateFormat sdf = new SimpleDateFormat("mm/dd/yyyy");
		Date startdate = sdf.parse(date1);
		Date enddate = sdf.parse(date2);
		long diffTime = enddate.getTime() - startdate.getTime();
		return  (int) diffTime / (1000 * 60 * 60 * 24);
		
	}
	
	public ArrayList<Series> getResultSeries(){
		return resultSeries;
	}
	public HashMap<Integer,String> getDictionary() {
		return dictionary;
	}

	public void setDictionary(HashMap<Integer,String> dictionary) {
		this.dictionary = dictionary;
		candidateTree.setHash(dictionary.size());
		candidateTree.setThreshold(dictionary.size());
	}

	public HashMap<String, Series> getSeries() {
		return series;
	}

	public void setSeries(HashMap<String, Series> series) {
		this.series = series;
	}
	
	public HashMap<String, Series> getCandidates() {
		return candidates;
	}

	public void setCandidates(HashMap<String, Series> candidates) {
		this.candidates = candidates;
	}

	public CandidateHashTree getCandidateTree() {
		return candidateTree;
	}

	public void setCandidateTree(CandidateHashTree candidateTree) {
		this.candidateTree = candidateTree;
	}

	public int getMinGap() {
		return minGap;
	}

	public void setMinGap(int minGap) {
		this.minGap = minGap;
	}

	public int getMaxGap() {
		return maxGap;
	}

	public void setMaxGap(int maxGap) {
		this.maxGap = maxGap;
	}

	public String getFileName() {
		return fileName;
	}

	public void setFileName(String fileName) {
		this.fileName = fileName;
	}

	public int getTimeConstraint() {
		return timeConstraint;
	}

	public void setTimeConstraint(int timeConstraint) {
		this.timeConstraint = timeConstraint;
	}



	
}
