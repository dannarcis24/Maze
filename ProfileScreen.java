import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class ProfileScreen extends JFrame {
    private JLabel usernameLabel, gamesLabel, highscoreLabel;
    
    public ProfileScreen() {
        setTitle("Player Profile");
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setSize(500, 300);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());
        
        // Panelul din stânga - informații despre jucător
        JPanel leftPanel = new JPanel();
        leftPanel.setLayout(new BoxLayout(leftPanel, BoxLayout.Y_AXIS));
        leftPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        
        usernameLabel = new JLabel("Username: " + Main.player.getName());
        gamesLabel = new JLabel("Total Games: " + Main.player.getGames());
        highscoreLabel = new JLabel("Highscore: " + Main.player.getHighscore());
        
        leftPanel.add(usernameLabel);
        leftPanel.add(Box.createVerticalStrut(10));
        leftPanel.add(gamesLabel);
        leftPanel.add(Box.createVerticalStrut(10));
        leftPanel.add(highscoreLabel);
        
        // Panelul din dreapta - butoane pentru modificări
        JPanel rightPanel = new JPanel();
        rightPanel.setLayout(new BoxLayout(rightPanel, BoxLayout.Y_AXIS));
        rightPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        
        JButton changeUsernameButton = new JButton("Change Username");
        JButton changePasswordButton = new JButton("Change Password");
        JButton changeColorButton = new JButton("Change Color");
        
        rightPanel.add(changeUsernameButton);
        rightPanel.add(Box.createVerticalStrut(10));
        rightPanel.add(changePasswordButton);
        rightPanel.add(Box.createVerticalStrut(10));
        rightPanel.add(changeColorButton);
        
        // Adăugăm panourile în fereastră
        add(leftPanel, BorderLayout.WEST);
        add(rightPanel, BorderLayout.EAST);
        
        // Butonul Done în partea de jos, aliniat la dreapta
        JPanel bottomPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        JButton doneButton = new JButton("Done");
        bottomPanel.add(doneButton);
        add(bottomPanel, BorderLayout.SOUTH);
        
        // Acțiune pentru butonul Done: deschide meniul principal și închide fereastra profil
        doneButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                new MainMenu();
                dispose();
            }
        });
        
        // Acțiune pentru schimbarea username-ului
        changeUsernameButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                String newUsername = JOptionPane.showInputDialog(ProfileScreen.this, "Enter new username:");
                if(newUsername != null && !newUsername.trim().isEmpty()) {
                    if(Main.player.updateName(newUsername.trim())) {
                        usernameLabel.setText("Username: " + Main.player.getName());
                        JOptionPane.showMessageDialog(ProfileScreen.this, "Username updated successfully!");
                    } else {
                        JOptionPane.showMessageDialog(ProfileScreen.this, "Invalid username! Must be at least 3 characters.");
                    }
                }
            }
        });
        
        // Acțiune pentru schimbarea parolei
        changePasswordButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                JPasswordField passwordField = new JPasswordField();
                int option = JOptionPane.showConfirmDialog(ProfileScreen.this, passwordField, "Enter new password:", JOptionPane.OK_CANCEL_OPTION);
                if(option == JOptionPane.OK_OPTION) {
                    String newPassword = new String(passwordField.getPassword()).trim();
                    if(!newPassword.isEmpty()) {
                        if(Main.player.updatePassword(newPassword)) {
                            JOptionPane.showMessageDialog(ProfileScreen.this, "Password updated successfully!");
                        } else {
                            JOptionPane.showMessageDialog(ProfileScreen.this, "Invalid password! It must be at least 8 characters long and include uppercase, lowercase, and a digit.");
                        }
                    }
                }
            }
        });
        
        // Acțiune pentru schimbarea culorii
        changeColorButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                Color newColor = JColorChooser.showDialog(ProfileScreen.this, "Choose a new color", Main.player.getColor());
                if(newColor != null) {
                    if(Main.player.updateColor(newColor)) {
                        JOptionPane.showMessageDialog(ProfileScreen.this, "Color updated successfully!");
                    } else {
                        JOptionPane.showMessageDialog(ProfileScreen.this, "Invalid color! (Cannot be white)");
                    }
                }
            }
        });
        
        setVisible(true);
    }
}