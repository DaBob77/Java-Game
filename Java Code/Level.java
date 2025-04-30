import java.util.*;
import java.awt.*;
import java.awt.image.*;

public class Level {
    private int levelNum;
    private ArrayList<Rectangle> platforms;

    public Level(int levelNum, ArrayList<Rectangle> platforms) {
        this.levelNum = levelNum;
        this.platforms = platforms;
    }    

    public void addPlatform(Rectangle rect) {
        platforms.add(rect);
    }

    public ArrayList<Rectangle> getPlatforms() {
        return platforms;
    }

    //This will override your current level, NOT change the level
    public void setLevel(int num) {
        this.levelNum = num;
    }

    public int getLevel() {
        return levelNum;
    }

    public void draw(Graphics g) { //This will be removed as all backgrounds are added
        for (int i = 0; i < platforms.size(); i++) { //Loop through all platforms and draw the image for each
            g.drawImage(platforms.get(i).getImage(), platforms.get(i).getXPos(), platforms.get(i).getYPos(), null);
        }
    }
}
