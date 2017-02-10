package com.nflabs.controller;

import lombok.Getter;

public class Error extends Result {
	@Getter
	private String error;
	
	public Error(Status result, String error) {
		super(result);
		this.error = error;
	}
}
