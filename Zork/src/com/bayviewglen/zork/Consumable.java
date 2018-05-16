package com.bayviewglen.zork;

public class Consumable extends EItems {
	
	public double healthGain; // Change to an array once more things are added
	
	public Consumable () {
		super();
		healthGain = 0;
	}
	public Consumable (String _name,String _description,double _healthGain) {
		super(_name,_description,false,true,false,false);
		healthGain = _healthGain;
	}

}
