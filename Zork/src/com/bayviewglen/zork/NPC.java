package com.bayviewglen.zork;

public class NPC extends Entity {
	
	private boolean doesAutoEngage; // implement
	private int npcType;
	public final static int TYPE_BOSS = 1;
	public final static int TYPE_ENEMY = 2;
	public final static int TYPE_NEUTRAL = 3;
	
	NPC(String name, int type, String stats) {
		super(name, Stats.ENEMY_INDEX, stats);
		npcType = type;
	}
	
	NPC(String name) {
		super(name);
		npcType = TYPE_NEUTRAL;
	}
	
	public int getEntityType() {
		return npcType;
	}
}
