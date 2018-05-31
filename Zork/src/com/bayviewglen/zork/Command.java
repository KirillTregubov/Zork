package com.bayviewglen.zork;

/** "Command" Class - generates user's commands.
 * 
 *  Original Code Author: 	Michael Kolling
 *  Original Code Version:	1.0
 *  Original Published Date: July 1999
 * 
 *  Current Authors: 		Kirill Tregubov, Zacharia Burrafato, Andrew Douglas, Alim Halani
 *  Current Code Version:	0.3-alpha
 *  Current Published Date:	May 2018
 *
 *  This class holds information about a command that was issued by the user.
 *  A command currently consists of two strings: a command word and a second
 *  word (for example, if the command was "take map", then the two strings
 *  obviously are "take" and "map").
 * 
 *  The way this is used is: Commands are already checked for being valid
 *  command words. If the user entered an invalid command (a word that is not
 *  known) then the command word is <null>.
 *
 *  If the command had only one word, then the second word is <null>.
 *
 *  The second word is not checked at the moment. It can be anything. If this
 *  game is extended to deal with items, then the second part of the command
 *  should probably be changed to be an item rather than a String.
 */

class Command {
	private String command;
	private String commandType;
	private String actualCommand;
	private String contextWord;
	private Integer numbers[];

	/**
	 * Create a command object. First and second word must be supplied, but
	 * either one (or both) can be null. The command word should be null to
	 * indicate that this was a command that is not recognized by this game.
	 */
	public Command (String command, String commandType) {
		this.command = command;
		this.commandType = commandType;
	}

	public Command (boolean isSecondary, String inputLine, Integer numbers[]) {
		this.command = inputLine;
		if (numbers.length > 0) {
			this.numbers = numbers;
		}
	}

	public Command (String command, String commandType, String contextWord) {
		this.command = command;
		this.commandType = commandType;
		this.contextWord = contextWord;
	}

	public Command (String command, String commandType, String contextWord, Integer numbers[]) {
		this.command = command;
		this.commandType = commandType;
		this.contextWord = contextWord;
		this.numbers = numbers;
	}
	
	public Command (boolean isAlternate, String command, String commandType, String actualCommand) {
		this.command = command;
		this.commandType = commandType;
		this.actualCommand = actualCommand;
	}

	public String getCommand() {
		return command;
	}

	public String getCommandType() {
		return commandType;
	}

	public String getContextWord() {
		return contextWord;
	}
	
	public String getActualCommand() {
		return actualCommand;
	}
	
	public boolean hasActualCommand() {
		return actualCommand != null;
	}

	public Integer[] getNumbers() {
		return numbers;
	}

	public Integer getFirstNumber() { // is this needed?
		return numbers[0];
	}

	/**
	 * Return true if this command was not understood.
	 */
	public boolean isUnknown() {
		return (command == null);
	}
}
