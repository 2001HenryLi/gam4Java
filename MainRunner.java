package com.gam;

//Panel for Gam
//Henry Li
//4-18-16

//MainRunner creates the main menu panel, which can be changed by clicking
//the buttons on the main menu. Clicking the "play" button will play
//the game, clicking the "help" button will show the help menu.
//in the game panel, a pause button will be shown along with the map and player.
//clicking the pause button will bring up the pause menu, which can display helpful information
//the pause menu also features two buttons that return the player to the game and the main menu
//after each round, the player can choose a trait to upgrade.
//after a set amound of rounds on each level, the map will change and have a different environment, which may
//include enemies. The enemies may take food, acting as a competitor, or harm the player, acting as a predator.
//The upgrade menu holds the mutations that the player can select to change their stats.


//Testing
//Only the help, play, pause, resume, and upgrade buttons can be pressed, and they should send the player back to the specified
//panel. The buttons in the upgrade menu should change a specific stat of the player, or return to the game.
//The player class will be controlled by the wasd keys and collect food by walking over it
//the player can only move if the focus is onto the gamPanel
//in the upgrade menu, the player may choose only one trait to upgrade.

import javax.swing.*;
import javax.swing.event.*;
import java.awt.*;
import java.awt.event.*;
import javax.imageio.*;
import java.io.*;


public class MainRunner extends JFrame
{
    private String selectedStr;
    private boolean helpClicked, gamClicked, paused, onUpgrades, onCharS,onInt, onTransition;
    private boolean mut1,mut2,mut3,mut4;
    private boolean gameOver, onTutorial;
    private MainPanel mp;
    private CardLayout cl;
    private FeedbackPanel fp;
    private GamPanel gp;
    private MainMenu mm;
    private HelpPanel hp;
    private PauseMenu pm;
    private UpgradeMenu um;
    private CharacterSelect cs;
    private Tutorial ttl;
    private TransitionPanel tp;
    private int movespeed, environment;
    private double foodMult;

    public static void main(String[] args)
    {
        MainRunner mr = new MainRunner();
    }

    public MainRunner()
    {
        //constructor
        //create dimensions for the frame
        super("Gam");
        System.out.println("\n\n\n");
        setSize(1000,800);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setResizable(true);
        mp = new MainPanel();
        setContentPane(mp);
        setVisible(true);//add MainPanel

        environment = 0;
        onTransition = false;
        onCharS = false;
        onUpgrades = false;
        helpClicked = false;//initialize booleans
        gamClicked = false;
    }

    public class TransitionPanel extends JPanel implements ActionListener
    {
        private JButton next;
        private Image environ;
        private String filePath;

        public TransitionPanel()
        {
            setFont(new Font("Serif",Font.PLAIN,24));
            next = new JButton("Next");
        }

        public void paintComponent(Graphics g)
        {
            g.drawString("You gathered enough food so your player can survive!",450,200);
            g.drawString("Your population is roaming to find a new place to gather food.",450,220);
            g.drawString("You can now introduce a mutation into your population, ",450,240);
            g.drawString("Which will determine whether or not your population will survive",450,260);
            g.drawString("when their environment changes. ",450,280);

            setFont(new Font("Serif",Font.BOLD,36));

            g.drawString("Next Environment: ",600,500);
            if(environment == 1)
            {
                filePath = "Cold.jpg";
            }
            if(environment == 2)
            {
                filePath = "Hot.jpg";
            }

            try
            {
                environ = ImageIO.read(new File(filePath));
            }
            catch(IOException e)
            {
                e.printStackTrace();
            }
            g.drawImage(environ,620,520,this);
        }

        public void actionPerformed(ActionEvent evt)
        {
            onUpgrades = true;
            onTransition = false;
            mp.showCards();
        }

    }


