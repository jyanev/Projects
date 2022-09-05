package team12.model.Projectiles;

import team12.model.tools.*;
import team12.view.ProjectileSprites.*;
import team12.model.GameCharacters.Fighter;
import java.lang.Math;
import java.util.ArrayList;

public class Fireball extends Projectile {


   public Fireball(ProjectileSprite pSprite, int pType, int x, int y) {

      super(pSprite, pType, x, y);

   }

   public void moveVertical(int velocity) {}

   public ArrayList<Integer> calculateGrenadeTrajectory(int Y) {
      return new ArrayList<Integer>();
   }

   public void moveHorizontal(int velocity) {
      this.x += velocity;
      super.notifyObservers();
   }

   public boolean checkHit(Fighter fighter) {
      if (Math.abs(Math.sqrt(Math.pow((this.x - fighter.getX()), 2) + Math.pow((this.y - fighter.getY()), 2))) < 40) {
         
         return true;
      }

      return false;
   }

}
