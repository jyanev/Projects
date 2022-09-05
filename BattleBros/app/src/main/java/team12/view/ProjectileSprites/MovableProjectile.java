package team12.view.ProjectileSprites;

import team12.model.Projectiles.*;
import javax.swing.*;
import java.awt.Component;
import team12.model.tools.*;
import java.awt.Graphics;


public class MovableProjectile extends JLabel implements Icon, ObjectObserver {

   private Projectile projectile;
   private ProjectileSprite pSprite;
   private int x;
   private int y;
   private int width;
   private int height;


   public MovableProjectile(Projectile p, int width, int height) {

      this.projectile = p;
      this.width = width;
      this.height = height;
      this.x = p.getX();
      this.y = p.getY();

      // pType = 0: fireball
      if (p.getProjectileType() == 0) {
         this.pSprite = projectile.getProjectileSprite();
         super.setBounds(0, 0, 900, 507);
         this.setIcon(this);
      }
      
      // pType = 1: grenade
      else if (p.getProjectileType() == 1) {
         this.pSprite = projectile.getProjectileSprite();
         super.setBounds(0, 0, 900, 507);
         this.setIcon(this);
         
      }
   }

   public Projectile getProjectile() {
      return this.projectile;
   }

   @Override
   public int getIconWidth() {
      return this.width;
   }

   @Override
   public int getIconHeight() {
      return  this.height;
   }

   @Override
   public void update(int x, int y) {
      this.x = x;
      this.y = y;
      this.revalidate();
      this.repaint();
   }

   @Override
   public void paintIcon(Component c, Graphics g, int x, int y) {
      g.drawImage(pSprite.getCurrentSprite(), x + this.x, y + this.y, null);
   }

   

}

