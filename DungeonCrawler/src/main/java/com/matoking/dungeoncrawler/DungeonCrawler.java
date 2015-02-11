package com.matoking.dungeoncrawler;

import com.matoking.dungeoncrawler.state.GameMap;
import com.matoking.dungeoncrawler.state.GameState;
import com.matoking.dungeoncrawler.ui.GameFrame;

import javax.swing.SwingUtilities;

public class DungeonCrawler {
    public static void main(String[] args) {
        // Start the game
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                GameState gameState = new GameState();
                gameState.startGame();
                
                gameState.getMapGenerator().generateMap(GameMap.MAP_DEFAULT_WIDTH, GameMap.MAP_DEFAULT_HEIGHT);
                
                GameFrame gameFrame = new GameFrame(gameState);
                gameFrame.setVisible(true);
            }
        });
    }
}
