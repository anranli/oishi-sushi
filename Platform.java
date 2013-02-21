import greenfoot.Actor;
import greenfoot.GreenfootImage;

public class Platform extends Actor
{
    public Platform()
    {
        setImage("images/platform.png");
    }

    public void act()
    {
    }

    public int getTopY()
    {
        return super.getY() - getImage().getHeight() / 2;
    }
}
