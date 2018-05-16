package com.bayviewglen.zork;

import java.util.Scanner;

public class Battle {

	public Player player;
	public boolean playerIsBlocking;
	public Entity entity;
	public boolean entityIsBlocking;

	public Battle(Player p, Entity ent) {
		player = p;
		playerIsBlocking = false;
		entity = ent;
		entityIsBlocking = false;
	}


	public Player getPlayer(){
		return player;
	}

	public Entity getEntity() {
		return entity;
	}

	public void startBattle() { // Consumables count as a turn.


		double storedPlayerSpeed = player.stats.getSpeed();
		double storedEntitySpeed = entity.stats.getSpeed();
		System.out.println("\nYou have been engaged by "+entity.getName()+"!");

		while(player.isAlive()&&entity.isAlive()) { // TODO: And while running away is not true

			System.out.println("");

			if (player.stats.getSpeed()>=entity.stats.getSpeed()) { // Multiply by weapon speed
				System.out.println(player.getName()+" will now move!");

				System.out.println("Take an action!");
				battleParser();

				if (entity.isAlive()) {
					if (entity.stats.getSpeed()<storedEntitySpeed) {
						entity.stats.setSpeed(entity.stats.getSpeed()+20);
					}
					System.out.println(entity.getName()+" will now move!");


					damageDealer(entity.stats,entity.getName(),player.stats,player.getName(),playerIsBlocking);

				}

			}
			else if (entity.stats.getSpeed()>player.stats.getSpeed()) { // Multiply by weapon speed
				System.out.println(entity.getName()+" will now move!");

				/*if (player.stats.getSpeedHint()) {
					System.out.println("Hint: The enemy has the first move on this turn, consider upgrading your speed stat!");
					player.stats.setSpeedHint(false);
				}*/

				System.out.println(entity.getName()+" attacks "+entity.getName()+"!");
				damageDealer(entity.stats,entity.getName(),player.stats,player.getName(),playerIsBlocking);

				if (player.isAlive()) {

					System.out.println("Take an action!");
					battleParser();

					if (player.stats.getSpeed()<storedPlayerSpeed) {
						player.stats.setSpeed(player.stats.getSpeed()+20);
					}

				}
			}
		}

		if (player.isAlive()==false) {
			System.out.println("A dark day for the Republic. You are defeated...");
		}
		else if (entity.isAlive()==false) {
			System.out.println("You have defeated level "+entity.stats.getLevel()+" "+entity.getName()+"!");
		}

	}

	public void battleParser() { // TODO: Add loop and ability to observe the enemy, which will show it's stats, take care of fail cases
		// Attack, hit, swing, stab, bludgeon, slash, strike, power, run, use (item), consume, eat, drink, help
		Scanner input = new Scanner(System.in);
		String parseMe;

		for(boolean endParse=false;endParse==false;) {
			parseMe = input.nextLine().toLowerCase();
			if (parseMe.equals("block")||parseMe.equals("block attack")||parseMe.equals("block hit")) {
				playerIsBlocking=true;
				endParse = true;
			}
			else if (parseMe.equals("attack")||parseMe.equals("hit")||parseMe.equals("swing")||parseMe.equals("stab")||
					parseMe.equals("bludgeon")||parseMe.equals("slash")||parseMe.equals("strike")) {
				playerIsBlocking=false;
				damageDealer(player.stats,player.getName(),entity.stats,entity.getName(),entityIsBlocking);
				endParse = true;
			}
			else if (parseMe.equals("help")||parseMe.equals("help me")) {
				System.out.println("Commands are \"attack\" and \"block\" or words and phases that mean them. It's really not that difficult, dude.");
			}
			else {
				System.out.println("No known command by that name, try again. It's not like there's a battle going on right?");
			}
		}
		/*else if (parseMe=="Attack") {
			damageDealer(player,entity);
		}*/

		// Create parser or use Curills

	}

	public void damageDealer (Stats attacker,String attackerName,Stats defender,String defenderName,boolean defenderBlockingState) {

		System.out.println(attackerName+" attacks "+defenderName+"!");



		if (didHit(attacker)) { // Accuracy calculator
			if (defenderBlockingState==false) {
				if (attacker.getAttack()*((40-defender.getDefense())/40)<1) {
					defender.setCurrentHP(defender.getCurrentHP()-1);
					System.out.println(defenderName+" took 1 damage!"); // Minimum of one damage
					System.out.println(defenderName+"'s health: "+ defender.getCurrentHP());
				}
				else {
					System.out.println(defenderName+" took "+attacker.getAttack()*((40-defender.getDefense())/40)+" damage!");
					defender.setCurrentHP(defender.getCurrentHP()-attacker.getAttack()*((40-defender.getDefense())/40));
					System.out.println(defenderName+"'s health: "+ defender.getCurrentHP());
				}
			}
			else {
				System.out.println(attackerName+"'s attack was successfully blocked by "+defenderName+"!");
				System.out.println("The successful block has caught "+attackerName+" offguard. Strike Now!");
				attacker.setSpeed(attacker.getSpeed()-20);
				playerIsBlocking=false; // Resets their blocking states for the next combat turn
				entityIsBlocking=false;
			}

			playSound("/Users/adouglas/git/Zork/Zork/Test1.mp3"); // Doesn't work

		}
		else {
			System.out.println(attackerName+" completely missed their target!");
		}


	}

	public boolean didHit(Stats _stats) { // Accuracy is a double value (Ex: 0.5)
		int random = (int)((Math.random())*100); // Accuracy raises the minimum value
		double hits = random/100.0;

		if (hits<_stats.getAccuracy()) {
			return true;
		}
		else {
			return false;
		}

	}

	public void playSound(String path) {

	}
}