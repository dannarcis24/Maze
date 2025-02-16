import javax.swing.*;
import java.awt.*;
import java.io.*;
import java.util.ArrayList;

public class LoginScreen extends JDialog {
    private ArrayList<Player> players = new ArrayList<>();
    private Player loggedInPlayer = null;
    private final String fileName = ".fisier.bin";

    public LoginScreen() {}

    public LoginScreen(Frame owner) {
        super(owner, "Login", true);
        readBinaryFile();
        initComponents();
    }
    
    private void initComponents() {
        CardLayout cardLayout = new CardLayout();
        JPanel cards = new JPanel(cardLayout);
        
        // Panelul pentru conectare (Sign In)
        JPanel signInPanel = new JPanel(new BorderLayout());
        signInPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        
        DefaultListModel<String> listModel = new DefaultListModel<>();
        for (Player p : players) 
            listModel.addElement(p.getName());

        JList<String> playersList = new JList<>(listModel);
        playersList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        signInPanel.add(new JScrollPane(playersList), BorderLayout.CENTER);
        
        JButton signInButton = new JButton("Sign In");
        signInPanel.add(signInButton, BorderLayout.SOUTH);
        
        signInButton.addActionListener(e -> {
            int index = playersList.getSelectedIndex();
            if (index >= 0) {
                loggedInPlayer = players.get(index);
                writeBinaryFile();
                JOptionPane.showMessageDialog(this, "Login Successful!");
                dispose(); 
            } else {
                JOptionPane.showMessageDialog(this, "Select an account from the list!");
            }
        });
        
        // Panelul pentru crearea unui cont nou (Sign Up)
        JPanel signUpPanel = new JPanel(new GridBagLayout());
        signUpPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.anchor = GridBagConstraints.WEST;
        
        JLabel signUpLabel = new JLabel("Create new account");
        signUpLabel.setFont(new Font("Arial", Font.BOLD, 16));
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 2;
        signUpPanel.add(signUpLabel, gbc);
        
        gbc.gridwidth = 1;
        gbc.gridx = 0;
        gbc.gridy = 1;
        signUpPanel.add(new JLabel("Username:"), gbc);
        
        gbc.gridx = 1;
        JTextField usernameField = new JTextField(15);
        signUpPanel.add(usernameField, gbc);
        
        gbc.gridx = 0;
        gbc.gridy = 2;
        signUpPanel.add(new JLabel("Password:"), gbc);
        
        gbc.gridx = 1;
        JPasswordField passwordField = new JPasswordField(15);
        signUpPanel.add(passwordField, gbc);
        
        JButton createAccountButton = new JButton("Create account");
        gbc.gridx = 0;
        gbc.gridy = 3;
        gbc.gridwidth = 2;
        gbc.anchor = GridBagConstraints.CENTER;
        signUpPanel.add(createAccountButton, gbc);
        
        createAccountButton.addActionListener(e -> {
            String username = usernameField.getText().trim();
            String password = new String(passwordField.getPassword()).trim();
            Player newPlayer = new Player();
            if(newPlayer.validation(username, password)) {
                players.add(newPlayer);
                loggedInPlayer = newPlayer;
                writeBinaryFile();
                JOptionPane.showMessageDialog(this, "Account created successfully!");
                dispose();
            } else {
                JOptionPane.showMessageDialog(this, "Username or password is invalid!\n" +
                        "- Username: minimum 3 characters.\n" +
                        "- Password: minimum 8 characters (minimum 1 uppercase letter, minim 1 lowercase letter and minimum 1 digit).");
                usernameField.setText("");
                passwordField.setText("");
            }
        });
        
        // Adaugă ambele paneluri în CardLayout
        cards.add(signInPanel, "signIn");
        cards.add(signUpPanel, "signUp");
        
        // Panel pentru comutarea între cele două opțiuni
        JPanel switchPanel = new JPanel(new FlowLayout());
        JButton toSignInButton = new JButton("Sign In");
        JButton toSignUpButton = new JButton("Creează cont");
        switchPanel.add(toSignInButton);
        switchPanel.add(toSignUpButton);
        
        toSignInButton.addActionListener(e -> cardLayout.show(cards, "signIn"));
        toSignUpButton.addActionListener(e -> cardLayout.show(cards, "signUp"));
        
        getContentPane().setLayout(new BorderLayout());
        getContentPane().add(cards, BorderLayout.CENTER);
        getContentPane().add(switchPanel, BorderLayout.SOUTH);
        
        pack();
        setLocationRelativeTo(null);
    }
    
    private void readBinaryFile() {
        try (ObjectInputStream in = new ObjectInputStream(new FileInputStream(fileName))) { players = (ArrayList<Player>) in.readObject();
        } catch (IOException | ClassNotFoundException e) { players = new ArrayList<>();}
    }
    
    public void writeBinaryFile() {
        try (ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream(fileName))) {out.writeObject(players);
        } catch (IOException e) { e.printStackTrace();}
    }
    
    public static Player showLoginScreen(Frame owner) {
        Main.login = new LoginScreen(owner);
        Main.login.setVisible(true); 
        return Main.login.loggedInPlayer;
    }
}