//Class for everything living and basically humanoid (players, enemies, etc.)
import java.awt.*;
import java.awt.image.*;

public class Character {
    private int xPos;
    private int yPos;
    private BufferedImage image;
    private int imageHeight = 104;
    private int imageWidth = 56;

    private final int JUMP_FORCE = 15; // Jump Constant



    // Constructor with image, xPos and yPos
    public Character(BufferedImage image, int xPos, int yPos) {
        this.image = image;
        this.xPos = xPos;
        this.yPos = yPos;
    }

    // Render character
    public void draw(Graphics g) {
        g.drawImage(image, xPos, yPos, null);
    }
    
    // Setters
    public void setXPos(int xPos) {
        this.xPos = xPos;
    }
    
    public void setYPos(int yPos) {
        this.yPos = yPos;
    }

    public void setPos(int xPos, int yPos) {
        this.xPos = xPos;
        this.yPos = yPos;
    }

    public void setImage(BufferedImage image) {
        this.image = image;
    }


    // Getters
    public int getXPos() {
        return xPos;
    }

    public int getYPos() {
        return yPos;
    }

    public BufferedImage getImage() {
        return image;
    }

    public int getImageHeight() {
        return imageHeight;
    }

    public int getImageWidth() {
        return imageWidth;
    }

    //@Override toString
    public String toString() {
        return "Character{xPos=" + xPos + ", yPos=" + yPos + "}";
    }
}
