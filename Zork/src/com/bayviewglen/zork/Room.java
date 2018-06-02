package com.bayviewglen.zork;

import java.util.Set;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;

/** "Room" Class - a class manipulating rooms in the game.
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
 *  "Room" represents one location in the scenery of the game.  It is 
 *  connected to at most four other rooms via exits.  The exits are labelled
 *  north, east, south, west.  For each direction, the room stores a reference
 *  to the neighbouring room, or null if there is no exit in that direction.
 */

class Room {
	private String roomID;
	private String roomName;

	private String description;
	private ArrayList<Item> items;
	private ArrayList<Integer> originalItemAmounts;
	private HashMap<String, Room> exits; // stores exits of this room.
	private ArrayList<Entity> enemies;
	private ArrayList<Entity> bosses;
	private ArrayList<Entity> npcs;
	private String trialName;

	/**
	 * Create a room described "description". Initially, it has no exits.
	 * "description" is something like "a kitchen" or "an open court yard".

	public Room(String description) {
		this.description = description;
		exits = new HashMap<String, Room>();
	}*/

	public Room() {
		// default constructor.
		roomName = "DEFAULT ROOM";
		description = "DEFAULT DESCRIPTION";
		exits = new HashMap<String, Room>();
		// Items
		items = new ArrayList<Item>();
		originalItemAmounts = new ArrayList<Integer>();
		// Characters
		enemies = new ArrayList<Entity>();
		bosses = new ArrayList<Entity>();
		npcs = new ArrayList<Entity>();
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
		String returnString = "Currently in: " + roomName;
		if (description.length() > 1) returnString += "\n" + description;
		if (hasExits()) returnString += "\n" + listExits(); // hide trial exits if (!roomID.equals("2-1")) 
		if (hasItems()) returnString += "\n" + listItems();
		if (hasNPCs()) returnString += "\n" + listNPCs();
		if (hasEnemies()) returnString += "\n" + listEnemies();
		if (hasBosses()) returnString += "\n" + listBosses();
		return returnString;
	}


	/**
	 * Returns the name, description, and exits related to the room being travelled to.
	 */
	public String travelDescription() {
		String returnString = "Going to: " + roomName;
		if (description.length() > 1) returnString += "\n" + description;
		if (hasExits()) returnString += "\n" + listExits(); // hide trial exits
		if (hasItems()) returnString += "\n" + listItems();
		if (hasNPCs()) returnString += "\n" + listNPCs();
		if (hasEnemies()) returnString += "\n" + listEnemies();
		if (hasBosses()) returnString += "\n" + listBosses();
		return returnString;
	}

	/**
	 * Return a string describing the room's exits, for example
	 * "Exits: north west ".
	 */
	private String listExits() {
		String returnString = "Available Directions:";
		Set<String> keys = exits.keySet();
		for(Iterator<String> iter = keys.iterator(); iter.hasNext(); )
			returnString += " " + iter.next();
		return returnString;
	}

	private boolean hasExits() {
		if (exits.size() > 0) return true;
		else return false;
	}

	private boolean hasItems() {
		if (items.size() > 0) return true;
		else return false;
	}
	
	public boolean hasNPCs() { // doesn't account for NPCs -> fix?
		if (getRoomNPCs()!=null && getRoomNPCs().size() > 0) return true;
		else return false;
	}

	public boolean hasEnemies() { // doesn't account for NPCs -> fix?
		if (getRoomEnemies()!=null && getRoomEnemies().size() > 0) return true;
		else return false;
	}

	public boolean hasBosses() {
		if (getRoomBosses()!=null && getRoomBosses().size() > 0) return true;
		else return false;
	}

