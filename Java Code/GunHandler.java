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

    // Store old position for collision detection
    int oldX = currentX;
    int oldY = currentY;

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
        
        if (portalCollisionBox.intersects(platform)) {
            int platformTop = platform.getYPos();
            int platformBottom = platform.getYPos() + platform.getHeight();
            int platformLeft = platform.getXPos();
            int platformRight = platform.getXPos() + platform.getWidth();

            if (Math.abs(bluePortalDirection[1]) > Math.abs(bluePortalDirection[0])) {
                // 1. Hitting top of platform (portal moving down)
                if (bluePortalDirection[1] > 0 && oldY + portalHeight <= platformTop) {
                    bluePortal.setPos(newX, platformTop - portalHeight);
                    isBlueMoving = false;
                    return;
                }
                // 2. Hitting bottom of platform (portal moving up)
                else if (bluePortalDirection[1] < 0 && oldY >= platformBottom) {
                    bluePortal.setPos(newX, platformBottom);
                    isBlueMoving = false;
                    return;
                }
            }
            // Moving primarily horizontally
            else {
                // 3. Hitting left side of platform (portal moving right)
                if (bluePortalDirection[0] > 0 && oldX + portalWidth <= platformLeft) {
                    bluePortal.setPos(platformLeft - portalWidth, newY);
                    isBlueMoving = false;
                    return;
                }
                // 4. Hitting right side of platform (portal moving left)
                else if (bluePortalDirection[0] < 0 && oldX >= platformRight) {
                    bluePortal.setPos(platformRight, newY);
                    isBlueMoving = false;
                    return;
                }
            }
        }
    }

    bluePortal.setPos(newX, newY);

    if (newX < 0 || newX > 9999 || newY < 0 || newY > 9999) {
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

    // Store old position for collision detection
    int oldX = currentX;
    int oldY = currentY;

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
        if (portalCollisionBox.intersects(platform)) {
            int platformTop = platform.getYPos();
            int platformBottom = platform.getYPos() + platform.getHeight();
            int platformLeft = platform.getXPos();
            int platformRight = platform.getXPos() + platform.getWidth();

            // Check primary movement direction first
            // Moving primarily vertically
            if (Math.abs(orangePortalDirection[1]) > Math.abs(orangePortalDirection[0])) {
                // 1. Hitting top of platform (portal moving down)
                if (orangePortalDirection[1] > 0 && oldY + portalHeight <= platformTop) {
                    orangePortal.setPos(newX, platformTop + portalHeight);
                    isOrangeMoving = false;
                    return;
                }
                // 2. Hitting bottom of platform (portal moving up)
                else if (orangePortalDirection[1] < 0 && oldY >= platformBottom) {
                    orangePortal.setPos(newX, platformBottom);
                    isOrangeMoving = false;
                    return;
                }
            }
            // Moving primarily horizontally
            else {
                // 3. Hitting left side of platform (portal moving right)
                if (orangePortalDirection[0] > 0 && oldX + portalWidth <= platformLeft) {
                    orangePortal.setPos(platformLeft - portalWidth, newY);
                    isOrangeMoving = false;
                    return;
                }
                // 4. Hitting right side of platform (portal moving left)
                else if (orangePortalDirection[0] < 0 && oldX >= platformRight) {
                    orangePortal.setPos(platformRight, newY);
                    isOrangeMoving = false;
                    return;
                }
            }
        }
    }

    orangePortal.setPos(newX, newY);

    if (newX < 0 || newX > 9999|| newY < 0 || newY > 9999) {
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