public class PlayerHandler {
    boolean grounded = false;
    double yVelocity = 0;
    // double xVelocity = 0; // Not used yet
    Character player;

    public PlayerHandler(Character player) {
        this.player = player;
    }

    public void updatePlayer(Level level1) {
        // 1. Apply physics (gravity based on current grounded state)
        applyGravity();

        // 2. Update player position based on velocity
        int oldY = player.getYPos();
        player.setYPos(oldY + (int)yVelocity);

        // 3. Check for collisions with the *new* position and resolve them
        checkAndResolveCollisions(level1, oldY);

        // Debugging output
        // System.out.println("Grounded: " + grounded + ", yVel: " + yVelocity + ", yPos: " + player.getYPos());
    }

    private void applyGravity() {
        // Apply gravity only if not grounded
        if (!grounded) {
            if (yVelocity <= Constants.TERMINAL_VELOCITY) {
                yVelocity += Constants.Y_ACCEL;
            }
        }
        // If grounded, velocity should ideally be reset in collision resolution
    }

    private void checkAndResolveCollisions(Level level1, int oldY) {
        grounded = false; // Assume not grounded until a collision proves otherwise
        Rectangle playerRect = new Rectangle(null, player.getXPos(), player.getYPos(), player.getImageWidth(), player.getImageHeight());

        for (Rectangle platform : level1.getPlatforms()) {
            if (playerRect.intersects(platform)) { // Use intersects directly
                int playerBottom = player.getYPos() + player.getImageHeight();
                int platformTop = platform.getYPos();
                int playerTop = player.getYPos();
                int platformBottom = platform.getYPos() + platform.getHeight(); // Use getHeight

                // Check if player was previously above the platform and is now intersecting (landing)
                if (yVelocity >= 0 && oldY + player.getImageHeight() <= platformTop) {
                     player.setYPos(platformTop - player.getImageHeight()); // Place player exactly on top
                     grounded = true;
                     yVelocity = 0; // Stop vertical movement
                     break; // Exit loop once grounded on a platform
                }
            }
        }
        // If no collision resolving into grounded state was found, grounded remains false.
    }
}