    public class MainPanel extends JPanel
    {
        public MainPanel()
        {
            tp = new TransitionPanel();
            ttl = new Tutorial();
            um = new UpgradeMenu();
            cl = new CardLayout();
            cs = new CharacterSelect();
            mm = new MainMenu();
            gp = new GamPanel();
            hp = new HelpPanel();
            pm = new PauseMenu();
            fp = new FeedbackPanel();
            movespeed = 1;
            foodMult = 1.00;
            setLayout(cl);//set layout as a cardLayout
            add(mm,"Main Menu");//add 3 panels: MainMenu, GamPanel, and HelpPanel
            add(gp,"Gam");
            add(hp,"Help");
            add(pm,"Pause Menu");
            add(um,"Upgrade Menu");
            add(fp,"Game Over");
            add(cs,"Character Select");
            add(ttl,"Tutorial");
            add(tp,"Transition");
            cl.show(this,"Main Menu");
        }

        public void showCards()
        {
            if(onTransition)
                cl.show(mp,"Transition");
            else if(gameOver)
                cl.show(mp,"Game Over");
            else if(onTutorial)
                cl.show(mp,"Tutorial");
            else if(onCharS)
                cl.show(mp,"Character Select");
            else if(onUpgrades)
                cl.show(mp,"Upgrade Menu");
            else if(paused)//if helpClicked is true, change to helpPanel
                cl.show(mp,"Pause Menu");
            else if(gamClicked)
                cl.show(mp,"Gam");//if gamClicked is true, change to gamPanel
            else if(helpClicked)
                cl.show(mp,"Help");
            else//if none are true, revert to MainMenu
                cl.show(mp,"Main Menu");
        }
    }

    public class FeedbackPanel extends JPanel implements ActionListener
    {

        private String[] tip1, tip2, tip3;
        private JButton exit;
        private int pos;
        private TipReader tr;

        public FeedbackPanel()
        {
            tr = new TipReader();
            tr.readFile();
            //tip1 = new String[tr.get1()];
            //tip2 = new String[tr.get2()];
            //tip3 = new String[tr.get3()];
            tip1 = tr.getTip1();
            tip2 = tr.getTip2();
            tip3 = tr.getTip3();
            setLayout(new FlowLayout(1,300,600));
            exit = new JButton("Back to main");
            exit.addActionListener(this);
            pos = 180;
            setBackground(Color.WHITE);
            add(exit);
        }

        public void actionPerformed(ActionEvent evt)
        {
            gameOver = false;
            helpClicked = false;
            gamClicked = false;
            paused = false;
            onUpgrades = false;
            mut1 = true;
            mut2 = true;
            mut3 = true;
            mut4 = true;
            foodMult = 1.0;
            movespeed = 1;

            mp.showCards();
        }

        public void paintComponent(Graphics g)
        {
            super.paintComponent(g);
            g.setFont(new Font("Serif",Font.BOLD,24));
            g.drawString("You didn't collect enough food, and died.",100,50);
            g.drawString("Mutations Selected: ",500,150);
            g.drawString("Tips:",50,400);
            g.drawString("Stats:",50,150);
            g.setFont(new Font("Serif",Font.PLAIN,20));
            g.drawString("Food Multiplier: "+foodMult,50,180);
            g.drawString("Movement Speed: "+movespeed,50,200);
            drawMutations(g);
            drawTips(g);
        }


        public void drawTips(Graphics g)
        {
            int i = 450;
            int icr = 0;
            while(icr < tr.get1())
            {
                g.drawString(tip1[icr],50,i);
                i += 20;
                icr++;
            }
            icr = 0;
            while(icr < tr.get2())
            {
                g.drawString(tip2[icr],50,i);
                i += 20;
                icr++;
            }
            icr = 0;
            while(icr < tr.get3())
            {
                g.drawString(tip3[icr],50,i);
                i += 20;
                icr++;
            }
        }
        public void drawMutations(Graphics g)
        {
            if(!mut1)
            {
                g.drawString("Longer Legs", 500, pos);
                pos += 20;
            }
            if(!mut2)
            {
                g.drawString("Longer Arms",500,pos);
                pos += 20;
            }
            if(!mut3)
            {
                g.drawString("Darker Skin",500,pos);
                pos += 20;
            }
            if(!mut4)
            {
                g.drawString("Lighter Skin",500,pos);
            }
        }
    }

    public class CharacterSelect extends JPanel implements ActionListener, ChangeListener
    {
        private Timer timer;
        private DoneListener tl;
        private Image player;
        private String path;
        private JButton toGam;
        private JSlider skinColor;
        private int skinVal, frameCount;

