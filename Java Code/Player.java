import java.awt.*;
import java.awt.image.*;

public class Player {
    private int xPos;
    private int yPos;
    private int xVelocity;
    private int yVelocity;
    private boolean isJumping = false; // Jumping state
    private boolean isGrounded = false; // Grounded state
    private BufferedImage image; 

    private final int GRAVITY = 1; // Gravity constant
    private final int TERMINAL_VELOCITY = 60; // Max fall speed
    private final int JUMP_FORCE = 15; // Jump Constant



    // Constructor with image, xPos and yPos
    public Player(BufferedImage image, int xPos, int yPos) {
        this.image = image;
        this.xPos = xPos;
        this.yPos = yPos;
        this.xVelocity = 0;
        this.yVelocity = 0;
    }

    public void update() {
        //yVelocity handler
        if (yVelocity > TERMINAL_VELOCITY) {
            yVelocity -= yVelocity * GRAVITY;
        }



        //Position manager 
        yPos += yVelocity;

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

    public int getxVelocity() {
        return xVelocity;
    }

    public int getyVelocity() {
        return yVelocity;
    }

    public BufferedImage getImage() {
        return image;
    }

    //@Override toString
    public String toString() {
        return "Player{xPos=" + xPos + ", yPos=" + yPos + ", xVelocity=" + xVelocity + ", yVelocity=" + yVelocity +'}';
    }
}
