import java.util.LinkedList;
import java.util.Queue;
import java.util.Random;

public class Maze {
    private boolean[][] maze;
    private int exit_x, exit_y;

    public Maze() {
        maze = new boolean[10][10];
        for (int i = 0; i < 10; i++)
            for (int j = 0; j < 10; j++)
                maze[i][j] = false;

        generate(0, 0);
        setExit();
    }

    private void generate(int x, int y) {
        maze[x][y] = true;
        int directions[] = shuffleOptions();

        for(int i = 0; i < 4; i++) {
            int nx, ny;
            switch(directions[i]) {
                case 0:  nx = x - 1; ny = y; break; 
                case 1:  nx = x; ny = y + 1; break; 
                case 2:  nx = x + 1; ny = y; break; 
                default: nx = x; ny = y - 1;       
            }

            if(!isOut(nx, ny) && !maze[nx][ny]) {
                if(countNeighbor(nx, ny) > 1) continue;

                generate(nx, ny);
            }
        }
    }

    private Random rand = new Random();
    private int[] shuffleOptions() {
        int[] vec = {0, 1, 2, 3};
        for(int i = vec.length - 1; i > 0; i--) 
        {
            int poz = rand.nextInt(i + 1);
            int aux = vec[i];
            vec[i] = vec[poz];
            vec[poz] = aux;
        }

        return vec;
    }

    private boolean isOut(int x, int y) {
        return (x < 0 || y < 0 || x > 9 || y > 9);
    }

    private int countNeighbor(int x, int y) {
        int nr = 0;
        if(x > 0 && maze[x - 1][y]) nr++;
        if(y > 0 && maze[x][y - 1]) nr++;
        if(x < 9 && maze[x + 1][y]) nr++;
        if(y < 9 && maze[x][y + 1]) nr++;
        
        return nr;
    }

    private void setExit() {
        int[][] distances = new int[10][10];
        for (int i = 0; i < 10; i++)
            for (int j = 0; j < 10; j++)
                distances[i][j] = -1;
        
        Queue<int[]> queue = new LinkedList<>();
        queue.add(new int[]{0, 0});
        distances[0][0] = 0;
        
        int[][] moves = {{-1, 0}, {0, 1}, {1, 0}, {0, -1}};
        
        while(!queue.isEmpty())
        {
            int[] cell = queue.poll();
            int x = cell[0], y = cell[1];

            for(int[] move : moves) 
            {
                int nx = x + move[0], ny = y + move[1];
                if (!isOut(nx, ny) && maze[nx][ny] && distances[nx][ny] == -1) {
                    distances[nx][ny] = distances[x][y] + 1;
                    queue.add(new int[]{nx, ny});
                }
            }
        }
        
        int ma = 0, x = 0, y = 0;
        for (int i = 0; i < 10; i++) {
            for (int j = 0; j < 10; j++) {
                if (distances[i][j] > ma) {
                    ma = distances[i][j];   x = i;  y = j;
                }
            }
        }
        
        exit_x = x;
        exit_y = y;
    }

    public void write(int x, int y) {
        for(int i = 0; i < 10; i++) {
            for(int j = 0; j < 10; j++)
                System.out.print((x == i && y == j ? "x " : (maze[i][j] ? "1 " : "0 ")));
            System.out.println();
        }
    }

    public boolean isExit(int x, int y) {
        return (x == exit_x && y == exit_y);
    }

    public boolean validMove(int x, int y) {
        return (isOut(x, y) || !maze[x][y]);
    }

    public boolean isWall(int x, int y) {
        return !maze[x][y];
    }
}