        public CharacterSelect()
        {
            tl = new DoneListener();
            frameCount = 0;
            timer = new Timer(300,this);
            timer.setInitialDelay(0);
            skinVal = 1;
            skinColor = new JSlider(0,1,3,1);
            skinColor.setMajorTickSpacing(1);
            skinColor.setPaintTicks(true);
            skinColor.addChangeListener(this);
            toGam = new JButton("Done");
            toGam.addActionListener(tl);
            add(toGam);
            add(skinColor);
        }

        public void paintComponent(Graphics g)
        {
            timer.start();
            super.paintComponent(g);
            skinVal = skinColor.getValue();
            if(skinVal == 1)
                path = "Player1.png";
            if(skinVal == 2)
                path = "Player2.png";
            if(skinVal == 3)
                path = "Player3.png";

            try
            {
                player = ImageIO.read(new File(path));
            }
            catch(IOException e)
            {
                e.printStackTrace();
            }
            g.drawImage(player,16,0,96,120,(frameCount*16),0,(frameCount*16)+16,24,this);
            frameCount++;
            if(frameCount > 3)
                frameCount = 0;
        }

        public void stateChanged(ChangeEvent evt)
        {
            repaint();
        }

        public void actionPerformed(ActionEvent evt)
        {
            if(!onCharS)
                timer.stop();
            cs.repaint();
        }
    }


    public class DoneListener implements ActionListener
    {
        private boolean toGam;

        public boolean getGam()
        {
            return toGam;
        }

        public void actionPerformed(ActionEvent evt)
        {
            gamClicked = true;
            onCharS = false;
            toGam = false;
            cs.repaint();
            mp.showCards();
        }
    }

    public class PauseMenu extends JPanel implements ActionListener
    {
        private JButton resume, help, exit;
        private boolean helpPressed;

        public PauseMenu()
        {
            setBackground(Color.GRAY);
            resume = new JButton("Resume");
            help = new JButton("Help");
            exit = new JButton("Exit to Main Menu");
            help.addActionListener(this);
            exit.addActionListener(this);
            resume.addActionListener(this);
            add(resume);
            add(help);
            add(exit);
        }

        public void actionPerformed(ActionEvent evt)
        {
            JButton testButton;
            testButton = (JButton)(evt.getSource());
            if(testButton == resume)
            {
                paused = false;
                gamClicked = true;
                helpClicked = false;
                mp.showCards();
            }
            else if(testButton == help)
            {
                helpPressed = true;
                this.repaint();
            }
            else
            {
                paused = false;
                gamClicked = false;
                helpClicked = false;
                mp.showCards();
            }
        }
        public void paintComponent(Graphics g)
        {
            super.paintComponent(g);
            if(helpPressed)
            {
                super.paintComponent(g);
                setBackground(Color.GRAY);
                g.setFont(new Font("Serif",Font.BOLD,36));
                g.drawString("Controls",350,150);//writes the controls, goal of the game, and why it is
                g.setFont(new Font("Serif",Font.PLAIN,20));
                g.drawString("WASD to move, walk over food to collect it.",250,170);
                g.drawString("Collect food in the set amount of rounds to survive.",250,190);

                g.drawString("The environment will change after each level is completed,", 250, 230);
                g.drawString("and you can choose a mutation to change your character.",250,250);
                g.drawString("If you choose the correct mutation, the player can live.", 250, 280);
                g.drawString("Otherwise if you chose the wrong mutation, you die",250,300);
                g.drawString("because you didn't choose the mutation that is best for",250,320);
                g.drawString("your environment.",250,340);
            }
        }
    }

    public class MainMenu extends JPanel implements ActionListener
    {
        private JButton playButton, helpButton, tutorialButton;
        public MainMenu()
        {
            tutorialButton = new JButton("Play Tutorial");
            playButton = new JButton("Play");
            helpButton = new JButton("Help");
            PlayHandler ph = new PlayHandler();
            HelpHandler hh = new HelpHandler();
            tutorialButton.addActionListener(this);
            playButton.addActionListener(ph);
            helpButton.addActionListener(hh);
            setLayout(new FlowLayout(2,500,150));//set layout as a flow layout
            add(playButton);//add 3 buttons : play, tutorial and help
            add(tutorialButton);
            add(helpButton);
        }

