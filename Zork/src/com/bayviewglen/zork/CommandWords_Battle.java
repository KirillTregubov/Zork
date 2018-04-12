package com.bayviewglen.zork;

public class CommandWords_Battle extends CommandWords {

	private static final String validCommands[][] = {
			{"help", "", "Prints the help message. Usage: \"help\""},
			{"list", "", "Lists things. Usage: \"list commands\""}, // add an example if list gains more uses
			{"go", "", "Allows you to go places. Usage: \"go west\", \"go down\""}, {"walk", "", "Allows you to walk places. Usage: \"walk west\", \"walkT down\""},
			{"give", "", "\"Cheater\" command. Remove before final release!"},
			{"eat", "", "Allows you to eat stuff. Usage: \"eat apple\", \"eat sword\""}, {"consume", "", "Allows you to consume stuff. Usage: \"consume apple\", \"consume sword\""},
			{"look", "3", "Allows you to look at things. Usage: \"look at inventory\", \"look at red apple\""}, {"inspect", "", "Allows you to inspect things. Usage: \"inspect inventory\", \"inspect red apple\""},
			{"take", "", "Allows you to take things. Usage: \"take red apple\", \"take sword\""}, {"pick", "3", "Allows you to pick up things. Usage: \"pick up red apple\", \"pick up basic sword\""},
			{"save", "", "Saves the current state of your playthrough. Usage: \"save\""},
			{"quit", "", "Quits playing the game. Usage: \"quit game\", \"quit playing\""}, {"stop", "", "Stops playing the game. Usage: \"stop game\", \"stop playing\""},
			{"attack","","Attacks"}, {"hit","","Attacks"},
			{"run","","Runs from combat"
			}
	};
	
	public CommandWords_Battle () {
		super();
		
	}
	
	
	
}
