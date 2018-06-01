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
	// A constant array that holds all valid commands, their types, and their descriptions
	private static final String validCommands[][] = {
			{"help", "assist aid", "default", "Prints the help message. Usage: \"help\""},
			{"list", "", "default", "Lists things, such as available commands. Usage: \"list commands\""}, // add an example if list gains more uses

			{"go", "walk move advance", "place", "Allows you to go places. Usage: \"go west\", \"go down\""},
			{"look", "glance inspect examine explore", "inventory item place enemy", "Allows you to look at things. Usage: \"look at inventory\", \"look at red apple\""},
			{"check", "", "default", "Allows you to check things. Usage: \"check equipped\", \"check stats\""},
			{"take", "grab acquire obtain pickup", "item", "Allows you to take things. Usage: \"take red apple\", \"take sword\""},
			{"equip", "", "item", "Allows you to equip a weapon or armor. Usage: \"equip longsword\",\"equip titanium armor\""},
			{"consume", "use ingest", "item", "Allows you to consume stuff. Usage: \"consume apple\", \"consume sword\""},

			{"battle", "fight attack strike challenge", "battle", "Starts a battle with an enemy. Usage: \"battle the man\", \"battle some dude\""},
			{"talk", "converse discuss communicate speak chat", "enemy", "Talk to an NPC. Usage: \"talk to George\""},
			{"start", "begin commence", "trial", "Starts a trial. Usage: \"start trial one\", \"start trial 2\""},
			{"abandon", "stop surrender leave", "default", "Abandon a trial. Usage: \"abandon trial one\", \"abandon trial 2\""},

			{"heal", "treat", "default", "Ask to heal yourself (if you are in the Healing Center). Usage: \"heal me\""},
			{"mute", "silence deafen", "default", "Mutes the game's sound. Usage: \"mute game\""},
			{"unmute", "resume", "default", "Unmutes the game's sound. Usage: \"unmute game\""},
			{"save", "record store", "default", "Saves the current state of your playthrough. Usage: \"save\""},
			{"quit", "stop terminate", "default", "Quits playing the game. Usage: \"quit game\", \"quit playing\""}
	};

	private static final String validBattleCommands[][] = {
			{"help", "assist aid", "help", "IGNORE"},
			{"run", "flee escape", "run", "Allows you to escape the battle you are in. Usage: \"run\""},
			{"attack", "hit swing slash stab strike bludgeon", "attack", "Allows you to strike the enemy you are fighting. Usage: \"hit\""},
			{"consume", "use ingest", "item", "Allows you to consume a consumable item. Usage \"consume small heal potion\""},
			{"quit", "stop terminate", "quit", "Quits playing the game. Usage: \"quit game\", \"quit playing\""}
	};

	private static final String validShopCommands[][] = {
			{"help", "assist aid", "help", "IGNORE"},
			{"buy", "purchase", "buy", "Please use the full item names when buying! Usage: \"buy medium heal potion\""},
			{"check", "look inspect price cost", "check price", "Please use the full item names when checking their price! Usage: \"check medium heal potion\", \"check my money\""},
			{"go", "walk move advance", "place", "Allows you to go places. Usage: \"go west\", \"go down\""},
			{"quit", "stop terminate", "quit", "Quits playing the game. Usage: \"quit game\", \"quit playing\""}
	};

	private static final String validRiddleCommands[][] = {
			{"help", "assist aid", "help", "IGNORE"},
			{"answer", "reply ", "answer", "Allows you to submit an answer to a question. Usage:\"answer (your answer)\""},
			{"abandon", "stop surrender leave", "default", ""},
			{"quit", "stop terminate", "quit", "Quits playing the game. Usage: \"quit game\", \"quit playing\""}
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
	 * Return all battle command names as an array
	 **/
	public String[] getValidBattleCommands() {
		String returnCommands[] = new String[validBattleCommands.length];
		for (int i = 0; i < validBattleCommands.length; i++) {
			returnCommands[i] = validBattleCommands[i][0];
		}
		return returnCommands;
	}

	/**
	 * Return all shop command names as an array
	 **/
	public String[] getValidShopCommands() {
		String returnCommands[] = new String[validShopCommands.length];
		for (int i = 0; i < validShopCommands.length; i++) {
			returnCommands[i] = validShopCommands[i][0];
		}
		return returnCommands;
	}

	/**
	 * Return all riddle command names as an array
	 **/
	public String[] getValidRiddleCommands() {
		String returnCommands[] = new String[validRiddleCommands.length];
		for (int i = 0; i < validRiddleCommands.length; i++) {
			returnCommands[i] = validRiddleCommands[i][0];
		}
		return returnCommands;
	}

	/**
	 * Return the type of a given command (using the name of it)
	 **/
	public String[] getCommandAlternatives(String commandName) {
		for (int i = 0; i < validCommands.length; i++) {
			if (validCommands[i][0] == commandName)
				return validCommands[i][1].split(" ");
		}
		return null;
	}

	/**
	 * Return the type of a given battle command (using the name of it)
	 **/
	public String[] getBattleCommandAlternatives(String battleCommandName) { // change this
		for (int i = 0; i < validBattleCommands.length; i++) {
			if (validBattleCommands[i][0] == battleCommandName)
				return validBattleCommands[i][1].split(" ");
		}
		return null;
	}

	/**
	 * Return the type of a given shop command (using the name of it)
	 **/
	public String[] getShopCommandAlternatives(String shopCommandName) { // change this
		for (int i = 0; i < validShopCommands.length; i++) {
			if (validShopCommands[i][0] == shopCommandName)
				return validShopCommands[i][1].split(" ");
		}
		return null;
	}

	/**
	 * Return the type of a given riddle command (using the name of it)
	 **/
	public String[] getRiddleCommandAlternatives(String riddleCommandName) { // change this
		for (int i = 0; i < validRiddleCommands.length; i++) {
			if (validRiddleCommands[i][0] == riddleCommandName)
				return validRiddleCommands[i][1].split(" ");
		}
		return null;
	}

	/**
	 * Return the type of a given command (using the name of it)
	 **/
	public String getCommandType(String commandName) {
		for (int i = 0; i < validCommands.length; i++) {
			if (validCommands[i][0] == commandName)
				return validCommands[i][2];
		}
		return null;
	}

	/**
	 * Return the type of a given battle command (using the name of it)
	 **/
	public String getBattleCommandType(String battleCommandName) {
		for (int i = 0; i < validBattleCommands.length; i++) {
			if (validBattleCommands[i][0] == battleCommandName)
				return validBattleCommands[i][2];
		}
		return null;
	}

	/**
	 * Return the type of a given shop command (using the name of it)
	 **/
	public String getShopCommandType(String shopCommandName) {
		for (int i = 0; i < validShopCommands.length; i++) {
			if (validShopCommands[i][0] == shopCommandName)
				return validShopCommands[i][2];
		}
		return null;
	}

	/**
	 * Return the type of a given riddle command (using the name of it)
	 **/
	public String getRiddleCommandType(String riddleCommandName) {
		for (int i = 0; i < validRiddleCommands.length; i++) {
			if (validRiddleCommands[i][0] == riddleCommandName)
				return validRiddleCommands[i][2];
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
			if (validCommands[i][2] != "IGNORE") returnString = returnString + validCommands[i][0] + " - " + validCommands[i][3] + "\n";
		return returnString;
	}

	/*
	 * Returns a string of all available battle commands and their description
	 */
	public String listBattleCommands() {
		String returnString = "";
		for(int i = 0; i < validBattleCommands.length; i++)
			if (validBattleCommands[i][3] != "IGNORE") returnString = returnString + validBattleCommands[i][0] + " - " + validBattleCommands[i][3] + "\n";
		return returnString;
	}

	/*
	 * Returns a string of all available battle commands and their description
	 */
	public String listShopCommands() {
		String returnString = "";
		for(int i = 0; i < validShopCommands.length; i++)
			if (validShopCommands[i][3] != "IGNORE") returnString = returnString + validShopCommands[i][0] + " - " + validShopCommands[i][3] + "\n";
		return returnString;
	}

	/*
	 * Returns a string of all available battle commands and their description
	 */
	public String listRiddleCommands() {
		String returnString = "";
		for(int i = 0; i < validRiddleCommands.length; i++)
			if (validRiddleCommands[i][3] != "IGNORE") returnString = returnString + validRiddleCommands[i][0] + " - " + validRiddleCommands[i][3] + "\n";
		return returnString;
	}
}