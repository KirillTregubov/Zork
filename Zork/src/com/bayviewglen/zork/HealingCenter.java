package com.bayviewglen.zork;

public class HealingCenter {
// heals the player as long as they are in the contest hall (they visit the healing center)
	
	public HealingCenter () {
	}

	public void healPlayer (Player player) {
		if (player.stats.getCurrentHP() == player.stats.getMaximumHP()) {
			System.out.println("You are already at full health.");
		} else if (player.stats.getCurrentHP() == 1) {
			player.stats.setCurrentHP(player.stats.getMaximumHP());
			System.out.println("Woah, you only have 1 HP left. You really should be more careful. You're HP has been fully restored! Please come again!");
		} else {
			player.stats.setCurrentHP(player.stats.getMaximumHP());
			System.out.println("Thank you for visiting the healing center. You're HP has been fully restored! Please come again!");
		}
	}
}
