package com.nflabs;

import java.nio.file.Files;
import java.nio.file.Paths;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class NflabsApplication implements CommandLineRunner {

	public static void main(String[] args) {
		SpringApplication.run(NflabsApplication.class, args);
	}

	@Override
	public void run(String... arg0) throws Exception {
		if(!Files.exists(Paths.get("data"))) {
			Files.createDirectory(Paths.get("data"));			
		}
		
		if(!Files.exists(Paths.get("data", "mask"))) {
			Files.createFile(Paths.get("data", "mask"));
		}
	}
}
