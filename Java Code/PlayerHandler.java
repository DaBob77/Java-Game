public class PlayerHandler {
    boolean grounded = false;
    double yVelocity = 0;
    double xVelocity = 0;
    int xDir = 0; //-1 for left, 0 for none, 1 for right
    boolean teleportCheck = false; // True right after you teleport, resets once you leave the portal
    Character player;
    Inputs input; //For movement
    GunHandler gunHandler;

    public PlayerHandler(Character player, Inputs input, GunHandler gunHandler) {
        this.player = player;
        this.input = input;
        this.gunHandler = gunHandler;
    }

    public void updatePlayer(Level level1) {
        // 1. Apply physics
        applyGravity();
        xMovement();
        checkPortals();
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

        //System.out.println("Grounded: " + grounded + ", yVel: " + yVelocity + ", xVel: " + xVelocity + ", yPos: "  + player.getYPos() + " + xPos: " + player.getXPos());
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
            if (platform.getIgnoreCollisions()) {
                continue;
            }
            if (playerRect.intersects(platform)) {
                int platformTop = platform.getYPos();
                int platformBottom = platform.getYPos() + platform.getHeight() - Constants.PLATFORM_OFFSET;
                int playerLeft = player.getXPos();
                int playerRight = player.getXPos() + Constants.PLAYER_WIDTH;
                int platformLeft = platform.getXPos();
                int platformRight = platform.getXPos() + platform.getWidth();

                // 1. Landing on top of platform
                if (yVelocity >= 0 && oldY + Constants.PLAYER_HEIGHT <= platformTop) {
                    player.setYPos(platformTop - Constants.PLAYER_HEIGHT);
                    grounded = true;
                    yVelocity = 0;
                    // Don't break yet, check for side collisions too
                }
                // 2. Hitting head on bottom of platform
                else if (yVelocity < 0 && oldY >= platformBottom) {
                    player.setYPos(platformBottom);
                    yVelocity = 0;
                }
                // 3. Side collisions (moving right)
                else if (xVelocity > 0 && playerRight > platformLeft && oldY + Constants.PLAYER_HEIGHT > platformTop && oldY < platformBottom) {
                    player.setXPos(platformLeft - Constants.PLAYER_WIDTH);
                    xVelocity = 0;
                }
                // 4. Side collisions (moving left)
                else if (xVelocity < 0 && playerLeft < platformRight && oldY + Constants.PLAYER_HEIGHT > platformTop && oldY < platformBottom) {
                    player.setXPos(platformRight);
                    xVelocity = 0;
                }
            }
        }
        // If no collision resolving into grounded state was found, grounded remains false.
    }

    public void checkPortals() { // Check for portal collisions and teleport player if needed
        Portals bluePortal = gunHandler.getBluePortal();
        Portals orangePortal = gunHandler.getOrangePortal();

        if (bluePortal.isExisting() == false || orangePortal.isExisting() == false) { // End early if the portals dont exist
            return;
        }

        if (gunHandler.getIsMoving()){ // End early if portals are still moving
                return;
        }

        if (teleportCheck == false ) {

            if (Math.abs(player.getXPos() - bluePortal.getXPos()) < Constants.PORTAL_DISTANCE && Math.abs(player.getYPos() - bluePortal.getYPos()) < Constants.PORTAL_DISTANCE) {
                player.setPos(orangePortal.getXPos(), orangePortal.getYPos());
                teleportCheck = true;
            }

            if (Math.abs(player.getXPos() - orangePortal.getXPos()) < Constants.PORTAL_DISTANCE && Math.abs(player.getYPos() - orangePortal.getYPos()) < Constants.PORTAL_DISTANCE) {
                player.setPos(bluePortal.getXPos(), bluePortal.getYPos());
                teleportCheck = true;
            }
        } else {
            // Check if the player has left the portal
            if ((Math.abs(player.getXPos() - bluePortal.getXPos()) > Constants.PORTAL_DISTANCE && Math.abs(player.getYPos()) - bluePortal.getYPos() < Constants.PORTAL_DISTANCE) || (Math.abs(player.getXPos() - orangePortal.getXPos()) < Constants.PORTAL_DISTANCE && Math.abs(player.getYPos() - orangePortal.getYPos()) < Constants.PORTAL_DISTANCE)) {
                teleportCheck = false;
            }
        }
    }
}