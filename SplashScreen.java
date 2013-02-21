import greenfoot.Actor;
import greenfoot.Greenfoot;
import greenfoot.World;

public class SplashScreen extends Actor
{
    private boolean anywhere;

    public SplashScreen(int num, boolean anywhere)
    {
        setImage("images/splash" + num + ".png");
        this.anywhere = anywhere;
    }

    public void act()
    {
        if (this.anywhere)
        {
            if (Greenfoot.mousePressed(null))
            {
                getWorld().removeObject(this);
            }

        }
        else if (Greenfoot.mousePressed(this))
        {
            getWorld().removeObject(this);
        }
    }
}
