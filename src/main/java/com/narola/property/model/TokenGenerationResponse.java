package com.narola.property.model;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter @AllArgsConstructor
public class TokenGenerationResponse {
	private final String jwt;
}
