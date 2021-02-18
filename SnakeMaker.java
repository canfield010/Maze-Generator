import java.awt.*;

public class SnakeMaker extends Thread{
    public void run() {
        while (true) {
            //System.out.println(MazeMaker.snakes.size());
            // adds some delay if people want to see it generate in slow motion.
            if (MazeMaker.slowMotionTimestep != 0) {
                try {
                    Thread.sleep(MazeMaker.slowMotionTimestep);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }

            if (MazeMaker.snakes.size()==0) {
                break;
            }
            for (Point snake: MazeMaker.snakes) {
                // finds out which directions it can move to.
                boolean[] up = new boolean[2];
                if (snake.y<MazeMaker.mazeHeight-1) {
                    up[0] = MazeMaker.maze[snake.x][snake.y+1][0];
                    up[1] = true;
                }

                boolean[] down = new boolean[2];
                if (snake.y>0) {
                    down[0] = MazeMaker.maze[snake.x][snake.y-1][0];
                    down[1] = true;
                }

                boolean[] right = new boolean[2];
                if (snake.x<MazeMaker.mazeWidth-1) {
                    right[0] = MazeMaker.maze[snake.x+1][snake.y][0];
                    right[1] = true;
                }

                boolean[] left = new boolean[2];
                if (snake.x>0) {
                    left[0] = MazeMaker.maze[snake.x-1][snake.y][0];
                    left[1] = true;
                }

                // counts the number of possible directions
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
                    MazeMaker.snakes.remove(snake);
                    break;
                }
                // chooses a random direction
                //int r = (int)(Math.random()*100);
                int r = (int)(MazeMaker.r.nextFloat()*100);
                int chosenDirectionNum = (r%directionCount) + 1;
                int chosenDirection = -1;
                while (chosenDirectionNum>0) {
                    switch (chosenDirection) {
                        case -1 -> {
                            chosenDirection++;
                            if (up[1] && !up[0]) {
                                chosenDirectionNum -= 1;
                            }
                        }
                        case 0 -> {
                            chosenDirection++;
                            if (down[1] && !down[0]) {
                                chosenDirectionNum -= 1;
                            }
                        }
                        case 1 -> {
                            chosenDirection++;
                            if (right[1] && !right[0]) {
                                chosenDirectionNum -= 1;
                            }
                        }
                        case 2 -> {
                            chosenDirection++;
                            if (left[1] && !left[0]) {
                                chosenDirectionNum -= 1;
                            }
                        }
                    }
                }
                // moves the snake
                MazeMaker.maze[snake.x][snake.y][0] = true;
                MazeMaker.maze[snake.x][snake.y][chosenDirection+1] = true;
                int pSnakeX = snake.x;
                int pSnakeY = snake.y;
                switch (chosenDirection) {
                    case 0 -> {
                        snake.y++;
                        MazeMaker.maze[snake.x][snake.y][2] = true;
                        MazeMaker.maze[snake.x][snake.y][0] = true;
                        up[0] = true;
                    }
                    case 1 -> {
                        snake.y--;
                        MazeMaker.maze[snake.x][snake.y][1] = true;
                        MazeMaker.maze[snake.x][snake.y][0] = true;
                        down[0] = true;
                    }
                    case 2 -> {
                        snake.x++;
                        MazeMaker.maze[snake.x][snake.y][4] = true;
                        MazeMaker.maze[snake.x][snake.y][0] = true;
                        right[0] = true;
                    }
                    case 3 -> {
                        snake.x--;
                        MazeMaker.maze[snake.x][snake.y][3] = true;
                        MazeMaker.maze[snake.x][snake.y][0] = true;
                        left[0] = true;
                    }
                }
                // adds new snakes
                for (int i = directionCount-1; i>0; i-=1) {
                    if (MazeMaker.r.nextFloat()<MazeMaker.snakeSpawnChance) {
                        if (directionCount==2) {
                            if (left[1] && !left[0]) {
                                MazeMaker.snakesToAdd.add(new Point(pSnakeX-1, pSnakeY));
                                MazeMaker.maze[pSnakeX][pSnakeY][4] = true;
                                MazeMaker.maze[pSnakeX-1][pSnakeY][3] = true;
                                MazeMaker.maze[pSnakeX-1][pSnakeY][0] = true;
                            }
                            if (right[1] && !right[0]) {
                                MazeMaker.snakesToAdd.add(new Point(pSnakeX+1, pSnakeY));
                                MazeMaker.maze[pSnakeX][pSnakeY][3] = true;
                                MazeMaker.maze[pSnakeX+1][pSnakeY][4] = true;
                                MazeMaker.maze[pSnakeX+1][pSnakeY][0] = true;
                            }
                            if (down[1] && !down[0]) {
                                MazeMaker.snakesToAdd.add(new Point(pSnakeX, pSnakeY-1));
                                MazeMaker.maze[pSnakeX][pSnakeY][2] = true;
                                MazeMaker.maze[pSnakeX][pSnakeY-1][1] = true;
                                MazeMaker.maze[pSnakeX][pSnakeY-1][0] = true;
                            }
                            if (up[1] && !up[0]) {
                                MazeMaker.snakesToAdd.add(new Point(pSnakeX, pSnakeY+1));
                                MazeMaker.maze[pSnakeX][pSnakeY][1] = true;
                                MazeMaker.maze[pSnakeX][pSnakeY+1][2] = true;
                                MazeMaker.maze[pSnakeX][pSnakeY+1][0] = true;
                            }
                        } else {
                            if (up[1] && !up[0]) {
                                MazeMaker.snakesToAdd.add(new Point(pSnakeX, pSnakeY+1));
                                MazeMaker.maze[pSnakeX][pSnakeY][1] = true;
                                MazeMaker.maze[pSnakeX][pSnakeY+1][2] = true;
                                MazeMaker.maze[pSnakeX][pSnakeY+1][0] = true;
                            }
                            if (down[1] && !down[0]) {
                                MazeMaker.snakesToAdd.add(new Point(pSnakeX, pSnakeY-1));
                                MazeMaker.maze[pSnakeX][pSnakeY][2] = true;
                                MazeMaker.maze[pSnakeX][pSnakeY-1][1] = true;
                                MazeMaker.maze[pSnakeX][pSnakeY-1][0] = true;
                            }
                            if (right[1] && !right[0]) {
                                MazeMaker.snakesToAdd.add(new Point(pSnakeX+1, pSnakeY));
                                MazeMaker.maze[pSnakeX][pSnakeY][3] = true;
                                MazeMaker.maze[pSnakeX+1][pSnakeY][4] = true;
                                MazeMaker.maze[pSnakeX+1][pSnakeY][0] = true;
                            }
                            if (left[1] && !left[0]) {
                                MazeMaker.snakesToAdd.add(new Point(pSnakeX-1, pSnakeY));
                                MazeMaker.maze[pSnakeX][pSnakeY][4] = true;
                                MazeMaker.maze[pSnakeX-1][pSnakeY][3] = true;
                                MazeMaker.maze[pSnakeX-1][pSnakeY][0] = true;
                            }
                        }
                    }
                }
            }
            MazeMaker.snakes.addAll(MazeMaker.snakesToAdd);
            MazeMaker.snakesToAdd.clear();

            if (MazeMaker.snakes.size() == 0) {
                boolean found = false;
                for (int x = 0; x<MazeMaker.mazeWidth; x++) {
                    for (int y = 0; y<MazeMaker.mazeHeight; y++) {
                        if (!MazeMaker.maze[x][y][0] && !found) {
                            // imma copy some snake movement code. This could really be a nice method, since im using it twice:
                            boolean[] up = new boolean[2];
                            if (y<MazeMaker.mazeHeight-1) {
                                up[0] = MazeMaker.maze[x][y+1][0];
                                up[1] = true;
                            }

                            boolean[] down = new boolean[2];
                            if (y>0) {
                                down[0] = MazeMaker.maze[x][y-1][0];
                                down[1] = true;
                            }

                            boolean[] right = new boolean[2];
                            if (x<MazeMaker.mazeWidth-1) {
                                right[0] = MazeMaker.maze[x+1][y][0];
                                right[1] = true;
                            }

                            boolean[] left = new boolean[2];
                            if (x>0) {
                                left[0] = MazeMaker.maze[x-1][y][0];
                                left[1] = true;
                            }

                            // counts the number of possible directions
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
                                break;
                            }
                            MazeMaker.snakes.add(new Point(x, y));
                            MazeMaker.maze[x][y][0] = true;
                            // chooses a random direction
                            int r = (int)(MazeMaker.r.nextFloat()*100);
                            int chosenDirectionNum = (r%directionCount) + 1;
                            int chosenDirection = -1;
                            while (chosenDirectionNum>0) {
                                switch (chosenDirection) {
                                    case -1 -> {
                                        chosenDirection++;
                                        if (up[1] && !up[0]) {
                                            chosenDirectionNum -= 1;
                                        }
                                    }
                                    case 0 -> {
                                        chosenDirection++;
                                        if (down[1] && !down[0]) {
                                            chosenDirectionNum -= 1;
                                        }
                                    }
                                    case 1 -> {
                                        chosenDirection++;
                                        if (right[1] && !right[0]) {
                                            chosenDirectionNum -= 1;
                                        }
                                    }
                                    case 2 -> {
                                        chosenDirection++;
                                        if (left[1] && !left[0]) {
                                            chosenDirectionNum -= 1;
                                        }
                                    }
                                }
                            }
                            switch (chosenDirection) {
                                case 0 -> {
                                    MazeMaker.maze[x][y][1] = true;
                                    y++;
                                    MazeMaker.maze[x][y][2] = true;
                                    //MazeMaker.maze[x][y][0] = true;
                                    up[0] = true;
                                }
                                case 1 -> {
                                    MazeMaker.maze[x][y][2] = true;
                                    y--;
                                    MazeMaker.maze[x][y][1] = true;
                                    //MazeMaker.maze[x][y][0] = true;
                                    down[0] = true;
                                }
                                case 2 -> {
                                    MazeMaker.maze[x][y][3] = true;
                                    x++;
                                    MazeMaker.maze[x][y][4] = true;
                                    //MazeMaker.maze[x][y][0] = true;
                                    right[0] = true;
                                }
                                case 3 -> {
                                    MazeMaker.maze[x][y][4] = true;
                                    x--;
                                    MazeMaker.maze[x][y][3] = true;
                                    //MazeMaker.maze[x][y][0] = true;
                                    left[0] = true;
                                }
                            }
                            found = true;
                        }
                    }
                }
                if (!found) {
                    break;
                }
            }
        }
        MazeMaker.done = true;
    }
}