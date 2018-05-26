package com.bayviewglen.zork;

/** "CommandWords" Class - stores all commands of the game.
 * 
 *  Original Code Author: 	Michael Kolling
 *  Original Code Version:	1.0
 *  Original Published Date: July 1999
 * 
 *  Current Authors: 		Kirill Tregubov, Zacharia Burrafato, Andrew Douglas, Alim Halani
 *  Current Code Version:	0.2-alpha
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
			{"go", "place", "Allows you to go places. Usage: \"go west\", \"go down\""}, {"walk", "place", "Allows you to walk places. Usage: \"walk west\", \"walkT down\""},
			{"teleport", "place", "\"Cheater\" command. Remove before final release!"}, {"tp", "place", "\"Cheater\" command. Remove before final release!"},
			//{"give", "item", "\"Cheater\" command. Remove before final release!"},
			{"eat", "item", "Allows you to eat stuff. Usage: \"eat apple\", \"eat sword\""}, {"consume", "item", "Allows you to consume stuff. Usage: \"consume apple\", \"consume sword\""},
			{"look at", "inventory item place", "Allows you to look at things. Usage: \"look at inventory\", \"look at red apple\""}, {"inspect", "inventory item place", "Allows you to inspect things. Usage: \"inspect inventory\", \"inspect red apple\""},
			{"take", "item", "Allows you to take things. Usage: \"take red apple\", \"take sword\""}, {"grab", "item", "Allows you to grab things. Usage: \"grab red apple\", \"grab sword\""},
			{"pick up", "item", "Allows you to pick up things. Usage: \"pick up red apple\", \"pick up basic sword\""},
			{"mute", "default", "Mutes the game's sound. Usage: \"mute game\""},
			{"unmute", "default", "Unmutes the game's sound. Usage: \"unmute game\""},
			{"save", "default", "Saves the current state of your playthrough. Usage: \"save\""},
			{"quit", "default", "Quits playing the game. Usage: \"quit game\", \"quit playing\""}, {"stop", "default", "Stops playing the game. Usage: \"stop game\", \"stop playing\""},
			{"battle", "battle", "Starts a battle with an enemy. Usage: \"battle the man\", \"battle some dude\""},
			{"fight", "battle", "Start to fight an enemy. Usage: \"fight the man\", \"fight some dude\""},
			{"challenge", "battle", "Challenges an enemy to a battle. \"challenge the man\", \"challenge some dude\""}
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
	 * Check whether a given String is a valid command word.
	 **/
	public boolean isCommand(String command) {
		for (int i = 0; i < validCommands.length; i++)
			if (validCommands[i][0].equalsIgnoreCase(command)) return true;
		// if we get here, the string was not found in the commands
		return false;
	}

	/*
	 * Returns true if command needs three variables.
	 */
	@Deprecated
	public boolean needsThreeVariables(String command) {
		for (int i = 0; i < validCommands.length; i++)
			if (validCommands[i][0].equalsIgnoreCase(command) && validCommands[i][1].equals("3")) return true;
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

}