import greenfoot.Greenfoot;
import greenfoot.World;
import java.util.List;

public class Fob extends GifActor
{
    private int size;
    private boolean right;
    private int level;

    public Fob(boolean right, int level)
    {
        this.right = right;
        this.level = level;
    }

    public void act()
    {
        super.act();
        if (this.size >= 100)
        {
            ((Level)getWorld()).nextLevel();
        }
        else
        {
            List objs = getIntersectingObjects(Food.class);

            for (Food obj : objs)
            {
                if (obj.isGood())
                {
                    this.size += 5;
                    Greenfoot.playSound("sounds/nom.wav");
                }
                else
                {
                    this.size -= 5;
                    Greenfoot.playSound("sounds/bleh.wav");
                }
                getWorld().removeObject(obj);
            }
            if (this.size < 0)
            {
                this.size = 0;
            }
        }
    }

    public int getSize()
    {
        return this.size;
    }

    protected void addedToWorld(World world)
    {
        setImage("images/fob" + (this.right ? "right" : "left") + this.level + ".gif");
    }
}
