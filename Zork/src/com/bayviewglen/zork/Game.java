package com.bayviewglen.zork;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.FileWriter; 
import java.io.FileReader;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.Collections; // temporary
import java.util.HashMap;

/** "Game" Class - the main class of the "Zork" game.
 * 
 * Original Author:  Michael Kolling
 * Original Version: 1.0
 * Original Date:    July 1999
 * 
 * Current Authors: Kirill Tregubov, Zacharia Burrafato, Andrew Douglas, Alim Halani
 * Current Version: 0.1-alpha
 * Current Date:    March 2018
 * 
 *  This class is the main class of the "Zork" application. Zork is a very
 *  simple, text based adventure game.  Users can walk around some scenery.
 *  That's all. It should really be extended to make it more interesting!
 * 
 *  To play this game, create an instance of this class and call the "play"
 *  routine.
 * 
 *  This main class creates and initialises all the others: it creates all
 *  rooms, creates the parser and starts the game.  It also evaluates the
 *  commands that the parser returns.
 */

//Search terms: Teleporter, Changeme.

class Game {
	private Parser parser;
	private BufferedWriter writer;
	private BufferedReader reader;
	private final String FileLocation = "data\\"; // Change to "data/save.dat" if using Mac
	private Room currentRoom;
	private String room = "0";
	private Inventory inventory = new Inventory();
	// This is a MASTER object that contains all of the rooms and is easily accessible.
	// The key will be the name of the room -> no spaces (Use all caps and underscore -> Great Room would have a key of GREAT_ROOM
	// In a hashmap keys are case sensitive.
	// masterRoomMap.get("GREAT_ROOM") will return the Room Object that is the Great Room (assuming you have one).
	private HashMap<String, Room> masterRoomMap;

