import java.awt.*;
import java.awt.image.*;
import java.util.ArrayList;

/**
 * Handles portal gun mechanics including firing, movement, and collision detection
 */
public class GunHandler {
    private static final int OUT_OF_BOUNDS_LIMIT = 9999;
    private static final double MIN_DIRECTION_THRESHOLD = 1.0;
    
    private final Character player;
    private final Inputs input;
    private final Level currentLevel;

    private final Portals bluePortal;
    private final Portals orangePortal;
    
    private final PortalState blueState;
    private final PortalState orangeState;

    /**
     * Inner class to encapsulate portal movement state
     */
    private static class PortalState {
        double directionX = 0;
        double directionY = 0;
        boolean isMoving = false;
        int cooldown = 0;
        
        void setDirection(double dx, double dy) {
            this.directionX = dx;
            this.directionY = dy;
        }
        
        void reset() {
            this.directionX = 0;
            this.directionY = 0;
            this.isMoving = false;
        }
    }

    public GunHandler(Character player, Inputs input, Level level) {
        this.player = player;
        this.input = input;
        this.currentLevel = level;
        
        this.bluePortal = new Portals(Images.ALL_LOADED_IMAGES.get(1), -OUT_OF_BOUNDS_LIMIT, -OUT_OF_BOUNDS_LIMIT, "blue");
        this.orangePortal = new Portals(Images.ALL_LOADED_IMAGES.get(2), -OUT_OF_BOUNDS_LIMIT, -OUT_OF_BOUNDS_LIMIT, "orange");
        
        this.blueState = new PortalState();
        this.orangeState = new PortalState();
    }

    public void updateGun() {
        handlePortalFiring();
        updatePortalMovement();
        decrementCooldowns();
    }
    
    private void handlePortalFiring() {
        if (input.isMouseLeftPressed() && blueState.cooldown <= 0) {
            firePortal(bluePortal, blueState);
            blueState.cooldown = Constants.PORTAL_COOLDOWN;
        }
        
        if (input.isMouseRightPressed() && orangeState.cooldown <= 0) {
            firePortal(orangePortal, orangeState);
            orangeState.cooldown = Constants.PORTAL_COOLDOWN;
        }
    }
    
    private void updatePortalMovement() {
        if (blueState.isMoving) {
            updatePortal(bluePortal, blueState);
        }
        
        if (orangeState.isMoving) {
            updatePortal(orangePortal, orangeState);
        }
    }
    
    private void decrementCooldowns() {
        blueState.cooldown--;
        orangeState.cooldown--;
    }
    
    private void firePortal(Portals portal, PortalState state) {
        Point playerCenter = getPlayerCenter();
        portal.setPos(playerCenter.x, playerCenter.y);
        portal.setExisting(true);

        Point mousePos = new Point(input.getMouseX(), input.getMouseY());
        Vector2D direction = calculateDirection(playerCenter, mousePos);
        
        if (direction.getMagnitude() < MIN_DIRECTION_THRESHOLD) {
            state.reset();
            return;
        }
        
        direction.normalize();
        state.setDirection(direction.getX(), direction.getY());
        state.isMoving = true;
    }
    
    private Point getPlayerCenter() {
        return new Point(
            player.getXPos() - Constants.PLAYER_WIDTH / 4,
            player.getYPos() - Constants.PLAYER_HEIGHT / 4
        );
    }
    
    private Vector2D calculateDirection(Point from, Point to) {
        return new Vector2D(to.x - from.x, to.y - from.y);
    }
    
    private void updatePortal(Portals portal, PortalState state) {
        if (!state.isMoving || (state.directionX == 0 && state.directionY == 0)) {
            state.isMoving = false;
            return;
        }

        Point currentPos = new Point(portal.getXPos(), portal.getYPos());
        Point newPos = calculateNewPosition(currentPos, state);
        
        // Check for out of bounds
        if (isOutOfBounds(newPos)) {
            state.isMoving = false;
            return;
        }
        
        // Check for platform collision
        CollisionResult collision = checkPlatformCollision(currentPos, newPos, state);
        if (collision.hasCollision) {
            portal.setPos(collision.finalPosition.x, collision.finalPosition.y);
            state.isMoving = false;
        } else {
            portal.setPos(newPos.x, newPos.y);
        }
    }
    
