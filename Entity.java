package com.gam;

/*
Spencer Chang
4/18/16
Entity.java
Description: Superclass for all entities inside of the game, such as
the player, animals, and disasters
*/

// import statement(s)
import java.awt.*;

public class Entity
{
    // the entity's position
    protected Position2D position;

    // the amount of turns the player has left
    protected int turns;

    // entity attributes
    protected int hunger;
    protected int health;
    protected final int MOVESPEED = 64;

    // total amount of evolutionary points
    protected int points;

    // the texture of the spritesheet loaded
    protected Image texture;

    // whether the current entity is still alive and active
    protected boolean isAlive;

    // for animations of the player
    protected int currentFrame;
    private final int TOTALFRAMES = 4;
    private long timer, startTimer;
    protected final long TIMERINTERVAL = 200000000;

    // the dimensions of each frame
    private int width, height;

    // the calories that the entity has
    protected int calories;

    // Creates and initializes a new instance of an entity
    public Entity(String path)
    {
        // Loads the image for the entity
        texture = Toolkit.getDefaultToolkit().getImage(path);

        // Initializes the position of the entity to be at 0,0
        position = new Position2D(0f, 0f);

        // initializes the frames of the animation, defauled at 4 frames
        // per animation
        currentFrame = 0;
        startTimer = System.nanoTime();
        timer = 0;

        calories = 16;

        // initializes the dimensions of the texture
        width = 16;
        height = 24;
    }

    public void setTexture(String path)
    {
        texture = Toolkit.getDefaultToolkit().getImage(path);
    }

    public void addCal(int cals)
    {
        // adds the specified amount of calories to the current calories value
        calories += cals;
    }

    public int getTurns()
    {
        return turns;
    }

    public int getWidth()
    {
        // returns the width of a single frame of the entity
        return width;
    }

    public int getHeight()
    {
        // returns the height of the entity
        return height;
    }

    public Position2D getPos()
    {
        // returns the position on the gameworld that the entity is drawn on
        return position;
    }

    public void setY(float y)
    {
        // Sets the y axis of the position
        position.setY(y);
    }

    public void setX(float x)
    {
        // Sets the x axis of the position
        position.setX(x);
    }

    public void addPoints(int points2)
    {
        points += points2;
    }

    public void update()
    {
        // updates the timer
        timer = System.nanoTime();

        // if the timer reaches a specific amount of time
        if (timer - startTimer > TIMERINTERVAL)
        {
            // if the frame number exceeds the total
            if (currentFrame >= TOTALFRAMES - 1)
            {
                // reset the animation
                currentFrame = 0;

                // resets the timer
                startTimer = System.nanoTime();
            }
            else // if not then just increment the current frame
            {
                // increments the frame of the texture
                currentFrame++;

                // resets the timer
                startTimer = System.nanoTime();
            }
        }

        // Checks for tile and food collision
        //checkCollision(map);
    }

    // called when this entity dies
    public void perish()
    {
        // sets entity as no longer active
        isAlive = false;

        // TODO: Perishing animation
    }

    public int getPoints()
    {
        // returns the amount of points
        return points;
    }

    public int getFrame()
    {
        // returns the current frame number of the spritesheet animation
        return currentFrame;
    }

    public void draw(Graphics g)
    {
        // calculates the place of which the image is drawn at
        int destx1 = (int)position.getX();
        int destx2 = (int)position.getX() + width * 4;
        int desty1 = (int)position.getY();
        int desty2 = (int)position.getY() + height * 4;

        //g.fillRect(destx1, desty1,
        //	width * 4, height * 4);

        // draws the entity to the screen
        g.drawImage(texture, destx1, desty1, destx2, desty2, width * currentFrame,
                0, width * (currentFrame + 1), height,
                null);


        //System.out.println(texture);
    }
}