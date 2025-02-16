import java.awt.Color;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;

public class Player implements Serializable {
    private static final long serialVersionUID = 1L;
    private String name, password;
    private double highscore = 0;
    private long games = 0;
    private transient Color color = Color.BLUE;

    private boolean validationPassword(String password) {
        if(password.length() < 8) return false;
        
        boolean number = false, uppercase = false, lowercase = false;
        for(int i = 0; i < password.length() && !(lowercase && uppercase && number); i++) {
            char c = password.charAt(i);

            lowercase = (lowercase ? true : Character.isLowerCase(c));
            uppercase = (uppercase ? true : Character.isUpperCase(c));
            number    = (number ? true : Character.isDigit(c));
        }

        return (lowercase && uppercase && number);
    }

    public boolean validation(String name, String password) {
        if(name.length() < 3 || !validationPassword(password)) return false;

        this.name = name;
        this.password = password;
        return true;
    }

    public void updateScore(double score) {
        if(score < highscore) highscore = score;
        games++;
    }

    public boolean updateName(String name) {
        if(name.length() < 3) return false;

        this.name = name;
        return true;
    }

    public boolean updatePassword(String password) {
        if(!validationPassword(password))   return false;

        this.password = password;
        return true;
    }

    public boolean updateColor(Color color) {
        if(color == Color.WHITE) return false;

        this.color = color;
        return true;
    }

    public Color getColor() {
        return color;
    }

    public String getName() {
        return name;
    }

    public double getHighscore() {
        return highscore;
    }

    public long getGames() {
        return games;
    }    

    private void writeObject(ObjectOutputStream out) throws IOException {
        out.defaultWriteObject();
        out.writeInt(color.getRGB());
    }

    private void readObject(ObjectInputStream in) throws IOException, ClassNotFoundException {
        in.defaultReadObject();
        color = new Color(in.readInt()); 
    }
}
