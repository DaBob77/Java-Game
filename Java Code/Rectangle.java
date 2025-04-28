import java.awt.*;

public class Rectangle {
    private int xPos; //xPos and yPos for the bottom right point of the rectangle
    private int yPos;
    private int width;
    private int length;

    public Rectangle(int xPos, int yPos, int width, int length) {
        this.xPos = xPos;
        this.yPos = yPos;
        this.width = width;
        this.length = length;
    }
    
    public boolean intersects(Rectangle rect1, Rectangle rect2) {
        // Check if rectangles do NOT overlap horizontally or vertically
        if (rect1.xPos <= rect2.xPos - rect2.width || rect2.xPos <= rect1.xPos - rect1.width)
            return false;
        if (rect1.yPos <= rect2.yPos - rect2.length || rect2.yPos <= rect1.yPos - rect1.length)
            return false;
        return true;
    }

    //Getters and Setters
    public int getXPos() {
        return xPos;
    }

    public void setXPos(int xPos) {
        this.xPos = xPos;
    }

    public int getYPos() {
        return yPos;
    }

    public void setYPos(int yPos) {
        this.yPos = yPos;
    }

    public int getWidth() {
        return width;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    public int getLength() {
        return length;
    }

    public void setLength(int length) {
        this.length = length;
    }

    public void setPos(int xPos, int yPos) {
        this.xPos = xPos;
        this.yPos = yPos;
    }

    public void setSize(int width, int length) {
        this.width = width;
        this.length = length;
    }
}
