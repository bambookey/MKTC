package com.lxy.MKTC.main;

public class OTwitter {
	// 文本内容
	public String text;
	// 聚类编号
	public int cluster;
	// 向量化结果
	public double[] vec;

	public OTwitter(String text, int cluster, double[] vec) {
		this.text = text;
		this.cluster = cluster;
		this.vec = vec;
	}
}
