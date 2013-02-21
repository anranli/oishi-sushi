import greenfoot.Actor;
import greenfoot.GreenfootImage;
import java.awt.AlphaComposite;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.awt.image.DataBufferInt;
import java.awt.image.WritableRaster;
import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public abstract class GifActor extends Actor
{
    private GreenfootImage[] images;
    private int[] delay;
    private int currentIndex;
    private long time;
    private String file;
    private boolean pause;

    public void setImage(String file)
    {
        this.file = file;
        this.pause = false;
        if (file.endsWith(".gif")) {
            loadImages();
            setImage(this.images[0]);
        }
        else {
            super.setImage(file);
        }
    }

    public List<GreenfootImage> getImages()
    {
        ArrayList images = new ArrayList(this.images.length);
        for (GreenfootImage image : this.images) {
            images.add(image);
        }
        return images;
    }

    public void pause()
    {
        this.pause = true;
    }

    public void resume()
    {
        this.pause = false;
    }

    public boolean isRunning()
    {
        return !this.pause;
    }

    public void act()
    {
        if ((this.time + this.delay[this.currentIndex] <= System.currentTimeMillis()) && (!this.pause))
            nextFrame();
    }

    private void nextFrame()
    {
        this.time = System.currentTimeMillis();
        this.currentIndex = ((this.currentIndex + 1) % this.images.length);
        setImage(this.images[this.currentIndex]);
    }

    private void loadImages()
    {
        GifActor.GifDecoder decode = new GifActor.GifDecoder(null);
        decode.read(this.file);
        int numFrames = decode.getFrameCount();
        if (numFrames > 0) {
            this.images = new GreenfootImage[numFrames];
            this.delay = new int[numFrames];
        }
        else {
            this.images = new GreenfootImage[1];
            this.images[0] = new GreenfootImage(1, 1);
        }

        for (int i = 0; i < numFrames; i++) {
            GreenfootImage image = new GreenfootImage(decode.getFrame(i).getWidth(), decode.getFrame(i).getHeight());
            BufferedImage frame = image.getAwtImage();
            Graphics2D g = (Graphics2D)frame.getGraphics();
            g.drawImage(decode.getFrame(i), null, 0, 0);
            this.delay[i] = decode.getDelay(i);
            this.images[i] = image;
        }
        this.time = System.currentTimeMillis();
    }

    public int getMaxHeight()
    {
        int maxHeight = 0;
        for (GreenfootImage img : this.images)
        {
            if (img.getHeight() > maxHeight)
            {
                maxHeight = img.getHeight();
            }
        }
        return maxHeight;
    }

    public int getMaxWidth()
    {
        int maxWidth = 0;
        for (GreenfootImage img : this.images)
        {
            if (img.getWidth() > maxWidth)
            {
                maxWidth = img.getWidth();
            }
        }
        return maxWidth; } 
        private class GifDecoder { public static final int STATUS_OK = 0;
            public static final int STATUS_FORMAT_ERROR = 1;
            public static final int STATUS_OPEN_ERROR = 2;
            private BufferedInputStream in;
            private int status;
            private int width;
            private int height;
            private boolean gctFlag;
            private int gctSize;
            private int loopCount;
            private int[] gct;
            private int[] lct;
            private int[] act;
            private int bgIndex;
            private int bgColor;
            private int lastBgColor;
            private int pixelAspect;
            private boolean lctFlag;
            private boolean interlace;
            private int lctSize;
            private int ix;
            private int iy;
            private int iw;
            private int ih;
            private Rectangle lastRect;
            private BufferedImage image;
            private BufferedImage lastImage;
            private byte[] block;
            private int blockSize;
            private int dispose;
            private int lastDispose;
            private boolean transparency;
            private int delay;
            private int transIndex;
            private static final int MaxStackSize = 4096;
            private short[] prefix;
            private byte[] suffix;
            private byte[] pixelStack;
            private byte[] pixels;
            private ArrayList<GifActor.GifDecoder.GifFrame> frames;
            private int frameCount;

            private GifDecoder() { this.loopCount = 1;

                this.block = new byte[256];

                this.blockSize = 0;

                this.dispose = 0;

                this.lastDispose = 0;

                this.transparency = false;

                this.delay = 0;
            }

            public int getDelay(int n)
            {
                this.delay = -1;
                if ((n >= 0) && (n < this.frameCount)) {
                    this.delay = ((GifActor.GifDecoder.GifFrame)this.frames.get(n)).delay;
                }
                return this.delay;
            }

            public int getFrameCount()
            {
                return this.frameCount;
            }

            public BufferedImage getImage()
            {
                return getFrame(0);
            }

            public int getLoopCount()
            {
                return this.loopCount;
            }

            protected void setPixels()
            {
                int[] dest = ((DataBufferInt)this.image.getRaster().getDataBuffer()).getData();

                if (this.lastDispose > 0) {
                    if (this.lastDispose == 3)
                    {
                        int n = this.frameCount - 2;
                        if (n > 0)
                            this.lastImage = getFrame(n - 1);
                        else {
                            this.lastImage = null;
                        }
                    }

                    if (this.lastImage != null) {
                        int[] prev = ((DataBufferInt)this.lastImage.getRaster().getDataBuffer()).getData();
                        System.arraycopy(prev, 0, dest, 0, this.width * this.height);

                        if (this.lastDispose == 2)
                        {
                            Graphics2D g = this.image.createGraphics();
                            Color c = null;
                            if (this.transparency)
                                c = new Color(0, 0, 0, 0);
                            else {
                                c = new Color(this.lastBgColor);
                            }
                            g.setColor(c);
                            g.setComposite(AlphaComposite.Src);
                            g.fill(this.lastRect);
                            g.dispose();
                        }
                    }

                }

                int pass = 1;
                int inc = 8;
                int iline = 0;
                for (int i = 0; i < this.ih; i++) {
                    int line = i;
                    if (this.interlace) {
                        if (iline >= this.ih) {
                            pass++;
                            switch (pass) {
                                case 2:
                                    iline = 4;
                                    break;
                                case 3:
                                    iline = 2;
                                    inc = 4;
                                    break;
                                case 4:
                                    iline = 1;
                                    inc = 2;
                            }
                        }
                        line = iline;
                        iline += inc;
                    }
                    line += this.iy;
                    if (line < this.height) {
                        int k = line * this.width;
                        int dx = k + this.ix;
                        int dlim = dx + this.iw;
                        if (k + this.width < dlim) {
                            dlim = k + this.width;
                        }
                        int sx = i * this.iw;
                        while (dx < dlim)
                        {
                            int index = this.pixels[(sx++)] & 0xFF;
                            int c = this.act[index];
                            if (c != 0) {
                                dest[dx] = c;
                            }
                            dx++;
                        }
                    }
                }
            }

            public BufferedImage getFrame(int n)
            {
                BufferedImage im = null;
                if ((n >= 0) && (n < this.frameCount)) {
                    im = ((GifActor.GifDecoder.GifFrame)this.frames.get(n)).image;
                }
                return im;
            }

            public Dimension getFrameSize()
            {
                return new Dimension(this.width, this.height);
            }

            public int read(BufferedInputStream is)
            {
                init();
                if (is != null) {
                    this.in = is;
                    readHeader();
                    if (!err()) {
                        readContents();
                        if (this.frameCount < 0)
                            this.status = 1;
                    }
                }
                else {
                    this.status = 2;
                }
                try {
                    is.close();
                } catch (IOException e) {
                }
                return this.status;
            }

            public int read(InputStream is)
            {
                init();
                if (is != null) {
                    if (!(is instanceof BufferedInputStream))
                        is = new BufferedInputStream(is);
                    this.in = ((BufferedInputStream)is);
                    readHeader();
                    if (!err()) {
                        readContents();
                        if (this.frameCount < 0)
                            this.status = 1;
                    }
                }
                else {
                    this.status = 2;
                }
                try {
                    is.close();
                } catch (IOException e) {
                }
                return this.status;
            }

            public int read(String name)
            {
                this.status = 0;
                try {
                    name = name.trim().toLowerCase();
                    URL url = getClass().getClassLoader().getResource(name);
                    this.in = new BufferedInputStream(url.openStream());
                    this.status = read(this.in);
                } catch (IOException e) {
                    this.status = 2;
                }

                return this.status;
            }

            protected void decodeImageData()
            {
                int NullCode = -1;
                int npix = this.iw * this.ih;

                if ((this.pixels == null) || (this.pixels.length < npix)) {
                    this.pixels = new byte[npix];
                }
                if (this.prefix == null)
                    this.prefix = new short[4096];
                if (this.suffix == null)
                    this.suffix = new byte[4096];
                if (this.pixelStack == null) {
                    this.pixelStack = new byte[4097];
                }

                int data_size = read();
                int clear = 1 << data_size;
                int end_of_information = clear + 1;
                int available = clear + 2;
                int old_code = NullCode;
                int code_size = data_size + 1;
                int code_mask = (1 << code_size) - 1;
                for (int code = 0; code < clear; code++) {
                    this.prefix[code] = 0;
                    this.suffix[code] = ((byte)code);
                }
                int bi;
                int pi;
                int top;
                int first;
                int count;
                int bits;
                int datum = bits = count = first = top = pi = bi = 0;

                for (int i = 0; i < npix; ) {
                    if (top == 0) {
                        if (bits < code_size)
                        {
                            if (count == 0)
                            {
                                count = readBlock();
                                if (count <= 0)
                                    break;
                                bi = 0;
                            }
                            datum += ((this.block[bi] & 0xFF) << bits);
                            bits += 8;
                            bi++;
                            count--;
                        }
                        else
                        {
                            code = datum & code_mask;
                            datum >>= code_size;
                            bits -= code_size;

                            if ((code > available) || (code == end_of_information))
                                break;
                            if (code == clear)
                            {
                                code_size = data_size + 1;
                                code_mask = (1 << code_size) - 1;
                                available = clear + 2;
                                old_code = NullCode;
                            }
                            else if (old_code == NullCode) {
                                this.pixelStack[(top++)] = this.suffix[code];
                                old_code = code;
                                first = code;
                            }
                            else {
                                int in_code = code;
                                if (code == available) {
                                    this.pixelStack[(top++)] = ((byte)first);
                                    code = old_code;
                                }
                                while (code > clear) {
                                    this.pixelStack[(top++)] = this.suffix[code];
                                    code = this.prefix[code];
                                }
                                first = this.suffix[code] & 0xFF;

                                if (available >= 4096)
                                    break;
                                this.pixelStack[(top++)] = ((byte)first);
                                this.prefix[available] = ((short)old_code);
                                this.suffix[available] = ((byte)first);
                                available++;
                                if (((available & code_mask) == 0) && (available < 4096)) {
                                    code_size++;
                                    code_mask += available;
                                }
                                old_code = in_code;
                            }
                        }
                    }
                    else {
                        top--;
                        this.pixels[(pi++)] = this.pixelStack[top];
                        i++;
                    }
                }
                for (i = pi; i < npix; i++)
                    this.pixels[i] = 0;
            }

            protected boolean err()
            {
                return this.status != 0;
            }

            protected void init()
            {
                this.status = 0;
                this.frameCount = 0;
                this.frames = new ArrayList();
                this.gct = null;
                this.lct = null;
            }

            protected int read()
            {
                int curByte = 0;
                try {
                    curByte = this.in.read();
                } catch (IOException e) {
                    this.status = 1;
                }
                return curByte;
            }

            protected int readBlock()
            {
                this.blockSize = read();
                int n = 0;
                if (this.blockSize > 0) {
                    try {
                        int count = 0;
                        while (n < this.blockSize) {
                            count = this.in.read(this.block, n, this.blockSize - n);
                            if (count == -1)
                                break;
                            n += count;
                        }
                    }
                    catch (IOException e) {
                    }
                    if (n < this.blockSize) {
                        this.status = 1;
                    }
                }
                return n;
            }

            protected int[] readColorTable(int ncolors)
            {
                int nbytes = 3 * ncolors;
                int[] tab = null;
                byte[] c = new byte[nbytes];
                int n = 0;
                try {
                    n = this.in.read(c);
                } catch (IOException e) {
                }
                if (n < nbytes) {
                    this.status = 1;
                } else {
                    tab = new int[256];
                    int i = 0;
                    int j = 0;
                    while (i < ncolors) {
                        int r = c[(j++)] & 0xFF;
                        int g = c[(j++)] & 0xFF;
                        int b = c[(j++)] & 0xFF;
                        tab[(i++)] = (0xFF000000 | r << 16 | g << 8 | b);
                    }
                }
                return tab;
            }

            protected void readContents()
            {
                boolean done = false;
                while ((!done) && (!err())) {
                    int code = read();
                    switch (code)
                    {
                        case 44:
                            readImage();
                            break;
                        case 33:
                            code = read();
                            switch (code) {
                                case 249:
                                    readGraphicControlExt();
                                    break;
                                case 255:
                                    readBlock();
                                    String app = "";
                                    for (int i = 0; i < 11; i++) {
                                        app = app + (char)this.block[i];
                                    }
                                    if (app.equals("NETSCAPE2.0"))
                                        readNetscapeExt();
                                    else
                                        skip();
                                    break;
                                default:
                                    skip();
                            }
                            break;
                        case 59:
                            done = true;
                            break;
                        case 0:
                            break;
                        default:
                            this.status = 1;
                    }
                }
            }

            protected void readGraphicControlExt()
            {
                read();
                int packed = read();
                this.dispose = ((packed & 0x1C) >> 2);
                if (this.dispose == 0) {
                    this.dispose = 1;
                }
                this.transparency = ((packed & 0x1) != 0);
                this.delay = (readShort() * 10);
                this.transIndex = read();
                read();
            }

            protected void readHeader()
            {
                String id = "";
                for (int i = 0; i < 6; i++) {
                    id = id + (char)read();
                }
                if (!id.startsWith("GIF")) {
                    this.status = 1;
                    return;
                }

                readLSD();
                if ((this.gctFlag) && (!err())) {
                    this.gct = readColorTable(this.gctSize);
                    this.bgColor = this.gct[this.bgIndex];
                }
            }

            protected void readImage()
            {
                this.ix = readShort();
                this.iy = readShort();
                this.iw = readShort();
                this.ih = readShort();

                int packed = read();
                this.lctFlag = ((packed & 0x80) != 0);
                this.interlace = ((packed & 0x40) != 0);

                this.lctSize = (2 << (packed & 0x7));

                if (this.lctFlag) {
                    this.lct = readColorTable(this.lctSize);
                    this.act = this.lct;
                } else {
                    this.act = this.gct;
                    if (this.bgIndex == this.transIndex)
                        this.bgColor = 0;
                }
                int save = 0;
                if (this.transparency) {
                    save = this.act[this.transIndex];
                    this.act[this.transIndex] = 0;
                }

                if (this.act == null) {
                    this.status = 1;
                }

                if (err()) {
                    return;
                }
                decodeImageData();
                skip();

                if (err()) {
                    return;
                }
                this.frameCount += 1;

                this.image = new BufferedImage(this.width, this.height, 3);

                setPixels();

                this.frames.add(new GifActor.GifDecoder.GifFrame(this.image, this.delay));

                if (this.transparency) {
                    this.act[this.transIndex] = save;
                }
                resetFrame();
            }

            protected void readLSD()
            {
                this.width = readShort();
                this.height = readShort();

                int packed = read();
                this.gctFlag = ((packed & 0x80) != 0);

                this.gctSize = (2 << (packed & 0x7));

                this.bgIndex = read();
                this.pixelAspect = read();
            }

            protected void readNetscapeExt()
            {
                do
                {
                    readBlock();
                    if (this.block[0] == 1)
                    {
                        int b1 = this.block[1] & 0xFF;
                        int b2 = this.block[2] & 0xFF;
                        this.loopCount = (b2 << 8 | b1);
                    }
                }
                while ((this.blockSize > 0) && (!err()));
            }

            protected int readShort()
            {
                return read() | read() << 8;
            }

            protected void resetFrame()
            {
                this.lastDispose = this.dispose;
                this.lastRect = new Rectangle(this.ix, this.iy, this.iw, this.ih);
                this.lastImage = this.image;
                this.lastBgColor = this.bgColor;
                int dispose = 0;
                boolean transparency = false;
                int delay = 0;
                this.lct = null;
            }

            protected void skip()
            {
                do
                    readBlock();
                while ((this.blockSize > 0) && (!err()));
            }

            private class GifFrame
            {
                private BufferedImage image;
                private int delay;

                public GifFrame(BufferedImage im, int del)
                {
                    this.image = im;
                    this.delay = del;
                }
            }
        }
}
