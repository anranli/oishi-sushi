import greenfoot.Actor;
import greenfoot.Greenfoot;
import greenfoot.MouseInfo;
import greenfoot.World;

public class Level extends World
{
    private static final int respawnDelay = 40;
    private int timer;
    private int fridgeX;
    private int fridgeY;
    private Overlay overlay;
    private Fob fob;
    private int level;
    private static final int normalSpeed = 50;
    private static final int fastSpeed = 60;

    public Level()
    {
        super(800, 600, 1);

        Greenfoot.setSpeed(50);

        setBackground("images/background1.png");

        setPaintOrder(new Class[] { SplashScreen.class, Item.class, FastForwardButton.class, FastForwardButtonPressed.class, Overlay.class, Fob.class, Garbage.class, Fridge.class, Platform.class, Actor.class, World.class });

        this.timer = 0;
        this.level = 0;

        this.overlay = new Overlay(getWidth(), getHeight());
        addObject(this.overlay, getWidth() / 2, getHeight() / 2);

        nextLevel();
    }

    public void act()
    {
        if ((this.level == 6) && (Greenfoot.mousePressed(null)))
        {
            setBackground("images/credits.png");
        }

        if ((this.level != 6) && (this.timer > 40))
        {
            addObject(new Food(Math.random() > 0.5D), this.fridgeX, this.fridgeY);
            this.timer = 0;
        }
        this.timer += 1;
        if (this.level != 6)
            this.overlay.act(this.fob.getSize());
        if ((Greenfoot.mouseDragged(null)) && (Greenfoot.getMouseInfo().getActor() != null) && (correctClass(Greenfoot.getMouseInfo().getActor())))
        {
            Greenfoot.getMouseInfo().getActor().setLocation(Greenfoot.getMouseInfo().getX(), Greenfoot.getMouseInfo().getY());
        }
    }

    private boolean correctClass(Actor act)
    {
        return (act.getClass() == Wall.class) || (act.getClass() == Chute.class) || (act.getClass() == Ladder.class);
    }

