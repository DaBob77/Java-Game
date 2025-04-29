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

        try {
            playerImage = ImageIO.read(new File("IMAGES/MC.png"));
        } catch(IOException e) {
            System.out.println("Image failed to load");
            e.printStackTrace();
        }

        //Convert image to final to use in other classes
        final BufferedImage finalPlayerImage = playerImage;

        Character player = new Character(finalPlayerImage, 0, 0);
        ArrayList<Rectangle> platforml1 = new ArrayList<Rectangle>(Arrays.asList(new Rectangle(0, 800, 50, 800)));
        Level l1 = new Level(1, platforml1);
        PlayerHandler playerHandler = new PlayerHandler(player); //Create a new playerHandler to use for the player

        JPanel gamePanel = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                
                player.draw(g);
                }
            };

        frame.add(gamePanel);
        frame.setVisible(true);
        

        //Use timer for gameloop
        Timer gameTimer = new Timer(DELAY, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                playerHandler.updatePlayer(l1);
                
                // Repaint the panel
                gamePanel.repaint();
                System.out.println(player);
            }
        });

        gameTimer.start();
    }
}


