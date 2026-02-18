import javax.swing.*;
import java.awt.*;

public class CookingStepScreen extends JPanel {
    private GameWindow window;
    private String menuName;
    private Image kitchenBg;

    public CookingStepScreen(GameWindow window, String menuName) {
        this.window = window;
        this.menuName = menuName;
        setLayout(null);

        try {
            // โหลดพื้นหลัง
            // kitchenBg = new ImageIcon(getClass().getResource("/images/Cooking_bg.png")).getImage();
            
            if (menuName.equalsIgnoreCase("Hamburger")) {
                kitchenBg = new ImageIcon(getClass().getResource("/images/Cooking_Hamburger.png")).getImage();
            } else if (menuName.equalsIgnoreCase("Steak")) {
                kitchenBg= new ImageIcon(getClass().getResource("/images/Cooking_bg.png")).getImage();
            }

            ImageIcon finishIcon = new ImageIcon(getClass().getResource("/images/finish_button.png"));
            JButton finishBtn = new JButton(finishIcon); 
            finishBtn.setBounds(1300, 700, 200, 100);
            finishBtn.setBorderPainted(false);
            finishBtn.setContentAreaFilled(false);
            finishBtn.setFocusPainted(false);
            finishBtn.setOpaque(false);
            finishBtn.setCursor(new Cursor(Cursor.HAND_CURSOR));
            finishBtn.addActionListener(e -> window.showResult(menuName));
            add(finishBtn);


            JButton replayBtn = new JButton(new ImageIcon(getClass().getResource("/images/back_button.png"))); 
            replayBtn.setBounds(50, 20, 200, 100); 
            replayBtn.setBorderPainted(false);
            replayBtn.setContentAreaFilled(false);
            replayBtn.setFocusPainted(false);
            replayBtn.setOpaque(false);
            replayBtn.setCursor(new Cursor(Cursor.HAND_CURSOR));
            replayBtn.addActionListener(e -> window.switchToGameplay()); // เปลี่ยนให้กลับไปหน้าเลือกเมนู
            add(replayBtn);

        } catch (Exception e) {
            System.err.println("Error loading images: " + e.getMessage());
        }
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D) g;

        // วาดพื้นหลัง
        if (kitchenBg != null) {
            g2d.drawImage(kitchenBg, -30, -60, 1570, 895, null);
        }

        // วาดกรอบ UI
        g2d.setColor(new Color(255, 255, 255, 200));
        g2d.fillRoundRect(50, 150, 500, 200, 50, 50);

        // แสดงชื่อเมนู
        g2d.setColor(Color.BLACK);
        g2d.setFont(new Font("Monospaced", Font.BOLD, 30));
        g2d.drawString("COOKING : " + menuName.toUpperCase(), 135, 190);

        if (menuName.equalsIgnoreCase("Hamburger")) {
            drawHamburgerSteps(g2d);
        } else if (menuName.equalsIgnoreCase("Steak")) {
            drawSteakSteps(g2d);
        }
        
    
    }

    private void drawHamburgerSteps(Graphics2D g2d) {
        g2d.setFont(new Font("Monospaced", Font.PLAIN, 20));
        g2d.drawString("1. Grill", 80, 230);
        g2d.drawString("2. Add veget", 80, 280);
        g2d.drawString("3. Add cheses", 80, 330);
    }

    private void drawSteakSteps(Graphics2D g2d) {
        g2d.setFont(new Font("Monospaced", Font.PLAIN, 20));
        g2d.drawString("1. Season the steak with salt & pepper", 80, 230);
        g2d.drawString("2. Sear on high heat", 80, 280);
        g2d.drawString("3. Add butter and rosemary", 80, 330);
    }
} 