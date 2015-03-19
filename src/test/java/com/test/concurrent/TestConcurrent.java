package com.test.concurrent;

import java.sql.SQLException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class TestConcurrent {

	public static void main(String[] args) throws SQLException {

		ExecutorService service = Executors.newFixedThreadPool(3);

		service.execute(new TestTread());
		service.execute(new TestTread());
		service.execute(new TestTread());
	}
 
}
