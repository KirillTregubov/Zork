package com.bayviewglen.zork;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;

/** "Stats" Class - a class that creates and stores stats of everything in the game.
 * 
 * Current Authors: Kirill Tregubov, Zacharia Burrafato, Andrew Douglas, Alim Halani
 * Current Version: 0.2-alpha
 * Current Date:    April 2018
 */

public class Stats {
	// Object Stats
	public ArrayList<Double> stats;
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
	private ArrayList<String> statNames;
	public String type; // is this needed?
	private ArrayList<Integer> usedIndexes;
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
	private final static int DEFAULT_STAT_VALUE = 10;
	private final static int NULL_STAT_VALUE = 10;
	public final static int STATS_AMOUNT = STAT_NAMES.length;
	// Default Indexes of Stats
	public final static int LVL_INDEX = 0;
	public final static int EXP_INDEX = 1;
	public final static int AP_INDEX = 2;
	public final static int CURR_HP_INDEX = 3;
	public final static int MAX_HP_INDEX = 4;
	public final static int ATK_INDEX = 5;
	public final static int DEF_INDEX = 6;
	public final static int SPEED_INDEX = 7;
	public final static int ACCURACY_INDEX = 8;
	public final static int CRIT_INDEX = 9;
	public final static int HEAL_POINTS_INDEX = 10;
	public final static int LIFE_STEAL_INDEX = 12;
	public final static int DMG_REFLECT_INDEX = 13;

	Stats(int type, int subtype, String input) {
		assignUsedIndexes(TYPES[type][subtype]);
		if (usedIndexes.size() < 1) return;
		else assignStats(input);
	}

	// Unique Items
	Stats(int type, int subtype, String input, int[] uniqueIndexes) {
		assignUniqueUsedIndexes(TYPES[type][subtype], uniqueIndexes);
		if (usedIndexes.size() < 1) return;
		else assignStats(input);
	}

	private void assignUsedIndexes(String type) {
		this.type = type;
		if (type == TYPES[ITEM_INDEX][Item.BASE_INDEX]) {
			usedIndexes = new ArrayList<Integer>();
		} else if (type == TYPES[ITEM_INDEX][Item.CONSUMABLE_INDEX]) {
			usedIndexes =  new ArrayList<Integer>(Arrays.asList(new Integer[]{HEAL_POINTS_INDEX}));
		} else if (type == TYPES[ITEM_INDEX][Item.WEAPON_INDEX]) {
			usedIndexes =  new ArrayList<Integer>(Arrays.asList(new Integer[]{ATK_INDEX, CRIT_INDEX}));
		} else if (type == TYPES[ITEM_INDEX][Item.ARMOR_INDEX]) {
			usedIndexes =  new ArrayList<Integer>(Arrays.asList(new Integer[]{DEF_INDEX}));
		} else if (type == TYPES[ENTITY_INDEX][PLAYER_INDEX]) {
			usedIndexes = new ArrayList<Integer>(Arrays.asList(new Integer[]{LVL_INDEX, EXP_INDEX, AP_INDEX, CURR_HP_INDEX, MAX_HP_INDEX,
					ATK_INDEX, DEF_INDEX, SPEED_INDEX, ACCURACY_INDEX, CRIT_INDEX}));
		} else if (type == TYPES[ENTITY_INDEX][ENEMY_INDEX]) {
			usedIndexes = new ArrayList<Integer>(Arrays.asList(new Integer[]{LVL_INDEX, AP_INDEX, CURR_HP_INDEX, MAX_HP_INDEX,
					ATK_INDEX, DEF_INDEX, SPEED_INDEX, ACCURACY_INDEX, CRIT_INDEX}));
		}

		if (usedIndexes.size() > 0) {
			if (usedIndexes.contains(LVL_INDEX)) lvlIndex = usedIndexes.indexOf(LVL_INDEX);
			if (usedIndexes.contains(AP_INDEX)) apIndex = usedIndexes.indexOf(AP_INDEX);
			if (usedIndexes.contains(CURR_HP_INDEX)) currHPIndex = usedIndexes.indexOf(CURR_HP_INDEX);
			if (usedIndexes.contains(MAX_HP_INDEX)) maxHPIndex = usedIndexes.indexOf(MAX_HP_INDEX);
			if (usedIndexes.contains(ATK_INDEX)) atkIndex = usedIndexes.indexOf(ATK_INDEX);
			if (usedIndexes.contains(DEF_INDEX)) defIndex = usedIndexes.indexOf(DEF_INDEX);
			if (usedIndexes.contains(SPEED_INDEX)) speedIndex = usedIndexes.indexOf(SPEED_INDEX);
			if (usedIndexes.contains(ACCURACY_INDEX)) accuracyIndex = usedIndexes.indexOf(ACCURACY_INDEX);
			if (usedIndexes.contains(CRIT_INDEX)) critIndex = usedIndexes.indexOf(CRIT_INDEX);
			if (usedIndexes.contains(HEAL_POINTS_INDEX)) healPointsIndex = usedIndexes.indexOf(HEAL_POINTS_INDEX);
			if (usedIndexes.contains(LIFE_STEAL_INDEX)) lifeStealIndex = usedIndexes.indexOf(LIFE_STEAL_INDEX);
			if (usedIndexes.contains(DMG_REFLECT_INDEX)) dmgReflectIndex = usedIndexes.indexOf(DMG_REFLECT_INDEX);

			Collections.sort(usedIndexes);
		}
	}

