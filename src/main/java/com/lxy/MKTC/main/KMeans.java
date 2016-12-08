package com.lxy.MKTC.main;

import java.util.ArrayList;
import java.util.HashSet;

public class KMeans {
	// 收敛条件，转换次数阈值
	private final static int CONVERGENCE_CNT = 5;

	public static void TwitterKmeans(ArrayList<OTwitter> otList, int k) throws Exception {
		HashSet<OTwitter> centerSet = new HashSet<OTwitter>();
		ArrayList<OTwitter> centerList = new ArrayList<OTwitter>();
		int len = otList.size();
		// 1. init k centers
		int clusterNo = 0;
		while (centerSet.size() < k) {
			OTwitter ot = otList.get((int) (Math.random() * len));
			if (!centerSet.contains(ot)) {
				ot.cluster = clusterNo;
				centerSet.add(ot);
				centerList.add(ot);
				clusterNo++;
			}
		}
		System.out.println(String.format("init %d centers", k));
		// 2. iteration
		int itCnt = 0;
		int adjustCnt = Integer.MAX_VALUE;
		while (adjustCnt > CONVERGENCE_CNT) {
			adjustCnt = 0;
			System.out.println(String.format("iteration %dth time", itCnt++));
			// 2.1. a iteration
			for (int i = 0; i < len; i++) {
				int curCenterCluster = otList.get(i).cluster;
				/**
				 * 这里是对kmeans的收敛条件做判断，本程序通过kmeans的聚类结果的稳定性作为标准
				 * 第一次将cosSimi定义为一个负值是为了防止在第一次运行时，存在0值余弦结果而导致聚类标签不会被加上
				 * 在第二次迭代时，先将该向量与其中心向量的余弦值作为初始值，可以看出每次迭代的标签变化次数
				 */
				double cosSimi = -0.1;
				if (curCenterCluster != -1) {
					cosSimi = Utils.cosVec(centerList.get(curCenterCluster).vec, otList.get(i).vec);
				}

				for (int j = 0; j < k; j++) {
					double twitterCenterSimi = Utils.cosVec(centerList.get(j).vec, otList.get(i).vec);
					if (twitterCenterSimi > cosSimi) {
						cosSimi = twitterCenterSimi;

						if (otList.get(i).cluster != centerList.get(j).cluster) {
							otList.get(i).cluster = centerList.get(j).cluster;
							adjustCnt++;
						}

					}
				}
			}
			System.out.println(adjustCnt);
			// 2.2. refresh center
			centerList = refreshCenter(otList, k);
		}
		
		for(int i = 0 ; i < k; i++) {
			ArrayList<OTwitter> clusteredList = new ArrayList<OTwitter> ();
			for(int j = 0 ; j < len ; j++) {
				if(otList.get(j).cluster == i) {
					clusteredList.add(otList.get(j));
				}
			}
			TFIDF.TwitterTfidf(clusteredList);
		}
	}

	public static ArrayList<OTwitter> refreshCenter(ArrayList<OTwitter> otList, int k) {
		int vecLen = otList.get(0).vec.length;
		double vecs[][] = new double[k][vecLen];
		int vecsCnt[] = new int[k];
		for (int i = 0; i < otList.size(); i++) {
			int index = otList.get(i).cluster;
			vecsCnt[index]++;
			for (int j = 0; j < vecLen; j++) {
				vecs[index][j] += otList.get(i).vec[j];
			}
		}
		ArrayList<OTwitter> centerList = new ArrayList<OTwitter>();
		for (int i = 0; i < k; i++) {
			centerList.add(new OTwitter(null, i, vecDiv(vecs[i], vecsCnt[i])));
		}
		String strCntByType = "";
		for (int i = 0; i < k; i++) {
			strCntByType += i + ":" + vecsCnt[i] + "  ";
		}
		System.out.println(strCntByType);
		return centerList;
	}

	public static double[] vecDiv(double[] vec, double div) {
		for (int i = 0; i < vec.length; i++) {
			vec[i] = vec[i] / div;
		}
		return vec;
	}
}
