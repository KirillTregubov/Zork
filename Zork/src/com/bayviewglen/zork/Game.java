package com.bayviewglen.zork;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.FileWriter; 
import java.io.FileReader;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Scanner;

/** "Game" Class - the main class of the game.
 * 
 *  Original Code Author: 	Michael Kolling
 *  Original Code Version:	1.0
 *  Original Published Date: July 1999
 * 
 *  Current Authors: 		Kirill Tregubov, Zacharia Burrafato, Andrew Douglas, Alim Halani
 *  Current Code Version:	0.3-alpha
 *  Current Published Date:	May 2018
 * 
 *  This class is the main class of the "Zork" application. Zork is a very
 *  simple, text based adventure game.  Users can walk around some scenery.
 *  That's all. It should really be extended to make it more interesting!
 * 
 *  To play this game, create an instance of this class and call the "play"
 *  routine.
 * 
 *  This main class creates and initializes all the others: it creates all
 *  rooms, creates the parser and starts the game.  It also evaluates the
 *  commands that the parser returns.
 */

// LEGACY Search terms: Teleporter, Changeme..

class Game {
	public static final String GAME_NAME = "A Life Beyond"; // NAME
	public static final String GAME_VERSION = "0.3-alpha"; // VERSION
	private Parser parser;
	private BufferedWriter writer;
	private BufferedReader reader;
	public static final String FILE_LOCATION = "data\\"; // Change to "data/save.dat" if using Mac
	private final String DEFAULT_ROOM = "0-1";
	private Player player;
	private Sound musicMainTheme;
	private TrialDriver trialDriver;
	private Trial currentTrial;
	private boolean completingTrial;

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
			initRooms(FILE_LOCATION + "rooms.dat");
			musicMainTheme = new Sound(FILE_LOCATION + "music1.wav");
			trialDriver = new TrialDriver();


			// Load game if saved
			if (gameIsSaved()) load();
			else player.setCurrentRoom(player.masterRoomMap.get(DEFAULT_ROOM));

