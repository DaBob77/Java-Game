import java.awt.*;
import java.awt.image.*;
import java.util.ArrayList; // Required for Level platforms

// Handles mouse input; ie. machine gun and portal
public class GunHandler {
    Character player;
    Inputs input;
    Level currentLevel; // To access platforms for collision

    Portals bluePortal = new Portals(Images.ALL_LOADED_IMAGES.get(1), -9999, -9999, "blue");
    Portals orangePortal = new Portals(Images.ALL_LOADED_IMAGES.get(2), -9999, -9999, "orange");
    // targetBluePos and targetOrangePos are removed

    double[] bluePortalDirection = {0, 0}; // {dx, dy} normalized
    double[] orangePortalDirection = {0, 0}; // {dx, dy} normalized

    int blueCooldown = 0;
    int orangeCooldown = 0;

    boolean isBlueMoving = false; // Replaces single isMoving
    boolean isOrangeMoving = false; // Replaces single isMoving

    public GunHandler(Character player, Inputs input, Level level) { // Added Level parameter
        this.player = player;
        this.input = input;
        this.currentLevel = level; // Store the level
    }

    public void updateGun() { // Removed Level parameter, uses this.currentLevel
        if (input.isMouseLeftPressed() && blueCooldown < 0) {
            startBluePortal(); // No longer assigns to targetBluePos
            blueCooldown = Constants.PORTAL_COOLDOWN;
        }
        if (input.isMouseRightPressed() && orangeCooldown < 0) {
            startOrangePortal(); // No longer assigns to targetOrangePos
            orangeCooldown = Constants.PORTAL_COOLDOWN;
        }

        if (isBlueMoving) { 
            updateBluePortal(); // No longer passes targetPos, uses this.currentLevel
        }
        
        if (isOrangeMoving) { 
            updateOrangePortal(); // No longer passes targetPos, uses this.currentLevel
        }

        blueCooldown--;
        orangeCooldown--;
    }
    
    private void startBluePortal() { // No longer returns int[]
        int sourceXPos = player.getXPos() - Constants.PLAYER_WIDTH / 4;
        int sourceYPos = player.getYPos() - Constants.PLAYER_HEIGHT / 4;

        bluePortal.setPos(sourceXPos, sourceYPos);
        bluePortal.setExisting(true);

        int targetMouseX = input.getMouseX();
        int targetMouseY = input.getMouseY();
        // System.out.println("Blue Portal from " + sourceXPos + ", " + sourceYPos + " toward " + targetMouseX + ", " + targetMouseY);

        double dx = targetMouseX - sourceXPos;
        double dy = targetMouseY - sourceYPos;
        double distance = Math.sqrt(dx * dx + dy * dy);

        if (distance < 1.0) { // Effectively zero distance, or too close to define a direction
            bluePortalDirection[0] = 0;
            bluePortalDirection[1] = 0;
            isBlueMoving = false; // Don't start moving
        } else {
            bluePortalDirection[0] = dx / distance;
            bluePortalDirection[1] = dy / distance;
            isBlueMoving = true;
        }
    }
    
