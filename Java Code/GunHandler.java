// Handles mouse input; ie. machine gun and portal
public class GunHandler {
    Character player;
    Inputs input;

    Portals bluePortal = new Portals(0, 0, "blue");
    Portals orangePortal = new Portals(0, 0, "orange");

    public GunHandler(Character player, Inputs input) {
        this.player = player;
        this.input = input;
    }

    public void updateGun() {
        if (input.isMouseLeftPressed()) {
            shootBluePortal();
        }
        if (input.isMouseRightPressed()) {
            shootOrangePortal();
        }
    }



    private void shootBluePortal() {
        int sourceXPos = player.getXPos(); // Where the bullet starts
        int sourceYPos = player.getYPos();

        int targetXPos = input.getMouseX();
        int targetYPos = input.getMouseY();
    }
    
    private void shootOrangePortal() {

    }
}
