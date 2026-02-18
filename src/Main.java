 import javax.swing.SwingUtilities;

public class Main {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            GameWindow game = new GameWindow();
            game.setVisible(true);
            System.out.println("Hello World");
        });
    }
}