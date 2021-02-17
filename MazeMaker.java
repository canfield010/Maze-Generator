import java.awt.Canvas;
import java.awt.Graphics;
import java.awt.Color;

import javax.imageio.ImageIO;
import javax.swing.JFrame;
import java.awt.Rectangle;
import java.awt.Point;
import java.awt.GraphicsEnvironment;
import java.awt.GraphicsDevice;
import java.awt.Robot;
import java.awt.image.BufferedImage;
import java.io.File;
import java.util.ArrayList;

public class MazeMaker extends Canvas {
    public static Canvas canvas;
    public static int screenWidth;
    public static int screenHeight;
    public static Robot robot;
    public static BufferedImage image;
    public static int mazeWidth;
    public static int mazeHeight;
    public static boolean maze[][][];
    public static ArrayList<Point> snakes;
    public static ArrayList<Point> snakesToAdd;
    public static boolean done = false;
    public static void main(String[] args) {
        JFrame frame = new JFrame("My Drawing");
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
    }
    public void paint(Graphics g) {
        try {
            robot = new Robot();
            //while (true) {
            mazeWidth = 64;
            mazeHeight = 64;
            maze = new boolean[mazeWidth][mazeHeight][5];
            for (boolean layer[][]: maze) {
                for (boolean square[]: layer) {
                    for (boolean bool: square) {
                        bool = false;
                    }
                }
            }
            image = new BufferedImage(mazeWidth*8 + 4, mazeHeight*8 + 4, BufferedImage.TYPE_3BYTE_BGR);
            final Rectangle rect = getMaximumScreenBounds();
            //this.image = robot.createScreenCapture(rect);
            g.setColor(Color.black);
            g.fillRect(0, 0, (mazeWidth*8)+4, (mazeHeight*8)+4);
            snakes = new ArrayList();
            snakesToAdd = new ArrayList();
            snakes.add(new Point());
            maze[0][0][0] = true;

            SnakeMaker s = new SnakeMaker();
            s.start();


            /*while (true) {
                //System.out.println("got Here 1");
                if (snakes.size()==0) {
                    break;
                }
                for (Point snake: snakes) {
                    //System.out.println("got Here 11");
                    boolean up[] = new boolean[2];
                    if (snake.y<mazeHeight-1) {
                        up[0] = maze[snake.x][snake.y+1][0];
                        up[1] = true;
                    } else {
                        up[1] = false;
                    }

                    boolean down[] = new boolean[2];
                    if (snake.y>0) {
                        down[0] = maze[snake.x][snake.y-1][0];
                        down[1] = true;
                    } else {
                        down[1] = false;
                    }

                    boolean right[] = new boolean[2];
                    if (snake.x<mazeWidth-1) {
                        right[0] = maze[snake.x+1][snake.y][0];
                        right[1] = true;
                    } else {
                        right[1] = false;
                    }

                    boolean left[] = new boolean[2];
                    if (snake.x>0) {
                        left[0] = maze[snake.x-1][snake.y][0];
                        left[1] = true;
                    } else {
                        left[1] = false;
                    }
                    //System.out.print("UP: ");
                    //System.out.print(up[0]);
                    //System.out.println(up[1]);
                    //System.out.print("DOWN: ");
                    //System.out.print(down[0]);
                    //System.out.println(down[1]);
                    //System.out.print("RIGHT: ");
                    //System.out.print(right[0]);
                    //System.out.println(right[1]);
                    //System.out.print("LEFT: ");
                    //System.out.print(left[0]);
                    //System.out.println(left[1]);
                    //System.out.println("got Here 12");
                    int directionCount = 0;
                    if (up[1] && !up[0]) {
                        directionCount++;
                    }
                    if (down[1] && !down[0]) {
                        directionCount++;
                    }
                    if (right[1] && !right[0]) {
                        directionCount++;
                    }
                    if (left[1] && !left[0]) {
                        directionCount++;
                    }
                    if (directionCount==0) {
                        snakes.remove(snake);
                        break;
                    }
                    //System.out.println("DirectionCount: " + Integer.toString(directionCount));
                    //System.out.println("got Here 13");
                    int r = (int)(Math.random()*100);
                    int chosenDirectionNum = (r%directionCount) + 1;
                    //System.out.println("ChosenDirectionNum: " + Integer.toString(chosenDirectionNum));
                    int chosenDirection = -1;
                    //System.out.println("got Here 2");
                    while (chosenDirectionNum>0) {
                        switch (chosenDirection) {
                            case -1:
                                chosenDirection++;
                                if (up[1] && !up[0]) {
                                    chosenDirectionNum-=1;
                                }
                                break;
                            case 0:
                                chosenDirection++;
                                if (down[1] && !down[0]) {
                                    chosenDirectionNum-=1;
                                }
                                break;
                            case 1:
                                chosenDirection++;
                                if (right[1] && !right[0]) {
                                    chosenDirectionNum-=1;
                                }
                                break;
                            case 2:
                                chosenDirection++;
                                if (left[1] && !left[0]) {
                                    chosenDirectionNum-=1;
                                }
                                break;
                        }
                        //chosenDirectionNum--;
                    }
                    //System.out.println("ChosenDirection: " + Integer.toString(chosenDirection));
                    
                    //System.out.println("got Here 2 XD");
                    maze[snake.x][snake.y][0] = true;
                    maze[snake.x][snake.y][chosenDirection+1] = true;
                    int pSnakeX = snake.x;
                    int pSnakeY = snake.y;
                    switch (chosenDirection) {
                        case 0:
                            snake.y++;
                            maze[snake.x][snake.y][2] = true;
                            up[0] = true;
                            break;
                        case 1:
                            snake.y--;
                            maze[snake.x][snake.y][1] = true;
                            down[0] = true;
                            break;
                        case 2:
                            snake.x++;
                            maze[snake.x][snake.y][4] = true;
                            right[0] = true;
                            break;
                        case 3:
                            snake.x--;
                            maze[snake.x][snake.y][3] = true;
                            left[0] = true;
                            break;
                    }
                    for (int i = directionCount-1; i>0; i-=1) {
                        if (Math.random()>0.90) {
                            if (directionCount==2) {
                                if (left[1] && !left[0]) {
                                    snakesToAdd.add(new Point(pSnakeX-1, pSnakeY));
                                    maze[pSnakeX][pSnakeY][4] = true;
                                    maze[pSnakeX-1][pSnakeY][3] = true;
                                }
                                if (right[1] && !right[0]) {
                                    snakesToAdd.add(new Point(pSnakeX+1, pSnakeY));
                                    maze[pSnakeX][pSnakeY][3] = true;
                                    maze[pSnakeX+1][pSnakeY][4] = true;
                                }
                                if (down[1] && !down[0]) {
                                    snakesToAdd.add(new Point(pSnakeX, pSnakeY-1));
                                    maze[pSnakeX][pSnakeY][2] = true;
                                    maze[pSnakeX][pSnakeY-1][1] = true;
                                }
                                if (up[1] && !up[0]) {
                                    snakesToAdd.add(new Point(pSnakeX, pSnakeY+1));
                                    maze[pSnakeX][pSnakeY+1][2] = true;
                                }
                            } else {
                                if (up[1] && !up[0]) {
                                    snakesToAdd.add(new Point(pSnakeX, pSnakeY+1));
                                    maze[pSnakeX][pSnakeY][1] = true;
                                    maze[pSnakeX][pSnakeY+1][2] = true;
                                }
                                if (down[1] && !down[0]) {
                                    snakesToAdd.add(new Point(pSnakeX, pSnakeY-1));
                                    maze[pSnakeX][pSnakeY][2] = true;
                                    maze[pSnakeX][pSnakeY-1][1] = true;
                                }
                                if (right[1] && !right[0]) {
                                    snakesToAdd.add(new Point(pSnakeX+1, pSnakeY));
                                    maze[pSnakeX][pSnakeY][3] = true;
                                    maze[pSnakeX+1][pSnakeY][4] = true;
                                }
                                if (left[1] && !left[0]) {
                                    snakesToAdd.add(new Point(pSnakeX-1, pSnakeY));
                                    maze[pSnakeX][pSnakeY][4] = true;
                                    maze[pSnakeX-1][pSnakeY][3] = true;
                                }
                            }
                        }
                    }
                    
                    //System.out.println("SnakeValue: " + snake.toString() + Integer.toString(snakes.size()));
                }
                //System.out.println("drawing");
                //g.setColor(Color.black);
                //g.fillRect(0, 0, (mazeWidth*8)+4, (mazeHeight*8)+4);
                g.setColor(Color.white);
                for (int x = 0; x<mazeWidth; x++) {
                    for (int y = 0; y<mazeHeight; y++) {
                        if (maze[x][y][0])
                            g.fillRect((x*8)+3, (y*8)+3, 6, 6);
                        if (maze[x][y][1])
                            g.fillRect((x*8)+3, (y*8)+9, 6, 1);
                        if (maze[x][y][2])
                            g.fillRect((x*8)+3, (y*8)+2, 6, 1);
                        if (maze[x][y][3])
                            g.fillRect((x*8)+9, (y*8)+3, 1, 6);
                        if (maze[x][y][4])
                            g.fillRect((x*8)+2, (y*8)+3, 1, 6);
                    }
                }
                //g.fillRect(600, 200, 600, 600);
                //g.setColor(Color.black);
                //g.drawString(Integer.toString(snakes.size()), 1000, 500);
                //Thread.sleep(50);
                for (Point snake: snakesToAdd) {
                    snakes.add(snake);
                }
                snakesToAdd.clear();
            }*/
            //done = true;
            g.setColor(Color.black);
            g.fillRect(0, 0, (mazeWidth*8)+4, (mazeHeight*8)+4);
            g.setColor(Color.white);
            while (!done) {
                Thread.sleep(1);
            }
            for (int x = 0; x<mazeWidth; x++) {
                for (int y = 0; y<mazeHeight; y++) {
                    if (maze[x][y][0]) {
                        for (int i = 0; i<6; i++) {
                            for (int j = 0; j<6; j++) {
                                image.setRGB((x*8)+3 + i,(y*8)+3 + j,16777215);
                            }
                        }
                    }
                    if (maze[x][y][1]) {
                        for (int i = 0; i<6; i++) {
                            for (int j = 0; j<1; j++) {
                                image.setRGB((x*8)+3 + i,(y*8)+9 + j,16777215);
                            }
                        }
                    }
                    if (maze[x][y][2]) {
                        for (int i = 0; i<6; i++) {
                            for (int j = 0; j<1; j++) {
                                image.setRGB((x*8)+3 + i,(y*8)+2 + j,16777215);
                            }
                        }
                    }
                    if (maze[x][y][3]) {
                        for (int i = 0; i<1; i++) {
                            for (int j = 0; j<6; j++) {
                                image.setRGB((x*8)+9 + i,(y*8)+3 + j,16777215);
                            }
                        }
                    }
                    if (maze[x][y][4]) {
                        for (int i = 0; i<1; i++) {
                            for (int j = 0; j<6; j++) {
                                image.setRGB((x*8)+2 + i,(y*8)+3 + j,16777215);
                            }
                        }
                    }
                }
            }
            try {
                //ImageIO.write(img, "PNG", new File("c:\\random.PNG"));
                ImageIO.write(image, "PNG", new File("C:\\Users\\Paul\\OneDrive\\Desktop\\Programming\\Javas\\MazeMaker\\maze.png"));
            } catch (Exception e) {
                System.out.println(e);
            }
            while (true) {
                //g.setColor(Color.black);
                //g.fillRect(0, 0, (mazeWidth*8)+4, (mazeHeight*8)+4);
                //g.setColor(Color.white);
                for (int x = 0; x<mazeWidth; x++) {
                    for (int y = 0; y<mazeHeight; y++) {
                        if (maze[x][y][0])
                            g.fillRect((x*8)+3, (y*8)+3, 6, 6);
                        if (maze[x][y][1])
                            g.fillRect((x*8)+3, (y*8)+9, 6, 1);
                        if (maze[x][y][2])
                            g.fillRect((x*8)+3, (y*8)+2, 6, 1);
                        if (maze[x][y][3])
                            g.fillRect((x*8)+9, (y*8)+3, 1, 6);
                        if (maze[x][y][4])
                            g.fillRect((x*8)+2, (y*8)+3, 1, 6);
                    }
                }
                /*for (boolean layer[][]: maze) {
                    for (boolean square[]: layer) {
                        System.out.print(Boolean.toString(square[0]) + ", ");
                    }
                    System.out.println();
                }*/
            }

            //for ()


            //Point pos = canvas.getMousePosition();
            /*if (pos!=null) {
                int r = 0;
                g.setColor(Color.black);
                g.fillRect(0, 0, screenWidth, screenHeight);
                g.setColor(Color.white);
                g.fillOval(pos.x-r, pos.y-r, r*2, r*2);
                //g.fillOval(100, 100, 200, 200);
                //g.fillOval(pos.x-r, pos.y-r, pos.x+r, pos.y+r);
                g.drawImage(image, 0, 0, new Observer());
                try{
                    Thread.sleep(17);
                } catch (Exception e){
                    System.err.println(e);
                }
            }*/
            //}
        } catch (Exception e) {
            System.err.println(e);
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