        public void actionPerformed(ActionEvent evt)
        {
            onTutorial = true;
            mp.showCards();
        }
        public void paintComponent(Graphics g)
        {
            super.paintComponent(g);
            setFont(new Font("Serif",Font.BOLD,36));
            g.drawString("Survival of the Fittest",300,100);
            setFont(new Font("Serif",Font.PLAIN,24));
            g.drawString("A Gam made by Henry Li and Spencer Chang",200,600);
        }
    }
    public class HelpHandler implements ActionListener
    {
        public void actionPerformed(ActionEvent evt)
        {
            helpClicked = true;
            gamClicked = false;
            if(helpClicked)//if helpClicked is true, change to helpPanel
                cl.show(mp,"Help");
            else if(gamClicked)
                cl.show(mp,"Gam");//if gamClicked is true, change to gamPanel
            else if(paused)
                cl.show(pm,"Pause Menu");
            else//if none are true, revert to MainMenu
                cl.show(mp,"Main Menu");
        }
    }
    public class PlayHandler implements ActionListener
    {
        public void actionPerformed(ActionEvent evt)
        {
            onCharS = true;
            helpClicked = false;
            mp.showCards();
            gp.setFocusable(true);
            gp.requestFocusInWindow();
        }
    }

    public class InteractPanel extends JPanel
    {
        private Font intFont;

        public InteractPanel()
        {
            intFont = new Font("Serif",Font.PLAIN,24);
            setFont(intFont);
            setBackground(Color.BLACK);
            onInt = false;
        }

        public void paintComponent(Graphics g)
        {
            super.paintComponent(g);
            if(onInt)
            {
                if(selectedStr.equals("Longer Legs"))//if longer legs was selected
                {
                    if(mut1)
                    {
                        setBackground(Color.WHITE);
                        g.drawString("The leg bones have increased height and become stronger, increasing",80,150);
                        g.drawString("movement speed but also increasing heat loss due to a bigger body.",80,170);
                    }
                    else
                    {
                        super.paintComponent(g);
                        setBackground(Color.RED);
                        g.drawString("This mutation has already been selected!",100,150);
                        g.drawString("Please choose a different mutation by clicking the button,",100,170);
                        g.drawString("then clicking on the select mutation button to choose that mutation.",100,190);
                    }
                }
                if(selectedStr.equals("Longer Arms"))//if longer arms was selected
                {
                    if(mut2)
                    {
                        //super.paintComponent(g);
                        setBackground(Color.WHITE);
                        g.drawString("The bones in the arm are longer and stronger.",100,150);
                        g.drawString("Gathering food multiplier is increased from stronger arms,",100,170);
                        g.drawString("But heat loss is increased due to a bigger body.",100,190);
                    }
                    else
                    {
                        super.paintComponent(g);
                        setBackground(Color.RED);
                        g.drawString("This mutation has already been selected!",100,150);
                        g.drawString("Please choose a different mutation by clicking the button,",100,170);
                        g.drawString("then clicking on the select mutation button to choose that mutation.",100,190);
                    }

                }
                if(selectedStr.equals("Darker Skin"))
                {
                    if(mut3)
                    {
                        //super.paintComponent(g);
                        setBackground(Color.WHITE);
                        g.drawString("The leg bones have increased height and become stronger, increasing",80,150);
                        g.drawString("movement speed but also increasing heat loss due to a bigger body.",80,170);
                    }
                    else
                    {
                        super.paintComponent(g);
                        setBackground(Color.RED);
                        g.drawString("This mutation has already been selected!",100,150);
                        g.drawString("Please choose a different mutation by clicking the button,",100,170);
                        g.drawString("then clicking on the select mutation button to choose that mutation.",100,190);
                    }
                }
                if(selectedStr.equals("Lighter Skin"))
                {
                    if(mut4)
                    {
                        setBackground(Color.WHITE);
                        g.drawString("The leg bones have increased height and become stronger, increasing",80,150);
                        g.drawString("movement speed but also increasing heat loss due to a bigger body.",80,170);
                    }
                    else
                    {
                        super.paintComponent(g);
                        setBackground(Color.RED);
                        g.drawString("This mutation has already been selected!",100,150);
                        g.drawString("Please choose a different mutation by clicking the button,",100,170);
                        g.drawString("then clicking on the select mutation button to choose that mutation.",100,190);
                    }
                }
            }
            else
            {
                setBackground(Color.BLACK);
                g.drawString("To find out more about a mutation,",100,150);
                g.drawString("Click on the buttons with mutations on them.",100,170);
            }
        }

