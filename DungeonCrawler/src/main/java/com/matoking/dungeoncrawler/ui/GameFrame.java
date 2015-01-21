package com.matoking.dungeoncrawler.ui;

import com.matoking.dungeoncrawler.state.GameState;
import javax.swing.JFrame;

public class GameFrame extends JFrame {
    private GameState gameState;
    
    private MapPanel mapPanel;
    private SpriteCache spriteCache;
    private Controls controls;
    
    public GameFrame(GameState gameState) {
        this.setTitle("Dungeon Crawler");
        this.setSize(TileSprite.TILE_WIDTH*25,
                     TileSprite.TILE_HEIGHT*25);
        
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        
        this.gameState = gameState;
        
        this.mapPanel = new MapPanel(this.gameState, this, 0, 0, 25, 25);
        this.spriteCache = new SpriteCache();
        this.controls = new Controls(this);
        
        this.addKeyListener(this.controls);
        
        this.add(this.mapPanel);
    }

    public MapPanel getMapPanel() {
        return mapPanel;
    }

    public GameState getGameState() {
        return gameState;
    }

    public SpriteCache getSpriteCache() {
        return spriteCache;
    }
}