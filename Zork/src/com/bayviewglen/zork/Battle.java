package com.bayviewglen.zork;

import java.util.Scanner;

public class Battle {
	
	public PlayerStats playe;
	public Entity entity;
	
	public Battle(PlayerStats p, Entity ent) {
		playe = p;
		entity = ent;
	}
	
	
	public PlayerStats getPlayer(){
		return playe;
		
	}
	
	public Entity getEntity() {
		return entity;
	}
	
	public void startBattle() { // Consumables count as a turn
		
		int storedPlayerSpeed = playe.getSpeed();
		int storedEntitySpeed = entity.getSpeed();
		
		System.out.println("You have been engaged by "+entity.getName()+"!");
		
		while(playe.isAlive()&&entity.isAlive()) { // TODO: And while running away is not true
			
			
			
			if (playe.getSpeed()>=entity.getSpeed()) { // Multiply by weapon speed
				System.out.println(playe.getName()+" will now move!");
				
				System.out.println("Take an action!");
				battleParser();
				
				if (entity.isAlive()) {
					if (entity.getSpeed()<storedEntitySpeed) {
						entity.setSpeed(entity.getSpeed()+20);
					}
					System.out.println(entity.getName()+" will now move!");
					
					damageDealer(entity,playe);
					
				}
				
			}
			else if (entity.getSpeed()>playe.getSpeed()) { // Multiply by weapon speed
				System.out.println(entity.getName()+" will now move!");
				
				if (playe.getSpeedHint()) {
					System.out.println("Hint: The enemy has the first move on this turn, consider upgrading your speed stat!");
					playe.setSpeedHint(false);
				}
			
				
				damageDealer(entity,playe);
				
				if (playe.isAlive()) {
					
					System.out.println("Take an action!");
					battleParser();
					
					if (playe.getSpeed()<storedPlayerSpeed) {
						playe.setSpeed(playe.getSpeed()+20);
				}
				
			}
			}
		}
		
		if (playe.isAlive()==false) {
			System.out.println("A dark day for the Republic. You are defeated...");
		}
		else if (entity.isAlive()==false) {
			System.out.println("You have defeated level "+entity.getLvl()+" "+entity.getName()+"!");
		}
		
	}
	
	public void battleParser() { // TODO: Add loop and ability to observe the enemy, which will show it's stats, take care of fail cases
		// Attack, hit, swing, stab, bludgeon, slash, strike, power, run, use (item), consume, eat, drink, help
		final String[] WORDS = {"use","block"};
		Scanner input = new Scanner(System.in);
		String parseMe = input.nextLine().toLowerCase();
		
		if (parseMe.equals("block")||parseMe.equals("block attack")||parseMe.equals("block hit")) {
			playe.setIsBlocking(true);
		}
		else if (parseMe.equals("attack")||parseMe.equals("hit")||parseMe.equals("swing")||parseMe.equals("stab")||
				parseMe.equals("bludgeon")||parseMe.equals("slash")||parseMe.equals("strike")) {
			damageDealer(playe,entity);
		}
		else if (parseMe.equals("power attack")) {
			// Damage increase
		}
		/*else if (parseMe=="Attack") {
			damageDealer(player,entity);
		}*/
		
		// Create parser or use Curills
		
	}
	
	public void damageDealer (Entity attacker, Entity defender) { // increase by 2.5% each time
										// damage = getItemDamage()*(attacker.getStrength()/40)*((40-defender.getDefense())/40)
		
		System.out.println(attacker.attackText()+defender.getName());
		
		
		
		if (didHit(attacker)) { // Accuracy calculator
			if (defender.getIsBlocking()==false) {
				if (/*getItemDamage()*/10*(attacker.getStrength()/40)*((40-defender.getDefence())/40)<=0) {
					defender.setCurrHP(defender.getCurrHP()-1);
					System.out.println(defender.getName()+" took 1 damage!"); // Minimum of one damage
					System.out.println(defender.getName()+"'s health: "+ defender.getCurrHP());
				}
				else {
					System.out.println(defender.getName()+" took "+/*getItemDamage()*/10*(attacker.getStrength()/40)*((40-defender.getDefence())/40)+" damage!");
					defender.setCurrHP(defender.getCurrHP()-/*getItemDamage()*/(10*(attacker.getStrength()/40)*((40-defender.getDefence())/40)));
					defender.setCurrHP(defender.getCurrHP()-1);
					System.out.println(defender.getName()+"'s health: "+ defender.getCurrHP());
				}
			}
			else {
				System.out.println(attacker.getName()+"'s attack was successfully blocked by "+defender.getName()+"!");
				System.out.println("The successful block has caught "+attacker.getName()+" offguard. Strike Now!");
				attacker.setSpeed(attacker.getSpeed()-20);
				playe.setIsBlocking(false); // Resets their blocking states for the next combat turn
				entity.setIsBlocking(false);
			}
			
			playSound("/Users/adouglas/git/Zork/Zork/Test1.mp3"); // Doesn't work
			
			/*
			// open the sound file as a Java input stream
		    String gongFile = "/Users/adouglas/git/Zork/Zork/Recording.m4a";
		    InputStream in = new FileInputStream(gongFile);

		    // create an audiostream from the inputstream
		    AudioStream audioStream = new AudioStream(in);

		    // play the audio clip with the audioplayer class
		    AudioPlayer.player.start(audioStream);
		    */
			
			
		}
		else {
			System.out.println(attacker.getName()+" completely missed their target!");
		}
		
		
	}
	
	public boolean didHit(Entity ent) {
		int random = (int) (Math.random()*100+ent.getAccuracy()); // Accuracy raises the minimum value TODO: subtract by item accuracy
		
		if (((random))>50) {
			return true;
		}
		else {
			return false;
		}
		
	}
	
	public void playSound(String path) {
		
	}
}