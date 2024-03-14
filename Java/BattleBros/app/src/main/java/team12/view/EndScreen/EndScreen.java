package team12.view.EndScreen;
import javax.swing.*;
import team12.view.tools.*;
import java.net.URL;
import java.awt.event.*;
import team12.control.Navigators.*;

public class EndScreen implements ActionListener, GameWindow{
    private JFrame mainFrame;
    private BackgroundPanel panel;
    private JLabel text;
    private JButton submitButton;
    private NavigateToScreen screenNavigator;

    //true means victory
    //false means defeat
    public EndScreen(boolean type){
    this.mainFrame = new JFrame("Result");
    this.mainFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

    // panel
    URL backgroundURL = this.getClass().getClassLoader().getResource("BackgroundImage.png");
        this.panel = new BackgroundPanel(new ImageIcon(backgroundURL).getImage());

    this.text = new JLabel();
    URL endScreenURL;
    if (type) {
        endScreenURL = this.getClass().getClassLoader().getResource("Victory.png");
    }
    else {
        endScreenURL = this.getClass().getClassLoader().getResource("Defeat.png");
    }
    this.text.setIcon(new ImageIcon(endScreenURL));
    this.text.setBounds(240, -40, 1000, 350);
    this.panel.add(this.text);

    // battle button
    this.submitButton = new JButton("Play again?");
    this.submitButton.setBounds(375, 390, 150, 65);
    this.submitButton.addActionListener(this);

    this.panel.add(this.submitButton);

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
