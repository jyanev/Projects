package team12.view.Arena;

import javax.swing.JLabel;

public class Countdown extends JLabel {

    private int count;

    public Countdown() {
        this.count = 3;
    }

    //The count starts at 3 and will count down from 3 to 0
    public void countDown() {
        for(int i = 4; i >= 1; i--) {
            count--;
            System.out.println(count);
        }
    }

}
