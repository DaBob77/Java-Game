import javax.swing.*;
import javax.swing.Timer;
import java.util.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.image.*;
import javax.imageio.*;
import java.io.*;

public class FinalGame1 {
    private static final int DELAY = 16; //16 for 60 fps
    public static void main(String[] args) {
        JFrame frame = new JFrame("Portal 2d");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(1080, 720);
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

        Character player = new Character(finalPlayerImage, 0, 0);
        ArrayList<Rectangle> platforml1 = new ArrayList<Rectangle>(Arrays.asList(new Rectangle(finalTestRect, 0, 950, 100, 2000)));
        Level l1 = new Level(1, platforml1);
        PlayerHandler playerHandler = new PlayerHandler(player); //Create a new playerHandler to use for the player

        JPanel gamePanel = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                player.draw(g);
                l1.draw(g);
                }
            };
        
        // Add mouse listener to get click coordinates
        gamePanel.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                int x = e.getX();
                int y = e.getY();
                System.out.println("Mouse clicked at: (" + x + ", " + y + ")");
            }
        });



        frame.add(gamePanel);
        frame.setVisible(true);
        

        //Use timer for gameloop
        Timer gameTimer = new Timer(DELAY, new ActionListener() {
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


