package team12.view.tools;

import javax.swing.JFrame;
import team12.control.Navigators.NavigateToScreen;

public interface GameWindow {
   //GameWindow is an interface for each window of the game, (i.e StartMenu, Arena...)

   public JFrame getFrame();

   public void addScreenNavigator(NavigateToScreen screenNavigator);
}


