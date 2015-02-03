package com.matoking.dungeoncrawler.ui;

/**
 *
 * @author matoking
 */
public class Sprite {
    static public int TILE_BITMAP_WIDTH = 9;
    static public int TILE_BITMAP_HEIGHT = 9;
    
    static public int UI_SPRITE_BITMAP_WIDTH = 11;
    static public int UI_SPRITE_BITMAP_HEIGHT = 11;
    
    static public int ZOOM_FACTOR = 3;
    
    static public int TILE_WIDTH = ZOOM_FACTOR * TILE_BITMAP_WIDTH;
    static public int TILE_HEIGHT = ZOOM_FACTOR * TILE_BITMAP_HEIGHT;
    
    static public int UI_SPRITE_WIDTH = ZOOM_FACTOR * UI_SPRITE_BITMAP_WIDTH;
    static public int UI_SPRITE_HEIGHT = ZOOM_FACTOR * UI_SPRITE_BITMAP_HEIGHT;
}
