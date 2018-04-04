package com.bayviewglen.zork;

public class Player extends Entity {

	int lvl;
	int xp;
	
	
	Player() {
		super();
	}
	Player(String _name,int[] _stats,int _lvl,int _xp) {
		super(_name,_stats);
		xp = _xp;
		lvl = _lvl;
	}
	
	public void printStats() {
		super.printStats();
		System.out.println("Level: "+lvl);
		System.out.println("Experience: "+xp);
	}
}
