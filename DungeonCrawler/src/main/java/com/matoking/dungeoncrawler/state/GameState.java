package com.matoking.dungeoncrawler.state;

public class GameState {
    private GameMap gameMap;
    
    private Player player;
    
    public GameState() {
        this.gameMap = new GameMap();
        
        this.player = new Player(this.gameMap, 5, 5);
    }
}
