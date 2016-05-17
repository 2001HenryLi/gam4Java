package com.gam;

/*
Spencer Chang
Player.java
4/18/16
Description: Handles the player movement's, user input, health, hunger,
and other properties
*/

// import statement(s)
import java.awt.*;

public class Player extends Entity
{
    /*
    * Creates a new instance of the player class and initializes it
    */
    public Player(String imagePath)
    {
        // overrides the super constructor
        super(imagePath);

        // Initializes the fields for the player
        // Points are defaulted to zero
        points = 0;

        // Player's amount of turns is defaulted to zero
        turns = 16;
    }

    public void setTexture(String imagePath)
    {
        setTexture(imagePath);
    }

    public void setTurns(int turns0)
    {
        // sets the amount of turns
        turns = turns0;
    }

    // handles keyboard input
    public void handleInput(char key)
    {
        int x = (int)position.getX();
        int y = (int)position.getY();

        // checks for WASD keys and moves the character based on them
        // not using if else because multiple keys may be pressed
        if (key == 'o')
        {
            turns++;
        }

        // if the player still has turns, then they can move
        if (turns > 0)
        {
            // checks each key being pressed and increments position
            // accordingly.
            if (key == 'w')
            {
                position.setY(y - MOVESPEED);
                turns--;
            }
            if (key == 'a')
            {
                position.setX(x - MOVESPEED);
                turns--;
            }
            if (key == 's')
            {
                position.setY(y + MOVESPEED);
                turns--;
            }
            if (key == 'd')
            {
                position.setX(x + MOVESPEED);
                turns--;
            }
        }
    }

    public void resetFrame()
    {
        // resets the animation
        currentFrame = 0;
    }

}