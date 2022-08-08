package BrickBreakerGame;

import javax.swing.JPanel;
import javax.swing.Timer;

import java.awt.event.KeyListener;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.Rectangle;
import java.awt.Font;

public class GamePlay extends JPanel implements KeyListener, ActionListener{
    private boolean play = false;
    private int score = 0;

    private int totalBricks = 28;

    private Timer timer;
    private int delay = 1;

    private int ballPosX = 120;
    private int ballPosY = 350;
    private int ballXdir = -1;
    private int ballYdir = -2;
    private int playerX = 350;

    private MapGenerator map;  // object from MapGenerator Class

    public GamePlay() {
        addKeyListener(this);
        setFocusable(true);
        setFocusTraversalKeysEnabled(false);
        timer = new Timer(delay,this);
        timer.start();

        map = new MapGenerator(4, 7);
    }

   @Override
   public void paint(Graphics g) {
       // black canvas
       super.paint(g);
       g.setColor(Color.black);
       g.fillRect(1, 1, 692, 592);

       //border
       g.setColor(Color.yellow);
       g.fillRect(0, 0, 700, 3);

       g.setColor(Color.yellow);
       g.fillRect(0, 3, 3, 592);

       g.setColor(Color.yellow);
       g.fillRect(683, 3, 3, 592);

       g.setColor(Color.red);
       g.fillRect(3, 560, 690, 3);

       // paddle
       g.setColor(Color.green);
       g.fillRect(playerX, 545, 100, 13);

       // Ball
       g.setColor(Color.red);
       g.fillOval(ballPosX, ballPosY, 20, 20);

       //Bricks
       map.draw((Graphics2D) g);

       //Score 
       g.setColor(Color.green);
       g.setFont(new Font("serif",Font.BOLD,20));
       g.drawString("Score : "+ score, 550, 30);

       //GameOver
       if(ballPosY>=570){
           play = false;
           ballXdir = 0;
           ballYdir = 0;

           g.setColor(Color.green);
           g.setFont(new Font("serif",Font.BOLD,30));
           g.drawString("GameOver !!", 250, 250);
           g.drawString("Score : " + score, 250, 300);

           g.setColor(Color.red);
           g.setFont(new Font("serif",Font.BOLD,20));
           g.drawString("Press SPACE to Restart !!", 250, 350);
       }

       // winning

       if(totalBricks<=0){
        play = false;
        ballXdir = 0;
        ballYdir = 0;

        g.setColor(Color.green);
        g.setFont(new Font("serif",Font.BOLD,30));
        g.drawString("Congrats !! You WON !!", 223, 200);
        g.drawString("Score : " + score, 225, 250);

        g.setColor(Color.red);
        g.setFont(new Font("serif",Font.BOLD,20));
        g.drawString("Press SPACE to Restart !!", 225, 300);
       }

   }
    
    private void moveLeft(){
        play = true;
        playerX -= 20;
    }

    private void moveRight(){
        play = true;
        playerX += 20;
    }
    @Override
    public void keyPressed(KeyEvent e) {
        if(e.getKeyCode()==KeyEvent.VK_LEFT){
            if(playerX<=3)
              playerX = 3;
            else
              moveLeft();
        }
        if(e.getKeyCode()==KeyEvent.VK_RIGHT){
            if(playerX>=582)
              playerX = 582;
            else
              moveRight();
        }
        if(e.getKeyCode()==KeyEvent.VK_SPACE){
            if(!play){
                score = 0;
                totalBricks = 28;
                play = true;
                ballPosX = 120;
                ballPosY = 350;
                ballXdir = -1;
                ballYdir = -2;
                playerX = 320;
                map = new MapGenerator(4, 9);
            }
        }
        repaint();
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if(play){
            if(ballPosX<=0 || ballPosX>=660){
                ballXdir = -ballXdir;
            }
            if(ballPosY<=0){
                ballYdir = -ballYdir;
            }

            // RECTANGLE AROUND BALL
            Rectangle ballRect = new Rectangle(ballPosX, ballPosY, 20, 20);
            Rectangle paddleRect = new Rectangle(playerX, 545, 100, 13);
            if(ballRect.intersects(paddleRect)){
                ballYdir = -ballYdir;
            }
            A: for(int i=0;i<map.map.length;i++){
                for(int j=0;j<map.map[0].length;j++){
                    if(map.map[i][j]>0){
                        int width = map.brickWidth;
                        int height = map.brickHeight;
                        int brickXPos = j*width+80;
                        int brickYPos = i*height+50;
                        Rectangle brickRect = new Rectangle( brickXPos, brickYPos, width, height);

                        if(ballRect.intersects(brickRect)){
                            map.setBrick(0,i,j);
                            totalBricks--;
                            score += 5;
                            if(ballPosX+19<=brickXPos || ballPosX+1>=brickXPos+width) // collide from -->( left || right))
                                ballXdir = -ballXdir;
                            else
                                ballYdir = -ballYdir;
                            
                                break A;
                        }
                           
                    }
                }
            }
            ballPosX += ballXdir;
            ballPosY += ballYdir;
        }
        repaint();
    }
    @Override
    public void keyReleased(KeyEvent e) {
    }
    @Override
    public void keyTyped(KeyEvent e) {
        
    }
    
}
