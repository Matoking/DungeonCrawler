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
    
    static public String getGameStartMessage() {
        return getMessageFromList("game_start");
    }
    
    static public String getKeyPickupMessage() {
        return getMessageFromList("key_pickup");
    }
    
    /**
     * Get a random message from the provided list, which is loaded from a
     * text file of the same name
     * @param list String that corresponds to a list and the text file
     * @return A random String from the list
     */
    static public String getMessageFromList(String list) {
        // Load the game messages from a file if it hasn't been done already
        if (!gameMessages.containsKey(list)) {
            ArrayList<String> messages = new ArrayList<String>();
            
            try {
                File file = new File(String.format("%s%s", System.getProperty("user.dir"),
                                                           String.format("/resources/text/%s.txt", list)));
                
                Scanner scanner = new Scanner(file);
                
                while (scanner.hasNextLine()) {
                    String message = scanner.nextLine();
                    messages.add(message);
                }
                
                gameMessages.put(list, messages);
            } catch (Exception e) {
                return String.format("[Couldn't load messages from %s.txt]", list);
            }
        }
        
        ArrayList<String> messages = gameMessages.get(list);
        
        int randInt = random.nextInt(messages.size());
        
        return messages.get(randInt);
    }
}
