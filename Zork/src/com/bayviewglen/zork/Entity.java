package com.bayviewglen.zork;

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
	
	public String getName() {
		return name;
	}
	public void setName(String _name) {
		name = _name;
	}

	public boolean isAlive() {
		return (stats.getCurrentHP() > 0);
	}

	// toString method
	public String toString() {
		return name;
	}

}