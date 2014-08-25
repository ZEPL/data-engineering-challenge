package com.nflabs.bigdata;

import org.junit.Test;

public class BigDataCliTest {

	@Test(expected = NumberFormatException.class)
	public void testWrongInputException() {
    	String[] args = new String[]{"-n", "1 3 a", "-help"};
    	BigDataCli.main(args);
	}

}