	/**
	 * Return a string describing the room's exits, for example
	 * "Exits: north west ".
	 */
	private String listItems() {
		if (!hasItems()) return "There are no items in this room.";

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
	public boolean hasItem(Item item) {
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
				} else System.out.println("Failed to add " + Item.getItem(Integer.parseInt(items2[i].substring(0, endItemIndex))) + " to inventory!");
			}
		}
	}

	public void updateItems(Player player, String roomID) {
		for (int i = 0; i < items.size(); i++) {
			if (player.inventory.hasItem(items.get(i).toString()) && player.didPickUpItem(items.get(i).toString(), roomID)) {

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

	public String getTrial() {
		return trialName;
	}

	public void setTrial(String trialName) {
		this.trialName = trialName;
	}

	// toString method.
	public String toString() {
		return roomName;
	}

	/*
	 *  Battle Method
	 */
	public String listNPCs() {
		liveCheck();
		String returnString = "";
		if (hasNPCs()) {
			returnString = "NPCs in this room: ";

			for (int i = 0; i < getRoomNPCs().size(); i++) {
				returnString += getRoomNPCs().get(i).toString();

				if (i == getRoomNPCs().size() - 1) returnString += ".";
				else returnString += ", ";
			}
		}
		return returnString;
	}
	
	public String listEnemies() {
		liveCheck();
		String returnString = "";
		if (hasEnemies()) {
			returnString = "Enemies in this room: ";

			for (int i = 0; i < getRoomEnemies().size(); i++) {
				returnString += getRoomEnemies().get(i).toString();

				if (i == getRoomEnemies().size() - 1) returnString += ".";
				else returnString += ", ";
			}
		}
		return returnString;
	}

	public String listBosses() {
		liveCheck();
		String returnString = "";
		if (hasBosses()) {
			returnString = "Bosses in this room: ";

			for (int i = 0; i < getRoomBosses().size(); i++) {
				returnString += getRoomBosses().get(i).toString();

				if (i == getRoomBosses().size() - 1) returnString += ".";
				else returnString += ", ";
			}
		}
		return returnString;
	}

	public void resetEntities() {
		enemies = new ArrayList<Entity>();
		bosses = new ArrayList<Entity>();
		npcs = new ArrayList<Entity>();
	}

	public ArrayList<Entity> getRoomEnemies() {
		return enemies;
	}

	public void setRoomEnemies(ArrayList<Entity> enemies) {
		this.enemies = enemies;
	}

	public ArrayList<Entity> getRoomBosses() {
		return bosses;
	}

	public void setRoomBosses(ArrayList<Entity> bosses) {
		this.bosses = bosses;
	}

	public ArrayList<Entity> getRoomNPCs() {
		return npcs;
	}

	public void setRoomNPC(ArrayList<Entity> npcs) {
		this.npcs = npcs;
	}

	public boolean hasRepeatedEnemies(String enemyName) {
		int check = 0;
		for (int i = 0; i < enemies.size(); i++) {
			if (Utils.containsCompareBoth(enemyName, enemies.get(i).toString())) check++;
		}

		if (check > 1) return true;
		else return false;
	}

	//Checks and edits the list of entities based on if they are alive or not
	public void liveCheck() {
		// Enemy
		if (getRoomEnemies()!=null) {
			int enemyAmt = getRoomEnemies().size();
			for (int i = 0; i < enemyAmt; i++) {
				if (getRoomEnemies().get(i).isAlive() == false) {
					getRoomEnemies().remove(i);
					enemyAmt--;
					i--;
				}
			}
		}
		// Boss
		if (getRoomBosses() != null) {
			int bossAmt = getRoomBosses().size();
			for (int i = 0; i < bossAmt; i++) {
				if (getRoomBosses().get(i).isAlive() == false) {
					getRoomBosses().remove(i);
					bossAmt--;
					i--;
				}
			}
		}
		// NPC
		/*int nSize = npcs.size();
			for (int i=0;i<nSize;i++) {
				if (npcs.get(i).isAlive()==false) {
					npcs.remove(i);
					nSize--;
					i--;
					}
			}*/
	}

	public Entity findEnemy(Player player, String entityName) {
		for (Entity entity : getRoomEnemies()) {
			if (Utils.containsIgnoreCase(entity.toString(), entityName)) return entity;
		}
		for (Entity entity : getRoomBosses()) {
			if (Utils.containsIgnoreCase(entity.toString(), entityName)) return entity;
		}
		return null;
	}
	
	public Entity findNPC(Player player, String entityName) {
		for (Entity entity : getRoomNPCs()) {
			if (Utils.containsIgnoreCase(entity.toString(), entityName)) return entity;
		}
		return null;
	}

	public int startCustomBattle(Player player, String entityName) {
		// Correctly assign given entity
		Entity enemy = findEnemy(player, entityName);

		//System.out.println(entityName); // test
		int battleResult = -1;
		boolean run = false;
		boolean loss = false;

		Battle currentBattle = new Battle(player, enemy);
		battleResult = currentBattle.startBattle();

		liveCheck(); // Removes dead enemies
		return battleResult;
	}

	public void setEntities(String[][] ent) {
		if (ent != null && ent[0][0]!=null) { //String _name,int _type,String _stats
			for (int i=0;i<ent[0].length;i++) {
				if (ent[1][i].compareTo("Enemy") == 0)
					getRoomEnemies().add(new Entity(ent[0][i],Entity.ENEMY_INDEX,ent[2][i],ent[3][i]));
				else if (ent[1][i].compareTo("Boss") == 0)
					getRoomBosses().add(new Entity(ent[0][i],Entity.BOSS_INDEX,ent[2][i],ent[3][i]));
				else if (ent[1][i].compareTo("NPC") == 0) {
					getRoomNPCs().add(new Entity(ent[0][i],Entity.NPC_INDEX,"",ent[3][i]));
				}
				else
					System.out.println("Loading entities has failed");
			}
		}
	}

	public void addEntity(Entity entity) {
		if (entity.getType().equals(Entity.TYPES[Entity.ENEMY_INDEX])) {
			getRoomEnemies().add(entity);
		} else if (entity.getType().equals(Entity.TYPES[Entity.BOSS_INDEX])) {
			getRoomBosses().add(entity);
		} else
			System.out.println("Loading entities has failed");
	}
}