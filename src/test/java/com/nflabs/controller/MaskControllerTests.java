package com.nflabs.controller;

import static org.junit.Assert.assertEquals;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

import org.apache.tomcat.util.http.fileupload.FileUtils;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(webEnvironment = WebEnvironment.DEFINED_PORT)
public class MaskControllerTests {
	
	private TestRestTemplate restTemplate = new TestRestTemplate();
	
	@Before
	public void setup() {
		try {
			FileUtils.deleteDirectory(new File("data"));
			Files.createDirectory(Paths.get("data"));
			Files.createFile(Paths.get("data", "mask"));
		}
		catch(IOException ie) {
			ie.printStackTrace();
		}
	}
	
	@Test
	public void testGetMaskForNull() {
		ResponseEntity<String> response = restTemplate.getForEntity("http://localhost:8080/mask", String.class);
		assertEquals(200, response.getStatusCodeValue());
		assertEquals(null, response.getBody());
	}
	
	@Test
	public void testPostMaskField() {
		ResponseEntity<String> response = restTemplate.postForEntity("http://localhost:8080/mask", "name", String.class);
		assertEquals(200, response.getStatusCodeValue());
		assertEquals("name", response.getBody());
	}
	
	@Test
	public void testPostMaskFields() {
		ResponseEntity<String> response = restTemplate.postForEntity("http://localhost:8080/mask", "name,age,job", String.class);
		assertEquals(200, response.getStatusCodeValue());
		assertEquals("name,age,job", response.getBody());
	}
	
	@Test
	public void testPostMaskFieldsWithSpaces() {
		ResponseEntity<String> response = restTemplate.postForEntity("http://localhost:8080/mask", "name, age, job ", String.class);
		assertEquals(200, response.getStatusCodeValue());
		assertEquals("name,age,job", response.getBody());
	}
	
	@Test
	public void testMultiplePostMaskFields() {
		ResponseEntity<String> response = restTemplate.postForEntity("http://localhost:8080/mask", "name", String.class);
		assertEquals(200, response.getStatusCodeValue());
		assertEquals("name", response.getBody());
		
		response = restTemplate.postForEntity("http://localhost:8080/mask", "age", String.class);
		assertEquals(200, response.getStatusCodeValue());
		assertEquals("name,age", response.getBody());
		
		response = restTemplate.postForEntity("http://localhost:8080/mask", "job", String.class);
		assertEquals(200, response.getStatusCodeValue());
		assertEquals("name,age,job", response.getBody());
	}
	
	@Test
	public void testDuplicatePostMask() {
		restTemplate.postForEntity("http://localhost:8080/mask", "name,age", String.class);
		ResponseEntity<String> response = restTemplate.postForEntity("http://localhost:8080/mask", "age", String.class);
		
		assertEquals(200, response.getStatusCodeValue());
		assertEquals("name,age", response.getBody());
	}
	
	@Test
	public void testPostMaskWithEmpty() {
		ResponseEntity<String> response = restTemplate.postForEntity("http://localhost:8080/mask", "", String.class);
		assertEquals(200, response.getStatusCodeValue());
		assertEquals(null, response.getBody());
	}
	
	@Test
	public void testPostAndGetMask() {
		restTemplate.postForEntity("http://localhost:8080/mask", "name,age,job", String.class);
		
		ResponseEntity<String> response = restTemplate.getForEntity("http://localhost:8080/mask", String.class);
		assertEquals(200, response.getStatusCodeValue());
		assertEquals("name,age,job", response.getBody());
	}
}
