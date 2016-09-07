/**
 * 2016.09.01
 * Shirp
 * 线程池
 * 需要使用ConfigManager类从资源文件读取部分参数
 */
package com.sl.java.utils;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

public class ThreadPool {

	static int corePoolSize = Integer.valueOf(ConfigManager.getConfigData("corePoolSize") != null
			&& ConfigManager.getConfigData("corePoolSize").length() > 0 ? ConfigManager.getConfigData("corePoolSize")
					: "1");
	static int maximumPoolSize = Integer.valueOf(ConfigManager.getConfigData("maximumPoolSize") != null
			&& ConfigManager.getConfigData("maximumPoolSize").length() > 0
					? ConfigManager.getConfigData("maximumPoolSize") : "1");
	static long keepAliveTime = Long.valueOf(ConfigManager.getConfigData("keepAliveTime") != null
			&& ConfigManager.getConfigData("keepAliveTime").length() > 0 ? ConfigManager.getConfigData("keepAliveTime")
					: "60");

	static public ThreadPoolExecutor mThreadPool = new ThreadPoolExecutor(3, 30, 60, TimeUnit.SECONDS,
			new ArrayBlockingQueue<Runnable>(30000), new ThreadPoolExecutor.DiscardOldestPolicy());

	static public ThreadPoolExecutor tpx = new ThreadPoolExecutor(corePoolSize, maximumPoolSize, keepAliveTime,
			TimeUnit.SECONDS, new ArrayBlockingQueue<Runnable>(30000), new ThreadPoolExecutor.DiscardOldestPolicy());
}
