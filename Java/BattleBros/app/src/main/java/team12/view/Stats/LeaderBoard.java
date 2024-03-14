package team12.view.Stats;
import java.awt.Graphics;
import javax.swing.JPanel;
import java.awt.Color;

public class LeaderBoard extends JPanel {
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.drawRect(0, 0, 314, 414);
        g.setColor(Color.WHITE);
        g.fillRect(0, 0, 314, 414);

        g.drawRect(7, 7, 300, 400);
        g.setColor(Color.ORANGE);
        g.fillRect(7, 7, 300, 400);
    }
}

