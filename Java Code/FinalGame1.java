import javax.swing.*;
import java.util.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.image.*;
import javax.imageio.*;
import java.io.*;

public class FinalGame1 {
    public static void main(String[] args) {
        double deltaTime = 0;
        JFrame frame = new JFrame("Portal 2d");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(1080, 720);

        // Create a null image in case file reading fails
        BufferedImage playerImage = null;

        try {
            playerImage = ImageIO.read(new File("IMAGES/MC.png"));
        } catch (IOException e) {
            System.out.println("Image failed to load");
            e.printStackTrace();
        }

        // Convert image to final to use in other classes
        final BufferedImage finalPlayerImage = playerImage;

        // Initial player position
        final int[] playerPosition = {360, 540};

        JPanel gamePanel = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                g.drawImage(finalPlayerImage, playerPosition[0], playerPosition[1], null);
            }
        };

        // Add key listener for movement
        frame.addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                int key = e.getKeyCode();
                switch (key) {
                    case KeyEvent.VK_UP:
                        playerPosition[1] -= 10; // Move up
                        break;
                    case KeyEvent.VK_DOWN:
                        playerPosition[1] += 10; // Move down
                        break;
                    case KeyEvent.VK_LEFT:
                        playerPosition[0] -= 10; // Move left
                        break;
                    case KeyEvent.VK_RIGHT:
                        playerPosition[0] += 10; // Move right
                        break;
                }
                gamePanel.repaint(); // Repaint the panel to reflect the new position
            }
        });

        frame.add(gamePanel);
        frame.setVisible(true);
    }
}





