package com.bayviewglen.zork;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Scanner;

/** "Player" Class - generates and controls all of the player's information.
 * 
 *  Authors: 		Kirill Tregubov, Zacharia Burrafato, Andrew Douglas, Alim Halani
 *  Code Version:	0.3-alpha
 *  Published Date:	May 2018
 */

public class Player extends Entity {

	private Integer money;
	private Room currentRoom;
	public Inventory inventory;
	public HashMap<String, Room> masterRoomMap;
	private Item equippedWeapon;
	private Item equippedArmor;

	Player() {
		super("Player", Stats.PLAYER_INDEX, "1,0,0,10,10,2,0,1,0.9,0.1");
		money = 0;
		inventory = new Inventory();
		inventory.forceAdd(Item.getItem(SPLINTERED_BRANCH));
		inventory.forceAdd(Item.getItem(CARDBOARD_ARMOR));
		equippedWeapon = Item.getItem(SPLINTERED_BRANCH);
		equippedArmor = Item.getItem(CARDBOARD_ARMOR);
	}

	public static Item items[] = {
			// Weapons - Common
			new Item("Splintered Branch", "A branch that hurts...a litle.", Item.WEAPON_INDEX, "2,0.0"),
			new Item("Wooden Rapier", "A wooden sword with a cool name.", Item.WEAPON_INDEX, "3,0.0"),
			new Item("Copper Katana", "A copper katana wielded by the samurai.", Item.WEAPON_INDEX, "4,0.0"),
			new Item("Silver Longsword", "A sword of silver that is very long.", Item.WEAPON_INDEX, "5,0.0"),

			// Weapons - Rare
			new Item("Steel Dagger", "A dagger crafted of the finest steel.", Item.WEAPON_INDEX, "7,0.0"),
			new Item("Cobalt Broadsword", "A large attack sword crafted from cobalt.", Item.WEAPON_INDEX, "8,0.01"),
			new Item("Iron Spear", "A powerful iron spear that can pack a punch.", Item.WEAPON_INDEX, "5,0.05"),
			new Item("Staff of Draining", "A magic staff crafted by the angels. It is said to steal the life force from any enemy it faces", Item.WEAPON_INDEX, "6,0.0,0.25", new int[]{ Stats.DMG_REFLECT_INDEX } ),
			new Item("Golden Bamboo Sword", "A powerful sword crafted with the only golden bamboo wood on the planet.", Item.WEAPON_INDEX, "8,0.03"),

			//Weapons - Epic
			new Item("Titanium Scythe", "A scythe said to be wielded by the grim reaper himself.", Item.WEAPON_INDEX, "10,0.02"),
			new Item("Gauntlet of Terror", "A gaunlet that strikes fear into the enemies it faces.", Item.WEAPON_INDEX, "12,0.02"),
			new Item("Donkey Kong Hammer", "The hammer that Mario himself used to defeat Donkey Kong.", Item.WEAPON_INDEX, "15,0.03"), 
			new Item("The Vile Blade", "It's a vile sword? It's cool don't worry.", Item.WEAPON_INDEX, "20,0.01"), 

			//Weapons - Legendary
			new Item("Genji's Dragonblade", "Ryujin no ken wo kurae!", Item.WEAPON_INDEX, "20,0.0,0.25", new int[]{ Stats.DMG_REFLECT_INDEX }),
			new Item("Gandalf's Staff", "You shall not pass!", Item.WEAPON_INDEX, "20,0.0"),
			new Item("Enchanted Sabre", "A sword crafted by God himself. So sharp, the edge of it's blade is a single atom thick.", Item.WEAPON_INDEX, "30,0.05,0.33", new int[]{ Stats.DMG_REFLECT_INDEX }),
			new Item("Infinity Gauntlet", "The Infinity stones are not included.", Item.WEAPON_INDEX, "45,0.1,0.5", new int[]{ Stats.DMG_REFLECT_INDEX }),
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
			new Item("Dark Angel Armor", "A suit of armor taken from the dark angels.", Item.ARMOR_INDEX, "15,0.33", new int[]{ Stats.DMG_REFLECT_INDEX }),
			new Item("Enchanted Steel Armor", "A suit crafted from vibranium that has been enchanted with deflection properties.", Item.ARMOR_INDEX, "20,0.33", new int[]{ Stats.DMG_REFLECT_INDEX }),

			//Healing Items / Other Consumables - MSG: ADD ATK+ FOR COMBAT POTION
			new Item("Small Heal Potion", "A magical potion that restores 5 health.", Item.CONSUMABLE_INDEX, "5"),
			new Item("Medium Heal Potion", "A magical potion that restores 20 health.", Item.CONSUMABLE_INDEX, "20"),
			new Item("Large Heal Potion", "A magical potion that restores 50 health.", Item.CONSUMABLE_INDEX, "50"),
			new Item("Skyfire Root", "A root that grows inside volcanos. It restores you to full health.", Item.CONSUMABLE_INDEX, "999"),
			new Item("Combat Potion", "Military grade potion that heals you to full health and boost attack power for 1 battle.", Item.CONSUMABLE_INDEX, "999"),

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
	 * Item & Inventory Methods
	 */
	public Integer getMoney() {
		return money;
	}

	public void loadMoney(Integer money) {
		this.money = money;
	}

	public String getMoneyString() {
		return "You have $" + money + " left in your wallet.";
	}

	public void addMoney(Integer moneyAmount) {
		money += moneyAmount;
	}

	public boolean subtractMoney(Integer moneyAmount) {
		if (money - moneyAmount > 0) {
			money -= moneyAmount;
			return true;
		}
		else return false;
	}
	
	public Item getEquippedWeapon() {
		return equippedWeapon;
	}
	
	public Item getEquippedArmor() {
		return equippedArmor;
	}

	public String checkEquippedItems() {
		return "Equipped Weapon: " + equippedWeapon + "\nEquipped Armor: " + equippedArmor;
	}

	public void setEquippedWeapon(Item equippedWeapon) {
		this.equippedWeapon = equippedWeapon;
	}

	public void setEquippedArmor(Item equippedArmor) {
		this.equippedArmor = equippedArmor;
	}

	public boolean didPickUpItem(String itemName, String roomID) {
		if (inventory.hasItem(itemName) && inventory.getItem(itemName).roomID.contains(roomID)) return true;
		return false;
	}

	public String pickUpItem(String itemName, String roomID) { // add fix for consumables
		Item inputItem;
		try {
			inputItem = new Item(Item.getItem(itemName));

			if (1 > currentRoom.getItem(itemName).getAmount())
				return "toomuch";
			else if (inventory.hasItem(itemName))
				inputItem.setAmount(1 + inventory.getItem(itemName).getAmount());
			else
				if (inputItem.isStackable) inputItem.setAmount(1); // change so that user can specify how many to pick up*/

			if (!inputItem.roomID.contains(roomID)) {
				inputItem.roomID.add(roomID);
				inputItem.pickedUpAmounts.add(1);
			} else {
				inputItem.pickedUpAmounts.set(inputItem.roomID.indexOf(roomID), inputItem.pickedUpAmounts.get(inputItem.roomID.indexOf(roomID))+1); //(inputItem.roomID.indexOf(roomID), 1);
			}

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

			if (inventory.hasItem(itemName) && inventory.getItem(itemName).getAmount() < currentRoom.getItem(itemName).getAmount())
				inputItem.setAmount(amount + inventory.getItem(itemName).getAmount());
			else if (inventory.hasItem(itemName) && amount + inventory.getItem(itemName).getAmount() >= currentRoom.getItem(itemName).getAmount())
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
		else if (!currentRoom.hasItem(inputItem)) return "roomnotcontains";
		else if (inventory.hasItem(itemName) && !Item.getItem(itemName).isStackable) return "inventorycontains";
		else return "";
	}

	public String itemCanBeLookedAt(String itemName) {
		//Item inputItem = Item.getItem(itemName);
		if (currentRoom.hasRepeatedItems(itemName)) return "roomrepeated";
		else if (inventory.hasItem(itemName) || currentRoom.hasItem(Item.getItem(itemName))) return "contains";
		return "";
	}

	public boolean consumeItem(String itemName) {
		if (inventory.hasItem(itemName)) {
			//System.out.println(inventory.getItem(itemName) + " " + inventory.getItemAmount(itemName));
			inventory.consumeItem(itemName);
			int healingDone = Item.getItem(itemName).stats.getHealPoints();
			if (healingDone == 999 || stats.getCurrentHP() + healingDone > stats.getMaximumHP()) stats.setCurrentHP(stats.getMaximumHP());
			else stats.setCurrentHP(stats.getCurrentHP() + Item.getItem(itemName).stats.getHealPoints());
			return true;
		}
		return false;
	}

	/*
	 * Room Getters
	 */
	/*public Room getRoom() {
		return currentRoom;
	}*/

	public String getRoomTrial() {
		return currentRoom.getTrial();
	}

	public boolean doesRoomHaveTrial() {
		return currentRoom.getTrial() != null;
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

	public boolean getRoomHasItem(String itemName) {
		return currentRoom.hasItem(Item.getItem(itemName));
	}
	
	public ArrayList<Entity> getRoomNPCs() {
		return currentRoom.getRoomNPCs();
	}

	public ArrayList<Entity> getRoomEnemies() {
		return currentRoom.getRoomEnemies();
	}

	public ArrayList<Entity> getRoomBosses() {
		return currentRoom.getRoomBosses();
	}
	
	public boolean getRoomHasNPCs() {
		return currentRoom.hasNPCs();
	}

	public boolean getRoomHasEnemies() {
		return currentRoom.hasEnemies();
	}

	public boolean getRoomHasRepeatedEnemies(String enemyName) {
		return currentRoom.hasRepeatedEnemies(enemyName);
	}

	public boolean getRoomHasBosses() {
		return currentRoom.hasBosses();
	}

	public Entity getRoomFindEnemy(String enemyName) {
		return currentRoom.findEnemy(this, enemyName);
	}
	
	public Entity getRoomFindNPC(String npcName) {
		return currentRoom.findNPC(this, npcName);
	}

	public int getRoomStartBattle(String enemyName) {
		return currentRoom.startCustomBattle(this, enemyName);
	}

	public Entity getRoomEnemy(String enemyName) {
		return currentRoom.findEnemy(this, enemyName);
	}

	public void getRoomAddEntity(Entity entity) {
		currentRoom.addEntity(entity);
	}

	public void getRoomResetEntities() {
		currentRoom.resetEntities();
	}

	public boolean doesRoomContainItem(String itemName) {
		Item inputItem = Item.getItem(itemName);
		return currentRoom.hasItem(inputItem);
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
		this.currentRoom = masterRoomMap.get("0-0");
	}

	public void expCalculator(int battleResult, ArrayList<Integer> counters, int enemyType, Drops drops) {
		int expGained = 0;
		int moneyGained = 0;
		int attackCounter = counters.get(0) - counters.get(2);
		int consumableUsageCounter = counters.get(1);
		int critHitCounter = counters.get(2);

		/*for (Integer counter : counters) { //test
			System.out.println(counter);
		}*/

		// Base Exp Calculator
		expGained += attackCounter * 5 + consumableUsageCounter * 10 + critHitCounter * 15;
		moneyGained += attackCounter * 5 + critHitCounter * 10; 

		// Battle Result Checker
		if (battleResult == 2) {
			expGained /= 2;
			moneyGained = 0;
		} else if (battleResult == 1) {
			moneyGained += 20;
		}

		// Type Checker
		if (enemyType == Entity.BOSS_INDEX) {
			expGained *= 2;
			moneyGained *= 2;
		}

		stats.setExp(stats.getExp() + expGained);
		System.out.print("earned $" + moneyGained + ", gained " + expGained + " exp");
		
		addMoney(moneyGained);
		levelUp();
		
		if (battleResult == 1) {
			System.out.println(drops.calculate(this));
		}
	}

	public int nextLevelExp() {
		int expNeeded = 20;

		for (int i = 1; i < stats.getLevel(); i++) {
			expNeeded *= 1.5;
		}

		return expNeeded;
	}

	public void levelUp() {
		/*int baseExpNeeded = 20;

		//Create exp variable to account for level
		int expNeeded = baseExpNeeded * stats.getLevel();*/

		if (stats.getLevel() >= 99 || stats.getExp() < nextLevelExp()) System.out.println(".");
		else {
			while (stats.getExp() > nextLevelExp()) {
				stats.setExp(stats.getExp() - nextLevelExp());
				stats.setLevel(stats.getLevel() + 1);
				if (stats.getLevel() < 11) 
					stats.setAttributePoints(stats.getAttributePoints() + 1);
				else if (stats.getLevel() < 21)
					stats.setAttributePoints(stats.getAttributePoints() + 2);
				else if (stats.getLevel() < 31)
					stats.setAttributePoints(stats.getAttributePoints() + 3);
				else if (stats.getLevel() < 41)
					stats.setAttributePoints(stats.getAttributePoints() + 4);
				else
					stats.setAttributePoints(stats.getAttributePoints() + 5);
			}
			System.out.println(", levelling up to Lvl " + stats.getLevel() + "!"); // gained AP
			System.out.println("You have " + stats.getAttributePoints() + " attribute points, would you like to spend them?");
			Scanner input = new Scanner(System.in);
			String answer = input.nextLine().toLowerCase();
			if (answer.equals("yes")||answer.equals("sure")||answer.equals("ok")||answer.equals("fine")||answer.equals("sure thing")||answer.equals("why not")||answer.equals("why not?")||answer.equals("accept")||answer.equals("y"))
				attributeSpender();
			System.out.println("");
		}
	}	

	public void attributeSpender() {
		System.out.println("\n\nWelcome to the Attribute spending screen.\nIf you would like an explanation on how to spend points, say \"Spend Help\".");
		Scanner input = new Scanner(System.in);
		int x = 0;
		while (stats.getAttributePoints() > x) {
			System.out.print("Command: ");
			String command = input.nextLine().toLowerCase();
			if (command.equals("spendhelp")||command.equals("spend help")||command.equals("help")||command.equals("help me")||command.equals("what do i do")||command.equals("what do i do?")) {
				System.out.println("\nEnter one of these values into the console to specify which stat to increase:\n"
						+ "1: Increase health by 1\n2: Increase attack by 1\n3: Increase defence by 1\n4: Increase speed by 1\n5: Increase accuracy by 1%\n6: Increase critical hit chance by 1%\n\"exit\"");
			}
			else if (command.equals("1")||command.equals("2")||command.equals("3")||command.equals("4")||command.equals("5")||command.equals("6")) {
				switch (command) {

				case "1": // Health
					if (stats.getMaximumHP()>=150) {
						System.out.println("Health is at maximum.");
					}
					else {
						stats.setAttributePoints(stats.getAttributePoints()-1);
						stats.setMaximumHP(stats.getMaximumHP()+1);
						stats.setCurrentHP(stats.getMaximumHP());
						System.out.println("Health increased by 1, total: "+stats.getMaximumHP()+".");
					}
					break;
				case "2": // Attack
					if (stats.getAttack()>=40) { 
						System.out.println("Attack is at maximum.");
					}
					else {
						stats.setAttributePoints(stats.getAttributePoints()-1);
						stats.setAttack(stats.getAttack()+1);
						System.out.println("Attack increased by 1, total: "+stats.getAttack()+".");
					}
					break;
				case "3": // Defense
					if (stats.getDefense()>=40) { 
						System.out.println("Defence is at maximum.");
					}
					else {
						stats.setAttributePoints(stats.getAttributePoints()-1);
						stats.setDefense(stats.getDefense()+1);
						System.out.println("Defence increased by 1, total: "+stats.getDefense()+".");
					}
					break;
				case "4": // Speed
					if (stats.getSpeed()>=40) { 
						System.out.println("Speed is at maximum.");
					}
					else {
						stats.setAttributePoints(stats.getAttributePoints()-1);
						stats.setSpeed(stats.getSpeed()+1);
						System.out.println("Speed increased by 1, total: "+stats.getSpeed()+".");
					}
					break;
				case "5": // Accuracy
					if (stats.getAccuracy()>=1.0) { 
						System.out.println("Accuracy is at maximum.");
					}
					else {
						stats.setAttributePoints(stats.getAttributePoints()-1);
						stats.setAccuracy(stats.getAccuracy()+0.01);
						System.out.println("Accuracy increased by 1%, total: "+stats.getAccuracy()*100+"%.");
					}
					break;
				case "6": // Critical chance
					if (stats.getCriticalChance()>=0.6) { 
						System.out.println("Critical hit chance is at maximum.");
					}
					else {
						stats.setAttributePoints(stats.getAttributePoints()-1);
						stats.setCriticalChance(stats.getCriticalChance()+0.01);
						System.out.println("Critical hit chance increased by 1%, total: "+stats.getAttack()*100+"%.");
					}
					break;

				}
			}
			else if (command.equals("quit")||command.equals("stop")||command.equals("end"))
				x++;
			else {
				System.out.print("Not a valid option.");
			}
		}
	}


	public Inventory getInventory() {
		return inventory;
	}
}
