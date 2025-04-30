//Class used to basically turn anything into a rectangle and check if it overlaps with anything else
public class Collisions {
    // Check if two rectangles intersect
    public static boolean isColliding(Rectangle r1, Rectangle r2) {
        return r1.intersects(r1, r2);
    }

    //Check if a player is colliding with a platform
    public static boolean isPlayerColliding(Character player, Rectangle platform) {
        //Convert player to a rectangle for simple collisions
        Rectangle playerRect = new Rectangle(null, player.getXPos(), player.getYPos(), player.getImageHeight(), player.getImageWidth() );
        return isColliding(playerRect, platform);
    }
}

