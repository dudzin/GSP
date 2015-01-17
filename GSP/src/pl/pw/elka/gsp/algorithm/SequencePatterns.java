package pl.pw.elka.gsp.algorithm;

import static org.junit.Assert.assertTrue;

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
	private boolean withHashTree;
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
	private Long si =0L, tsi=0L, ts1 =0L, tsimax =0L, tmin =0L;
	private long[] dates ;
	private long t,l;
	private int windowSize;
	
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
		double generatedCandidatesNum = 0, confirmedSequencesNum = 0;
		System.out.println("SEQUENCE SEARCH REPORT:");
		System.out.println();
		long startTime = System.currentTimeMillis();
		do {
			generateCandidates();
			System.out.println("Step: " + i);
			System.out.println("generated candidates :" + candidates.size());
			generatedCandidatesNum += candidates.size();
			buildCandidateHashTree();
			checkSupport(withHashTree);
			System.out.println("confirmed sequences  :" + (resultSeries.size()- cnt));
			confirmedSequencesNum += (resultSeries.size()- cnt);
			cnt =  resultSeries.size();
			i++; 
			System.out.println("ver " + withHashTree + " " + resultSeries.size());
		} while (supportedCandidates.size() !=0);
		long endTime = System.currentTimeMillis();
		System.out.println(); System.out.println();
		
		double candidate_sequenceRatio = confirmedSequencesNum/generatedCandidatesNum;
		double execTime = endTime - startTime;
		double timePerConfirmedSeq = execTime/confirmedSequencesNum;
	
		System.out.println("SUMMARY");
		System.out.println();
		System.out.println("Parameters:");
		System.out.println("file:       " + fileName);
		System.out.println("minSupp:    " + minSupp);
		System.out.println("minGap:     " + minGap);
		System.out.println("maxGap:     " + maxGap);
		System.out.println("timeConstr: " + timeConstraint);
		System.out.println("widnowSize: " + windowSize);
		System.out.println();
		System.out.println("Execution info:");
		System.out.println("execTime: " + ((int)execTime) + "ms");
		System.out.println("Pattern Sequence found: " + resultSeries.size());
		System.out.println("Longest: " + (treeLevel-1));
		System.out.println();
		System.out.println("Performance indicators:");
		System.out.println("Ratio [Confirmed Sequences/Generated Candidates]: " + candidate_sequenceRatio);
		System.out.println("Exec time per confirmed sequence: " + timePerConfirmedSeq + "ms");
		System.out.println();
	}
	
	
	public boolean isWithHashTree() {
		return withHashTree;
	}

	public void setWithHashTree(boolean withHashTree) {
		this.withHashTree = withHashTree;
	}

	public int getMinSupp() {
		return minSupp;
	}

	public void setMinSupp(int minSupp) {
		this.minSupp = minSupp;
	}

	public boolean generateCandidates() {

		if(minSupp != 0){			
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
	
	private void fillCandidates(ArrayList<Series> newCandidates2) {
		candidates = new HashMap<String, Series>();
		for (int i =0; i<newCandidates.size() ; i++) {
			candidates.put(""+newCandidates.get(i), newCandidates.get(i));
		}
		
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
				//System.out.println("from " + lparent + " & " + rparent + " "+ newserieslist);
			}
		}
		fillCandidates(newCandidates);
	}
	
	
	public void buildCandidateHashTree() {
		
		candidateTree.clear();
		Set<String> keys = candidates.keySet();
		for (String key : keys) {
			candidateTree.add(candidates.get(key));
		}
		
	}
	
	
	
	public void checkSupport(boolean withHashTree) {

		ArrayList<Series> candidatesInit;
		checkTree();
		checkCandidateTree();
		if(withHashTree){
			
			candidatesInit= candidateTree.getRoot().getCandidateSeries(minSupp);
			
		}else {

			candidatesInit= candidateTree.getRoot().getCandidateSeries();
		}
		ArrayList<Series> candidatesToCheck = new ArrayList<Series>();
		
		Series analysedSeries;
		
		//System.out.println("ver " + withHashTree + " " + candidatesInit.size());
		//for (Series series : candidatesInit) {
		//	System.out.println("to Check:" + series);
		//}
		
		
		if(candidatesInit == null || candidatesInit.size() ==0){
			supportedCandidates = new ArrayList<Series>();
			return;
		}
		
		int size = candidatesInit.get(0).getDataSeq().size();
		for(Series candidate : candidatesInit){
			//candidate.setSupport(0);
			if(size ==1){
				candidatesToCheck.add(candidate);
			}else {
				int[] candidateMinusOne = candidate.getItemsOrderedMinusOne();
				for(Series suppcandidate : supportedCandidates){
					if(suppcandidate.shouldCheck(candidateMinusOne)){
						candidatesToCheck.add(candidate);
						break;
					}
				}
			}
		}		
		Set<String> keys = series.keySet();
		supportedCandidates = new ArrayList<Series>();
		for (Series candidate : candidatesToCheck) {
			
			candidateSize =candidate.getDataSeq().size();
			if(withHashTree){
				keys = candidate.getsupportedByHash();
			}
			for (String serriesname : keys) {
				analysedSeries = series.get(serriesname);
				dates = analysedSeries.getDatesOrdered();
				si =0L;
				tsi=0L;
				ts1 =0L;
				tsimax = 0L;
				tmin = 0L;
						
				for (int i=0 ; i< dates.length ;i++) {
					l = dates[i];
					t =l;
					/*if(tsi != 0L){
						while(l < tsi+ minGap){
							i++;
							l = dates[i];
						}
					}*/
					//{}
					ArrayList<Long> datesToCheck = new ArrayList<Long>();
					
					for (Long date : dates) {
						if(date >= (t-windowSize) && date <= (t+windowSize) ){
							datesToCheck.add(date);
						}
					}
					HashMap<Long, ItemSet> itemSetsToCheck = new HashMap<Long, ItemSet>();
					for (Long date : datesToCheck) {
						itemSetsToCheck.put(date, analysedSeries.getDataSeq().get(date));
					}
					
					ItemSetWithWindow isWW = new ItemSetWithWindow(itemSetsToCheck);
					ItemSet isCheck = candidate.getDataSeq().get(si);
					//System.out.println("check " + itemSet);
					if((isWW.contains(isCheck))){
					//if(analysedSeries.getDataSeq().get(l).contains(candidate.getDataSeq().get(si))){
						t = isWW.getMaxDate(isCheck);
						tmin = isWW.getMinDate(isCheck);
						if(tsi==0L){
							if(si == candidateSize-1){
								//candidate.supportIncr();
								candidate.addSupportedByCheck(analysedSeries.getSeriesName());
								break;
							}else {
								tsi =isWW.getMinDate(isCheck);
								tsimax = isWW.getMaxDate(isCheck);
								ts1 =l;
								si++;
								
							}
						}else {
						
							//if(((tsi +maxGap) >= t) && //(tsi + minGap) <= l){
							//		(tsimax + minGap) >= tmin){
							if( (t -tsi) <= maxGap  
									&& ( (tmin-tsimax) >= minGap )
									//&& (tsi + minGap) <= l
									){	
								
								if(((t - ts1) <= timeConstraint)){
									if(si==candidateSize-1){
										//candidate.supportIncr();
										candidate.addSupportedByCheck(analysedSeries.getSeriesName());
										break;
									}else{
										tsi =isWW.getMinDate(isCheck);
										tsimax = isWW.getMaxDate(isCheck);
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
			if(candidate.getSupportByCheck()>= minSupp){
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
			if(i<0) {
				return true;
			}
			l = dates[i];
		}
		if(si ==0){
			return true;
		}
		return  false;
	}
	
	public void checkCandidateTree() {
		Set<String> seriesNames = series.keySet();
		for (String string : seriesNames) {
			candidateTree.checkSeries(series.get(string));
		}
		
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
		candidateTree.setMaxGap(maxGap);
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

	public void checkTree(){
		candidateTree.checkTree();
	}

	public int getWindowSize() {
		return windowSize;
	}

	public void setWindowSize(int windowSize) {
		this.windowSize = windowSize;
		this.candidateTree.setWindowSize(windowSize);
	}

	
	
}
