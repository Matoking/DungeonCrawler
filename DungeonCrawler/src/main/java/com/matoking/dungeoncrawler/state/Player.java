package com.matoking.dungeoncrawler.state;

/**
 * The main Player class
 * 
 * @author matoking
 */
public class Player {
    private GameMap gameMap;
    
    private int x;
    private int y;
    
    public Player(GameMap gameMap, int x, int y) {
        this.gameMap = gameMap;
        
        this.x = x;
        this.y = y;
    }
    
    /**
     * Move in a direction if the direction isn't blocked by a wall
     * 
     * @param direction 
     */
    public void move(Direction direction) {
        Coordinate newCoordinates = Coordinate.getNewCoordinates(direction, x, y);
        
        int newX = newCoordinates.getX();
        int newY = newCoordinates.getY();
        
        if (this.gameMap.isTileBlocked(newX, newY)) {
            return;
        }
        
        if (newX < 0 || newX > this.gameMap.getWidth()-1 ||
            newY < 0 || newY > this.gameMap.getHeight()-1) {
            return;
        }
        
        this.x = newX;
        this.y = newY;
    }

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }
}
