package com.bayviewglen.zork;

/** "Zork" Class - the central class of the game.
 * 
 *  Original Code Author: 	Michael Kolling
 *  Original Code Version:	1.0
 *  Original Published Date: July 1999
 * 
 *  Current Authors: 		Kirill Tregubov, Zacharia Burrafato, Andrew Douglas, Alim Halani
 *  Current Code Version:	0.3-alpha
 *  Current Published Date:	May 2018
 */

public class Zork {
	public static void main(String[] args) {	
		Game game = new Game();
		game.play();
	}
}