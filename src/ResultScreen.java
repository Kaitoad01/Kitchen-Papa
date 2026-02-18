import javax.swing.*;
import java.awt.*;

public class ResultScreen extends JPanel {
    private GameWindow window;
    private String menuName;
    private Image bg, foodImg, backImg, chefServingImg;

    public ResultScreen(GameWindow window, String menuName) {
        this.window = window;
        this.menuName = menuName;
        setLayout(null);

        try {
            // โหลดพื้นหลัง
            bg = new ImageIcon(getClass().getResource("/images/background.png")).getImage();

            // Logic การสลับรูปเชฟถืออาหารตามเมนู
            if (menuName.equalsIgnoreCase("Hamburger")) {
                chefServingImg = new ImageIcon(getClass().getResource("/images/chef_Hamburger.png")).getImage();
                foodImg = new ImageIcon(getClass().getResource("/images/hamburger.png")).getImage();
            } else if (menuName.equalsIgnoreCase("Steak")) {
                chefServingImg = new ImageIcon(getClass().getResource("/images/chef_Steak.png")).getImage();
                foodImg = new ImageIcon(getClass().getResource("/images/steak.png")).getImage();
            }

            backImg = new ImageIcon(getClass().getResource("/images/back_button.png")).getImage();
        } catch (Exception e) {
            System.err.println("ไม่สามารถโหลดรูปภาพในหน้า Result ได้: " + e.getMessage());
        }

        JButton replayBtn = new JButton(new ImageIcon(backImg)); 
        replayBtn.setBounds(50, 20, 200, 100); 
        replayBtn.setBorderPainted(false);
        replayBtn.setContentAreaFilled(false);
        replayBtn.setFocusPainted(false);
        replayBtn.setOpaque(false);
        replayBtn.setCursor(new Cursor(Cursor.HAND_CURSOR));
        replayBtn.addActionListener(e -> window.switchToGameplay()); 
        add(replayBtn);
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D) g;
        
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g2d.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_NEAREST_NEIGHBOR);

        // 1. วาดพื้นหลัง
        if (bg != null) g2d.drawImage(bg, -30, -60, 1600, 1000, null);

        // 2. วาดกรอบข้อความ
        g2d.setColor(new Color(255, 255, 255, 200));
        g2d.fillRoundRect(300, 150, 1100, 150, 30, 30);
        
        g2d.setColor(Color.BLACK);
        g2d.setFont(new Font("Monospaced", Font.BOLD, 50));
        g2d.drawString("HERE IS YOUR " + menuName.toUpperCase() + "!", 500, 240);

        // 3. วาดเชฟ (แก้ไข: เปลี่ยนจาก chefImg เป็น chefServingImg)
        if (chefServingImg != null) {
        if (menuName.equalsIgnoreCase("Hamburger")) {
            g2d.drawImage(chefServingImg, 370, 300, 650, 650, null);
        } 
        else if (menuName.equalsIgnoreCase("Steak")) {
            g2d.drawImage(chefServingImg, 370, 250, 850, 550, null);

    }
}
    }
}