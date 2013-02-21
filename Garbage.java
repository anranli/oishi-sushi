import greenfoot.Actor;
import greenfoot.World;

public class Garbage extends Actor
{
    public Garbage()
    {
        setImage("images/garbage.png");
    }

    public void act()
    {
        getWorld().removeObjects(getIntersectingObjects(Food.class));
    }
}
