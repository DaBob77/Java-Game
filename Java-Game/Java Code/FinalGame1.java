import java.awt.*;
import java.awt.event.*;
import java.util.*;
import javax.swing.*;
import javax.swing.Timer;

public class FinalGame1 {
    private Level currentLevel; // Make this a class field
    
    public static void main(String[] args) {
        new FinalGame1().startGame(); // Create instance and start game
    }
    
    public void startGame() {
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

        ArrayList<Rectangle> platforml2 = new ArrayList<>(Arrays.asList(
            new Rectangle(Images.ALL_LOADED_IMAGES.get(3), 0, 0, 1000, 1000, true), // Background, no collisions
            new Rectangle(null, 8, 903, 927, 406, false),
            new Rectangle(null, 8, 107, 64, 843, false),
            new Rectangle(null, 867, 110 ,61, 839, false),
            new Rectangle(null, 136, 678, 92, 70, false),
            new Rectangle(null, 259, 729, 131, 69, false),
            new Rectangle(null, 412, 789, 109, 72, false)
        ));

        ArrayList<Rectangle> platforml3 = new ArrayList<>(Arrays.asList(
            new Rectangle(Images.ALL_LOADED_IMAGES.get(3), 0, 0, 1000, 1000, true), // Background, no collisions
            new Rectangle(null, 8, 903, 927, 406, false),
            new Rectangle(null, 8, 107, 64, 843, false),
            new Rectangle(null, 867, 110 ,61, 839, false),
            new Rectangle(null, 136, 678, 92, 70, false),
            new Rectangle(null, 259, 729, 131, 69, false),
            new Rectangle(null, 412, 789, 109, 72, false)
        ));

        Inputs inputHandler = new Inputs();

        Level l1 = new Level(1, 300, 100, 500, 500, platforml1);
        Level l2 = new Level(2, 300, 100, 500, 500, platforml2);
        Level l3 = new Level(3, 300, 100, 500, 500, platforml3);
        Character player = new Character(Images.ALL_LOADED_IMAGES.get(0), l1.getStartXPos(), l1.getStartYPos()); // Start player at (100, 100)
        GunHandler gunHandler = new GunHandler(player, inputHandler, l1); //Create a gun handler with input for mouse clicks and player for positioning
        PlayerHandler playerHandler = new PlayerHandler(player, inputHandler, gunHandler); //Create a new playerHandler to use for the player, along with an inputHandler for movement
        currentLevel = l1; // Initialize the field

        // Store levels in an array for easy access
        Level[] levels = {l1, l2, l3};

        JPanel gamePanel = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                currentLevel.draw(g); // Use currentLevel instead of hardcoded l1
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
                playerHandler.updatePlayer(currentLevel);
                gunHandler.updateGun();

                Level newLevel = checkForTransitions(levels, currentLevel, player);
                if (newLevel != currentLevel) {
                //    currentLevel = newLevel;
                }
                
                // Repaint the panel
                gamePanel.repaint();
            }
        });

        gameTimer.start();
    }

    Level checkForTransitions(Level[] levels, Level level, Character player) {
        // Check if player has reached the end of the level
        if (Math.abs(player.getXPos() - level.getEndXPos()) < 20 && Math.abs(player.getYPos() - level.getEndYPos()) < 20) {
            // Transition to next level or exit
            int nextLevelNum = level.getLevel() + 1;
            if (nextLevelNum > Constants.MAX_LEVELS) {
                System.exit(0); // Exit the game if no more levels
            }
            
            // Return the next level object
            Level nextLevel = levels[nextLevelNum - 1];
            player.setPos(nextLevel.getStartXPos(), nextLevel.getStartYPos());
            return nextLevel;
        }
        return level; // Return current level if no transition
    }
}