package team12.view.tools;
import javax.swing.*;
import java.awt.Image;
import java.awt.Dimension;
import java.awt.Graphics;

public class BackgroundPanel extends JPanel
{
    private Image background;
 
    public BackgroundPanel(Image background){
        this.background = background;
        setLayout(null);
    }
 
    @Override
    protected void paintComponent(Graphics g){
        super.paintComponent(g);
 
        g.drawImage(background, 0, 0, null);
    }
 
    @Override
    public Dimension getPreferredSize(){
        return new Dimension(background.getWidth(this), background.getHeight(this));
    }
}
