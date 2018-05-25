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
	private ArrayList<Entity> enemies;
	private ArrayList<Entity> bosses;
	private ArrayList<Entity> npcs;


	/**
	 * Create a room described "description". Initially, it has no exits.
	 * "description" is something like "a kitchen" or "an open court yard".

	public Room(String description) {
		this.description = description;
		exits = new HashMap<String, Room>();
	}*/

	public Room() {
		// default constructor.
		enemies = new ArrayList<Entity>();
		bosses = new ArrayList<Entity>();
		npcs = new ArrayList<Entity>();
		roomName = "DEFAULT ROOM";
		description = "DEFAULT DESCRIPTION";
		exits = new HashMap<String, Room>();
		items = new ArrayList<Item>();
		originalItemAmounts = new ArrayList<Integer>();
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
		String returnString = "Currently in: " + roomName +"\n" + description + "\n" + listExits()+"\n"+listEnemies();
		if (hasItems()) return returnString + "\n" + listItems();
		else return returnString;
	}


	/**
	 * Returns the name, description, and exits related to the room being travelled to.
	 */
	public String travelDescription() {
		String returnString = "Going to: " + roomName +"\n" + description + "\n" + listExits()+"\n"+listEnemies();
		if (hasItems()) return returnString + "\n" + listItems();
		else return returnString;
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

	private boolean hasItems() {
		if (items.size() == 0) return false;
		else return true;
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

	// toString method.
	public String toString() {
		return roomName;

	}

	// Andy Entity Stuff

	public String listEnemies() {

		liveCheck();
		String returnString = "";
		if (getRoomEnemies()!=null) {
			if (getRoomEnemies().size() > 0) {
				returnString = "\n" + "Enemies in this room: ";

				for (int i = 0; i < getRoomEnemies().size(); i++) {

					returnString += getRoomEnemies().get(i).getName(); // Display stats later (Inspect function)

					if (i == getRoomEnemies().size() - 1) {
						returnString += ".";
					} else {
						returnString += ", ";
					}
				}
			} else {
				returnString = "";
			}
		}
		return returnString;
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

	public ArrayList<Entity> getRoomNPC() {
		return npcs;
	}

	public void setRoomNPC(ArrayList<Entity> npc) {
		this.npcs = npc;
	}

	//Checks and edits the list of entities based on if they are alive or not
	public void liveCheck() {
		// Enemy
		if (getRoomEnemies()!=null) {
			int eSize = getRoomEnemies().size();
			for (int i=0;i<eSize;i++) {
				if (getRoomEnemies().get(i).isAlive()==false) {
					getRoomEnemies().remove(i);
					eSize--;
					i--;
				}
			}
		}
		// Boss
		if (getRoomBosses()!=null) {
			int bSize = getRoomBosses().size();
			for (int i=0;i<bSize;i++) {
				if (getRoomBosses().get(i).isAlive()==false) {
					getRoomBosses().remove(i);
					bSize--;
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
	
	public void startCustomBattle(Player player, String entityName) {
		
		System.out.println(entityName);
		int battleResult = -1;
		boolean run = false;
		boolean loss = false;
		
		if (getRoomEnemies()!=null) { // Enemy battle
			if (getRoomEnemies().size()>0) {
				for (int i=0;i<getRoomEnemies().size();i++) {
					if (entityName.toLowerCase().equals(getRoomEnemies().get(i).getName().toLowerCase())) {
						// Start the enemy battle
						Battle batEnemy = new Battle(player,getRoomEnemies().get(i));
						battleResult = batEnemy.startBattle();
						if (battleResult==0) {
							// TODO: Run away commands
							run = true;
						}
						else if (battleResult==1) {
							// TODO: Victory. Should probably give them loot here based on the enemy's level
							//getRoomEnemies().get(i).stats.getLevel();
							player.expCalculator(getRoomEnemies().get(i).stats.getLevel(), NPC.TYPE_ENEMY);
						}
						else if (battleResult==2) {
							loss = true;
						}
					}
				}
			}
		}
		if (getRoomBosses()!=null) { // Enemy battle
			if (getRoomBosses().size()>0) {
				for (int i=0;i<getRoomBosses().size();i++) {
					if (entityName.toLowerCase().equals(getRoomBosses().get(i).getName().toLowerCase())) {
						// Start the boss battle
						Battle batBoss = new Battle(player,getRoomBosses().get(i));
						battleResult = batBoss.startBattle();
						if (battleResult==0) {
							// TODO: Run away commands
							run = true;
						}
						else if (battleResult==1) {
							// TODO: Victory. Should probably give them loot here based on the enemy's level
							//getRoomEnemies().get(i).stats.getLevel();
							player.expCalculator(getRoomBosses().get(i).stats.getLevel(), NPC.TYPE_BOSS);
						}
						else if (battleResult==2) {
							loss = true;
						}
					}
				}
			}
		}
		
		liveCheck(); // Removes the dead enemies
		
		if (loss==true) {
			player.setDefaultRoom();
			System.out.println("Respawning...");
			System.out.println("\n"+player.getRoom().longDescription());
			player.stats.setCurrentHP(player.stats.getMaximumHP());
		}
		else if (run==true) {
			// TODO: Move the player back to whence they came (Move the back to their previous room which should be stored somewhere)
			
		}
		else if (loss==false&&run==false&&battleResult==-1) {
			System.out.println("There is no enemy in this room by that name!");
		}
		else {
			System.out.println("\n"+player.getRoom().longDescription());
		}
		
	}

	public void startBattle(Player player) {
	// Start Battle
		
		
		int battleResult;
		boolean run = false;
		boolean loss = false;
		if (getRoomEnemies()!=null) { // Enemy battle
			if (getRoomEnemies().size()>0) {
				for (int i=0;i<getRoomEnemies().size()&&run==false&&loss==false;i++) {
					Battle bat = new Battle(player,getRoomEnemies().get(i));
					battleResult = bat.startBattle();
					if (battleResult==0) {
						// TODO: Run away commands
						run = true;
					}
					else if (battleResult==1) {
						// TODO: Victory. Should probably give them loot here based on the enemy's level
						//getRoomEnemies().get(i).stats.getLevel();
						player.expCalculator(getRoomEnemies().get(i).stats.getLevel(), NPC.TYPE_ENEMY);
					}
					else if (battleResult==2) {
						loss = true;
					}
				}
			}
		}
		if (getRoomBosses()!=null) { // Boss battle
			if (getRoomBosses().size()>0) {
				for (int i=0;i<getRoomBosses().size()&&run==false&&loss==false;i++) {
					Battle bat = new Battle(player,getRoomBosses().get(i));
					battleResult = bat.startBattle();
					if (battleResult==0) {
						run = true;
					}
					else if (battleResult==1) {
						// TODO: Victory. Should probably give them loot here based on the enemy's level
						//getRoomEnemies().get(i).stats.getLevel();
						player.expCalculator(getRoomBosses().get(i).stats.getLevel(), NPC.TYPE_BOSS);
					}
					else if (battleResult==2) {
						loss = true;
					}
				}
			}
		}
		
		liveCheck(); // Removes the dead enemies
		
		if (loss==true) {
			player.setDefaultRoom();
			System.out.println("Respawning...");
			System.out.println("\n"+player.getRoom().longDescription());
			player.stats.setCurrentHP(player.stats.getMaximumHP());
		}
		else if (run==true) {
			// TODO: Move the player back to whence they came (Move the back to their previous room which should be stored somewhere)
			
		}
		else {
			System.out.println("\n"+player.getRoom().longDescription());
		}
	}

	public void setEntities(String[][] ent) {

		if (ent!=null) { //String _name,int _type,String _stats

			if (ent[0][0]!=null) {
				for (int i=0;i<ent[0].length;i++) {
					if (ent[1][i].compareTo("Enemy")==0) {
						getRoomEnemies().add(new NPC(ent[0][i],NPC.TYPE_ENEMY,ent[2][i]));
					}
					else if (ent[1][i].compareTo("Boss")==0) {
						getRoomBosses().add(new NPC(ent[0][i],NPC.TYPE_BOSS,ent[2][i]));
					}
					else if (ent[1][i].compareTo("NPC")==0) {
					}
					else {
						System.out.println("Mission failed, run it again. (Loading entities has failed)");
					}
				}
			}

		}
	}
}