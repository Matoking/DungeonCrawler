package com.matoking.dungeoncrawler.ui;

import com.matoking.dungeoncrawler.state.GameState;
import javax.swing.JFrame;

public class GameFrame extends JFrame {
    private GameState gameState;
    
    private MapPanel mapPanel;
    private LogPanel logTextArea;
    private StatPanel statPanel;
    
    private SpriteCache spriteCache;
    private Controls controls;
    
    public GameFrame(GameState gameState) {
        this.setTitle("Dungeon Crawler");
        this.setSize(TileSprite.TILE_WIDTH*25+300,
                     TileSprite.TILE_HEIGHT*25);
        
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        
        this.setResizable(false);
        
        this.gameState = gameState;
        
        this.spriteCache = new SpriteCache();
        this.controls = new Controls(this);
        
        this.mapPanel = new MapPanel(this.gameState, this, 0, 0, 25, 25);
        this.logTextArea = new LogPanel(this.gameState, this.mapPanel.getWidth(), 0,
                                           300, this.getHeight() / 2);
        this.statPanel = new StatPanel(this.gameState, this,
                                       this.mapPanel.getWidth(), this.logTextArea.getWidth(),
                                       300, this.getHeight() / 2);
        
        this.add(this.logTextArea);
        this.add(this.mapPanel);
        this.add(this.statPanel);
        
        // Force focus only on this component to make sure
        // the key listener is always responsive
        this.setFocusable(true);
        this.addKeyListener(this.controls);
    }

    public LogPanel getLogTextArea() {
        return logTextArea;
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