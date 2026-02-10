import javax.swing.*;

public class KitchenPapa {
    public static void main(String[] args) {
        JFrame window = new JFrame("Kitchen Papa");
        window.setLocationRelativeTo(null);
        window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        GamePanel gamePanel = new GamePanel();
        window.add(gamePanel);
        window.pack(); // ปรับขนาดหน้าต่างให้พอดีกับขนาดของคอมโพเนนต์ภายในทั้งหมด


        window.setVisible(true);
    }
}
