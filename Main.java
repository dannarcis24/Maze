import javax.swing.*;

public class Main {
    public static Player player;
    public static LoginScreen login;
    public static void main(String[] args) {
        JFrame frame = new JFrame();
        player = LoginScreen.showLoginScreen(frame);
        new MainMenu();
    }
}
