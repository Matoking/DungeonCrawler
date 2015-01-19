package com.matoking.dungeoncrawler.ui;

import javax.swing.JFrame;

public class GameFrame extends JFrame {
    private MapPanel mapPanel;
    
    public GameFrame() {
        setTitle("Dungeon Crawler");
        setSize(300, 300);
        
        this.mapPanel = new MapPanel();
    }
}
