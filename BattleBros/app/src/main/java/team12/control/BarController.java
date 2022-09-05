package team12.control;

import team12.model.GameCharacters.*;
import team12.view.Arena.*;
import team12.view.Bar.Shield;
import team12.view.ProjectileSprites.*;

import java.awt.Color;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.Timer;
import java.awt.event.*;
import java.io.IOException;
import java.awt.Component;
import java.awt.Font;

public class BarController {
    
    private int barValue;
    private boolean active;
    //private Timer timer;
    private Timer offTimer;
    private int count;
    private boolean needFill = false;
    private int counter = 0;
    
    private Timer timer = new Timer(140, new ActionListener() {
         public void actionPerformed(ActionEvent e) { 

            if (active && !isZero() && !needFill) {
                 barValue -= 6;
                 if (barValue <= 0)
                 {
                     barValue = 0;
                     needFill = true;
                 }
                 
             }

             else if (needFill) {
                barValue += 10;
                if (barValue >= 160) {

                   barValue = 160;
                   needFill = false;
                   shutDownShieldTimer();
                }
                
             }

             else if (!active) {
                 barValue += 3;
                 if (barValue >= 160)
                 {
                     barValue = 160;
                     shutDownShieldTimer();
                 }
                 
             }
         }
     });
    public BarController(int type) {
       
        // 0 starts the bar with 200 (for health)
        if (type == 0) {
            barValue = 200;
        }

        // 1 starts the bar with 160 (for shield)
        else if (type == 1) {
            barValue = 160;
        }

        this.active = false;
        
    }

    public void isAttacked(boolean isHit, int damage) {
        if (isHit) {
            this.barValue = this.barValue - damage;
        }
    }

    public int getValue() {
        return this.barValue;
    }

    public int setValue(int num) {
        return this.barValue = num;
    }

    public boolean isZero() {
        if (getValue() == 0) {
            return true;
        }
        else {
            return false;
        }
    }
    
    public boolean isActive() {
        // A shield is activated by a keypress, so it should also be like while pressed, then decrease the bar
        return active;
    }


    public void deactivateShield() {
        this.offTimer = new Timer(100, new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                count++;
                if (count == 10) {
                    stopShieldDepletionTimer();
                }
            }  
        });
        this.offTimer.start();
    }

   public boolean isShieldTimerRunning() {
      if (this.timer.isRunning()) {
         System.out.println("SHIELD TIMER IS ON");
         return true;
      }
      return false;
   }

   public void startShieldDepletionTimer() {
      
      if (!this.timer.isRunning()) {
         this.timer.start();
      }
      else {
         
      }
      this.active = true;

   }


   public void stopShieldDepletionTimer() {
      this.active = false;
   }

   public void shutDownShieldTimer() {
      this.timer.stop();
   }

}
