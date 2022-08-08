package SnakeGame;

import java.awt.Color;
import javax.swing.JFrame;

public class SnakeGame{
    public static void main(String[] args) {
        JFrame frame = new JFrame("Snake Game By Anurag");
        frame.setBounds(100,10,905,700);
        frame.setResizable(false);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        

        GamePanel panel = new GamePanel();
        panel.setBackground(Color.DARK_GRAY);
        frame.add(panel);

        frame.setVisible(true);
    }
}
