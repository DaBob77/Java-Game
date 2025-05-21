import java.awt.image.BufferedImage;
import javax.imageio.ImageIO;
import java.io.IOException;
import java.io.File;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;
import java.util.ArrayList;

/**
 * Utility class for loading and storing game images.
 */
public class Images {
    // Stores all images loaded from the image-names.txt file
    public static final List<BufferedImage> ALL_LOADED_IMAGES = new ArrayList<>();

    private static BufferedImage loadImageSafely(String path, String imageNameForErrorLog) {
        if (path == null || path.trim().isEmpty()) {
            System.out.println("Path for " + imageNameForErrorLog + " image is null or empty.");
            return null;
        }
        try {
            File imageFile = new File(path);
            if (!imageFile.exists()) {
                System.out.println("Image file not found for " + imageNameForErrorLog + " at path: " + path);
                return null;
            }
            return ImageIO.read(imageFile);
        } catch (IOException e) {
            System.out.println("Failed to load " + imageNameForErrorLog + " image from path: " + path + ". Error: " + e.getMessage());
            return null;
        }
    }

    // The findPathInList method is no longer needed as we are loading all images from the file into a list.

    static {
        List<String> imageFilePaths = new ArrayList<>();
        String imageListFilePath = "U:/Java Programs/Java Final Game/Java-Game/Java-Game/txt Files/image-names.txt";
        boolean listReadSuccess = false;

        try {
            if (!Files.exists(Paths.get(imageListFilePath))) {
                System.err.println("ERROR: Image list file not found at " + imageListFilePath);
            } else {
                imageFilePaths = Files.readAllLines(Paths.get(imageListFilePath));
                listReadSuccess = true;
            }
        } catch (IOException e) {
            System.err.println("ERROR: Failed to read image paths from " + imageListFilePath + ". " + e.getMessage());
        }

        if (listReadSuccess) {
            if (imageFilePaths.isEmpty()) {
                System.out.println("Warning: The image list file " + imageListFilePath + " is empty. No images will be loaded into ALL_LOADED_IMAGES.");
            } else {
                System.out.println("Attempting to load images from: " + imageListFilePath);
                for (String path : imageFilePaths) {
                    if (path != null && !path.trim().isEmpty()) {
                        String imageName = new File(path).getName(); // Use filename for logging
                        BufferedImage image = loadImageSafely(path, imageName);
                        if (image != null) {
                            ALL_LOADED_IMAGES.add(image);
                            // System.out.println("Successfully loaded image: " + imageName); // Optional: for verbose logging
                        } else {
                            // Error message is printed by loadImageSafely
                        }
                    } else {
                        System.out.println("Skipping null or empty path in image list file.");
                    }
                }
                if (ALL_LOADED_IMAGES.isEmpty() && !imageFilePaths.isEmpty()) {
                     System.out.println("Warning: No images were successfully loaded into ALL_LOADED_IMAGES, though paths were present in the list.");
                } else if (!ALL_LOADED_IMAGES.isEmpty()){
                    System.out.println("Total images loaded into ALL_LOADED_IMAGES: " + ALL_LOADED_IMAGES.size());
                }
            }
        } else {
            System.out.println("ALL_LOADED_IMAGES will be empty due to failure in reading image list file or file not found.");
        }
    }

    // Prevent instantiation
    private Images() {}
}