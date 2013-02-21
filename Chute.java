import greenfoot.GreenfootImage;
import java.util.List;

public class Chute extends Item
{
    private int type;
    private int height;
    private int width;

    public Chute(int type)
    {
        this.type = type;
        setImage("images/chute" + type + ".png");
        this.height = getImage().getHeight();
        this.width = getImage().getWidth();
    }

    public void act()
    {
        int y = getY();
        int minHeight = y + this.height / 2;
        List objs = getObjectsAtOffset(-this.width / 2, this.height / 2, Platform.class);
        for (Platform plat : objs)
        {
            if (plat.getTopY() < minHeight)
            {
                minHeight = plat.getTopY();
            }
        }
        List objs2 = getObjectsAtOffset(this.width / 2, this.height / 2, Platform.class);
        for (Platform plat : objs2)
        {
            if (plat.getTopY() < minHeight)
            {
                minHeight = plat.getTopY();
            }
        }
        y = minHeight - this.height / 2;
        int x = getX();
        List edges = getIntersectingObjects(Edge.class);
        for (Edge edge : edges)
        {
            x = edge.getX();
        }
        setLocation(x, y);
    }

    public int getType()
    {
        return this.type;
    }
}
