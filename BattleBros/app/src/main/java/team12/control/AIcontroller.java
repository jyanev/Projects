package team12.control;

import team12.view.Arena.Arena;
import team12.view.Bar.Shield;
import team12.model.GameCharacters.Fighter;
import java.awt.event.*;
import javax.swing.Timer;
import java.lang.Math;
import team12.view.Arena.MovableCharacter;

public class AIcontroller implements ActionListener {
    private Fighter player;
    private Fighter bot;
    private Arena arena;
    private Timer aiTimer = new Timer(200, this);
    private long timeOfLastDownSlam = System.currentTimeMillis() - 500;
    private KeyEvent a;
    private KeyEvent d;
    private KeyEvent w;
    private Shield shi;
    private MovableCharacter mp;

    private KeyEvent aReleased;
    private KeyEvent dReleased;

    private KeyEvent s;
    private KeyEvent j;
    private KeyEvent k;
    private KeyEvent l;

    private KeyEvent sReleased;
    private KeyEvent jReleased;
    private KeyEvent kReleased;
    private KeyEvent lReleased;

    private CharacterMovementController mController;
    private CharacterFightingController fController;


    public AIcontroller(Fighter player, Fighter bot, Arena arena, Shield shield, MovableCharacter mc) {
        this.player = player;
        this.bot = bot;
        this.arena = arena;
        this.shi = shield;

        a = new KeyEvent(arena.getFrame(), KeyEvent.KEY_PRESSED,
                            System.currentTimeMillis(), 0, KeyEvent.VK_A);

        d = new KeyEvent(arena.getFrame(), KeyEvent.KEY_PRESSED,
                            System.currentTimeMillis(), 0, KeyEvent.VK_D);

        aReleased = new KeyEvent(arena.getFrame(), KeyEvent.KEY_RELEASED,
                            System.currentTimeMillis(), 0, KeyEvent.VK_A);

        dReleased = new KeyEvent(arena.getFrame(), KeyEvent.KEY_RELEASED,
                            System.currentTimeMillis(), 0, KeyEvent.VK_D);

        w = new KeyEvent(arena.getFrame(), KeyEvent.KEY_PRESSED,
                            System.currentTimeMillis(), 0, KeyEvent.VK_W);


        j = new KeyEvent(arena.getFrame(), KeyEvent.KEY_PRESSED,
                            System.currentTimeMillis(), 0, KeyEvent.VK_J);   

        k = new KeyEvent(arena.getFrame(), KeyEvent.KEY_PRESSED,
                            System.currentTimeMillis(), 0, KeyEvent.VK_K);

        s = new KeyEvent(arena.getFrame(), KeyEvent.KEY_PRESSED,
                            System.currentTimeMillis(), 0, KeyEvent.VK_S);

        l = new KeyEvent(arena.getFrame(), KeyEvent.KEY_PRESSED,
                            System.currentTimeMillis(), 0, KeyEvent.VK_L);

        sReleased = new KeyEvent(arena.getFrame(), KeyEvent.KEY_RELEASED,
                            System.currentTimeMillis(), 0, KeyEvent.VK_S);
        jReleased = new KeyEvent(arena.getFrame(), KeyEvent.KEY_RELEASED,
                            System.currentTimeMillis(), 0, KeyEvent.VK_J); 
        kReleased = new KeyEvent(arena.getFrame(), KeyEvent.KEY_RELEASED,
                            System.currentTimeMillis(), 0, KeyEvent.VK_K);
        lReleased = new KeyEvent(arena.getFrame(), KeyEvent.KEY_RELEASED,
                            System.currentTimeMillis(), 0, KeyEvent.VK_L);

        this.mController = new CharacterMovementController(bot);
        this.fController = new CharacterFightingController(bot, player, shi, mc);
        startAITimer();
    }

    

