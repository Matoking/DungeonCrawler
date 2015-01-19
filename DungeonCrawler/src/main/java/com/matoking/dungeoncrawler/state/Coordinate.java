package com.matoking.dungeoncrawler.state;

public class Coordinate {
    private int x;
    private int y;
    
    public Coordinate(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }
    
    static public int getNewX(Direction direction, int x) {
        if (direction == Direction.RIGHT) {
            return x+1;
        } else if (direction == Direction.LEFT) {
            return x-1;
        } else {
            return x;
        }
    }
    
    static public int getNewY(Direction direction, int y) {
        if (direction == Direction.UP) {
            return y-1;
        } else if (direction == Direction.DOWN) {
            return y+1;
        } else {
            return y;
        }
    }
    
    static public Coordinate getNewCoordinates(Direction direction, int x, int y) {
        int newX = Coordinate.getNewX(direction, x);
        int newY = Coordinate.getNewY(direction, y);
        
        return new Coordinate(newX, newY);
    }
}
