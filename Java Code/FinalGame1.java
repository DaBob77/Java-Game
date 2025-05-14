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


    Character player = new Character(finalPlayerImage, 250, 250); // Start player at (100, 100)
    ArrayList<Rectangle> platforml1 = new ArrayList<>(Arrays.asList(
        new Rectangle(finalTestRect, 0, 0, 1000, 1000, true), // Background, no collisions
        new Rectangle(finalTestRect, 8, 903, 927, 406, false),
        new Rectangle(finalTestRect, 8, 107, 64, 843, false),
        new Rectangle(finalTestRect, 867, 110 ,61, 839, false),
        new Rectangle(finalTestRect, 136, 678, 92, 70, false),
        new Rectangle(finalTestRect, 259, 729, 131, 69, false),
        new Rectangle(finalTestRect, 412, 789, 109, 72, false)
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


