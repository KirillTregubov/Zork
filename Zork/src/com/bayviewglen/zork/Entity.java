package com.bayviewglen.zork;

public class Entity {
	
	public final static String[] STAT_NAMES = {"CurrentHP","MaxHP","Strength","Defence","Speed","Accuracy"};
	public final static int DEFAULT_STAT_VAL = 100;
	public final static int NUM_STATS = 6;
	
	public final static int CURR_HP = 0;
	public final static int MAX_HP = 1;
	public final static int STR = 2;
	public final static int DEF = 3;
	public final static int SPD = 4;
	public final static int ACC = 5;
	
	public String name;
	public boolean isAlive;
	public int[] stats;
	
	//currentHP,maxHP,str,def,spd,acc;
	
	
	
	public Entity () {
		name = "Generic";
		stats = new int[NUM_STATS];
		isAlive = true;
		
		for (int i=0;i<NUM_STATS;i++) {
			stats[i] = DEFAULT_STAT_VAL;
		}
	}
	
	public Entity (String _name,int[] _stats) {
		name = _name;
		stats = new int[NUM_STATS];
		isAlive = true;
		
		for (int i=0;i<NUM_STATS;i++) {
			stats[i] = _stats[i];
		}
	}
	
	public void printStats () {
		System.out.println("Name: "+name);
		for (int i=0;i<NUM_STATS;i++) {
			System.out.println(STAT_NAMES[i]+": "+stats[i]);
		}
	}
	
	public String getName() {
		return name;
	}
	public void setName(String _name) {
		name = _name;
	}
	
	public int getCurrentHP() {
		return stats[CURR_HP];
	}
	public void setCurrentHP(int _curr_hp) {
		stats[CURR_HP] = _curr_hp;
	}
	
	public int getMaxHP() {
		return stats[MAX_HP];
	}
	public void setMaxHP(int _max_hp) {
		stats[MAX_HP] = _max_hp;
	}
	
	public int getStrength() {
		return stats[STR];
	}
	public void setStrength(int _str) {
		stats[STR] = _str;
	}
	
	public int getDefence() {
		return stats[DEF];
	}
	public void setDefence(int _def) {
		stats[DEF] = _def;
	}
	
	public int getSpeed() {
		return stats[SPD];
	}
	public void setSpeed(int _spd) {
		stats[SPD] = _spd;
	}
	
	public int getAccuracy() {
		return stats[ACC];
	}
	public void setAccuracy(int _acc) {
		stats[ACC] = _acc;
	}
}
