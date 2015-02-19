package com.matoking.dungeoncrawler.ui;

import java.awt.Graphics;
import java.awt.Image;
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
                return null;
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
        
        if (image == null) {
            return null;
        }
        
        BufferedImage resized = new BufferedImage(image.getWidth()*factor, image.getHeight()*factor, BufferedImage.TYPE_INT_ARGB);
        
        Graphics g = resized.getGraphics();
        g.drawImage(image, 0, 0, resized.getWidth(), resized.getHeight(), null);
        g.dispose();
        
        return resized;
    }
}
