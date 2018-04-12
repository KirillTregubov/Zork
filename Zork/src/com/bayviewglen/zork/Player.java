package com.bayviewglen.zork;

public class Player extends Entity {

	int xp;
	boolean battleSpeedHint;
	
	
	Player() {
		super();
		battleSpeedHint = true;
	}
	Player(String _name,int[] _stats,int _xp) {
		super(_name,_stats);
		xp = _xp;
		battleSpeedHint = true;
	}
	
	public int getXp() {
		return xp;
	}
	public void setXp(int _xp) {
		xp = _xp;
	}
	
	public boolean getSpeedHint() {
		return battleSpeedHint;
	}
	public void setSpeedHint(boolean state) {
		battleSpeedHint = state;
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
	
	public String attackText() {
		return (this.getName()+" swings their dagger at ");
	}
	
}
