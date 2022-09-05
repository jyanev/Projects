package team12.control;

import team12.model.GameCharacters.*;
import javax.swing.*;
import javax.imageio.ImageIO;
import java.awt.Component;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.net.URL;

public class SpriteDisplayController {

   private BufferedImage currentSprite;
   
   private BufferedImage rightFacing;
   private BufferedImage leftFacing;
   
   private BufferedImage leftPunchWindup;
   private BufferedImage leftPunching;
   private BufferedImage rightPunchWindup;
   private BufferedImage rightPunching;

   private BufferedImage rightRanged;
   private BufferedImage leftRanged;

   private BufferedImage shielding;
   private BufferedImage ranged;

   private Fighter fighter;

   private long lastRangedTime;
   private long lastWindupTime;
   private long lastPunchTime;

   public SpriteDisplayController(Fighter c, int playerOrAI) {

      this.fighter = c;
      this.lastRangedTime = System.currentTimeMillis() - 300;
      this.lastWindupTime = System.currentTimeMillis() - 200;
      this.lastPunchTime = System.currentTimeMillis() - 400;

      if (c.getFighterType() == 0) {

         URL rightFacingURL = this.getClass().getClassLoader().getResource("mario1.png");
         URL leftFacingURL = this.getClass().getClassLoader().getResource("mario1 inverted.png");
         URL rightPunchWindupURL = this.getClass().getClassLoader().getResource("mariopunch1.png");
         URL rightPunchingURL = this.getClass().getClassLoader().getResource("mariopunch2.png");
         URL leftPunchWindupURL = this.getClass().getClassLoader().getResource("mariopunch1 inverted.png");
         URL leftPunchingURL = this.getClass().getClassLoader().getResource("mariopunch2 inverted.png");
         URL rightRangedURL = this.getClass().getClassLoader().getResource("marioranged.png");
         URL leftRangedURL = this.getClass().getClassLoader().getResource("marioranged inverted.png");
         
         try {
            this.rightFacing = ImageIO.read(rightFacingURL);
            this.leftFacing = ImageIO.read(leftFacingURL);
            this.rightPunchWindup = ImageIO.read(rightPunchWindupURL);
            this.rightPunching = ImageIO.read(rightPunchingURL);
            this.leftPunchWindup = ImageIO.read(leftPunchWindupURL);
            this.leftPunching = ImageIO.read(leftPunchingURL);
            this.rightRanged = ImageIO.read(rightRangedURL);
            this.leftRanged = ImageIO.read(leftRangedURL);
         }
         catch (IOException e) {
            System.out.println(e.getMessage());
         }

      }

      else if (c.getFighterType() == 1) {

         URL rightFacingURL = this.getClass().getClassLoader().getResource("luigi1.png");
         URL leftFacingURL = this.getClass().getClassLoader().getResource("luigi1 inverted.png");
         URL rightPunchWindupURL = this.getClass().getClassLoader().getResource("luigipunch1.png");
         URL rightPunchingURL = this.getClass().getClassLoader().getResource("luigipunch2.png");
         URL leftPunchWindupURL = this.getClass().getClassLoader().getResource("luigipunch1 inverted.png");
         URL leftPunchingURL = this.getClass().getClassLoader().getResource("luigipunch2 inverted.png");
         URL rightRangedURL = this.getClass().getClassLoader().getResource("luigiranged.png");
         URL leftRangedURL = this.getClass().getClassLoader().getResource("luigiranged inverted.png");
         try {
            this.rightFacing = ImageIO.read(rightFacingURL);
            this.leftFacing = ImageIO.read(leftFacingURL);
            this.rightPunchWindup = ImageIO.read(rightPunchWindupURL);
            this.rightPunching = ImageIO.read(rightPunchingURL);
            this.leftPunchWindup = ImageIO.read(leftPunchWindupURL);
            this.leftPunching = ImageIO.read(leftPunchingURL);
            this.rightRanged = ImageIO.read(rightRangedURL);
            this.leftRanged = ImageIO.read(leftRangedURL);
         }
         catch (IOException e) {
            System.out.println(e.getMessage());
         }
      }

      if (playerOrAI == 0) {
         this.currentSprite = this.rightFacing;
      }
      else if (playerOrAI == 1) {
         this.currentSprite = this.leftFacing;
      }

   }

   public void setCurrentSprite(int option) {
      // values for option
      // 0: rightFacing
      // 1: leftFacing
      // 2: rightPunchWindup
      // 3: rightPunching
      // 4: leftPunchWindup
      // 5: leftPunching
      // 6: rightRanged
      // 7: leftRanged
      
      if (option == 0) {
         if (System.currentTimeMillis() - this.lastPunchTime > 450 && 
             System.currentTimeMillis() - this.lastRangedTime > 420) {
            this.currentSprite = rightFacing;
            this.fighter.setDirection(0);
         }
         else if (System.currentTimeMillis() - this.lastPunchTime > 150 &&
                  System.currentTimeMillis() - lastRangedTime > 420) {
            setCurrentSprite(3);
         }
      }

      else if (option == 1) {
         if (System.currentTimeMillis() - this.lastPunchTime > 450 &&
             System.currentTimeMillis() - this.lastRangedTime > 420) {
            this.currentSprite = leftFacing;
            this.fighter.setDirection(1);
         }

         else if (System.currentTimeMillis() - this.lastPunchTime > 150 &&
                  System.currentTimeMillis() - this.lastRangedTime > 420) {
            setCurrentSprite(5);
         }
      }

      else if (option == 2) {
         this.currentSprite = rightPunchWindup;
         this.lastWindupTime = System.currentTimeMillis();
         this.lastPunchTime = System.currentTimeMillis(); 
      }

      else if (option == 3) {
         this.currentSprite = rightPunching;
      }
      
      else if (option == 4) {
         this.currentSprite = leftPunchWindup;
         this.lastWindupTime = System.currentTimeMillis();
         this.lastPunchTime = System.currentTimeMillis();
      }

      else if (option == 5) {
         this.currentSprite = leftPunching;
      }

      else if (option == 6) {
         this.currentSprite = rightRanged;
         this.lastRangedTime = System.currentTimeMillis();
      }

      else if (option == 7) {
         this.currentSprite = leftRanged;
         this.lastRangedTime = System.currentTimeMillis();
      }
   }

   public BufferedImage getCurrentSprite() {
      return this.currentSprite;
   }



}


