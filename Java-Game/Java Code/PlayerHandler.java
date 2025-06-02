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

    public void updatePlayer(Level level) {
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
        checkAndResolveCollisions(level, oldY);

        System.out.println("yPos: "  + player.getYPos() + " + xPos: " + player.getXPos());
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


    private void checkAndResolveCollisions(Level level, int oldY) {
        grounded = false; // Assume not grounded until a collision proves otherwise
        Rectangle playerRect = new Rectangle(null, player.getXPos(), player.getYPos(), Constants.PLAYER_WIDTH, Constants.PLAYER_HEIGHT, false);

        for (Rectangle platform : level.getPlatforms()) {
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

    private void transformVelocityForPortal(int entryWallDirection, int exitWallDirection) {
        // Calculate the total speed (magnitude of velocity)
        double totalSpeed = Math.sqrt(xVelocity * xVelocity + yVelocity * yVelocity);
        
        // If player has no velocity, don't apply any transformation
        if (totalSpeed < 0.1) {
            return;
        }
        
        // Determine the primary velocity component based on entry wall
        double primaryVelocity = 0;
        double secondaryVelocity = 0;
        
        switch (entryWallDirection) {
            case GunHandler.WALL_TOP:
            case GunHandler.WALL_BOTTOM:
                // Entering through vertical wall, Y velocity is primary
                primaryVelocity = Math.abs(yVelocity);
                secondaryVelocity = xVelocity;
                break;
            case GunHandler.WALL_LEFT:
            case GunHandler.WALL_RIGHT:
                // Entering through horizontal wall, X velocity is primary
                primaryVelocity = Math.abs(xVelocity);
                secondaryVelocity = yVelocity;
                break;
        }
        
        // Apply the velocity in the exit direction
        switch (exitWallDirection) {
            case GunHandler.WALL_TOP: // Exiting upward from top wall
                yVelocity = -primaryVelocity; // Negative Y is up
                xVelocity = secondaryVelocity * 0.5; // Reduce perpendicular velocity
                break;
            case GunHandler.WALL_BOTTOM: // Exiting downward from bottom wall
                yVelocity = primaryVelocity; // Positive Y is down
                xVelocity = secondaryVelocity * 0.5;
                break;
            case GunHandler.WALL_LEFT: // Exiting leftward from left wall
                xVelocity = -primaryVelocity; // Negative X is left
                yVelocity = secondaryVelocity * 0.5;
                break;
            case GunHandler.WALL_RIGHT: // Exiting rightward from right wall
                xVelocity = primaryVelocity; // Positive X is right
                yVelocity = secondaryVelocity * 0.5;
                break;
        }
        
        // Apply damping to both velocity components
        xVelocity *= Constants.PORTAL_VELOCITY_DAMPER;
        yVelocity *= Constants.PORTAL_VELOCITY_DAMPER;
        
        // Reset grounded state when exiting portal
        grounded = false;
    }

    public void checkPortals() { // Check for portal collisions and teleport player if needed
        Portals bluePortal = gunHandler.getBluePortal();
        Portals orangePortal = gunHandler.getOrangePortal();

        if (!bluePortal.isExisting() || !orangePortal.isExisting()) { // End early if the portals dont exist
            return;
        }

        if (gunHandler.getIsMoving()){ // End early if portals are still moving
                return;
        }

        // Calculate proximity once
        boolean playerNearBlue = Math.abs(player.getXPos() - bluePortal.getXPos()) < Constants.PORTAL_DISTANCE &&
                                 Math.abs(player.getYPos() - bluePortal.getYPos()) < Constants.PORTAL_DISTANCE;
        boolean playerNearOrange = Math.abs(player.getXPos() - orangePortal.getXPos()) < Constants.PORTAL_DISTANCE &&
                                   Math.abs(player.getYPos() - orangePortal.getYPos()) < Constants.PORTAL_DISTANCE;

        if (!teleportCheck) {
            if (playerNearBlue) {
                // Store entry wall direction and transform velocity
                int blueWallDirection = gunHandler.getBluePortalWallDirection();
                int orangeWallDirection = gunHandler.getOrangePortalWallDirection();
                
                // Calculate offset position based on exit wall direction
                int offsetX = orangePortal.getXPos();
                int offsetY = orangePortal.getYPos();
                
                if (orangeWallDirection != -1) {
                    switch (orangeWallDirection) {
                        case GunHandler.WALL_TOP: // Exiting upward
                            offsetY -= Constants.PORTAL_EXIT_OFFSET;
                            break;
                        case GunHandler.WALL_BOTTOM: // Exiting downward
                            offsetY += Constants.PORTAL_EXIT_OFFSET;
                            break;
                        case GunHandler.WALL_LEFT: // Exiting leftward
                            offsetX -= Constants.PORTAL_EXIT_OFFSET;
                            break;
                        case GunHandler.WALL_RIGHT: // Exiting rightward
                            offsetX += Constants.PORTAL_EXIT_OFFSET;
                            break;
                    }
                }
                
                player.setPos(offsetX, offsetY);
                
                // Transform velocity based on wall directions
                if (blueWallDirection != -1 && orangeWallDirection != -1) {
                    transformVelocityForPortal(blueWallDirection, orangeWallDirection);
                }
                
                teleportCheck = true;
            } else if (playerNearOrange) { // Prevent immediate re-evaluation after a teleport
                // Store entry wall direction and transform velocity
                int orangeWallDirection = gunHandler.getOrangePortalWallDirection();
                int blueWallDirection = gunHandler.getBluePortalWallDirection();
                
                // Calculate offset position based on exit wall direction
                int offsetX = bluePortal.getXPos();
                int offsetY = bluePortal.getYPos();
                
                if (blueWallDirection != -1) {
                    switch (blueWallDirection) {
                        case GunHandler.WALL_TOP: // Exiting upward
                            offsetY -= Constants.PORTAL_EXIT_OFFSET;
                            break;
                        case GunHandler.WALL_BOTTOM: // Exiting downward
                            offsetY += Constants.PORTAL_EXIT_OFFSET;
                            break;
                        case GunHandler.WALL_LEFT: // Exiting leftward
                            offsetX -= Constants.PORTAL_EXIT_OFFSET;
                            break;
                        case GunHandler.WALL_RIGHT: // Exiting rightward
                            offsetX += Constants.PORTAL_EXIT_OFFSET;
                            break;
                    }
                }
                
                player.setPos(offsetX, offsetY);
                
                // Transform velocity based on wall directions
                if (orangeWallDirection != -1 && blueWallDirection != -1) {
                    transformVelocityForPortal(orangeWallDirection, blueWallDirection);
                }
                
                teleportCheck = true;
            }
        } else {
            // Check if player has left portal
            boolean notNearBlueAnymore = Math.abs(player.getXPos() - bluePortal.getXPos()) > Constants.PORTAL_DISTANCE ||
                                         Math.abs(player.getYPos() - bluePortal.getYPos()) > Constants.PORTAL_DISTANCE;
            boolean notNearOrangeAnymore = Math.abs(player.getXPos() - orangePortal.getXPos()) > Constants.PORTAL_DISTANCE ||
                                           Math.abs(player.getYPos() - orangePortal.getYPos()) > Constants.PORTAL_DISTANCE;

            if (notNearBlueAnymore && notNearOrangeAnymore) {
                teleportCheck = false;
            }
        }
    }
}