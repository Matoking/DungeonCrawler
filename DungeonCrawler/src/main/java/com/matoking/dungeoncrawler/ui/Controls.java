package com.matoking.dungeoncrawler.ui;

import com.matoking.dungeoncrawler.state.Direction;
import com.matoking.dungeoncrawler.state.GameState;
import com.matoking.dungeoncrawler.state.Player;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

/**
 * Handles the game's controls. Namely, it acts as a KeyListener and reacts to
 * arrow key presses
 */
public class Controls implements KeyListener {

    private GameFrame gameFrame;
    
    public Controls(GameFrame gameFrame) {
        this.gameFrame = gameFrame;
    }
    
    @Override
    public void keyTyped(KeyEvent ke) { }

    @Override
    public void keyReleased(KeyEvent ke) { }
    
    /**
     * Responds to player's key presses
     * @param ke 
     */
    @Override
    public void keyPressed(KeyEvent ke) {
        GameState gameState = this.gameFrame.getGameState();
        
        switch (ke.getKeyCode()) {
            case KeyEvent.VK_UP:
                gameState.performMove(Direction.UP);
                break;
                
            case KeyEvent.VK_RIGHT:
                gameState.performMove(Direction.RIGHT);
                break;
                
            case KeyEvent.VK_DOWN:
                gameState.performMove(Direction.DOWN);
                break;
                
            case KeyEvent.VK_LEFT:
                gameState.performMove(Direction.LEFT);
                break;
                
            case KeyEvent.VK_R:
                // R key restarts the game
                gameState.startGame();
                break;
        }
        
        this.gameFrame.update();
    }
    
}
