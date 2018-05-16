package com.bayviewglen.zork;

import java.util.ArrayList;

public class Weapon extends EItems {

	public ArrayList<Double> wepStats;
	public int ATTACK_INDEX = 0;
	public int CRITICAL_INDEX = 1;
	public int LIFESTEAL_INDEX = 2;
	
	public Weapon () {
		super();
		wepStats.add(0.0);
		wepStats.add(0.0);
		wepStats.add(0.0);
	}
	public Weapon (String _name,String _description,double attack, double critical, double lifeSteal) {
		super(_name,_description,true,false,false,false);
		wepStats.add(attack);
		wepStats.add(critical);
		wepStats.add(lifeSteal);
	}
	
}
