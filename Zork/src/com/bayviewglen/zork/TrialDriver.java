package com.bayviewglen.zork;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;

public class TrialDriver {

	/*
	 * Trial Class will:
	 * 
	 * - Update "exits" that go further in the rooms
	 * - Allows battles to occur
	 * - Executes any special commands that a room might have
	 * - Limit to which ones you can enter / start
	 * - Is in charge of all the dialogue with characters
	 * - Tracks what enemies are defeated and doesn't respawn them until the trial is complete
	 * - Influences the parser in some way
	 * 
	 * Boss rooms:
	 * - Can't leave them (updates exits)
	 */

	boolean trialOneComplete;
	boolean trialTwoComplete;
	boolean trialThreeComplete;
	boolean trialFourComplete;
	boolean trialFiveComplete;
	boolean trialSixComplete;
	boolean trialSevenComplete;
	boolean trialEightComplete;

	TrialDriver() {
		trialOneComplete = false;
		trialTwoComplete = false;
		trialThreeComplete = false;
		trialFourComplete = false;
		trialFiveComplete = false;
		trialSixComplete = false;
		trialSevenComplete = false;
		trialEightComplete = false;
	}

	//starts trial 1
	public static void TrialOneStart (Player player) {
		//run trial
	}

	//starts trial 2 and checks if necessary trials are completed
	public Trial TrialTwoStart () {
		String exitString = "";
		BufferedReader reader = new BufferedReader(new FileReader(Game.FILE_LOCATION + "dialogue.dat"));
		ArrayList<String> dialogue = new ArrayList<>();
		String line;
		
		try {
			while((line = reader.readLine()) != null) {
				if (line.equals("TRIAL TWO START")) {
					
				}
			}
		} catch (Exception e) {
			System.out.println("AN ERROR OCCURRED");
		}
		/*ArrayList<String> dialogue = new ArrayList<>(Arrays.asList(new String[] {
			"",
			"",
			""
		}));*/

		// Check if needed trials are completed
		if (!trialOneComplete)
			exitString += "needT1";//"You cannot access this trial until you complete Trial 1.";

		// Compose the ArrayList


		// Return Trial
		return new Trial(exitString, dialogue);
	}

	//starts trial 3 and checks if necessary trials are completed
	public void TrialThreeStart () {
		if (isComplete(trialTwoComplete)==false)
			System.out.println("You cannot access this trial until you complete Trial 2.");
	}

	//checks if necessary trials are completed to start a trial
	public boolean isComplete (boolean preReq) {
		if (!preReq) {
			System.out.println("You cannot access this trial until you complete the previous trial.");
			//does not change position
			return false;
		}else
			return true;

	}
}