    private Point calculateNewPosition(Point currentPos, PortalState state) {
        double moveX = state.directionX * Constants.PORTAL_SPEED;
        double moveY = state.directionY * Constants.PORTAL_SPEED;
        
        return new Point(
            (int) Math.round(currentPos.x + moveX),
            (int) Math.round(currentPos.y + moveY)
        );
    }
    
    private boolean isOutOfBounds(Point pos) {
        return pos.x < 0 || pos.x > OUT_OF_BOUNDS_LIMIT || 
               pos.y < 0 || pos.y > OUT_OF_BOUNDS_LIMIT;
    }
    
    private CollisionResult checkPlatformCollision(Point oldPos, Point newPos, PortalState state) {
        Rectangle portalBox = new Rectangle(null, newPos.x, newPos.y, 
                                          Constants.PORTAL_WIDTH, Constants.PORTAL_HEIGHT, true);
        
        for (Rectangle platform : currentLevel.getPlatforms()) {
            if (platform.getIgnoreCollisions()) {
                continue;
            }
            
            if (portalBox.intersects(platform)) {
                Point finalPos = calculateCollisionPosition(oldPos, newPos, platform, state);
                return new CollisionResult(true, finalPos);
            }
        }
        
        return new CollisionResult(false, newPos);
    }
    
    private Point calculateCollisionPosition(Point oldPos, Point newPos, Rectangle platform, PortalState state) {
        int platformTop = platform.getYPos();
        int platformBottom = platform.getYPos() + platform.getHeight();
        int platformLeft = platform.getXPos();
        int platformRight = platform.getXPos() + platform.getWidth();
        
        int portalWidth = Constants.PORTAL_WIDTH;
        int portalHeight = Constants.PORTAL_HEIGHT;
        
        // Determine primary movement direction
        boolean movingPrimarilyVertical = Math.abs(state.directionY) > Math.abs(state.directionX);
        
        if (movingPrimarilyVertical) {
            // Moving down and hitting top of platform
            if (state.directionY > 0 && oldPos.y + portalHeight <= platformTop) {
                return new Point(newPos.x, platformTop - portalHeight);
            }
            // Moving up and hitting bottom of platform
            else if (state.directionY < 0 && oldPos.y >= platformBottom) {
                return new Point(newPos.x, platformBottom);
            }
        } else {
            // Moving right and hitting left side of platform
            if (state.directionX > 0 && oldPos.x + portalWidth <= platformLeft) {
                return new Point(platformLeft - portalWidth, newPos.y);
            }
            // Moving left and hitting right side of platform
            else if (state.directionX < 0 && oldPos.x >= platformRight) {
                return new Point(platformRight, newPos.y);
            }
        }
        
        // Fallback: stop at current position if collision logic fails
        return oldPos;
    }
    
    /**
     * Helper class for collision detection results
     */
    private static class CollisionResult {
        final boolean hasCollision;
        final Point finalPosition;
        
        CollisionResult(boolean hasCollision, Point finalPosition) {
            this.hasCollision = hasCollision;
            this.finalPosition = finalPosition;
        }
    }
    
    /**
     * Simple 2D vector class for direction calculations
     */
    private static class Vector2D {
        private double x, y;
        
        Vector2D(double x, double y) {
            this.x = x;
            this.y = y;
        }
        
        double getMagnitude() {
            return Math.sqrt(x * x + y * y);
        }
        
        void normalize() {
            double magnitude = getMagnitude();
            if (magnitude > 0) {
                x /= magnitude;
                y /= magnitude;
            }
        }
        
        double getX() { return x; }
        double getY() { return y; }
    }
    
    public void draw(Graphics g) {
        if (bluePortal.isExisting()) {
            bluePortal.draw(g);
        }
        if (orangePortal.isExisting()) {
            orangePortal.draw(g);
        }
    }

    public Portals getBluePortal() {
        return bluePortal;
    }

    public Portals getOrangePortal() {
        return orangePortal;
    }

    public boolean getIsMoving() {
        return blueState.isMoving || orangeState.isMoving;
    }
}