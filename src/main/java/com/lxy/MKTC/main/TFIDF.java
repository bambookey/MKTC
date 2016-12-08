package com.lxy.MKTC.main;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;
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

		// 根据tf-idf值排序
		System.out.println(String.format("CLUSTER %d", twitterFromACluster.get(0).cluster));
		wordTfIdfSort = sortMap(wordTfIdfSort); 
		int cnt = 0;
		for(String k : wordTfIdfSort.keySet()) {
			System.out.println(String.format("%s : %f", k, wordTfIdfSort.get(k)));
			if(cnt++ > 15) break;
		}
	}
	
	public static Map sortMap(Map oldMap) {  
        ArrayList<Map.Entry<String, Double>> list = new ArrayList<Map.Entry<String, Double>>(oldMap.entrySet());  
        Collections.sort(list, new Comparator<Map.Entry<String, Double>>() {  
  
            public int compare(Entry<java.lang.String, Double> arg0,  
                    Entry<java.lang.String, Double> arg1) {  
                return arg0.getValue() - arg1.getValue() > 0 ? 1 : -1;  
            }  
        });  
        Map newMap = new HashMap();  
        for (int i = 0; i < list.size(); i++) {  
            newMap.put(list.get(i).getKey(), list.get(i).getValue());  
        }  
        return newMap;  
    }  
}