    private void startOrangePortal() { // No longer returns int[]
        int sourceXPos = player.getXPos() - Constants.PLAYER_WIDTH / 4;
        int sourceYPos = player.getYPos() - Constants.PLAYER_HEIGHT / 4;

        orangePortal.setPos(sourceXPos, sourceYPos);
        orangePortal.setExisting(true);

        int targetMouseX = input.getMouseX();
        int targetMouseY = input.getMouseY();
        // System.out.println("Orange Portal from " + sourceXPos + ", " + sourceYPos + " toward " + targetMouseX + ", " + targetMouseY);
        
        double dx = targetMouseX - sourceXPos;
        double dy = targetMouseY - sourceYPos;
        double distance = Math.sqrt(dx * dx + dy * dy);

        if (distance < 1.0) {
            orangePortalDirection[0] = 0;
            orangePortalDirection[1] = 0;
            isOrangeMoving = false;
        } else {
            orangePortalDirection[0] = dx / distance;
            orangePortalDirection[1] = dy / distance;
            isOrangeMoving = true;
        }
    }
    
private void updateBluePortal() {
    if (!isBlueMoving) {
        return;
    }

    int currentX = bluePortal.getXPos();
    int currentY = bluePortal.getYPos();

    if (bluePortalDirection[0] == 0 && bluePortalDirection[1] == 0) {
        isBlueMoving = false;
        return;
    }

    double moveX = bluePortalDirection[0] * Constants.PORTAL_SPEED;
    double moveY = bluePortalDirection[1] * Constants.PORTAL_SPEED;

    int newX = (int) Math.round(currentX + moveX);
    int newY = (int) Math.round(currentY + moveY);

    int portalWidth = bluePortal.getImage() != null ? Constants.PORTAL_WIDTH : 10;
    int portalHeight = bluePortal.getImage() != null ? Constants.PORTAL_HEIGHT : 10;

    Rectangle portalCollisionBox = new Rectangle(null, newX, newY, portalWidth, portalHeight, true);

    for (Rectangle platform : this.currentLevel.getPlatforms()) {
        if (platform.getIgnoreCollisions()) {
            continue;
        }
        if (platform.intersects(portalCollisionBox)) {
            // Adjust position so portal stops at the edge of the platform
            int stopX = newX;
            int stopY = newY;

            // Horizontal movement
            if (Math.abs(bluePortalDirection[0]) > Math.abs(bluePortalDirection[1])) {
                if (bluePortalDirection[0] > 0) { // Moving right
                    stopX = platform.getXPos() - portalWidth;
                } else { // Moving left
                    stopX = platform.getXPos() + platform.getWidth();
                }
            } else { // Vertical movement
                if (bluePortalDirection[1] > 0) { // Moving down
                    stopY = platform.getYPos() - portalHeight;
                } else { // Moving up
                    stopY = platform.getYPos() + platform.getHeight();
                }
            }

            bluePortal.setPos(stopX, stopY);
            isBlueMoving = false;
            return;
        }
    }

    bluePortal.setPos(newX, newY);

    if (newX < 0 || newX > Constants.SCREEN_WIDTH || newY < 0 || newY > Constants.SCREEN_HEIGHT) {
        isBlueMoving = false;
    }
}

private void updateOrangePortal() {
    if (!isOrangeMoving) {
        return;
    }

    int currentX = orangePortal.getXPos();
    int currentY = orangePortal.getYPos();

    if (orangePortalDirection[0] == 0 && orangePortalDirection[1] == 0) {
        isOrangeMoving = false;
        return;
    }

    double moveX = orangePortalDirection[0] * Constants.PORTAL_SPEED;
    double moveY = orangePortalDirection[1] * Constants.PORTAL_SPEED;

    int newX = (int) Math.round(currentX + moveX);
    int newY = (int) Math.round(currentY + moveY);

    int portalWidth = orangePortal.getImage() != null ? Constants.PORTAL_WIDTH : 10;
    int portalHeight = orangePortal.getImage() != null ? Constants.PORTAL_HEIGHT : 10;

    Rectangle portalCollisionBox = new Rectangle(null, newX, newY, portalWidth, portalHeight, true);

    for (Rectangle platform : this.currentLevel.getPlatforms()) {
        if (platform.getIgnoreCollisions()) {
            continue;
        }
        if (platform.intersects(portalCollisionBox)) {
            int stopX = newX;
            int stopY = newY;

            if (Math.abs(orangePortalDirection[0]) > Math.abs(orangePortalDirection[1])) {
                if (orangePortalDirection[0] > 0) { // Moving right
                    stopX = platform.getXPos() - portalWidth;
                } else { // Moving left
                    stopX = platform.getXPos() + platform.getWidth();
                }
            } else {
                if (orangePortalDirection[1] > 0) { // Moving down
                    stopY = platform.getYPos() - portalHeight;
                } else { // Moving up
                    stopY = platform.getYPos() + platform.getHeight();
                }
            }

            orangePortal.setPos(stopX, stopY);
            isOrangeMoving = false;
            return;
        }
    }

    orangePortal.setPos(newX, newY);

    if (newX < 0 || newX > Constants.SCREEN_WIDTH || newY < 0 || newY > Constants.SCREEN_HEIGHT) {
        isOrangeMoving = false;
    }
}
    
    public void draw(Graphics g) {
        if (bluePortal.isExisting()) bluePortal.draw(g);
        if (orangePortal.isExisting()) orangePortal.draw(g);
    }

    public Portals getBluePortal() {
        return bluePortal;
    }

    public Portals getOrangePortal() {
        return orangePortal;
    }

    public boolean getIsMoving() { // Updated to reflect individual portal movement
        return isBlueMoving || isOrangeMoving;
    }
}