import greenfoot.Actor;
import greenfoot.Greenfoot;
import greenfoot.World;

public class FastForwardButton extends Actor
{
    private final int normalSpeed;
    private final int fastSpeed;

    public FastForwardButton(int normalSpeed, int fastSpeed)
    {
        this.normalSpeed = normalSpeed;
        this.fastSpeed = fastSpeed;
        setImage("images/fastforward.png");
    }

    public void act()
    {
        if (Greenfoot.mousePressed(this))
        {
            Greenfoot.setSpeed(this.fastSpeed);
            getWorld().addObject(new FastForwardButtonPressed(this.normalSpeed, this.fastSpeed), getX(), getY());
            getWorld().removeObject(this);
        }
    }
}
