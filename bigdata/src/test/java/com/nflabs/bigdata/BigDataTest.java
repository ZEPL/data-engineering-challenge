package com.nflabs.bigdata;

import static org.junit.Assert.assertEquals;

import java.util.Arrays;

import org.junit.Test;

public class BigDataTest {

	@Test
	public void testRun() {
    	String[] values = {"-5", "-4", "-3", "-2", "-1", "0", "1", "2", "3", "4", "5"};
    	BigData bigdata = new BigData(values);

		assertEquals("sum wrong", Double.valueOf(0), bigdata.run().get("sum"));
		assertEquals("avg wrong", Double.valueOf(0), bigdata.run().get("avg"));
		assertEquals("median wrong", Double.valueOf(0), bigdata.run().get("median"));
		assertEquals("max wrong", Double.valueOf(5), bigdata.run().get("max"));
		assertEquals("count wrong", Long.valueOf(11), bigdata.run().get("count"));
	}

}
