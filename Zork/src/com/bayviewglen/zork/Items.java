package com.bayviewglen.zork;

/** "Inventory" Class - a class that stores and manages the inventory of the player.
 * Current Authors: Kirill Tregubov, Zacharia Burrafato, Andrew Douglas, Alim Halani
 * Current Version: 0.1-alpha
 * Current Date:    March 2018
 */

class Items {
	// an array that holds all valid command words
    private static final String items[] = {
        "Item 1", "Item 2", "Item 3", "Item 4", "Item 5"
    };
    
    public Items() {
        // empty for now
    }
    
    public static void listItems() {			// List all items in the game
        for(int i = 0; i < items.length; i++) {
            System.out.print(items[i] + "  ");
        }
        System.out.println();
    }
    
    public static String getItem(int index) {	// Gets item at specific index
    	if (index < items.length)
    		return items[index];
    	else
    		return null;
    }
}
