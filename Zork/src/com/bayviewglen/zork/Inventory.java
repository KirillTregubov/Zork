package com.bayviewglen.zork;

import java.util.ArrayList;

/** "Inventory" Class - a class that stores and manages the inventory of the player.
 * Current Authors: Kirill Tregubov, Zacharia Burrafato, Andrew Douglas, Alim Halani
 * Current Version: 0.1-alpha
 * Current Date:    March 2018
 */

class Inventory {
	// an array that holds all valid command words   
    private ArrayList<String> inventory = new ArrayList<String>();
    
    public Inventory() {
        // empty for now
    }
    
    public String saveInventory() { // save inventory
    	String inventoryString = "";
    	int i = 0;
    	for(i = 0; i < inventory.size(); i++) {
            inventoryString = inventoryString + inventory.get(i) + ", ";
        }
        return inventoryString;
    }
    
    public void loadInventory(String[] savedInventory) { // load inventory from save
    	inventory.clear();
    	for (int i = 0; i < savedInventory.length; i++) {
    		inventory.add(savedInventory[i]);
    	}
    }
    
    public void getInventory() {
    	for(int i = 0; i < inventory.size(); i++) {
            if (inventory.get(i) != null)
            	System.out.print(inventory.get(i) + "  ");
        }
        System.out.println();
    }

    public void addToInventory(String item) {
    	if (item != null)
    		inventory.add(item);
    	else
    		throw new IllegalStateException("Item doesn't exist!");
    }
    
    public boolean isEmpty() {
    	return inventory.isEmpty();
    }
}
