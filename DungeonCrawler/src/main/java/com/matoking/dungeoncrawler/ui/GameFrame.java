package com.matoking.dungeoncrawler.ui;

import com.matoking.dungeoncrawler.state.GameState;
import javax.swing.JFrame;

public class GameFrame extends JFrame {
    public static final int SIDE_PANEL_WIDTH = 400;
    
    private GameState gameState;
    
    private MapPanel mapPanel;
    private LogPanel logPanel;
    private StatPanel statPanel;
    
    private SpriteCache spriteCache;
    private Controls controls;
    
    public GameFrame(GameState gameState) {
        this.setTitle("Dungeon Crawler");
        this.setSize(Sprite.TILE_WIDTH*25+SIDE_PANEL_WIDTH,
                     Sprite.TILE_HEIGHT*25);
        
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        
        this.setResizable(false);
        
        this.gameState = gameState;
        
        this.spriteCache = new SpriteCache();
        this.controls = new Controls(this);
        
        this.mapPanel = new MapPanel(this.gameState, this, 0, 0, 25, 25);
        this.logPanel = new LogPanel(this.gameState, this.mapPanel.getWidth(), 0,
                                           SIDE_PANEL_WIDTH, this.getHeight() / 2);
        this.statPanel = new StatPanel(this.gameState, this,
                                       this.mapPanel.getWidth(), this.logPanel.getHeight(),
                                       SIDE_PANEL_WIDTH, this.getHeight() / 2);
        
        this.add(this.statPanel);
        this.add(this.logPanel);
        this.add(this.mapPanel);
        
        // Force focus only on this component to make sure
        // the key listener is always responsive
        this.setFocusable(true);
        this.addKeyListener(this.controls);
        
        this.repaint();
    }
    
    /**
     * Updates panels which need to be explicitly called
     */
    public void update() {
        this.logPanel.update();
        this.statPanel.update();
        
        this.repaint();
    }

    public StatPanel getStatPanel() {
        return statPanel;
    }

    public LogPanel getLogPanel() {
        return logPanel;
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