package com.bayviewglen.zork;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Scanner;

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

/*
 * Search terms: Teleporter, Changeme.
 */
class Game {
	private Parser parser;
	private BufferedWriter writer;
	private BufferedReader reader;
	private final String SAVE = "data\\save.dat";
	private Room currentRoom;
	private String room = "ROOM_1";
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
				if (line.contains("Room Name: ")) {
					Room room = new Room();
					// Read the Name
					String roomName = line;
					room.setRoomName(roomName.split(":")[1].trim()); // ATTENTION: Why is roomName not stored the way it's read?
					
					// Read the Description
					String roomDescription = reader.readLine();
					room.setDescription(roomDescription.split(":")[1].replaceAll("<br>", "\n").trim());
					// Read the Exits
					String roomExits = reader.readLine();
					
					// An array of strings in the format "E-RoomName"
					String[] rooms = roomExits.split(":")[1].split(",");
					HashMap<String, String> temp = new HashMap<String, String>(); 
					for (String s : rooms) {
						temp.put(s.split("-")[0].trim(), s.split("-")[1]);
					}
					exits.put(roomName.substring(10).trim().toUpperCase().replaceAll(" ",  "_"), temp);
					
					// Read items, assign to array, and store it
					String roomItems = reader.readLine();
					roomItems = roomItems.split(":")[1].trim();
					String[] items;
					items = roomItems.split(", ");
					room.setItems(items); // assign items to the room's variable
					masterRoomMap.put(roomName.toUpperCase().substring(10).trim().replaceAll(" ",  "_"), room);
				}
			}
			for (String key : masterRoomMap.keySet()) {
				Room roomTemp = masterRoomMap.get(key);
				HashMap<String, String> tempExits = exits.get(key);
				for (String s : tempExits.keySet()) {
					// s = direction
					// value is the room.

					String roomName2 = tempExits.get(s.trim());
					Room exitRoom = masterRoomMap.get(roomName2.toUpperCase().replaceAll(" ", "_"));
					roomTemp.setExit(s.trim().charAt(0), exitRoom);
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
			initRooms("data/Rooms.dat");
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
			Command command = parser.getCommand();
			System.out.println("");
			finished = processCommand(command);
		}
		System.out.println("Thank you for playing. Goodbye!");
	}

	/**
	 * Prints welcome message
	 */
	private void printWelcome() {
		System.out.println();
		System.out.println("Welcome to Zork!");
		System.out.println("Zork is a new, incredibly boring adventure game.");
		System.out.println("Currenty Playing Version: 0.1-alpha"); // Changeme
		System.out.println("Type 'help' if you need help.");
		System.out.println();
		System.out.println(currentRoom.longDescription());
	}

	/**
	 * Processes a given command (if this command ends the game, true is returned)
	 */
	private boolean processCommand(Command command) {
		if(command.isUnknown()) {
			System.out.println("I don't know what you mean...");
			return false;
		}
		String secondWord = null;
		String thirdWord = null;

		String commandWord = command.getCommandWord();
		if (command.hasSecondWord()) {
			secondWord = command.getSecondWord();
			if (command.hasThirdWord()) {
				thirdWord = command.getThirdWord();
			}
		}

		if (commandWord.equals("help"))
			printHelp();
		else if (commandWord.equals("go"))
			goRoom(command);
		else if (commandWord.equals("give")) {
			System.out.println("What would you like to be receive?");
			continueCommand(commandWord);
		} else if (commandWord.equals("eat")) {
			System.out.println("Do you really think you should be eating at a time like this?");
		} else if (commandWord.equals("show")) {
			if(command.hasSecondWord()) {
				if (secondWord.equals("items"))
					Items.listItems();
				else if (secondWord.equals("inventory")) {
					if (inventory.isEmpty())
						System.out.println("Your inventory is empty!");
					else
						inventory.getInventory();
				} else 
					System.out.println("I cannot show that...");
			} else
				System.out.println("What would you like to be shown?");
		} else if (commandWord.equals("pick")) {
			if(command.hasSecondWord()) {
				if (secondWord.equals("up")) {
					if (command.hasThirdWord()) {
						boolean pickedUp = false;
						for (int i = 0; i < currentRoom.getItemAmount(); i++) {
							if (thirdWord.equals(currentRoom.getItem(i))) {
								inventory.addToInventory(currentRoom.getItem(i));
								pickedUp = true;
							}
						}
						if (pickedUp)
							System.out.println(thirdWord + " was added to your inventory!");
						else 
							System.out.println("You weren't able to pick up the item.");
					} else 
						System.out.println("Pick up what?");
				} else {
					System.out.println("Pick what?");
				 }
			} else
				System.out.println("Pick ..?");
		} else if (commandWord.equals("save"))
			save();
		else if (commandWord.equals("quit")) {
			if(command.hasSecondWord())
				System.out.println("Quit what?");
			else
				return true;  // signal that we want to quit
		}
		return false;
	}

	private void continueCommand(String originalCommand) {
		Command command = parser.getSecondaryCommand();
		System.out.println("");
		if(command.isUnknown()) {
			System.out.println("I don't know what you mean...");
		}

		String commandWord = command.getCommandWord();
		String secondWord = command.getSecondWord();
		String thirdWord = command.getThirdWord();

		if (originalCommand.equals("give")) {
			inventory.addToInventory(Items.getItem(Integer.parseInt(commandWord)));
			System.out.println("Item given!");
		}
	}

	/*
	 * Save Method
	 */
	public void save() {
		try {
			writer = new BufferedWriter(new FileWriter(SAVE, true));
			writer.write("Save file on " + LocalDateTime.now());
			writer.newLine();
			writer.write("Room: " + currentRoom.getRoomName() + "; ");	// save room currently in
			if (!inventory.isEmpty()) { // check to make sure inventory isn't empty
				writer.write("Inventory: " + inventory.saveInventory());	// save inventory	
			}
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
			reader = new BufferedReader(new FileReader(SAVE));
			return reader.readLine() != null;
		} catch(IOException e) {
			return false;
		}
	}

	/*
	 * Load Method - loads save file (if it exists)
	 */
	public void load() {
		String saveFile = null, counter;
		try {
			if (gameIsSaved()) {
				reader = new BufferedReader(new FileReader(SAVE));
				while ((counter = reader.readLine()) != null) {	// while loop to determine last line in save file
					saveFile = counter; // assigns the latest save to variable saveFile
				}
				reader.close();
				room = saveFile.substring(ordinalIndexOf(saveFile, " ", 1), ordinalIndexOf(saveFile, ";", 1)); // find saved room
				room = room.trim().toUpperCase().replaceAll(" ",  "_");
				if (ordinalIndexOf(saveFile, ":", 2) != -1) { // check if inventory was saved
					int x = 0, index = 0;
					while (x != -1) { // find how many items are in the saved inventory
						x = ordinalIndexOf(saveFile, ",", index);
						index++;
					}
					String[] savedInventory = new String[index];
					for (int i = 0; i < index-2; i++) { // assign saved inventory to an array
						if (i == 0)
							savedInventory[i] = saveFile.substring(ordinalIndexOf(saveFile, ":", 2)+2, ordinalIndexOf(saveFile, ",", i));
						else
							savedInventory[i] = saveFile.substring(ordinalIndexOf(saveFile, ",", i)+2, ordinalIndexOf(saveFile, ",", i+1));
					}
					inventory.loadInventory(savedInventory);
				}
			}
		} catch(IOException e) {
			System.out.println("Error Loading Save: " + e);
		}
	}


	// User Commands:

	/**
	 * Print list of commands used
	 */
	private void printHelp() {
		System.out.println("You are lost. You are alone. You wander...");
		System.out.println();
		System.out.println("Your command words are:");
		parser.showCommands();
	}

	/** 
	 * Go to specified room (in the specified direction)
	 */
	private void goRoom(Command command) {
		if (!command.hasSecondWord()) {
			System.out.println("Where would you like to go?");
			return;
		}
		String direction = command.getSecondWord();
		// Try to leave current room.
		Room nextRoom = currentRoom.nextRoom(direction);
		if (nextRoom != null) {
			currentRoom = nextRoom;
			System.out.println(currentRoom.travelDescription());
		} else 
			System.out.println("That's not an option... You might be trapped.");
	}

	/** 
	 * Find index of the specified occurrence of a character
	 */
	public static int ordinalIndexOf(String str, String substr, int n) {
		int pos = str.indexOf(substr);
		while (--n > 0 && pos != -1)
			pos = str.indexOf(substr, pos + 1);
		return pos;
	}

}