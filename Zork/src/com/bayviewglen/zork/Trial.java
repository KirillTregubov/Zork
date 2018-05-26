package com.bayviewglen.zork;

import java.util.ArrayList;

/** "Trial" Class - generates all the game's trials.
 * 
 *  Authors: 		Kirill Tregubov, Zacharia Burrafato, Andrew Douglas, Alim Halani
 *  Code Version:	0.2-alpha
 *  Published Date:	May 2018
 */

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
