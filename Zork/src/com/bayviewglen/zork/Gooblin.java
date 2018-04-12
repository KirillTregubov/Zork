package com.bayviewglen.zork;

public class Gooblin extends Enemy {
	//Level, atr points, currenthp, MaxHP, Strength, defence, speed, accuracy
	static int[] BASE_GOOBLIN_STATS = new int[]{1,0,30,30,20,20,25,25}; // Must 
	
	
	public Gooblin () {
		super();
	}
	public Gooblin (String _name, int level) { // When enemies are initialized the first time
		super(_name,BASE_GOOBLIN_STATS);
		setLvl(level);
		initLevelPoints();
		autoAtrSpend();
	}
	
	public void engagementText() {
		System.out.println("You have engaged a hostile goblin called "+this.getName()+"!");
	}
	
	public String attackText () {
		return (this.getName()+" swings their sword at ");
	}
}
