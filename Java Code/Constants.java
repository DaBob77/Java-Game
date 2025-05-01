public final class Constants {
    private Constants() {} // Prevent instantiation

    // Game loop
    public static final int DELAY = 16; // Approx 60 FPS

    // Window dimensions
    public static final int SCREEN_WIDTH = 1080;
    public static final int SCREEN_HEIGHT = 720;

    // Physics
    public static final int TERMINAL_VELOCITY = 15; // Max fall speed
    public static final double Y_ACCEL = 0.5;      // Adjusted gravity
    public static final int JUMP_FORCE = -12;     // Negative for upward force

    //Player dimensions
    public static final int PLAYER_WIDTH = 56;
    public static final int PLAYER_HEIGHT = 104;
}