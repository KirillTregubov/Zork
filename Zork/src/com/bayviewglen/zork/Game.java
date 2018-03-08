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
    private final String SAVE = "data\\save.dat";
    private String room = "ROOM_1";
    private Room currentRoom;
    private Inventory inventory = new Inventory();
    // This is a MASTER object that contains all of the rooms and is easily accessible.
    // The key will be the name of the room -> no spaces (Use all caps and underscore -> Great Room would have a key of GREAT_ROOM
    // In a hashmap keys are case sensitive.
    // masterRoomMap.get("GREAT_ROOM") will return the Room Object that is the Great Room (assuming you have one).
    private HashMap<String, Room> masterRoomMap;
    
    private void initRooms(String fileName) throws Exception {
    	masterRoomMap = new HashMap<String, Room>();
    	Scanner roomScanner;
		try {
			HashMap<String, HashMap<String, String>> exits = new HashMap<String, HashMap<String, String>>();    
			roomScanner = new Scanner(new File(fileName));
			while(roomScanner.hasNext()) {
				Room room = new Room();
				// Read the Name
				String roomName = roomScanner.nextLine();
				room.setRoomName(roomName.split(":")[1].trim());
				// Read the Description
				String roomDescription = roomScanner.nextLine();
				room.setDescription(roomDescription.split(":")[1].replaceAll("<br>", "\n").trim());
				// Read the Exits
				String roomExits = roomScanner.nextLine();
				// An array of strings in the format E-RoomName
				String[] rooms = roomExits.split(":")[1].split(",");
				HashMap<String, String> temp = new HashMap<String, String>(); 
				for (String s : rooms) {
					temp.put(s.split("-")[0].trim(), s.split("-")[1]);
				}
				
				exits.put(roomName.substring(10).trim().toUpperCase().replaceAll(" ",  "_"), temp);
				
				// This puts the room we created
				masterRoomMap.put(roomName.toUpperCase().substring(10).trim().replaceAll(" ",  "_"), room);
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
			roomScanner.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
    }    

    /**
     * Create the game and initialize its internal map.
     */
    public Game() {
        try {
        	initRooms("data/Rooms.dat");
        	if (isGameSaved())
        		load();
        	currentRoom = masterRoomMap.get(room); // Teleporter	
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        parser = new Parser();
    }

    /**
     *  Main play routine.  Loops until end of play.
     */
    public void play() {            
        printWelcome();

        // Enter the main command loop.  Here we repeatedly read commands and
        // execute them until the game is over.
                
        boolean finished = false;
        while (!finished) {
            Command command = parser.getCommand();
            System.out.println("");
            finished = processCommand(command);
        }
        System.out.println("Thank you for playing. Goodbye!");
    }

    /**
     * Print out the opening message for the player.
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
     * Given a command, process (that is: execute) the command.
     * If this command ends the game, true is returned, otherwise false is
     * returned.
     */
    private boolean processCommand(Command command) {
        if(command.isUnknown()) {
            System.out.println("I don't know what you mean...");
            return false;
        }

        String commandWord = command.getCommandWord();
        String secondWord = command.getSecondWord();
        String thirdWord = command.getThirdWord();
        
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
        		if (secondWord.equals("me")) {
        			if (command.hasThirdWord()) {
        				if (thirdWord.equals("items"))
                			Items.listItems();
                		else if (thirdWord.equals("inventory"))
                			inventory.getInventory();	
        			}
        			else 
        				System.out.println("Show you what?");
        		} else
        			System.out.println("Show who?");
        	} else
        		System.out.println("Show who, what, where, when?");
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
     * Save Function
     */
    private BufferedWriter writer;
    private BufferedReader reader;
    String LineRead, TmpRead,Appender;

    
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
    	} catch(IOException e) {
    		System.out.println("Error Saving: " + e);
    	}
    }
    public boolean isGameSaved() {
    	try {
    		reader = new BufferedReader(new FileReader(SAVE));
    		return reader.readLine() != null;
    	} catch(IOException e) {
        	return false;
    	}
    }
    
    public void load() {
    	String saveFile = null, counter;
    	try {
    		reader = new BufferedReader(new FileReader(SAVE));
    		while ((counter = reader.readLine()) != null) {		// while loop to determine last line in save file
    			saveFile = counter; 	// assigns the latest save to variable saveFile
    		}
    	    reader.close();
    	    room = saveFile.substring(ordinalIndexOf(saveFile, " ", 1), ordinalIndexOf(saveFile, ";", 1));	// find saved room
    	    room = room.trim().toUpperCase().replaceAll(" ",  "_");
    	    if (ordinalIndexOf(saveFile, ":", 2) != -1) {	// check if inventory was saved
    	    	int x = 0, index = 0;
        	    while (x != -1) {	// find how many items are in the saved inventory
        	    	x = ordinalIndexOf(saveFile, ",", index);
        	    	index++;
        	    }
        	    String[] savedInventory = new String[index];
        	    for (int i = 0; i < index-2; i++) {		// assign saved inventory to an array
        	    	if (i == 0)
        	    		savedInventory[i] = saveFile.substring(ordinalIndexOf(saveFile, ":", 2)+2, ordinalIndexOf(saveFile, ",", i));
        	    	else
        	    		savedInventory[i] = saveFile.substring(ordinalIndexOf(saveFile, ",", i)+2, ordinalIndexOf(saveFile, ",", i+1));
        	    }
        	    inventory.loadInventory(savedInventory);
    	    }
    	} catch(IOException e) {
    	        	System.out.println("Error Loading Save: " + e);
    	}
	}


    // implementations of user commands:

    /**
     * Print out some help information.
     * Here we print some stupid, cryptic message and a list of the 
     * command words.
     */
    private void printHelp() {
        System.out.println("You are lost. You are alone. You wander...");
        System.out.println();
        System.out.println("Your command words are:");
        parser.showCommands();
    }

    /** 
     * Try to go to one direction. If there is an exit, enter the new
     * room, otherwise print an error message.
     */
    private void goRoom(Command command) {
        if(!command.hasSecondWord()) {
            // if there is no second word, we don't know where to go...
            System.out.println("Where would you like to go?");
            return;
        }

        String direction = command.getSecondWord();

        // Try to leave current room.
        Room nextRoom = currentRoom.nextRoom(direction);

        if (nextRoom == null)
            System.out.println("That's not an option... You might be trapped.");
        else {
            currentRoom = nextRoom;
            System.out.println(currentRoom.travelDescription());
        }
    }
    
    /** 
     * Try to go to one direction. If there is an exit, enter the new
     * room, otherwise print an error message.
     */
    private void checkInventory() {
        
    	
    	/*if(!command.hasSecondWord()) {
            // if there is no second word, we don't know where to go...
            System.out.println("Where would you like to go?");
            return;
        }

        String direction = command.getSecondWord();

        // Try to leave current room.
        Room nextRoom = currentRoom.nextRoom(direction);

        if (nextRoom == null)
            System.out.println("That's not an option... You might be trapped.");
        else {
            currentRoom = nextRoom;
            System.out.println(currentRoom.travelDescription());
        }
        */
    }
    
    public static int ordinalIndexOf(String str, String substr, int n) {
        int pos = str.indexOf(substr);
        while (--n > 0 && pos != -1)
            pos = str.indexOf(substr, pos + 1);
        return pos;
    }
  
}