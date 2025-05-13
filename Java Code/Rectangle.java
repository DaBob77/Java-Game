import java.awt.*;
import java.awt.image.*;

public class Rectangle {
    private BufferedImage image;
    private int xPos; //xPos and yPos for the bottom right point of the rectangle
    private int yPos;
    private int width;
    private int height;
    private boolean ignoreCollisions;

    public Rectangle(BufferedImage image, int xPos, int yPos, int width, int height, boolean ignoreCollisions) { //Temperary image before we add an actual map
        this.image = image;
        this.xPos = xPos;
        this.yPos = yPos;
        this.width = width;
        this.height = height;
        this.ignoreCollisions = ignoreCollisions;
    }
    
    
    public boolean intersects(Rectangle other) {
        if (other == null) return false;
        // Check for non-overlap
        if (this.xPos + this.width <= other.xPos ||    // this is left of other
            this.xPos >= other.xPos + other.width ||   // this is right of other
            this.yPos + this.height <= other.yPos ||   // this is above other
            this.yPos >= other.yPos + other.height) {  // this is below other
            return false;
        }
        // Otherwise, they overlap
        return true;
    }


    //Shorthand getters and setters so that this file isn't 500 lines long
    public int getXPos() { return xPos; }
    public void setXPos(int xPos) { this.xPos = xPos; }
    public int getYPos() { return yPos; }
    public void setYPos(int yPos) { this.yPos = yPos; }
    public int getWidth() { return width; }
    public void setWidth(int width) { this.width = width; }
    public int getHeight() { return height; }
    public BufferedImage getImage() { return image; }
    public void setHeight(int height) { this.height = height; }
    public void setPos(int xPos, int yPos) { this.xPos = xPos; this.yPos = yPos; }
    public void setSize(int width, int height) { this.width = width; this.height = height; }
    public boolean getIgnoreCollisions() {
        return ignoreCollisions;
    }
}
