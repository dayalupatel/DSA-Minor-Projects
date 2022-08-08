package BrickBreakerGame;

import javax.swing.JFrame;

public class Main {
    public static void main(String[] args) {
        JFrame frame = new JFrame();
        
        frame.setSize(700,600);
        frame.setTitle("BreakOut Ball");

        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setResizable(false);

        GamePlay gamePlay = new GamePlay();
        frame.add(gamePlay);

        frame.setVisible(true);
    }
}
