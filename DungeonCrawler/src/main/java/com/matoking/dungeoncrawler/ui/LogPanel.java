package com.matoking.dungeoncrawler.ui;

import com.matoking.dungeoncrawler.state.GameState;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.util.ArrayList;
import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.ScrollPaneConstants;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;

/**
 * Displays the game log
 */
public class LogPanel extends JPanel {
    private GameState gameState;
    
    private JLabel logText;
    private JScrollPane scrollPane;
    
    public LogPanel(GameState gameState, int x, int y, int width, int height) {
        this.gameState = gameState;
        
        // Set layout to BoxLayout and add padding
        this.setLayout(new BoxLayout(this, BoxLayout.PAGE_AXIS));
        this.setBorder(new EmptyBorder(5, 5, 5, 5));
        
        this.setBackground(Color.BLACK);
        
        this.setLocation(x, y);
        this.setSize(width, height);
        
        // Create and format the scroll pane
        this.scrollPane = new JScrollPane();
        this.scrollPane.setBorder(BorderFactory.createEmptyBorder());
        this.scrollPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_NEVER);
        this.scrollPane.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
        this.scrollPane.setBackground(Color.BLACK);
        this.scrollPane.setOpaque(false);
        this.scrollPane.getViewport().setOpaque(false);
        
        // Format the text area
        this.logText = new JLabel("<html>");
        
        // setSize accepts setSize(int x, int y)
        // setMaximumSize only accepts setMaximumSize(Dimension)
        // :/
        this.logText.setFont(new Font(Font.MONOSPACED, Font.PLAIN, 4*Sprite.ZOOM_FACTOR));
        this.logText.setHorizontalAlignment(SwingConstants.LEADING);
        this.logText.setVerticalAlignment(SwingConstants.TOP);
        this.logText.setVerticalTextPosition(JLabel.TOP);
        
        this.logText.setText("");
        this.logText.setBackground(Color.BLACK);
        this.logText.setForeground(Color.WHITE);
        
        this.scrollPane.setViewportView(this.logText);
        
        this.add(this.scrollPane);
        
        this.update();
    }
    
    /**
     * Update the text area
     */
    public void update() {
        ArrayList<String> messages = this.gameState.getGameLog().getMessages();
        
        String text = "";
        
        for (String message : messages) {
            text += message + "<br><br>";
        }
        
        // Subtracting 100 pixels from width makes sure the text doesn't clip for
        // whatever reason
        this.logText.setText(String.format("<html><body style='width: %dpx;'>%s</body></html>", this.getWidth()-100, text));
        
        this.validate();
        this.scrollPane.getVerticalScrollBar().setValue(this.scrollPane.getVerticalScrollBar().getMaximum());
    }
    
}
