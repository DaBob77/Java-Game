import java.awt.*;
import java.awt.image.*;

// Handles mouse input; ie. machine gun and portal
public class GunHandler {
    Character player;
    Inputs input;

    Portals bluePortal = new Portals(Images.ORANGE_PORTAL, -9999, -9999, "blue"); //We don't have a blue portal yet
    Portals orangePortal = new Portals(Images.ORANGE_PORTAL, -9999, -9999, "orange");
    int blueCooldown = 0;
    int orangeCooldown = 0;

    public GunHandler(Character player, Inputs input) {
        this.player = player;
        this.input = input;
    }

    public void updateGun() {
        if (input.isMouseLeftPressed() && blueCooldown < 0) { // If mouse pressed and cooldown fulfilled
            shootBluePortal();
            blueCooldown = Constants.PORTAL_COOLDOWN;
        }
        if (input.isMouseRightPressed() && orangeCooldown < 0) {
            shootOrangePortal();
            orangeCooldown = Constants.PORTAL_COOLDOWN;
        }

        blueCooldown--;
        orangeCooldown--;
        
    }
    



    private void shootBluePortal() {
        int sourceXPos = player.getXPos(); // Where the bullet starts
        int sourceYPos = player.getYPos();

        int targetXPos = input.getMouseX();
        int targetYPos = input.getMouseY();
        System.out.println("Blue Portal from " + sourceXPos + ", " + sourceYPos + " toward " + targetXPos + ", " + targetYPos);
        bluePortal.setPos(sourceXPos, sourceYPos);
        bluePortal.setExisting(true);
    }
    
    private void shootOrangePortal() {
        int sourceXPos = player.getXPos(); // Where the bullet starts
        int sourceYPos = player.getYPos();

        int targetXPos = input.getMouseX();
        int targetYPos = input.getMouseY();
        System.out.println("Orange Portal from " + sourceXPos + ", " + sourceYPos + " toward " + targetXPos + ", " + targetYPos);
        orangePortal.setPos(sourceXPos, sourceYPos);
        orangePortal.setExisting(true);
    }
    
    public void draw(Graphics g) { //Draw both portals
        bluePortal.draw(g);
        orangePortal.draw(g);
    }
}
