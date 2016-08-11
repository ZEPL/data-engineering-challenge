package com.nflabs.controller;

import com.fasterxml.jackson.annotation.JsonValue;

import lombok.Getter;

public class Result {
	@Getter
	private Status result;
	
	public Result(Status result) {
		this.result = result;
	}
	
	public static enum Status {
		SUCCESS,
		FAILS
		;
		
		@JsonValue
		public String value() {
			return name().toLowerCase();
		}
	}
}
