public final class Constants {
    private Constants() {} // Prevent instantiation

    // Game loop
    public static final int DELAY = 16; // Approx 60 FPS

    // Window dimensions
    public static final int SCREEN_WIDTH = 1080;
    public static final int SCREEN_HEIGHT = 720;

    // Physics
    public static final int TERMINAL_VELOCITY = 25; // Max fall speed
    public static final int MAX_X_VELO_GROUNDED = 20; //Max x velocity while on the ground
    public static final int MAX_X_VELO_AIR = 15; //Max x velocity while in the air
    public static final double Y_ACCEL = 1.55;      
    public static final double X_ACCEL_GROUNDED = 0.55; //
    public static final double X_DEACCEL_GROUNDED = 0.85;
    public static final double X_ACCEL_AIR = 0.35; // Slightly faster while in the air
    public static final double X_DEACCEL_AIR = 0.35;
    public static final int JUMP_FORCE = -25;     // Negative for upward force
    
    // Portals
    public static final int PORTAL_COOLDOWN = 15; // 15 frames (.25 seconds)
    public static final int PORTAL_SPEED = 40;
    public static final int PORTAL_DISTANCE = 50; 

    // Rectangles
    public static final int PLATFORM_OFFSET = 35;

    // Player dimensions (images)
    public static final int PLAYER_WIDTH = 66; 
    public static final int PLAYER_HEIGHT = 133;
}