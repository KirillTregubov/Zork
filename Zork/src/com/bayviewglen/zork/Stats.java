package com.bayviewglen.zork;

import java.util.Arrays;

/** "Stats" Class - a class that creates and stores stats of everything in the game.
 * 
 * Current Authors: Kirill Tregubov, Zacharia Burrafato, Andrew Douglas, Alim Halani
 * Current Version: 0.2-alpha
 * Current Date:    April 2018
 */

public class Stats {
	// Object Stats
	public double[] stats;
	// Indexes of Stats
	private Integer lvlIndex;
	private Integer expIndex;
	private Integer apIndex;
	private Integer currHPIndex;
	private Integer maxHPIndex;
	private Integer atkIndex;
	private Integer defIndex;
	private Integer speedIndex;
	private Integer accuracyIndex;
	private Integer critIndex;
	private Integer healPointsIndex;
	private Integer lifeStealIndex;
	private Integer dmgReflectIndex;
	// Object Variables
	public String[] statNames;
	public String type; // is this needed?
	private int[] usedIndexes;
	private static final String TYPES[][] = {Item.TYPES, {"Player", "Enemy"}};
	// Indexes of Types
	public final static int ITEM_INDEX = 0;
	public final static int ENTITY_INDEX = 1;
	// Indexes of Sub-Types
	public final static int PLAYER_INDEX = 0;
	public final static int ENEMY_INDEX = 1;
	// Booleans
	public boolean isBlocking;

	// Default Variables
	public final static String[] STAT_NAMES = {"Level", "Experience Points", "Attribute Points", "Current HP", "Maximum HP", "Attack", "Defense",
			"Speed", "Accuracy", "Critical Hit Chance", "Heal Points", "Damage Reduction", "Life Steal", "Damage Reflection"};
	public final static int DEFAULT_STAT_VALUE = 10;
	public final static int NULL_STAT_VALUE = 10;
	public final static int NUM_STATS = STAT_NAMES.length;
	// Default Indexes of Stats
	private final static int DEFAULT_LVL_INDEX = 0;
	private final static int DEFAULT_EXP_INDEX = 1;
	private final static int DEFAULT_AP_INDEX = 2;
	private final static int DEFAULT_CURR_HP_INDEX = 3;
	private final static int DEFAULT_MAX_HP_INDEX = 4;
	private final static int DEFAULT_ATK_INDEX = 5;
	private final static int DEFAULT_DEF_INDEX = 6;
	private final static int DEFAULT_SPEED_INDEX = 7;
	private final static int DEFAULT_ACCURACY_INDEX = 8;
	private final static int DEFAULT_CRIT_INDEX = 9;
	private final static int DEFAULT_HEAL_POINTS_INDEX = 10;
	private final static int DEFAULT_LIFE_STEAL_INDEX = 12;
	private final static int DEFAULT_DMG_REFLECT_INDEX = 13;

	Stats(int type, int subtype, String input) {
		applyType(TYPES[type][subtype]);

		if (usedIndexes.length < 1) {
			return;
		}
		// Set Stat Names
		statNames = new String[usedIndexes.length];
		for (int i = 0; i < usedIndexes.length; i++) {
			statNames[i] = STAT_NAMES[usedIndexes[i]];
		}
		// Set Stat Values
		stats = new double[NUM_STATS];
		for (int i = 0; i < input.split(",", -1).length; i++) {
			stats[i] = Double.parseDouble(input.split(",", -1)[i]);
		}
	}

	// Unique Items
	Stats(int type, int subtype, String input, String uniqueIndexes) {
		applyUniqueType(TYPES[type][subtype], uniqueIndexes);

		// Set Stat Names
		statNames = new String[usedIndexes.length];
		for (int i = 0; i < usedIndexes.length; i++) {
			statNames[i] = STAT_NAMES[usedIndexes[i]];
		}
		// Set Stat Values
		stats = new double[NUM_STATS];
		for (int i = 0; i < input.split(",", -1).length; i++) {
			stats[i] = Double.parseDouble(input.split(",", -1)[i]);
		}

	}

