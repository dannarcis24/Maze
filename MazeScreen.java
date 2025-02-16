import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class MazeScreen extends JPanel implements KeyListener {
    private Maze maze;
    private int playerX = 0, playerY = 0;
    private final int CELL_SIZE = 40;
    private long start, end;
    private Color playeColor;

    public MazeScreen(Color color) {
        maze = new Maze();
        playeColor = color;

        setPreferredSize(new Dimension(10 * CELL_SIZE, 10 * CELL_SIZE));
        setFocusable(true);
        addKeyListener(this);

        start = System.nanoTime();
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        for(int i = 0; i < 10; i++) {
            for(int j = 0; j < 10; j++) {
                if(maze.isWall(i, j)) 
                    g.setColor(Color.BLACK);
                else 
                    g.setColor(Color.WHITE);
                
                g.fillRect(j * CELL_SIZE, i * CELL_SIZE, CELL_SIZE, CELL_SIZE);
                g.setColor(Color.GRAY);
                g.drawRect(j * CELL_SIZE, i * CELL_SIZE, CELL_SIZE, CELL_SIZE);
            }
        }

        g.setColor(playeColor);
        g.fillOval(playerY * CELL_SIZE + 10, playerX * CELL_SIZE + 10, 20, 20);
    }

    @Override
    public void keyPressed(KeyEvent e) {
        int nx = playerX, ny = playerY;
        switch (e.getKeyCode()) {
            case KeyEvent.VK_W: case KeyEvent.VK_UP:    nx--; break;
            case KeyEvent.VK_S: case KeyEvent.VK_DOWN:  nx++; break;
            case KeyEvent.VK_A: case KeyEvent.VK_LEFT:  ny--; break;
            case KeyEvent.VK_D: case KeyEvent.VK_RIGHT: ny++; break;
        }

        if (!maze.validMove(nx, ny)) {
            playerX = nx;
            playerY = ny;
        }
        if (maze.isExit(playerX, playerY)) {
            end = System.nanoTime();
            double score = (end - start) / 1_000_000_000.;
            String message = "You reached the exit in " + String.format("%.2f", score) + "s! Game Over.";
            Main.player.updateScore(score);
            JOptionPane.showMessageDialog(this, message);

            JFrame frame = (JFrame) SwingUtilities.getWindowAncestor(this);;
            frame.dispose();

            new MainMenu();
        }
        
        repaint();
    }

    @Override
    public void keyReleased(KeyEvent e) {}
    @Override
    public void keyTyped(KeyEvent e) {}
}
