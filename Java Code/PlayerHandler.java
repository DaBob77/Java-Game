//Class used to update players, enemies, boxes, basically everything that moves
public class PlayerHandler {
    final int TERMINAL_VELOCITY = 100;
    final int yAccel = 2;
    int xPos = 100;
    int yPos = 100;
    boolean grounded = false;
    double yVelocity = 0;
    double xVelocity = 0;
    Character player;

    public PlayerHandler(Character player) {
        this.player = player;
    }

    public void updatePlayer(Level level1) {
        if(yVelocity <= TERMINAL_VELOCITY && grounded == false) {
            yVelocity += yAccel;
        }

        yPos += yVelocity;
         


        player.setYPos(yPos);
    }
}