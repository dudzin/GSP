package pl.pw.elka.gsp.algorithm;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.HashMap;

public class GSP {
	
	//INIT ARGUMENTS:
	//[minSup] [minGap] [maxGap] [windowSize] [data file number]
	//Available data file numbers: 1, 2, 3
	public static void main(String[] args) {
		
		int[] arguments = null;
		if (args.length == 5) {
			arguments = new int[5];
			for(int i=0 ; i < 5 ; i++) {
			    try {
			    	arguments[i] = Integer.parseInt(args[i]);
			    } catch (NumberFormatException e) {
			        System.err.println("Argument "+i+". in row:" + args[i] + " must be an integer.");
			        printErrorMsg();
			        System.exit(1);
			    }
			}
		}
		else {
			printErrorMsg();
			System.exit(1);
		}
		
		SequencePatterns seqPatt = null;
		try {
			seqPatt = initSeqPatt(arguments, true);
		} catch (ParseException e) {
			System.out.println("Parse exception thrown!");
			e.printStackTrace();
		}
		findSequences(seqPatt);
	}

	private static SequencePatterns initSeqPatt(int[] arguments, boolean hierarchy) throws ParseException {
		CSVReader reader = new CSVReader();
		String path = getDataFileString(arguments[4]);
		HashMap<String, Series>  testSeries = reader.read(path, null, hierarchy);
		
				
		SequencePatterns seqPatt = new SequencePatterns(path);
		seqPatt.setSeries(testSeries);
		seqPatt.setMinSupp(arguments[0]);
		seqPatt.setDictionary(reader.getDictionary());
		seqPatt.setMinGap(arguments[1]);
		seqPatt.setMaxGap(arguments[2]);
		//seqPatt.setWindowSize(arguments[3]);
		
		return seqPatt;
	}
	
	private static void findSequences(SequencePatterns seqPatt) {
		
		seqPatt.runAlgorithm(true);
		ArrayList<Series> result = seqPatt.getResultSeries();
		seqPatt.getSeries();
		
//		for (Series s : seqPatt.getResultSeries() ) {
//			System.out.println(s);
//		}
	}

	private static String getDataFileString(int dataFileNum) {
		return "testdata/test"+dataFileNum+".csv";
	}
	
	private static void printErrorMsg() {
		System.err.println("Wrong arguments number! Please give five arguments in following order:");
		System.err.println("[minSup] [minGap] [maxGap] [windowSize] [data file number]");
		System.err.println("Available data file numbers: 1, 2, 3 ");
	}
}
