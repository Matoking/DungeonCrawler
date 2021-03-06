package com.matoking.dungeoncrawler.state;

import java.util.ArrayList;

/**
 * Game log keeps track of game messages, such as when player picks up a key
 */
public class GameLog {
    private final static int MAX_MESSAGES = 20;
    
    private GameState gameState;
    
    private ArrayList<String> messages;
    
    public GameLog(GameState gameState) {
        this.gameState = gameState;
        
        this.messages = new ArrayList<String>();
    }
    
    /**
     * Add a message that will be displayed to the player
     * 
     * @param message The message as a String
     */
    public void addMessage(String message) {
        this.messages.add(message);
        
        while (this.messages.size() > MAX_MESSAGES) {
            this.messages.remove(0);
        }
    }

    public ArrayList<String> getMessages() {
        return this.messages;
    }
    
    /**
     * Clear all of the messages in the log
     */
    public void clearMessages() {
        this.messages.clear();
    }
}
