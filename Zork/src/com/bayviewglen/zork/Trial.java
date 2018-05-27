package com.bayviewglen.zork;

import java.util.ArrayList;

/** "Trial" Class - generates all the game's trials.
 * 
 *  Authors: 		Kirill Tregubov, Zacharia Burrafato, Andrew Douglas, Alim Halani
 *  Code Version:	0.3-alpha
 *  Published Date:	May 2018
 */

public class Trial {

	private String trialName;
	private String exitString;
	private boolean canLeave;
	private String leaveReason;
	//private ArrayList<String> dialogue;

	Trial(String trialName) {
		this.trialName = trialName;
	}

	Trial(String trialName, boolean canLeave, String leaveReason) {
		this.trialName = trialName;
		this.canLeave = canLeave;
		this.leaveReason = leaveReason;
	}

	Trial(String trialName, String exitString) {
		this.trialName = trialName;
		this.exitString = exitString;
		//this.dialogue = dialogue;
	}

	public String getExitString() {
		return exitString;
	}

	public String getLeaveReason() {
		return leaveReason;
	}

	public boolean canLeave() {
		return canLeave;
	}

	// toString method
	public String toString() {
		return trialName;
	}
}
