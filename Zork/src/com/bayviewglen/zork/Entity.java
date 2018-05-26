package com.bayviewglen.zork;

/** "Entity" Class - generates entities.
 * 
 *  Authors: 		Kirill Tregubov, Zacharia Burrafato, Andrew Douglas, Alim Halani
 *  Code Version:	0.2-alpha
 *  Published Date:	May 2018
 */

public class Entity {

	public String name;
	public Stats stats;


	public Entity(String name) {
		this.name = name;
	}
	
	public Entity(String name, int type, String stats) {
		this.name = name;
		this.stats = new Stats(Stats.ENTITY_INDEX, type, stats);
	}

	// Getters and Setters
	public void setName(String name) {
		this.name = name;
	}

	public boolean isAlive() {
		return stats.getCurrentHP() > 0;
	}

	// toString method
	public String toString() {
		return name;
	}

}