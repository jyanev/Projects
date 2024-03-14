package team12.view.StartMenu;
import javax.swing.*;
import team12.view.tools.*;
import java.net.URL;
import java.awt.event.*;
import team12.view.Arena.Arena;
import java.util.ArrayList;
import team12.control.Navigators.*;
import team12.control.StartGame;
import team12.model.GameCharacters.*;
import team12.model.Projectiles.*;
import team12.view.ProjectileSprites.*;

public class StartMenu implements GameWindow
{
    public JFrame mainFrame;
    private BackgroundPanel panel;
    private JLabel text;
    private JButton characterButton1;
    private JButton characterButton2;
    private boolean twoPlayer;
    public JButton submitButton;
    public JButton statButton;
    public JButton twoPlayerButton;
    private ArrayList<NavigateToScreen> navigators = new ArrayList<NavigateToScreen>();
    private StartGame startGame;

    public StartMenu(Arena arena) 
    {

        this.startGame = new StartGame(arena);
        // frame
        this.mainFrame = new JFrame("Start Menu");
        this.mainFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // panel
	    URL backgroundURL = this.getClass().getClassLoader().getResource("BackgroundImage.png");
        this.panel = new BackgroundPanel(new ImageIcon(backgroundURL).getImage());

        this.text = new JLabel();
	    URL battleBrosURL = this.getClass().getClassLoader().getResource("BattleBrosText.png");
        this.text.setIcon(new ImageIcon(battleBrosURL));
        this.text.setBounds(240, -40, 1000, 200);
        this.panel.add(this.text);

        // character buttons
	    URL marioURL = this.getClass().getClassLoader().getResource("mario.png");
        this.characterButton1 = new JButton("", new ImageIcon(marioURL));
        this.characterButton1.setBounds(248, 140, 200, 200);
        this.panel.add(this.characterButton1);

	    URL luigiURL = this.getClass().getClassLoader().getResource("luigi.png");
        this.characterButton2 = new JButton("", new ImageIcon(luigiURL));
        this.characterButton2.setBounds(452, 140, 200, 200);
        this.panel.add(this.characterButton2);

        // battle button
        this.submitButton = new JButton("BATTLE!");
        this.submitButton.setBounds(223, 390, 150, 65);
        this.submitButton.setEnabled(false);

        this.panel.add(this.submitButton);
        
        // 2 player button
        this.twoPlayerButton = new JButton("2 Player Mode");
        this.twoPlayerButton.setBounds(375, 390, 150, 65);
        this.twoPlayerButton.setEnabled(false);

        this.panel.add(this.twoPlayerButton);

        // stats button
        this.statButton = new JButton("Stats");
        this.statButton.setBounds(527, 390, 150, 65);

        this.panel.add(this.statButton);

        //Character selection button 1
        //initializes both game characters using the StartGame class
        characterButton1.addActionListener (
            new ActionListener() {
                public void actionPerformed(ActionEvent e){
                    characterButton1.setEnabled(false);
                    characterButton2.setEnabled(true);
                    Fighter player = new Fighter(0, 0, 0, 0, 0, 0, 
                                                 new Fireball(new ProjectileSprite(0), 0, 0, 0), 
                                                 80, 180);
                    Fighter cpu = new Fighter(1, 0, 0, 0, 0, 0, 
                                              new Grenade(new ProjectileSprite(1) ,1, 0, 0), 
                                              720, 180);
                    startGame.setPlayers(player, cpu);
                    submitButton.setEnabled(true);
                    twoPlayerButton.setEnabled(true);
                }
            }
        );

        //Character selection button 2
        characterButton2.addActionListener (
            new ActionListener() {
               public void actionPerformed(ActionEvent e){
                    characterButton2.setEnabled(false);
                    characterButton1.setEnabled(true);
                    Fighter player = new Fighter(1, 0, 0, 0, 0, 0, 
                                                 new Grenade(new ProjectileSprite(1), 1, 0, 0), 
                                                 80, 180);
                    Fighter cpu = new Fighter(0, 0, 0, 0, 0, 0, 
                                              new Fireball(new ProjectileSprite(0), 0, 0, 0), 
                                              720, 180);
                    startGame.setPlayers(player, cpu);
                    submitButton.setEnabled(true);
                    twoPlayerButton.setEnabled(true);
               }
            }
        ); 

        submitButton.addActionListener (
            new ActionListener() {
                public void actionPerformed(ActionEvent e)
                {
                    twoPlayer = false;
                    startGame.beginFight(twoPlayer);
                    navigators.get(1).goToScreen();
                    characterButton1.setEnabled(true);
                    characterButton2.setEnabled(true);
                    submitButton.setEnabled(false);
                    twoPlayerButton.setEnabled(false);
                }
            }
        );

        twoPlayerButton.addActionListener (
            new ActionListener() {
                public void actionPerformed(ActionEvent e)
                {
                    twoPlayer = true;
                    startGame.beginFight(twoPlayer);
                    navigators.get(1).goToScreen();
                    characterButton1.setEnabled(true);
                    characterButton2.setEnabled(true);
                    submitButton.setEnabled(false);
                    twoPlayerButton.setEnabled(false);
                }
            }
        );

        statButton.addActionListener (
            new ActionListener() {
                public void actionPerformed(ActionEvent e)
                {
                    navigators.get(0).goToScreen();
                }
            }
        );

        this.mainFrame.add(this.panel);
        this.mainFrame.pack();
        this.mainFrame.setVisible(true); 
    }
   
    @Override
    public JFrame getFrame() { 
        return mainFrame;
    }   

    public void addSubmitListener(ActionListener l) {
        this.submitButton.addActionListener(l);
    }

    public void addStatListener(ActionListener l) {
        this.statButton.addActionListener(l);
    }

    @Override
    public void addScreenNavigator(NavigateToScreen screenNavigator) {
       this.navigators.add(screenNavigator);
    }

    public boolean getTwoPlayer() {
        return twoPlayer;
    }
}
