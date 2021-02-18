import java.awt.*;

import javax.imageio.ImageIO;
import javax.swing.JFrame;
import java.awt.image.BufferedImage;
import java.awt.image.ImageObserver;
import java.io.File;
import java.util.ArrayList;
import java.util.Random;

public class MazeMaker extends Canvas {
    public static Canvas canvas;
    public static int screenWidth;
    public static int screenHeight;
    public static Robot robot;
    public static BufferedImage image;
    public static boolean hasDrawn = false;
    public static Random r = new Random();

    // settings:
    public static final int mazeWidth = 64;
    public static final int mazeHeight = 64;
    public static final double snakeSpawnChance = 0.05;
    // theoretically, the smaller this number, the farther the snakes will go until branching off into new snakes.
    public static final int slowMotionTimestep = 0;
    // 50 is a good value to choose to watch it generate.
    public static boolean setSeed = true;
    public static long seed = 0;

    public static boolean[][][] maze;
    public static ArrayList<Point> snakes;
    public static ArrayList<Point> snakesToAdd;
    public static boolean done = false;
    public static void main(String[] args) {
        if (setSeed) {
            r.setSeed(seed);
        }
        image = new BufferedImage(mazeWidth*8 + 4, mazeHeight*8 + 4, BufferedImage.TYPE_3BYTE_BGR);
        //image = new BufferedImage(mazeWidth*8 + 4, mazeHeight*8 + 4, BufferedImage.TYPE_INT_ARGB);
        JFrame frame = new JFrame("aMazing Maze");
        canvas = new MazeMaker();
        //Canvas canvas = new Canvas();
        final Rectangle rect = getMaximumScreenBounds();
        screenWidth = rect.width;
        screenHeight = rect.height;
        canvas.setSize(screenWidth, screenHeight);
        frame.add(canvas);
        frame.pack();
        frame.setVisible(true);
        Graphics g = canvas.getGraphics();
        /*try {
            //ImageIO.write(image, "PNG", new File("C:\\Users\\Paul\\OneDrive\\Desktop\\Programming\\Javas\\MazeMaker\\maze.png"));
            ImageIO.write(image, "PNG", new File("maze.png"));
            //ImageIO.write(image, "PNG", new File("C:\\Users\\canfield010\\Desktop\\Programming\\Java\\Maze-Generator\\maze.png"));
        } catch (Exception e) {
            System.out.println(e);
        }*/
    }
    public void paint(Graphics g) {
        try {
            robot = new Robot();
            //while (true) {
            //mazeWidth = 64;
            //mazeHeight = 64;
            maze = new boolean[mazeWidth][mazeHeight][5];
            // I think this already happens. I wrote this the first time just to be sure.
            /*for (boolean[][] layer : maze) {
                for (boolean[] square : layer) {
                    for (boolean bool: square) {
                        bool = false;
                    }
                }
            }*/
            //image = new BufferedImage(mazeWidth*8 + 4, mazeHeight*8 + 4, BufferedImage.TYPE_3BYTE_BGR);
            final Rectangle rect = getMaximumScreenBounds();
            //this.image = robot.createScreenCapture(rect);
            //g.setColor(Color.black);
            //g.fillRect(0, 0, (mazeWidth*8)+4, (mazeHeight*8)+4);
            snakes = new ArrayList();
            snakesToAdd = new ArrayList();
            snakes.add(new Point());
            maze[0][0][0] = true;

            SnakeMaker s = new SnakeMaker();
            s.start();


            // drawing stuff:
            while (true) {
                for (int x = 0; x < mazeWidth; x++) {
                    for (int y = 0; y < mazeHeight; y++) {
                        if (maze[x][y][0]) {
                            for (int i = 0; i < 6; i++) {
                                for (int j = 0; j < 6; j++) {
                                    image.setRGB((x * 8) + 3 + i, (y * 8) + 3 + j, 16777215);
                                }
                            }
                        }
                        if (maze[x][y][1]) {
                            for (int i = 0; i < 6; i++) {
                                for (int j = 0; j < 1; j++) {
                                    image.setRGB((x * 8) + 3 + i, (y * 8) + 9 + j, 16777215);
                                }
                            }
                        }
                        if (maze[x][y][2]) {
                            for (int i = 0; i < 6; i++) {
                                for (int j = 0; j < 1; j++) {
                                    image.setRGB((x * 8) + 3 + i, (y * 8) + 2 + j, 16777215);
                                }
                            }
                        }
                        if (maze[x][y][3]) {
                            for (int i = 0; i < 1; i++) {
                                for (int j = 0; j < 6; j++) {
                                    image.setRGB((x * 8) + 9 + i, (y * 8) + 3 + j, 16777215);
                                }
                            }
                        }
                        if (maze[x][y][4]) {
                            for (int i = 0; i < 1; i++) {
                                for (int j = 0; j < 6; j++) {
                                    image.setRGB((x * 8) + 2 + i, (y * 8) + 3 + j, 16777215);
                                }
                            }
                        }
                    }
                }
                // the ide filled this out - I have no idea what it does.
                g.drawImage(image, 0, 0, new ImageObserver() {
                    @Override
                    public boolean imageUpdate(Image img, int infoflags, int x, int y, int width, int height) {
                        return false;
                    }
                });
                //if (!hasDrawn && done) {
                    try {
                        //ImageIO.write(image, "PNG", new File("C:\\Users\\Paul\\OneDrive\\Desktop\\Programming\\Javas\\MazeMaker\\maze.png"));
                        ImageIO.write(image, "PNG", new File("maze.png"));
                        ImageIO.write(image, "JPG", new File("maze.jpg"));
                        //ImageIO.write(image, "PNG", new File("C:\\Users\\canfield010\\Desktop\\Programming\\Java\\Maze-Generator\\maze.png"));
                    } catch (Exception e) {
                        System.out.println(e);
                    }
                //}
            }



        } catch (Exception e) {
            System.err.println(e);
        }
        try {
            //ImageIO.write(image, "PNG", new File("C:\\Users\\Paul\\OneDrive\\Desktop\\Programming\\Javas\\MazeMaker\\maze.png"));
            ImageIO.write(image, "PNG", new File("maze.png"));
            ImageIO.write(image, "JPG", new File("maze.jpg"));
            //ImageIO.write(image, "PNG", new File("C:\\Users\\canfield010\\Desktop\\Programming\\Java\\Maze-Generator\\maze.png"));
        } catch (Exception e) {
            System.out.println(e);
        }
    }
    public static Rectangle getMaximumScreenBounds() {
        int minx=0, miny=0, maxx=0, maxy=0;
        final GraphicsEnvironment environment = GraphicsEnvironment.getLocalGraphicsEnvironment();
        for(final GraphicsDevice device : environment.getScreenDevices()){
            final Rectangle bounds = device.getDefaultConfiguration().getBounds();
            minx = Math.min(minx, bounds.x);
            miny = Math.min(miny, bounds.y);
            maxx = Math.max(maxx,  bounds.x+bounds.width);
            maxy = Math.max(maxy, bounds.y+bounds.height);
        }
        return new Rectangle(minx, miny, maxx-minx, maxy-miny);
    }
}