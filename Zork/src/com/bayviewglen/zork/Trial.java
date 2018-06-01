package com.bayviewglen.zork;

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
	int number;

	Trial(int sections, String trialName, String leaveReason) {
		this.sections = sections;
		this.trialName = trialName;
		this.leaveReason = leaveReason;
	}
	
	Trial(int sections, String trialName, String leaveReason, int number) {
		this.sections = sections;
		this.trialName = trialName;
		this.leaveReason = leaveReason;
		this.number = number;
	}

	public int getSections() {
		return sections;
	}

	public String getLeaveReason() {
		return leaveReason;
	}
	
	public int getNumber() {
		return number;
	}

	// toString method
	public String toString() {
		return trialName;
	}
}
