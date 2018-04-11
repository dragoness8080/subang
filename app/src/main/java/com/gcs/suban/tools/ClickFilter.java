package com.gcs.suban.tools;
/**
 * 描述： 防止按钮连续点击
 */
public class ClickFilter {
	private static long lastClickTime;

	public synchronized static boolean isFastClick() {
		long time = System.currentTimeMillis();
		if (time - lastClickTime < 1000) {
			return true;
		}
		lastClickTime = time;
		return false;
	}

	private static long lastpayClickTime;

	public synchronized static boolean ispayFastClick() {
		long time = System.currentTimeMillis();
		if (time - lastpayClickTime < 2000) {
			return true;
		}
		lastClickTime = time;
		return false;
	}
}