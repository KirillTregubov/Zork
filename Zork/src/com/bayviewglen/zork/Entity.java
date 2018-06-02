package com.bayviewglen.zork;

/** "Entity" Class - generates entities.
 * 
 *  Authors: 		Kirill Tregubov, Zacharia Burrafato, Andrew Douglas, Alim Halani
 *  Code Version:	0.3-alpha
 *  Published Date:	May 2018
 */

public class Entity {

	public String name;
	private String type;
	public final static String[] TYPES = {"Player", "Enemy", "Boss", "NPC"};
	public final static int PLAYER_INDEX = 0;
	public final static int ENEMY_INDEX = 1;
	public final static int BOSS_INDEX = 2;
	public final static int NPC_INDEX = 3;
	public Stats stats;
	private String dialogue;
	private Drops drops;


	public Entity(String name) {
		this.name = name;
	}
	
	public Entity(String name, int type) {
		this.name = name;
		this.type = TYPES[type];
	}

	public Entity(String name, int type, String stats) {
		this.name = name;
		this.type = TYPES[type];
		this.stats = new Stats(Stats.ENTITY_INDEX, type, stats);
	}
	
	public Entity(String name, int type, String stats, String extraString) {
		this.name = name;
		this.type = TYPES[type];
		if (type == NPC_INDEX)
			dialogue = extraString;
		else {
			this.stats = new Stats(Stats.ENTITY_INDEX, type, stats);
			drops = new Drops(extraString);	
		}
	}

	public String getType() {
		return type;
	}
	
	public String getDialogue() {
		return dialogue;
	}

	// Getters and Setters
	public void setName(String name) {
		this.name = name;
	}
	
	public Drops getDrops() {
		return drops;
	}

	public boolean isAlive() {
		return stats.getCurrentHP() > 0;
	}

	// toString method
	public String toString() {
		return name;
	}

}