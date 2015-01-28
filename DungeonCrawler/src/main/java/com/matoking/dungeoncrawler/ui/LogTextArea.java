package com.matoking.dungeoncrawler.ui;

import com.matoking.dungeoncrawler.state.GameState;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.util.ArrayList;
import javax.swing.JPanel;
import javax.swing.JTextArea;

/**
 * Displays the game log
 */
public class LogTextArea extends JPanel {
    private final static int TEXT_ROWS = 34;
    private final static int TEXT_COLUMNS = 40;
    
    private GameState gameState;
    
    private JTextArea textArea;
    
    public LogTextArea(GameState gameState, int x, int y, int width, int height) {
        this.gameState = gameState;
        
        this.setBackground(Color.BLACK);
        
        this.setFocusable(false);
        
        this.setLocation(x, y);
        this.setSize(new Dimension(width, height));
        
        // Format the text area
        this.textArea = new JTextArea(TEXT_ROWS, TEXT_COLUMNS);
        this.textArea.setLocation(x, y);
        this.textArea.setFont(new Font(Font.MONOSPACED, Font.PLAIN, 12));
        this.textArea.setLineWrap(true);
        this.textArea.setWrapStyleWord(true);
        this.textArea.setText("Welcome to Dungeon Crawler! Press arrow keys to move.");
        this.textArea.setEditable(false);
        this.textArea.setFocusable(false);
        this.textArea.setBackground(Color.BLACK);
        this.textArea.setForeground(Color.WHITE);
        
        this.add(this.textArea);
        
        this.update();
    }
    
    /**
     * Update the text area
     */
    public void update() {
        ArrayList<String> messages = this.gameState.getGameLog().getMessages();
        
        String text = "";
        
        for (int i=messages.size()-1; i >= 0; i--) {
            String message = messages.get(i);
            
            text += message + "\n\n";
        }
        
        this.textArea.setText(text);
    }
    
}
