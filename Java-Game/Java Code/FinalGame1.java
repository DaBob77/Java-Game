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


    ArrayList<Rectangle> platforml1 = new ArrayList<>(Arrays.asList(
        new Rectangle(Images.ALL_LOADED_IMAGES.get(3), 0, 0, 1000, 1000, true), // Background, no collisions
        new Rectangle(null, 8, 903, 927, 406, false),
        new Rectangle(null, 8, 107, 64, 843, false),
        new Rectangle(null, 867, 110 ,61, 839, false),
        new Rectangle(null, 136, 678, 92, 70, false),
        new Rectangle(null, 259, 729, 131, 69, false),
        new Rectangle(null, 412, 789, 109, 72, false)
    ));

    Inputs inputHandler = new Inputs();


    Level l1 = new Level(1, 300, 100, 500, 500, platforml1); // If we had more time, we would load levels from a file instead
    Level l2 = new Level(2, 100, 100, 500, 500, new ArrayList<>()); 

    Character player = new Character(Images.ALL_LOADED_IMAGES.get(0), l1.getStartXPos(), l1.getStartYPos()); // Start player at (100, 100)
    GunHandler gunHandler = new GunHandler(player, inputHandler, l1); //Create a gun handler with input for mouse clicks and player for positioning
    PlayerHandler playerHandler = new PlayerHandler(player, inputHandler, gunHandler); //Create a new playerHandler to use for the player, along with an inputHandler for movement
    


        JPanel gamePanel = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                l1.draw(g);
                gunHandler.draw(g);
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
                gunHandler.updateGun();
                
                // Repaint the panel
                gamePanel.repaint();
            }
        });

        gameTimer.start();
    }
}

