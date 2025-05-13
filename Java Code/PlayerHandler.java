public class PlayerHandler {
    boolean grounded = false;
    double yVelocity = 0;
    double xVelocity = 0;
    int xDir = 0; //-1 for left, 0 for none, 1 for right
    Character player;
    Inputs input; //For movement

    public PlayerHandler(Character player, Inputs input) {
        this.player = player;
        this.input = input;
    }

    public void updatePlayer(Level level1) {
        // 1. Apply physics
        applyGravity();
        xMovement();
        if (input.isSpacePressed() && grounded) {
            jump();
        }

        // 2. Update player position based on velocity
        int oldY = player.getYPos();
        int oldX = player.getXPos();

        player.setXPos((int)(oldX + xVelocity));
        player.setYPos((int)(oldY + yVelocity));

        // 3. Check for collisions with the new position and resolve them
        checkAndResolveCollisions(level1, oldY);

        System.out.println("Grounded: " + grounded + ", yVel: " + yVelocity + ", xVel: " + xVelocity + ", yPos: "  + player.getYPos() + " + xPos: " + player.getXPos());
    }



    private void xMovement() {
        if (input.isDPressed() || input.isAPressed()) { //Check the player is trying to move
            if (input.isDPressed()) {
                xDir = 1;
            } else {
                xDir = -1;
            }

            //Apply accleration force before checking for max speed
            xVelocity += xDir * Constants.X_ACCEL_GROUNDED;

            if (xDir == 1) { // Moving right
                if (xVelocity > Constants.MAX_X_VELO_GROUNDED) {
                    xVelocity = Constants.MAX_X_VELO_GROUNDED; 
                }
            } else { // Moving left (xDir == -1)
                if (xVelocity < -Constants.MAX_X_VELO_GROUNDED) {
                    xVelocity = -Constants.MAX_X_VELO_GROUNDED;
                }
            }
        } else {
         // 2. No input, handle deceleration (friction)
            if (Math.abs(xVelocity) < 0.5) { // If speed is very low, stop completely
                xVelocity = 0;
                xDir = 0; // Not moving, so no specific direction
            } else {
                // Player is coasting, set facing direction based on current movement
                xDir = (xVelocity > 0) ? 1 : -1;
                
                // Determine direction for deceleration force (opposite to current velocity)
                int deaccelForceDirection = (xVelocity > 0) ? -1 : 1;
                
                xVelocity += deaccelForceDirection * Constants.X_DEACCEL_GROUNDED;

                // Ensure deceleration doesn't reverse direction (i.e., overshoot zero)
                if ((deaccelForceDirection == -1 && xVelocity < 0) || // Was moving right, decelerated past 0
                    (deaccelForceDirection == 1 && xVelocity > 0)) {  // Was moving left, decelerated past 0
                    xVelocity = 0;
                }
                
                // If velocity becomes zero after deceleration, update facing direction to neutral
                if (xVelocity == 0) {
                    xDir = 0;
                }
                
            }
        }
    }


    private void jump() {
        yVelocity += Constants.JUMP_FORCE;
    }


    private void applyGravity() {
        // Apply gravity only if not grounded
        if (!grounded) {
            if (yVelocity <= Constants.TERMINAL_VELOCITY) {
                yVelocity += Constants.Y_ACCEL;
            }
        } else {
            yVelocity = 0;
        }
    }



    private void checkAndResolveCollisions(Level level1, int oldY) {
        grounded = false; // Assume not grounded until a collision proves otherwise
        Rectangle playerRect = new Rectangle(null, player.getXPos(), player.getYPos(), Constants.PLAYER_WIDTH, Constants.PLAYER_HEIGHT, false);

        for (Rectangle platform : level1.getPlatforms()) {
            if (platform.getIgnoreCollisions() == true ) {
                continue;
            }
            if (playerRect.intersects(platform)) {
                int playerBottom = player.getYPos() + Constants.PLAYER_HEIGHT;
                int platformTop = platform.getYPos();
                int playerTop = player.getYPos();
                int platformBottom = platform.getYPos() + platform.getHeight(); // Use getHeight

                // Check if player was previously above the platform and is now intersecting (landing)
                if (yVelocity >= 0 && oldY + Constants.PLAYER_HEIGHT <= platformTop) {
                     player.setYPos(platformTop - Constants.PLAYER_HEIGHT); // Place player exactly on top
                     grounded = true;
                     yVelocity = 0; // Stop vertical movement
                     break; // Exit loop once grounded on a platform
                }
            }
        }
        // If no collision resolving into grounded state was found, grounded remains false.
    }
}