    public void actionPerformed(ActionEvent e) {

       if (bot.getShieldBar().getController().isActive()) {
          fController.keyReleased(lReleased);
       }
       

       if (bot.getX() > player.getX() + 75) {
          if (Math.random() > 0.4) {  
             if (mController.getPressedKeys().contains(d.getKeyCode())) {
                 this.mController.keyReleased(dReleased);
             }
             this.mController.keyPressed(a);
             if (Math.random() > 0.75) {
                 this.mController.keyPressed(w);
             }
          }
          
          else if (Math.random() < 0.2) {
             if (mController.getPressedKeys().contains(d.getKeyCode())) {
                this.mController.keyReleased(dReleased);
             }
          }

          else {
             if (this.mController.getPressedKeys().contains(a.getKeyCode())) {
                this.mController.keyReleased(aReleased);
                this.mController.keyPressed(d);
             }
             
             
          }

        }

        else if (bot.getX() < player.getX() - 75) {
           if (Math.random() > 0.4) {

             if (mController.getPressedKeys().contains(a.getKeyCode())) {
                 this.mController.keyReleased(aReleased);
             }
             if (Math.random() > 0.75) {
                 this.mController.keyPressed(w);
             }
             this.mController.keyPressed(d);
           }

           else if (Math.random() < 0.2) {
              if (mController.getPressedKeys().contains(d.getKeyCode())) {
                 this.mController.keyReleased(dReleased);
              }
           }

           else {
              if (this.mController.getPressedKeys().contains(d.getKeyCode())) {
                 this.mController.keyReleased(dReleased);
                 this.mController.keyPressed(d);
              }
           }
        }

        if (bot.getY() > player.getY() && Math.abs(bot.getX() - player.getX()) < 300) {
            if (Math.random() > 0.5) {
                this.mController.keyPressed(w);
            }
        }

        if (bot.getY() + 20 < player.getY() && Math.abs(bot.getX() - player.getX()) < 500) {
            
            if (Math.random() > 0.6) {
                if (System.currentTimeMillis() - this.timeOfLastDownSlam > 500) {
                   this.fController.keyPressed(s);
                   this.fController.keyReleased(sReleased);
                   this.timeOfLastDownSlam = System.currentTimeMillis();
                }
                           
            }
        }

        if (Math.abs(bot.getX() - player.getX()) < 150 && Math.abs(bot.getY() - player.getY()) < 150) {
            if (Math.random() > 0.25) {
                this.fController.keyPressed(j);
                this.fController.keyReleased(jReleased);
            }
        }

        if (bot.getFighterType() == 1) {
           if (Math.abs(bot.getX() - player.getX()) > 100 && Math.abs(bot.getX() - player.getX()) < 350) {
              if (Math.random() < 0.18) {
                 fController.keyPressed(k);
                 fController.keyReleased(kReleased);
              }
           }
        }

        else if (bot.getFighterType() == 0) {
           if (Math.abs(bot.getY() - player.getY()) < 75) {
              if (Math.random() < 0.14) {
                 fController.keyPressed(k);
                 fController.keyReleased(kReleased);
              }
           }
        }

        if (Math.abs(player.getProjectileType().getX() - bot.getX()) < 180) {

           if (Math.random() < .2) {
              fController.keyPressed(l);
           }
            
           else if (Math.random() > .9) {
              mController.keyPressed(w);
           }
        }
    }

   
    public void startAITimer() {
       System.out.println("ai timer started");
       this.aiTimer.start();
    }

    public void stopAITimer() {
       System.out.println("ai timer stopped");
       this.aiTimer.stop();
       if (mController.isLeftRightTimerRunning()) {
          System.out.println("TERMINATING LEFT RIGHT TIMER ~ AI");
          mController.stopLeftRightTimer();
       }

       if (mController.isJumpTimerRunning()) {
          System.out.println("TERMINATING JUMP TIMER ~ AI");
          mController.stopJumpTimer();
       }

       if (fController.isDownSlamTimerRunning()) {
          System.out.println("TERMINATING DOWN SLAM TIMER ~ AI");
          fController.stopDownSlamTimer();
       }

       if (fController.isProjectileTimerRunning()) {
          System.out.println("TERMINATING PROJECTILE TIMER ~ AI");
          fController.stopProjectileTimer();
       }

       if (fController.isExplosionTimerRunning()) {
          System.out.println("TERMINATING EXPLOSION TIMER ~ AI");
          fController.stopExplosionTimer();
       }

       if (fController.isShieldTimerRunning()) {
          System.out.println("TERMINATING SHIELD TIMER ~ AI");
          fController.stopShieldTimer();
       }

    }


}
