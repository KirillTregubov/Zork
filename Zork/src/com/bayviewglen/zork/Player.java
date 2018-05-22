package com.bayviewglen.zork;

import java.util.Arrays;
import java.util.HashMap;

/** "Player" Class - a class that creates a player and stores their data.
 * 
 * Current Authors: Kirill Tregubov, Zacharia Burrafato, Andrew Douglas, Alim Halani
 * Current Version: 0.2-alpha
 * Current Date:    April 2018.
 */

public class Player extends Entity {

	//public String name;
	//public Stats stats;
	private Room currentRoom;
	public Inventory inventory;
	public HashMap<String, Room> masterRoomMap;


	Player() {
		super("Player", Stats.PLAYER_INDEX, "1,1,1,999,120,120,120,20,0.8,0.1");
		inventory = new Inventory();
		//stats = new Stats(Stats.ENTITY_INDEX, Stats.PLAYER_INDEX, "1,0,0,20,20,2,2,2,0.5,0.1");	
	}

	public static Item items[] = {//};
			// Weapons - Common
			new Item("Splintered Branch", "A branch that hurts...a litle.", Item.WEAPON_INDEX, "2,0.0"),
			new Item("Wooden Rapier", "A wooden sword with a cool name.", Item.WEAPON_INDEX, "3,0.0"),
			new Item("Copper Katana", "A copper katana wielded by the samurai.", Item.WEAPON_INDEX, "4,0.0"),
			new Item("Silver Longsword", "A sword of silver that is very long.", Item.WEAPON_INDEX, "5,0.0"),

			// Weapons - Rare
			new Item("Steel Dagger", "A dagger crafted of the finest steel.", Item.WEAPON_INDEX, "7,0.0"),
			new Item("Cobalt Broadsword", "A large attack sword crafted from cobalt.", Item.WEAPON_INDEX, "8,1.0"),
			new Item("Iron Spear", "A powerful iron spear that can pack a punch.", Item.WEAPON_INDEX, "5,5.0"),
			new Item("Staff of Draining", "A magic staff crafted by the angels. It is said to steal the life force from any enemy it faces", Item.WEAPON_INDEX, "6,0.0,0.25", new int[]{ Stats.DMG_REFLECT_INDEX } ),
			new Item("Golden Bamboo Sword", "A powerful sword crafted with the only golden bamboo wood on the planet.", Item.WEAPON_INDEX, "8,3.0"),

			//Weapons - Epic
			new Item("Titanium Scythe", "A scythe said to be wielded by the grim reaper himself.", Item.WEAPON_INDEX, "10,2.0"),
			new Item("Gauntlet of Terror", "A gaunlet that strikes fear into the enemies it faces.", Item.WEAPON_INDEX, "12,2.0"),
			new Item("Donkey Kong Hammer", "The hammer that Mario himself used to defeat Donkey Kong.", Item.WEAPON_INDEX, "15,3.0"), 
			new Item("The Vile Blade", "It's a vile sword? It's cool don't worry.", Item.WEAPON_INDEX, "20,1.0"), 

			//Weapons - Legendary MSG: IMPLEMENT DAMAGE REFLECT AND DOUBLE ATTACK
			new Item("Genji's Dragonblade", "Ryujin no ken wo kurae!", Item.WEAPON_INDEX, "20,0.0,0.25", new int[]{ Stats.DMG_REFLECT_INDEX }),
			new Item("Gandalf's Staff", "You shall not pass!", Item.WEAPON_INDEX, "20,0.0"),
			new Item("Enchanted Sabre", "A sword crafted by God himself. So sharp, the edge of it's blade is a single atom thick.", Item.WEAPON_INDEX, "30,5.0,0.33", new int[]{ Stats.DMG_REFLECT_INDEX }),
			new Item("Infinity Gauntlet", "The Infinity stones are not included.", Item.WEAPON_INDEX, "45,10.0,0.5", new int[]{ Stats.DMG_REFLECT_INDEX }),
			new Item("Developer Sword", "A sword Retrieved from the very heart of the syntax. It seems to have markings on the side written in binary.", Item.WEAPON_INDEX, "10000,0.0"),

			//Armor - Normal 
			new Item("Cardboard Armor", "A suit made of cardboard that your mom gave you for you're birthday.", Item.ARMOR_INDEX, "0"),
			new Item("Leather Suit", "A lovely leather suit to protect you from scratches.", Item.ARMOR_INDEX, "1"),
			new Item("Knight Armor", "A well-crafted suit of armor from the times of King Arthur.", Item.ARMOR_INDEX, "3"),
			new Item("Titanium Blast Plate Armor", "Military grade armor used to protect space shuttles.", Item.ARMOR_INDEX, "5"),
			new Item("Vibranium Heavy Armor", "A suit of armor crafted from the strongest metal in the world.", Item.ARMOR_INDEX, "7"),
			new Item("Electromagnetic Shield Generator", "An electromagnetic barrier that deflects attacks away.", Item.ARMOR_INDEX, "10"),

			//Armor - Enchanted - MSG: IMPLEMENT DAMAGE REFLECT
			new Item("Enchanted Steel Armor", "Magical steel Armor that deflects damage.", Item.ARMOR_INDEX, "7,0.25", new int[]{ Stats.DMG_REFLECT_INDEX }),
			new Item("Enchanted Force Field", "A powerful force field that blocks and deflects damage.", Item.ARMOR_INDEX, "10,0.25", new int[]{ Stats.DMG_REFLECT_INDEX }),
			new Item("Skynight Armor", "An armor suit forged in the heart of the skynight forgery.", Item.ARMOR_INDEX, "12,0.33", new int[]{ Stats.DMG_REFLECT_INDEX }),
			new Item("Enchanted Steel Armor", "A suit of armor taken from the dark angels.", Item.ARMOR_INDEX, "15,0.33", new int[]{ Stats.DMG_REFLECT_INDEX }),
			new Item("Enchanted Steel Armor", "A suit crafted from vibranium that has been enchanted with deflection properties.", Item.ARMOR_INDEX, "20,0.33", new int[]{ Stats.DMG_REFLECT_INDEX }),

			//Healing Items / Other Consumables - MSG: ADD ATK+ FOR COMBAT POTION
			new Item("Small Heal Potion", "A magical potion that restores 5 health.", Item.CONSUMABLE_INDEX, "5"),
			new Item("Medium Heal Potion", "A magical potion that restores 20 health.", Item.CONSUMABLE_INDEX, "20"),
			new Item("Large Heal Potion", "A magical potion that restores 50 health.", Item.CONSUMABLE_INDEX, "50"),
			new Item("Skyfire Root", "A root that grows inside volcanos. It restores you to full health.", Item.CONSUMABLE_INDEX, "10000"),
			new Item("Combat Potion", "Military grade potion that heals you to full health and boost attack power for 1 battle.", Item.CONSUMABLE_INDEX, "10000"),

			// Item Variables
			new Item("First Item", "This is a test item 1."),
			new Item("Second Item", "This is a test item 1."),
			new Item("Item Three", "This is a test item 1."),
			new Item("Item Four", "This is a test item 1."),
			new Item("Item Five", "This is a test item 1."),
			new Item("Red Apple", "Apple", Item.CONSUMABLE_INDEX, "5"),
			new Item("Basic Sword", "This is a test item 1.", Item.WEAPON_INDEX, "10,0.5"),
			new Item("Unique Test", "This is unique!", Item.WEAPON_INDEX, "20,0.0,0.25", new int[]{ Stats.DMG_REFLECT_INDEX }),
	};

