package com.matoking.dungeoncrawler.ui;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileNotFoundException;
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
}
