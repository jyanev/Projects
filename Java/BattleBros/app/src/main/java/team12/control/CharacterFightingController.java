package team12.control;

import team12.view.Bar.*;
import team12.view.Arena.MovableCharacter;
import team12.model.GameCharacters.Fighter;
import java.awt.event.*;
import javax.swing.Timer;
import team12.model.Projectiles.*;
import java.util.ArrayList;

public class CharacterFightingController implements KeyListener {
   private Fighter fighter;
   private Fighter opponent;
   private long timeOfLastBasicAttack;
   private long timeOfLastRangedAttack;
   private boolean isKeyPressed;
   private boolean waitTime;
   private Shield shield;
   private int count = 0;
   private int currentDirection;
   private ArrayList<Integer> y_velocitiesGrenade;
   private int index;
   private int offCount;
   private Timer offTimer;
   private MovableCharacter mc;

   private int counter =0;

   private Timer timer = new Timer(100, new ActionListener() {
      public void actionPerformed(ActionEvent e) {
         if (fighter.getShieldBar().getController().getValue() <= 0) {
            shield.setVisible(false);
            waitTime = false;
         }
         else if (fighter.getShieldBar().getController().getValue() >= 160) {
            waitTime = true;
         }
      }
   });

   //takes care of animation in case of down slam
   private Timer downSlamTimer = new Timer(5, new ActionListener() {
      public void actionPerformed(ActionEvent e) {
         //checks if the character has hit the ground (Y = 180) 
         
         
         if (fighter.getY() < 180) {
               fighter.downSlam();
         }
         else {
               //checks if one character hits another
               if (fighter.checkHit(opponent, true)) {
                  if (opponent.getShieldBar().getController().isActive()) {
                     opponent.getShieldBar().getController().isAttacked(true, 25);
                  }
                  else {
                     opponent.getHealthBar().getController().isAttacked(true, 40);
                  }
               }
               //failsafe that teleports fighter to (Y = 180)
               //after downslam is done in case it falls through ground
               fighter.jump(180 - fighter.getY());
               fighter.setMidair(false);
               stopDownSlamTimer();
         }
      }
   });

   private Timer projectileTimer = new Timer(10, new ActionListener() {
      public void actionPerformed(ActionEvent e) {

         if (fighter.getProjectileType().getX() < 980 && fighter.getProjectileType().getX() > -80) {
            if (fighter.getProjectileType().getProjectileType() == 0) {
               if (currentDirection == 0) {
                  fighter.getProjectileType().moveHorizontal(8);
               } 
               else if (currentDirection == 1) {
                  fighter.getProjectileType().moveHorizontal(-8);
               }
               if (fighter.getProjectileType().checkHit(opponent)) {
                  stopProjectileTimer();
                  fighter.getProjectileType().setX(-300);
                  fighter.getProjectileType().setY(0);
                  if (opponent.getShieldBar().getController().isActive()) {
                     opponent.getShieldBar().getController().isAttacked(true, 30);
                  }
                  else {
                     opponent.getHealthBar().getController().isAttacked(true, 50);
                  }
               }
            }
            else if (fighter.getProjectileType().getProjectileType() == 1) {
               
               if (currentDirection == 0) {
                  fighter.getProjectileType().moveHorizontal(8);
                  fighter.getProjectileType().moveVertical(y_velocitiesGrenade.get(index));
               }
               else if (currentDirection == 1) {
                  fighter.getProjectileType().moveHorizontal(-8);
                  fighter.getProjectileType().moveVertical(y_velocitiesGrenade.get(index));
               }

               if (index < y_velocitiesGrenade.size() - 1) {
                  index++;
               }
               else {
                  stopProjectileTimer();
                  fighter.getProjectileType().getProjectileSprite().setExplosion();
                  if (fighter.getProjectileType().checkHit(opponent)) {
                     if (opponent.getShieldBar().getController().isActive()) {
                        opponent.getShieldBar().getController().isAttacked(true, 50);
                     }
                     else {
                        opponent.getHealthBar().getController().isAttacked(true, 80);
                     }
                  }
                  startExplosionTimer();

               }
            }

               
         }
         else {
            stopProjectileTimer();
            fighter.getProjectileType().setX(-300);
            fighter.getProjectileType().setY(0);
         }
      }
   });

   private Timer explosionTimer = new Timer(1000, new ActionListener() {
      public void actionPerformed(ActionEvent e) {
         fighter.getProjectileType().setX(-300);
         fighter.getProjectileType().setY(0);
         stopExplosionTimer();
      }
   });


   public CharacterFightingController(Fighter c, Fighter opp, Shield shield, MovableCharacter mc) {
      this.fighter = c;
      this.opponent = opp;
      this.isKeyPressed = false;
      this.timeOfLastBasicAttack = System.currentTimeMillis() - 1500;
      this.timeOfLastRangedAttack = System.currentTimeMillis() - 1500;
      this.shield = shield;
      this.waitTime = true;
      this.mc = mc;
   }

