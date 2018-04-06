package com.bayviewglen.zork;
/** "Command Words" Class - part of the "Zork" game.
 * 
 * Original Author:  Michael Kolling
 * Original Version: 1.0
 * Original Date:    July 1999
 * 
 * Current Authors: Kirill Tregubov, Zacharia Burrafato, Andrew Douglas, Alim Halani
 * Current Version: 0.1-alpha
 * Current Date:    March 2018
 * 
 * This class holds an enumeration of all command words known to the game.
 * It is used to recognise commands as they are typed in.
 *
 * This class is part of the "Zork" game.
 */

class CommandWords {
	// a constant array that holds all valid command words
	private static final String validCommands[][] = {
			{"help", "", "Prints the help message. Usage: \"help\""},
			{"list", "", "Lists things. Usage: \"list commands\""}, // add an example if list gains more uses
			{"go", "", "Allows you to go places. Usage: \"go west\", \"go down\""}, {"walk", "", "Allows you to walk places. Usage: \"walk west\", \"walkT down\""},
			{"give", "", "\"Cheater\" command. Remove before final release!"},
			{"eat", "", "Allows you to eat stuff. Usage: \"eat apple\", \"eat sword\""}, {"consume", "", "Allows you to consume stuff. Usage: \"consume apple\", \"consume sword\""},
			{"look", "3", "Allows you to look at things. Usage: \"look at inventory\", \"look at red apple\""}, {"inspect", "", "Allows you to inspect things. Usage: \"inspect inventory\", \"inspect red apple\""},
			{"take", "", "Allows you to take things. Usage: \"take red apple\", \"take sword\""}, {"pick", "3", "Allows you to pick up things. Usage: \"pick up red apple\", \"pick up basic sword\""},
			{"save", "", "Saves the current state of your playthrough. Usage: \"save\""},
			{"quit", "", "Quits playing the game. Usage: \"quit game\", \"quit playing\""}, {"stop", "", "Stops playing the game. Usage: \"stop game\", \"stop playing\""}
	};

	/**
	 * Constructor - initialize the command words.
	 */
	public CommandWords() {
		// empty for now
	}

	/**
	 * Check whether a given String is a valid command word. 
	 * Return true if it is, false if it isn't.
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
	public boolean needsThreeVariables(String command) {
		for (int i = 0; i < validCommands.length; i++)
			if (validCommands[i][0].equalsIgnoreCase(command) && validCommands[i][1].equals("3")) return true;
		return false;
	}


	/*
	 * Print all valid commands to System.out.
	 */
	public String showAll() { // assumes every command has two keywords -> alternative should be found
		String returnString = "";
		for(int i = 0; i < validCommands.length; i++)
			if (validCommands[i][2] != "IGNORE") returnString = returnString + validCommands[i][0] + " - " + validCommands[i][2] + "\n";
		return returnString;
	}
}