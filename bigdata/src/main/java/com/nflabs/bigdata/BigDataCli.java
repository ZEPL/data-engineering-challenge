package com.nflabs.bigdata;

import java.util.HashMap;

import org.apache.commons.cli.BasicParser;
import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.CommandLineParser;
import org.apache.commons.cli.Option;
import org.apache.commons.cli.OptionBuilder;
import org.apache.commons.cli.Options;
import org.apache.commons.cli.ParseException;
import org.apache.commons.cli.HelpFormatter;

public class BigDataCli 
{
    public static void main( String[] args )
    {
    	Options options = new Options();
    	  	
    	Option number = OptionBuilder.withArgName("numbers")
    								.hasArgs()
    								.withDescription("run BigData with given numbers")
    								.withValueSeparator(' ')
    								.create("n");
    	Option help = OptionBuilder.withDescription("print this message")
    								.withLongOpt("help")
    								.create("h");
    	
    	options.addOption(number);
    	options.addOption(help);
    	
    	try{
    		CommandLineParser parser = new BasicParser();
    		CommandLine line = parser.parse(options, args);
    		
    		if(line.hasOption("n")){
    			String values[] = line.getOptionValues("n");
    			BigData bigData = new BigData(values);
    			HashMap<String, Object> resultMap = bigData.run();
    			bigData.printJson(resultMap);
    		}
    		
    		else if(line.hasOption("h") || line.hasOption("help")){
    			HelpFormatter formatter = new HelpFormatter();
    			formatter.printHelp( "bigdata", options );
    		}
    		
    		else{
    			HelpFormatter formatter = new HelpFormatter();
    			formatter.printHelp( "bigdata", options );
    		}
    	}
    	
    	catch(ParseException exp){
    		System.out.println("Unexpected exception:" + exp.getMessage());
    	}				
    }
}
