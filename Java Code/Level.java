import java.util.*;
import java.awt.*;

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

    public void draw(Graphics g) {
        for (Rectangle platform : platforms) {
            int x = Math.min(platform.getXPosL(), platform.getXPosR());
            int y = Math.min(platform.getYPosL(), platform.getYPosR());
            int width = Math.abs(platform.getXPosR() - platform.getXPosL());
            int height = Math.abs(platform.getYPosR() - platform.getYPosL());
            g.drawImage(platform.getImage(), x, y, width, height, null);
        }
    }
}