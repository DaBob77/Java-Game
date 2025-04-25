public class Collisions {
    // Check if two rectangles intersect
    public static boolean isColliding(Rectangle r1, Rectangle r2) {
        return r1.intersects(r2);
    }

    // Check if a player collides with a platform (example)
    public static boolean isPlayerColliding(Player player, Rectangle platform) {
        Rectangle playerRect = new Rectangle(
            player.getXPos(),
            player.getYPos(),
            player.getImage().getWidth(),
            player.getImage().getHeight()
        );
        return isColliding(playerRect, platform);
    }
}

