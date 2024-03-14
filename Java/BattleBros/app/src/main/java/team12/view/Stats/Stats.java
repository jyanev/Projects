package team12.view.Stats;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

public class Stats {
    
    private String fileName;
    private int wins;
    private int losses;
    private int minutes;
    private int seconds;

    public Stats(String fileName)
    {
        this.fileName = fileName;
    }

    public void readStats() throws IOException 
    {
        BufferedReader br = new BufferedReader(new FileReader(this.fileName));
        String line;
        while((line = br.readLine()) != null) {
            String[] parts = line.split(",");
            this.wins = Integer.parseInt(parts[0]);
            this.losses = Integer.parseInt(parts[1]);
            this.minutes = Integer.parseInt(parts[2]);
            this.seconds = Integer.parseInt(parts[3]);
        }
    }

    public void writeStats(int w, int l, int m, int s) throws IOException {
        try {
            FileWriter writer = new FileWriter(fileName);         
            writer.append(Integer.toString(w));
            writer.append(',');
            writer.append(Integer.toString(l));
            writer.append(',');
            writer.append(Integer.toString(m));
            writer.append(',');
            writer.append(Integer.toString(s));
            writer.flush();
            writer.close();
        }

        catch(Exception e) {
    
        }
    }

    public int getWins() {
        return this.wins;
    }

    public int getLosses() {
        return this.losses;
    }

    public int getMinutes() {
        return this.minutes;
    }

    public int getSeconds() {
        return this.seconds;
    }
}
