package team12.view.Arena;

import team12.model.GameCharacters.*;
import team12.model.tools.*;
import javax.swing.*;
import javax.imageio.ImageIO;
import java.awt.Component;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.net.URL;
import team12.control.SpriteDisplayController;

public class MovableCharacter extends JLabel implements Icon, ObjectObserver {	
	private Fighter gameCharacter;
	private BufferedImage sprite;
   private SpriteDisplayController sdController;
	private int x;
	private int y;
	private int width;
	private int height;

	public MovableCharacter(Fighter c, int width, int height) throws IOException {
		this.gameCharacter = c;
		if (c.getFighterType() == 0) {
			
         this.sdController = new SpriteDisplayController(c, 0);
         this.x = c.getX();
			this.y = c.getY();
		}
		else {
			
         this.sdController = new SpriteDisplayController(c, 1);
         this.x = c.getX();
			this.y = c.getY();
		}
		
			this.width = width;
			this.height = height;
			super.setBounds(0, 0, 900, 507);
			super.setIcon(this);
	}

   public Fighter getGameCharacter() {
		return this.gameCharacter;
	}

	@Override
	public int getIconWidth() {
		return this.width;
	}

	@Override
	public int getIconHeight() {
		return this.height;
	}

   	//updates the image of the game character on the screen
	@Override
	public void update(int x, int y) {
		this.x = x;
		this.y = y;
		this.revalidate();
		this.repaint();
	}

	@Override
	public void paintIcon(Component c, Graphics g, int x, int y) {	
		g.drawImage(sdController.getCurrentSprite(), x + this.x, y + this.y, null);
	}

	public SpriteDisplayController getDisplay()
	{
		return this.sdController;
	}
}
