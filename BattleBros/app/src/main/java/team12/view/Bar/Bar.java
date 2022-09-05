package team12.view.Bar;

import javax.swing.JPanel;
import team12.control.BarController;
import java.awt.Color;
import java.awt.Graphics;

public class Bar extends JPanel {
    
    private BarController controller;
    private int barSize;
    private int barValue;
    private Color emptyBar;
    private Color fullBar;

    public Bar(BarController c, int type) {
        
        this.controller = c;

        // 0 is for Health
        if(type == 0) {
            barSize = 200;
            barValue = 200;
            emptyBar = Color.RED;
            fullBar = Color.GREEN;
        }

        // 1 is for Shield
        else if(type == 1) {
            barSize = 160;
            barValue = 160;
            emptyBar = Color.BLUE;
            fullBar = Color.WHITE;
        }
    }

    protected void paintComponent(Graphics g){
        super.paintComponent(g);
        g.drawRect(0, 0, this.barSize, 20);
        g.setColor(this.emptyBar);
        g.fillRect(0, 0, this.barSize, 20);

        g.drawRect(0, 0, this.controller.getValue(), 20);
        g.setColor(this.fullBar);
        g.fillRect(0, 0, this.controller.getValue(), 20);
    }

    public BarController getController() {
        return this.controller;
    }

    public int barValue() {
        return this.barValue;
    }
}
