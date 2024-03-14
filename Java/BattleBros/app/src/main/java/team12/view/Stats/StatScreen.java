package team12.view.Stats;

import javax.swing.*;
import team12.view.tools.*;
import java.net.URL;
import java.awt.Font;
import java.awt.event.*;
import java.io.IOException;

import team12.control.Navigators.*;

public class StatScreen implements ActionListener, GameWindow
{
    public JFrame mainFrame;
    private BackgroundPanel panel;
    private LeaderBoard leaderboard;
    private JButton backButton;
    private JLabel boardTitle;
    private NavigateToScreen screenNavigator;
    private JLabel victoryStat;
    private JLabel timeStat;

    public StatScreen() 
    {
        // frame
        this.mainFrame = new JFrame("Stats");
        this.mainFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // panel
        URL backgroundURL = this.getClass().getClassLoader().getResource("BackgroundImage.png");
        this.panel = new BackgroundPanel(new ImageIcon(backgroundURL).getImage());

        Stats statsUpdater = new Stats("stats.csv");
        
        try {
            statsUpdater.readStats();
        } catch (IOException e) {
            e.printStackTrace();
        }
        
        // leaderboard label
        this.boardTitle = new JLabel();
        this.boardTitle.setText("Stats");
        this.boardTitle.setBounds(405, 20, 400, 90);
        this.boardTitle.setFont(new Font("Serif", Font.BOLD, 48));
        this.panel.add(this.boardTitle);

        // time stat
        this.timeStat = new JLabel();
        this.timeStat.setBounds(360, 110, 400, 90);
        this.timeStat.setFont(new Font("Serif", Font.BOLD, 14));
        this.panel.add(this.timeStat);

        // victory stat
        this.victoryStat = new JLabel();
        this.victoryStat.setText("Victory/Defeat : " + Integer.toString(statsUpdater.getWins()) + "/" + Integer.toString(statsUpdater.getLosses()));
        this.victoryStat.setBounds(360, 70, 400, 90);
        this.victoryStat.setFont(new Font("Serif", Font.BOLD, 14));
        this.panel.add(this.victoryStat);

        // leaderboard
        this.leaderboard = new LeaderBoard();
        this.leaderboard.setBounds(292, 10, 314, 414);
        this.panel.add(this.leaderboard);

        // backButton
        this.backButton = new JButton("Back");
        this.backButton.setBounds(375, 430, 150, 65);
        this.backButton.addActionListener(this);

        this.panel.add(this.backButton);

        this.mainFrame.add(this.panel);
        this.mainFrame.pack(); 
    }

    @Override
    public JFrame getFrame() { 
        return mainFrame;
    }   

    @Override
    public void addScreenNavigator(NavigateToScreen screenNavigator) {
        this.screenNavigator = screenNavigator;
    }

    public void actionPerformed(ActionEvent e) {
        this.screenNavigator.goToScreen();
    }
}

