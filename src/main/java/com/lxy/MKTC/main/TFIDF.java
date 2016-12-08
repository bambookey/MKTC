package com.lxy.MKTC.main;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

public class TFIDF {
	/**
	 * 求一个聚类中的TF-IDF @Title: TwitterTfidf @Description: TODO @param @param
	 * twitterFromACluster @return void @throws
	 */
	public static void TwitterTfidf(ArrayList<OTwitter> twitterFromACluster) {
		if(twitterFromACluster == null || twitterFromACluster.size() == 0) {
			return;
		}
		int len = twitterFromACluster.size();
		int clusterTotalCnt = 0;// 该聚类中的总词数
		HashMap<String, Integer> wordCntMap = new HashMap<String, Integer>();
		Map<String, Double> wordTfIdfSort = new TreeMap<String, Double>();

		for (int i = 0; i < len; i++) {
			for (String w : twitterFromACluster.get(i).cleanedWords) {
				clusterTotalCnt++;
				if (wordCntMap.containsKey(w)) {
					wordCntMap.put(w, wordCntMap.get(w) + 1);
				} else {
					wordCntMap.put(w, 1);
				}
			}
		}

		// 求每个词的tf-idf
		for (String key : wordCntMap.keySet()) {
			int keyCnt = wordCntMap.get(key);
			double tf = (double) keyCnt / (double) clusterTotalCnt;

			int textDataLen = Main.otList.size();
			int textContainKeyLen = 0;
			for (int i = 0; i < textDataLen; i++) {
				if (Main.otList.get(i).text.contains(key)) {
					textContainKeyLen++;
				}
			}
			double idf = Math.log((double) textDataLen / ((double) textContainKeyLen + 1.0));

			double tfidf = tf * idf;

			wordTfIdfSort.put(key, tfidf);
		}

		
		List<Map.Entry<String, Double>> list_Data = new ArrayList<Map.Entry<String, Double>>(wordTfIdfSort.entrySet());   
	    Collections.sort(list_Data, new Comparator<Map.Entry<String, Double>>() {    
			public int compare(Map.Entry<String, Double> o1, Map.Entry<String, Double> o2) {  
				if ((o2.getValue() - o1.getValue())>0)  
					return 1;  
				else if((o2.getValue() - o1.getValue())==0)  
					return 0;  
				else   
					return -1;  
				}  
			}  
		);  
	    
		// 根据tf-idf值排序
		System.out.println(String.format("CLUSTER %d:", twitterFromACluster.get(0).cluster));
		LinkedHashMap<String,Double> sorted_map = new LinkedHashMap<String,Double>();
		for(Map.Entry<String, Double> e : list_Data) {
			sorted_map.put(e.getKey(), e.getValue());
		} 
		int cnt = 0;
		for(String k : sorted_map.keySet()) {
			System.out.println(String.format("%s : %f", k, sorted_map.get(k)));
			if(cnt++ > 15) break;
		}
	}
}