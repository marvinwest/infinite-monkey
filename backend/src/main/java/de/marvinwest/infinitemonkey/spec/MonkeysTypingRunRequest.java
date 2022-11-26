package de.marvinwest.infinitemonkey.spec;

import java.util.Optional;

public class MonkeysTypingRunRequest {

	private String targetText;

	private String alphabet;

	private Integer persistanceThreshold;
	
	MonkeysTypingRunRequest() {
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
