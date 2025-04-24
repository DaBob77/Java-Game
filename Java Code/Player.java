import java.awt.*;
import java.awt.image.*;

public class Player {
    private int xPos;
    private int yPos;
    private int xSpeed;
    private int ySpeed;
    private BufferedImage image; 
    
    // Constructor with image, xPos and yPos
    public Player(BufferedImage image, int xPos, int yPos) {
        this.image = image;
        this.xPos = xPos;
        this.yPos = yPos;
        this.xSpeed = 0;
        this.ySpeed = 0;
    }

    // Render player
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

    public void setXSpeed(int xSpeed) {
        this.xSpeed = xSpeed;
    }

    public void setYSpeed(int ySpeed) {
        this.ySpeed = ySpeed;
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

    public int getXSpeed() {
        return xSpeed;
    }

    public int getYSpeed() {
        return ySpeed;
    }

    public BufferedImage getImage() {
        return image;
    }

    //@Override toString
    public String toString() {
        return "Player{ xPos=" + xPos + ", yPos=" + yPos + ", xSpeed=" + xSpeed + ", ySpeed=" + ySpeed +'}';
    }
}
