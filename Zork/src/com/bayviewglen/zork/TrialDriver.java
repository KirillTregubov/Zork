package com.bayviewglen.zork;

import java.util.ArrayList;
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
	private Player player;

	TrialDriver(Player player) {
		tutorialComplete = false;
		trialOneComplete = false;
		trialTwoComplete = false;
		trialThreeComplete = false;
		trialFourComplete = false;
		trialFiveComplete = false;
		trialSixComplete = false;
		trialSevenComplete = false;
		trialEightComplete = false;

		this.player = player;
	}

	//starts tutorial
	public Trial tutorial(int sectionCounter) {	
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
			System.out.println("\nTo engage in battle, type in 'battle', followed by the enemy's name. You should end up typing:\nbattle Sparring Robot");

			return new Trial(2, "tutorial", "You must battle the Sparring Robot first!");
		} else {
			System.out.println("\nThe dummy breaks and collapses on the floor. It continues to speak..."
					+ "\n\nRobot: Congratulations, you will now be sent to the contest hall. Good luck!\n");
			player.setDefaultRoom();
			System.out.println(player.getRoomTravelDescription());
			tutorialComplete = true;
			return null;
		}
	}

	//starts trial 1
	public Trial trialOne(int sectionCounter) {
		if (sectionCounter == 0) {
			player.setCurrentRoom(player.masterRoomMap.get("1"));
			System.out.println(player.getRoomDescription());

			Utils.formattedPrint(true, "Narrator: You enter what looks like a hotel lobby. The building looks very medieval yet has a very artificial look to it. The lobby is"
					+ " covered in red and gold paint with a single hotel staff member working at the concierge. There are elevators or any other exits besides"
					+ " the way you came in. You approach the concierge and he begins to speak. As you approach him you see a single red door with a golden door"
					+ " handle with a large roman numeral 1. The concierge begins to speak:");
			Utils.formattedPrint(true, "George: Welcome to Trial 1, the first of 8 trials! My name is George, your guide through your trial adventure! There is only one"
					+ " direction you can go and that�s forward through the door behind me, however, you can also choose to go back to the main room using the abandon command."
					+ " Remember, you can't leave a trial once you begin fighting the boss. The only way you get out of that is in a coffin or if you win! Good Luck!");
			return new Trial(5, "Trial One", "You must battle the Squire before continuing!"); // change int
		} else if (sectionCounter == 1) {
			Utils.formattedPrint(true, "Narrator: You enter a circular room completely different from the first one. You are in what looks like a dungeon which is built"
					+ " entirely out of gray bricks. The wall is lined with torches that burn through the darkness. In the center of the room is a knight in shining"
					+ " silver armor holding a large attack sword. He raises his sword posing to fight.");
			return new Trial(5, "Trial One", "You must battle the Squire before continuing!"); // change int
		} else if (sectionCounter == 2) {
			System.out.println("\nNarrator: You can now proceed up to the next room."
					+ "\n\nYou hear an unsheathing sword through the door.");
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

	public Trial shop(int sectionCounter, Shop shop) {
		if (sectionCounter == 0) {
			System.out.println("\nWelcome to the shop! We offer the best items at the lowest possible prices!" + shop.displayItems());
			return new Trial(2, "Shop", "");
		} else if (sectionCounter == 1) {
			Utils.formattedPrint(false, "Leave Shop"); // print before going east?
			player.setDefaultRoom();
			System.out.println("\n" + player.getRoomTravelDescription());
			return null;
		}
		return null;
	}

	//starts trial 2 and checks if necessary trials are completed
	public Trial trialTwo(int sectionCounter) {
		if (sectionCounter == 0) {
			player.setCurrentRoom(player.masterRoomMap.get("2"));
			System.out.println(player.getRoomDescription());
			// Return Trial
			return new Trial(2, "trialtwo", "");  // change int
		} else if (sectionCounter == 1) {

			// Return Trial
			return new Trial(2, "trialtwo", "");  // change int
		}
		return null;
	}

	public Trial challengeGate(int sectionCounter, int difficulty) {
		if (sectionCounter == 0) {
			Utils.formattedPrint(false, "Welcome to the Challenge Gate! Please select a difficulty: easy, medium, or hard.");
			int inputDifficulty = -2;
			while (inputDifficulty != 0 && inputDifficulty != 1 && inputDifficulty != 2) {
				inputDifficulty = Parser.getDifficultyAnswer();
				if (inputDifficulty == -1)
					System.out.println("Please try again...");
			}

			return new Trial(3, "Challenge Gate", "", inputDifficulty);
		} else if (sectionCounter == 1) {
			// easy, medium, hard
			String difficultyString = "";
			String enemyName = "";
			// easy, medium, hard || 0,1,2
			//10,10,1,0,1,0.5,0.0	
			//Easy: 
			//1.	Training Bot (10HP/1ATK/0ARM/1SPD)
			//2.	Squire (10HP/1ATK/1ARM/1SPD) Drops: Wooden Rapier 70%, Copper Katana 50%, Silver Longsword 10%, Leather Suit 80%
			//3.	Samurai King (Boss) (15HP/3ATK/2ARM/2SPD) Drops: Copper Katana 100%, Silver Longsword 40%, Small Healing Potion 100%, Steel Dagger 20%
			if (difficulty == 0) {
				difficultyString = "easy";
				Entity easyEnt1 = new Entity("Training Bot",Entity.ENEMY_INDEX,"10,10,1,0,1,0.7,0.0");
				Entity easyEnt2 = new Entity("Squire",Entity.ENEMY_INDEX,"10,10,1,1,1,0.7,0.0");
				Entity easyEnt3 = new Entity("Samurai King",Entity.BOSS_INDEX,"15,15,3,2,2,0.7,0.0");
				int rand = (int) (Math.random()* 3 + 1);
				if (rand == 1) {
					player.getRoomAddEntity(easyEnt1);
					enemyName = easyEnt1.name;
				} else if (rand == 2) {
					player.getRoomAddEntity(easyEnt2);
					enemyName = easyEnt2.name;
				} else if (rand == 3) {
					player.getRoomAddEntity(easyEnt3);
					enemyName = easyEnt3.name;
				}
			}

			//Medium
			//1.	The Pharaoh (17HP/3ATK/2ARM/1SPD) Drops: Steel Dagger 40%, Cobalt Broadsword 30%, Knight�s Armor 70%, Iron Spear 20%, Small Heal Potion 60%
			//2.	The Living Shadow (20HP/3ATK/2ARM/2SPD) Drops: Iron Spear 60%, Staff of Life Drain 30%, Golden Bamboo Sword 20%, Titanium Blast Plate Armor 30% Small Heal Potion 60%
			//3.	Sky-night Warrior (22HP/4ATK/2ARM/2SPD) Drops: Titanium Scythe 40%, Gauntlet of Terror 20%, Donkey Kong Hammer 10%, Vibranium Heavy Armor 30%, Small Heal Potion 60%
			else if (difficulty == 1) {
				difficultyString = "medium";
				Entity mediumEnt1 = new Entity("The Pharaoh",Entity.ENEMY_INDEX,"17,17,3,2,1,0.7,0.0");
				Entity mediumEnt2 = new Entity("The Living Shadow",Entity.ENEMY_INDEX,"20,20,3,2,2,0.7,0.0");
				Entity mediumEnt3 = new Entity("Sky-night Warrior",Entity.BOSS_INDEX,"22,22,4,2,2,0.7,0.0");
				int rand = (int) (Math.random() * 3 + 1);
				if (rand == 1) {
					player.getRoomAddEntity(mediumEnt1);
					enemyName = mediumEnt1.name;
				} else if (rand == 2) {
					player.getRoomAddEntity(mediumEnt2);
					enemyName = mediumEnt2.name;
				} else if (rand == 3) {
					player.getRoomAddEntity(mediumEnt3);
					enemyName = mediumEnt3.name;
				}
			}
			//Hard
			//3.	Lunatic (35HP/5ATK/2ARM/2PD) Drops: Gaunlet of Terror 40%, Donkey Kong Hammer 30%, Vile Blade 20%, Electromagnetic Shield Generator 30%, Medium Health Potion 50%
			//4.	Eyeless Child (Boss) (40HP/6ATK/4ARM/2SPD) Drops: Genji�s Dragonblade 30%, Staff of Gandalf 20%, Enchanted Sabre 10%, Sky-night Armor 20%
			//5.	The Titan King Kronos (45HP/10ATK/5ARM/3SPD) Staff of Gandalf 40%, Dark Angel Armor 20%, Medium Heal Potion 30%
			//6.	Our Lord and Savior George (60HP/15ATK/10ARM/5SPD)
			else if (difficulty == 2) {
				difficultyString = "hard";
				Entity hardEnt1 = new Entity("Lunatic",Entity.ENEMY_INDEX,"35,35,5,2,2,0.9,0.0");
				Entity hardEnt2 = new Entity("The Pharaoh",Entity.BOSS_INDEX,"40,40,6,4,2,0.9,0.0");
				Entity hardEnt3 = new Entity("The Living Shadow",Entity.BOSS_INDEX,"45,45,10,5,3,0.9,0.0");
				Entity hardEnt4 = new Entity("Derp",Entity.BOSS_INDEX,"60,60,15,10,5,0.9,0.0");
				int rand = (int) (Math.random()* 4 + 1);
				if (rand == 1) {
					player.getRoomAddEntity(hardEnt1);
					enemyName = hardEnt1.name;
				} else if (rand == 2) {
					player.getRoomAddEntity(hardEnt2);
					enemyName = hardEnt2.name;
				} else if (rand == 3) {
					player.getRoomAddEntity(hardEnt3);
					enemyName = hardEnt3.name;
				} else if (rand == 4) {
					player.getRoomAddEntity(hardEnt4);
					enemyName = hardEnt4.name;
				}
				
			}
			else {
				System.out.println("No difficulty specified.");
			}
			
			if (!difficultyString.isEmpty()) {
				Utils.formattedPrint(false, "You selected the " + difficultyString + " difficulty. You will now face " + enemyName + "!");
			}
			return new Trial(3, "Challenge Gate", "");
		}
		return null;
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

	public boolean isFirstTrialComplete() {
		return tutorialComplete && trialOneComplete;
	}

	public boolean areFiveTrialsComplete() {
		return tutorialComplete && trialOneComplete && trialTwoComplete && trialThreeComplete && trialFourComplete && trialFiveComplete;
	}

	public boolean areSixTrialsComplete() {
		return tutorialComplete && trialOneComplete && trialTwoComplete && trialThreeComplete && trialFourComplete && trialFiveComplete && trialSixComplete;
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
		if (trialOneComplete) returnArray.add("Trial One");
		if (trialTwoComplete) returnArray.add("Trial Two");
		if (trialThreeComplete) returnArray.add("Trial Threee");
		if (trialFourComplete) returnArray.add("Trial Four");
		if (trialFiveComplete) returnArray.add("Trial Five");
		if (trialSixComplete) returnArray.add("Trial Six");
		if (trialSevenComplete) returnArray.add("Trial Seven");
		if (trialEightComplete) returnArray.add("Trial Eight");

		String returnString = "Completed Trials:";
		for (int i = 0; i < returnArray.size(); i++) {
			returnString += " " + returnArray.get(i);

			if (i < returnArray.size() - 1) returnString += ",";
			else returnString += ".";
		}

		return returnString;
	}
}