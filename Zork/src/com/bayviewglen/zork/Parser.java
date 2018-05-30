package com.bayviewglen.zork;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

/** "Parser" Class - part of the "Zork" game.
 * 
 *  Original Code Author: 	Michael Kolling
 *  Original Code Version:	1.0
 *  Original Published Date: July 1999
 * 
 *  Current Authors: 		Kirill Tregubov, Zacharia Burrafato, Andrew Douglas, Alim Halani
 *  Current Code Version:	0.3-alpha
 *  Current Published Date:	May 2018
 * 
 *  This class is part of Zork. Zork is a simple, text based adventure game.
 *
 *  This parser reads user input and tries to interpret it as a "Zork"
 *  command. Every time it is called it reads a line from the terminal and
 *  tries to interpret the line as a two word command. It returns the command
 *  as an object of class Command.
 *
 *  The parser has a set of known command words. It checks user input against
 *  the known commands, and if the input is not one of the known commands, it
 *  returns a command object that is marked as an unknown command.
 */

class Parser {

	private CommandWords commands;  // holds all valid command words

	public Parser() {
		commands = new CommandWords();
	}

	public Command getCommand(Player player) {
		try {
			// Initialize variables
			BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
			String inputLine = null; // will hold the full input line
			String command = null; // holds the command
			String actualCommand = null; // holds the command
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

			//Dirty fix
			if (Utils.containsCompareBoth("pick up", inputLine)) inputLine = inputLine.substring(0, inputLine.indexOf("pick")) + "pickup" + inputLine.substring(inputLine.indexOf("pick up")+7);
			//System.out.println(inputLine);
			String inputArr[] = inputLine.split(" ");

			for (String inputString : inputArr) {
				for (String validCommand : commands.getValidCommands()) {
					if (inputString.equalsIgnoreCase(validCommand)) {
						command = validCommand;
						commandType = commands.getCommandType(command);
						break;
					}
					for (String alternativeCommand : commands.getCommandAlternatives(validCommand)) {
						if (inputString.equalsIgnoreCase(alternativeCommand)) {
							actualCommand = alternativeCommand;
							command = validCommand;
							commandType = commands.getCommandType(command);
							break;
						}
					}

				}
			}
			//System.out.println(inputLine + " : " + command + " " + actualCommand + " " + commandType);

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
				if (actualCommand == null) inputLine = Utils.removeBeforeSubstring(inputLine, command);
				else inputLine = Utils.removeBeforeSubstring(inputLine, actualCommand);
			}

			inputArr = inputLine.split(" ");
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
									String item = findItemRemovePlural(player, inputArr);
									if (item != null) return new Command(command, typeArray[i], item.toLowerCase(), numbers);
								} catch (Exception e) {}
							} else {
								try {
									String item = findItem(player, inputArr);
									if (item != null) return new Command(command, typeArray[i], item.toLowerCase());
								} catch (Exception e) {}
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
						} else if (typeArray[i].equals("enemy")) {
							String enemy = findEnemy(player, inputArr);
							if (enemy != null) return new Command(command, "enemy", enemy);
						}
					}
				} // Single type command
				else {
					if (commandType.equals("trial")) {
						try {
							if (numbers[0] != null) return new Command(command, commandType, inputLine.toLowerCase(), numbers);
						} catch (Exception e) {
						}
					}
					// Item
					else if (commandType.equals("item")) { // multiple item fix
						if (numbers != null) {
							String item = findItemRemovePlural(player, inputArr);
							if (item != null) return new Command(command, commandType, item.toLowerCase(), numbers);
						} else {
							String item = findItem(player, inputArr);
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
					} // Battle
					else if (commandType.equals("battle")) {
						String enemy = findEnemy(player, inputArr);
						if (enemy != null) return new Command(command, commandType, enemy);
						else return new Command(command, commandType, null);
					}
				}
			}
			if (command.equals("look")) return new Command(command, "place", "");
			else if (command != null) return new Command(command, commandType, inputLine.toLowerCase());
		} catch (Exception e) {
		}
		return new Command(null, null);
	}

	public Command getBattleCommand(Player player) {
		try {
			// Initialize variables
			BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
			String inputLine = null; // will hold the full input line
			String command = null; // holds the command
			String commandType = null; // holds command type
			boolean check = false;

			// Take input
			System.out.print("> "); // print prompt
			try {
				inputLine = reader.readLine();
			} catch(java.io.IOException e) {
				System.out.println ("There was an error reading input: " + e.getMessage());
			}

			for (int i = 0; i < commands.getValidBattleCommands().length; i++) {
				if (Utils.containsIgnoreCase(inputLine, commands.getValidBattleCommands()[i])) {
					command = commands.getValidBattleCommands()[i];
					commandType = commands.getBattleCommandType(command);
				}
			}

			// Update inputLine
			inputLine = inputLine.replaceAll("\\d","").replaceAll(" +", " ");
			inputLine = Utils.removeBeforeSubstring(inputLine, command);
			String inputArr[] = inputLine.split(" ");

			//System.out.println(command + " and " + commandType + " and " + inputArr[0]);

			if (commandType != null) {
				if (commandType.equals("attack") || commandType.equals("run") || commandType.equals("help")) return new Command(command, commandType);
				else if (commandType.equals("item")) {
					String item = findItem(player, inputArr);
					//System.out.println(item);
					if (item != null) return new Command(command, commandType, item.toLowerCase());
				} else return new Command(null,null);	
			}

			if (command != null) return new Command(command, commandType, inputLine.toLowerCase());
			else return new Command(null, null);
		} catch (Exception e) {
			return new Command(null, null);
		}
	}

	public static boolean getYesNoAnswer() {
		System.out.print("\n> ");
		Scanner nameInput = new Scanner(System.in);
		String inputString = nameInput.nextLine().toLowerCase();
		if (Utils.containsIgnoreCase(inputString, "y") || Utils.containsIgnoreCase(inputString,"sure") || Utils.containsIgnoreCase(inputString,"fine") || Utils.containsIgnoreCase(inputString,"why not") || Utils.containsIgnoreCase(inputString,"accept") || Utils.containsIgnoreCase(inputString,"true") || Utils.containsIgnoreCase(inputString,"ok")) {
			return true;
		}
		return false;
	}

	public Command getSecondaryCommand(Player player) {
		BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
		String inputLine = null; // will hold the full input line
		Integer numbers[] = null; // temporary workaround before parser is fixed
		//boolean check = false; LEGACY

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

	public String findItem (Player player, String inputArr[]) {
		for (String inputString : inputArr) {
			if (player.getRoomHasItem(inputString))
				return inputString;
			if (player.inventory.hasItem(inputString))
				return inputString;
		}
		return null;
	}

	public String findItemRemovePlural(Player player, String inputArr[]) {
		for (String inputString : inputArr) {
			if (player.getRoomHasItem(inputString.substring(0, inputString.length()-1)))
				return inputString.substring(0, inputString.length()-1);
			else if (player.inventory.hasItem(inputString.substring(0, inputString.length()-1)))
				return inputString.substring(0, inputString.length()-1);
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

	public String findEnemy(Player player, String inputArr[]) {
		if (player.getRoom().hasEnemies() || player.getRoom().hasBosses()) {
			for (String inputWord : inputArr) {
				for (Entity enemy : player.getRoom().getRoomEnemies()) {
					if (Utils.containsIgnoreCase(enemy.toString(), inputWord)) return inputWord;
				}
				for (Entity boss: player.getRoom().getRoomBosses()) {
					if (Utils.containsIgnoreCase(boss.toString(), inputWord)) return inputWord;
				}
			}
		}
		return null;
	}

	/*
	 * Print out a list of valid command words.
	 */
	public String listCommands() {
		return commands.toString();
	}

	/*
	 * Print out a list of valid command words.
	 */
	public String listBattleCommands() {
		return commands.listBattleCommands();
	}
}