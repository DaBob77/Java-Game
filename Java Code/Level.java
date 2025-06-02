import java.util.*;
import java.awt.*;

public class Level {
    private int levelNum;
    private int endXPos;
    private int endYPos;
    private int startXPos;
    private int startYPos;

    private ArrayList<Rectangle> platforms;

    public Level(int levelNum, int startXPos, int startYPos, int endXPos, int endYPos, ArrayList<Rectangle> platforms) {
        this.levelNum = levelNum;
        this.startXPos = startXPos;
        this.startYPos = startYPos;
        this.endXPos = endXPos;
        this.endYPos = endYPos;

        this.platforms = platforms;
    }    

    public void addPlatform(Rectangle rect) {
        platforms.add(rect);
    }

    public ArrayList<Rectangle> getPlatforms() {
        return platforms;
    }

    //This will override your current level, NOT change the level
    public void setLevel(int num) {this.levelNum = num;}
    
    public int getLevel() {return levelNum;}
    public int getEndXPos() {return endXPos;}
    public int getEndYPos() {return endYPos;}
    public int getStartXPos() {return startXPos;}
    public int getStartYPos() {return startYPos;}
    public void setEndXPos(int endXPos) {this.endXPos = endXPos;}
    public void setEndYPos(int endYPos) {this.endYPos = endYPos;}
    public void setStartXPos(int startXPos) {this.startXPos = startXPos;}
    public void setStartYPos(int startYPos) {this.startYPos = startYPos;}


    public void draw(Graphics g) { //This will be removed as all backgrounds are added
        for (Rectangle platform : platforms) { //Loop through all platforms and draw the image for each
            if (platform.getIgnoreCollisions() == true) {
            g.drawImage(platform.getImage(), platform.getXPos(), platform.getYPos(), null);
            }
        }
    }
}