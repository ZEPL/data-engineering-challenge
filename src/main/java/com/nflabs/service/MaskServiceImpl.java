package com.nflabs.service;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service
public class MaskServiceImpl implements MaskService {
	private static Logger log = LoggerFactory.getLogger(MaskServiceImpl.class);
	
	private Lock lock = new ReentrantLock();
	
	@Override
	public void mask(String fieldNames) {
		mergeMaskedFields(fieldNames);
	}

	@Override
	public String getMaskedFieldNames() {
		return readMaskedFields();
	}
	
	private void mergeMaskedFields(String fieldNames) {
		Set<String> currentMaskedFields = toSet(readMaskedFields());
		Set<String> newMaskedFields = toSet(fieldNames);
	
		currentMaskedFields.addAll(newMaskedFields);
		writeMaskedFields(currentMaskedFields);
	}
	
	private Set<String> toSet(String fieldNames) {
		if(fieldNames == null || fieldNames.isEmpty()) {
			return new LinkedHashSet<>();
		}
		
		String[] splitFieldNames = fieldNames.split(",");
		for(int i=0; i<splitFieldNames.length; i++) {
			splitFieldNames[i] = splitFieldNames[i].trim();
		}
		
		Set<String> set = new LinkedHashSet<>(Arrays.asList(splitFieldNames));
		return set;
	}
	
	private void writeMaskedFields(Set<String> fieldNames) {
		String[] arr = fieldNames.toArray(new String[fieldNames.size()]);
		String fieldNamesToWrite = String.join(",", arr);
		
		try(
			BufferedWriter bw = Files.newBufferedWriter(Paths.get("data", "mask"), StandardOpenOption.TRUNCATE_EXISTING);
		) {
			lock.lock();
			bw.write(fieldNamesToWrite);
			bw.flush();
		}
		catch(IOException ie) {
			throw new RuntimeException(ie);
		}
		finally {
			lock.unlock();
		}
	}
	
	private String readMaskedFields() {
		try(
				BufferedReader br = Files.newBufferedReader(Paths.get("data", "mask"));
			) {
				String maskedFields = br.readLine();
				if(maskedFields == null || maskedFields.isEmpty()) {
					return null;
				}
				
				return maskedFields;
			}
			catch(IOException ie) {
				throw new RuntimeException(ie);
			}
	}

	@Override
	public List<String> getMaskedFieldNamesInList() {
		String maskedFields = readMaskedFields();
		if(maskedFields == null || maskedFields.isEmpty()) {
			return new ArrayList<>();
		}
		
		List<String> list = Arrays.asList(maskedFields.split(","));
		return list;
	}
}
