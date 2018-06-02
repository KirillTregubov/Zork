package com.bayviewglen.zork;

import java.util.ArrayList;
import java.util.Arrays;

public class Shop {
	/* Shop Functionality
	 * - assigns items to shop
	 * - assigns price to an item
	 * - allows players to purchase items from the shop
	 */
	private ArrayList<Item> shopItems;
	private ArrayList<Integer> shopItemPrices;
	Player player;

	public Shop (Player player) {
		this.player = player;
		this.shopItems = new ArrayList<Item>(Arrays.asList(
				// Weapons
				Item.getItem(Player.SILVER_LONGSWORD), Item.getItem(Player.STEEL_DAGGER), Item.getItem(Player.IRON_SPEAR), Item.getItem(Player.VILE_BLADE), Item.getItem(Player.ENCHANTED_SABRE),
				// Armor
				Item.getItem(Player.ENCHANTED_STEEL_ARMOR), Item.getItem(Player.ENCHANTED_FORCE_FIELD), Item.getItem(Player.VIBRANIUM_KINETIC_SUIT),
				// Healing
				Item.getItem(Player.SMALL_HEAL), Item.getItem(Player.MEDIUM_HEAL), Item.getItem(Player.LARGE_HEAL), Item.getItem(Player.SKYFIRE_ROOT),Item.getItem(Player.COMBAT_POTION)));
		this.shopItemPrices = new ArrayList<Integer>(Arrays.asList(250, 300, 400, 700, 1200, 400, 600, 2000, 30, 100, 200, 300, 500));
	}
	
	public ArrayList<Item> getItems () {
		return shopItems;
	}

	public void addItem (Item item, int price) {
		shopItems.add(item);
		shopItemPrices.add(price);
	}

	public void removeItem (String itemName) {
		for (Item item : shopItems) {
			if (Utils.containsIgnoreCase(item.toString(), itemName)) {
				shopItemPrices.remove(shopItems.indexOf(item));
				shopItems.remove(shopItems.indexOf(item));
				return;
			}	
		}
	}

	public void setPrice (Item item, int price) {
		shopItemPrices.set(shopItems.indexOf(item), price);
	}

	public int getPrice (String itemName) {
		for (Item item : shopItems) {
			if (Utils.containsIgnoreCase(item.toString(), itemName))
				return shopItemPrices.get(shopItems.indexOf(item));
		}
		return -1;
	}

	public String displayItems () {
		String returnString = "";
		for (int i = 0; i < shopItems.size(); i++) {
			returnString += "\n|| Buy " + shopItems.get(i) + " for $" + shopItemPrices.get(i);
		}
		return returnString;
	}

	public void purchaseItem (String itemName) {
		Item inputItem = Item.getItem(itemName);

		if (player.getMoney() < shopItemPrices.get(shopItems.indexOf(inputItem)))
			System.out.println("You do not have enough money to purchase this item.");
		else if (player.inventory.hasItem(inputItem) && !inputItem.isStackable)
			System.out.println("You already own this item.");
		else {
			int cost = shopItemPrices.get(shopItems.indexOf(inputItem));
			player.subtractMoney(cost);
			//removeItem(itemName);
			System.out.println("You successfully bought " + Item.getItem(itemName) + " for $" + cost + ".");
			System.out.println(player.getMoneyString());
			
			player.inventory.forceAdd(itemName);
		}
	}
}