   @Override
   public void keyPressed(KeyEvent e) {
      //takes care of different key press cases
      if (!this.isKeyPressed) {
         if (e.getKeyCode() == KeyEvent.VK_S && !downSlamTimer.isRunning() && fighter.getY() < 180) {
            startDownSlamTimer();
         }
         if (e.getKeyCode() == KeyEvent.VK_J && System.currentTimeMillis() - timeOfLastBasicAttack > 1200) {
            if (fighter.getDirection() == 0) {
               mc.getDisplay().setCurrentSprite(2);
            }
            else if (fighter.getDirection() == 1) {
               mc.getDisplay().setCurrentSprite(4);
            }

            fighter.getProjectileType().notifyObservers();
            fighter.meleeAttack(1);
            if (fighter.checkHit(this.opponent, false)) {
               if (opponent.getShieldBar().getController().isActive()) {
                  opponent.getShieldBar().getController().isAttacked(true, 15);
               }
               else {
                  opponent.getHealthBar().getController().isAttacked(true, 25);
               }
            }
            timeOfLastBasicAttack = System.currentTimeMillis();
         }
         else if (e.getKeyCode() == KeyEvent.VK_K && System.currentTimeMillis() - timeOfLastRangedAttack > 1500) {
            
            this.currentDirection = fighter.getDirection();
            if (fighter.getProjectileType().getProjectileType() == 1) {
               this.y_velocitiesGrenade = fighter.getProjectileType().calculateGrenadeTrajectory(fighter.getY());
               this.index = 0;
            }

            if (this.currentDirection == 0) {
               mc.getDisplay().setCurrentSprite(6);
               fighter.getProjectileType().getProjectileSprite().setRightFacing();
               fighter.getProjectileType().notifyObservers();
            }
            else if (this.currentDirection == 1) {
               mc.getDisplay().setCurrentSprite(7);
               fighter.getProjectileType().getProjectileSprite().setLeftFacing();
               fighter.getProjectileType().notifyObservers();
            }
            fighter.getProjectileType().setX(fighter.getX());
            fighter.getProjectileType().setY(fighter.getY());
           
            startProjectileTimer();
            timeOfLastRangedAttack = System.currentTimeMillis();  
         }
         else if (e.getKeyCode() == KeyEvent.VK_L) {
            if(!fighter.getShieldBar().getController().isZero() && waitTime) {
               
               fighter.shield();
               if (count != 0) {
                  fighter.getShieldBar().getController().stopShieldDepletionTimer();
               }
               count++;

               fighter.getShieldBar().getController().startShieldDepletionTimer();
               shield.setVisible(true);
               shield.revalidate();
               shield.repaint();

               if (!this.timer.isRunning()) {
                  startShieldTimer();
               }
               
            }
         }
         this.isKeyPressed = true;
      }
   }

   @Override
   public void keyReleased(KeyEvent e) {
      this.isKeyPressed = false;
      if(e.getKeyCode() == KeyEvent.VK_L)
      {
         shield.setVisible(false);
         fighter.getShieldBar().getController().stopShieldDepletionTimer();
      }
   }
 
   @Override
   public void keyTyped(KeyEvent e){

   
   }

   public boolean isShieldTimerRunning() {
      if (fighter.getShieldBar().getController().isShieldTimerRunning()) {
         return true;
      }
      return false;
   }

   public boolean isDownSlamTimerRunning() {
      if (this.downSlamTimer.isRunning()) {
         System.out.println("DOWN SLAM TIMER IS ON");
         return true;
      }
      return false;
   }

   public boolean isProjectileTimerRunning() {
      if (this.projectileTimer.isRunning()) {
         System.out.println("PROJECTILE TIMER IS ON");
         return true;
      }
      return false;
   }

   public boolean isExplosionTimerRunning() {
      if (this.explosionTimer.isRunning()) {
         System.out.println("EXPLOSION TIMER IS ON");
         return true;
      }
      return false;
   }

   public void startShieldTimer() {
      this.timer.start();
   }

   public void stopShieldTimer() {
      fighter.getShieldBar().getController().shutDownShieldTimer();   

   }

   public void startDownSlamTimer() {
      this.downSlamTimer.start();
   }

   public void stopDownSlamTimer() {
      this.downSlamTimer.stop();
   }

   public void startProjectileTimer() {
      this.projectileTimer.start();
   }
   
   public void stopProjectileTimer() {
      this.projectileTimer.stop();
   }

   public void startExplosionTimer() {
      this.explosionTimer.start();
   }

   public void stopExplosionTimer() {
      this.explosionTimer.stop();
   }

 
}



