package com.bayviewglen.zork;

import java.util.ArrayList;
import java.util.Scanner;

public class Battle {

	public Player player;
	public boolean playerIsBlocking;
	public Entity entity;
	public boolean entityIsBlocking;
	public boolean running;

	public Battle(Player p, Entity ent) {
		player = p;
		playerIsBlocking = false;
		entity = ent;
		entityIsBlocking = false;
		running = false;
	}


	public Player getPlayer(){
		return player;
	}

	public Entity getEntity() {
		return entity;
	}

	public int startBattle() { // Consumables count as a turn. Returns either a 0,1,2. 0 means you ran away. 1 means won. 2 means loss.
		
		System.out.println("\nYou have been engaged by "+entity.getName()+"!");

		while((player.isAlive()&&entity.isAlive())&&running==false) { // TODO: And while running away is not true

			System.out.println("");

			if (player.stats.getSpeed()>=entity.stats.getSpeed()) { // Multiply by weapon speed
				System.out.println(player.getName()+" will now move!");

				System.out.println("Take an action!");
				battleParser();

				if (entity.isAlive()) {
					System.out.println(entity.getName()+" will now move!");


					damageDealer(entity.stats,entity.getName(),player.stats,player.getName(),playerIsBlocking);

				}

			}
			else if (entity.stats.getSpeed()>player.stats.getSpeed()) {
				System.out.println(entity.getName()+" will now move!");

				damageDealer(entity.stats,entity.getName(),player.stats,player.getName(),playerIsBlocking);

				if (player.isAlive()) {

					System.out.println("Take an action!");
					battleParser();


				}
			}
		}
		
		if (running==true) {
			System.out.println("You flee in panic. Cause. Ya know. Yer bad m8.");
			return 0; // Retreat integer
		}
		else if (entity.isAlive()==false) {
			System.out.println("You have defeated level "+entity.stats.getLevel()+" "+entity.getName()+"!");
			return 1; // Victory integer
		}
		else if (player.isAlive()==false) {
			System.out.println("You are defeated...");
			entity.stats.setCurrentHP(entity.stats.getMaximumHP());
			return 2; // Loss integer
		}
		return 0; // Retreat integer (in case everything breaks and you somehow get here

	}

	public void battleParser() { // TODO: Add loop and ability to observe the enemy, which will show it's stats, take care of fail cases
		// Attack, hit, swing, stab, bludgeon, slash, strike, power, run, use (item), consume, eat, drink, help
		Scanner input = new Scanner(System.in);
		String parseMe;

		for(boolean endParse=false;endParse==false;) {
			parseMe = input.nextLine().toLowerCase();
			String[] words=parseMe.split(" ");
			if (parseMe.equals("run")||parseMe.equals("run away")||parseMe.equals("flee")) {
				running=true;
				endParse = true;
			}
			else if (parseMe.equals("attack")||parseMe.equals("hit")||parseMe.equals("swing")||parseMe.equals("stab")||
					parseMe.equals("bludgeon")||parseMe.equals("slash")||parseMe.equals("strike")) {
				playerIsBlocking=false;
				damageDealer(player.stats,player.getName(),entity.stats,entity.getName(),entityIsBlocking);
				endParse = true;
			}
			else if (words[0].equals("consume")||words[0].equals("eat")||words[0].equals("use")) {
				String itemName="";
				for (int j=1;j<words.length;j++) {
					itemName+=words[j]+" ";
				}
				itemName = itemName.trim();
				// Call useItem()
				
				if (player.getInventory().containsItem(itemName)) {
				System.out.println(player.getName()+" used a "+itemName+"!");
				int itemIndex = player.getInventory().getItemIndex(itemName);
				player.stats.setCurrentHP(player.stats.getCurrentHP()+player.getInventory().getItemList().get(itemIndex).stats.getHealPoints());
				ArrayList<Item> temp = player.getInventory().getItemList();
				temp.remove(itemIndex);
				
				player.getInventory().setItemList(temp);
				endParse = true;
				}
				else {
					System.out.println("That item does not exist, you're wasting valuable time! \nTake an action!");
				}
			}
			else if (parseMe.equals("help")||parseMe.equals("help me")) {
				System.out.println("Commands are \"attack\" and \"block\" or words and phases that mean them. It's really not that difficult, dude.");
			}
			else {
				System.out.println("No known command by that name, try again. It's not like there's a battle going on right?");
			}
		}

	}

	public void damageDealer (Stats attacker,String attackerName,Stats defender,String defenderName,boolean defenderBlockingState) {

		System.out.println(attackerName+" attacks "+defenderName+"!");



		if (didHit(attacker.getAccuracy())) { // Hit chance
			if (defenderBlockingState==false) {
				if (didHit(attacker.getCriticalChance())&&(attacker.getAttack()*1.5-defender.getDefense())>1) { // Critical hit chance
					// Apply critical bonus (Currently 50% damage bonus, ask Zach for specifics)
					System.out.println("It was a critical hit!");
					System.out.println(defenderName+" took "+(attacker.getAttack()*1.5-defender.getDefense())+" damage!");
					defender.setCurrentHP((int) (defender.getCurrentHP()-(attacker.getAttack()*1.5-defender.getDefense())));
					System.out.println(defenderName+"'s health: "+ defender.getCurrentHP());
				}
				
				else if ((attacker.getAttack()-defender.getDefense())<1) { // One damage minimum
					System.out.println("The attack is ineffective...");
					defender.setCurrentHP(defender.getCurrentHP()-1);
					System.out.println(defenderName+" took 1 damage!"); // Minimum of one damage
					System.out.println(defenderName+"'s health: "+ defender.getCurrentHP());
				}
				else { // Normal Damage Calculation
					System.out.println(defenderName+" took "+(attacker.getAttack()-defender.getDefense())+" damage!");
					defender.setCurrentHP(defender.getCurrentHP()-(attacker.getAttack()-defender.getDefense()));
					System.out.println(defenderName+"'s health: "+ defender.getCurrentHP());
				}
			}
			else { // This is basically redundant because of spamming reasons
				System.out.println(attackerName+"'s attack was successfully blocked by "+defenderName+"!");
				System.out.println("The successful block has caught "+attackerName+" offguard. Strike Now!");
				playerIsBlocking=false; // Resets their blocking states for the next combat turn
				entityIsBlocking=false;
			}

			playSound("/Users/adouglas/git/Zork/Zork/Test1.mp3"); // Doesn't work

		}
		else {
			System.out.println(attackerName+" completely missed their target!");
		}


	}

	public boolean didHit(double accuracy) { // Accuracy is a double value (Ex: 0.5)
		int random = (int)((Math.random())*100); // Accuracy raises the minimum value
		double hits = random/100.0;

		if (hits<=accuracy) {
			return true;
		}
		else {
			return false;
		}

	}

	public void playSound(String path) {

	}
}