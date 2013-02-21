import greenfoot.GreenfootImage;
import java.util.List;

public class Wall extends Item
{
    private int type;
    private int height;

    public Wall(int type)
    {
        this.type = type;
        setImage("images/wall" + type + ".png");
        this.height = getImage().getHeight();
    }

    public void act()
    {
        int y = getY();
        List objs = getIntersectingObjects(Platform.class);
        int minHeight = y + this.height / 2;
        for (Platform plat : objs)
        {
            if (plat.getTopY() < minHeight)
            {
                minHeight = plat.getTopY();
            }
        }
        y = minHeight - this.height / 2;
        int x = getX();
        setLocation(x, y);
    }

    public int getType()
    {
        return this.type;
    }
}
