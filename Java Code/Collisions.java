public class Collisions {
    // Check if two rectangles intersect
    public static boolean isColliding(Rectangle r1, Rectangle r2) {
        // Ensure non-null before calling method
        if (r1 == null || r2 == null) {
            return false;
        }
        // Call the instance method on r1 (or r2)
        return r1.intersects(r2);
    }

    //Check if a player is colliding with a platform
    public static boolean isPlayerColliding(Character player, Rectangle platform) {
        //Convert player to a rectangle for simple collisions
        // Use player's width and height getters correctly
        Rectangle playerRect = new Rectangle(null, player.getXPos(), player.getYPos(), player.getImageWidth(), player.getImageHeight(), );
        return isColliding(playerRect, platform);
    }
}