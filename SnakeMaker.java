import java.awt.*;

public class SnakeMaker extends Thread{
    public void run() {
        while (true) {
            if (MazeMaker.snakes.size()==0) {
                break;
            }
            for (Point snake: MazeMaker.snakes) {
                boolean up[] = new boolean[2];
                if (snake.y<MazeMaker.mazeHeight-1) {
                    up[0] = MazeMaker.maze[snake.x][snake.y+1][0];
                    up[1] = true;
                } else {
                    up[1] = false;
                }

                boolean down[] = new boolean[2];
                if (snake.y>0) {
                    down[0] = MazeMaker.maze[snake.x][snake.y-1][0];
                    down[1] = true;
                } else {
                    down[1] = false;
                }

                boolean right[] = new boolean[2];
                if (snake.x<MazeMaker.mazeWidth-1) {
                    right[0] = MazeMaker.maze[snake.x+1][snake.y][0];
                    right[1] = true;
                } else {
                    right[1] = false;
                }

                boolean left[] = new boolean[2];
                if (snake.x>0) {
                    left[0] = MazeMaker.maze[snake.x-1][snake.y][0];
                    left[1] = true;
                } else {
                    left[1] = false;
                }
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
                int r = (int)(Math.random()*100);
                int chosenDirectionNum = (r%directionCount) + 1;
                int chosenDirection = -1;
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
                }
                MazeMaker.maze[snake.x][snake.y][0] = true;
                MazeMaker.maze[snake.x][snake.y][chosenDirection+1] = true;
                int pSnakeX = snake.x;
                int pSnakeY = snake.y;
                switch (chosenDirection) {
                    case 0:
                        snake.y++;
                        MazeMaker.maze[snake.x][snake.y][2] = true;
                        MazeMaker.maze[snake.x][snake.y][0] = true;
                        up[0] = true;
                        break;
                    case 1:
                        snake.y--;
                        MazeMaker.maze[snake.x][snake.y][1] = true;
                        MazeMaker.maze[snake.x][snake.y][0] = true;
                        down[0] = true;
                        break;
                    case 2:
                        snake.x++;
                        MazeMaker.maze[snake.x][snake.y][4] = true;
                        MazeMaker.maze[snake.x][snake.y][0] = true;
                        right[0] = true;
                        break;
                    case 3:
                        snake.x--;
                        MazeMaker.maze[snake.x][snake.y][3] = true;
                        MazeMaker.maze[snake.x][snake.y][0] = true;
                        left[0] = true;
                        break;
                    /*case 0:
                        snake.y++;
                        MazeMaker.maze[snake.x][snake.y][1] = true;
                        up[0] = true;
                        break;
                    case 1:
                        snake.y--;
                        MazeMaker.maze[snake.x][snake.y][2] = true;
                        down[0] = true;
                        break;
                    case 2:
                        snake.x++;
                        MazeMaker.maze[snake.x][snake.y][3] = true;
                        right[0] = true;
                        break;
                    case 3:
                        snake.x--;
                        MazeMaker.maze[snake.x][snake.y][4] = true;
                        left[0] = true;
                        break;*/
                }
                for (int i = directionCount-1; i>0; i-=1) {
                    if (Math.random()>0.90) {
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
            for (Point snake: MazeMaker.snakesToAdd) {
                MazeMaker.snakes.add(snake);
            }
            MazeMaker.snakesToAdd.clear();
        }
        MazeMaker.done = true;
    }
}