package com.lxy.MKTC.main;

public class Utils {

	/**
	 * 向量余弦夹角
	 * @Title: cosVec 
	 * @Description: TODO
	 * @param @param v1
	 * @param @param v2
	 * @param @return
	 * @param @throws Exception
	 * @return double
	 * @throws
	 */
	public static double cosVec(double[] v1, double[] v2) throws Exception {
		if (v1.length != v2.length) {
			throw new Exception("Illegal Input!! v1.length != v2.length");
		}
		double res = 0.0;
		double upper = 0.0;
		double downer = 0.0;
		double downer1 = 0.0;
		double downer2 = 0.0;
		int len = v1.length;
		for (int i = 0; i < len; i++) {
			downer1 += v1[i] * v1[i];
			downer2 += v2[i] * v2[i];
			upper += v1[i] * v2[i];
		}
		downer = Math.sqrt(downer1) * Math.sqrt(downer2) + 1.0;
		if (downer == 0) {
			throw new Exception("Illegal Cal Result!! downer==0");
		}

		res = upper / downer;
		return res;
	}
	/**
	 * 单词清理
	 * @Title: getCleanWord 
	 * @Description: TODO
	 * @param @param w
	 * @param @return
	 * @return String
	 * @throws
	 */
	public static String getCleanWord(String w) {
		String[] filter = {",", ".", "!", "@", "#", "$", "%", "^", "&", "*", "?", "(", ")", " ", 
				"1", "2", "3", "4", "5", "6", "7", "8", "9", "0", "|", "\"", ":", ";"
				};
		for(String f : filter) {
			w = w.replace(f, "");
		}
		return w.toLowerCase().trim();
	}
}