	private void applyType(String type) {
		this.type = type;
		if (type == TYPES[ITEM_INDEX][Item.BASE_INDEX]) {
			usedIndexes = new int[0];
		} else if (type == TYPES[ITEM_INDEX][Item.CONSUMABLE_INDEX]) {
			usedIndexes = new int[]{DEFAULT_HEAL_POINTS_INDEX};
			healPointsIndex = 0;
		} else if (type == TYPES[ITEM_INDEX][Item.WEAPON_INDEX]) {
			usedIndexes = new int[]{DEFAULT_ATK_INDEX, DEFAULT_CRIT_INDEX};
			atkIndex = 0;
			critIndex = 1;
		} else if (type == TYPES[ITEM_INDEX][Item.ARMOR_INDEX] && type == TYPES[ITEM_INDEX][Item.SHIELD_INDEX]) {
			usedIndexes = new int[]{DEFAULT_DEF_INDEX};
			defIndex = 0;
		} else if (type == TYPES[ENTITY_INDEX][PLAYER_INDEX]) {
			usedIndexes = new int[]{DEFAULT_LVL_INDEX, DEFAULT_EXP_INDEX, DEFAULT_AP_INDEX, DEFAULT_CURR_HP_INDEX, DEFAULT_MAX_HP_INDEX,
					DEFAULT_ATK_INDEX, DEFAULT_DEF_INDEX, DEFAULT_SPEED_INDEX, DEFAULT_ACCURACY_INDEX, DEFAULT_CRIT_INDEX};
			lvlIndex = 0;
			expIndex = 1;
			apIndex = 2;
			currHPIndex = 3;
			maxHPIndex = 4;
			atkIndex = 5;
			defIndex = 6;
			speedIndex = 7;
			accuracyIndex = 8;
			critIndex = 9;
		} else if (type == TYPES[ENTITY_INDEX][ENEMY_INDEX]) {
			usedIndexes = new int[]{DEFAULT_LVL_INDEX, DEFAULT_AP_INDEX, DEFAULT_CURR_HP_INDEX, DEFAULT_MAX_HP_INDEX, DEFAULT_ATK_INDEX,
					DEFAULT_DEF_INDEX, DEFAULT_SPEED_INDEX, DEFAULT_ACCURACY_INDEX, DEFAULT_CRIT_INDEX};
			lvlIndex = 0;
			apIndex = 1;
			currHPIndex = 2;
			maxHPIndex = 3;
			atkIndex = 4;
			defIndex = 5;
			speedIndex = 6;
			accuracyIndex = 7;
			critIndex = 8;
		}
		if (usedIndexes.length > 1) {
			Arrays.sort(usedIndexes);
		}
	}

	// Unique Items
	private void applyUniqueType(String type, String uniqueIndexes) { // unfinished
		applyType(type);
		int indexAmount = uniqueIndexes.split(",", -1).length;
		System.out.println(indexAmount);
	}

	public void loadStats(String inputStats) {
		String[] inputArr = inputStats.split("/");

		int count = 0;
		for (int i = 0; i < inputArr.length; i++) {
			stats[usedIndexes[count]] = Double.parseDouble(inputArr[i]);
			count++;
		}
	}

	public String listStats() {
		String returnString = "";
		if (lvlIndex != null) returnString += getLevel() + "/";
		if (expIndex != null) returnString += getExp() + "/";
		if (apIndex != null) returnString += getAttributePoints() + "/";
		if (currHPIndex != null) returnString += getCurrentHP() + "/";
		if (maxHPIndex != null) returnString += getMaximumHP() + "/";
		if (atkIndex != null) returnString += getAttack() + "/";
		if (defIndex != null) returnString += getDefense() + "/";
		if (speedIndex != null) returnString += getSpeed() + "/";
		if (accuracyIndex != null) returnString += getAccuracy() + "/";
		if (critIndex != null) returnString += getCriticalChance() + "/";
		if (healPointsIndex != null) returnString += getHealPoints() + "/";
		if (lifeStealIndex != null) returnString += getLifeSteal() + "/";
		if (dmgReflectIndex != null) returnString += getDamageReflection() + "/";
		return returnString;
	}

	// Stats Getters

	public int getLevel() {
		return (int) stats[lvlIndex];
	}
	public int getExp() {
		return (int) stats[expIndex];
	}
	public int getAttributePoints() {
		return (int) stats[apIndex];
	}
	public int getCurrentHP() {
		return (int) stats[currHPIndex];
	}
	public int getMaximumHP() {
		return (int) stats[maxHPIndex];
	}
	public int getAttack() {
		return (int) stats[atkIndex];
	}
	public int getDefense() {
		return (int) stats[defIndex];
	}
	public int getSpeed() {
		return (int) stats[speedIndex];
	}
	public double getAccuracy() {
		return stats[accuracyIndex];
	}
	public double getCriticalChance() {
		return stats[critIndex];
	}
	public int getHealPoints() {
		return (int) stats[healPointsIndex];
	}
	public int getLifeSteal() {
		return (int) stats[lifeStealIndex];
	}
	public double getDamageReflection() {
		return stats[dmgReflectIndex];
	}

	// Stats Setters

	public void setLevel(int level) {
		stats[lvlIndex] = level;
	}
	public void setExp(int exp) {
		stats[expIndex] = exp;
	}
	public void setAttributePoints(int attributePoints) {
		stats[apIndex] = attributePoints;
	}
	public void setCurrentHP(int currHP) {
		stats[currHPIndex] = currHP;
	}
	public void setMaximumHP(int maxHP) {
		stats[maxHPIndex] = maxHP;
	}
	public void setAttack(int attack) {
		stats[atkIndex] = attack;
	}
	public void setDefense(int defense) {
		stats[defIndex] = defense;
	}
	public void setSpeed(int speed) {
		stats[speedIndex] = speed;
	}
	public void setAccuracy(double accuracy) {
		stats[accuracyIndex] = accuracy;
	}
	public void setCriticalChance(double critChance) {
		stats[critIndex] = critChance;
	}
	public void setHealPoints(int healPoints) {
		stats[healPointsIndex] = healPoints;
	}
	public void setLifeSteal(int lifeSteal) {
		stats[lifeStealIndex] = lifeSteal;
	}
	public void setDamageReflection(double dmgReflect) {
		stats[dmgReflectIndex] = dmgReflect;
	}

	// Other Getters & Setters

	public boolean getIsBlocking() {
		return isBlocking;
	}
	public void setIsBlocking(boolean state) {
		isBlocking = state;
	}
}
