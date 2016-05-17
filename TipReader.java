package com.gam;

import java.io.*;
import java.awt.*;
import java.util.*;

public class TipReader
{
    private File tipFile;
    private Scanner tipS;
    private String[] tip1;
    private String[] tip2;
    private String[] tip3;

    public TipReader()
    {
        tipFile = new File("Tips.txt");
        try
        {
            tipS = new Scanner(tipFile);
        }
        catch(IOException e)
        {
            e.printStackTrace();
        }
    }

    public void readFile()
    {
        int i = -1;
        int j = 0;
        i = tipS.nextInt();
        tipS.nextLine();
        tip1 = new String[i];
        while(j < i)
        {
            tip1[j] = tipS.nextLine();
            j++;
        }
        j = 0;
        i = tipS.nextInt();
        tipS.nextLine();
        tip2 = new String[i];
        while(j < i)
        {
            tip2[j] = tipS.nextLine();
            j++;
        }
        j = 0;
        i = tipS.nextInt();
        tipS.nextLine();
        tip3 = new String[i];
        while(j < i)
        {
            tip3[j] = tipS.nextLine();
            j++;
        }
    }
    public String[] getTip1()
    {
        return tip1;
    }

    public String[] getTip2()
    {
        return tip2;
    }

    public String[] getTip3()
    {
        return tip3;
    }

    public int get1()
    {
        return tip1.length;
    }

    public int get2()
    {
        return tip2.length;
    }

    public int get3()
    {
        return tip3.length;
    }
}