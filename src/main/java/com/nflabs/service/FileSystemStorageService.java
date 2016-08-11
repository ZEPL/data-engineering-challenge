package com.nflabs.service;

import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.List;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;

@Service("fileSystem")
public class FileSystemStorageService implements StorageService {
	private static Logger log = LoggerFactory.getLogger(FileSystemStorageService.class);

	@Autowired
	private ObjectMapper objectMapper;
	
	@Autowired
	private MaskService maskService;
	
	private Lock lock = new ReentrantLock();

	@Override
	public void store(String json) {
		log.debug("input json - " + json);
		if (json == null || json.isEmpty()) {
			throw new IllegalArgumentException("The input json must not be null or empty");
		}

		try (
				BufferedWriter data = Files.newBufferedWriter(Paths.get("data", "json-data"), StandardOpenOption.CREATE, StandardOpenOption.APPEND);
			) {
			lock.lock();
			String compactJson = json.replaceAll("\\s+", "");
			String maskedJson = mask(compactJson);
			data.write(maskedJson);
			data.write("\n");
			data.flush();
		} catch (IOException ie) {
			throw new RuntimeException(ie);
		}
		finally {
			lock.unlock();
		}
	}
	
	private String mask(String json) {	
		List<String> maskedFields = maskService.getMaskedFieldNamesInList();
		
		JsonNode rootNode = null;
		try {		
			rootNode = objectMapper.readTree(json);
		}
		catch(Exception e) {
			throw new RuntimeException(e);
		}
		
		for (String field : maskedFields) {
			List<JsonNode> parents = rootNode.findParents(field);
			parents.forEach((parentNode) -> {
				((ObjectNode) parentNode).put(field, "*");
			});
		}

		return rootNode.toString();	
	}
}
