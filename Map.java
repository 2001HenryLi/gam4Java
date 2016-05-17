package com.gam;

/*
Spencer Chang
4/18/16
Map.java
Description: Loads a .map file and stores it's values into an array,
which is then used to generate a map.
*/

// import statement(s)
import java.io.*;
import java.awt.*;
import java.util.*;

public class Map
{
    // An array of tiles to be drawn for the map
    private Tile[][] tiles;

    private MapLoader ml;

    // An array of food blocks to be drawn and updated to the map
    private Food[][] foods;

    // The files of the map
    private File mapFile, foodFile;
    private Scanner mapIo, foodIo;

    // dimensions of the tiles
    private final int TILE_WIDTH = 64;
    private final int TILE_HEIGHT = 64;

    /*
     * Creates and initializes a new instance of a map
    */


    public Map(String asset)
    {
        // Initializes the field variables
        ml = new MapLoader();
        mapFile = new File("Map/" + asset + "/tiles.map");
        foodFile = new File("Map/" + asset + "/foods.map");
    }

    // loops through a given tile 2d array and generates tiles inside of
    // another array of tiles
    public void generateTiles(int[][] tileValue)
    {
        ml.read();
        int width = ml.returnCols();//tileValue[1].length;
        int height = ml.returnRows();//tileValue.length;
        tiles = new Tile[width][height];
        // Loops through the y axis of the tile array
        for (int y = 0; y <= height-1; y++)
        {
            // Loops through the x axis of the tile array
            for (int x = 0; x <= width-1; x++)
            {
                // Adds the values to the array (needs to be initialized first in
                // the read() method first!)
                tiles[x][y] = new Tile(tileValue[x][y], x * TILE_WIDTH, y * TILE_HEIGHT);
            }
        }
    }

    // loops through a given food 2d array and generates foods inside of
    // another array of foods
    public void generateFood(int[][] foodValue)
    {
        // Loops through the y axis of the foods
        for(int y = 0; y < foodValue[1].length; y++)
        {
            // Loops through the x axis of the foods
            for (int x = 0; x < foodValue[0].length; x++)
            {
                // Adds the values to the array (needs to be initialized first in
                // the read() method first!)
                foods[y][x] = new Food(foodValue[x][y], x * TILE_WIDTH, y * TILE_HEIGHT);
            }
        }
    }

    // Loops through a file to find the dimensions of it
    public Tile[][] getTiles()
    {
        // returns the list of tiles
        return tiles;
    }

    public Food[][] getFoods()
    {
        // returns the list of foods
        // (The array of foods are then checked for collision within the
        // entity itself)
        return foods;
    }

    // Draws the foods and tiles
    public void draw(Graphics g)
    {
        // checks if the tile array is null or not
        if (tiles != null)
        {
            // Loops through the list of tiles inside of the map
            // Draws the tiles
            for (int i = 0; i < tiles.length; i++)
            {
                for (int j = 0; j < tiles[i].length; j++)
                {
                    tiles[i][j].drawImage(g);
                }
            }
        }

        // checks if the food array is null or not
        if (foods != null)
        {
            // Loops through the list of foods inside of the map
            // Draws the tiles
            for (int i = 0; i < foods.length; i++)
            {
                for (int j = 0; j < foods[i].length; j++)
                {
                    foods[i][j].drawImage(g);
                }
            }
        }
    }
}

class MapLoader//Henry's code
{
    private int[][] tiles;
    private String path;
    private File mapFile;
    private Scanner readIn;
    private int maxRow, maxCol;

    public MapLoader()
    {
        path = "dank.map";
        mapFile = new File(path);
        initialize();
    }

    public MapLoader(String pathing)
    {
        path = pathing;
        mapFile = new File(path);
        initialize();
    }

    public void initialize()
    {
        try
        {
            readIn = new Scanner(mapFile);
        }
        catch(IOException e)
        {
            e.printStackTrace();
        }
    }

    public int returnCols()
    {
        return maxCol;
    }

    public int returnRows()
    {
        return maxRow;
    }

    public void read()
    {
        maxRow = readIn.nextInt();
        maxCol = readIn.nextInt();
        tiles = new int[maxCol][maxRow];
        for(int row = 0; row < maxCol; row++)
        {
            for(int col = 0; col < maxRow; col++)
            {
                tiles[row][col] = readIn.nextInt();
            }
        }
    }
    public int[][] returnTiles()
    {
        return tiles;
    }
}



class MapElement
{
    // id of the element
    protected int id;

    // texture of the element
    protected Image texture;

    // position of the element
    protected Position2D position;

    //whether the element is active or not
    protected boolean isActive;

    // dimensions of the tile
    protected final int WIDTH = 64;
    protected final int HEIGHT = 64;

    public void drawImage(Graphics g)
    {
        // Draws the element if the tile is active
        if (isActive)
            g.drawImage(texture, (int)position.getX(), (int)position.getY(), null);

    }
}

class Tile extends MapElement
{

    public Tile(int count, int x, int y)
    {
        // sets the id for the tile
        id = count;

        // Loads the image file for the tile based on it's id
        texture = Toolkit.getDefaultToolkit().getImage("Tiles/Tile" + id);

        // initializes and sets the position of the tile based on the
        // x and y position given
        position = new Position2D(x, y);
    }
}

class Food extends MapElement
{

    public Food(int count, float x, float y)
    {
        // sets the id for the tile
        id = count;

        // Loads the image file for the tile based on it's id
        texture = Toolkit.getDefaultToolkit().getImage("Tiles/Food" + id);

        // initializes and sets the position of the tile based on the
        // x and y position given
        position = new Position2D(x, y);
    }

    public boolean intersects(Entity entity)
    {
        Position2D pos = entity.getPos();
        // checks if the entity collides with this food
        if (isActive)
            if (position.getX() + 64 >= pos.getX() && position.getX() >=
                    pos.getX() + entity.getWidth() && position.getY() <=
                    pos.getY() + entity.getHeight() && position.getY() + HEIGHT >=
                    pos.getY())
            {
                // stops updating and drawing after it does
                isActive = false;
                return true;
            }

        // returns false if nothing is colliding
        return false;
    }
}