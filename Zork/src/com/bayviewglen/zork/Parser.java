package com.bayviewglen.zork;
/** "Parser" Class - part of the "Zork" game.
 * 
 * Original Author:  Michael Kolling
 * Original Version: 1.0
 * Original Date:    July 1999
 * 
 * Current Authors: Kirill Tregubov, Zacharia Burrafato, Andrew Douglas, Alim Halani
 * Current Version: 0.1-alpha
 * Current Date:    March 2018
 * 
 * This class is part of Zork. Zork is a simple, text based adventure game.
 *
 * This parser reads user input and tries to interpret it as a "Zork"
 * command. Every time it is called it reads a line from the terminal and
 * tries to interpret the line as a two word command. It returns the command
 * as an object of class Command.
 *
 * The parser has a set of known command words. It checks user input against
 * the known commands, and if the input is not one of the known commands, it
 * returns a command object that is marked as an unknown command.
 */

import java.io.BufferedReader;
import java.io.InputStreamReader;

class Parser {

	private CommandWords commands;  // holds all valid command words

	public Parser() {
		commands = new CommandWords();
	}

	public Command getCommand() {
		String inputLine = null; // will hold the full input line
		String word1 = null;
		String word2 = null;
		String word3 = null;

		System.out.print("> "); // print prompt

		// Take input
		BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
		try {
			inputLine = reader.readLine();
		} catch(java.io.IOException exc) {
			System.out.println ("There was an error reading input: " + exc.getMessage());
		}
		// Assign input to 3 different variables
		String[] input;
		String lastWord = null;
		input = inputLine.split(" ", 2);
		word1 = input[0];
		if (input.length > 1) {
			word2 = input[1];
			if (commands.needsThreeVariables(word1)) {
				input = inputLine.split(" ", 3);
				for (int i=0;i<input.length; i++) {
					if (input.length > 1) {
						word2 = input[1];
						if (input.length > 2) { 
							word3 = input[2];
							lastWord = word3;
						} else word3 = null;
					}
				}
			} else lastWord = word2;
		} else {
			word2 = null;
			word3 = null;
			lastWord = null;
		}
		
		if (Game.containsIgnoreCase(lastWord, "the "))
			lastWord = lastWord.substring(Game.containsFindIndex(lastWord, "the")+4);
		else if (Game.containsIgnoreCase(lastWord, "an "))
			lastWord = lastWord.substring(Game.containsFindIndex(lastWord, "an")+3);
		else if (Game.containsIgnoreCase(lastWord, "a "))
			lastWord = lastWord.substring(Game.containsFindIndex(lastWord, "a")+2);
		
		if (commands.needsThreeVariables(word1)) {
			word3 = lastWord;
		} else word2 = lastWord;

		// Check if the word is a known command. If not, create a "null" command.
		if(commands.isCommand(word1))
			return new Command(word1, word2, word3);
		else
			return new Command(null, null, null);

	}

	public Command getSecondaryCommand() {
		String inputLine = ""; // will hold the full input line
		String word1;
		String word2;
		String word3;

		System.out.print("> ");  // print prompt

		// Take input
		BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
		try {
			inputLine = reader.readLine();
		} catch (java.io.IOException exc) {
			System.out.println ("There was an error reading input: " + exc.getMessage());
		}
		// Assign input to 3 different variables
		String[] input;
		input = inputLine.split(" ", 3);
		word1 = input[0];
		if (input.length > 1)
			word2 = input[1];
		else
			word2 = null;
		if (input.length > 2)
			word3 = input[2];
		else
			word3 = null;
		// Return command
		return new Command(word1, word2, word3);
	}

	/*
	 * Print out a list of valid command words.
	 */
	public String showCommands() {
		return commands.showAll();
	}
}