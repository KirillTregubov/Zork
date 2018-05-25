package com.bayviewglen.zork;

import java.util.ArrayList;

public class Trial {

	private String exitString;
	private ArrayList<String> dialogue;

	Trial(String exitString, ArrayList<String> dialogue) {
		this.exitString = exitString;
		this.dialogue = dialogue;
	}

	// toString method
	public String toString() {
		return exitString;
	}
}
