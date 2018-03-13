package com.bayviewglen.zork;

import java.util.ArrayList;

/** "Inventory" Class - a class that stores and manages the inventory of the player.
 * Current Authors: Kirill Tregubov, Zacharia Burrafato, Andrew Douglas, Alim Halani
 * Current Version: 0.1-alpha
 * Current Date:    March 2018
 */

class Inventory {
	/**
	 * An array that holds the player's inventory
	 */
	private ArrayList<Integer> inventory;

	public Inventory() {
		inventory = new ArrayList<Integer>();
	}

	/**
	 * Saves inventory to a save file
	 */
	public String saveInventory() { // save inventory
		String inventoryString = "";
		int i = 0;
		for(i = 0; i < inventory.size(); i++) inventoryString = inventoryString + inventory.get(i) + ", ";
		return inventoryString;
	}

	/**
	 * Loads inventory from a save file
	 */
	public void loadInventory(int[] savedInventory) { // load inventory from save
		inventory.clear();
		for (int i = 0; i < savedInventory.length; i++) inventory.add(savedInventory[i]);
	}

	/**
	 * Returns player's inventory as a string
	 */
	public String getInventory() {
		String returnString = "";
		for (int i = 0; i < inventory.size(); i++) {
			returnString += " " + Items.getItem(inventory.get(i));
			if (i < inventory.size()-1) returnString += ",";
			else returnString += ".";
		}
		return returnString;
	}

	/**
	 * Adds item to inventory using specified index (if item exists)
	 */
	public void addToInventory(int index) {
		if (Items.isItem(Items.getItem(index))) inventory.add(index);
		else throw new IllegalStateException("Item doesn't exist!");
	}

	/**
	 * Returns true if an inventory contains the given item.
	 */
	public boolean containsItem(String item) {
		for (int i = 0; i < inventory.size(); i++) {
			if (Game.containsIgnoreCase(item, Items.getItem(inventory.get(i)))) return true;
			if (Game.containsIgnoreCase(Items.getItem(inventory.get(i)), item)) return true;
		}
		return false;
	}

	/**
	 * Returns true if an inventory contains the item at the given index.
	 */
	public boolean containsItem(int itemIndex) {
		for (int i = 0; i < inventory.size(); i++)
			if (inventory.get(i) == itemIndex) return true;
		return false;
	}

	/**
	 * Returns true if a string is repeated in the inventory.
	 */
	public boolean isRepeated(String string) {
		int check = 0;
		for (int i = 0; i < inventory.size(); i++) {
			if (Game.containsIgnoreCase(Items.getItem(inventory.get(i)), string)) check++;
		}
		if (check > 1) return true;
		else return false;
	}

	/**
	 * Returns true if the player's inventory is empty.
	 */
	public boolean isEmpty() {
		return inventory.isEmpty();
	}
}