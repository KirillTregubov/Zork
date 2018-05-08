package com.bayviewglen.zork;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.FileWriter; 
import java.io.FileReader;
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
 * Current Version: 0.2-alpha
 * Current Date:    April 2018
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
	private final String fileLocation = "data\\"; // Change to "data/save.dat" if using Mac
	private final String DEFAULT_ROOM = "0";
	private Player player;
	//private Inventory inventory = new Inventory();

	// This is a MASTER object that contains all of the rooms and is easily accessible.
	// The key will be the name of the room -> no spaces (Use all caps and underscore -> Great Room would have a key of GREAT_ROOM
	// In a hashmap keys are case sensitive.
	// masterRoomMap.get("GREAT_ROOM") will return the Room Object that is the Great Room (assuming you have one).
	// private HashMap<String, Room> masterRoomMap;

	/**
	 * Create the game and initialize its internal map
	 */
	public Game() {
		try {
			// Load Player
			player = new Player();

			// Initialize Rooms
			initRooms(fileLocation + "rooms.dat");

			// Load game if saved
			if (gameIsSaved()) load();
			else player.setCurrentRoom(player.masterRoomMap.get(DEFAULT_ROOM), player);
		} catch (Exception e) {
			e.printStackTrace();
		}
		parser = new Parser();
	}

	/**
	 *  Main play routine (loops until quit)
	 */
	public void play() {
		Music.playMusic(fileLocation);
		printWelcome();

		// Enter the main command loop: repeatedly reads / executes commands until the game is over
		boolean finished = false;
		while (!finished) {
			/*Command command = parser.legacyGetCommand(); // legacy implementation
			System.out.println("");
			finished = legacyProcessCommand(command); // change if making new command
			 */
			//WORK IN PROGRESS
			System.out.println("");
			Command command = parser.getCommand(player);
			finished = processCommand(command);
		}
		System.out.println("Thank you for playing. Goodbye!");
		Music.stopMusic();
		
	}

	private void initRooms(String fileName) throws Exception {
		player.masterRoomMap = new HashMap<String, Room>();
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
					String[] itemsString = roomItems.split(", ");
					room.setItems(itemsString); // assign items to the room's variable
					player.masterRoomMap.put(roomID, room);
				}
			}

			for (String key : player.masterRoomMap.keySet()) {
				Room roomTemp = player.masterRoomMap.get(key);
				HashMap<String, String> tempExits = exits.get(key);
				for (String s : tempExits.keySet()) {
					// s = direction
					// value is the room
					String roomName2 = tempExits.get(s);
					Room exitRoom = player.masterRoomMap.get(roomName2);
					roomTemp.setExit(s.charAt(0), exitRoom);
				}
			}
			reader.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
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
			System.out.print("Welcome back, " + player.name
					+ "\nAutomatically loaded game state from " + timeGameWasSaved()
					+ "\nLast known location: " + player.getRoomName()
					+ "\nLast known items in inventory:");
			if (player.inventory.isEmpty()) System.out.println(" Nothing was found...");
			else System.out.println(player.inventory);
			System.out.println();
		} else {
			System.out.println("Hello, traveller! What is your name?");
			System.out.print("> ");
			Scanner nameInput = new Scanner(System.in);
			player.setName(nameInput.nextLine());
			System.out.println("\nEnjoy your journey, " + player.name + "!");
		}

		System.out.println(player.getRoomDescription());
	}

	/*
	 * Work in Progress
	 */
	private boolean processCommand(Command command) {
		if(command.isUnknown()) {
			System.out.println("You cannot do that...");
			return false;
		}
		String commandName = command.command;
		String commandType = command.commandType;
		String contextWord = command.contextWord;
		Integer numbers[] = command.numbers;

		//System.out.println(commandName + "\n" + commandType + "\n" + contextWord);

		// help
		if (commandName.equalsIgnoreCase("help")) printHelp();
		// list
		else if (commandName.equalsIgnoreCase("list")) printCommands(); // might need to add contextWord
		// go
		else if (commandName.equalsIgnoreCase("go") || commandName.equalsIgnoreCase("walk")) goRoom(command, commandName);
		// teleport
		else if (commandName.equalsIgnoreCase("teleport") || commandName.equalsIgnoreCase("tp")) {
			Room nextRoom = player.masterRoomMap.get(contextWord);
			if (nextRoom != null) {
				player.setCurrentRoom(nextRoom, player);
				System.out.println(player.getRoomTravelDescription());
			}
		}/* // give
		else if (commandName.equalsIgnoreCase("give")) {
		System.out.println("What would you like to receive?");
		continueCommand(commandName);
		}*/ // eat
		else if (commandName.equalsIgnoreCase("eat") || commandName.equalsIgnoreCase("consume")) { // add check if it's consumable - add joke
			if (contextWord != null) {
				try { 
					if (player.inventory.containsItem(contextWord))System.out.println("Do you really think you should be eating " + Item.getItem(contextWord) + " at a time like this?");
					else System.out.println("The " + Item.getItem(contextWord) + " is not in your inventory... Even if it was, do you really think you should be eating at a time like this?");
				} catch (Exception e) {
					System.out.println("That's not an item! Even if it was, do you really think you should be eating at a time like this?");
				}
			} else System.out.println("Do you really think you should be eating at a time like this?");
			//continueCommand("eat");
		} // look
		else if (commandName.equalsIgnoreCase("look at") || commandName.equalsIgnoreCase("inspect")) {
			if (contextWord != null) {
				if (commandType.contains(" ")) System.out.println("You are not able to inspect that! Please be more specific.");
				if (commandType.equals("item")) {
					String check = player.itemCanBeLookedAt(contextWord);
					if (check.equals("roomrepeated")) System.out.println("Please be more specific. There are multiple items with this name!");
					else if (check.equals("contains")) System.out.println(Item.getItem(contextWord).getDescription() + "\n" + "Stats: " + Item.getItem(contextWord).stats);
					else System.out.println("That item is not in your inventory or in the " + player.getRoomName() + ".");
				} else if (commandType.equals("inventory")) {
					if (player.inventory.isEmpty()) System.out.println("Your inventory is empty!");
					else System.out.println("Your inventory contains:" + player.inventory);

				} else if (commandType.equals("place")) {
					if (contextWord.equals("room")) System.out.println(player.getRoomDescription());
					else {
						int count = 0;
						String roomName = null;
						for (String key : player.masterRoomMap.keySet()) {
							Room roomTemp = player.masterRoomMap.get(key);
							if (Utils.containsIgnoreCase(roomTemp.getRoomName(), contextWord)) {
								roomName = roomTemp.getRoomName();
								count++;
							}
						}
						if (count == 1) {
							if (roomName.equals(player.getRoomName()))  // doesn't work well / at all
								System.out.println(player.getRoomDescription());
							else
								System.out.println("In the future, you will be able to get directions to the " + roomName); // implement more
						}
						else System.out.println("You are not able to inspect that! Please be more specific.");
					}			
				}	
			} else {
				System.out.print("What would you like to ");
				if (commandName.equalsIgnoreCase("look")) System.out.println("look at?");
				else System.out.println(commandName + "?");
			}
		} // take
		else if (commandName.equalsIgnoreCase("take") || (commandName.equalsIgnoreCase("pick up"))) { // add way to pick up amounts of stackable items
			if (contextWord != null) {
				//System.out.println(player.itemCanBePickedUp(givenItem)); test command
				if (player.itemCanBePickedUp(contextWord).equals("roomrepeated")) {
					System.out.println("Please be more specific. There are multiple items with this name!");
					/*} else if (player.itemCanBePickedUp(givenItem).equals("inventorycontains")) { may be needed in the future
					System.out.println("This item is already in your inventory!");*/
				} else if (player.itemCanBePickedUp(contextWord).equals("roomnotcontains")) {
					System.out.println(Item.getItem(contextWord) + " is not in this room!");
				} else if (player.itemCanBePickedUp(contextWord).isEmpty()) {
					if (numbers != null) {
						if (player.pickUpItem(contextWord, player.getRoomID(), numbers) == null) {
							player.updateItems(player, player.getRoomID());
							if (numbers[0] > 1) System.out.println(numbers[0] + " " + Item.getItem(contextWord) + "s were added to your inventory!");
							else System.out.println(numbers[0] + " " + Item.getItem(contextWord) + " was added to your inventory!");
						}
						else if (player.pickUpItem(contextWord, player.getRoomID(), numbers) == "toomuch") System.out.println("TOO MUCH");
						else System.out.println("Encountered an error while adding the item to your inventory!");
					} else {
						if (player.pickUpItem(contextWord, player.getRoomID()) == null) {
							player.updateItems(player, player.getRoomID());
							System.out.println("A " + Item.getItem(contextWord) + " was added to your inventory!");
						}
						else if (player.pickUpItem(contextWord, player.getRoomID()) == "toomuch") System.out.println("TOO MUCH");
						else System.out.println("Encountered an error while adding the item to your inventory!");
					}
				} else {
					System.out.print("You weren't able to ");
					if (contextWord.equalsIgnoreCase("take")) System.out.print("take");
					else System.out.print("pick up");
					System.out.println(" this item... It probably doesn't exist.");
				}
			} else {
				System.out.print("You cannot");
				if (commandName.equalsIgnoreCase("take")) System.out.println("take that.");
				else System.out.println("pick up that.");
			}
		} // save
		else if (commandName.equalsIgnoreCase("save")) save();
		// quit
		else if (commandName.equalsIgnoreCase("quit") || commandName.equalsIgnoreCase("stop")) { // player wants to quit
			return true;
		} // wrong command
		else System.out.println("You cannot do that...");
		return false;
	}

	/**
	 * Processes a given command, assuming a related command was previously entered
	 */
	@Deprecated
	private void continueCommand(String originalCommand) { // work in progress
		Command command = parser.getSecondaryCommand(player);
		String commandInput = command.command;
		Integer numbers[] = command.numbers;

		System.out.println("");
		if(command.isUnknown()) {
			System.out.println("You cannot do that...");
		}

		/* if (originalCommand.equals("eat")) {
			System.out.println("Whatever you say... You still can't eat at a time like this.");
		} // wrong command
		else System.out.println("You cannot do that...");*/
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
		System.out.print(parser.listCommands());
	}

	/** 
	 * Go to specified room (in the specified direction)
	 */
	private void goRoom(Command command, String givenCommand) {
		if (command.contextWord == null) {
			System.out.print("Please indicate a direction you would like to ");
			if (givenCommand.equalsIgnoreCase("walk")) System.out.println("walk in.");
			else System.out.println("go in.");
			return;
		}
		String direction = command.contextWord;
		// Try to leave current room.
		Room nextRoom = player.getNextRoom(direction);
		if (nextRoom != null) {
			player.setCurrentRoom(nextRoom, player);
			player.updateItems(player, nextRoom.getRoomID());
			System.out.println(player.getRoomTravelDescription());
		} else System.out.println("That's not an option... You might be trapped.");
	}

	/*
	 * Save Method
	 */
	public void save() {
		try {
			writer = new BufferedWriter(new FileWriter(fileLocation + "save.dat", true));
			writer.write("Name: " + player.name + "; ");	// save room currently in
			writer.write("Room: " + player.getRoomID() + "; ");	// save room currently in
			if (!player.inventory.isEmpty()) { // check to make sure inventory isn't empty
				writer.write("Inventory: " + player.inventory.saveInventory() + "; ");	// save inventory	
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
			reader = new BufferedReader(new FileReader(fileLocation + "save.dat"));
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
				reader = new BufferedReader(new FileReader(fileLocation + "save.dat"));
				while ((line = reader.readLine()) != null) {
					saveFile = line;
				}
				if (saveFile.substring(Utils.ordinalIndexOf(saveFile, ":", 3) - 1, Utils.ordinalIndexOf(saveFile, ":", 3)).equals("y")) // check if inventory was saved
					return saveFile.substring(Utils.ordinalIndexOf(saveFile, ":", 4)+13, Utils.ordinalIndexOf(saveFile, ":", 4)+21)
							+ " on " + saveFile.substring(Utils.ordinalIndexOf(saveFile, ":", 4) + 10, Utils.ordinalIndexOf(saveFile, ":", 4) + 12)
							+ "/" + saveFile.substring(Utils.ordinalIndexOf(saveFile, ":", 4) + 7, Utils.ordinalIndexOf(saveFile, ":", 4) + 9)
							+ "/" + saveFile.substring(Utils.ordinalIndexOf(saveFile, ":", 4) + 2, Utils.ordinalIndexOf(saveFile, ":", 4) + 6);
				else
					return saveFile.substring(Utils.ordinalIndexOf(saveFile, ":", 3) + 13, Utils.ordinalIndexOf(saveFile, ":", 3) + 21)
							+ " on " + saveFile.substring(Utils.ordinalIndexOf(saveFile, ":", 3) + 10, Utils.ordinalIndexOf(saveFile, ":", 3) + 12)
							+ "/" + saveFile.substring(Utils.ordinalIndexOf(saveFile, ":", 3) + 7, Utils.ordinalIndexOf(saveFile, ":", 3) + 9)
							+ "/" + saveFile.substring(Utils.ordinalIndexOf(saveFile, ":", 3) + 2, Utils.ordinalIndexOf(saveFile, ":", 3) + 6);
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
				reader = new BufferedReader(new FileReader(fileLocation + "save.dat"));
				while ((line = reader.readLine()) != null) {	// while loop to determine last line in save file
					saveFile = line; // assigns the latest save to variable saveFile
				}
				reader.close();

				// Find and assign currentRoom to the room in the save file
				player.setName(saveFile.substring(Utils.ordinalIndexOf(saveFile, ":", 1) + 2, Utils.ordinalIndexOf(saveFile, ";", 1)));
				player.setCurrentRoom(player.masterRoomMap.get(saveFile.substring(Utils.ordinalIndexOf(saveFile, ":", 2) + 2, Utils.ordinalIndexOf(saveFile, ";", 2))), player);

				// Find and load inventory
				if (Utils.ordinalIndexOf(saveFile, ":", 3) != -1) { // check if inventory was saved
					int x = 0, index = 0;
					for (int i = 1; x != -1; i++) {
						index = i;
						x = Utils.ordinalIndexOf(saveFile, ",", index);
						if (x == -1)
							index--;
					}
					String[] savedInventory = new String[index];
					for (int i = 0; i < index; i++) { // assign saved inventory to an array
						if (i == 0)
							savedInventory[i] = saveFile.substring(Utils.ordinalIndexOf(saveFile, ":", 3)+2, Utils.ordinalIndexOf(saveFile, ",", i));
						else
							savedInventory[i] = saveFile.substring(Utils.ordinalIndexOf(saveFile, ",", i)+2, Utils.ordinalIndexOf(saveFile, ",", i+1));
					}

					player.inventory.loadInventory(savedInventory);
					player.updateItems(player, player.getRoomID());
				}
			}
		} catch(IOException e) {
			System.out.println("Error Loading Save: " + e);
		}
	}
}