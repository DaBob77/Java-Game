import java.util.*;

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
}
