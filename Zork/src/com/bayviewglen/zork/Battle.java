package com.bayviewglen.zork;

import java.util.ArrayList;
import java.util.Scanner;

/** "Battle" Class - instantiates and facilitates battles.
 * 
 *  Authors: 		Kirill Tregubov, Zacharia Burrafato, Andrew Douglas, Alim Halani
 *  Code Version:	0.3-alpha
 *  Published Date:	May 2018
 */

public class Battle {

	private Player player;
	private Entity enemy;
	private boolean didRunAway;
	private int attackCounter;
	private int consumableUsageCounter;
	private int critHitCounter;

	public Battle(Player player, Entity enemy) {
		this.player = player;
		this.enemy = enemy;
		didRunAway = false;
		attackCounter = 0;
		consumableUsageCounter = 0;
		critHitCounter = 0;
	}

	public ArrayList<Integer> getCounters() {
		ArrayList<Integer> counters = new ArrayList<Integer>();
		counters.add(attackCounter);
		counters.add(consumableUsageCounter);
		counters.add(critHitCounter);
		return counters;
	}

	public int startBattle() {
		Game.musicMainTheme.pause();
		Game.battleMusic.loop();
		if (enemy.getType().equals(Entity.TYPES[Entity.BOSS_INDEX])) {
			enemy.stats.setSpeed(999);
		}
		System.out.println("You have engaged in battle with " + enemy.toString() + "!");
		System.out.println(enemy.toString() + "'s stats are: " + "\n" + enemy.stats);

		while((player.isAlive() && enemy.isAlive()) && didRunAway == false) {
			System.out.println("");

			if (player.stats.getSpeed() >= enemy.stats.getSpeed()) {
				System.out.print("You move first. ");
				processUserInput();
				if (!didRunAway && enemy.isAlive()) {
					System.out.print("\n" + enemy.toString() + " will now move. It has " + enemy.stats.getCurrentHP() + " HP left. ");
					damageDealingProcessor(true);
				}

			} else if (enemy.stats.getSpeed() > player.stats.getSpeed()) {
				System.out.print(enemy.toString() + " moves first. It has " + enemy.stats.getCurrentHP() + " HP left. ");
				damageDealingProcessor(true);

				if (player.isAlive()) {
					System.out.print("\nYou move next. ");
					processUserInput();
				}
			}
		}

		if (didRunAway) {
			System.out.println("You fled the battle in panic.");
			Game.battleMusic.pause();
			Game.battleMusic.reset();
			Game.musicMainTheme.reset();
			Game.musicMainTheme.loop();
			return 0; // Retreat integer
		}
		else if (!enemy.isAlive()) {
			System.out.print("\nYou have defeated " + enemy.toString() + ""
					+ "\nYou have " + player.stats.getCurrentHP() + " health remaining, ");
			player.expCalculator(1, getCounters(), Entity.ENEMY_INDEX);
			Game.battleMusic.pause();
			Game.battleMusic.reset();
			Game.musicMainTheme.reset();
			Game.musicMainTheme.loop();
			return 1; // Victory integer
		}
		else if (!player.isAlive()) {
			System.out.print("\nYou have been defeated by " + enemy.toString() + ",");
			player.expCalculator(2, getCounters(), Entity.ENEMY_INDEX);
			enemy.stats.setCurrentHP(enemy.stats.getMaximumHP()); // reset enemy HP
			player.setDefaultRoom(); // teleport to Contest Hall
			System.out.println("You have been returned to the " + player.getRoomName() + ".");
			// might want to reset trial as well
			Game.battleMusic.pause();
			Game.musicMainTheme.loop();
			return 2; // Loss integer
		}
		return 0; // Retreat integer (or in case everything breaks and you somehow get here)

	}

	private void processUserInput() {
		System.out.println("You have " + player.stats.getCurrentHP() + " HP left. Choose an action. Enter 'help' if you need any.");
		Parser parser = new Parser(player);
		Command command = parser.getBattleCommand();
		String commandName = command.getCommand();
		String commandType = command.getCommandType();
		String contextWord = command.getContextWord();

		//System.out.println(commandType + "\n" + contextWord);
		if (commandName == null) {
			System.out.println("You cannot do that...\n");
			processUserInput();
		} else if (commandType.equals("help")) {
			System.out.println("Here are all the commands you can use in battle:");
			System.out.println(parser.listBattleCommands());
			processUserInput();
		} else if (commandType.equals("attack")) {
			damageDealingProcessor(false);
		} else if (commandType.equals("run")) {
			didRunAway = true;
		} else if(commandType.equals("item")) {
			if (player.inventory.hasItem(contextWord)) {
				player.consumeItem(contextWord);
				consumableUsageCounter++;
				System.out.println("You have successfully consumed " + Item.getItem(contextWord) + "!");
			} else System.out.println("There is no " + Item.getItem(contextWord) + " in your inventory...");
		} else { // should be unreachable
			System.out.println("You cannot do that...\n");
			processUserInput();
		}
	}

	private void damageDealingProcessor(boolean enemyIsAttacking) {
		if (enemyIsAttacking) System.out.print("It attacks you, "); // add
		else System.out.print("You attack "+ enemy.name + ", "); //add

		if (enemyIsAttacking) {
			if (didHit(enemy.stats.getAccuracy())) {
				// Critical Hit (50% more)
				if (didHit(enemy.stats.getCriticalChance())) {
					System.out.println("and lands a critical hit, dealing " + (int) (enemy.stats.getAttack() * 1.5) + " damage.");
					player.stats.setCurrentHP((int) (player.stats.getCurrentHP() - enemy.stats.getAttack() * 1.5));
				} // Normal Attack
				else if (enemy.stats.getAttack() - player.stats.getDefense() > 1) {
					System.out.println("and deals " + enemy.stats.getAttack() + " damage.");
					player.stats.setCurrentHP((int) (player.stats.getCurrentHP() - enemy.stats.getAttack()));
				} // Base Attack because too much armor / damage
				else {
					System.out.println("and deals 1 damage.");
					player.stats.setCurrentHP(player.stats.getCurrentHP()-1);
				}
			} else System.out.println("but misses.");
		} else {
			if (didHit(player.stats.getAccuracy())) {
				// Critical Hit (50% more)
				if (didHit(player.stats.getCriticalChance())) {
					System.out.println("and land a critical hit, dealing " + (int) (player.stats.getAttack() * 1.5) + " damage.");
					enemy.stats.setCurrentHP((int) (enemy.stats.getCurrentHP() - player.stats.getAttack() * 1.5));
					attackCounter++;
					critHitCounter++;
				} // Normal Attack
				else if (player.stats.getAttack() - enemy.stats.getDefense() > 1) {
					System.out.println("and deal " + player.stats.getAttack() + " damage.");
					enemy.stats.setCurrentHP((int) (enemy.stats.getCurrentHP() - player.stats.getAttack()));
					attackCounter++;
				} // Base Attack because too much armor / damage
				else {
					System.out.println("and deal 1 damage.");
					enemy.stats.setCurrentHP(enemy.stats.getCurrentHP()-1);
					attackCounter++;
				}
			} else System.out.println("but miss your attack!");
		}
	}

	public boolean didHit(double accuracy) { // Accuracy is a double value (Ex: 0.5)
		int random = (int)(Math.random() * 100); // Accuracy raises the minimum value
		double hits = random/100.0;

		if (hits <= accuracy) return true;
		else return false;
	}
}