	// Indexes of Items
	public final static int SPLINTERED_BRANCH = 0;
	public final static int WOODEN_RAPIER = 1;
	public final static int COPPER_KATANA = 2;
	public final static int SILVER_LONGSWORD = 3;
	public final static int STEEL_DAGGER = 4;
	public final static int COBALT_BROADSWORD = 5;
	public final static int IRON_SPEAR = 6;
	public final static int LIFE_DRAIN_STAFF = 7;
	public final static int GOLDEN_BAMBOO_SWORD = 8;
	public final static int TITANIUM_SCYTHE = 9;
	public final static int TERROR_GAUNTLET = 10;
	public final static int KONG_HAMMER = 11;
	public final static int VILE_BLADE = 12;
	public final static int GENJI_DRAGONBLADE = 13;
	public final static int GANDALF_STAFF = 14;
	public final static int ENCHANTED_SABRE = 15;
	public final static int INFINITY_GAUNTLET = 16;
	public final static int DEVELOPER_SWORD = 17;
	public final static int CARDBOARD_ARMOR = 18;
	public final static int LEATHER_SUIT = 19;
	public final static int KNIGHT_ARMOR = 20;
	public final static int TITANIUM_ARMOR = 21;
	public final static int VIBRANIUM_ARMOR = 22;
	public final static int ELECTROMAGNETIC_SHIELD = 23;
	public final static int ENCHANTED_STEEL_ARMOR = 24;
	public final static int ENCHANTED_FORCE_FIELD = 25;
	public final static int SKYNIGHT_ARMOR = 26;
	public final static int DARK_ANGEL_ARMOR = 27;
	public final static int VIBRANIUM_KINETIC_SUIT = 28;
	public final static int SMALL_HEAL = 29;
	public final static int MEDIUM_HEAL = 30;
	public final static int LARGE_HEAL = 31;
	public final static int SKYFIRE_ROOT = 32;
	public final static int COMBAT_POTION = 33;

