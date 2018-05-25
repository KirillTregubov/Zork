package com.bayviewglen.zork;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.List;

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

	// Completion booleans
	public boolean trialOneComplete;
	public boolean trialTwoComplete;
	public boolean trialThreeComplete;
	public boolean trialFourComplete;
	public boolean trialFiveComplete;
	public boolean trialSixComplete;
	public boolean trialSevenComplete;
	public boolean trialEightComplete;

	// Dialogue
	private int arraySize = 4;
	private List<List<String>> dialogue = new ArrayList<List<String>>(arraySize); // change as you go
	
	// Indexes of what dialogue is what room
	public final int TRIAL_ONE_DIALOGUE = 0;
	public final int TRIAL_TWO_DIALOGUE = 1;
	public final int TRIAL_THREE_DIALOGUE = 2;
	public final int TRIAL_FOUR_DIALOGUE = 3;

	TrialDriver() {
		trialOneComplete = false;
		trialTwoComplete = false;
		trialThreeComplete = false;
		trialFourComplete = false;
		trialFiveComplete = false;
		trialSixComplete = false;
		trialSevenComplete = false;
		trialEightComplete = false;

		try {
			BufferedReader reader = new BufferedReader(new FileReader(Game.FILE_LOCATION + "dialogue.dat"));
			String line;

			for (int i = 0; i < arraySize; i++) {
				// dialogue.add(new ArrayList<String>()); for test purposes
				while ((line = reader.readLine()).length() > 1) {
					dialogue.get(i).add(line);
				}
			}
			reader.close();
			
			/* Testing correct array initialization
			for (List<String> strs : dialogue) {
				System.out.println("NEW TRIAL");
				for (String str : strs) {
					System.out.println(str);
				}
			} */
		} catch (Exception e) {
			System.out.println("AN ERROR OCCURRED");
		}
	}

	//starts trial 1
	public static void TrialOneStart (Player player) {
		//run trial
	}

	//starts trial 2 and checks if necessary trials are completed
	public Trial TrialTwoStart () {
		String exitString = "";

		// Check if needed trials are completed
		if (!trialOneComplete)
			exitString += "needT1";//"You cannot access this trial until you complete Trial 1.";

		// Return Trial
		return new Trial(exitString, new ArrayList<String>());
		//return new Trial(exitString, dialogue);
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
