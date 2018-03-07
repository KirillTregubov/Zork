package com.bayviewglen.zork;
/** "Command Words" Class - part of the "Zork" game.
 * 
 * Original Author:  Michael Kolling
 * Original Version: 1.0
 * Original Date:    July 1999
 * 
 * Current Authors: Kirill Tregubov, Zacharia Burrafato, Andrew Douglas, Alim Halani
 * Current Version: 0.1-alpha
 * Current Date:	March 2018
 * 
 * This class holds an enumeration of all command words known to the game.
 * It is used to recognise commands as they are typed in.
 *
 * This class is part of the "Zork" game.
 */

class CommandWords
{
    // a constant array that holds all valid command words
    private static final String validCommands[] = {
        "go", "quit", "help", "eat"
    };

    /**
     * Constructor - initialize the command words.
     */
    public CommandWords()
    {
        // nothing to do at the moment...
    }

    /**
     * Check whether a given String is a valid command word. 
     * Return true if it is, false if it isn't.
     **/
    public boolean isCommand(String aString)
    {
        for(int i = 0; i < validCommands.length; i++)
        {
            if(validCommands[i].equals(aString))
                return true;
        }
        // if we get here, the string was not found in the commands
        return false;
    }

    /*
     * Print all valid commands to System.out.
     */
    public void showAll() 
    {
        for(int i = 0; i < validCommands.length; i++)
        {
            System.out.print(validCommands[i] + "  ");
        }
        System.out.println();
    }
}