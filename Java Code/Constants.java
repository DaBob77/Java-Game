public final class Constants {
    private Constants() {} // Prevent instantiation

    // Game loop
    public static final int DELAY = 100; // Approx 60 FPS

    // Window dimensions
    public static final int SCREEN_WIDTH = 1080;
    public static final int SCREEN_HEIGHT = 72;

    // Physics
    public static final int TERMINAL_VELOCITY = 25; // Max fall speed
    public static final int MAX_X_VELO_GROUNDED = 20; //Max x velocity while on the ground
    public static final int MAX_X_VELO_AIR = 15; //Max x velocity while in the air
    public static final double Y_ACCEL = 1.55;      
    
    //Player dimensions (images)
    public static final int PLAYER_WIDTH = 56; 
    public static final int PLAYER_HEIGHT = 104;
}