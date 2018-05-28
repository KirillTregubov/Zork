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
	private boolean canContinue = true;
	private boolean canFlee = true;
	private String leaveReason = "";
	private String fleeDirection = "";
	//private ArrayList<String> dialogue;

	Trial(int sections, String trialName) {
		this.sections = sections;
		this.trialName = trialName;
	}

	Trial(int sections, String trialName, boolean canContinue, boolean canFlee, String fleeDirection, String leaveReason) {
		this.sections = sections;
		this.trialName = trialName;
		this.canContinue = canContinue;
		this.canFlee = canFlee;
		this.fleeDirection = fleeDirection;
		this.leaveReason = leaveReason;
	}
	
	public int getSections() {
		return sections;
	}
	
	public String getLeaveReason() {
		return leaveReason;
	}

	public boolean canContinue() {
		return canContinue;
	}
	
	public boolean canFlee() {
		return canFlee;
	}
	
	public String getFleeDirection() {
		return fleeDirection;
	}

	// toString method
	public String toString() {
		return trialName;
	}
}
