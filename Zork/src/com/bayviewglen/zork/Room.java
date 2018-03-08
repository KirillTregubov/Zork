package com.bayviewglen.zork;
/** "Room" Class - a class manipulating rooms in the game.
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
 * "Room" represents one location in the scenery of the game.  It is 
 * connected to at most four other rooms via exits.  The exits are labelled
 * north, east, south, west.  For each direction, the room stores a reference
 * to the neighbouring room, or null if there is no exit in that direction.
 */

import java.util.Set;
import java.util.HashMap;
import java.util.Iterator;

class Room {
	private String roomName;
    private String description;
    private String[] items;
    private HashMap<String, Room> exits;        // stores exits of this room.

    /**
     * Create a room described "description". Initially, it has no exits.
     * "description" is something like "a kitchen" or "an open court yard".
     */
    public Room(String description) {
        this.description = description;
        exits = new HashMap<String, Room>();
    }

    public Room() {
		// default constructor.
    	roomName = "DEFAULT ROOM";
    	description = "DEFAULT DESCRIPTION";
    	exits = new HashMap<String, Room>();
	}

    public void setExit(char direction, Room r) throws Exception{
    	String dir= "";
    	switch (direction){
    		case 'E': dir = "east";break;
    		case 'W': dir = "west";break;
    		case 'S': dir = "south";break;
    		case 'N': dir = "north";break;
    		case 'U': dir = "up";break;
    		case 'D': dir = "down";break;
    		default: throw new Exception("Invalid Direction");
    	}
    	exits.put(dir, r);
    }
    
	/**
     * Define the exits of this room.  Every direction either leads to
     * another room or is null (no exit there).
     */
    public void setExits(Room north, Room east, Room south, Room west, Room up, Room down) {
        if(north != null)
            exits.put("north", north);
        if(east != null)
            exits.put("east", east);
        if(south != null)
            exits.put("south", south);
        if(west != null)
            exits.put("west", west);
        if(up != null)
            exits.put("up", up);
        if(up != null)
            exits.put("down", down);   
    }

    /** LEGACY
     * Return the description of the room (the one that was defined in the
     * constructor).
     * public String shortDescription() {
     *   return "Room: " + roomName +"\n\n" + description;
     * }
     */

    /**
     * Returns the name, description, and exits related to the current room.
     */
    public String longDescription() {
        return "Currently in: " + roomName +"\n" + description + "\n" + exitString() + "\n" + itemString();
    }
    
    
    /**
     * Returns the name, description, and exits related to the room being travelled to.
     */
    public String travelDescription() {
        return "Going to: " + roomName +"\n" + description + "\n" + exitString() + "\n" + itemString();
    }

    /**
     * Return a string describing the room's exits, for example
     * "Exits: north west ".
     */
    private String exitString() {
        String returnString = "Exits:";
		Set keys = exits.keySet();
        for(Iterator iter = keys.iterator(); iter.hasNext(); )
            returnString += " " + iter.next();
        return returnString;
    }
    
    /**
     * Return a string describing the room's exits, for example
     * "Exits: north west ".
     */
    private String itemString() {
        String returnString = "Items in this room:";
        for (int i = 0; i < items.length; i++) {
        	returnString += " " + items[i];
        	if (i < items.length-1)
        		returnString += ",";
        	else
        		returnString += ".";
        }
        return returnString;
    }

    /**
     * Getters and Setters
     */
    
    public Room nextRoom(String direction) {
        return (Room)exits.get(direction);
    }

	public String getRoomName() {
		return roomName;
	}

	public void setRoomName(String roomName) {
		this.roomName = roomName;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}
	
	public String getItem(int index) {	// Gets item at specific index
    	if (index < items.length)
    		return items[index];
    	else
    		return null;
    }
	
	public int getItemAmount() { // Gets amount of items
    	return items.length;
    }
	
	public void getItems() {
		for(int i = 0; i < items.length; i++) {
            System.out.print(items[i] + "  ");
        }
        System.out.println();
	}

	public void setItems(String[] roomItems) {
		items = new String[roomItems.length];
		for (int i = 0; i < roomItems.length; i++) {
    		if (Items.isItem(roomItems[i]))
    			items[i] = roomItems[i];
    	}
	}
}