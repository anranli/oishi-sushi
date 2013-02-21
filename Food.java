import greenfoot.GreenfootImage;
import greenfoot.World;
import java.util.Iterator;
import java.util.List;

public class Food extends GifActor
{
    private static final double speed = 1.3D;
    private static final double gravity = 1.5D;
    private static final int fadeSpeed = 2;
    private static final int lifeSpan = 2500;
    private boolean goingRight;
    private double x;
    private double y;
    private int worldWidth;
    private int worldHeight;
    private int width;
    private int height;
    private boolean good;
    private boolean climbing;
    private static final int numGood = 4;
    private static final int numBad = 4;
    private int currentImage;
    private int opacity;
    private int timer;

    public Food(boolean good)
    {
        this.good = good;
        this.goingRight = false;
        this.climbing = false;
        this.currentImage = ((int)(Math.random() * (good ? 4 : 4)));
        this.opacity = 255;
        this.timer = 0;
    }

    public void act()
    {
        super.act();
        this.timer += 1;

        if (!onPlatform()) {
            if (onLadder())
            {
                this.y -= 1.3D;
            }
            else if (onChute())
            {
                this.y += 1.5D;
                this.climbing = false;
            }
            else if (nextToPlatform()) {
                this.climbing = false;
                this.goingRight = (!this.goingRight);
                setImage("images/" + (this.good ? "good" : "bad") + (this.goingRight ? "right" : "left") + this.currentImage + ".gif");

                if (this.goingRight)
                    this.x += 1.3D;
                else
                    this.x -= 1.3D;
            }
            else {
                this.climbing = false;
                this.y += 1.5D;
            }

        }
        else if ((onLadder()) && (!this.climbing))
        {
            this.y -= 1.3D;
            if (onPlatform())
            {
                this.climbing = (!this.climbing);
            }
        }
        else if ((!inBounds()) || (nextToWall())) {
            this.goingRight = (!this.goingRight);
            setImage("images/" + (this.good ? "good" : "bad") + (this.goingRight ? "right" : "left") + this.currentImage + ".gif");

            if (this.goingRight)
                this.x += 1.3D;
            else {
                this.x -= 1.3D;
            }
            this.climbing = false;
        }
        else
        {
            this.climbing = false;
            if (this.goingRight)
                this.x += 1.3D;
            else {
                this.x -= 1.3D;
            }

        }

        move();
        fade();
    }

    private boolean inBounds()
    {
        return (this.x - this.width / 2 >= 0.0D) && (this.y - this.height / 2 >= 0.0D) && (this.x + this.width / 2 < this.worldWidth) && (this.y + this.height / 2 < this.worldHeight);
    }

    private boolean onPlatform()
    {
        List objs = getObjectsAtOffset(0, this.height / 2, Platform.class);
        if (objs.size() > 0)
        {
            double minHeight = this.y + this.height / 2;
            for (Platform plat : objs)
            {
                if (plat.getTopY() < minHeight)
                {
                    minHeight = plat.getTopY();
                }
            }
            this.y = (minHeight - this.height / 2);
            return true;
        }
        return false;
    }

    private boolean nextToPlatform()
    {
        return (getObjectsAtOffset(2, this.height / 2, Platform.class).size() > 0) || (getObjectsAtOffset(-2, this.height / 2, Platform.class).size() > 0);
    }

    private boolean nextToWall()
    {
        for (Iterator i$ = getObjectsAtOffset(this.goingRight ? this.width / 2 : -this.width / 2, 0, Wall.class).iterator(); i$.hasNext(); ) { Object obj = i$.next();

            Wall wall = (Wall)obj;
            if (((this.good) && (wall.getType() == 0)) || ((!this.good) && (wall.getType() == 1)) || (wall.getType() == 2))
            {
                return true;
            }
        }
        return false;
    }

    private boolean onChute()
    {
        for (Iterator i$ = getObjectsAtOffset(0, this.height / 2, Chute.class).iterator(); i$.hasNext(); ) { Object obj = i$.next();

            Chute chute = (Chute)obj;
            if (((this.good) && (chute.getType() == 0)) || ((!this.good) && (chute.getType() == 1)) || (chute.getType() == 2))
            {
                return true;
            }
        }
        return false;
    }

    private boolean onLadder()
    {
        for (Iterator i$ = getObjectsAtOffset(0, this.height / 2, Ladder.class).iterator(); i$.hasNext(); ) { Object obj = i$.next();

            Ladder ladder = (Ladder)obj;
            if ((ladder.isClimbable()) && (((this.good) && (ladder.getType() == 0)) || ((!this.good) && (ladder.getType() == 1)) || (ladder.getType() == 2)))
            {
                this.x = ladder.getX();
                return true;
            }
        }
        return false;
    }

    private void fade()
    {
        if (this.timer > 2500)
        {
            this.opacity -= 2;
        }
        if (this.opacity >= 0)
        {
            getImage().setTransparency(this.opacity);
        }
        else
        {
            getWorld().removeObject(this);
        }
    }

    protected void addedToWorld(World world)
    {
        super.addedToWorld(world);
        this.x = getX();
        this.y = getY();
        this.worldWidth = world.getWidth();
        this.worldHeight = world.getHeight();
        setImage("images/" + (this.good ? "good" : "bad") + (this.goingRight ? "right" : "left") + this.currentImage + ".gif");

        this.width = getMaxWidth();
        this.height = getMaxHeight();
    }

    public boolean isGood()
    {
        return this.good;
    }

    private void move()
    {
        if (this.x < 0.0D)
        {
            this.x = 0.0D;
        }
        if (this.x > this.worldWidth)
        {
            this.x = (this.worldWidth - 1);
        }
        if (this.y < 0.0D)
        {
            this.y = 0.0D;
        }
        if (this.y > this.worldHeight)
        {
            this.y = (this.worldHeight - 1);
        }
        setLocation((int)this.x, (int)this.y);
    }
}
