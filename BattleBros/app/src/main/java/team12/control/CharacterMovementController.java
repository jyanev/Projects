package team12.control;

import team12.model.GameCharacters.Fighter;
import java.awt.event.*;
import java.util.*;
import javax.swing.Timer;
import java.util.ArrayList;

public class CharacterMovementController implements KeyListener, ActionListener {
    // Set of currently pressed keys
    private final Set<Integer> pressedKeys = new HashSet<>();
    private Fighter fighter;
    private ArrayList<Integer> y_velocities;
    private Timer timer = new Timer(3, this);
    private int index;
    private int jumpCounter = 0;
    
    //jumpTimer changes the Y position of the gameCharacter every 7 milliseconds
    //based on the values in y_velocities

    private Timer jumpTimer = new Timer(7, new ActionListener() {
        public void actionPerformed(ActionEvent e) {
            fighter.jump(y_velocities.get(index));
            if (index < y_velocities.size() - 1) {
                index++;
            }
            else {
                fighter.jump(180 - fighter.getY());
                fighter.setMidair(false);
                jumpCounter = 0;
                index = 0;
                stopJumpTimer();
            }
        }
    });

    public CharacterMovementController(Fighter c) {
        this.fighter = c;
        this.timer.setInitialDelay(0);
    }

    @Override
    public synchronized void keyPressed(KeyEvent e) {
        if (timer.isRunning()) {
            stopLeftRightTimer();
        }

        //if jump key is pressed, trigger jumpEvent
        if (e.getKeyCode() == KeyEvent.VK_W) {
            if (jumpCounter < 2) {
                jumpEvent();
            }
        }
        
        //if downslam key is pressed while jumping, stop jump animation
        if (e.getKeyCode() == KeyEvent.VK_S) {
            if (jumpTimer.isRunning()) {
                stopJumpTimer();
                jumpCounter = 0;
                index = 0;
            }
        }

        //if left or right are pressed, add them 
        else if (e.getKeyCode() == KeyEvent.VK_A || e.getKeyCode() == KeyEvent.VK_D) {
            pressedKeys.add(e.getKeyCode());
        }
        startLeftRightTimer();
    }

    @Override
    public synchronized void keyReleased(KeyEvent e) {  
        //if key is released, stop the movement timer (this.timer)
        stopLeftRightTimer();
        pressedKeys.remove(e.getKeyCode());
        if (!pressedKeys.isEmpty()) {
            startLeftRightTimer();
        }
    }

    @Override
    public void keyTyped(KeyEvent e) {
         /* Not used */ 
    }

    //called by the movement timer (this.timer)
    public void actionPerformed(ActionEvent e) {
        //takes care of left and right movement    
        if (!pressedKeys.isEmpty()) {
            for (Iterator<Integer> it = pressedKeys.iterator(); it.hasNext();) {
                switch (it.next()) {
                    case KeyEvent.VK_A:
                        if (!fighter.getShieldBar().getController().isActive()) {
                            fighter.walk(-7);
                        }
                        break;
                    //case KeyEvent.VK_S:
                    case KeyEvent.VK_D:
                        if (!fighter.getShieldBar().getController().isActive()) {
                            fighter.walk(7);
                        }
                        break;
                }
            }
        }
    }

    public void jumpEvent() {    
        if (jumpTimer.isRunning()) {
            stopJumpTimer();
            index = 0;
        }

        fighter.setMidair(true);
        jumpCounter++;
        
        y_velocities = fighter.calculateJumpTrajectory();
                
        startJumpTimer();
   }

   public Set<Integer> getPressedKeys() {
       return this.pressedKeys;
   }

   
   public boolean isLeftRightTimerRunning() {
      if (this.timer.isRunning()) {
         System.out.println("LEFT RIGHT TIMER IS ON");
         return true;
      }
      return false;
   }

   public boolean isJumpTimerRunning() {
      if (this.jumpTimer.isRunning()) {
         System.out.println("JUMP TIMER IS ON");
         return true;
      }
      return false;
   }


   public void startLeftRightTimer() {
      this.timer.start();
   }

   public void stopLeftRightTimer() {
      this.timer.stop();
   }

   public void startJumpTimer() {
      this.jumpTimer.start();
   }

   public void stopJumpTimer() {
      this.jumpTimer.stop();
   }

   
}
