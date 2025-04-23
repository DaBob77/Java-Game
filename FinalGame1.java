import javax.swing.*;
import java.util.*;
import java.awt.*;
import java.awt.event.*;

public class FinalGame1 {
    public static void main(String[] args) {
        JFrame frame = new JFrame("Portal 2d");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(1080, 720);

        JPanel gamePanel = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                g.setColor(Color.BLACK);
                g.fillRect(360, 540, 50, 50);
            }
        };

        frame.add(gamePanel);

        frame.setVisible(true);
    }
}


