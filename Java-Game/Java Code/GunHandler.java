import java.awt.*;

/**
 * Handles portal gun mechanics including firing, movement, and collision detection
 */
public class GunHandler {
    private static final int OUT_OF_BOUNDS_LIMIT = 9999;
    private static final double MIN_DIRECTION_THRESHOLD = 1.0;
    private static final int MIN_PORTAL_DISTANCE = 100; // Minimum distance between portals
    
    // Wall direction constants
    public static final int WALL_TOP = 0;
    public static final int WALL_BOTTOM = 1;
    public static final int WALL_LEFT = 2;
    public static final int WALL_RIGHT = 3;
    
    private final Character player;
    private final Inputs input;
    private final Level currentLevel;

    private final Portals bluePortal;
    private final Portals orangePortal;
    
    private final PortalState blueState;
    private final PortalState orangeState;
    
    private int bluePortalWallDirection = -1; // -1 means no direction set
    private int orangePortalWallDirection = -1;

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
            updatePortal(bluePortal, blueState, orangePortal, true);
        }
        
        if (orangeState.isMoving) {
            updatePortal(orangePortal, orangeState, bluePortal, false);
        }
    }
    
    private void decrementCooldowns() {
        blueState.cooldown = Math.max(0, blueState.cooldown - 1);
        orangeState.cooldown = Math.max(0, orangeState.cooldown - 1);
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
            player.getXPos() + Constants.PORTAL_X_OFFSET,
            player.getYPos() + Constants.PORTAL_Y_OFFSET
        );
    }
    
    private Vector2D calculateDirection(Point from, Point to) {
        return new Vector2D(to.x - from.x, to.y - from.y);
    }
    
    private double calculateDistance(Point pos1, Point pos2) {
        double dx = pos1.x - pos2.x;
        double dy = pos1.y - pos2.y;
        return Math.sqrt(dx * dx + dy * dy);
    }
    
    private boolean isValidPortalPlacement(Point newPosition, Portals otherPortal) {
        if (!otherPortal.isExisting() || 
            otherPortal.getXPos() <= -OUT_OF_BOUNDS_LIMIT || 
            otherPortal.getYPos() <= -OUT_OF_BOUNDS_LIMIT) {
            return true;
        }
        
        Point otherPosition = new Point(otherPortal.getXPos(), otherPortal.getYPos());
        double distance = calculateDistance(newPosition, otherPosition);
        
        return distance >= MIN_PORTAL_DISTANCE;
    }
    
    private void updatePortal(Portals portal, PortalState state, Portals otherPortal, boolean isBluePortal) {
        if (!state.isMoving || (state.directionX == 0 && state.directionY == 0)) {
            state.isMoving = false;
            return;
        }

        Point currentPos = new Point(portal.getXPos(), portal.getYPos());
        Point newPos = calculateNewPosition(currentPos, state);
        
        if (isOutOfBounds(newPos)) {
            state.isMoving = false;
            return;
        }
        
        CollisionResult collision = checkPlatformCollision(currentPos, newPos, state);
        if (collision.hasCollision) {
            if (isValidPortalPlacement(collision.finalPosition, otherPortal)) {
                portal.setPos(collision.finalPosition.x, collision.finalPosition.y);
                if (isBluePortal) {
                    bluePortalWallDirection = collision.wallDirection;
                } else {
                    orangePortalWallDirection = collision.wallDirection;
                }
            } else {
                portal.setPos(-OUT_OF_BOUNDS_LIMIT, -OUT_OF_BOUNDS_LIMIT);
                portal.setExisting(false);
                if (isBluePortal) {
                    bluePortalWallDirection = -1;
                } else {
                    orangePortalWallDirection = -1;
                }
            }
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
        Rectangle portalBox = new Rectangle(null, newPos.x, newPos.y, Constants.PORTAL_WIDTH, Constants.PORTAL_HEIGHT, true);
        
        for (Rectangle platform : currentLevel.getPlatforms()) {
            if (platform.getIgnoreCollisions()) {
                continue;
            }
            
            if (portalBox.intersects(platform)) {
                WallCollisionResult wallResult = calculateCollisionPosition(oldPos, newPos, platform, state);
                return new CollisionResult(true, wallResult.finalPosition, wallResult.wallDirection);
            }
        }
        
        return new CollisionResult(false, newPos, -1);
    }
    
    private WallCollisionResult calculateCollisionPosition(Point oldPos, Point newPos, Rectangle platform, PortalState state) {
        int platformTop = platform.getYPos();
        int platformBottom = platform.getYPos() + platform.getHeight();
        int platformLeft = platform.getXPos();
        int platformRight = platform.getXPos() + platform.getWidth();
        
        int portalWidth = Constants.PORTAL_WIDTH;
        int portalHeight = Constants.PORTAL_HEIGHT;
        
        boolean movingPrimarilyVertical = Math.abs(state.directionY) > Math.abs(state.directionX);
        
        if (movingPrimarilyVertical) {
            if (state.directionY > 0 && oldPos.y + portalHeight <= platformTop) {
                int finalX = Math.max(platformLeft, Math.min(newPos.x, platformRight - portalWidth));
                return new WallCollisionResult(new Point(finalX, platformTop - portalHeight), WALL_TOP);
            } else if (state.directionY < 0 && oldPos.y >= platformBottom) {
                int finalX = Math.max(platformLeft, Math.min(newPos.x, platformRight - portalWidth));
                return new WallCollisionResult(new Point(finalX, platformBottom), WALL_BOTTOM);
            }
        } else {
            if (state.directionX > 0 && oldPos.x + portalWidth <= platformLeft) {
                int finalY = Math.max(platformTop, Math.min(newPos.y, platformBottom - portalHeight));
                return new WallCollisionResult(new Point(platformLeft - portalWidth, finalY), WALL_LEFT);
            } else if (state.directionX < 0 && oldPos.x >= platformRight) {
                int finalY = Math.max(platformTop, Math.min(newPos.y, platformBottom - portalHeight));
                return new WallCollisionResult(new Point(platformRight, finalY), WALL_RIGHT);
            }
        }
        
        return new WallCollisionResult(oldPos, -1);
    }
    
    private static class WallCollisionResult {
        final Point finalPosition;
        final int wallDirection;
        
        WallCollisionResult(Point finalPosition, int wallDirection) {
            this.finalPosition = finalPosition;
            this.wallDirection = wallDirection;
        }
    }
    
    private static class CollisionResult {
        final boolean hasCollision;
        final Point finalPosition;
        final int wallDirection;
        
        CollisionResult(boolean hasCollision, Point finalPosition, int wallDirection) {
            this.hasCollision = hasCollision;
            this.finalPosition = finalPosition;
            this.wallDirection = wallDirection;
        }
    }
    
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

    public Portals getBluePortal() { return bluePortal; }
    public Portals getOrangePortal() { return orangePortal; }
    public boolean getIsMoving() { return blueState.isMoving || orangeState.isMoving; }
    public int getBluePortalWallDirection() { return bluePortalWallDirection; }
    public int getOrangePortalWallDirection() { return orangePortalWallDirection; }
}