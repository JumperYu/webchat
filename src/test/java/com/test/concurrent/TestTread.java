package com.test.concurrent;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

import util.DBUtil;

public class TestTread extends Thread {

	private AtomicInteger atomicInteger = new AtomicInteger(1);

	@Override
	public void run() {

		while (true) {
			try {
				TimeUnit.MICROSECONDS.sleep(100);
				DBUtil.insert(atomicInteger.getAndIncrement());
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}

	}
}
