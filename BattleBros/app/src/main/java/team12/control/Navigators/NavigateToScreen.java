package team12.control.Navigators;

import team12.view.tools.GameWindow;

public class NavigateToScreen {

   private GameWindow destination;
   private GameWindow origin;

   public NavigateToScreen(GameWindow destination, GameWindow origin) {
      this.destination = destination;
      this.origin = origin;
   }

   public void goToScreen() {
      this.origin.getFrame().setVisible(false);
      this.destination.getFrame().setVisible(true);
   }

}

