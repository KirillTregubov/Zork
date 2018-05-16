package com.bayviewglen.zork;

public class EItems {

	public boolean weapon;
	public boolean consumable;
	public boolean armour;
	public boolean misc;
	
	public String name;
	public String description;
	
	public EItems () {
		name = "Default Item";
		description = "A default, uninitialized item.";
		weapon=false;
		consumable=false;
		armour=false;
		misc=false;
	}
	public EItems (String _name,String _description,boolean _isWeapon,boolean _isConsumable,boolean _isArmour,boolean _isMisc) {
		name = _name;
		description = _description;
		weapon = _isWeapon;
		consumable = _isConsumable;
		armour = _isArmour;
		misc = _isMisc;
	}
	
}
