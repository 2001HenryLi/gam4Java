package com.gam;
/*
Spencer Chang
4/19/16
Position2D.java
Description: Holds 2 coordinate values, X and Y
*/

public class Position2D
{
    // The coordinate value of the position (x and y axis)
    private float x, y;

    // Creates and initializes a new instance of a Position2D
    public Position2D(float newX, float newY)
    {
        // Sets the x and y coordinates of the position
        x = newX;
        y = newY;
    }

    public float getX()
    {
        // Returns the x coordinate
        return x;
    }

    public float getY()
    {
        // Returns the y coordinate
        return y;
    }

    public void setX(float newX)
    {
        // Sets a new x specific value
        x = newX;
    }

    public void setY(float newY)
    {
        // Sets a new y specific value
        y = newY;
    }

    /*
    http://stackoverflow.com/questions/14431032/i-want-to-calculate-the-distance-between-two-points-in-java
    */
    public float getDistance(Position2D position2)
    {
        // returns the distance between this position and another <position2>
        return (float)Math.sqrt(
                (x - position2.getX()) * (x - position2.getX()) + (y + position2.getY()) * (y + position2.getY()));
    }
}