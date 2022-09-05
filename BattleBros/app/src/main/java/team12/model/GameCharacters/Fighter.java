package team12.model.GameCharacters;
import java.lang.Math;
import team12.control.BarController;
import team12.view.Bar.*;
import team12.model.tools.*;
import java.util.ArrayList;
import team12.model.Projectiles.*;

public class Fighter {

	private int fighterType;

	protected Bar healthBar;
	private int health;
	protected Bar shieldBar;
	private int shieldHealth;

	private int walkSpeed;
	private int jumpHeight;

	private int attackType;
	private Projectile projectileType;

	protected boolean attacking;
	protected boolean midair;
   	private int direction;

	protected int X;
	protected int Y;

   private ArrayList<Integer> y_velocities = new ArrayList<Integer>();

   protected ArrayList<ObjectObserver> observers;

	public Fighter(int fighterType, int health, int shieldHealth,
      int walkSpeed, int jumpHeight, int attackType, Projectile projectileType, int x, int y) {
		this.fighterType = fighterType;
		this.healthBar = new Bar(new BarController(0), 0);
		this.health = health;
		this.shieldBar = new Bar(new BarController(1), 1);
		this.shieldHealth = shieldHealth;
		this.walkSpeed = walkSpeed;
		this.jumpHeight = jumpHeight;
		this.attackType = attackType;
		this.projectileType = projectileType;
		this.attacking = false;
		this.midair = false;
		this.X = x;
		this.Y = y;
		this.y_velocities = new ArrayList<Integer>();
		this.observers = new ArrayList<ObjectObserver>();
		System.out.println("GameCharacters constructor");
	}

	public boolean isAttacking() {
		return attacking;
	}

	public void setAttack(boolean val) {
		this.attacking = val;
	}

   public boolean checkHit(Fighter c, boolean downSlam) {
      //if character is within a certain range of another character when attacking, returns true
      //else returns false
      if (downSlam) {
         if (Math.abs(Math.sqrt(Math.pow((this.X - c.getX()), 2) + Math.pow((this.Y - c.getY()), 2))) < 55) {
            
            return true;
         }
      }

      else {
         if (Math.abs(Math.sqrt(Math.pow((this.X - c.getX()), 2) + Math.pow((this.Y - c.getY()), 2))) < 105) {
            
            return true;
         }
      }

      return false;
   }

   public void setDirection(int value) {
      this.direction = value;
   }

   public int getDirection() {
      return this.direction;
   }

   public Bar getHealthBar() {
      return this.healthBar;
   }

   public Bar getShieldBar() {
      return this.shieldBar;
   }

	public boolean isMidair() {
		return midair;
	}

	public void setMidair(boolean val) {
   	this.midair = val;
	}

	public int getX() {
		return this.X;
	}

	public int getY() {
		return this.Y;
	}

   public void setX(int x) {
      this.X = x;
   }

   public void setY(int y) {
      this.Y = y;
   }

   public void meleeAttack(int direction){
		notifyObservers();
   }

   public void rangedAttack(int direction){
		notifyObservers();
   }

   public void downSlam(){
      setY(getY() + 40);
		notifyObservers();
   }

   public void shield(){
	  notifyObservers();
   }

   public void walk(int velocity) {
		if ((this.X >= 10 && velocity < 0) || (this.X <= 825 && velocity > 0))
		{
			int x = getX() + velocity;
			setX(x);
			notifyObservers();
		}
	}

   public void jump(int velocity) {
		int y = getY() + velocity;
      	setY(y);
      	notifyObservers();
	}

   public ArrayList<Integer> calculateJumpTrajectory() {
		
		//calculates jump trajectory by storing changes in Y velocity in an ArrayList
		y_velocities.clear();

		int tempY = this.Y;
		int currentVelocity = -20;
		tempY += currentVelocity;
		y_velocities.add(currentVelocity);
		currentVelocity += 2;

		while (tempY < 180) {
			tempY += currentVelocity;
			y_velocities.add(currentVelocity);
			currentVelocity += 2;
		}

		return y_velocities;
	}

   public void notifyObservers() {
		for (ObjectObserver o : observers) {
			o.update(getX(), getY());
		}
	}

	public void addObserver(ObjectObserver o) {
		observers.add(o);
	} 

	public int getFighterType(){
		return this.fighterType;
	}
   
   public Projectile getProjectileType() {
      return this.projectileType;
   }

}
