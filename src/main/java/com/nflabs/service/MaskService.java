package com.nflabs.service;

import java.util.List;

public interface MaskService {
	/**
	 * 
	 * @param fieldNames - comma separated masked field names
	 */
	void mask(String fieldNames);
	
	/**
	 * 
	 * @return - comma separated masked field names
	 */
	String getMaskedFieldNames();
	
	/**
	 * 
	 * @return - masked field names in list
	 */
	List<String> getMaskedFieldNamesInList();
}
