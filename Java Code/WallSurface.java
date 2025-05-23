public enum WallSurface {
    NONE,       // Not attached to a specific surface
    LEFT_WALL,  // Portal is on the left-facing surface of a platform
    RIGHT_WALL, // Portal is on the right-facing surface of a platform
    FLOOR,      // Portal is on a horizontal top surface of a platform (floor)
    CEILING     // Portal is on a horizontal bottom surface of a platform (ceiling)
}