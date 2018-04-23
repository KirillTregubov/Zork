package com.bayviewglen.zork;

import java.util.HashMap;

/** "Player" Class - a class that creates a player and stores their data.
 * 
 * Current Authors: Kirill Tregubov, Zacharia Burrafato, Andrew Douglas, Alim Halani
 * Current Version: 0.2-alpha
 * Current Date:    April 2018
 */

public class Player {

	public String name;
	private Room currentRoom;
	public Inventory inventory;
	public Stats stats;
	public HashMap<String, Room> masterRoomMap;

	Player() {
		inventory = new Inventory();
		//pickedUpItems = new Inventory();
		stats = new Stats(Stats.ENTITY_INDEX, Stats.PLAYER_INDEX, "1,0,0,20,20,2,2,2,0.5,0.1");		
	}

	// Item Variables
	static Item itemOne = new Item("First Item", "This is a test item 1.");
	static Item itemTwo = new Item("Second Item", "This is a test item 1.");
	static Item itemThree = new Item("Item Three", "This is a test item 1.");
	static Item itemFour = new Item("Item Four", "This is a test item 1.");
	static Item itemFive = new Item("Item Five", "This is a test item 1.");
	static Item redApple = new Item("Red Apple", "Apple", Item.CONSUMABLE_INDEX, "5");
	static Item basicSword = new Item("Basic Sword", "This is a test item 1.", Item.WEAPON_INDEX, "10,0.5");
	public static Item items[] = {itemOne, itemTwo, itemThree, itemFour, itemFive, redApple, basicSword};
	//public static ArrayList<Item> pickedUpItems = new ArrayList<Item>();

	public String teleport(String roomName) {
		Room roomTemp = masterRoomMap.get(roomName);
		try {
			if (roomTemp != null) return roomName;
		} catch (Exception e) { }
		return null;
	}

	public boolean didPickUpItem(String itemName, String roomID) {
		if (inventory.containsItem(itemName) && inventory.getItem(itemName).roomID.contains(roomID)) return true;
		return false;
	}

	/*
	 * Inventory Methods
	 */

	public String pickUpItem(String itemName, String roomID) { // add fix for consumables
		Item inputItem;
		try {
			inputItem = new Item(Item.getItem(itemName));

			if (1 > currentRoom.getItem(itemName).amount)
				return "toomuch";
			else if (inventory.containsItem(itemName))
				inputItem.amount = 1 + inventory.getItem(itemName).amount;
			else
				if (inputItem.isStackable) inputItem.amount = 1; // change so that user can specify how many to pick up*/

			if (!inputItem.roomID.contains(roomID)) {
				inputItem.roomID.add(roomID);
				inputItem.pickedUpAmounts.add(1);
			} else inputItem.pickedUpAmounts.add(inputItem.roomID.indexOf(roomID), 1);

			inventory.addToInventory(inputItem, itemName, roomID);
			return null;
		} catch (Exception e) {
			return "error";
		}
	}

	public String pickUpItem(String itemName, String roomID, Integer numbers[]) { // add fix for consumables
		Item inputItem;
		try {
			inputItem = new Item(Item.getItem(itemName));

			int amount = 1;
			if (inputItem.isStackable) amount = numbers[0];

			if (inventory.containsItem(itemName) && inventory.getItem(itemName).amount < currentRoom.getItem(itemName).amount)
				inputItem.amount = amount + inventory.getItem(itemName).amount;
			else if (inventory.containsItem(itemName) && amount + inventory.getItem(itemName).amount >= currentRoom.getItem(itemName).amount)
				return "toomuch";
			else if (amount > currentRoom.getItem(itemName).amount) 
				return "toomuch";
			else
				if (inputItem.isStackable) inputItem.amount = amount; // change so that user can specify how many to pick up

			if (!inputItem.roomID.contains(roomID)) {
				inputItem.roomID.add(roomID);
				inputItem.pickedUpAmounts.add(numbers[0]);
			} else inputItem.pickedUpAmounts.set(inputItem.roomID.indexOf(roomID), amount + inputItem.pickedUpAmounts.get(inputItem.roomID.indexOf(roomID)));

			inventory.addToInventory(inputItem, itemName, roomID, amount);
			return null;
		} catch (Exception e) {
			return "error";
		}
	}

	public String itemCanBePickedUp(String itemName) { // add check for consumables
		Item inputItem;
		try {
			inputItem = Item.getItem(itemName);
		} catch (Exception e) {
			return "error";
		}
		if (currentRoom.hasRepeatedItems(itemName)) return "roomrepeated";
		//else if (inventory.hasRepeatedItems(itemName)) return "inventoryrepeated";
		else if (!currentRoom.containsItem(inputItem)) return "roomnotcontains";
		//else if (inventory.containsItem(itemName)) return "inventorycontains";
		else return "";
	}

	public String itemCanBeLookedAt(String itemName) {
		//Item inputItem = Item.getItem(itemName);
		if (currentRoom.hasRepeatedItems(itemName)) return "roomrepeated";
		//else if (pickedUpItems.containsItem(itemName) || currentRoom.containsItem(Item.getItem(itemName))) return "contains";
		return "";
	}

	/*
	 * Room Getters
	 */
	public Room getRoom() {
		return currentRoom;
	}

	public String getRoomID() {
		return currentRoom.getRoomID();
	}

	public String getRoomName() {
		return currentRoom.getRoomName();
	}

	public String getRoomDescription() {
		return currentRoom.longDescription();
	}

	public String getRoomShortDescription() {
		return currentRoom.getDescription();
	}

	public String getRoomTravelDescription() {
		return currentRoom.travelDescription();
	}

	public Room getNextRoom(String direction) {
		return currentRoom.nextRoom(direction);
	}

	public int getRoomItemAmount() {
		return currentRoom.getItemAmount();
	}

	public int getRoomItemAmount(String itemName) {
		return currentRoom.getItemAmount(itemName);
	}

	public boolean doesRoomContainItem(String itemName) {
		Item inputItem = Item.getItem(itemName);
		return currentRoom.containsItem(inputItem);
	}

	public void updateItems(Player player, String roomID) {
		currentRoom.updateItems(player, roomID);
	}

	/*
	 * Room Setter
	 */
	public void setCurrentRoom(Room currentRoom, Player player) {
		this.currentRoom = currentRoom;
		//updateItems(player);
	}

	/*
	 * Misc Setters
	 */
	public void setName(String name) {
		this.name = name;
	}
}
