package com.test.log;

import java.io.IOException;
import java.util.logging.ConsoleHandler;
import java.util.logging.FileHandler;
import java.util.logging.Formatter;
import java.util.logging.Level;
import java.util.logging.LogRecord;
import java.util.logging.Logger;

public class JavaLog {

	public static void main(String[] args) throws SecurityException,
			IOException {
		Logger log = Logger.getLogger("AA");
		log.setLevel(Level.INFO);
		log.info("haha");
		Logger log1 = Logger.getLogger("阿阿");
		log.info(log == log1 ? "true" : "false");
		log.finest("I don't appear");

		ConsoleHandler consoleHandler = new ConsoleHandler();
		consoleHandler.setLevel(Level.ALL);
		log1.addHandler(consoleHandler);
		FileHandler fileHandler = new FileHandler("d:\\log.out");
		fileHandler.setLevel(Level.INFO);
		fileHandler.setFormatter(new MyLogHander());
		log.addHandler(fileHandler);
		log.info("aaa");
		log1.info("bbb");
		log1.fine("fine");
	}

}

class MyLogHander extends Formatter {
	@Override
	public String format(LogRecord record) {
		return record.getLevel() + ":" + record.getMessage() + "\n";
	}
}
