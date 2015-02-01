package com.matoking.dungeoncrawler.ui;

import com.matoking.dungeoncrawler.state.GameState;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.util.ArrayList;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;

/**
 * Displays the game log
 */
public class LogPanel extends JPanel {
    private final static int TEXT_ROWS = 34;
    private final static int TEXT_COLUMNS = 40;
    
    private GameState gameState;
    
    private JLabel logText;
    
    public LogPanel(GameState gameState, int x, int y, int width, int height) {
        this.gameState = gameState;
        
        this.setBackground(Color.BLACK);
        
        this.setLocation(x, y);
        this.setSize(width, height);
        
        // Format the text area
        this.logText = new JLabel("<html>");
        
        // setSize accepts setSize(int x, int y)
        // setMaximumSize only accepts setMaximumSize(Dimension)
        // :/
        this.logText.setSize(width+50, height+50);
        this.logText.setMaximumSize(new Dimension(width, height));
        
        this.logText.setLocation(x, y);
        this.logText.setFont(new Font(Font.MONOSPACED, Font.PLAIN, 12));
        this.logText.setHorizontalAlignment(SwingConstants.LEFT);
        
        this.logText.setText("");
        this.logText.setBackground(Color.BLACK);
        this.logText.setForeground(Color.WHITE);
        
        this.add(this.logText);
        
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
            
            text += message + "<br><br>";
        }
        
        // Subtracting 80 pixels from width makes sure the text doesn't clip for
        // whatever reason
        this.logText.setText(String.format("<html><body style='width: %dpx;'>%s</body></html>", this.getWidth()-80, text));
    }
    
}
