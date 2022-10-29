package de.marvinwest.infinitemonkey.controllers;

public class TextHitState {

	private int endIndex;

	private String currentText;

	public TextHitState(String currentText) {
		this.endIndex = 1;
		this.currentText = currentText;
	}
	
	public void addTextHit(String addedCharacter) {
		this.currentText += addedCharacter;
		this.endIndex++;
	}

	public int getEndIndex() {
		return endIndex;
	}

	public String getCurrentText() {
		return currentText;
	}

}
