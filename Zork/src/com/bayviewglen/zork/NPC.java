package com.bayviewglen.zork;

/** "NPC" Class - generates NPCs.
 * 
 *  Authors: 		Kirill Tregubov, Zacharia Burrafato, Andrew Douglas, Alim Halani
 *  Code Version:	0.3-alpha
 *  Published Date:	May 2018
 */

public class NPC extends Entity {

	NPC(String name, String stats) {
		super(name, Stats.ENEMY_INDEX, stats);
	}

	NPC(String name) {
		super(name);
	}

}