			// Create Parser
			parser = new Parser();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * 
	 *  Main play routine (loops until quit
	 */
	public void play() {
		// Initiate Music
		Sound mainmusic = new Sound(FILE_LOCATION + "music1.wav");
		mainmusic.loop();
		FlashingImages welcomeImage = new FlashingImages(FILE_LOCATION+"max.jpg",3000); 
		
<<<<<<< HEAD
=======

		printWelcome();
>>>>>>> branch 'master' of https://github.com/KTregubov/Zork

		printWelcome();
		
		// Enter the main command loop: repeatedly reads / executes commands until the game is over
		boolean finished = false;

		// Player is playing game
		while (!finished) { // check if in trial mode !!!
			if (completingTrial) finished = playTrial();
			else {
				System.out.println("");
				Command command = parser.getCommand(player);
				finished = processCommand(command);
			}
		}
		// End Game
		System.out.println("Thank you for playing. Goodbye!");
		Sound.stop();

	}

	private void initRooms(String fileName) throws Exception {
		player.masterRoomMap = new HashMap<String, Room>();
		BufferedReader reader;
		try {
			HashMap<String, HashMap<String, String>> exits = new HashMap<String, HashMap<String, String>>();
			reader = new BufferedReader(new FileReader(fileName));
			String line;

			while((line = reader.readLine()) != null) {
				if (line.contains("ID: ")) {
					// Create room
					Room room = new Room();
					// Read the ID
					String[] rawID = line.split(":")[1].split(" ");
					String roomID = "";
					for (int i = 1; i < rawID.length; i++) {
						roomID += Integer.parseInt(rawID[i], 2);
						if (i < rawID.length-1) roomID += "-";
					}
					//String roomID = line.substring(line.indexOf(":")+2).replaceAll(" ", "-");
					room.setRoomID(roomID);
					// Read the Name
					String roomName = reader.readLine();
					room.setRoomName(roomName.split(":")[1].trim()); // ATTENTION: Why is roomName not stored the way it's read?
					// Read the Description
					String roomDescription = reader.readLine();
					room.setDescription(Utils.formatStringForPrinting(roomDescription.substring(roomDescription.indexOf(":")+2).replaceAll("<br>", "\n").trim()));
					// Read the Exits
					String roomExits = reader.readLine();

					// An array of strings in the format "E-RoomName"
					String[] rooms = roomExits.split(":")[1].split(",");
					HashMap<String, String> temp = new HashMap<String, String>(); 
					for (String s : rooms)
						temp.put(s.split("=")[0].trim(), s.split("=")[1]);
					// LEGACY exits.put(roomName.substring(10).trim().toUpperCase().replaceAll(" ",  "_"), temp);
					exits.put(roomID, temp);

					// Read items, assign to array, and store it
					String roomItems = reader.readLine();
					if (roomItems.contains("Items: ") && roomItems.split(":")[1].trim().length() > 0) {
						roomItems = roomItems.split(":")[1].trim();
						String[] itemsString = roomItems.split(", ");
						room.setItems(itemsString); // assign items to the room's variable
					}

					// Read Trials and Assign them 
					String trial = reader.readLine();
					if (trial.contains("Trial: ") && trial.substring(trial.indexOf(":")+2).trim().length() > 0) {
						room.setTrial(trial.substring(trial.indexOf(":")+2).trim());
					}
					// WORK IN PROGRESS

					// Read Entities and Create them 
					String roomEntities = reader.readLine();
					if (roomEntities.contains("Entities: ") && roomEntities.split(":")[1].trim().length() > 0) {
						roomEntities = roomEntities.split(":")[1].trim();

						String[] entityString;
						entityString = roomEntities.split("/ ");
						// Enemy, type
						String[][] entities = new String[3][entityString.length];
						String[] entitiesStrings;

						for (int i=0;i<entityString.length;i++) {
							entitiesStrings = entityString[i].split(" <");
							entities[0][i] = entitiesStrings[0];
							entities[1][i] = entitiesStrings[1].substring(0, entitiesStrings[1].length()-1);
							entities[2][i] = entitiesStrings[2].substring(0,entitiesStrings[2].length()-1);
						}
						room.setEntities(entities);
					}

					// Assign room to be stored as roomID
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
			System.out.println("The rooms.dat file was not found! Please download one from the game's repository and insert it into " + FILE_LOCATION);
		}
	}

	/**
	 * Prints welcome message
	 */
	private void printWelcome() {
		System.out.println("\n" + "Welcome to " + GAME_NAME + "!"
				+ "\n" + "We hope you enjoy playing " + GAME_NAME + ", an incredibly enjoyable adventure game!"
				+ "\n" + "You are currenty playing version " + GAME_VERSION
				+ "\n" + "You can type 'help' at any time if you need any."
				+ "\n" + "Good luck and have fun!"+ "\n");

		if (gameIsSaved()) {
			System.out.print("Welcome back, " + player.name
					+ "\nAutomatically loaded game state from " + timeGameWasSaved()
					+ "\nLast known location: " + player.getRoomName()
					+ "\nLast known items in inventory:");
			if (player.inventory.isEmpty()) System.out.println(" Nothing was found...");
			else System.out.println(player.inventory + "\n");
			if (completingTrial) System.out.println("WARNING: You are currently completing a trial!");
			System.out.println(player.getRoomDescription());
		} else {
			completingTrial = true;
		}
	}

	private boolean playTrial() {
		// trialDriver currentTrial completingTrial
		boolean finished = false;
		while(!finished && completingTrial) {
			// Tutorial
			if (currentTrial == null) {
				int i = 0;
				if (player.doesRoomHaveTrial() && player.getRoomTrial().equals("tutorial")) {
					currentTrial = trialDriver.tutorial(i, player);
					i++;
				}

				//while (i < currentTrial.getSections()) {
				while (!finished && player.getRoom().hasEnemies()) {
					System.out.println("");
					Command command = parser.getCommand(player);
					finished = processCommand(command);
				}
				if (finished) return true;
				else { 
					currentTrial = trialDriver.tutorial(i, player);
					completingTrial = false;
					return false;
				}
				//}

			} // Trial One
			else if (currentTrial.toString().equals("trialone")) {
				int i = 1;
				//while (i < currentTrial.getSections()) {
				while (!finished && player.getRoomID().equals("1")) { // add check ?
					System.out.println("");
					Command command = parser.getCommand(player);
					finished = processCommand(command);
				}
				if (finished) return true;
				else { 
					currentTrial = trialDriver.trialOne(i, player);
					while (!finished && player.getRoomID().equals("1-1")) { // add check ?
						System.out.println("");
						Command command = parser.getCommand(player);
						finished = processCommand(command);
					}
					//completingTrial = false;
					return false;
				}
				//}
			}
		}
		return false; // insurance in case everything breaks
	}

	/*
	 * Work in Progress
	 */
	private boolean processCommand(Command command) {
		if(command.isUnknown()) {
			System.out.println("You cannot do that...");
			return false;
		}

		String commandName = command.getCommand();
		String commandType = command.getCommandType();
		String contextWord = command.getContextWord();
		Integer numbers[] = command.getNumbers();
		//System.out.println(commandName + "\n" + commandType + "\n" + contextWord);

		// help
		if (commandName.equalsIgnoreCase("help")) printHelp();
		// list
		else if (commandName.equalsIgnoreCase("list")) printCommands(); // might need to add contextWord
		// go
		else if (commandName.equalsIgnoreCase("go") || commandName.equalsIgnoreCase("walk")) { 
			goRoom(command, commandName);
		} // start
		else if (commandType.equalsIgnoreCase("trial")) {
			if (completingTrial) {
				System.out.println("You cannot start a trial while completing one.");
				return false;
			}
			try {
				if (command.getFirstNumber() == 1) {
					currentTrial = trialDriver.trialOne(0, player);
					/*} else if (command.getFirstNumber() == 2) {

				} else if (command.getFirstNumber() == 3) {

				} else if (command.getFirstNumber() == 4) {

				} else if (command.getFirstNumber() == 5) {

				} else if (command.getFirstNumber() == 6) {

				} else if (command.getFirstNumber() == 7) {

					 */
				} else {
					System.out.println("Unable to start that trial! Please try again.");
					return false;
				}
				completingTrial = true;
			} catch (Exception e) {
				System.out.println("Unable to start that trial! Please try again.");
				return false;
			}
		} // abandon
		else if (commandName.equalsIgnoreCase("abandon")) {
			if (!completingTrial) {
				System.out.println("There is nothing to abandon!");
				return false;
			}
			if (currentTrial.toString().equals("tutorial")) {
				System.out.println("You cannot abandon the tutorial!");
				return false;
			}
			System.out.println("Are you sure you want to abandon your current trial?");
			System.out.print("\n> ");
			Scanner nameInput = new Scanner(System.in);
			String answer = nameInput.nextLine().toLowerCase();
			if (answer.contains("y") || answer.contains("e")) {
				System.out.println("You have abandoned " + currentTrial.toString() + ".");
				currentTrial = null;
				completingTrial = false;
			} else return false;
		} // teleport
		else if (commandName.equalsIgnoreCase("teleport") || commandName.equalsIgnoreCase("tp")) {
			Room nextRoom = player.masterRoomMap.get(contextWord);
			if (nextRoom != null) {
				player.setCurrentRoom(nextRoom);
				System.out.println(player.getRoomTravelDescription());
			}
			/*} // give
		else if (commandName.equalsIgnoreCase("give")) {
		System.out.println("What would you like to receive?");
		continueCommand(commandName);*/
		} // battle
		else if (commandType.equalsIgnoreCase("battle")) { // || commandName.equalsIgnoreCase("fight") || commandName.equalsIgnoreCase("challenge") || commandName.equalsIgnoreCase("attack")
			if (contextWord == null) {
				System.out.println("You cannot battle that!");
			} else {
				if (player.getRoom().hasRepeatedEnemies(contextWord)) {
					System.out.println("There are multiple enemies in this room with that name! Please be more specific!");
				} else if (player.getRoom().hasEnemies() && player.getRoom().findEnemy(player, contextWord).getType().equals(Entity.TYPES[Entity.BOSS_INDEX])) {
					System.out.println("You must defeat all enemies before challenging the boss!");
				}
				else {
					int battleResult = player.getRoom().startCustomBattle(player,contextWord);
					if (battleResult == 2) {
						player.setDefaultRoom();
						System.out.println("Respawning...");
						System.out.println("\n"+player.getRoom().longDescription());
						player.stats.setCurrentHP(player.stats.getMaximumHP());
					}
					if (!completingTrial || battleResult == 0) System.out.println("\n" + player.getRoom().longDescription());
				}
			}
		} // eat
		else if (commandName.equalsIgnoreCase("consume")) { // add check if it's consumable - add joke
			if (contextWord != null) {
				try {
					if (player.consumeItem(contextWord)) {
						System.out.println("You consumed " + Item.getItem(contextWord));
					} else if (!player.inventory.containsItem(contextWord)) System.out.println("That item is not in your inventory!");
					else System.out.println("You cannot consume that.");
				} catch (Exception e) {
					System.out.println("That's not an item! Even if it was, do you really think you should be eating at a time like this?");
				}
			} else System.out.println("What would you like to consume?");
			//continueCommand("eat");
		} // look
		else if (commandName.equalsIgnoreCase("look") || commandName.equalsIgnoreCase("inspect")) { // might want to add look around?
			if (contextWord != null) {
				if (commandType.contains(" ")) System.out.println("You are not able to inspect that! Please be more specific.");
				if (commandType.equals("item")) {
					if (contextWord.equalsIgnoreCase("equipped")) {
						System.out.println(player.checkEquippedItems());
					}
					String check = player.itemCanBeLookedAt(contextWord);
					if (check.equals("roomrepeated")) System.out.println("Please be more specific. There are multiple items with this name!");
					else if (check.equals("contains")) System.out.println(Item.getItem(contextWord).getDescription() + "\n" + Item.getItem(contextWord).stats);
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
		} // check
		else if (commandName.equalsIgnoreCase("check")) {
			if (contextWord.equalsIgnoreCase("equipped")) System.out.println(player.checkEquippedItems());
			else if (contextWord.equalsIgnoreCase("stats")) System.out.println(player.stats);
		} // equip
		else if (commandName.equalsIgnoreCase("equip")) {
			if (Item.isItem(contextWord)) {
				if (player.inventory.containsItem(contextWord)) {
					if (Item.getItem(contextWord).type.equals(Item.TYPES[Item.WEAPON_INDEX])) {
						player.setEquippedWeapon(player.inventory.getItem(contextWord));
						// print
					} else if (Item.getItem(contextWord).type.equals(Item.TYPES[Item.WEAPON_INDEX])) {
						player.setEquippedArmor(player.inventory.getItem(contextWord));
						// print
					} else System.out.println("The given item is not armor or a weapon.");
				} else System.out.println("An item must be in your inventory for it to be equipped!");
			} else System.out.println("You are not able to equip that! Check your spelling, otherwise it probably doesn't exist.");

		} // take
		else if (commandName.equalsIgnoreCase("take") || (commandName.equalsIgnoreCase("pickup") || (commandName.equalsIgnoreCase("grab")))) { // add way to pick up amounts of stackable items
			if (contextWord != null) {
				//System.out.println(player.itemCanBePickedUp(givenItem)); test command
				if (player.itemCanBePickedUp(contextWord).equals("roomrepeated")) {
					System.out.println("Please be more specific. There are multiple items with this name!");
					/*} else if (player.itemCanBePickedUp(givenItem).equals("inventorycontains")) { may be needed in the future
					System.out.println("This item is already in your inventory!");*/
				} else if (player.itemCanBePickedUp(contextWord).equals("roomnotcontains")) {
					System.out.println("There is no " + Item.getItem(contextWord) + " in this room!");
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
		} // mute
		else if (commandName.equalsIgnoreCase("mute")) {
			musicMainTheme.pause();
			System.out.println("Game sound has been muted.");
		} // unmute
		else if (commandName.equalsIgnoreCase("unmute")) {
			musicMainTheme.loop();
			System.out.println("Game sound has been enabled.");
		} // save
		else if (commandName.equalsIgnoreCase("save")) save();
		// quit
		else if (commandName.equalsIgnoreCase("quit") || commandName.equalsIgnoreCase("stop")) { // player wants to quit
			return true;
		} // wrong command
		else System.out.println("The command " + commandName + " has been unaccounted for. Valve, please fix!");
		return false;
	}

	/**
	 * Processes a given command, assuming a related command was previously entered
	@Deprecated
	private void continueCommand(String originalCommand) { // work in progress??
		Command command = parser.getSecondaryCommand(player);
		String commandInput = command.command;
		Integer numbers[] = command.numbers;

		System.out.println("");
		if(command.isUnknown()) {
			System.out.println("You cannot do that...");
		}

		if (originalCommand.equals("eat")) {
			System.out.println("Whatever you say... You still can't eat at a time like this.");
		} // wrong command
		else System.out.println("You cannot do that...");
	}*/

	// User Commands

	/**
	 * Print list of commands used
	 */
	private void printHelp() {
		System.out.println("You are lost. You are alone. You wander..."
				+ "\nTo find out what commands are available, type in \"list commands\""
				+ "\nIf you are stuck in a trial, you can always abandon it using the abandon command.");
	}

	private void printCommands() {
		System.out.println("All available commands:");
		System.out.print(parser.listCommands());
	}

	/** 
	 * Go to specified room (in the specified direction)
	 */
	private void goRoom(Command command, String givenCommand) {
		/*for (Entry<String, Room> entry : player.masterRoomMap.entrySet()) {
			String key = entry.getKey();
			System.out.println(key);
			Room value = entry.getValue();
			System.out.println(value.getRoomName());
			if (room.getRoomName().equals(command.getContextWord())) {
				System.out.println("SUCC");
			}
		}*/

		if (command.getContextWord() == null) {
			System.out.print("Please indicate a direction you would like to ");
			if (givenCommand.equalsIgnoreCase("walk")) System.out.println("walk in.");
			else System.out.println("go in.");
			return;
		}
		String direction = command.getContextWord();
		System.out.println(currentTrial.canFlee() + " " + direction.equals(currentTrial.getFleeDirection()));
		if (completingTrial && !currentTrial.canFlee() && direction.equals(currentTrial.getFleeDirection())) 
			System.out.println(currentTrial.getLeaveReason());
		else if (completingTrial && !currentTrial.canContinue() && !currentTrial.canFlee()) 
			System.out.println(currentTrial.getLeaveReason());
		else {
			// Try to leave current room.
			Room nextRoom = player.getNextRoom(direction);
			if (nextRoom != null) {
				player.setCurrentRoom(nextRoom);
				player.updateItems(player, nextRoom.getRoomID());
				System.out.println(player.getRoomTravelDescription());

				// LEGACY Init battles
				//player.getRoom().startBattle(player);
			} else System.out.println("That's not an option... You might be trapped.");
		}
	}

	/*
	 * Save Method
	 */
	public void save() {
		if (completingTrial) {
			System.out.println("You cannot save while completing a trial!");
			return;
		}
		try {
			writer = new BufferedWriter(new FileWriter(FILE_LOCATION + "save.dat", true));
			writer.write("Name: " + player.name + "; ");	// save room currently in
			writer.write("Room: " + player.getRoomID() + "; ");	// save room currently in
			if (!player.inventory.isEmpty()) { // check to make sure inventory isn't empty
				writer.write("Inventory: " + player.inventory.saveInventory() + "; ");	// save inventory	
			}
			writer.write("Time Saved: " + LocalDateTime.now() + "; ");
			writer.write("Trial: " + completingTrial);
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
			reader = new BufferedReader(new FileReader(FILE_LOCATION + "save.dat"));
			return reader.readLine() != null;
		} catch(IOException e) {
			return false;
		}
	}

	/*
	 * Returns time the last time the game was saved
	 */
	public String timeGameWasSaved() {
		try {
			if (gameIsSaved()) {
				String saveFile = null, line;
				reader = new BufferedReader(new FileReader(FILE_LOCATION + "save.dat"));
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
				reader = new BufferedReader(new FileReader(FILE_LOCATION + "save.dat"));
				while ((line = reader.readLine()) != null) {	// while loop to determine last line in save file
					saveFile = line; // assigns the latest save to variable saveFile
				}
				reader.close();

				// Find and assign currentRoom to the room in the save file
				player.setName(saveFile.substring(Utils.ordinalIndexOf(saveFile, ":", 1) + 2, Utils.ordinalIndexOf(saveFile, ";", 1)));
				player.setCurrentRoom(player.masterRoomMap.get(saveFile.substring(Utils.ordinalIndexOf(saveFile, ":", 2) + 2, Utils.ordinalIndexOf(saveFile, ";", 2))));

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
				if (saveFile.substring(Utils.ordinalIndexOf(saveFile, "Trial:", 1)+7).equals("true")) completingTrial = true;
				else completingTrial = false;
			}
		} catch(IOException e) {
			System.out.println("Error Loading Save: " + e);
		}
	}
}