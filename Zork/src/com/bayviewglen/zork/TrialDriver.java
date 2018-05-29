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

	//starts tutorial
	public Trial tutorial(int sectionCounter, Player player) {	
		if (sectionCounter == 0) {
			System.out.println(player.getRoomShortDescription()); // fix exits?
			System.out.println("");
			player.setCurrentRoom(player.masterRoomMap.get("0-2"));
			System.out.println(player.getRoomDescription());
			
			Utils.formattedPrint(true, "There is a sharp high pitch ring in your ears as you vanish and appear in another room, much smaller than the one"
					+ " you were in before, with a sparring robot standing in front of you. It begins to speak:");
			System.out.println("\nRobot: Hello contest number 29886-E, please enter your name below.");
			System.out.print("> ");
			Scanner nameInput = new Scanner(System.in);
			player.setName(nameInput.nextLine());
			//nameInput.close(); forced resource leak?
			Utils.formattedPrint(false, "Robot: Excellent, " + player.name + ". Allow me to explain how combat functions within the contest. Your strength"
					+ " is determined by your stats and items. Each stat begins at a base stat and can be upgraded individually with attribute points."
					+ " These points are awarded to you each time you level up. You level up by defeating enemies. Here are the stats:");
			System.out.println("\nMax Health: Base: 10 Max: 150"
					+ "\nAttack: Base: 2, Max: 40"
					+ "\nDefense: Base: 0, Max: 20"
					+ "\nPlayer Speed: Base: 2, Max: 40"
					+ "\nPlayer Accuracy: Base: 90%, Max: 100%"
					+ "\nCritical Hit Chance: Base: 10%, Max: 60%");
			Utils.formattedPrint(true, "Items such as weapons increase your attack and critical hit stat while other items such as armor increase your defense stat."
					+ " When you engage in battle, you may choose 1 of 4 commands. You may choose to attack, use an item such as a healing potion (which restores"
					+ " health), or run away. The entity who attacks first is determined by the speed stat. This means if your enemy has a higher speed stat, it"
					+ " attacks first. When an attack is initiated, the entities defense stat along with any armor they have will negate some or most damage dealt,"
					+ " with the minimum being 1. The battle will continue in a turn-based fashion until one entity loses all their HP. You have been given a free"
					+ " Splintered Branch as a gift.");
			System.out.println("\nTo engage in battle, type in 'battle', followed by my name. You should end up typing:\nbattle Sparring Robot");

			return new Trial(2, "tutorial", "You must battle the Sparring Robot first!");
		} else {
			System.out.println("\nThe dummy breaks and collapses on the floor. It continues to speak..."
					+ "\nRobot: Congratulations, you will now be sent to the contest hall. Good luck!\n");
			player.setDefaultRoom();
			System.out.println(player.getRoomTravelDescription());
			tutorialComplete = true;
			return null;
		}
	}

	//starts trial 1
	public Trial trialOne(int sectionCounter, Player player) {
		if (sectionCounter == 0) {
			player.setCurrentRoom(player.masterRoomMap.get("1"));
			System.out.println(player.getRoomDescription());

			Utils.formattedPrint(true, "Narrator: You enter what looks like a hotel lobby. The building looks very medieval yet has a very artificial look to it. The lobby is"
					+ " covered in red and gold paint with a single hotel staff member working at the concierge. There are elevators or any other exits besides"
					+ " the way you came in. You approach the concierge and he begins to speak. As you approach him you see a single red door with a golden door"
					+ " handle with a large roman numeral 1. The concierge begins to speak:");
			Utils.formattedPrint(true, "George: Welcome to Trial 1, the first of 8 trials! My name is George, your guide through your trial adventure! There is only one"
					+ " direction you can go and that’s forward through the door behind me, however, you can also choose to go back to the main room using the abandon command."
					+ " Remember, you can't leave a trial once you begin fighting the boss. The only way you get out of that is in a coffin or if you win! Good Luck!");
			return new Trial(5, "Trial One", "You must battle the Squire before continuing!"); // change int
		} else if (sectionCounter == 1) {
			Utils.formattedPrint(true, "Narrator: You enter a circular room completely different from the first one. You are in what looks like a dungeon which is built"
					+ " entirely out of gray bricks. The wall is lined with torches that burn through the darkness. In the center of the room is a knight in shining"
					+ " silver armor holding a large attack sword. He raises his sword posing to fight.");
			return new Trial(5, "Trial One", "You must battle the Squire before continuing!"); // change int
		} else if (sectionCounter == 2) {
			Utils.formattedPrint(true, "Narrator: You can now proceed to the right through to the next room. You hear an unsheathing sword through the door to the right.");
			return new Trial(5, "Trial One", "");
		} else if (sectionCounter == 3) {
			Utils.formattedPrint(true, "Narrator: You enter a large throne room. On the throne at the far end of the room sits the king who towers over you by at least"
					+ " 3 feet. He stands up from his throne wielding his sword. He approaches and speaks with a loud booming voice.");
			Utils.formattedPrint(true, "Samurai King: You dare challenge the king to a duel?! You are sure to lose. I am the protector of the 1st key and I will do"
					+ " what I must to protect it. This is your last chance: stand down, or face my wrath.");
			return new Trial(5, "Trial One", "You must defeat the boss before continuing!");
		} else if (sectionCounter == 4) {
			Utils.formattedPrint(true, "Narrator: You have been teleported back to the contest hall. You notice a keychain that has mysteriously appeared in your pocket."
					+ " Attached to it is a single key, the key you were awarded for beating the first trial.");
			player.setDefaultRoom();
			System.out.println("\n" + player.getRoomTravelDescription());
			trialOneComplete = true;
			return null;
		}
		return null;
	}

	//starts trial 2 and checks if necessary trials are completed
	public Trial trialTwo() {

		/* Check if needed trials are completed
		if (!trialOneComplete)
			exitString += "needT1";//"You cannot access this trial until you complete Trial 1.";
		 */
		// Return Trial
		return new Trial(1, "trialtwo", "");  // change int
		//return new Trial(exitString, dialogue);
	}

	public Trial challengeGate(int sectionCounter, int difficulty, Player player) {
		if (sectionCounter == 0) {
			// easy, medium, hard


		}
		return new Trial(1, "challenge", "");
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
	
	public void loadTrials(String inputBooleans) {
		if (Utils.containsIgnoreCase(inputBooleans, "tutorial")) tutorialComplete = true;
		if (Utils.containsIgnoreCase(inputBooleans, "one")) trialOneComplete = true;
		if (Utils.containsIgnoreCase(inputBooleans, "two")) trialTwoComplete = true;
		if (Utils.containsIgnoreCase(inputBooleans, "three")) trialThreeComplete = true;
		if (Utils.containsIgnoreCase(inputBooleans, "four")) trialFourComplete = true;
		if (Utils.containsIgnoreCase(inputBooleans, "five")) trialFiveComplete = true;
		if (Utils.containsIgnoreCase(inputBooleans, "six")) trialSixComplete = true;
		if (Utils.containsIgnoreCase(inputBooleans, "seven")) trialSevenComplete = true;
		if (Utils.containsIgnoreCase(inputBooleans, "eight")) trialEightComplete = true;
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
	
	public boolean areAnyTrialsComplete() {
		return tutorialComplete || trialOneComplete || trialTwoComplete || trialThreeComplete || trialFourComplete || trialFiveComplete || trialSixComplete || trialSevenComplete || trialEightComplete;
	}

	// Returns string of object name 
	public String toString(){
		if (!areAnyTrialsComplete()) return "You haven't completed any trials!";
		ArrayList<String> returnArray = new ArrayList<String>(); //"Completed Trials: ";
		
		if (tutorialComplete) returnArray.add("Tutorial");
		if (trialOneComplete) returnArray.add("\nTrial One");
		if (trialTwoComplete) returnArray.add("\nTrial Two");
		if (trialThreeComplete) returnArray.add("\nTrial Threee");
		if (trialFourComplete) returnArray.add("\nTrial Four");
		if (trialFiveComplete) returnArray.add("\nTrial Five");
		if (trialSixComplete) returnArray.add("\nTrial Six");
		if (trialSevenComplete) returnArray.add("\nTrial Seven");
		if (trialEightComplete) returnArray.add("\nTrial Eight");
		
		String returnString = "Completed Trials:";
		for (int i = 0; i < returnArray.size(); i++) {
			returnString += " " + returnArray.get(i);

			if (i < returnArray.size() - 1) returnString += ",";
			else returnString += ".";
		}
		
		return returnString;
	}
}