	private void initRooms(String fileName) throws Exception {
		masterRoomMap = new HashMap<String, Room>();
		BufferedReader reader;
		try {
			HashMap<String, HashMap<String, String>> exits = new HashMap<String, HashMap<String, String>>();
			reader = new BufferedReader(new FileReader(fileName));
			String line;
			
			while((line = reader.readLine()) != null) {
				if (line.contains("Room ID: ")) {
					// Create room
					Room room = new Room();
					// Read the ID
					String[] rawID = line.split(":")[1].split(" ");
					String roomID = "";
					for (int i = 1; i < rawID.length; i++) {
						roomID += Integer.parseInt(rawID[i], 2);
						if (i < rawID.length-1) roomID += "-";
					}
					room.setRoomID(roomID);
					// Read the Name
					String roomName = reader.readLine();
					room.setRoomName(roomName.split(":")[1].trim()); // ATTENTION: Why is roomName not stored the way it's read?
					// Read the Description
					String roomDescription = reader.readLine();
					room.setDescription(roomDescription.split(":")[1].replaceAll("<br>", "\n").trim());
					// Read the Exits
					String roomExits = reader.readLine();

					// An array of strings in the format "E-RoomName"
					String[] rooms = roomExits.split(":")[1].split(",");
					HashMap<String, String> temp = new HashMap<String, String>(); 
					for (String s : rooms) temp.put(s.split("-")[0].trim(), s.split("-")[1]);
					// LEGACY exits.put(roomName.substring(10).trim().toUpperCase().replaceAll(" ",  "_"), temp);
					exits.put(roomID, temp);

					// Read items, assign to array, and store it
					String roomItems = reader.readLine();
					roomItems = roomItems.split(":")[1].trim();
					String[] itemsString;
					itemsString = roomItems.split(", ");
					int[] items = new int[itemsString.length];
					int index = 0;
					for (String i : itemsString) {
						items[index] = Integer.parseInt(i);
						index++;
					}
					room.setItems(items); // assign items to the room's variable
					// LEGACY masterRoomMap.put(roomName.toUpperCase().substring(10).trim().replaceAll(" ",  "_"), room);
					masterRoomMap.put(roomID, room);
				}
			}
			
			for (String key : masterRoomMap.keySet()) {
				Room roomTemp = masterRoomMap.get(key);
				HashMap<String, String> tempExits = exits.get(key);
				for (String s : tempExits.keySet()) {
					// s = direction
					// value is the room
					String roomName2 = tempExits.get(s);
					Room exitRoom = masterRoomMap.get(roomName2);
					roomTemp.setExit(s.charAt(0), exitRoom);
				}
			}
			reader.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
	}    

	/**
	 * Create the game and initialize its internal map
	 */
	public Game() {
		try {
			initRooms(FileLocation + "rooms.dat");
			if (gameIsSaved())
				load();
			currentRoom = masterRoomMap.get(room); // Teleporter	
		} catch (Exception e) {
			e.printStackTrace();
		}
		parser = new Parser();
	}

	/**
	 *  Main play routine (loops until quit)
	 */
	public void play() {            
		printWelcome();

		// Enter the main command loop: repeatedly reads / executes commands until the game is over
		boolean finished = false;
		while (!finished) {
			//LEGACY
			Command command = parser.legacyGetCommand(); // legacy implementation
			System.out.println("");
			finished = legacyProcessCommand(command); // change if making new command
			
			/* WORK IN PROGRESS
			String command = parser.getCommand();
			System.out.println("");
			finished = processCommand(command);
			*/
		}
		System.out.println("Thank you for playing. Goodbye!");
	}

	/**
	 * Prints welcome message
	 */
	private void printWelcome() {
		System.out.println("\n" + "Welcome to Zork!"
		+ "\n" + "Zork is a new, incredibly boring adventure game."
		+ "\n" + "Currenty Playing Version: 0.1-alpha"
		+ "\n" + "Type 'help' if you need help."
		+ "\n");
		
		if (gameIsSaved()) {
			System.out.print("Automatically loaded game state from " + timeGameWasSaved()
			+ "\nLast known location: " + currentRoom.getRoomName()
			+ "\nLast known items in inventory:");
			if (inventory.isEmpty()) System.out.println(" Nothing was found...");
			else System.out.println(inventory.getInventory());
			System.out.println();
		}
		
		System.out.println(currentRoom.longDescription());
	}
	
	/*
	 * Work in Progress
	 */
	private boolean processCommand(String command) {
		System.out.println(command);
		return false;
	}
	
	/**
	 * Processes a given command (if this command ends the game, true is returned)
	 */
	@Deprecated
	private boolean legacyProcessCommand(Command command) {
		if(command.isUnknown()) {
			System.out.println("You cannot do that...");
			return false;
		}
		// Assign parts of the command to variables, make sure useless words aren't included (should this be in parser?)
		String commandWord = command.getCommandWord();
		String secondWord = null;
		String thirdWord = null;
		if (command.hasSecondWord()) {
			secondWord = command.getSecondWord();
			if (command.hasThirdWord())
				thirdWord = command.getThirdWord();
		}

		// help
		if (commandWord.equalsIgnoreCase("help")) printHelp();
		// list
		else if (commandWord.equalsIgnoreCase("list") && containsIgnoreCase("commands", secondWord)) printCommands();
		// go
		else if (commandWord.equalsIgnoreCase("go") || commandWord.equalsIgnoreCase("walk")) goRoom(command, commandWord);
		// teleport
		else if (commandWord.equalsIgnoreCase("teleport")) {
			System.out.println(secondWord);
			Room nextRoom = masterRoomMap.get(secondWord);
			if (nextRoom != null) {
				currentRoom = nextRoom;
				System.out.println(currentRoom.travelDescription());
			}
		}
		// give
		else if (commandWord.equalsIgnoreCase("give")) {
			System.out.println("What would you like to be receive?");
			continueCommand("give");
		} // eat
		else if (commandWord.equalsIgnoreCase("eat") || commandWord.equalsIgnoreCase("consume")) { // add check if it's consumable - add joke
			if (command.hasSecondWord()) {
				if (Items.isItem(secondWord)) {
					if (inventory.containsItem(secondWord))System.out.println("Do you really think you should be eating " + Items.getItem(Items.getItemIndex(secondWord)) + " at a time like this?");
					else System.out.println(Items.getItem(Items.getItemIndex(secondWord)) + " is not in your inventory... Even if it was, do you really think you should be eating at a time like this?");
				} else System.out.println("That's not an item! Even if it was, do you really think you should be eating at a time like this?");
			} else System.out.println("Do you really think you should be eating at a time like this?");
			continueCommand("eat");
		} // look
		else if (commandWord.equalsIgnoreCase("look") || commandWord.equalsIgnoreCase("inspect")) {
			if(command.hasSecondWord()) {
				if (containsIgnoreCase(secondWord, "items") || containsIgnoreCase(thirdWord, "items")) System.out.println("All items:" + Items.listItems());
				else if (containsIgnoreCase(secondWord, "inventory") || containsIgnoreCase(thirdWord, "inventory")) {
					if (inventory.isEmpty()) System.out.println("Your inventory is empty!");
					else System.out.println("Your inventory contains: " + inventory.getInventory());
				} else if (command.hasThirdWord()) {
					if (inventory.isRepeated(thirdWord)) System.out.println("Please be more specific. There are multiple items with this name!");
					if (Items.isItem(thirdWord) && (inventory.containsItem(thirdWord) || currentRoom.containsItem(thirdWord))) System.out.println(Items.getItemDescription(Items.getItemIndex(thirdWord)));
					else System.out.println("That item is not in your inventory or in " + currentRoom.getRoomName() + ".");
				} else {
					if (commandWord.equalsIgnoreCase("look")) System.out.println("You cannot look at that...");	
					else System.out.println("You cannot inspect that...");
				}
			} else {
				System.out.print("What would you like to ");
				if (commandWord.equalsIgnoreCase("look")) System.out.println("look at?");
				else System.out.println(commandWord + "?");
			}	
		} // take
		else if (commandWord.equalsIgnoreCase("take") || (commandWord.equalsIgnoreCase("pick") && containsIgnoreCase(secondWord, "up"))) {
			String givenItem = "";
			if (command.hasThirdWord()) givenItem = thirdWord;
			else givenItem = secondWord;
			if (command.hasSecondWord()) {
				if (currentRoom.isRepeated(givenItem)) System.out.println("Please be more specific. There are multiple items with this name!");
				else {
					int pickedUp = 0;
					String item = "";
					for (int i = 0; i < currentRoom.getItemAmount(); i++) {
						if (containsIgnoreCase(currentRoom.getItemName(i), givenItem)) {
							if (inventory.containsItem(currentRoom.getItemIndex(i))) { // add fix for consumables
								System.out.println("This item is already in your inventory!");
								pickedUp = 2;
							} else {
								inventory.addToInventory(currentRoom.getItemIndex(i));
								pickedUp = 1;
								item = Items.getItem(currentRoom.getItemIndex(i));
							}
						}
					}
					if (pickedUp == 1) System.out.println(item + " was added to your inventory!");
					else if (pickedUp == 0) {
						System.out.print("You weren't able to ");
						if (commandWord.equalsIgnoreCase("take")) System.out.print("take");
						else System.out.print("pick up");
						System.out.println(" the item... It probably doesn't exist.");
					}
				}
			} else {
				System.out.print("You cannot");
				if (commandWord.equalsIgnoreCase("take")) System.out.println("take that.");
				else System.out.println("pick up that.");
			}
		} // save
		else if (commandWord.equalsIgnoreCase("save")) save();
		// quit
		else if (commandWord.equalsIgnoreCase("quit") || commandWord.equalsIgnoreCase("stop")) { // player wants to quit
			if (command.hasSecondWord()) {
				if (secondWord.equalsIgnoreCase("game") || secondWord.equalsIgnoreCase("playing")) return true;
				else System.out.println("You cannot quit that!");
			} else System.out.println("Quit what?");
		} // wrong command
		else System.out.println("You cannot do that...");
		return false;
	}

	/**
	 * Processes a given command, assuming a related command was previously entered
	 */
	private void continueCommand(String originalCommand) {
		Command command = parser.getSecondaryCommand();
		System.out.println("");
		if(command.isUnknown()) {
			System.out.println("You cannot do that...");
		}

		//String secondWord = null;
		//String thirdWord = null;
		String commandWord = command.getCommandWord();
		/*if (command.hasSecondWord()) {
			secondWord = command.getSecondWord();
			if (command.hasThirdWord()) {
				thirdWord = command.getThirdWord();
			}
		}*/

		if (originalCommand.equals("give")) {
			inventory.addToInventory(Integer.parseInt(commandWord));
			System.out.println("Item given!");
		} else if (originalCommand.equals("eat")) {
			System.out.println("Whatever you say... You still can't eat at a time like this.");
		}
	}

	// User Commands

	/**
	 * Print list of commands used
	 */
	private void printHelp() {
		System.out.println("You are lost. You are alone. You wander...");
		System.out.println("To find out what commands are available, type in \"list commands\"");
	}

	private void printCommands() {
		System.out.println("All available commands:");
		System.out.print(parser.showCommands());
	}

	/** 
	 * Go to specified room (in the specified direction)
	 */
	private void goRoom(Command command, String givenCommand) {
		if (!command.hasSecondWord()) {
			System.out.print("Please indicate a direction you would like to ");
			if (givenCommand.equalsIgnoreCase("walk")) System.out.println("walk in.");
			else System.out.println("go in.");
			return;
		}
		String direction = command.getSecondWord();
		// Try to leave current room.
		Room nextRoom = currentRoom.nextRoom(direction);
		if (nextRoom != null) {
			currentRoom = nextRoom;
			System.out.println(currentRoom.travelDescription());
		} else System.out.println("That's not an option... You might be trapped.");
	}

	/*
	 * Save Method
	 */
	public void save() {
		try {
			writer = new BufferedWriter(new FileWriter(FileLocation + "save.dat", true));
			writer.write("Room: " + currentRoom.getRoomID() + "; ");	// save room currently in
			if (!inventory.isEmpty()) { // check to make sure inventory isn't empty
				writer.write("Inventory: " + inventory.saveInventory() + "; ");	// save inventory	
			}
			writer.write("Time Saved: " + LocalDateTime.now() + ".");
			writer.newLine();
			writer.close();
			System.out.println("Your game has been successfully saved!");
		} catch(IOException e) {
			System.out.println("Error Saving: " + e);
		}
	}

	/*
	 * Boolean that checks if game has been saved
	 */
	public boolean gameIsSaved() {
		try {
			reader = new BufferedReader(new FileReader(FileLocation + "save.dat"));
			return reader.readLine() != null;
		} catch(IOException e) {
			return false;
		}
	}

	/*
	 * Returns time the last save occurred
	 */
	public String timeGameWasSaved() {
		try {
			if (gameIsSaved()) {
				String saveFile = null, line;
				reader = new BufferedReader(new FileReader(FileLocation + "save.dat"));
				while ((line = reader.readLine()) != null) {
					saveFile = line;
				}
				if (saveFile.substring(ordinalIndexOf(saveFile, ":", 2)-1,ordinalIndexOf(saveFile, ":", 2)).equals("y")) // check if inventory was saved
					return saveFile.substring(ordinalIndexOf(saveFile, ":", 3)+13,ordinalIndexOf(saveFile, ":", 3)+21) + " on " + saveFile.substring(ordinalIndexOf(saveFile, ":", 3)+10,ordinalIndexOf(saveFile, ":", 3)+12) + "/" + saveFile.substring(ordinalIndexOf(saveFile, ":", 3)+7,ordinalIndexOf(saveFile, ":", 3)+9) + "/" + saveFile.substring(ordinalIndexOf(saveFile, ":", 3)+2,ordinalIndexOf(saveFile, ":", 3)+6);
				else
					return saveFile.substring(ordinalIndexOf(saveFile, ":", 2)+13,ordinalIndexOf(saveFile, ":", 2)+21) + " on " + saveFile.substring(ordinalIndexOf(saveFile, ":", 2)+10,ordinalIndexOf(saveFile, ":", 2)+12) + "/" + saveFile.substring(ordinalIndexOf(saveFile, ":", 2)+7,ordinalIndexOf(saveFile, ":", 2)+9) + "/" + saveFile.substring(ordinalIndexOf(saveFile, ":", 2)+2,ordinalIndexOf(saveFile, ":", 2)+6);
			}
		} catch(IOException e) { }
		return null;
	}

	/*
	 * Load Method - loads save file (if it exists)
	 */
	public void load() {
		String saveFile = null, line;
		try {
			if (gameIsSaved()) {
				reader = new BufferedReader(new FileReader(FileLocation + "save.dat"));
				while ((line = reader.readLine()) != null) {	// while loop to determine last line in save file
					saveFile = line; // assigns the latest save to variable saveFile
				}
				reader.close();
				
				// Find and assign currentRoom to the room in the save file
				room = saveFile.substring(ordinalIndexOf(saveFile, " ", 1), ordinalIndexOf(saveFile, ";", 1)).trim();

				// Find and load inventory
				if (ordinalIndexOf(saveFile, ":", 2) != -1) { // check if inventory was saved
					int x = 0, index = 0;
					for (int i = 1; x != -1; i++) {
						index = i;
						x = ordinalIndexOf(saveFile, ",", index);
						if (x == -1)
							index--;
					}
					int[] savedInventory = new int[index];
					for (int i = 0; i < index; i++) { // assign saved inventory to an array
						if (i == 0)
							savedInventory[i] = Integer.parseInt(saveFile.substring(ordinalIndexOf(saveFile, ":", 2)+2, ordinalIndexOf(saveFile, ",", i)));
						else
							savedInventory[i] = Integer.parseInt(saveFile.substring(ordinalIndexOf(saveFile, ",", i)+2, ordinalIndexOf(saveFile, ",", i+1)));
					}
					inventory.loadInventory(savedInventory);
				}
			}
		} catch(IOException e) {
			System.out.println("Error Loading Save: " + e);
		}
	}

	// Public, universal methods

	/** 
	 * Find index of the specified occurrence of a character
	 */
	public static int ordinalIndexOf(String str, String substr, int n) {
		int pos = str.indexOf(substr);
		while (--n > 0 && pos != -1)
			pos = str.indexOf(substr, pos + 1);
		return pos;
	}

	/** 
	 * Check if str contains searchStr (regardless of case)
	 */
	public static boolean containsIgnoreCase(String str, String searchStr)     {
		if (str == null || searchStr == null)
			return false;

		final int length = searchStr.length();
		if (length == 0)
			return true;

		for (int i = str.length() - length; i >= 0; i--) {
			if (str.regionMatches(true, i, searchStr, 0, length))
				return true;
		}
		return false;
	}

	/** 
	 * Check if str contains searchStr (regardless of case)
	 */
	public static int containsFindIndex(String str, String searchStr)     {
		if (str == null || searchStr == null)
			return -1;

		final int length = searchStr.length();
		if (length == 0)
			return -1;

		for (int i = str.length() - length; i >= 0; i--) {
			if (str.regionMatches(true, i, searchStr, 0, length))
				return i;
		}
		return -1;
	}
}