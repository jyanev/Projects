package team12.view.Arena;
import javax.swing.*;
import team12.view.tools.*;
import java.net.URL;
import team12.control.Navigators.*;
import java.util.ArrayList;
import java.awt.event.*;

public class Arena implements GameWindow
{
    private JFrame mainFrame;
    private BackgroundPanel panel;
    private ArrayList<NavigateToScreen> navigators = new ArrayList<NavigateToScreen>();
    private Timer timer;
    private int imgWidth;

    public Arena(){
        this.mainFrame = new JFrame("Battle Arena");
        this.mainFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	
        URL backgroundURL = this.getClass().getClassLoader().getResource("BackgroundImage.png");
        ImageIcon img = new ImageIcon(backgroundURL);
        this.imgWidth = img.getIconWidth();

        this.panel = new BackgroundPanel(img.getImage());
        this.mainFrame.add(this.panel);
        this.mainFrame.pack();
    }
   
    @Override
    public JFrame getFrame() { 
        return mainFrame;
    }

    public BackgroundPanel getPanel() {
       return this.panel;
    }

    public Timer getTimer() {
       return this.timer;
    }

    @Override
    public void addScreenNavigator(NavigateToScreen screenNavigator) {
        this.navigators.add(screenNavigator);
    }

    public ArrayList<NavigateToScreen> getNavigators() {
       return navigators;
    }

    public int getBackgroundWidth() {
       return this.imgWidth;
    }
}
