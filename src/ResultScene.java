import javax.swing.*;
import java.awt.*;

public class ResultScene extends BackgroundPanel {
    private GameControl gameControl;
    private boolean isWin = false;
    private String menuName = "";
    private Image chefBurgerImg, chefSteakImg, chefFailImg;

    public ResultScene(GameControl gameControl) {
        super("./assets/backgrounds/background.png");
        this.gameControl = gameControl;

        setLayout(null);
        loadImages();
        JButton btnRetry = new JButton("RETRY");
        styleCustomButton(btnRetry, new Color(255, 140, 0)); // wait for button
        btnRetry.setBounds(1150, 450, 250, 80); // วางไว้ฝั่งขวาของหน้าจอ (X=1150)
        btnRetry.addActionListener(e -> {gameControl.startGame(gameControl.getSelectedMenu());});

        JButton btnHome = new JButton("HOME");
        styleCustomButton(btnHome, new Color(220, 20, 60));
        btnHome.setBounds(1150, 580, 250, 80);
        btnHome.addActionListener(e -> gameControl.showScene("HOME"));

        add(btnRetry);
        add(btnHome);

    }
    private void styleCustomButton(JButton btn, Color bgColor) {
        btn.setFont(new Font("Monospaced", Font.BOLD, 40));
        btn.setBackground(bgColor);
        btn.setForeground(Color.WHITE);
        btn.setFocusPainted(false);
        // สร้างขอบสีดำหนา 4px คล้ายๆ ภาพ Pixel
        btn.setBorder(BorderFactory.createLineBorder(Color.BLACK, 4));
        btn.setCursor(new Cursor(Cursor.HAND_CURSOR));
    }
    private void loadImages() {
        try {
            // โหลดรูปเชฟถืออาหาร (ตอนชนะ)
            chefBurgerImg = new ImageIcon("./assets/characters/chef_Hamburger.png").getImage();
            chefSteakImg = new ImageIcon("./assets/characters/chef_Steak.png").getImage();

            // โหลดรูปเชฟตอนแพ้
            chefFailImg = new ImageIcon("./assets/characters/chef_santana.png").getImage();
        } catch (Exception e) {
            System.err.println("Cannot find image!");
        }
    }
    public void updateResult(boolean isWin, String menuName) {
        this.isWin = isWin;
        this.menuName = menuName;
        repaint(); // สั่งให้วาดหน้าจอใหม่ทุกครั้งที่ผลลัพธ์เปลี่ยน
    }
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D) g;

        g2d.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_NEAREST_NEIGHBOR);
        g2d.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON);

        int centerX = getWidth() / 2; // จุดกึ่งกลางหน้าจอ

        // 1. วาดกรอบข้อความโปร่งแสง
        g2d.setColor(new Color(255, 255, 255, 220));
        g2d.fillRoundRect(centerX - 450, 100, 900, 150, 30, 30);

        // 2. logic แสดงหน้าจบเกม
        g2d.setFont(new Font("Monospaced", Font.BOLD, 45));
        FontMetrics fm = g2d.getFontMetrics();

        if (isWin) {
            // กรณีชนะ (WIN)
            String text = "HERE IS YOUR " + menuName.toUpperCase() + "!";
            g2d.setColor(Color.BLACK);
            int textX = centerX - (fm.stringWidth(text) / 2);
            g2d.drawString(text, textX, 190);

            Image currentChef = menuName.equalsIgnoreCase("Burger") ? chefBurgerImg : chefSteakImg;
            if (currentChef != null) {
                g2d.drawImage(currentChef, centerX - 350, 250, 600, 650, null);
            }
        } else {
            //  กรณีแพ้ (FAIL)
            String text = "FAILED TO COOK " + menuName.toUpperCase() + "!";
            g2d.setColor(new Color(200, 0, 0));
            int textX = centerX - (fm.stringWidth(text) / 2);
            g2d.drawString(text, textX, 190);

            if (chefFailImg != null) {
                g2d.drawImage(chefFailImg, centerX - 250, 320, 420, 560, null);
            }
        }
    }
}
