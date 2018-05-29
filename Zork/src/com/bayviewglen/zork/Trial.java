package com.bayviewglen.zork;

import java.util.ArrayList;

/** "Trial" Class - generates all the game's trials.
 * 
 *  Authors: 		Kirill Tregubov, Zacharia Burrafato, Andrew Douglas, Alim Halani
 *  Code Version:	0.3-alpha
 *  Published Date:	May 2018
 */

public class Trial {

	private int sections;
	private String trialName;
	String leaveReason = "";
	//private ArrayList<String> dialogue;

	Trial(int sections, String trialName, String leaveReason) {
		this.sections = sections;
		this.trialName = trialName;
		this.leaveReason = leaveReason;
	}

	public int getSections() {
		return sections;
	}

	public String getLeaveReason() {
		return leaveReason;
	}

	// toString method
	public String toString() {
		return trialName;
	}
}
