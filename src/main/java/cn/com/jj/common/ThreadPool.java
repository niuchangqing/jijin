package cn.com.jj.common;

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

public class ThreadPool {
	private static final ExecutorService es = Executors.newFixedThreadPool(10);

	public static void execute(Runnable r) {
		es.execute(r);
	}

	public static Future<Boolean> submit(Callable<Boolean> c) {
		return es.submit(c);
	}
}
