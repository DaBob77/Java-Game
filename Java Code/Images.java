import java.awt.image.BufferedImage;
import javax.imageio.ImageIO;
import java.io.IOException;
import java.io.File;

/**
 * Utility class for loading and storing game images.
 */
public class Images {
    public static BufferedImage PLAYER;
    public static BufferedImage TEST_RECT;
    public static BufferedImage ORANGE_PORTAL;

    static {
        try {
            PLAYER = ImageIO.read(new File("U:/Java Programs/Java Final Game/Java-Game/Java-Game/IMAGES/MC.png"));
        } catch (IOException e) {
            System.out.println("Failed to load PLAYER image.");
            PLAYER = null;
        }
        try {
            TEST_RECT = ImageIO.read(new File("U:/Java Programs/Java Final Game/Java-Game/Java-Game/IMAGES/testrect.png"));
        } catch (IOException e) {
            System.out.println("Failed to load TEST_RECT image.");
            TEST_RECT = null;
        }
        try {
            ORANGE_PORTAL = ImageIO.read(new File("U:/Java Programs/Java Final Game/Java-Game/Java-Game/IMAGES/orange_portal.jpg"));
        } catch (IOException e) {
            System.out.println("Failed to load ORANGE_PORTAL image.");
            ORANGE_PORTAL = null;
        }
    }

    // Prevent instantiation
    private Images() {}
}
