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

    Character player = new Character(finalPlayerImage, 100, 100); // Start player at (100, 100)
    // Platform: x=0, y=500, width=1080, height=50
    ArrayList<Rectangle> platforml1 = new ArrayList<>(Arrays.asList(
        new Rectangle(finalTestRect, 0, 500, 1080, 50) // Will add more later
    ));
    Level l1 = new Level(1, platforml1);
    PlayerHandler playerHandler = new PlayerHandler(player); //Create a new playerHandler to use for the player
    Inputs inputHandler = new Inputs();

        JPanel gamePanel = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                player.draw(g);
                l1.draw(g);
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


