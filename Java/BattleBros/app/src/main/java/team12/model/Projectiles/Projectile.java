package team12.model.Projectiles;

import team12.model.tools.*;
import java.util.ArrayList;
import team12.view.ProjectileSprites.*;
import team12.model.GameCharacters.Fighter;

public abstract class Projectile {

   protected ProjectileSprite pSprite;
   protected int x;
   protected int y;
   private int pType;
   protected ArrayList<ObjectObserver> observers;

   public Projectile(ProjectileSprite pSprite, int pType, int x, int y) {

      this.pSprite = pSprite;
      this.pType = pType;
      this.x = x;
      this.y = y;
      this.observers = new ArrayList<ObjectObserver>();
   }

   public ProjectileSprite getProjectileSprite() {
      return this.pSprite;
   }

   public int getProjectileType() {
      return this.pType;
   }

   public void setX(int x) {
      this.x = x;
      notifyObservers();
   }

   public void setY(int y) {
      this.y = y;
      notifyObservers();
   }

   public int getX() {
      return this.x;
   }

   public int getY() {
      return this.y;
   }

   public void addObserver(ObjectObserver o) {
      observers.add(o);
   }

   public void notifyObservers() {
      for (ObjectObserver o: observers) {
         o.update(getX(), getY());
      }
   }

   public abstract ArrayList<Integer> calculateGrenadeTrajectory(int Y);

   public abstract void moveVertical(int velocity);

   public abstract void moveHorizontal(int velocity);

   public abstract boolean checkHit(Fighter fighter);
}