    public void nextLevel()
    {
        Greenfoot.setSpeed(50);
        removeObjects(getObjects(Object.class));
        addObject(this.overlay, getWidth() / 2, getHeight() / 2);
        this.level += 1;
        switch (this.level)
        {
            case 1:
                addObject(new SplashScreen(5, false), getWidth() / 2, getHeight() / 2);
                addObject(new SplashScreen(0, false), getWidth() / 2, getHeight() / 2);

                addObject(new Wall(2), 700, 290);
                addObject(new Wall(2), 700, 290);
                addObject(new Wall(2), 700, 290);
                addObject(new Wall(2), 700, 290);
                addObject(new Wall(1), 725, 290);
                addObject(new Wall(0), 750, 290);
                addObject(new Chute(2), 775, 290);
                addObject(new Chute(2), 775, 290);
                addObject(new Chute(2), 775, 290);
                addObject(new Chute(2), 775, 290);
                addObject(new Chute(2), 775, 290);

                this.fridgeX = 750;
                this.fridgeY = 50;
                addObject(new Fridge(), this.fridgeX, this.fridgeY);

                addObject(new Garbage(), 750, 550);
                addObject(new Garbage(), 50, 350);

                addObject(new FastForwardButton(50, 60), 35, 50);

                this.fob = new Fob(true, this.level - 1);
                addObject(this.fob, 50, 540);

                for (int i = 200; i < 850; i += 50)
                {
                    addObject(new Platform(), i, 90);
                }
                addObject(new Edge(false), 175, 90);
                for (int i = 0; i < 400; i += 50)
                {
                    addObject(new Platform(), i, 190);
                }
                addObject(new Edge(true), 375, 190);
                for (int i = 450; i < 850; i += 50)
                {
                    addObject(new Platform(), i, 190);
                }
                addObject(new Edge(false), 425, 190);
                for (int i = 200; i < 650; i += 50)
                {
                    addObject(new Platform(), i, 290);
                }
                addObject(new Edge(false), 175, 290);
                addObject(new Edge(true), 625, 290);
                for (int i = 0; i < 250; i += 50)
                {
                    addObject(new Platform(), i, 390);
                }
                addObject(new Edge(true), 225, 390);
                for (int i = 300; i < 550; i += 50)
                {
                    addObject(new Platform(), i, 390);
                }
                addObject(new Edge(false), 275, 390);
                addObject(new Edge(true), 525, 390);
                for (int i = 600; i < 850; i += 50)
                {
                    addObject(new Platform(), i, 390);
                }
                addObject(new Edge(false), 575, 390);
                for (int i = 0; i < 400; i += 50)
                {
                    addObject(new Platform(), i, 490);
                }
                addObject(new Edge(true), 375, 490);
                for (int i = 450; i < 850; i += 50)
                {
                    addObject(new Platform(), i, 490);
                }
                addObject(new Edge(false), 425, 490);
                for (int i = 0; i < 850; i += 50)
                {
                    addObject(new Platform(), i, 590);
                }
                break;
            case 2:
                addObject(new Chute(2), 725, 90);
                addObject(new Chute(2), 725, 90);
                addObject(new Chute(2), 725, 90);
                addObject(new Ladder(2), 700, 90);
                addObject(new Ladder(2), 700, 90);
                addObject(new Wall(1), 750, 90);

                addObject(new Garbage(), 460, 250);
                addObject(new Garbage(), 740, 350);
                addObject(new Garbage(), 460, 450);

                this.fridgeX = 400;
                this.fridgeY = 50;
                addObject(new Fridge(), this.fridgeX, this.fridgeY);

                this.overlay.nextLevel();

                this.fob = new Fob(false, this.level - 1);
                addObject(this.fob, 750, 240);

                addObject(new SplashScreen(1, true), getWidth() / 2, getHeight() / 2);

                addObject(new FastForwardButton(50, 60), 35, 50);

                for (int i = 100; i < 550; i += 50)
                {
                    addObject(new Platform(), i, 90);
                }
                addObject(new Edge(true), 525, 90);
                addObject(new Edge(false), 75, 90);
                for (int i = 100; i < 550; i += 50)
                {
                    addObject(new Platform(), i, 190);
                }
                addObject(new Edge(false), 75, 190);
                addObject(new Edge(true), 525, 190);
                for (int i = 0; i < 200; i += 50)
                {
                    addObject(new Platform(), i, 290);
                }
                addObject(new Edge(true), 175, 290);
                for (int i = 250; i < 400; i += 50)
                {
                    addObject(new Platform(), i, 290);
                }
                addObject(new Edge(false), 225, 290);
                addObject(new Edge(true), 375, 290);
                for (int i = 450; i < 600; i += 50)
                {
                    addObject(new Platform(), i, 290);
                }
                addObject(new Edge(false), 425, 290);
                addObject(new Edge(true), 575, 290);
                for (int i = 650; i < 850; i += 50)
                {
                    addObject(new Platform(), i, 290);
                }
                addObject(new Edge(false), 625, 290);
                for (int i = 0; i < 600; i += 50)
                {
                    addObject(new Platform(), i, 390);
                }
                addObject(new Edge(true), 575, 390);
                for (int i = 650; i < 850; i += 50)
                {
                    addObject(new Platform(), i, 390);
                }
                addObject(new Edge(false), 625, 390);
                for (int i = 450; i < 850; i += 50)
                {
                    addObject(new Platform(), i, 490);
                }
                addObject(new Edge(false), 425, 490);
                for (int i = 0; i < 850; i += 50)
                {
                    addObject(new Platform(), i, 590);
                }

                break;
            case 3:
                addObject(new Chute(2), 725, 90);
                addObject(new Chute(2), 725, 90);
                addObject(new Chute(1), 700, 90);
                addObject(new Ladder(2), 775, 90);
                addObject(new Ladder(2), 775, 90);
                addObject(new Wall(2), 750, 90);
                addObject(new Wall(2), 750, 90);
                this.overlay.nextLevel();

                addObject(new Garbage(), 500, 450);
                addObject(new Garbage(), 650, 550);
                addObject(new Garbage(), 300, 550);
                addObject(new Garbage(), 175, 550);

                this.fridgeX = 750;
                this.fridgeY = 450;
                addObject(new Fridge(), this.fridgeX, this.fridgeY);

                this.fob = new Fob(true, this.level - 1);
                addObject(this.fob, 50, 440);

                addObject(new FastForwardButton(50, 60), 35, 50);
                for (int i = 150; i < 250; i += 50)
                {
                    addObject(new Platform(), i, 390);
                }
                addObject(new Edge(false), 125, 390);
                addObject(new Platform(), 201, 390);
                addObject(new Edge(true), 225, 390);
                for (int i = 600; i < 700; i += 50)
                {
                    addObject(new Platform(), i, 390);
                }
                addObject(new Edge(false), 575, 390);
                addObject(new Platform(), 651, 390);
                addObject(new Edge(true), 675, 390);
                for (int i = 0; i < 150; i += 50)
                {
                    addObject(new Platform(), i, 490);
                }
                addObject(new Edge(true), 125, 490);
                for (int i = 250; i < 400; i += 50)
                {
                    addObject(new Platform(), i, 490);
                }
                addObject(new Edge(false), 225, 490);
                addObject(new Platform(), 351, 490);
                addObject(new Edge(true), 375, 490);
                for (int i = 450; i < 550; i += 50)
                {
                    addObject(new Platform(), i, 490);
                }
                addObject(new Platform(), 549, 490);
                addObject(new Edge(false), 425, 490);
                addObject(new Edge(true), 574, 490);
                for (int i = 700; i < 850; i += 50)
                {
                    addObject(new Platform(), i, 490);
                }
                addObject(new Edge(false), 675, 490);
                addObject(new Edge(true), 825, 490);
                for (int i = 0; i < 850; i += 50)
                {
                    addObject(new Platform(), i, 590);
                }
                break;
            case 4:
                addObject(new Chute(2), 725, 90);
                addObject(new Ladder(1), 775, 90);
                addObject(new Ladder(2), 750, 90);
                addObject(new Wall(2), 700, 90);
                this.overlay.nextLevel();

                addObject(new Garbage(), 760, 450);
                addObject(new Garbage(), 40, 450);
                addObject(new Garbage(), 40, 550);

                this.fridgeX = 350;
                this.fridgeY = 450;
                addObject(new Fridge(), this.fridgeX, this.fridgeY);

                this.fob = new Fob(true, this.level - 1);
                addObject(this.fob, 750, 240);

                addObject(new FastForwardButton(50, 60), 35, 50);

                for (int i = 0; i < 200; i += 50)
                {
                    addObject(new Platform(), i, 490);
                }
                addObject(new Edge(true), 175, 490);
                for (int i = 300; i < 450; i += 50)
                {
                    addObject(new Platform(), i, 490);
                }
                addObject(new Edge(false), 275, 490);
                addObject(new Edge(true), 425, 490);
                for (int i = 550; i < 850; i += 50)
                {
                    addObject(new Platform(), i, 490);
                }
                addObject(new Edge(false), 525, 490);
                for (int i = 650; i < 850; i += 50)
                {
                    addObject(new Platform(), i, 390);
                }
                addObject(new Edge(false), 625, 390);
                for (int i = 750; i < 850; i += 50)
                {
                    addObject(new Platform(), i, 290);
                }
                addObject(new Edge(false), 725, 290);
                for (int i = 0; i < 850; i += 50)
                {
                    addObject(new Platform(), i, 590);
                }
                break;
            case 5:
                addObject(new Chute(2), 700, 90);
                addObject(new Ladder(1), 750, 90);
                addObject(new Ladder(2), 775, 90);
                addObject(new Ladder(2), 775, 90);
                addObject(new Wall(2), 725, 90);
                this.overlay.nextLevel();

                addObject(new Garbage(), 760, 550);

                this.fridgeX = 100;
                this.fridgeY = 450;
                addObject(new Fridge(), this.fridgeX, this.fridgeY);

                this.fob = new Fob(true, this.level - 1);
                addObject(this.fob, 100, 240);

                addObject(new FastForwardButton(50, 60), 35, 50);
                for (int i = 0; i < 200; i += 50)
                {
                    addObject(new Platform(), i, 490);
                }
                addObject(new Edge(true), 175, 490);
                for (int i = 450; i < 650; i += 50)
                {
                    addObject(new Platform(), i, 490);
                }
                addObject(new Edge(false), 425, 490);
                addObject(new Edge(true), 625, 490);
                for (int i = 650; i < 850; i += 50)
                {
                    addObject(new Platform(), i, 390);
                }
                addObject(new Edge(false), 625, 390);
                for (int i = 450; i < 650; i += 50)
                {
                    addObject(new Platform(), i, 290);
                }
                addObject(new Edge(false), 425, 290);
                addObject(new Platform(), 601, 290);
                addObject(new Edge(true), 625, 290);
                for (int i = 200; i < 450; i += 50)
                {
                    addObject(new Platform(), i, 190);
                }
                addObject(new Edge(false), 175, 190);
                addObject(new Platform(), 401, 190);
                addObject(new Edge(true), 425, 190);
                for (int i = 50; i < 200; i += 50)
                {
                    addObject(new Platform(), i, 290);
                }
                addObject(new Edge(false), 25, 20);
                addObject(new Edge(true), 175, 20);
                for (int i = 0; i < 850; i += 50)
                {
                    addObject(new Platform(), i, 590);
                }
                break;
            case 6:
                removeObjects(getObjects(Object.class));
                setBackground("images/win.png");
                break;
        }
    }
}
