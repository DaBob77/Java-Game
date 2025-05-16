import java.awt.*;
import java.awt.image.*;

// Handles mouse input; ie. machine gun and portal
public class GunHandler {
    Character player;
    Inputs input;

    Portals bluePortal = new Portals(Images.ORANGE_PORTAL, -9999, -9999, "blue"); //We don't have a blue portal yet
    Portals orangePortal = new Portals(Images.ORANGE_PORTAL, -9999, -9999, "orange");
    int[] targetBluePos = {-9999, -9999}; // Start out of bounds
    int[] targetOrangePos = {-9999, -9999}; // Start out of bounds

    int blueCooldown = 0;
    int orangeCooldown = 0;

    public GunHandler(Character player, Inputs input) {
        this.player = player;
        this.input = input;
    }

    public void updateGun() {
        if (input.isMouseLeftPressed() && blueCooldown < 0) { // If mouse pressed and cooldown fulfilled
            targetBluePos = startBluePortal();
            blueCooldown = Constants.PORTAL_COOLDOWN;
        }
        if (input.isMouseRightPressed() && orangeCooldown < 0) {
            targetOrangePos = startOrangePortal();
            orangeCooldown = Constants.PORTAL_COOLDOWN;
        }

        if (!(bluePortal.getXPos() == targetBluePos[0] && bluePortal.getYPos() == targetBluePos[1])) { // If it hasn't reached it's target position
            updateBluePortal(targetBluePos);
        }
        
        if (!(orangePortal.getXPos() == targetOrangePos[0] && orangePortal.getYPos() == targetOrangePos[1])) { // If it hasn't reached it's target position
            updateOrangePortal(targetOrangePos);
        }

        blueCooldown--;
        orangeCooldown--;
        
    }
    



    private int[] startBluePortal() { // Bring the portal into exsistence and mark the target position.
        int sourceXPos = player.getXPos();
        int sourceYPos = player.getYPos();

        int targetXPos = input.getMouseX();
        int targetYPos = input.getMouseY();
        System.out.println("Blue Portal from " + sourceXPos + ", " + sourceYPos + " toward " + targetXPos + ", " + targetYPos);
        bluePortal.setPos(sourceXPos, sourceYPos);
        bluePortal.setExisting(true);

        int[] output = {targetXPos, targetXPos}; //Return the target position
        return output;
        
    }
    
    private int[] startOrangePortal() { // Bring the portal into exsistence and mark the target position.
        int sourceXPos = player.getXPos();
        int sourceYPos = player.getYPos();

        int targetXPos = input.getMouseX();
        int targetYPos = input.getMouseY();
        System.out.println("Orange Portal from " + sourceXPos + ", " + sourceYPos + " toward " + targetXPos + ", " + targetYPos);
        orangePortal.setPos(sourceXPos, sourceYPos);
        orangePortal.setExisting(true);

        int[] output = {targetXPos, targetXPos}; //Return the target position
        return output;
    }
    
    private void updateBluePortal(int[] targetPos) {
        int currentX = bluePortal.getXPos();
        int currentY = bluePortal.getYPos();
        int targetX = targetPos[0];
        int targetY = targetPos[1];

        // Calculate direction vector
        int dx = targetX - currentX;
        int dy = targetY - currentY;

        // If already at target, do nothing
        if (dx == 0 && dy == 0) return;

        // Normalize direction and move by speed
        double distance = Math.sqrt(dx * dx + dy * dy);
        double speed = 40; // pixels per frame, adjust as needed

        // If close enough, snap to target
        if (distance <= speed) {
            bluePortal.setPos(targetX, targetY);
            return;
        }

        double moveX = (dx / distance) * speed;
        double moveY = (dy / distance) * speed;

        int newX = (int) Math.round(currentX + moveX);
        int newY = (int) Math.round(currentY + moveY);

        bluePortal.setPos(newX, newY);
    }

    private void updateOrangePortal(int[] targetPos) {

    }
    
    
    public void draw(Graphics g) { //Draw both portals
        bluePortal.draw(g);
        orangePortal.draw(g);
    }
}
