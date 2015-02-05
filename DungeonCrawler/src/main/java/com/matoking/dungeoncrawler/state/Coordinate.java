package com.matoking.dungeoncrawler.state;

/**
 * Convenience class for retrieving new coordinates based on the direction
 * to move to
 */
public class Coordinate implements Comparable {
    private int x;
    private int y;
    
    private int weight;
    
    public Coordinate(int x, int y, int weight) {
        this.x = x;
        this.y = y;
        
        this.weight = weight;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Coordinate other = (Coordinate) obj;
        if (this.x != other.x) {
            return false;
        }
        if (this.y != other.y) {
            return false;
        }
        return true;
    }
    
    public Coordinate(int x, int y) {
        this(x, y, 0);
    }

    public int getWeight() {
        return weight;
    }

    public void setWeight(int weight) {
        this.weight = weight;
    }
    
    /**
     * Moves the coordinate to the provided direction by one step
     * 
     * @param direction Direction to move to
     */
    public void move(Direction direction) {
        this.x = Coordinate.getNewX(direction, this.x);
        this.y = Coordinate.getNewY(direction, y);
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

    @Override
    public int compareTo(Object t) {
        Coordinate comp = (Coordinate)t;
        
        return Integer.compare(this.getWeight(), comp.getWeight());
    }
}
