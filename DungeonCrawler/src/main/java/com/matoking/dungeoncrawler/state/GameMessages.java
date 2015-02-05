package com.matoking.dungeoncrawler.state;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Random;
import java.util.Scanner;

/**
 * A static class for providing in-game messages
 */
public class GameMessages {
    static private HashMap<String, ArrayList<String>> gameMessages = new HashMap<String, ArrayList<String>>();
    static private Random random = new Random();
    
    /**
     * Get a "game started" message
     * 
     * @return Game log message as String
     */
    static public String getGameStartMessage() {
        return getMessageFromList("game_start");
    }
    
    /**
     * Get a "key picked up" message
     * 
     * @return Game log message as String
     */
    static public String getKeyPickupMessage() {
        return getMessageFromList("key_pickup");
    }
    
    /**
     * Get a "player hits the skeleton" message
     * 
     * @param damage How much damage player dealt to the skeleton
     * @return Game log message as String
     */
    static public String getPlayerHitsSkeleton(int damage) {
        return String.format(getMessageFromList("player_hits_skeleton"), damage);
    }
    
    /**
     * Get a "player kills the skeleton" message
     * 
     * @param damage How much damage player dealt to the skeleton
     * @return Game log message as String
     */
    static public String getPlayerKillsSkeleton(int damage) {
        return String.format(getMessageFromList("player_kills_skeleton"), damage);
    }
    
    static public String getSkeletonStartsChase() {
        return getMessageFromList("skeleton_starts_chase");
    }
    
    static public String getSkeletonEndsChase() {
        return getMessageFromList("skeleton_ends_chase");
    }
    
    /**
     * Get a random message from the provided list, which is loaded from a
     * text file of the same name
     * 
     * @param list String that corresponds to a list and the text file
     * @return A random String from the list
     */
    static public String getMessageFromList(String list) {
        // Load the game messages from a file if it hasn't been done already
        if (!gameMessages.containsKey(list)) {
            ArrayList<String> messages = new ArrayList<String>();
            
            try {
                File file = new File(String.format("%s%s", System.getProperty("user.dir"),
                                                           String.format("/resources/messages/%s.txt", list)));
                
                Scanner scanner = new Scanner(file);
                
                while (scanner.hasNextLine()) {
                    String message = scanner.nextLine();
                    
                    // If the message starts with a #,
                    // skip it
                    if (message.charAt(0) == '#') {
                        continue;
                    }
                    
                    messages.add(message);
                }
                
                gameMessages.put(list, messages);
            } catch (Exception e) {
                return String.format("[Couldn't load messages from %s.txt]", list);
            }
        }
        
        // The list of messages has been loaded to gameMessages[list]
        // Now, pick a random message from that list
        ArrayList<String> messages = gameMessages.get(list);
        
        int randInt = random.nextInt(messages.size());
        
        return messages.get(randInt);
    }
}
