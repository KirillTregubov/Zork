package com.bayviewglen.zork;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

/** "Trial Driver" Class - is in charge of processing and leading the player through trials.
 * 
 *  Authors: 		Kirill Tregubov, Zacharia Burrafato, Andrew Douglas, Alim Halani
 *  Code Version:	0.3-alpha
 *  Published Date:	May 2018
 */

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
				dialogue.add(new ArrayList<String>());
				while ((line = reader.readLine()).length() > 1) {
					dialogue.get(i).add(line);
				}
			}
			reader.close();

			/*Testing correct array initialization
			for (List<String> strs : dialogue) {
				System.out.println("NEW TRIAL");
				for (String str : strs) {
					System.out.println(str);
				}
			}*/
		} catch (Exception e) {
			System.out.println("AN ERROR OCCURRED");
		}
	}

	//starts tutorial
	public Trial tutorialStart (Player player) {		
		System.out.println(player.getRoomShortDescription()); // fix exits?
		System.out.println("");
		player.setCurrentRoom(player.masterRoomMap.get("0-2"));
		System.out.println(player.getRoomDescription());

		System.out.println("\nRobot: Hello contest number 29886-E, please enter your name below.");
		System.out.print("> ");
		player.setName(new Scanner(System.in).nextLine());
		System.out.println("\n" + "Robot: Excellent, " + player.name + ". Allow me to explain how combat functions within the contest. Your strength"
				+ "\nis determined by your stats and items. Each stat begins at a base stat and can be upgraded"
				+ "\nindividually with attribute points. These points are awarded to you each time you level up."
				+ "\nYou level up by defeating enemies. Here are the stats:\n"
				+ "\nMax Health: Base: 10 Max: 150"
				+ "\nAttack: Base: 2, Max: 40"
				+ "\nDefense: Base: 0, Max: 20"
				+ "\nPlayer Speed: Base: 2, Max: 40"
				+ "\nPlayer Accuracy: Base: 90%, Max: 100%"
				+ "\nCritical Hit Chance: Base: 10%, Max: 60%\n"
				+ "\nItems such as weapons increase your attack and critical hit stat while other items such as armor"
				+ "\nincrease your defense stat.\n"
				+ "\nWhen you engage in battle, you may choose 1 of 4 commands. You may choose to attack. use an item"
				+ "\nsuch as a healing potion which restores health, or run away. The entity who attacks first is"
				+ "\ndetermined by the speed stat. However, has a higher speed stat, attacks first. When an attack is"
				+ "\ninitiated, the entities defense stat along with any armor they have will negate some or all damage"
				+ "\ndealt. The battle will continue in a turn-based fashion until one entity loses all their HP. It is"
				+ "\npossible however quite unlikely that an enemy will not only block but also counter attack. This"
				+ "\ncounter attack does not count as their turn so bear that in mind. You have been given a"
				+ "\nfree Splintered Branch as a gift.\n"
				+ "\nTo engage in battle, type in 'battle', followed by my name. You should end up typing:"
				+ "\nbattle Sparring Robot");

		return new Trial("tutorialstart", false, "You must battle the Sparring Robot first!");
	}

	public Trial tutorialEnd (Player player) {
		System.out.println("\nThe dummy breaks and collapses on the floor. It continues to speak."
				+ "\nRobot: Congratulations, you will now be sent to the contest hall. Good luck!\n");
		player.setCurrentRoom(player.masterRoomMap.get("0-0"));
		System.out.println(player.getRoomTravelDescription());

		return null; // reset currentTrial
	}

	//starts trial 1
	public static void trialOne (Player player) {
		//run trial
	}

	//starts trial 2 and checks if necessary trials are completed
	public Trial trialTwo () {
		String exitString = "";

		// Check if needed trials are completed
		if (!trialOneComplete)
			exitString += "needT1";//"You cannot access this trial until you complete Trial 1.";

		// Return Trial
		return new Trial("trialtwo", exitString);//, new ArrayList<String>());
		//return new Trial(exitString, dialogue);
	}

	//starts trial 3 and checks if necessary trials are completed
	public void trialThree () {
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
