package de.marvinwest.infinitemonkey.spec;

import java.util.Optional;

import com.fasterxml.jackson.annotation.JsonProperty;

public class RunRequest {

	private String targetText;

	private String alphabet;

	@JsonProperty
	private Integer persistanceThreshold;
	
	RunRequest() {
	}

	public String getTargetText() {
		return targetText;
	}

	public String getAlphabet() {
		return alphabet;
	}

	public Optional<Integer> maybePersistanceThreshold() {
		return Optional.ofNullable(persistanceThreshold);
	}

}
