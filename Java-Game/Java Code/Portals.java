import java.awt.*;
import java.awt.image.*;

public class Portals {
    private BufferedImage image; 
    private int yPos = 0;
    private int xPos = 0;
    private String color = "";
    private boolean existing = false; // Has the portal been placed?
    

    public Portals(BufferedImage image, int yPos, int xPos, String color) {
		this.image = image;
        this.yPos = yPos;
        this.xPos = xPos;
        
        this.color = color;
    }
    
    public void setImage(BufferedImage image) {this.image = image;}
    public int getYPos() { return yPos; }
    public int getXPos() { return xPos; }
    public String getColor() { return color; }
    public void setYPos(int yPos) { this.yPos = yPos; }
    public void setXPos(int xPos) { this.xPos = xPos; }
    public void setPos(int xPos, int yPos) {this.xPos = xPos; this.yPos = yPos;}
    public void setColor(String color) { this.color = color; }
    public boolean isExisting() { return existing; }
    public void setExisting(boolean existing) { this.existing = existing; }
    public BufferedImage getImage() {return image;}
    
      public void draw(Graphics g) {
        g.drawImage(image, xPos, yPos, null);
    }
}
