import javax.swing.*;
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

        JPanel gamePanel = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                g.drawImage(finalPlayerImage, 360, 540, null);
                }
            };

        frame.add(gamePanel);

        frame.setVisible(true);
    }
}


