package com.matoking.dungeoncrawler.generator;

import com.matoking.dungeoncrawler.state.Coordinate;
import com.matoking.dungeoncrawler.state.TileType;
import java.awt.Rectangle;
import java.awt.geom.Line2D;

/**
 * A class representing corridors which connect rooms
 *
 * @author matoking
 */
public class Corridor {
    private Line2D.Double line;
    
    private TileType floorType = TileType.STONE_FLOOR;

    public Corridor(Coordinate start, Coordinate end) {
        this.line = new Line2D.Double(start.getX(), start.getY(),
                end.getX(), end.getY());
    }
    
    public Corridor(Coordinate start, Coordinate end, TileType floorType) {
        this(start, end);
        this.setFloorType(floorType);
    }

    /**
     * Get a rectangle representing the corridor
     *
     * @return Corridor's shape and location as a rectangle
     */
    public Rectangle getRectangle() {
        return this.line.getBounds();
    }

    public TileType getFloorType() {
        return floorType;
    }

    public void setFloorType(TileType floorType) {
        this.floorType = floorType;
    }

    public int getX() {
        return (int) this.getRectangle().getX();
    }

    public int getY() {
        return (int) this.getRectangle().getY();
    }

    public int getWidth() {
        if ((int) this.getRectangle().getWidth() == 0) {
            return 1;
        } else {
            return (int)this.getRectangle().getWidth() + 1;
        }
    }

    public int getHeight() {
        if ((int) this.getRectangle().getHeight() == 0) {
            return 1;
        } else {
            return (int)this.getRectangle().getHeight() + 1;
        }
    }
}
