package com.bayviewglen.zork;

/** "Zork" Class - the central class of the "Zork" game.
 * Current Authors: Kirill Tregubov, Zacharia Burrafato, Andrew Douglas, Alim Halani
 * Current Version: 0.1-alpha
 * Current Date:    March 2018
 */

public class Zork {

	public static void main(String[] args) {	
		Game game = new Game();
		game.play();
		
		
		// Entity test data (Ignore)
		/*int[] serp = new int[Entity.NUM_STATS];
		for (int i=0;i<Entity.NUM_STATS;i++) {
			serp[i] = 100*(i+1);
		}
		
		Entity potat = new Entity();
		Entity potato = new Entity("cheese",serp); // String _name, int[] _stats
		potat.printStats();
		System.out.println();
		System.out.println();
		potato.printStats();*/
		
		
	}

}