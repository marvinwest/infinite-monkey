package de.marvinwest.infinitemonkey.spec;

public class RunResponse {

	private Long id;
	
	private String targetText;
	
	private String alphabet;
	
	private Integer persistanceThreshold;

	public RunResponse(Long id, String targetText, String alphabet) {
		this.id = id;
		this.targetText = targetText;
		this.alphabet = alphabet;
	}

	public Long getId() {
		return id;
	}

	public String getTargetText() {
		return targetText;
	}

	public String getAlphabet() {
		return alphabet;
	}
	
	public Integer getPersistanceThreshold() {
		return persistanceThreshold;
	}

	public void setPersistanceThreshold(Integer persistanceThreshold) {
		this.persistanceThreshold = persistanceThreshold;
	}
	
}
