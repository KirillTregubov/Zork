package com.bayviewglen.zork;

import java.util.ArrayList;

public class Drops {

	private boolean hasDrops;
	private ArrayList<String> itemNames;
	private ArrayList<Double> chances;

	public Drops(String dropString) {
		itemNames = new ArrayList<String>();
		chances = new ArrayList<Double>();

		//System.out.println(dropString);
		if (!dropString.isEmpty()) {
			hasDrops = true;
			for (String drop : dropString.split("; ")) {
				String[] dropValues = drop.split("-");
				itemNames.add(dropValues[0]);
				chances.add(Double.parseDouble(dropValues[1]));
			}
		}
	}

	public boolean hasDrops() {
		return hasDrops;
	}

	public String calculate(Player player) {
		String returnString = "Where your enemy once stood now lies... ";

		ArrayList<String> items = new ArrayList<String>();
		for (int i = 0; i < chances.size(); i++) {
			if (Math.random() <= chances.get(i)) {
				player.inventory.addDrop(itemNames.get(i));
				items.add(itemNames.get(i));
			}
		}

		if (items.isEmpty())
			returnString += "nothing.";
		else {
			for (int i = 0; i < items.size(); i++) {
				returnString += items.get(i);
				if (i < items.size() - 1) returnString += ", ";
				else returnString += ". ";
			}
			returnString += "You picked them up out of curiosity (if you could).";
		}
		
		return returnString;
	}

}
