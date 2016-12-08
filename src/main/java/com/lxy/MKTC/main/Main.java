package com.lxy.MKTC.main;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.HashSet;

public class Main {
	public static final String FILE_PATH_TIWTTER = "./data/input.txt";
	public static final String FILE_PATH_STOPWORDS = "./data/stopwords";
	public static HashSet<String> stopWords = new HashSet<String>();
	public static HashSet<String> words = new HashSet<String>();
	public static ArrayList<OTwitter> otList = new ArrayList<OTwitter>();//存所有的twitter

	public static void main(String[] args) throws Exception {
		loadStopWords();
		loadTwitter();
		KMeans.TwitterKmeans(otList, 10);
	}

	public static void loadTwitter() throws Exception {
		System.out.println("LOAD TWITTER");
		BufferedReader br = new BufferedReader(new FileReader(new File(FILE_PATH_TIWTTER)));
		String oline = "";
		// 1 init words
		while ((oline = br.readLine()) != null) {
			if (oline.equals("No Post Title") || oline.trim().length() == 0) {
				continue;
			}
			String[] olineWords = oline.split(" ");
			for (int i = 0; i < olineWords.length; i++) {
				String w = Utils.getCleanWord(olineWords[i]);
				if (stopWords.contains(w) || w.length() <= 2) {
					continue;
				} else {
					words.add(w);
				}
			}
		}
		// 2 init twitter vec
		br = new BufferedReader(new FileReader(new File(FILE_PATH_TIWTTER)));
		while ((oline = br.readLine()) != null) {
			if (oline.equals("No Post Title") || oline.trim().length() == 0) {
				continue;
			}

			String[] olineWords = oline.split(" ");
			ArrayList<String> olineWordsCleaned = new ArrayList<String>();
			for (int i = 0; i < olineWords.length; i++) {
				String w = Utils.getCleanWord(olineWords[i]);
				if (stopWords.contains(w) || w.length() <= 2) {
					continue;
				} else {
					olineWordsCleaned.add(w);
				}
			}
			OTwitter ot = word2VecTwitter(oline, olineWordsCleaned);
			otList.add(ot);
		}
	}

	public static void loadStopWords() throws Exception {
		System.out.println("LOAD STOPWORDS");
		BufferedReader br = new BufferedReader(new FileReader(new File(FILE_PATH_STOPWORDS)));
		String oline = "";
		while ((oline = br.readLine()) != null) {
			stopWords.add(Utils.getCleanWord(oline));
		}
		br.close();
	}

	public static OTwitter word2VecTwitter(String text, ArrayList<String> olineWordsCleaned) throws Exception {
		double[] vec = new double[words.size()];
		for (int i = 0; i < olineWordsCleaned.size(); i++) {
			String w = olineWordsCleaned.get(i);
			int index = getIndex(words, w);
			vec[index]++;
		}
		return new OTwitter(text, -1, vec, olineWordsCleaned);
	}

	/**
	 * 获取一个单词在words中的index 
	 * @Title: getIndex 
	 * @Description: TODO
	 * @param @param set
	 * @param @param v
	 * @param @return
	 * @param @throws Exception
	 * @return int
	 * @throws
	 */
	public static int getIndex(HashSet<String> set, String v) throws Exception {
		int index = 0;
		for (String x : set) {
			if (x.equals(v)) {
				return index;
			}
			index++;
		}
		throw new Exception(String.format("%s not exists", v));
		// return -1;
	}
}
