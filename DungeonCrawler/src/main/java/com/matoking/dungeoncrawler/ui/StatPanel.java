package com.matoking.dungeoncrawler.ui;

import com.matoking.dungeoncrawler.state.GameState;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;

/**
 * Panel that shows player's health and items
 */
public class StatPanel extends JPanel {
    private static final int ICON_PADDING = 5 * Sprite.ZOOM_FACTOR;
    
    private GameState gameState;
    private GameFrame gameFrame;
    
    private JLabel healthLabel;
    
    public StatPanel(GameState gameState, GameFrame gameFrame, int x, int y, int width, int height) {
        this.gameState = gameState;
        this.gameFrame = gameFrame;
        
        this.setLayout(null);
        
        this.setLocation(x, y);
        this.setSize(width, height);
        this.setBackground(Color.BLACK);
        
        this.healthLabel = new JLabel("420");
        
        this.healthLabel.setHorizontalAlignment(SwingConstants.LEFT);
        this.healthLabel.setFont(new Font(Font.MONOSPACED, Font.PLAIN, 12*Sprite.ZOOM_FACTOR));
        this.healthLabel.setForeground(Color.WHITE);
        
        this.healthLabel.setIconTextGap(ICON_PADDING);
        this.healthLabel.setIcon(new ImageIcon(this.gameFrame.getSpriteCache().getResizedImage("ui_heart", Sprite.ZOOM_FACTOR)));
        
        this.healthLabel.setLocation(ICON_PADDING, this.getHeight()-(Sprite.UI_SPRITE_HEIGHT*2));
        this.healthLabel.setSize(width, Sprite.UI_SPRITE_HEIGHT);
        
        this.add(this.healthLabel);
        
        this.update();
    }
    
    public void update() {
        this.healthLabel.setText(String.format("%d", this.gameState.getPlayer().getHealth()));
    }
}
