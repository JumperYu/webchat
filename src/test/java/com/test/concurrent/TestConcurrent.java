package com.test.concurrent;

import java.util.concurrent.CopyOnWriteArraySet;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

public class TestConcurrent {

	private static AtomicInteger atomicInteger = new AtomicInteger(1);

	private static CopyOnWriteArraySet<Integer> arrays = new CopyOnWriteArraySet<Integer>();

	public static void main(String[] args) {

		ExecutorService service = Executors.newFixedThreadPool(2);

		service.execute(new Thread() {
			@Override
			public void run() {
				while (true) {
					try {
						TimeUnit.MILLISECONDS.sleep(100);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
					arrays.add(atomicInteger.getAndIncrement());
					System.out.println(this.getName() + arrays);
				}
			}
		});
		service.execute(new Thread() {
			@Override
			public void run() {
				while (true) {
					try {
						TimeUnit.MILLISECONDS.sleep(100);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
					arrays.add(atomicInteger.getAndIncrement());
					System.out.println(this.getName() + arrays);
				}
			}
		});
	}

}
