package pl.pw.elka.gsp.algorithm;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Properties;
import java.util.Scanner;
import java.lang.Boolean;

public class GSP {
	
	public static void main(String[] args) {
		
		String configFileName = setConfigFileName(args);
		GSPparameters params = getParameters(configFileName);
		SequencePatterns seqPatt = null;
		try {
			seqPatt = initSeqPatt(params);
		} catch (ParseException e) {
			System.out.println("Parse exception thrown!");
			e.printStackTrace();
		}
		findSequences(seqPatt);
		printResultSeries(seqPatt);
	}
	
	private static String setConfigFileName(String[] args) {
		
		String configFileName = null;
		if (args.length == 1) {
			try {
				configFileName = args[0];
			} catch (NumberFormatException e) {
				System.err.println("Bad input argument format. Taking default config file: \"config.properties\" ");
				configFileName = "config.properties";
			}
		}
		else {
			configFileName = "config.properties";
		}
		return configFileName;
	}

	public static GSPparameters getParameters(String configFileName) {
		GSPparameters params = new GSPparameters();
		Properties prop = new Properties();
		InputStream input = null;
		try {
	 		input = new FileInputStream(configFileName);
			prop.load(input);
	 
			params.useHashTree = Boolean.parseBoolean(prop.getProperty("useHashTree"));
			params.useTaxonomies = Boolean.parseBoolean(prop.getProperty("useTaxonomies"));
			params.slidingWindowSize = Short.parseShort(prop.getProperty("slidingWindowSize"));
			params.minSupport = Short.parseShort(prop.getProperty("minSupport"));
			params.minGap = Short.parseShort(prop.getProperty("minGap"));
			params.maxGap = Short.parseShort(prop.getProperty("maxGap"));
			params.timeConstraint = Short.parseShort(prop.getProperty("timeConstraint"));
			params.dataFilePath = prop.getProperty("dataFilePath");
			
		} catch (IOException ex) {
			ex.printStackTrace();
		} finally {
			if (input != null) {
				try {
					input.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
		return params;
	}

	private static SequencePatterns initSeqPatt(GSPparameters params) throws ParseException {
		
		CSVReader reader = new CSVReader();
		String path = params.dataFilePath;
		HashMap<String, Series>  testSeries = reader.read(path, null, params.useTaxonomies);
		
				
		SequencePatterns seqPatt = new SequencePatterns(path);
		seqPatt.setSeries(testSeries);
		seqPatt.setMinSupp(params.minSupport);
		seqPatt.setDictionary(reader.getDictionary());
		seqPatt.setMinGap(params.minGap);
		seqPatt.setMaxGap(params.maxGap);
		seqPatt.setWindowSize(params.slidingWindowSize);
		seqPatt.setTimeConstraint(params.timeConstraint);
		seqPatt.setWithHashTree(params.useHashTree);
		seqPatt.setHierarchy(params.useTaxonomies);
		
		return seqPatt;
	}
	
	private static void findSequences(SequencePatterns seqPatt) {
		
		seqPatt.runAlgorithm();
		ArrayList<Series> result = seqPatt.getResultSeries();
		seqPatt.getSeries();
		
	}
	
	private static void printResultSeries(SequencePatterns seqPatt) {
		
		System.out.println("RESULT SERIES:");
		System.out.println();
		for (Series s : seqPatt.getResultSeries() ) {
			System.out.println(seqPatt.translateSeries(s));
		}
	}
}
