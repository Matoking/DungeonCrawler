package com.matoking.dungeoncrawler.ui;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.File;
import java.util.HashMap;
import java.util.Map;
import javax.imageio.ImageIO;

/**
 * Sprite cache which loads tile sprites and stores them for later use
 * 
 * @author matoking
 */
public class SpriteCache {
    Map<String, BufferedImage> sprites;
    
    public SpriteCache() {
        this.sprites = new HashMap<String, BufferedImage>();
    }
    
    /**
     * Loads the requested image if it hasn't been loaded already and then returns it
     * as a BufferedImage
     * 
     * @param imageName     Name of the image to load
     * @return              The loaded image as BufferedImage, null if the image is not found
     */
    public BufferedImage getImage(String imageName) {
        if (this.sprites.containsKey(imageName)) {
            return this.sprites.get(imageName);
        } else {
            try {
                File file = new File(String.format("%s%s", System.getProperty("user.dir"),
                                                           String.format("/resources/img/%s.png", imageName)));
                
                BufferedImage image = ImageIO.read(file);
                
                this.sprites.put(imageName, image);
            } catch (Exception e) {
                // If image couldn't be loaded, load the generated error image instead
                return this.getErrorImage();
            }
            
            return this.sprites.get(imageName);
        }
    }
    
    /**
     * Loads the requested image and resizes it by the given factor
     * 
     * @param imageName Image name to load
     * @param factor The zoom factor, eg. factor of 2 makes the image's resolution twice as large
     * 
     * @return Resized image as a BufferedImage, null if image couldn't be loaded
     */
    public BufferedImage getResizedImage(String imageName, int factor) {
        BufferedImage image = this.getImage(imageName);
        
        BufferedImage resized = new BufferedImage(image.getWidth()*factor, image.getHeight()*factor, BufferedImage.TYPE_INT_ARGB);
        
        Graphics g = resized.getGraphics();
        g.drawImage(image, 0, 0, resized.getWidth(), resized.getHeight(), null);
        g.dispose();
        
        return resized;
    }
    
    /**
     * Returns a generated image to be used in case of an error
     * (eg. the actual image file can't be loaded)
     * 
     * @return Image as a BufferedImage
     */
    public BufferedImage getErrorImage() {
        BufferedImage errorImage = new BufferedImage(Sprite.TILE_WIDTH, Sprite.TILE_HEIGHT, BufferedImage.TYPE_INT_ARGB);
        
        Graphics g = errorImage.getGraphics();
        
        // Draw a red X mark
        g.setColor(Color.RED);
        g.drawLine(0, 0, Sprite.TILE_WIDTH, Sprite.TILE_HEIGHT);
        g.drawLine(0, Sprite.TILE_HEIGHT, Sprite.TILE_WIDTH, 0);
        
        g.dispose();
        
        return errorImage;
    }
}
