import greenfoot.Actor;
import greenfoot.GreenfootImage;
import java.awt.Color;

public class Overlay extends Actor
{
    private GreenfootImage img;
    private long startTime;
    private long time;
    private long musicTime;
    private int size;
    private int level;
    private boolean started;
    private static final int musicLength = 120000;

    public Overlay(int width, int height)
    {
        this.level = 1;
        this.img = new GreenfootImage(width, height);
        setImage(this.img);
        paint();
        this.started = false;
        this.time = 0L;
        this.musicTime = 0L;
    }

    public void act(int size)
    {
        if (!this.started)
        {
            this.started = true;

            this.startTime = System.currentTimeMillis();
        }
        this.size = size;
        paint();
        this.time = System.currentTimeMillis();
        if (this.time >= this.musicTime + 120000L)
        {
            this.musicTime = System.currentTimeMillis();
        }
    }

    public void nextLevel()
    {
        this.level += 1;
    }

    private void paint()
    {
        long currentTime = this.time - this.startTime;
        this.img.clear();
        this.img.setColor(Color.BLACK);
        this.img.drawString("Elapsed Time: " + currentTime / 1000L / 60L + ":" + (currentTime / 1000L % 60L < 10L ? "0" + currentTime / 1000L % 60L : Long.valueOf(currentTime / 1000L % 60L)), 1, 12);
        this.img.drawString("Level: " + this.level, 1, 24);
        this.img.setColor(Color.GREEN);
        this.img.fillRect(0, this.img.getHeight() - 10, (int)(this.size / 100.0D * this.img.getWidth()), 10);
        this.img.setColor(Color.YELLOW);
        this.img.fillRect((int)(this.size / 100.0D * this.img.getWidth()), this.img.getHeight() - 10, this.img.getWidth(), 10);
    }
}
