package team12.view.Bar;

import javax.swing.JPanel;

import team12.model.GameCharacters.Fighter;

import java.awt.Color;
import java.awt.Graphics;

public class Shield extends JPanel {

    Fighter character;
    Color myColor;

    public Shield(Fighter fighter) {
        character = fighter;
        this.setVisible(false);
        myColor = new Color(0, 60, 180, 120);
    }

    protected void paintComponent(Graphics g)
    {
        g.setColor(myColor);
        g.fillOval(character.getX()-43, character.getY()+18, 80, 80);     
    }
}