	// Unique Items
	private void assignUniqueUsedIndexes(String type, int[] uniqueIndexes) { // unfinished
		assignUsedIndexes(type);
		for (int index : uniqueIndexes) {
			if (index == LVL_INDEX) {
				usedIndexes.add(LVL_INDEX);
				lvlIndex = usedIndexes.indexOf(LVL_INDEX);
			} if (index == AP_INDEX) {
				usedIndexes.add(AP_INDEX);
				apIndex = usedIndexes.indexOf(AP_INDEX);
			} if (index == CURR_HP_INDEX) {
				usedIndexes.add(CURR_HP_INDEX);
				currHPIndex = usedIndexes.indexOf(CURR_HP_INDEX);
			} if (index == MAX_HP_INDEX) {
				usedIndexes.add(MAX_HP_INDEX);
				maxHPIndex = usedIndexes.indexOf(MAX_HP_INDEX);
			} if (index == ATK_INDEX) {
				usedIndexes.add(ATK_INDEX);
				atkIndex = usedIndexes.indexOf(ATK_INDEX);
			} if (index == DEF_INDEX) {
				usedIndexes.add(DEF_INDEX);
				defIndex = usedIndexes.indexOf(DEF_INDEX);
			} if (index == SPEED_INDEX) {
				usedIndexes.add(SPEED_INDEX);
				speedIndex = usedIndexes.indexOf(SPEED_INDEX);
			} if (index == ACCURACY_INDEX) {
				usedIndexes.add(ACCURACY_INDEX);
				accuracyIndex = usedIndexes.indexOf(ACCURACY_INDEX);
			} if (index == CRIT_INDEX) {
				usedIndexes.add(CRIT_INDEX);
				critIndex = usedIndexes.indexOf(CRIT_INDEX);
			} if (index == HEAL_POINTS_INDEX) {
				usedIndexes.add(HEAL_POINTS_INDEX);
				healPointsIndex = usedIndexes.indexOf(HEAL_POINTS_INDEX);
			} if (index == LIFE_STEAL_INDEX) {
				usedIndexes.add(LIFE_STEAL_INDEX);
				lifeStealIndex = usedIndexes.indexOf(LIFE_STEAL_INDEX);
			} if (index == DMG_REFLECT_INDEX) {
				usedIndexes.add(DMG_REFLECT_INDEX);
				dmgReflectIndex = usedIndexes.indexOf(DMG_REFLECT_INDEX);
			}
		}
		if (usedIndexes.size() > 1) {
			Collections.sort(usedIndexes);
		}
	}

	private void assignStats(String input) {
		// Set Stat Names
		statNames = new ArrayList<String>();
		for (Integer statName : usedIndexes) {
			statNames.add(STAT_NAMES[statName]);
		}
		// Set Stat Values
		stats = new ArrayList<Double>();
		for (int i = 0; i < input.split(",", - 1).length; i++) {
			stats.add(Double.parseDouble(input.split(",", - 1)[i]));
		}
	}

	public void loadStats(String inputStats) {
		String[] inputArr = inputStats.split("/");

		for (int i = 0; i < inputArr.length; i++) {
			stats.add(Double.parseDouble(inputArr[i]));
		}
	}

	// Stats Getters

	public int getLevel() {
		return stats.get(lvlIndex).intValue();
	}
	public int getExp() {
		return stats.get(expIndex).intValue();
	}
	public int getAttributePoints() {
		return stats.get(apIndex).intValue();
	}
	public int getCurrentHP() {
		return stats.get(currHPIndex).intValue();
	}
	public int getMaximumHP() {
		return stats.get(maxHPIndex).intValue();
	}
	public int getAttack() {
		return stats.get(atkIndex).intValue();
	}
	public int getDefense() {
		return stats.get(defIndex).intValue();
	}
	public int getSpeed() {
		return stats.get(speedIndex).intValue();
	}
	public double getAccuracy() {
		return stats.get(accuracyIndex);
	}
	public double getCriticalChance() {
		return stats.get(critIndex);
	}
	public int getHealPoints() {
		return stats.get(healPointsIndex).intValue();
	}
	public int getLifeSteal() {
		return stats.get(lifeStealIndex).intValue();
	}
	public double getDamageReflection() {
		return stats.get(dmgReflectIndex);
	}

	// Stats Setters

