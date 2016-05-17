package com.gam;

//UpgradeHolder.java
//Henry Li
//A layout that holds all of the buttons for mutations

//No testing on this class, since it is just a layout and doesn't have action listeners for the buttons

        import java.awt.*;
        import java.awt.event.*;
        import javax.swing.*;


public class UpgradeHolder extends JPanel
{
    private String cont;
    private int center;

    public UpgradeHolder()
    {
        setLayout(new GridLayout(3,4,10,10));
    }

    public void paintComponent(Graphics g)
    {
        super.paintComponent(g);
        //g.drawString(cont,center, 10);
    }
}