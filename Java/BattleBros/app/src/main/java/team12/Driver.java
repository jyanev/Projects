package team12;

import team12.control.Navigators.*;
import team12.view.StartMenu.*;
import team12.view.Arena.*;
import team12.view.EndScreen.*;
import team12.view.Stats.*;


public class Driver {
   public static void main(String []args) {
      Arena arena = new Arena();
      Navigator navigator = new Navigator(new StartMenu(arena), arena, new EndScreen(true), new EndScreen(false), new StatScreen());
   }
}
