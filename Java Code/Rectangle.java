import java.awt.image.BufferedImage;

public class Rectangle {
    private BufferedImage image;
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
    public void setImage(BufferedImage image) { this.image = image; }
    public void setPos(int xPosL, int yPosL, int xPosR, int yPosR) {
        this.xPosL = xPosL;
        this.yPosL = yPosL;
        this.xPosR = xPosR;
        this.yPosR = yPosR;
    }
    public boolean ignoreCollisions() { return ignoreCollisions; }
}