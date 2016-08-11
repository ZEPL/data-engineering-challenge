package com.nflabs.controller;

import static org.junit.Assert.assertEquals;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(webEnvironment = WebEnvironment.DEFINED_PORT)
public class ErrorControllerTests {
	private TestRestTemplate restTemplate = new TestRestTemplate();
	
	@Test
	public void testNotFound() {
		ResponseEntity<String> response = restTemplate.getForEntity("http://localhost:8080/badurl", String.class);
		assertEquals(400, response.getStatusCodeValue());
	}
}
