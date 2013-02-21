import greenfoot.Actor;
import greenfoot.GreenfootImage;

public class Edge extends Actor
{
    private boolean right;

    public Edge(boolean right)
    {
        setImage(new GreenfootImage(1, 20));
        this.right = right;
    }

    public boolean getRight()
    {
        return this.right;
    }
}
