<<<<<<< HEAD
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


    Character player = new Character(Images.PLAYER, 250, 250); // Start player at (100, 100)
    ArrayList<Rectangle> platforml1 = new ArrayList<>(Arrays.asList(
        new Rectangle(Images.TEST_RECT, 0, 0, 1000, 1000, true), // Background, no collisions
        new Rectangle(Images.TEST_RECT, 8, 903, 927, 406, false),
        new Rectangle(Images.TEST_RECT, 8, 107, 64, 843, false),
        new Rectangle(Images.TEST_RECT, 867, 110 ,61, 839, false),
        new Rectangle(Images.TEST_RECT, 136, 678, 92, 70, false),
        new Rectangle(Images.TEST_RECT, 259, 729, 131, 69, false),
        new Rectangle(Images.TEST_RECT, 412, 789, 109, 72, false)
    ));

    Level l1 = new Level(1, platforml1);
    Inputs inputHandler = new Inputs();
    GunHandler gunHandler = new GunHandler(player, inputHandler); //Create a gun handler with input for mouse clicks and player for positioning
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


=======
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


    Character player = new Character(Images.PLAYER, 250, 250); // Start player at (100, 100)
    ArrayList<Rectangle> platforml1 = new ArrayList<>(Arrays.asList(
        new Rectangle(Images.TEST_RECT, 0, 0, 1000, 1000, true), // Background, no collisions
        new Rectangle(Images.TEST_RECT, 8, 903, 927, 406, false),
        new Rectangle(Images.TEST_RECT, 8, 107, 64, 843, false),
        new Rectangle(Images.TEST_RECT, 867, 110 ,61, 839, false),
        new Rectangle(Images.TEST_RECT, 136, 678, 92, 70, false),
        new Rectangle(Images.TEST_RECT, 259, 729, 131, 69, false),
        new Rectangle(Images.TEST_RECT, 412, 789, 109, 72, false)
    ));

    Level l1 = new Level(1, platforml1);
    Inputs inputHandler = new Inputs();
    PlayerHandler playerHandler = new PlayerHandler(player, inputHandler); //Create a new playerHandler to use for the player, along with an inputHandler for movement
    GunHandler gunHandler = new GunHandler(player, inputHandler); //Create a gun handler with input for mouse clicks and player for positioning
    


        JPanel gamePanel = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                l1.draw(g);
                player.draw(g);
                gunHandler.draw(g);
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


>>>>>>> origin/main
