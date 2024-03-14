package team12.control;

import team12.control.TwoPlayer.*;
import team12.model.GameCharacters.*;
import team12.view.Arena.*;
import team12.view.Bar.Shield;
import team12.view.ProjectileSprites.*;
import team12.view.Stats.Stats;

import java.awt.Color;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.Timer;
import java.awt.event.*;
import java.io.IOException;
import java.awt.Component;
import java.awt.Font;

public class StartGame {
   //StartGame initializes the human playable character 
   //and the computer's character
   private Arena arena;
   private Fighter player;
   private Fighter opponent;
   private Timer timer;
   private Timer countTime;
   private JLabel countDown;
   private Shield s1;
   private Shield s2;
   private int count = 3;
   private MovableCharacter mc1;
   private MovableCharacter mc2;

   private CharacterMovementController mController;
   private CharacterFightingController fController;
   private Fighter1Controller f1Controller;
   private Fighter2Controller f2Controller;
   private Movement2Controller oppController;
   private AIcontroller ai;

   private int counter = 0;

   public StartGame(Arena arena) {
      //Character 1 is used as the player's character, Character 2 is the computer
      this.arena = arena;
   }

   public void beginFight(boolean twoPlayer) {
      //creates the health visual
      if (!twoPlayer) {
         player.getHealthBar().setBounds(10, 15, 200, 20);
         arena.getPanel().add(player.getHealthBar());
         player.getShieldBar().setBounds(10, 35, 160, 10);
         arena.getPanel().add(player.getShieldBar());

         opponent.getHealthBar().setBounds(arena.getBackgroundWidth()-210, 15, 200, 20);
         arena.getPanel().add(opponent.getHealthBar());
         opponent.getShieldBar().setBounds(arena.getBackgroundWidth()-210, 35, 160, 10);
         arena.getPanel().add(opponent.getShieldBar());

         this.s1 = new Shield(player);
         this.s1.setBounds(player.getX()-43, player.getY()+18, 900, 508);
         arena.getPanel().add(s1);

         this.s2 = new Shield(opponent);
         this.s2.setBounds(opponent.getX() - 683, opponent.getY() + 18, 900, 508);
         arena.getPanel().add(s2);

         this.countDown = new JLabel();
         this.countDown.setText("3");
         this.countDown.setForeground(Color.WHITE);
         this.countDown.setFont(new Font("Serif", Font.BOLD, 200));
         this.countDown.setBounds(400, 0, 900, 500);
         arena.getPanel().add(countDown);

         //spawns in the characters as movable characters
         try {
            this.mc1 = new MovableCharacter(player, 78, 67);
            this.mc2 = new MovableCharacter(opponent, 50, 67);
         
            MovableProjectile mp1 = new MovableProjectile(player.getProjectileType(), 80, 54);
            MovableProjectile mp2 = new MovableProjectile(opponent.getProjectileType(), 80, 54);

            player.addObserver(mc1);
            opponent.addObserver(mc2);

            player.getProjectileType().addObserver(mp1);
            opponent.getProjectileType().addObserver(mp2);

            arena.getPanel().add(mc1);
            arena.getPanel().add(mc2);
            arena.getPanel().add(mp1);
            arena.getPanel().add(mp2);

            GameOver gameOver = new GameOver(player, opponent);
            
            this.timer = new Timer(100, new ActionListener() {
               public void actionPerformed(ActionEvent e) {
               
                  if (player.getX() > opponent.getX())
                  {
                     mc1.getDisplay().setCurrentSprite(1);
                     mc2.getDisplay().setCurrentSprite(0);
                     mc1.update(player.getX(), player.getY());
                     mc2.update(opponent.getX(), opponent.getY());
                  }

                  if (player.getX() < opponent.getX())
                  {
                     mc1.getDisplay().setCurrentSprite(0);
                     mc2.getDisplay().setCurrentSprite(1);
                     mc1.update(player.getX(), player.getY());
                     mc2.update(opponent.getX(), opponent.getY());
                  }               
      
                  if (gameOver.checkGameOver()) {
      
                     //if getWinner() returns true, then player wins and show victory screen
                     //else computer wins and show defeat screen
                     

                     if (mController.isLeftRightTimerRunning()) {
                        System.out.println("TERMINATING LEFT RIGHT TIMER ~ PLAYER");
                        mController.stopLeftRightTimer();
                     }

                     if (mController.isJumpTimerRunning()) {
                        System.out.println("TERMINATING JUMP TIMER ~ PLAYER");
                        mController.stopJumpTimer();
                     }

                     if (fController.isDownSlamTimerRunning()) {
                        System.out.println("TERMINATING DOWN SLAM TIMER ~ PLAYER");
                        fController.stopDownSlamTimer();
                     }

                     if (fController.isProjectileTimerRunning()) {
                        System.out.println("TERMINATING PROJECTILE TIMER ~ PLAYER");
                        fController.stopProjectileTimer();
                     }

                     if (fController.isExplosionTimerRunning()) {
                        System.out.println("TERMINATING EXPLOSION TIMER ~ PLAYER");
                        fController.stopExplosionTimer();
                     }

                     if (fController.isShieldTimerRunning()) {
                        System.out.println("TERMINATING SHIELD TIMER ~ PLAYER");
                        fController.stopShieldTimer();
                     }

                     arena.getFrame().removeKeyListener(mController);
                     arena.getFrame().removeKeyListener(fController);
                     ai.stopAITimer();
                     stopGameTimer();

                     Component[] components = arena.getPanel().getComponents();

                     for (Component c : components) {
                        arena.getPanel().remove(c);
                     }

                     Stats statsUpdater = new Stats("stats.csv");
                     try {
                        statsUpdater.readStats();
                     } 
                     catch (IOException io) {
                        io.printStackTrace();
                     }

                     counter++;
                     int tempMins = counter % 600;
                     int tempSecs = (counter - (tempMins * 600)) % 10;

                     if (gameOver.getWinner()) {
                        arena.getNavigators().get(0).goToScreen();
                        try {
                           if ((tempMins < statsUpdater.getMinutes()) || (tempMins == statsUpdater.getMinutes() && tempSecs < statsUpdater.getSeconds())) {
                              statsUpdater.writeStats(statsUpdater.getWins() + 1, statsUpdater.getLosses(), tempMins, tempSecs);
                           }
                           else {
                              statsUpdater.writeStats(statsUpdater.getWins() + 1, statsUpdater.getLosses(), statsUpdater.getMinutes(), statsUpdater.getSeconds());
                           }
                        } catch (IOException e1) {
                           e1.printStackTrace();
                        }
                     }
                     else {
                        arena.getNavigators().get(1).goToScreen();
                        try {
                           if ((tempMins < statsUpdater.getMinutes()) || (tempMins == statsUpdater.getMinutes() && tempSecs < statsUpdater.getSeconds())) {
                              statsUpdater.writeStats(statsUpdater.getWins(), statsUpdater.getLosses() + 1, tempMins, tempSecs);
                           }
                           else {
                              statsUpdater.writeStats(statsUpdater.getWins(), statsUpdater.getLosses() + 1, statsUpdater.getMinutes(), statsUpdater.getSeconds());
                           }
                        } catch (IOException e1) {
                           e1.printStackTrace();
                        }
                     }
                  }
               }
            });

            this.countTime = new Timer(1000, new ActionListener() {
               public void actionPerformed(ActionEvent e) {
                  

                  if (count >= 1) {
                     count--;

                     setCountDownLabelText(String.valueOf(count));
                  }
                  if(count == 0) {
                     stopCounterTimer();
                     setCountVisible(false);
                        
                     count = 3; 
                     mController = new CharacterMovementController(player);
                     fController = new CharacterFightingController(player, opponent, s1, mc1);
               
                     //initializes the AI controller
                     ai = new AIcontroller(player, opponent, arena, s2, mc2);

                     arena.getFrame().addKeyListener(mController);
                     arena.getFrame().addKeyListener(fController);
                  }
               }
            });

            startGameTimer();
            startCounterTimer();;
         }
         catch (IOException e) {
            System.out.println(e.getMessage());
         }
      }
      else if (twoPlayer) {
         player.getHealthBar().setBounds(10, 15, 200, 20);
         arena.getPanel().add(player.getHealthBar());
         player.getShieldBar().setBounds(10, 35, 160, 10);
         arena.getPanel().add(player.getShieldBar());

         opponent.getHealthBar().setBounds(arena.getBackgroundWidth()-210, 15, 200, 20);
         arena.getPanel().add(opponent.getHealthBar());
         opponent.getShieldBar().setBounds(arena.getBackgroundWidth()-210, 35, 160, 10);
         arena.getPanel().add(opponent.getShieldBar());

         this.s1 = new Shield(player);
         this.s1.setBounds(player.getX()-43, player.getY()+18, 900, 508);
         arena.getPanel().add(s1);

         this.s2 = new Shield(opponent);
         this.s2.setBounds(opponent.getX() - 683, opponent.getY() + 18, 900, 508);
         arena.getPanel().add(s2);

         this.countDown = new JLabel();
         this.countDown.setText("3");
         this.countDown.setForeground(Color.WHITE);
         this.countDown.setFont(new Font("Serif", Font.BOLD, 200));
         this.countDown.setBounds(400, 0, 900, 500);
         arena.getPanel().add(countDown);

         //spawns in the characters as movable characters
         try {
            this.mc1 = new MovableCharacter(player, 78, 67);
            this.mc2 = new MovableCharacter(opponent, 50, 67);
         
            MovableProjectile mp1 = new MovableProjectile(player.getProjectileType(), 80, 54);
            MovableProjectile mp2 = new MovableProjectile(opponent.getProjectileType(), 80, 54);

            player.addObserver(mc1);
            opponent.addObserver(mc2);

            player.getProjectileType().addObserver(mp1);
            opponent.getProjectileType().addObserver(mp2);

            arena.getPanel().add(mc1);
            arena.getPanel().add(mc2);
            arena.getPanel().add(mp1);
            arena.getPanel().add(mp2);

            GameOver gameOver = new GameOver(player, opponent);
            
            this.timer = new Timer(100, new ActionListener() {
               public void actionPerformed(ActionEvent e) {
               
                  if (player.getX() > opponent.getX())
                  {
                     mc1.getDisplay().setCurrentSprite(1);
                     mc2.getDisplay().setCurrentSprite(0);
                     mc1.update(player.getX(), player.getY());
                     mc2.update(opponent.getX(), opponent.getY());
                  }

                  if (player.getX() < opponent.getX())
                  {
                     mc1.getDisplay().setCurrentSprite(0);
                     mc2.getDisplay().setCurrentSprite(1);
                     mc1.update(player.getX(), player.getY());
                     mc2.update(opponent.getX(), opponent.getY());
                  }               
      
                  if (gameOver.checkGameOver()) {
      
                     //if getWinner() returns true, then player wins and show victory screen
                     //else computer wins and show defeat screen
                     if (mController.isLeftRightTimerRunning()) {
                        System.out.println("TERMINATING LEFT RIGHT TIMER ~ PLAYER");
                        mController.stopLeftRightTimer();
                     }

                     if (mController.isJumpTimerRunning()) {
                        System.out.println("TERMINATING JUMP TIMER ~ PLAYER");
                        mController.stopJumpTimer();
                     }

                     if (oppController.isLeftRightTimerRunning()) {
                        System.out.println("TERMINATING LEFT RIGHT TIMER ~ PLAYER");
                        oppController.stopLeftRightTimer();
                     }

                     if (oppController.isJumpTimerRunning()) {
                        System.out.println("TERMINATING JUMP TIMER ~ PLAYER");
                        oppController.stopJumpTimer();
                     }

                     if (f1Controller.isDownSlamTimerRunning()) {
                        System.out.println("TERMINATING DOWN SLAM TIMER ~ PLAYER");
                        f1Controller.stopDownSlamTimer();
                     }

                     if (f1Controller.isProjectileTimerRunning()) {
                        System.out.println("TERMINATING PROJECTILE TIMER ~ PLAYER");
                        f1Controller.stopProjectileTimer();
                     }

                     if (f1Controller.isExplosionTimerRunning()) {
                        System.out.println("TERMINATING EXPLOSION TIMER ~ PLAYER");
                        f1Controller.stopExplosionTimer();
                     }

                     if (f1Controller.isShieldTimerRunning()) {
                        System.out.println("TERMINATING SHIELD TIMER ~ PLAYER");
                        f1Controller.stopShieldTimer();
                     }

                     if (f2Controller.isDownSlamTimerRunning()) {
                        System.out.println("TERMINATING DOWN SLAM TIMER ~ PLAYER");
                        f2Controller.stopDownSlamTimer();
                     }

                     if (f2Controller.isProjectileTimerRunning()) {
                        System.out.println("TERMINATING PROJECTILE TIMER ~ PLAYER");
                        f2Controller.stopProjectileTimer();
                     }

                     if (f2Controller.isExplosionTimerRunning()) {
                        System.out.println("TERMINATING EXPLOSION TIMER ~ PLAYER");
                        f2Controller.stopExplosionTimer();
                     }

                     if (f2Controller.isShieldTimerRunning()) {
                        System.out.println("TERMINATING SHIELD TIMER ~ PLAYER");
                        f2Controller.stopShieldTimer();
                     }

                     arena.getFrame().removeKeyListener(mController);
                     arena.getFrame().removeKeyListener(oppController);
                     arena.getFrame().removeKeyListener(f1Controller);
                     arena.getFrame().removeKeyListener(f2Controller);
                     //ai.stopAITimer();
                     stopGameTimer();

                     Component[] components = arena.getPanel().getComponents();

                     for (Component c : components) {
                        arena.getPanel().remove(c);
                     }

                     Stats statsUpdater = new Stats("stats.csv");
                     try {
                        statsUpdater.readStats();
                     } 
                     catch (IOException io) {
                        io.printStackTrace();
                     }

                     counter++;
                     int tempMins = counter % 600;
                     int tempSecs = (counter - (tempMins * 600)) % 10;

                     if (gameOver.getWinner()) {
                        arena.getNavigators().get(0).goToScreen();
                        try {
                           if ((tempMins < statsUpdater.getMinutes()) || (tempMins == statsUpdater.getMinutes() && tempSecs < statsUpdater.getSeconds())) {
                              statsUpdater.writeStats(statsUpdater.getWins() + 1, statsUpdater.getLosses(), tempMins, tempSecs);
                           }
                           else {
                              statsUpdater.writeStats(statsUpdater.getWins() + 1, statsUpdater.getLosses(), statsUpdater.getMinutes(), statsUpdater.getSeconds());
                           }
                        } catch (IOException e1) {
                           e1.printStackTrace();
                        }
                     }
                     else {
                        arena.getNavigators().get(1).goToScreen();
                        try {
                           if ((tempMins < statsUpdater.getMinutes()) || (tempMins == statsUpdater.getMinutes() && tempSecs < statsUpdater.getSeconds())) {
                              statsUpdater.writeStats(statsUpdater.getWins(), statsUpdater.getLosses() + 1, tempMins, tempSecs);
                           }
                           else {
                              statsUpdater.writeStats(statsUpdater.getWins(), statsUpdater.getLosses() + 1, statsUpdater.getMinutes(), statsUpdater.getSeconds());
                           }
                        } catch (IOException e1) {
                           e1.printStackTrace();
                        }
                     }
                  }
               }
            });

            this.countTime = new Timer(1000, new ActionListener() {
               public void actionPerformed(ActionEvent e) {
                  if (count >= 1) {
                     count--;

                     setCountDownLabelText(String.valueOf(count));
                  }
                  if(count == 0) {
                     stopCounterTimer();
                     setCountVisible(false);
                        
                     count = 3; 
                     mController = new CharacterMovementController(player);
                     f1Controller = new Fighter1Controller(player, opponent, s1, mc1);
                     f2Controller = new Fighter2Controller(opponent,player, s2, mc2);
                     oppController = new Movement2Controller(opponent);
               
                     //initializes the AI controller
                     //ai = new AIcontroller(player, opponent, arena, s2, mc2);

                     arena.getFrame().addKeyListener(oppController);
                     arena.getFrame().addKeyListener(mController);
                     arena.getFrame().addKeyListener(f1Controller);
                     arena.getFrame().addKeyListener(f2Controller);
                  }
               }
            });

            startGameTimer();
            startCounterTimer();;
         }
         catch (IOException e) {
            System.out.println(e.getMessage());
         }
      }
   }

   public Fighter getPlayer() {
      return this.player;
   }

   public Fighter getOpponent() {
      return this.opponent;
   }

   public void setCountDownLabelText(String text) {
      countDown.setText(text);
   }

   public void setCountVisible(boolean b) {
      countDown.setVisible(b);
   }

   public void setPlayers(Fighter player, Fighter opponent) {
      this.player = player;
      this.opponent = opponent;
   }

   public void startGameTimer() {
      System.out.println("game timer started");
      this.timer.start();
   }

   public void stopGameTimer() {
      System.out.println("game timer stopped");
      this.timer.stop();
   }

   public void startCounterTimer() {
      System.out.println("counter timer started");
      this.countTime.start();
   }
   
   public void stopCounterTimer() {
      System.out.println("counter timer stopped");
      this.countTime.stop();
   }


}

