package com.nflabs.controller;

import static org.junit.Assert.assertEquals;

import java.io.BufferedReader;
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
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.web.client.UnknownHttpStatusCodeException;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(webEnvironment = WebEnvironment.DEFINED_PORT)
public class LogControllerTests {
	
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
	public void testPostLogWithOutMask() {
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);
		String body = "{\"name\":\"jinwoo\",\"job\":\"programmer\",\"age\":40,\"gender\":\"male\",\"spouse\":{\"name\":\"jaeun\",\"age\":40}}";
		HttpEntity<String> httpEntity = new HttpEntity<>(body, headers);
		
		ResponseEntity<String> response = restTemplate.exchange("http://localhost:8080/log", HttpMethod.POST, httpEntity, String.class);
		assertEquals(200, response.getStatusCodeValue());
		assertEquals(body, readFromFile());
	}
	
	@Test
	public void testPostLogWithSingleMask() {
		restTemplate.postForEntity("http://localhost:8080/mask", "name", String.class);
		
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);
		String body = "{\"name\":\"jinwoo\",\"job\":\"programmer\",\"age\":40,\"gender\":\"male\",\"spouse\":{\"name\":\"jaeun\",\"age\":40}}";
		HttpEntity<String> httpEntity = new HttpEntity<>(body, headers);
		
		ResponseEntity<String> response = restTemplate.exchange("http://localhost:8080/log", HttpMethod.POST, httpEntity, String.class);
		assertEquals(200, response.getStatusCodeValue());
		assertEquals("{\"name\":\"*\",\"job\":\"programmer\",\"age\":40,\"gender\":\"male\",\"spouse\":{\"name\":\"*\",\"age\":40}}", readFromFile());
	}
	
	@Test
	public void testPostLogWithMultipleMask() {
		restTemplate.postForEntity("http://localhost:8080/mask", "name,age,job", String.class);
		
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);
		String body = "{\"name\":\"jinwoo\",\"job\":\"programmer\",\"age\":40,\"gender\":\"male\",\"spouse\":{\"name\":\"jaeun\",\"age\":40}}";
		HttpEntity<String> httpEntity = new HttpEntity<>(body, headers);
		
		ResponseEntity<String> response = restTemplate.exchange("http://localhost:8080/log", HttpMethod.POST, httpEntity, String.class);
		assertEquals(200, response.getStatusCodeValue());
		assertEquals("{\"name\":\"*\",\"job\":\"*\",\"age\":\"*\",\"gender\":\"male\",\"spouse\":{\"name\":\"*\",\"age\":\"*\"}}", readFromFile());

	}
	
	@Test
	public void testPostLog100Times() {
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);
		String body = "{\"name\":\"jinwoo\",\"job\":\"programmer\",\"age\":40,\"gender\":\"male\",\"spouse\":{\"name\":\"jaeun\",\"age\":40}}";
		HttpEntity<String> httpEntity = new HttpEntity<>(body, headers);
		
		for(int i=0; i<100; i++) {
			restTemplate.exchange("http://localhost:8080/log", HttpMethod.POST, httpEntity, String.class);
		}
		
		assertEquals(100, lineCountFromFile());
	}
	
	// status code 499 is not recognized by RestTemplate
	@Test(expected = UnknownHttpStatusCodeException.class)
	public void testPostEmptyLog() {
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);
		String body = "";
		HttpEntity<String> httpEntity = new HttpEntity<>(body, headers);
		ResponseEntity<String> response = restTemplate.exchange("http://localhost:8080/log", HttpMethod.POST, httpEntity, String.class);
	}
	
	// status code 499 is not recognized by RestTemplate
	@Test(expected = UnknownHttpStatusCodeException.class)
	public void testPostInvalidLog() {
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);
		// missing closing curly brace
		String body = "{\"key\": \"value\"";
		HttpEntity<String> httpEntity = new HttpEntity<>(body, headers);
		ResponseEntity<String> response = restTemplate.exchange("http://localhost:8080/log", HttpMethod.POST, httpEntity, String.class);

	}
	
	private String readFromFile() {
		try(
			BufferedReader br = Files.newBufferedReader(Paths.get("data", "json-data"));
		) {
			return br.readLine();
		}
		catch(IOException ie) {
			ie.printStackTrace();
		}
		
		return null;
	}
	
	private int lineCountFromFile() {
		try(
			BufferedReader br = Files.newBufferedReader(Paths.get("data", "json-data"));
		) {
			String line = null;
			int count = 0;
			while((line = br.readLine()) != null) {
				count++;
			}
			return count;
		}
		catch(IOException ie) {
			ie.printStackTrace();
		}
		
		return 0;
	}
}
