import javax.swing.*;
import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

public class MainMenu extends JFrame {

    public MainMenu() {
        setTitle("Maze Game - Main Menu");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(600, 500);
        setLocationRelativeTo(null); // Centrează pe ecran
        setLayout(new BorderLayout());

        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                if (Main.login != null) {
                    Main.login.writeBinaryFile();
                }
                System.exit(0);
            }
        });

        // Panel stânga pentru butoane
        JPanel leftPanel = new JPanel();
        leftPanel.setLayout(new GridLayout(4, 1, 10, 10));
        
        JButton newGameButton = new JButton("New Game");
        JButton accountButton = new JButton("Account");
        JButton exitButton = new JButton("Exit");

        leftPanel.add(newGameButton);
        leftPanel.add(accountButton);
        leftPanel.add(exitButton);
        
        // Panel dreapta pentru detalii jucător
        JPanel rightPanel = new JPanel();
        rightPanel.setLayout(new BoxLayout(rightPanel, BoxLayout.Y_AXIS));
        rightPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        
        JLabel nameLabel = new JLabel("Player: " + Main.player.getName());
        JLabel scoreLabel = new JLabel("Highscore: " + Main.player.getHighscore());
        JLabel mazeImageLabel = new JLabel(new ImageIcon("maze_image.jpg")); // Înlocuiește cu imaginea reală
        
        rightPanel.add(nameLabel);
        rightPanel.add(scoreLabel);
        rightPanel.add(mazeImageLabel);
        
        add(leftPanel, BorderLayout.WEST);
        add(rightPanel, BorderLayout.CENTER);
        
        // Functionalitate butoane
        newGameButton.addActionListener(e -> startGame());
        accountButton.addActionListener(e -> { dispose(); new ProfileScreen();});
        exitButton.addActionListener(e -> {
            if (Main.login != null) {
                Main.login.writeBinaryFile();
            }
            System.exit(0);
        });
        
        setLocationRelativeTo(null);
        setVisible(true);
    }

    private void startGame() {
        JFrame gameFrame = new JFrame("Maze Game");
        MazeScreen game = new MazeScreen(Main.player.getColor());
        
        gameFrame.add(game);
        gameFrame.pack();
        gameFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        gameFrame.setLocationRelativeTo(null);
        gameFrame.setVisible(true);
        
        dispose(); // Închide meniul principal
    }
    
}