	public void setLevel(int level) {
		try {
			if (lvlIndex != null) stats.set(lvlIndex, (double) level);
		} catch (Exception e) {
			throw new IllegalStateException("Wasn't able to set level");
		}
	}
	public void setExp(int exp) {
		try {
			if (lvlIndex != null) stats.set(expIndex, (double) exp);
		} catch (Exception e) {
			throw new IllegalStateException("Wasn't able to set exp");
		}
	}
	public void setAttributePoints(int ap) {
		try {
			if (lvlIndex != null) stats.set(apIndex, (double) ap);
		} catch (Exception e) {
			throw new IllegalStateException("Wasn't able to set attribute points");
		}
	}
	public void setCurrentHP(int currHP) {
		try {
			if (lvlIndex != null) stats.set(currHPIndex, (double) currHP);
		} catch (Exception e) {
			throw new IllegalStateException("Wasn't able to set current hp");
		}
	}
	public void setMaximumHP(int maxHP) {
		try {
			if (lvlIndex != null) stats.set(maxHPIndex, (double) maxHP);
		} catch (Exception e) {
			throw new IllegalStateException("Wasn't able to set maximum hp");
		}
	}
	public void setAttack(int atk) {
		try {
			if (lvlIndex != null) stats.set(atkIndex, (double) atk);
		} catch (Exception e) {
			throw new IllegalStateException("Wasn't able to set attack");
		}
	}
	public void setDefense(int def) {
		try {
			if (lvlIndex != null) stats.set(defIndex, (double) def);
		} catch (Exception e) {
			throw new IllegalStateException("Wasn't able to set defense");
		}
	}
	public void setSpeed(int speed) {
		try {
			if (lvlIndex != null) stats.set(speedIndex, (double) speed);
		} catch (Exception e) {
			throw new IllegalStateException("Wasn't able to set speed");
		}
	}
	public void setAccuracy(double accuracy) {
		try {
			if (lvlIndex != null) stats.set(accuracyIndex, accuracy);
		} catch (Exception e) {
			throw new IllegalStateException("Wasn't able to set accuracy");
		}
	}
	public void setCriticalChance(double crit) {
		try {
			if (lvlIndex != null) stats.set(critIndex, crit);
		} catch (Exception e) {
			throw new IllegalStateException("Wasn't able to set critical chance");
		}
	}
	public void setHealPoints(int healPoints) {
		try {
			if (lvlIndex != null) stats.set(healPointsIndex, (double) healPoints);
		} catch (Exception e) {
			throw new IllegalStateException("Wasn't able to set heal points");
		}
	}
	public void setLifeSteal(int lifeSteal) {
		try {
			if (lvlIndex != null) stats.set(lifeStealIndex, (double) lifeSteal);
		} catch (Exception e) {
			throw new IllegalStateException("Wasn't able to set life steal");
		}
	}
	public void setDamageReflection(double dmgReflect) {
		try {
			if (lvlIndex != null) stats.set(dmgReflectIndex, dmgReflect);
		} catch (Exception e) {
			throw new IllegalStateException("Wasn't able to set damage reflection");
		}
	}

	// Other Getters & Setters

	public boolean getIsBlocking() {
		return isBlocking;
	}
	public void setIsBlocking(boolean state) {
		isBlocking = state;
	}

	// Returns string of object name 
	public String toString(){
		if (stats == null) return "There are no stats to display!";

		String returnString = "";
		for (int i = 0; i < stats.size(); i++) {
			if (usedIndexes.get(i) == LVL_INDEX) returnString += statNames.get(i) + ": " + getLevel();
			if (usedIndexes.get(i) == EXP_INDEX) returnString += statNames.get(i) + ": " + getExp();
			if (usedIndexes.get(i) == AP_INDEX) returnString += statNames.get(i) + ": " + getAttributePoints();
			if (usedIndexes.get(i) == CURR_HP_INDEX) returnString += statNames.get(i) + ": " + getCurrentHP();
			if (usedIndexes.get(i) == MAX_HP_INDEX) returnString += statNames.get(i) + ": " + getMaximumHP();
			if (usedIndexes.get(i) == ATK_INDEX) returnString += statNames.get(i) + ": " + getAttack();
			if (usedIndexes.get(i) == DEF_INDEX) returnString += statNames.get(i) + ": " + getDefense();
			if (usedIndexes.get(i) == SPEED_INDEX) returnString += statNames.get(i) + ": " + getSpeed();
			if (usedIndexes.get(i) == ACCURACY_INDEX) returnString += statNames.get(i) + ": " + getAccuracy();
			if (usedIndexes.get(i) == CRIT_INDEX) returnString += statNames.get(i) + ": " + getCriticalChance();
			if (usedIndexes.get(i) == HEAL_POINTS_INDEX) returnString += statNames.get(i) + ": " + getHealPoints();
			if (usedIndexes.get(i) == LIFE_STEAL_INDEX) returnString += statNames.get(i) + ": " + getLifeSteal();
			if (usedIndexes.get(i) == DMG_REFLECT_INDEX) returnString += statNames.get(i) + ": " + getDamageReflection();

			if (i < stats.size()-1) returnString += ", ";
			else returnString += ".";
		}
		return returnString;
	}
}
