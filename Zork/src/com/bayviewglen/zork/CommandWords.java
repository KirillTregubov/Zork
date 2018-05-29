package com.bayviewglen.zork;

/** "CommandWords" Class - stores all commands of the game.
 * 
 *  Original Code Author: 	Michael Kolling
 *  Original Code Version:	1.0
 *  Original Published Date: July 1999
 * 
 *  Current Authors: 		Kirill Tregubov, Zacharia Burrafato, Andrew Douglas, Alim Halani
 *  Current Code Version:	0.3-alpha
 *  Current Published Date:	May 2018
 * 
 *  This class holds an enumeration of all command words known to the game.
 *  It is used to recognize commands as they are typed in.
 */

class CommandWords {
	// a constant array that holds all valid command words
	private static final String validCommands[][] = {
			{"help", "default", "Prints the help message. Usage: \"help\""},
			{"list", "default", "Lists things. Usage: \"list commands\""}, // add an example if list gains more uses
			{"start", "trial", "Starts a trial. Usage: \"start trial one\", \"start trial 2\""},
			{"abandon", "default", "Abandon a trial. Usage: \"abandon trial one\", \"abandon trial 2\""},
			{"heal", "default", "Heals you (if you are in the Healing Center). Usage: \"heal me\""},
			{"go", "place", "Allows you to go places. Usage: \"go west\", \"go down\""},
			{"walk", "place", "Allows you to walk places. Usage: \"walk west\", \"walkT down\""},
			//{"teleport", "place", "\"Cheater\" command. Remove before final release!"},
			//{"tp", "place", "\"Cheater\" command. Remove before final release!"},
			//{"give", "item", "\"Cheater\" command. Remove before final release!"},
			{"consume", "item", "Allows you to consume stuff. Usage: \"consume apple\", \"consume sword\""},
			{"look", "inventory item place enemy", "Allows you to look at things. Usage: \"look at inventory\", \"look at red apple\""},
			{"inspect", "inventory item place enemy", "Allows you to inspect things. Usage: \"inspect inventory\", \"inspect red apple\""},
			{"take", "item", "Allows you to take things. Usage: \"take red apple\", \"take sword\""},
			{"grab", "item", "Allows you to grab things. Usage: \"grab red apple\", \"grab sword\""},
			{"pickup", "item", "Allows you to pick up things. Usage: \"pick up red apple\", \"pick up basic sword\""},
			{"check", "default", "Allows you to check things. Usage: \"check equipped\""},
			{"equip", "item", "Allows you to equip a weapon or armor. Usage: \"equip longsword\",\"equip titanium armor\""},
			{"mute", "default", "Mutes the game's sound. Usage: \"mute game\""},
			{"unmute", "default", "Unmutes the game's sound. Usage: \"unmute game\""},
			{"save", "default", "Saves the current state of your playthrough. Usage: \"save\""},
			{"quit", "default", "Quits playing the game. Usage: \"quit game\", \"quit playing\""}, {"stop", "default", "Stops playing the game. Usage: \"stop game\", \"stop playing\""},
			{"battle", "battle", "Starts a battle with an enemy. Usage: \"battle the man\", \"battle some dude\""},
			{"fight", "battle", "Start to fight an enemy. Usage: \"fight the man\", \"fight some dude\""},
			{"attack", "battle", "Start to attack an enemy. Usage: \"attack the man\", \"attack some dude\""},
			{"challenge", "battle", "Challenges an enemy to a battle. \"challenge the man\", \"challenge some dude\""}
	};

	private static final String validBattleCommands[][] = {
			{"run", "run", ""},
			{"flee", "run", ""},
			{"escape", "run", ""},

			{"attack", "attack", ""},
			{"hit", "attack", ""},
			{"swing", "attack", ""},
			{"slash", "attack", ""},
			{"stab", "attack", ""},
			{"strike", "attack", ""},
			{"bludgeon", "attack", ""},

			{"use", "item", ""},
			{"consume", "item", ""},

			{"help", "help", "IGNORE"},
	};

	/**
	 * Constructor - initialize the command words.
	 */
	public CommandWords() {
		// empty for now
	}

	/**
	 * Return all command names as an array
	 **/
	public String[] getValidCommands() {
		String returnCommands[] = new String[validCommands.length];
		for (int i = 0; i < validCommands.length; i++) {
			returnCommands[i] = validCommands[i][0];
		}
		return returnCommands;
	}

	/**
	 * Return all command names as an array
	 **/
	public String[] getValidBattleCommands() {
		String returnCommands[] = new String[validBattleCommands.length];
		for (int i = 0; i < validBattleCommands.length; i++) {
			returnCommands[i] = validBattleCommands[i][0];
		}
		return returnCommands;
	}

	/**
	 * Return the type of a given command (using the name of it)
	 **/
	public String getCommandType(String commandName) {
		for (int i = 0; i < validCommands.length; i++) {
			if (validCommands[i][0] == commandName)
				return validCommands[i][1];
		}
		return null;
	}

	/**
	 * Return the type of a given command (using the name of it)
	 **/
	public String getBattleCommandType(String battleCommandName) {
		for (int i = 0; i < validBattleCommands.length; i++) {
			if (validBattleCommands[i][0] == battleCommandName)
				return validBattleCommands[i][1];
		}
		return null;
	}

	/**
	 * Check whether a given String is a valid command word.
	 **/
	public boolean isCommand(String command) {
		for (int i = 0; i < validCommands.length; i++)
			if (validCommands[i][0].equalsIgnoreCase(command)) return true;
		// if we get here, the string was not found in the commands
		return false;
	}

	/*
	 * Returns a string of all available commands and their description
	 */
	public String toString() {
		String returnString = "";
		for(int i = 0; i < validCommands.length; i++)
			if (validCommands[i][2] != "IGNORE") returnString = returnString + validCommands[i][0] + " - " + validCommands[i][2] + "\n";
		return returnString;
	}

	/*
	 * Returns a string of all available commands and their description
	 */
	public String listBattleCommands() {
		String returnString = "";
		for(int i = 0; i < validBattleCommands.length; i++)
			if (validBattleCommands[i][2] != "IGNORE") returnString = returnString + validBattleCommands[i][0] + " - " + validBattleCommands[i][2] + "\n";
		return returnString;
	}
}