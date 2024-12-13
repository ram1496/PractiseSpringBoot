package com.casestudy.pharma.utility;

public class BatchIDGenerator {
	 private static int counter = 1000;
	 public static synchronized int getNextId() {
	        return counter++;
	    }
	public static String generateId() {
        return "BTC-"+String.format("%04d", getNextId());
    }
}
