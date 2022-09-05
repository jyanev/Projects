package team12.model.Projectiles;

import team12.model.tools.*;
import team12.view.ProjectileSprites.*;
import team12.model.GameCharacters.Fighter;
import java.lang.Math;
import java.util.ArrayList;

public class Grenade extends Projectile {

   private ArrayList<Integer> y_velocities = new ArrayList<Integer>();

   public Grenade(ProjectileSprite pSprite, int pType, int x, int y) {

      super(pSprite, pType, x, y);

   }

   public void moveVertical(int velocity) {
      this.y += velocity;
      super.notifyObservers();
   }

   public void moveHorizontal(int velocity) {
      this.x += velocity;
      super.notifyObservers();
   }

   public boolean checkHit(Fighter fighter) {
      if (Math.abs(Math.sqrt(Math.pow((this.x - fighter.getX()), 2) + Math.pow((this.y - fighter.getY()), 2))) < 115) {
         
         return true;
      }

      return false;
   }


   public ArrayList<Integer> calculateGrenadeTrajectory(int Y) {

      //calculates jump trajectory by storing changes in Y velocity in an ArrayList
      y_velocities.clear();

      int tempY = Y;
      int startY = Y;
      int currentVelocity = -14;
      tempY += currentVelocity;
      y_velocities.add(currentVelocity);
      currentVelocity += 1;

      while (tempY < startY) {
         tempY += currentVelocity;
         y_velocities.add(currentVelocity);
         currentVelocity += 1;
      }

      return y_velocities;
   }






}

