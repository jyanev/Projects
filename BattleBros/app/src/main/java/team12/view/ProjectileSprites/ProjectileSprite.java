package team12.view.ProjectileSprites;

import javax.swing.*;
import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.awt.Graphics;
import java.io.IOException;
import java.net.URL;


public class ProjectileSprite {

   private BufferedImage currentSprite;
   private BufferedImage rightFacing;
   private BufferedImage leftFacing;
   private BufferedImage explosion;


   public ProjectileSprite(int pType) {

      URL rightFacingURL;
      URL leftFacingURL;
      URL explosionURL;

      if (pType == 0) {
         rightFacingURL = this.getClass().getClassLoader().getResource("fireball.png");
         leftFacingURL = this.getClass().getClassLoader().getResource("fireball inverted.png");
         explosionURL = this.getClass().getClassLoader().getResource("explosion.png");
      }

      else  {
         rightFacingURL = this.getClass().getClassLoader().getResource("grenade.png");
         leftFacingURL = this.getClass().getClassLoader().getResource("grenade inverted.png");
         explosionURL = this.getClass().getClassLoader().getResource("explosion.png");
      }

      try {
         this.rightFacing = ImageIO.read(rightFacingURL);
         this.leftFacing = ImageIO.read(leftFacingURL);
         if (pType == 1) {
            this.explosion = ImageIO.read(explosionURL);
         }
      }

      catch (IOException e) {
         System.out.println(e.getMessage());
      }
   
   }

   public BufferedImage getCurrentSprite() {
      return this.currentSprite;
   }

   public void setRightFacing() {
      this.currentSprite = this.rightFacing;
   }

   public void setLeftFacing() {
      this.currentSprite = this.leftFacing;
   }

   public void setExplosion() {
      this.currentSprite = this.explosion;
   }


}