        public void printInfo(Graphics g)
        {
            onInt = true;
            repaint();
        }
    }

    public class GamPanel extends JPanel implements ActionListener, KeyListener
    {
        private JButton pauseButton;
        private boolean keysPressed;
        //private JButton toUpgrades; //used to test out upgrades, will be removed in release
        private Player p1;
        private Map map;
        private MapLoader ml;

        public GamPanel()
        {
            movespeed = 1;
            System.out.println(movespeed);
            //gameOverB = new JButton("[S] Game Over");
            gameOver = false;
            ml = new MapLoader();
            keysPressed = false;
            //toUpgrades = new JButton("Go to upgrade menu");
            //toUpgrades.addActionListener(this);
            addKeyListener(this);
            //gameOverB.addActionListener(this);
            pauseButton = new JButton("Pause");
            pauseButton.addActionListener(this);

            Color cornflowerBlue = new Color(43,134,255);
            setBackground(cornflowerBlue);

            if(environment == 0)
            {
                map = new Map("map0.map");
            }
            else if(environment == 1)
            {
                map = new Map("map1.map");
            }
            else
            {
                map = new Map("map2.map");
            }
            ml.read();


            int[][] tileVal = ml.returnTiles();
            map.generateTiles(tileVal);

			/*
            tileVal[0][0] = 1;
            tileVal[0][1] = 1;
            tileVal[1][0] = 1;
            tileVal[1][1] = 1;
            */

            paused = false;
            setLayout(new FlowLayout(10, 0, 0));
            add(pauseButton);
            //add(toUpgrades);
            //add(gameOverB);
            p1 = new Player("Player3 - ll + la.png");
            p1.setY(400 - p1.getHeight());
            p1.setX(400 - p1.getWidth());
            //use MapLoader to load up a map
        }

        public void keyPressed(KeyEvent evt)
        {
            char key = evt.getKeyChar();

            Position2D playerPos = p1.getPos();
            p1.handleInput(key);
            p1.update();
            System.out.println("(p1) X: " + playerPos.getX() + ", Y: " +
                    playerPos.getY() + ", CurrentFrame: " + p1.getFrame());
            keysPressed = true;
            if(p1.getTurns() == 0)
            {
                gameOver = true;
                mp.showCards();
            }
            if(p1.getPoints() > 100)
            {
                onTransition = true;
                mp.showCards();
            }
            repaint();
        }
        public void keyReleased(KeyEvent evt)
        {
            keysPressed = false;
        }
        public void keyTyped(KeyEvent evt)
        {
        }

        public void paintComponent(Graphics g)
        {
            if(environment == 0)
            {
                map = new Map("map0.map");
            }
            else if(environment == 1)
            {
                map = new Map("map1.map");
            }
            else
            {
                map = new Map("map2.map");
            }
            super.paintComponent(g);

            if (!keysPressed)
                p1.resetFrame();

            g.setFont(new Font("Serif",Font.BOLD,24));

            int[][] tileVal = ml.returnTiles();
            gp.setFocusable(true);
            gp.requestFocusInWindow();

            map.draw(g);
            p1.draw(g);
            g.drawString("Points: " + p1.getPoints(), 900, 41);
            g.drawString("Turns: " + p1.getTurns(), 900, 82);

            //System.out.println("f");
        }

        public void actionPerformed(ActionEvent evt)
        {
            //System.out.println("1");
            JButton test = (JButton)evt.getSource();
            if(test == pauseButton)
            {
                paused = true;//if game is paused, change paused to true
                helpClicked = false;
            }

            mp.showCards(); //show cards to return the card to the pause menu
        }
    }

