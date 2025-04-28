//Class for everything living and basically humanoid (players, enemies, etc.)
import java.awt.*;
import java.awt.image.*;

public class Character {
    private int xPos;
    private int yPos;
    private double xVelocity;
    private double yVelocity;
    private boolean isJumping = false; // Jumping state
    private boolean isGrounded = false; // Grounded state
    private BufferedImage image;
    private int imageHeight;
    private int imageWidth;

    private final int JUMP_FORCE = 15; // Jump Constant



    // Constructor with image, xPos and yPos
    public Character(BufferedImage image, int xPos, int yPos) {
        this.image = image;
        this.xPos = xPos;
        this.yPos = yPos;
        this.xVelocity = 0;
        this.yVelocity = 0;
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

    public void setXVelocity(int xVelocity) {
        this.xVelocity = xVelocity;
    }

    public void setYVelocity(int yVelocity) {
        this.yVelocity = yVelocity;
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

    public double getxVelocity() {
        return xVelocity;
    }

    public double getyVelocity() {
        return yVelocity;
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
        return "Character{xPos=" + xPos + ", yPos=" + yPos + ", xVelocity=" + xVelocity + ", yVelocity=" + yVelocity +'}';
    }
}
