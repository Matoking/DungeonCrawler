package com.matoking.dungeoncrawler.ui;

import com.matoking.dungeoncrawler.state.GameState;
import java.awt.Color;
import java.awt.Dimension;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;

/**
 * Panel that shows player's health and items
 */
public class StatPanel extends JPanel {
    private GameState gameState;
    private GameFrame gameFrame;
    
    private JLabel healthIcon;
    
    public StatPanel(GameState gameState, GameFrame gameFrame, int x, int y, int width, int height) {
        this.gameState = gameState;
        this.gameFrame = gameFrame;
        
        this.setLocation(x, y);
        this.setSize(new Dimension(width, height));
        
        this.setBackground(Color.BLACK);
        
        this.healthIcon = new JLabel(new ImageIcon(this.gameFrame.getSpriteCache().getImage("ui_heart")));
        
        this.healthIcon.setLocation(this.getWidth()+50, 350);
        
        this.add(this.healthIcon);
    }
}
