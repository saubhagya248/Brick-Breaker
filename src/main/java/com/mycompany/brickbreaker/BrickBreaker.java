/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 */

package com.mycompany.brickbreaker;

import javax.swing.*;
import javax.swing.JFrame;

/**
 *
 * @author saubhagyadwitiya
 */
public class BrickBreaker {

    public static void main(String[] args) {
        
        JFrame j = new JFrame();
        
        //create a gameplay object
        Gameplay g = new Gameplay();
        
        //set the frame properties
        j.setVisible(true);
        j.setBounds(10, 10, 700, 600);
        j.setTitle("BrickBreaker");
        j.setResizable(false);
        j.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        
        //add the gameplay object on frame
        j.add(g);
        
    }
}
