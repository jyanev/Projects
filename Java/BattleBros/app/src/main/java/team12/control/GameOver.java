package team12.control;

import team12.model.GameCharacters.Fighter;

public class GameOver {
   private Fighter opponent;
   private Fighter player;

   public GameOver(Fighter c, Fighter opp) {
      this.player = c;
      this.opponent = opp;
   }

   public boolean checkGameOver() {
      if (opponent.getHealthBar().getController().getValue() <= 0) {
         return true;
      }
      else if (player.getHealthBar().getController().getValue() <= 0) {
         return true;
      }
      else {
         return false;
      }
   }

   public boolean getWinner() {
      if (opponent.getHealthBar().getController().getValue() <= 0) {
         return true;
      }
      else {
         return false;
      }
   }
}

