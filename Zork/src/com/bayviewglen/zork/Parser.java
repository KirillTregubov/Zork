package com.bayviewglen.zork;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.List;

/** "Parser" Class - part of the "Zork" game.
 * 
 * Original Author:  Michael Kolling
 * Original Version: 1.0
 * Original Date:    July 1999
 * 
 * Current Authors: Kirill Tregubov, Zacharia Burrafato, Andrew Douglas, Alim Halani
 * Current Version: 0.2-alpha
 * Current Date:    April 2018
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

class Parser {

	private CommandWords commands;  // holds all valid command words

	public Parser() {
		commands = new CommandWords();
	}

	public Command getCommand(Player player) {
		// Initialize variables
		BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
		String inputLine = null; // will hold the full input line
		String command = null; // holds the command
		String commandType = null; // holds command type
		Integer numbers[] = null; // temporary workaround before parser is fixed
		boolean check = false;

		// Take input
		System.out.print("> "); // print prompt
		try {
			inputLine = reader.readLine();
		} catch(java.io.IOException e) {
			System.out.println ("There was an error reading input: " + e.getMessage());
		}

		for (int i = 0; i < commands.getValidCommands().length; i++) {
			if (Utils.containsIgnoreCase(inputLine, commands.getValidCommands()[i])) {
				command = commands.getValidCommands()[i];
				commandType = commands.getCommandType(command);
			}
		}

		try {
			if (command.equalsIgnoreCase("teleport") || command.equalsIgnoreCase("tp")) {
				check = true;
			}
		} catch (Exception e) { }
		if (!check) {
			// Format input / numbers
			inputLine = Utils.detectNumbers(inputLine);
			if (inputLine.matches(".*\\d+.*")) {
				List<String> numberInput = Arrays.asList(inputLine.replaceAll("[^-?0-9]+", " ").trim().split(" ")); //Arrays.asList()
				int x = 0;
				numbers = new Integer[numberInput.size()];
				for (String i : numberInput) {
					numbers[x] = Integer.parseInt(i);
					x++;
				}
			}
			// Update inputLine
			inputLine = inputLine.replaceAll("\\d","").replaceAll(" +", " ");
			inputLine = Utils.removeBeforeSubstring(inputLine, command);

		}

		String inputArr[] = inputLine.split(" ");
		if (command != null) {
			commandType = commands.getCommandType(command);

			// Check if multiple types
			if (commandType.contains(" ")) {
				String typeArray[] = commandType.split(" ");
				for (int i = 0; i < typeArray.length; i++) {
					// Item
					if (typeArray[i].equals("item")) {
						if (numbers != null) {
							try {
								String item = findItemRemovePlural(inputArr);
								if (item != null) return new Command(command, typeArray[i], item.toLowerCase(), numbers);
							} catch (Exception e) {}
						} else {
							String item = findItem(inputArr);
							if (item != null) return new Command(command, typeArray[i], item.toLowerCase());
						}
					} // Inventory
					else if (typeArray[i].equals("inventory")) {
						if (Utils.containsIgnoreCase(inputLine, "inventory")) return new Command(command, typeArray[i], "inventory");
					} // Place
					else if (typeArray[i].equals("place")) {
						String direction = findDirection(player, inputArr);
						String roomName = findRoom(player, inputArr);
						if (direction != null) return new Command(command, typeArray[i], direction.toLowerCase());
						else if (roomName != null) return new Command(command, typeArray[i], roomName.toLowerCase());
						else if (Utils.containsIgnoreCase(inputLine, "room")) return new Command(command, typeArray[i], "room");
						//else return new Command(command, typeArray[i], inputLine.toLowerCase());

					}
				}

			} // Single type command
			else {
				// Item
				if (commandType.equals("item")) { // multiple item fix
					if (numbers != null) {
						String item = findItemRemovePlural(inputArr);
						if (item != null) return new Command(command, commandType, item.toLowerCase(), numbers);
					} else {
						String item = findItem(inputArr);
						if (item != null) return new Command(command, commandType, item.toLowerCase());
					}
				} // Inventory
				else if (commandType.equals("inventory")) {
					if (Utils.containsIgnoreCase(inputLine, "inventory")) System.out.println("SUCC");

				} // Place
				else if (commandType.equals("place")) {
					if (command.equals("teleport") || command.equals("tp")) {
						String room = findRoomToTeleport(player, inputArr);
						if (room != null) return new Command(command, commandType, room.toLowerCase());
					} else {
						String direction = findDirection(player, inputArr);
						String roomName = findRoom(player, inputArr);
						if (direction != null) return new Command(command, commandType, direction.toLowerCase());
						else if (roomName != null) return new Command(command, commandType, roomName.toLowerCase());
						else return new Command(command, commandType, inputLine.toLowerCase());
					} 

				}
			}
		}
		if (command != null) return new Command(command, commandType, inputLine.toLowerCase());
		else return new Command(null, null);
	}

	public Command getSecondaryCommand(Player player) {
		BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
		String inputLine = null; // will hold the full input line
		Integer numbers[] = null; // temporary workaround before parser is fixed
		boolean check = false;

		// Take input
		System.out.print("> "); // print prompt
		try {
			inputLine = reader.readLine();
		} catch(java.io.IOException e) {
			System.out.println ("There was an error reading input: " + e.getMessage());
		}

		inputLine = Utils.detectNumbers(inputLine);
		if (inputLine.matches(".*\\d+.*")) {
			List<String> numberInput = Arrays.asList(inputLine.replaceAll("[^-?0-9]+", " ").trim().split(" ")); //Arrays.asList()
			int x = 0;
			numbers = new Integer[numberInput.size()];
			for (String i : numberInput) {
				numbers[x] = Integer.parseInt(i);
				x++;
			}
		}
		// Update inputLine
		inputLine = inputLine.replaceAll("\\d","").replaceAll(" +", " ");

		return new Command(true, inputLine, numbers);
	}

	public String findItem (String inputArr[]) {
		for (int i = 0; i < inputArr.length; i++) {
			for (int j = 0; j < Player.items.length; j++) {
				if (Utils.containsIgnoreCase(Player.items[j].name, inputArr[i])) {
					return inputArr[i]; // should we return item?
				}
			}
		}
		return null;
	}

	public String findItemRemovePlural(String inputArr[]) {
		for (int i = 0; i < inputArr.length; i++) {
			for (int j = 0; j < Player.items.length; j++) {
				if (Utils.containsIgnoreCase(Player.items[j].name, inputArr[i].substring(0, inputArr[i].length()-1))) {
					return inputArr[i].substring(0, inputArr[i].length()-1); // should we return item?
				}
			}
		}
		return null;
	}

	public String findDirection(Player player, String inputArr[]) {
		for (int i = 0; i < inputArr.length; i++) {
			Room nextRoom = player.getNextRoom(inputArr[i].toLowerCase()); // TO:DO refactor
			if (nextRoom != null) {
				return inputArr[i];
			}
		}
		return null;
	}

	public String findRoom(Player player, String inputArr[]) {
		for (int i = 0; i < inputArr.length; i++) {
			if (Utils.containsCompareBoth(inputArr[i].toLowerCase(), player.getRoomName())) return inputArr[i];
		}
		return null;
	}

	public String findRoomToTeleport(Player player, String inputArr[]) {
		for (int i = 0; i < inputArr.length; i++) 
			if (player.teleport(inputArr[i]) != null) return player.teleport(inputArr[i]);
		return null;
	}

	/*
	 * Print out a list of valid command words.
	 */
	public String listCommands() {
		return commands.listCommands();
	}
}