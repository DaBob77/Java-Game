import java.awt.image.BufferedImage;

public class Rectangle {
    private BufferedImage image;
<<<<<<< HEAD
    private int xPos; //xPos and yPos for the top right point of the rectangle
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
=======
    private int xPosL; // x position of top left
    private int yPosL; // y position of top left
    private int xPosR; // x position of bottom right
    private int yPosR; // y position of bottom right
    private boolean ignoreCollisions;

    public Rectangle(BufferedImage image, int xPosL, int yPosL, int xPosR, int yPosR, boolean ignoreCollisions) {
        this.image = image;
        this.xPosL = xPosL;
        this.yPosL = yPosL;
        this.xPosR = xPosR;
        this.yPosR = yPosR;
>>>>>>> origin/main
        this.ignoreCollisions = ignoreCollisions;
    }

    public boolean intersects(Rectangle other) {
        if (other == null) return false;

        // Calculate min/max for both rectangles
        int thisLeft = Math.min(this.xPosL, this.xPosR);
        int thisRight = Math.max(this.xPosL, this.xPosR);
        int thisBottom = Math.min(this.yPosL, this.yPosR);
        int thisTop = Math.max(this.yPosL, this.yPosR);

        int otherLeft = Math.min(other.xPosL, other.xPosR);
        int otherRight = Math.max(other.xPosL, other.xPosR);
        int otherBottom = Math.min(other.yPosL, other.yPosR);
        int otherTop = Math.max(other.yPosL, other.yPosR);

        // Check for non-overlap
        if (thisRight <= otherLeft ||    // this is left of other
            thisLeft >= otherRight ||    // this is right of other
            thisBottom >= otherTop ||    // this is above other
            thisTop <= otherBottom) {    // this is below other
            return false;
        }
        // Otherwise, they overlap
        return true;
    }

    // Getters and setters
    public int getXPosL() { return xPosL; }
    public void setXPosL(int xPosL) { this.xPosL = xPosL; }
    public int getYPosL() { return yPosL; }
    public void setYPosL(int yPosL) { this.yPosL = yPosL; }
    public int getXPosR() { return xPosR; }
    public void setXPosR(int xPosR) { this.xPosR = xPosR; }
    public int getYPosR() { return yPosR; }
    public void setYPosR(int yPosR) { this.yPosR = yPosR; }
    public BufferedImage getImage() { return image; }
<<<<<<< HEAD
    public void setHeight(int height) { this.height = height; }
    public void setPos(int xPos, int yPos) { this.xPos = xPos; this.yPos = yPos; }
    public void setSize(int width, int height) { this.width = width; this.height = height; }
    public boolean getIgnoreCollisions() {
        return ignoreCollisions;
    }
}
=======
    public void setImage(BufferedImage image) { this.image = image; }
    public void setPos(int xPosL, int yPosL, int xPosR, int yPosR) {
        this.xPosL = xPosL;
        this.yPosL = yPosL;
        this.xPosR = xPosR;
        this.yPosR = yPosR;
    }
    public boolean ignoreCollisions() { return ignoreCollisions; }
}
>>>>>>> origin/main
