public class Portals {
    private int yPos = 0;
    private int xPos = 0;
    private String color = "";
    private boolean existing = false; // Has the portal been placed?

    public Portals(int yPos, int xPos, String color) {
        this.yPos = yPos;
        this.xPos = xPos;
        this.color = color;
    }
    
    public int getYPos() { return yPos; }
    public int getXPos() { return xPos; }
    public String getColor() { return color; }
    public void setYPos(int yPos) { this.yPos = yPos; }
    public void setXPos(int xPos) { this.xPos = xPos; }
    public void setColor(String color) { this.color = color; }
    public boolean isExisting() { return existing; }
    public void setExisting(boolean existing) { this.existing = existing; }
}
