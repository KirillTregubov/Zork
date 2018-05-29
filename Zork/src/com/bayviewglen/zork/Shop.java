package com.bayviewglen.zork;

import java.util.ArrayList;

public class Shop {
	/* Shop Functionality
	 * - assigns items to shop
	 * - assigns price to an item
	 * - allows players to purchase items from the shop
	 */
	int money = 0; //money has not been implemented yet, using this variable for testing
	private ArrayList<Item> shopItems;
	private ArrayList<Integer> shopItemPrices;
	Player player;

	public Shop (Player player) {
		this.player = player;
		this.shopItems = new ArrayList<Item>();
		this.shopItemPrices = new ArrayList<Integer>();
	}

	public void addItem (Item item, int price) {
		shopItems.add(item);
		shopItemPrices.add(price);
	}

	public void removeItem (Item item, int price) {
		shopItems.remove(item);
		shopItemPrices.remove(price);
	}

	public void setPrice (Item item, int price) {
		shopItemPrices.set(shopItems.indexOf(item), price);
	}

	public int getPrice (Item item) {
		int price = shopItemPrices.get(shopItems.indexOf(item));
		return price;
	}

	public void displayItems () {
		for (int i = 0; i <= shopItems.size(); i++) {
			System.out.println("|| Item Name: " + shopItems.get(i) + "Price: " + shopItemPrices.get(i) + " ||");
		}
	}

	public void purchaseNonconsumable (String itemName) {
		Item inputItem = Item.getItem(itemName);
		if (money < shopItemPrices.get(shopItems.indexOf(inputItem)))
			System.out.println("You do not have enough money to purchase this item.");
		else if (player.inventory.containsItem(inputItem))
			System.out.print("You already own this item.");
		else {
			money = money - shopItemPrices.get(shopItems.indexOf(inputItem));
			shopItems.remove(inputItem);
			shopItemPrices.remove(shopItems.indexOf(inputItem));
			// assign to inventory
		}
	}
	
	public void purchaseConsumable (String itemName) {
		Item inputItem = Item.getItem(itemName);
		if (money < shopItemPrices.get(shopItems.indexOf(inputItem)))
			System.out.println("You do not have enough money to purchase this item.");
		else if (player.inventory.containsItem(inputItem))
			System.out.print("You already own this item.");
		else {
			money = money - shopItemPrices.get(shopItems.indexOf(inputItem));
			shopItems.remove(inputItem);
			shopItemPrices.remove(shopItems.indexOf(inputItem));
			// assign to inventory with stack-able possibility
		}
	}
}