	public String teleport(String roomName) {
		Room roomTemp = masterRoomMap.get(roomName);
		try {
			if (roomTemp != null) return roomName;
		} catch (Exception e) { }
		return null;
	}

	/*
	 * Inventory Methods
	 */

	public boolean didPickUpItem(String itemName, String roomID) {
		if (inventory.containsItem(itemName) && inventory.getItem(itemName).roomID.contains(roomID)) return true;
		return false;
	}

	public String pickUpItem(String itemName, String roomID) { // add fix for consumables
		Item inputItem;
		try {
			inputItem = new Item(Item.getItem(itemName));

			if (1 > currentRoom.getItem(itemName).getAmount())
				return "toomuch";
			else if (inventory.containsItem(itemName))
				inputItem.setAmount(1 + inventory.getItem(itemName).getAmount());
			else
				if (inputItem.isStackable) inputItem.setAmount(1); // change so that user can specify how many to pick up*/

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

			if (inventory.containsItem(itemName) && inventory.getItem(itemName).getAmount() < currentRoom.getItem(itemName).getAmount())
				inputItem.setAmount(amount + inventory.getItem(itemName).getAmount());
			else if (inventory.containsItem(itemName) && amount + inventory.getItem(itemName).getAmount() >= currentRoom.getItem(itemName).getAmount())
				return "toomuch";
			else if (amount > currentRoom.getItem(itemName).getAmount()) 
				return "toomuch";
			else
				if (inputItem.isStackable) inputItem.setAmount(amount); // change so that user can specify how many to pick up

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
		else if (inventory.containsItem(itemName) || currentRoom.containsItem(Item.getItem(itemName))) return "contains";
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
	public void setCurrentRoom(Room currentRoom) {
		this.currentRoom = currentRoom;
		//updateItems(player);
	}
	
	public void setDefaultRoom() {
		this.currentRoom = masterRoomMap.get("0");
	}
	
	public int nextLvlExp() {
		return (int) ((0.5*(stats.getLevel()+1)*(stats.getLevel()+1))*100 - (0.5*stats.getLevel()*stats.getLevel())*100);
	}
	
	public void expCalculator(int opLevel,int type) {
		int expGained = 0;
		
		if (opLevel<=stats.getLevel()) {
			expGained = (int) (nextLvlExp()*(1/(3.0+stats.getLevel()-opLevel)));
		}
		else {
			int expGainEqual = (int) (nextLvlExp()*(1/(3.0)));
			expGained = (int) (expGainEqual*Math.pow(1.25,opLevel-stats.getLevel()));
		}
		
		if (type==NPC.TYPE_BOSS) {
			expGained *= 2;
		}
		
		stats.setExp(stats.getExp()+expGained);
		levelUp();
		
	}
	
	public void levelUp() {
		
		while(stats.getExp()>nextLvlExp()) {
			stats.setExp(stats.getExp()-nextLvlExp());
			stats.setLevel(stats.getLevel()+1);
			stats.setAttributePoints(stats.getAttributePoints()+8);
		}
		
	}

}
