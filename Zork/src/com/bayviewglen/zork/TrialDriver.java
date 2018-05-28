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
	private boolean tutorialComplete;
	private boolean trialOneComplete;
	private boolean trialTwoComplete;
	private boolean trialThreeComplete;
	private boolean trialFourComplete;
	private boolean trialFiveComplete;
	private boolean trialSixComplete;
	private boolean trialSevenComplete;
	private boolean trialEightComplete;

	TrialDriver() {
		tutorialComplete = false;
		trialOneComplete = false;
		trialTwoComplete = false;
		trialThreeComplete = false;
		trialFourComplete = false;
		trialFiveComplete = false;
		trialSixComplete = false;
		trialSevenComplete = false;
		trialEightComplete = false;
	}

	public boolean[] saveTrials() {
		boolean[] completionBooleans = { tutorialComplete, trialOneComplete, trialTwoComplete, trialThreeComplete, trialFourComplete, trialFiveComplete, trialSixComplete, trialSevenComplete, trialEightComplete };
		return completionBooleans;
	}

	public boolean isTutorialComplete() {
		return tutorialComplete;
	}

	public boolean areBaseTrialsComplete() {
		return tutorialComplete && trialOneComplete && trialTwoComplete && trialThreeComplete && trialFourComplete && trialFiveComplete && trialSixComplete && trialSevenComplete;
	}

	public boolean isGameBeaten() {
		return trialEightComplete;
	}

	//starts tutorial
	public Trial tutorial(int sectionCounter, Player player) {	
		if (sectionCounter == 0) {
			System.out.println(player.getRoomShortDescription()); // fix exits?
			System.out.println("");
			player.setCurrentRoom(player.masterRoomMap.get("0-2"));
			System.out.println(player.getRoomDescription());

			System.out.println("\nRobot: Hello contest number 29886-E, please enter your name below.");
			System.out.print("> ");
			Scanner nameInput = new Scanner(System.in);
			player.setName(nameInput.nextLine());
			//nameInput.close(); forced resource leak?
			Utils.formattedPrint("Robot: Excellent, " + player.name + ". Allow me to explain how combat functions within the contest. Your strength"
					+ " is determined by your stats and items. Each stat begins at a base stat and can be upgraded individually with attribute points."
					+ " These points are awarded to you each time you level up. You level up by defeating enemies. Here are the stats:");
			System.out.println("\nMax Health: Base: 10 Max: 150"
					+ "\nAttack: Base: 2, Max: 40"
					+ "\nDefense: Base: 0, Max: 20"
					+ "\nPlayer Speed: Base: 2, Max: 40"
					+ "\nPlayer Accuracy: Base: 90%, Max: 100%"
					+ "\nCritical Hit Chance: Base: 10%, Max: 60%\n");
			Utils.formattedPrint("Items such as weapons increase your attack and critical hit stat while other items such as armor increase your defense stat."
					+ " When you engage in battle, you may choose 1 of 4 commands. You may choose to attack, use an item such as a healing potion (which restores"
					+ " health), or run away. The entity who attacks first is determined by the speed stat. This means if your enemy has a higher speed stat, it"
					+ " attacks first. When an attack is initiated, the entities defense stat along with any armor they have will negate some or most damage dealt,"
					+ " with the minimum being 1. The battle will continue in a turn-based fashion until one entity loses all their HP. You have been given a free"
					+ " Splintered Branch as a gift.");
			System.out.println("\nTo engage in battle, type in 'battle', followed by my name. You should end up typing:\nbattle Sparring Robot");

			return new Trial(2, "tutorial", false, false, "", "You must battle the Sparring Robot first!");
		} else {
			System.out.println("\nThe dummy breaks and collapses on the floor. It continues to speak..."
					+ "\nRobot: Congratulations, you will now be sent to the contest hall. Good luck!\n");
			player.setCurrentRoom(player.masterRoomMap.get("0-0"));
			System.out.println(player.getRoomTravelDescription());
			tutorialComplete = true;
			return null;
		}
	}

	//starts trial 1
	public Trial trialOne(int sectionCounter, Player player) {
		if (sectionCounter == 0) {
			//run trial
			Utils.formattedPrint("You enter what looks like a hotel lobby. The building looks very medieval yet has a very artificial look to it. The lobby is"
					+ " covered in red and gold paint with a single hotel staff member working at the concierge. There are no elevators or any other exits besides"
					+ " the way you came in. You approach the concierge and he begins to speak. As you approach him you see a single red door with a golden door"
					+ " handle with a large roman numeral 1. The concierge begins to speak:");
			System.out.println("");
			Utils.formattedPrint("George: Welcome to Trial 1, the 1st of 10 trials! My name is George, your guide through your trial adventure! There is only one"
					+ " direction you can go and that’s forward through the door behind me, however, you can also choose to go back to the main room."
					+ " Remember, you can't leave a trial once you begin fighting the boss. The only way you get out of that is in a coffin or if you win! Good Luck!");

			player.setCurrentRoom(player.masterRoomMap.get("1"));
			System.out.println("\n" + player.getRoomDescription());

			return new Trial(2, "trialone"); // change int
		} else if (sectionCounter == 1) {
			System.out.println("");
			Utils.formattedPrint("You enter a circular room completely different from the first one. You are in what looks like a dungeon which is built entirely"
					+ " out of gray bricks. The wall is lined with torches that burn through the darkness. In the center of the room is a knight in shining silver"
					+ " armor holding a large attack sword. He raises his sword posing to fight.");
			
			return new Trial(2, "trialone", false, true, "down", "You must battle the Sparring Robot first!"); //temporary
		} else {
			return null;
		}

	}

	//starts trial 2 and checks if necessary trials are completed
	public Trial trialTwo() {

		/* Check if needed trials are completed
		if (!trialOneComplete)
			exitString += "needT1";//"You cannot access this trial until you complete Trial 1.";
		 */
		// Return Trial
		return new Trial(1, "trialtwo");  // change int
		//return new Trial(exitString, dialogue);
	}

	public Trial challengeGate(int sectionCounter, int difficulty, Player player) {
		if (sectionCounter == 0) {
			// easy, medium, hard


		}
		return new Trial(1, "challenge");
	}

	/*
	//starts trial 3 and checks if necessary trials are completed
	public void trialThree() {
		if (isComplete(trialTwoComplete)==false)
			System.out.println("You cannot access this trial until you complete Trial 2.");
	}
	 */
	//checks if necessary trials are completed to start a trial
	public boolean isComplete(boolean preReq) {
		if (!preReq) {
			System.out.println("You cannot access this trial until you complete the previous trial.");
			//does not change position
			return false;
		}else
			return true;

	}
}
