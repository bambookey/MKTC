package com.lxy.MKTC.main;

import java.util.ArrayList;

public class OTwitter {
	// 文本内容
	public String text;
	// 聚类编号
	public int cluster;
	// 向量化结果
	public double[] vec;
	// 清理后的词列表
	ArrayList<String> cleanedWords = new ArrayList<String>();

	public OTwitter(String text, int cluster, double[] vec) {
		this.text = text;
		this.cluster = cluster;
		this.vec = vec;
	}
	
	public OTwitter(String text, int cluster, double[] vec, ArrayList<String> cleanedWords) {
		this.text = text;
		this.cluster = cluster;
		this.vec = vec;
		this.cleanedWords = cleanedWords;
	}
}
