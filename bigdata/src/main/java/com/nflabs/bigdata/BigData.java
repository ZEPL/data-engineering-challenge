package com.nflabs.bigdata;

import java.util.HashMap;

import org.apache.commons.math3.stat.descriptive.DescriptiveStatistics;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

public class BigData {
	
	private DescriptiveStatistics stat;
	
	private Gson gson = new GsonBuilder().setPrettyPrinting().serializeNulls().create();
	
	public BigData(String[] values){
		double[] tmpArr = new double[values.length];
		for (int i=0; i<values.length; i++)
		{
			tmpArr[i] = Double.parseDouble(values[i]);
		}
		this.stat = new DescriptiveStatistics(tmpArr);
	}
	
	public HashMap<String, Object> run(){
		HashMap<String, Object> resultMap = new HashMap<String, Object>();
		resultMap.put("sum", stat.getSum());
		resultMap.put("avg", stat.getMean());
		resultMap.put("median", stat.getPercentile(50));
		resultMap.put("max", stat.getMax());
		resultMap.put("count", stat.getN());
		
		return resultMap;
	}
	
	public void printJson(HashMap<String, Object> resultMap){
		String result = gson.toJson(resultMap);
		System.out.println(result);
	}
}
