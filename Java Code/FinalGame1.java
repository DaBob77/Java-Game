import javax.swing.*;
import javax.swing.Timer;
import java.util.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.image.*;
import javax.imageio.*;
import java.io.*;

public class FinalGame1 {
    public static void main(String[] args) {
        JFrame frame = new JFrame("Portal 2d");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(Constants.SCREEN_WIDTH, Constants.SCREEN_HEIGHT);
        //Create a null image in case file reading fails
        BufferedImage playerImage = null;
        BufferedImage testRect = null;

        try { //Attempt to set all images
            playerImage = ImageIO.read(new File("IMAGES/MC.png"));
            testRect = ImageIO.read(new File("IMAGES/testrect.png"));

        } catch(IOException e) {
            System.out.println("At least one image failed to load");
            e.printStackTrace();
        }

        //Convert image to final to use in other classes
        final BufferedImage finalPlayerImage = playerImage;
        final BufferedImage finalTestRect = testRect;


    Character player = new Character(finalPlayerImage, 1000, 250); // Start player at (100, 100)
    // Platform: x=0, y=500, width=1080, height=50
    ArrayList<Rectangle> platforml1 = new ArrayList<>(Arrays.asList(
        new Rectangle(finalTestRect, 500, 0, 1500,1000, true), //Ignore collisions, only used for the image
        new Rectangle(null, 0, 1000, 1000, 950, false)
        //new Rectangle(null, 508, 1029, 68, 915, false),
        //new Rectangle(null, 1447, 1030, 59, 915, false),
        //new Rectangle(null, 947, 934, 117, 78, false),
        //new Rectangle(null, 782, 865, 141, 73, false),
        //new Rectangle(null, 647, 809, 100, 72, false)
    ));

    Level l1 = new Level(1, platforml1);
    Inputs inputHandler = new Inputs();
    PlayerHandler playerHandler = new PlayerHandler(player, inputHandler); //Create a new playerHandler to use for the player, along with an inputHandler for movement


        JPanel gamePanel = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                l1.draw(g);
                player.draw(g);
                }
            };
        frame.addKeyListener(inputHandler);
        frame.addMouseListener(inputHandler);
        frame.addMouseMotionListener(inputHandler);

        frame.add(gamePanel);
        frame.setFocusable(true);
        frame.requestFocusInWindow();
        frame.setVisible(true);
        

        //Use timer for gameloop
        Timer gameTimer = new Timer(Constants.DELAY, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                playerHandler.updatePlayer(l1);
                
                // Repaint the panel
                gamePanel.repaint();
            }
        });

        gameTimer.start();
    }
}


