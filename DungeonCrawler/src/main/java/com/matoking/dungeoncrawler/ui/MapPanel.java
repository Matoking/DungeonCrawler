package com.matoking.dungeoncrawler.ui;

import com.matoking.dungeoncrawler.state.Entity;
import com.matoking.dungeoncrawler.state.GameMap;
import com.matoking.dungeoncrawler.state.GameState;
import com.matoking.dungeoncrawler.state.Player;
import com.matoking.dungeoncrawler.state.Tile;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import javax.swing.JPanel;

/**
 * The player's current view is rendered on this panel
 */
public class MapPanel extends JPanel {
    private GameState gameState;
    private GameFrame gameFrame;
    
    private int rows;
    private int columns;
    
    public MapPanel(GameState gameState, GameFrame gameFrame, int x, int y, int rows, int columns) {
        this.gameState = gameState;
        this.gameFrame = gameFrame;
        
        this.setFocusable(false);
        
        this.rows = rows;
        this.columns = columns;
        
        this.setSize(new Dimension(TileSprite.TILE_WIDTH*rows,
                                   TileSprite.TILE_HEIGHT*columns));
        
        this.setLocation(x, y);
        
        this.setBackground(Color.BLACK);
    }
    
    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        
        this.render(g);
    }
    
    /**
     * Renders the map screen
     * Should be called when the player moves
     */
    public void render(Graphics g) {
        this.renderTiles(g);
        this.renderEntities(g);
        this.renderPlayer(g);
    }
    
    /**
     * Renders all of the tiles in view
     * 
     * @param g 
     */
    public void renderTiles(Graphics g) {
        for (int x=0; x < this.getRows(); x++) {
            for (int y=0; y < this.getColumns(); y++) {
                this.renderTile(g, x, y);
            }
        }
    }    
    
    /**
     * Renders the tile
     * 
     * @param g
     * @param x X-coordinate of the tile in the visible map grid
     * @param y Y-coordinate of the tile in the visible map grid
     */
    public void renderTile(Graphics g, int x, int y) {
        Player player = this.gameState.getPlayer();
        GameMap gameMap = this.gameState.getGameMap();
        SpriteCache spriteCache = this.gameFrame.getSpriteCache();
        
        int offsetX = player.getX() - (this.getRows() / 2);
        int offsetY = player.getY() - (this.getColumns() / 2);
        
        int realX = offsetX + x;
        int realY = offsetY + y;
        
        int spriteX = x * TileSprite.TILE_WIDTH;
        int spriteY = y * TileSprite.TILE_HEIGHT;
        
        Tile tile = gameMap.getTile(realX, realY);
        
        if (tile != null) {
            // Render the tile image
            if (!tile.getImageName().equals("empty")) {
                BufferedImage tileImage = spriteCache.getImage(tile.getImageName());

                g.drawImage(tileImage, spriteX, spriteY, TileSprite.TILE_WIDTH, TileSprite.TILE_HEIGHT, null);
            }
        }
    }
    
    /**
     * Render entities in the view 
     */
    public void renderEntities(Graphics g) {
        ArrayList<Entity> entities = this.gameState.getGameMap().getEntities();
        
        Player player = this.gameState.getPlayer();
        SpriteCache spriteCache = this.gameFrame.getSpriteCache();
        
        int offsetX = player.getX() - (this.getRows() / 2);
        int offsetY = player.getY() - (this.getColumns() / 2);
        
        for (Entity entity : entities) {
            // Render only visible entities
            if (entity.getX() >= offsetX && entity.getX() <= offsetX + this.getRows() &&
                entity.getY() >= offsetY && entity.getY() <= offsetY + this.getColumns()) {
                BufferedImage entityImage = spriteCache.getImage(entity.getImageName());
                
                int spriteX = (entity.getX() - offsetX) * TileSprite.TILE_WIDTH;
                int spriteY = (entity.getY() - offsetY) * TileSprite.TILE_HEIGHT;
                
                g.drawImage(entityImage, spriteX, spriteY, TileSprite.TILE_WIDTH, TileSprite.TILE_HEIGHT, null);            
            }
        }
    }
    
    public void renderPlayer(Graphics g) {
        SpriteCache spriteCache = this.gameFrame.getSpriteCache();
        
        int spriteX = (this.getRows() / 2) * TileSprite.TILE_WIDTH;
        int spriteY = (this.getColumns() / 2) * TileSprite.TILE_WIDTH;
        
        g.drawImage(spriteCache.getImage("player"), spriteX, spriteY, TileSprite.TILE_WIDTH, TileSprite.TILE_HEIGHT, null);
    }
    
    public int getRows() {
        return rows;
    }

    public int getColumns() {
        return columns;
    }
    
}
