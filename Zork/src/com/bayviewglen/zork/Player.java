package com.bayviewglen.zork;

public class Player extends Entity {

	int xp;
	
	
	Player() {
		super();
	}
	Player(String _name,int[] _stats,int _xp) {
		super(_name,_stats);
		xp = _xp;
	}
	
	public int getXp() {
		return xp;
	}
	public void setXp(int _xp) {
		xp = _xp;
	}
	
	public void printStats() {
		super.printStats();
		System.out.println("Experience: "+xp);
	}
	public void levelUp() { // Experience required for level up calculation is still in progress, I'm open to suggestions at this point
		while (getXp()>=getLvl()) {
			if (getXp()>=getLvl()*100+100) {
				setXp(getLvl()*100+100);
				setLvl(getLvl()+1);
			}
		}
	}
	
}
