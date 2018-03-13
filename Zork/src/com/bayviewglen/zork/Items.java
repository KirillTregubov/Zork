package com.bayviewglen.zork;

/** "Inventory" Class - a class that stores and manages the inventory of the player.
 * Current Authors: Kirill Tregubov, Zacharia Burrafato, Andrew Douglas, Alim Halani
 * Current Version: 0.1-alpha
 * Current Date:    March 2018
 */

class Items {
	// an array that holds all items in the game and their stats
	public static final String items[][] = { // Name, Description, Type (Consumable, Weapon)
			{"Item 1", "This is a test item 1.", ""},
			{"Item 2", "This is a test item 2.", ""},
			{"Item 3", "This is a test item 3.", ""},
			{"Item 4", "This is a test item 4.", ""},
			{"Item 5", "This is a test item 5.", ""},
			{"Red Apple", "This is a test apple.", "Consumable"},
			{"Basic Sword", "This is a basic sword.", ""}
	};

	/**
	 * Gets item at specific index
	 */
	public static String getItem(int index) {
		if (index >= 0 && index < items.length) return items[index][0];
		else throw new IllegalStateException("Specified index is not a valid item!");
	}

	/**
	 * Gets item at specific index
	 */
	public static String getItemDescription(int index) {
		if (index < items.length) return items[index][1];
		else throw new IllegalStateException("Specified index is not a valid item description!");
	}

	/**
	 * Returns index of a given item (returns -1 if it's not an item)
	 */
	public static int getItemIndex(String item) {
		int index = -1;
		if (Items.isItem(item)) {
			for (int i = 0; i < items.length; i++) {
				if (Game.containsIgnoreCase(items[i][0],item)) index = i; // try both for now
				if (Game.containsIgnoreCase(item, items[i][0])) index = i;
			}
		}
		return index;
	}

	/**
	 * Lists all items in the game in a string
	 */
	public static String listItems() {
		String returnString = "";
		for (int i = 0; i < items.length; i++) {
			returnString += " " + items[i][0];
			if (i < items.length-1) returnString += ",";
			else returnString += ".";
		}
		return returnString;
	}

	/**
	 * Returns true if given string is an item
	 */
	public static boolean isItem(String item) { // item = "the 5"
		for (int i = 0; i < items.length; i++) {
			if (Game.containsIgnoreCase(items[i][0],item)) return true; // try both for now
			if (Game.containsIgnoreCase(item,items[i][0])) return true;
		}

		return false;
	}
}