    public class Tutorial extends JPanel implements ActionListener
    {
        private Image playerImg;
        private Image foodImg;
        private boolean next,pan1,pan2,pan3;
        private JButton nextButton;
        public Tutorial()
        {
            try
            {
                playerImg = ImageIO.read(new File("Player1.png"));
                foodImg = ImageIO.read(new File("C:/Users/henryli/Downloads/Chrome Downloads/y1.jpg"));
                //foodImg = ImageIO.read(new File(""));
            }
            catch(IOException e)
            {
                e.printStackTrace();
            }
            nextButton = new JButton("Next");
            setLayout(new FlowLayout(1,400,600));
            add(nextButton);
            pan1 = true;
            pan2 = false;
            pan3 = false;
            nextButton.addActionListener(this);
            setFont(new Font("Serif",Font.PLAIN,24));
        }
        public void paintComponent(Graphics g)
        {
            super.paintComponent(g);
            System.out.println(pan1+" "+pan2+" "+pan3);
            if(pan1)
            {
                drawPan1(g);
            }
            if(next)
            {
                if(pan2)
                {
                    drawPan2(g);
                    next = false;
                    pan1 = false;
                    pan3 = false;
                }
                if(pan3)
                {
                    drawPan3(g);
                    next = false;
                    gamClicked = true;
                    mp.showCards();
                }
            }
        }

        public void drawPan1(Graphics g)
        {
            g.drawString("Welcome to the Game Tutorial!",350,300);
            g.drawString("In the tutorial, you'll learn how to play",350,320);
            g.drawString("the game, and learn some of the basics of the game.",350,340);
        }
        public void drawPan2(Graphics g)
        {
            g.drawImage(playerImg, 400, 100, 480, 220, 0, 0, 16, 24, this);
            g.drawString("This is the player. You can customize the player in the character select menu.",10,330);
            g.drawString("To move the player, use the wasd keys.",10,350);
            g.drawString("As the game goes on, you will be able to choose mutations to change your character's appearance.",10,370);
            g.drawString("These mutations can help your character survive better in their environment, but the",10,390);
            g.drawString("wrong mutation can be detrimental to your character.",10,410);
        }
        public void drawPan3(Graphics g)
        {
            //g.drawImage(foodImg,0,0,this);
            g.drawString("This is a food tile.",10,300);
            g.drawString("Your character can collect food tiles by walking over them.",10,320);
            g.drawString("Food tiles are randomly scattered across the map in the game, and the player has to collect",10,340);
            g.drawString("enough food in order to survive and reproduce.",10,360);

            g.drawString("Now that you know the basics, click on the button below to create a character and start the game!",10,400);
        }
        public void actionPerformed(ActionEvent evt)
        {
            next = true;
            if(pan1)
            {
                pan1 = false;
                pan2 = true;
                pan3 = false;
                repaint();
            }
            else if(pan2)
            {
                pan2 = false;
                pan3 = true;
                repaint();
            }
            else if(pan3)
            {
                pan3 = false;
                pan1 = true;
                onTutorial = false;
                onCharS = true;
                mp.showCards();
            }
        }
    }

    public class UpgradeMenu extends JPanel implements ActionListener //this is the panel for the upgrade menu
    {																	//the upgrade menu will show after 5 rounds
        //have been completed.
        private UpgradeHolder uh;
        private int upgradePoints;
        private InteractPanel ip;
        private JButton speedUp,foodUp,m1,m2,m3,back;	//buttons that upgrade a trait the player chooses
        private boolean selectInfo;
        public UpgradeMenu()
        {
            mut1 = true;
            mut2 = true;
            mut3 = true;
            mut4 = true;
            selectInfo = false;
            ip = new InteractPanel();
            ip.setBackground(Color.WHITE);
            upgradePoints = 1;
            setLayout(new GridLayout(3,3));
            initializeButtons();
        }

        public void initializeButtons()
        {
            uh = new UpgradeHolder();
            speedUp = new JButton("Longer Legs");
            foodUp = new JButton("Longer Arms");
            back = new JButton("Next");
            m1 = new JButton("Darker Skin");
            m2 = new JButton("Lighter Skin");
            m3 = new JButton("Select Mutation");

            speedUp.addActionListener(this);
            foodUp.addActionListener(this);
            back.addActionListener(this);
            m1.addActionListener(this);
            m2.addActionListener(this);
            m3.addActionListener(this);

            uh.add(speedUp);
            uh.add(foodUp);
            uh.add(m1);
            uh.add(m2);
            uh.add(m3);
            uh.add(back);
            ip.setBackground(Color.BLACK);
            add(ip);
            add(uh);
        }

