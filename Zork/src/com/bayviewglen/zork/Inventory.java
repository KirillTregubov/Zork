package com.bayviewglen.zork;

import java.util.ArrayList;


/** "Inventory" Class - a class that stores and manages the inventory of the player.
 * 
 * Current Authors: Kirill Tregubov, Zacharia Burrafato, Andrew Douglas, Alim Halani
 * Current Version: 0.2-alpha
 * Current Date:    April 2018
 */

class Inventory {
	/**
	 * An array that holds the player's inventory
	 */
	private ArrayList<Item> inventory;

	public Inventory() {
		inventory = new ArrayList<Item>();
	}

	/**
	 * Saves inventory to a save file
	 */
	public String saveInventory() { // save inventory
		String inventoryString = "";
		int i = 0;
		for(i = 0; i < inventory.size(); i++) inventoryString = inventoryString + inventory.get(i) + "-" + inventory.get(i).getAmount() + "-" + inventory.get(i).stats + "-" + inventory.get(i).roomID.toString().replaceAll(",", "") + "-" + inventory.get(i).pickedUpAmounts.toString().replaceAll(",", "") + ", ";
		return inventoryString;
	}

	/**
	 * Loads inventory from a save file
	 */
	public void loadInventory(String[] savedInventory) { // load inventory from save        this is breaking items when saved
		inventory.clear();
		for (int i = 0; i < savedInventory.length; i++) {
			Item inputItem = Item.getItem(savedInventory[i].substring(0, Utils.ordinalIndexOf(savedInventory[i], "-", 1)));
			inputItem.setAmount(Integer.parseInt(savedInventory[i].substring(Utils.ordinalIndexOf(savedInventory[i], "-", 1) + 1, Utils.ordinalIndexOf(savedInventory[i], "-", 2))));
			inputItem.stats.loadStats(savedInventory[i].substring(Utils.ordinalIndexOf(savedInventory[i], "-", 2) + 1, Utils.ordinalIndexOf(savedInventory[i], "-", 3)));
			String roomIDs[] = savedInventory[i].substring(Utils.ordinalIndexOf(savedInventory[i], "-", 3) + 2, Utils.ordinalIndexOf(savedInventory[i], "-", 4)-1).split(" ");
			for (String j : roomIDs) {
				inputItem.roomID.add(j);
			}

			String pickedUpAmounts[] = savedInventory[i].substring(Utils.ordinalIndexOf(savedInventory[i], "-", 4) + 2, savedInventory[i].length()-1).split(" ");
			for (String j : pickedUpAmounts) {
				inputItem.pickedUpAmounts.add(Integer.parseInt(j));
			}
			inventory.add(inputItem);
		}
	}

	/**
	 * Adds item to inventory using specified index (if item exists)
	 * @return 
	 */
	public void addToInventory(Item item, String itemName, String roomID) { // work on this
		if (containsItem(itemName)) {
			if (!getItem(itemName).roomID.contains(roomID)) getItem(itemName).roomID.add(roomID);
			getItem(itemName).setAmount(getItem(itemName).getAmount()+1);
		} else inventory.add(item);
	}

	public void addToInventory(Item item, String itemName, String roomID, int amount) { // work on this
		if (containsItem(itemName)) {
			if (!getItem(itemName).roomID.contains(roomID)) getItem(itemName).roomID.add(roomID);
			getItem(itemName).setAmount(amount);
		} else inventory.add(item);
	}

	public Item getItem(String itemName) {
		for (int i = 0; i < inventory.size(); i++) {
			if (Utils.containsCompareBoth(inventory.get(i).toString(), itemName)) return inventory.get(i);
		}
		return null;
	}

	public Integer getItemIndex(String itemName) {
		for (int i = 0; i < inventory.size(); i++) {
			if (Utils.containsCompareBoth(inventory.get(i).toString(), itemName)) return i;
		}
		return null;
	}

	/**
	 * Returns true if inventory contains the given item.
	 */
	public boolean containsItem(String itemName) {
		for (int i = 0; i < inventory.size(); i++) {
			if (Utils.containsCompareBoth(inventory.get(i).toString(), itemName)) return true;
		}
		return false;
	}

	/**
	 * Returns true if inventory contains the given item.
	 */
	public boolean containsItem(Item item) {
		for (int i = 0; i < inventory.size(); i++) {
			if (Utils.containsCompareBoth(inventory.get(i).toString(), item.toString())) return true;
		}
		return false;
	}

	public int getItemAmount(String itemName) {
		Item inputItem = getItem(itemName);
		return inputItem.getAmount();
	}

	/**
	 * Returns true if a string is repeated in the inventory.
	 */
	public boolean hasRepeatedItems(String itemName) {
		int check = 0;
		for (int i = 0; i < inventory.size(); i++) {
			if (Utils.containsCompareBoth(inventory.get(i).toString(), itemName)) check++;
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

	// toString method
	public String toString() {
		String returnString = "";
		for (int i = 0; i < inventory.size(); i++) {
			if (inventory.get(i).isStackable) {
				returnString += " " + inventory.get(i).getAmount() + " " + inventory.get(i);
				if (inventory.get(i).getAmount() > 1) {
					returnString += "s";
				}
			}
			else returnString += " " + inventory.get(i);

			if (i < inventory.size()-1) returnString += ",";
			else returnString += ".";
		}
		return returnString;
	}
}