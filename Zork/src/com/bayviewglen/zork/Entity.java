package com.bayviewglen.zork;

public class Entity {

	public final static String[] STAT_NAMES = {"Level","Attribute points","CurrentHP","MaxHP","Strength","Defence","Speed","Accuracy"};
	public final static int DEFAULT_STAT_VAL = 10;
	public final static int NUM_STATS = 8;

	public final static int LVL = 0;
	public final static int ATR_POINTS = 1;
	public final static int CURR_HP = 2;
	public final static int MAX_HP = 3;
	public final static int STR = 4;
	public final static int DEF = 5;
	public final static int SPD = 6;
	public final static int ACC = 7;

	public String name;
	public boolean isBlocking;

	public int[] stats;

	//Level, atr points, currenthp, MaxHP, Strength, defence, speed, accuracy.


	public Entity () {
		name = "Generic";
		stats = new int[NUM_STATS];

		for (int i=0;i<NUM_STATS;i++) {
			stats[i] = DEFAULT_STAT_VAL;
		}

		isBlocking = false;

	}

	public Entity (String _name,int[] _stats) {
		name = _name;
		stats = new int[NUM_STATS];

		for (int i=0;i<NUM_STATS;i++) {
			stats[i] = _stats[i];
		}

		isBlocking = false;

	}




	public String getName() {
		return name;
	}
	public void setName(String _name) {
		name = _name;
	}

	public int getLvl() {
		return stats[LVL];
	}
	public void setLvl(int level) {
		stats[LVL] = level;
	}

	public int getAtrPoints() {
		return stats[ATR_POINTS];
	}
	public void setAtrPoints(int points) {
		stats[ATR_POINTS] = points;
	}

	public int getCurrHP() {
		return stats[CURR_HP];
	}
	public void setCurrHP(int _curr_hp) {
		stats[CURR_HP] = _curr_hp;
		if (getCurrHP()<0) {
			stats[CURR_HP] = 0;
		}
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

	public boolean getIsBlocking() {
		return isBlocking;
	}
	public void setIsBlocking(boolean state) {
		isBlocking = state;
	}


	public void printStats () {
		System.out.println("Name: "+name);
		for (int i=0;i<NUM_STATS;i++) {
			System.out.println(STAT_NAMES[i]+": "+stats[i]);
		}
	}

	public boolean isAlive() {
		return (getCurrHP()>0);
	}

	public void initLevelPoints() { // Used to give AI its attribute points and force it to use its points. Used upon AI initialization.
		// DO NOT USE THIS ON PLAYER INITIALIZATION
		this.setAtrPoints(this.getAtrPoints()+this.getLvl()*4); // 4 atr points per level, including starting level of 1
		this.autoAtrSpend(); // Spends gained points
	}

	public void autoAtrSpend() { // Automatically spends attribute points on random stats, used for AI generation or for lazy players
		int statChoice; // Choose between 5 different stats/numbers, index 3-7 inclusive
		if (this.getAtrPoints()>0) {
			while (0<getAtrPoints()) {
				setAtrPoints(getAtrPoints()-1);
				statChoice = (int) (Math.random()*5+1);

				if (statChoice==1) {
					this.setMaxHP(this.getMaxHP()+1);
					this.setCurrHP(getCurrHP()+1);
				}
				else if (statChoice==2) {
					this.setStrength(this.getStrength()+1);
				}
				else if (statChoice==3) {
					this.setDefence(this.getDefence()+1);
				}
				else if (statChoice==4) {
					this.setSpeed(this.getSpeed()+1);
				}
				else if (statChoice==5) {
					this.setAccuracy(this.getAccuracy()+1);
				}


			}
		}
	}

	public void engagementText() { // Displays what and who you are facing
		System.out.println("Hello world");
	}

	public String attackText() {
		return this.getName() + " attacks ";
	}

}