        public void paintComponent(Graphics g)
        {
            super.paintComponent(g);
            g.drawString("Click on a button to look at the trait,",380,650);
            g.drawString("Then click on the select mutation button to choose that trait",380,670);
            g.drawString("Upgrade Points: "+upgradePoints,380,570);
            g.drawString("Speed: "+movespeed,380,590);
            g.drawString("Digestion: "+foodMult,380,610);
            if(selectInfo == true)
                ip.printInfo(g);
        }

        public void actionPerformed(ActionEvent evt)
        {
            String text = ((JButton)evt.getSource()).getText();
            if(text.equals("Next"))
            {
                gamClicked = true;
                paused = false;
                onUpgrades = false;
                upgradePoints = 1;
                onInt = false;
                mp.showCards();
                ip.setBackground(Color.BLACK);
            }
            if(!(text.equals("Select Mutation"))&& !text.equals("next"))
            {
                selectInfo = true;
                selectedStr = ((JButton)evt.getSource()).getText();
                repaint();
            }
            else
            {
                if(selectedStr.equals("Longer Legs"))//if longer legs was selected
                {
                    if(upgradePoints != 0)
                    {
                        if(mut1)
                        {
                            movespeed += 1;//add 1 to the amount of moves the player can go in 1 turn
                            foodMult += 0.25;
                            upgradePoints--;
                            mut1 = false;
                            repaint();
                        }
                    }
                }
                if(selectedStr.equals("Longer Arms"))//if longer arms was selected
                {
                    if(upgradePoints != 0)
                    {
                        if(mut2)
                        {
                            foodMult += 0.5;
                            upgradePoints--;
                            mut2 = false;
                            repaint();
                        }
                    }
                }
                if(selectedStr.equals("Darker Skin"))
                {
                    if(upgradePoints != 0)
                    {
                        if(mut3)
                        {
                            mut3 = false;
                        }
                    }
                    repaint();
                }

                if(selectedStr.equals("Lighter Skin"))
                {
                    if(upgradePoints != 0)
                    {
                        if(mut4)
                        {
                            mut4 = false;
                        }
                    }
                    repaint();
                }
            }
            ip.repaint();
        }
    }


    public class HelpPanel extends JPanel implements ActionListener
    {
        private JButton toMain;
        private boolean pressed;
        private Font header;
        private Font body;
        public HelpPanel()
        {
            header = new Font("Serif",Font.BOLD,36);
            body = new Font("Serif",Font.PLAIN,20);
            toMain = new JButton("Back to Main Menu");
            pressed = false;
            setLayout(new FlowLayout(10, 350, 600));
            toMain.addActionListener(this);
            add(toMain);
        }
        public void paintComponent(Graphics g)
        {
            super.paintComponent(g);
            setBackground(Color.GRAY);
            g.setFont(header);
            g.drawString("Controls",350,150);//writes the controls, goal of the game, and why it is
            g.setFont(body);
            g.drawString("WASD to move, walk over food to collect it.",250,170);
            g.drawString("Collect food in the set amount of rounds to survive.",250,190);

            g.drawString("The environment will change after each level is completed,",250,230);
            g.drawString("and you can choose a mutation to change your character.",250,250);
            g.drawString("If you choose the correct mutation, the player can live.",250,280);
            g.drawString("Otherwise if you chose the wrong mutation, you die",250,300);
            g.drawString("because you didn't choose the mutation that is best for",250,320);
            g.drawString("your environment.",250,340);
            //educational onto the panel.
            //adds a button to the end with "done"
        }
        public void actionPerformed(ActionEvent evt)
        {

            gamClicked = false;
            helpClicked = false;
            //if the button is pressed
            mp.showCards();//repaints to exit to main menu
        }
        public boolean getPressed()
        {
            return pressed;
        }
    }
} //Henry Li