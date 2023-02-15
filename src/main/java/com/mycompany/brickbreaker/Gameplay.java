/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Other/File.java to edit this template
 */
package com.mycompany.brickbreaker;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

/**
 *
 * @author saubhagyadwitiya
 */
public class Gameplay extends JPanel implements KeyListener, ActionListener {

    /**
     * @param args the command line arguments
     */
    //set all the variables as player, ball, score, timer, balldirection
    private boolean play = false;
    private int score = 0;
    private int totalbricks = 21;
    private Timer timer;
    private int delay = 8;
    private int playerX = 310;
    private int playerY = 120;
    private int ballposX = 120;
    private int ballposY = 350;
    private int balldirX = -1;
    private int balldirY = -2;
    //initialize mapgenerater object
    private MapGenerator map;
    
    //contructor
    Gameplay(){
        map = new MapGenerator(3,7);
        addKeyListener(this);
        setFocusable(true);
        setFocusTraversalKeysEnabled(false);
        timer = new Timer(delay, this);
        timer.start();
    }
    
    public void paint(Graphics g) {
        g.setColor(Color.LIGHT_GRAY);
        g.fillRect(1, 1, 692, 592);

        map.draw((Graphics2D) g);

        //Borders
        g.setColor(Color.BLACK);
        g.fillRect(0, 0, 3, 592);
        g.fillRect(0, 0, 692, 3);
        g.fillRect(691, 0, 3, 592);

        //score
        g.setColor(Color.BLACK);
        g.setFont(new Font("serif", Font.BOLD, 25));
        g.drawString("" + score, 590, 30);

        //player
        g.setColor(Color.magenta);
        g.fillRect(playerX, 550, 100, 8);

        //ball
        g.setColor(Color.WHITE);
        g.fillOval(ballposX, ballposY, 20, 20);
     
        if(ballposY > 570){
            play = false;
            balldirX = 0;
            balldirY = 0;
            g.setColor(Color.red);
            g.setFont(new Font("serif",Font.BOLD,20));
            g.drawString("Game Over Score: " +score, 190, 300);
            
            g.setFont(new Font("serif",Font.BOLD,20));
            g.drawString("Press Enter to Restart", 190, 340);
        }
        
        if(totalbricks == 0){
            play = false;
            balldirX = -2;
            balldirY = -1;
            g.setColor(Color.red);
            g.setFont(new Font("serif",Font.BOLD,20));
            g.drawString("    Game Over: "+score,190,300);

            g.setFont(new Font("serif", Font.BOLD, 20));
            g.drawString("   Press Enter to Restart", 190, 340);
        }
        
        g.dispose();
    }
    
    @Override
    public void actionPerformed(ActionEvent e){
        timer.start();
        
        if(play){
            //if the ball intersects the slider
            if(new Rectangle(ballposX, ballposY,20,20).intersects(new Rectangle(playerX, 550,100,8))){
                balldirY = -balldirY;
            }
            
            outer:
            for(int i=0; i<map.map.length; i++){
                
                for(int j=0; j<map.map[0].length;j++){
                    if(map.map[i][j] > 0){
                        
                        //define the brick height and width
                        int brickX = j * map.bricksWidth + 80;
                        int brickY = i * map.bricksHeight + 50;
                        
                        int bricksWidth = map.bricksWidth;
                        int bricksHeight = map.bricksHeight;
                        
                        Rectangle rect = new Rectangle(brickX,brickY,bricksWidth,bricksHeight);
                        Rectangle ballrect = new Rectangle(ballposX,ballposY,20,20);
                        
                        Rectangle brickrect = rect;
                        
                        //if ball intersects brick
                        if(ballrect.intersects(brickrect)){
                            //set brick value to 0 in the matrix at ith and jth pos
                            map.setBricksValue(0, i, j);
                            totalbricks--;
                            score+=5;
                            
                            if(ballposX +19 <= brickrect.x || ballposX +1 >= brickrect.x + bricksWidth) {
                                balldirX = -balldirX;
                            } else {
                                balldirY = -balldirY;
                            }
                            break outer;
                        }
                    }
                }
            }
            
            //move the ball
            ballposX += balldirX;
            ballposY += balldirY;
            //when ball will hit the three borders
            if(ballposX < 0){
                balldirX = -balldirX;
            }
            if(ballposY < 0){
                balldirY = -balldirY;
            }
            if(ballposX > 670){
                balldirX = -balldirX;
            }
        }
        repaint();
    }
    
    @Override
    public void keyTyped(KeyEvent e){
        
    }
    
    @Override
    public void keyReleased(KeyEvent e){
        
    }
    
    @Override
    public void keyPressed(KeyEvent e){
        //if the right key is pressed
        if(e.getKeyCode() == KeyEvent.VK_RIGHT){
            //if slider itself is at right boundary
            if(playerX >= 600){
                playerX = 600;
            } else {
                moveRight();
            }
        }
        //if the left key is pressed
        if(e.getKeyCode() == KeyEvent.VK_LEFT){
            //if the slider itself is at the left boundary
            if(playerX < 10){
                playerX = 10;
            } else {
                moveLeft();
            }
        }
        
        //if the enter key is pressed
        if(e.getKeyCode() == KeyEvent.VK_ENTER){
            //if the game is over
            if(!play){
                //reset all the values
                ballposX = 120;
                ballposY = 350;
                balldirX = -1;
                balldirY = -2;
                score = 0;
                playerX = 310;
                totalbricks = 21;
                map = new MapGenerator(3,7);
                
                repaint();
            }
        }
    }
    
    public void moveRight(){
        play = true;
        playerX += 20;
    }
    
    public void moveLeft(){
        play = true;
        playerX -=20;
    }
}





