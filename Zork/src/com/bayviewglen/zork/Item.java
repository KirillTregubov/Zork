package com.bayviewglen.zork;

import java.util.ArrayList;

/** "Item" Class - a class that creates item objects.
 * 
 *  Authors: 		Kirill Tregubov, Zacharia Burrafato, Andrew Douglas, Alim Halani
 *  Code Version:	0.2-alpha
 *  Published Date:	May 2018
 */

class Item {
	private String name;
	private String description;
	public Stats stats;
	private int amount;
	public boolean isStackable;
	public ArrayList<String> roomID;
	public ArrayList<Integer> pickedUpAmounts;
	public String type;
	public static final String TYPES[] = {"Base", "Consumable", "Weapon", "Armor", "Unique"};
	public final static int BASE_INDEX = 0;
	public final static int CONSUMABLE_INDEX = 1;
	public final static int WEAPON_INDEX = 2;
	public final static int ARMOR_INDEX = 3;
	public final static int UNIQUE_INDEX = 5;

	Item(String name, String description) {
		this.name = name;
		this.description = description;
		this.type = TYPES[BASE_INDEX];
		stats = new Stats(Stats.ITEM_INDEX, BASE_INDEX, "");
		amount = 1;
		roomID = new ArrayList<String>();
		pickedUpAmounts = new ArrayList<Integer>();
	}

	Item(String name, String description, int typeIndex, String stats) {
		this.name = name;
		this.description = description;
		try {
			type = TYPES[typeIndex];
			if (type.equals(TYPES[CONSUMABLE_INDEX])) isStackable = true;
		} catch (Exception e) {
			System.out.println("There was an error assigning a type to " + name + "!");
		}
		amount = 1;
		this.stats = new Stats(Stats.ITEM_INDEX, typeIndex, stats);
		roomID = new ArrayList<String>();
		pickedUpAmounts = new ArrayList<Integer>();
	}

	Item(String name, String description, int typeIndex, String stats, int[] uniqueIndexes) {
		this.name = name;
		this.description = description;
		try {
			type = TYPES[typeIndex];
			if (type.equals(TYPES[CONSUMABLE_INDEX])) isStackable = true;
		} catch (Exception e) {
			System.out.println("There was an error assigning a type to " + name + "!");
		}

		amount = 1;
		if (uniqueIndexes != null) {
			this.stats = new Stats(Stats.ITEM_INDEX, typeIndex, stats, uniqueIndexes);
		} else this.stats = new Stats(Stats.ITEM_INDEX, typeIndex, stats);
		roomID = new ArrayList<String>();
		pickedUpAmounts = new ArrayList<Integer>();
	}

	// Duplicate item constructor
	Item(Item item) {
		name = item.name;
		description = item.description;
		type = item.type;
		stats = item.stats;
		amount = item.amount;
		isStackable = item.isStackable;
		roomID = item.roomID;
		pickedUpAmounts = item.pickedUpAmounts;
	}

	/*
	 * Getters and Setters
	 */
	public String getDescription() {
		return description;
	}
	public int getAmount() {
		return amount;
	}

	public void setAmount(int amount) {
		this.amount = amount;
	}

	/*
	 * Static Item Methods
	 */

	// Returns if item has stats
	public boolean hasStats() {
		if (stats.stats == null) {
			return false;
		}
		return true;
	}

	// Returns item from given string
	public static Item getItem(int index) {
		if (index >= 0 && index < Player.items.length) return Player.items[index];
		else throw new IllegalStateException("Specified index is not a valid item!");
	}

	public static Item getItem(String itemName) {
		for (int i = 0; i < Player.items.length; i++) {
			if (Utils.containsCompareBoth(Player.items[i].name, itemName)) return Player.items[i];
		}
		throw new IllegalStateException("Wasn't able to find item called " + itemName);
	}

	// Returns if given item is an actual item
	public static boolean isItem(Item item) {
		for (int i = 0; i < Player.items.length; i++) {
			if (Utils.containsCompareBoth(Player.items[i].name, item.name)) return true;
		}
		return false;
	}

	public static boolean isItem(String itemName) {
		return isItem(Item.getItem(itemName));
	}

	// Returns if an item is stackable
	public static boolean isStackable(Item item) {
		return item.isStackable;
	}

	// Returns item description
	public static String getItemDescription(String itemName) {
		return getItem(itemName).description;
	}

	// Returns index of a given item (returns -1 if it's not an item)
	public static int getItemIndex(String itemName) {
		for (int i = 0; i < Player.items.length; i++) {
			if (Utils.containsCompareBoth(Player.items[i].name, getItem(itemName).name)) return i;
		}
		return -1;
	}

	// toString method
	public String toString() {
		return name;
	}
}
