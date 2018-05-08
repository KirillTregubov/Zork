package com.bayviewglen.zork;

import java.util.Set;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;

/** "Room" Class - a class manipulating rooms in the game.
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
 * "Room" represents one location in the scenery of the game.  It is 
 * connected to at most four other rooms via exits.  The exits are labelled
 * north, east, south, west.  For each direction, the room stores a reference
 * to the neighbouring room, or null if there is no exit in that direction.
 */

class Room {
	private String roomID;
	private String roomName;
	private String description;
	private ArrayList<Item> items;
	private ArrayList<Integer> originalItemAmounts;
	private HashMap<String, Room> exits;        // stores exits of this room.

	/**
	 * Create a room described "description". Initially, it has no exits.
	 * "description" is something like "a kitchen" or "an open court yard".
	 */
	/*public Room(String description) {
		this.description = description;
		exits = new HashMap<String, Room>();
	}*/

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

	/**
	 * Returns the name, description, and exits related to the current room.
	 */
	public String longDescription() {
		return "Currently in: " + roomName +"\n" + description + "\n" + listExits() + "\n" + listItems();
	}


	/**
	 * Returns the name, description, and exits related to the room being travelled to.
	 */
	public String travelDescription() {
		return "Going to: " + roomName +"\n" + description + "\n" + listExits() + "\n" + listItems();
	}

	/**
	 * Return a string describing the room's exits, for example
	 * "Exits: north west ".
	 */
	private String listExits() {
		String returnString = "Exits:";
		Set<String> keys = exits.keySet();
		for(Iterator<String> iter = keys.iterator(); iter.hasNext(); )
			returnString += " " + iter.next();
		return returnString;
	}

	/**
	 * Return a string describing the room's exits, for example
	 * "Exits: north west ".
	 */
	private String listItems() {
		if (items.size() == 0) {
			return "There are no items in this room.";
		}
		String returnString = "Items in this room:";
		for (int i = 0; i < items.size(); i++) {
			if (items.get(i).isStackable) {
				returnString += " " + items.get(i).getAmount() + " " + items.get(i);
				if (items.get(i).getAmount() > 1)
					returnString += "s";
			} else {
				returnString += " " + items.get(i);
			}
			if (i < items.size()-1)
				returnString += ",";
			else
				returnString += ".";
		}
		return returnString;
	}

	/**
	 * Returns direction.
	 */
	public Room nextRoom(String direction) {
		return exits.get(direction);
	}

	/**
	 * Gets ID of room.
	 */
	public String getRoomID() {
		return roomID;
	}

	/**
	 * Sets ID of room.
	 */
	public void setRoomID(String roomID) {
		this.roomID = roomID;
	}

	/**
	 * Gets name of room.
	 */
	public String getRoomName() {
		return roomName;
	}

	/**
	 * Sets name of room.
	 */
	public void setRoomName(String roomName) {
		this.roomName = roomName;
	}

	/**
	 * Gets description of room.
	 */
	public String getDescription() {
		return description;
	}

	/**
	 * Sets description of room.
	 */
	public void setDescription(String description) {
		this.description = description;
	}

	/**
	 * Returns true if the room contains the given item.
	 */
	public boolean containsItem(Item item) {
		for (int i = 0; i < items.size(); i++) {
			if (Utils.containsCompareBoth(items.get(i).toString(), item.toString())) return true;
		}
		return false;
	}

	public String getItemName(int index) {	// Gets item at specific index
		if (index < items.size());
		//return Items.getItem(items[index]);
		//else
		return null;
	}

	public Item getItem(int index) {	// Gets item at specific index
		try {
			return items.get(index);
		} catch (Exception e) {
			throw new IllegalStateException("Wasn't able to find item in room at index " + index);
		}
	}

	public Item getItem(String itemName) {
		for (int i = 0; i < items.size(); i++) {
			if (Utils.containsCompareBoth(items.get(i).toString(), itemName)) return items.get(i);
		}
		throw new IllegalStateException("Wasn't able to find item called " + itemName);
	}

	public int getItemAmount() { // Gets amount of items, have to change this name when I add stackable items
		return items.size();
	}

	public int getItemAmount(String itemName) {
		Item inputItem = getItem(itemName);
		return inputItem.getAmount();
	}

	public boolean hasRepeatedItems(String itemName) {
		int check = 0;
		for (int i = 0; i < items.size(); i++) {
			if (Utils.containsCompareBoth(itemName, items.get(i).toString())) check++;
		}

		if (check > 1) return true;
		else return false;
	}

	public void setItems(String[] items2) {
		items = new ArrayList<Item>();
		originalItemAmounts = new ArrayList<Integer>();
		for (int i = 0; i < items2.length; i++) {
			try {
				if (Item.isItem(Item.getItem(Integer.parseInt(items2[i])))) {
					items.add(Item.getItem(Integer.parseInt(items2[i])));
				} else {
					System.out.println("Failed to add " + Item.getItem(items2[i]) + " to inventory!");
				}
			} catch (Exception e) {
				int endItemIndex = items2[i].indexOf("-");
				int itemAmountIndex = endItemIndex + 1;
				if (Item.isItem(Item.getItem(Integer.parseInt(items2[i].substring(0, endItemIndex))))) {
					Item inputItem = new Item(Item.getItem(Integer.parseInt(items2[i].substring(0, endItemIndex))));
					inputItem.setAmount(Integer.parseInt(items2[i].substring(itemAmountIndex)));
					originalItemAmounts.add(inputItem.getAmount());
					items.add(inputItem);
				} else {
					System.out.println("Failed to add " + Item.getItem(Integer.parseInt(items2[i].substring(0, endItemIndex))) + " to inventory!");
				}
			}
		}
	}

	public void updateItems(Player player, String roomID) {
		for (int i = 0; i < items.size(); i++) {
			if (player.inventory.containsItem(items.get(i).toString()) && player.didPickUpItem(items.get(i).toString(), roomID)) {
				if (items.get(i).isStackable) {
					Item oldItem = new Item(items.get(i));
					oldItem.setAmount(originalItemAmounts.get(i)
							- player.inventory.getItem(items.get(i).toString()).pickedUpAmounts.get(player.inventory.getItem(items.get(i).toString()).roomID.indexOf(roomID)));

					items.remove(items.get(i));
					items.add(i, oldItem);
					if (items.get(i).getAmount() < 1) {
						items.remove(i);
						originalItemAmounts.remove(i);
						i--;
					}
				} else {
					items.remove(items.get(i));
					i--;
				}
			}
		}
	}

	// toString method
	public String toString() {
		return roomName;
	}
}