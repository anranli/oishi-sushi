import greenfoot.Actor;
import greenfoot.Greenfoot;
import greenfoot.World;

public class FastForwardButtonPressed extends Actor
{
    private final int normalSpeed;
    private final int fastSpeed;

    public FastForwardButtonPressed(int normalSpeed, int fastSpeed)
    {
        this.normalSpeed = normalSpeed;
        this.fastSpeed = fastSpeed;
        setImage("images/fastforwardpressed.png");
    }

    public void act()
    {
        if (Greenfoot.mousePressed(this))
        {
            Greenfoot.setSpeed(this.normalSpeed);
            getWorld().addObject(new FastForwardButton(this.normalSpeed, this.fastSpeed), getX(), getY());
            getWorld().removeObject(this);
        }
    }
}
