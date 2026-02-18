import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class MenuScreen extends JPanel {
    private  Font Pixeled;
    private Image bg, hamburgerImg, steakImg, selectBtn, MenuImg, hamburger_str, steakStr;
    private GameWindow window;

    public MenuScreen(GameWindow window) {
        this.window = window;
        setLayout(null);
        System.out.println("bg: " + bg);
        System.out.println("hamburger: " + hamburgerImg);
        System.out.println("MenuImg: " + MenuImg);

        try { 
            bg = new ImageIcon(getClass().getResource("/images/kitchen_blur.png")).getImage();
            hamburgerImg = new ImageIcon(getClass().getResource("/images/hamburger.png")).getImage();
            steakImg = new ImageIcon(getClass().getResource("/images/steak.png")).getImage();
            selectBtn = new ImageIcon(getClass().getResource("/images/select_button.png")).getImage(); 
            MenuImg = new ImageIcon(getClass().getResource("/images/menu_selection.png")).getImage(); 
            steakStr = new ImageIcon(getClass().getResource("/images/steak_str.png")).getImage();
            hamburger_str = new ImageIcon(getClass().getResource("/images/hamburger_str.png")).getImage(); 
        } catch (Exception e) {
            System.err.println("หาไฟล์ภาพไม่เจอ! กรุณาเช็ก Path: " + e.getMessage());
        }
        // สร้างพื้นที่รับการคลิก
        createClickArea(180, 450, 500, 400, "Hamburger"); // พื้นที่คลิกของเมนูซ้าย
        createClickArea(860, 450, 500, 400, "Steak");     // พื้นที่คลิกของเมนูขวา
    }

    // ฟังก์ชันสร้างพื้นที่โปร่งแสงสำหรับดักจับการคลิกเมาส์
    private void createClickArea(int x, int y, int w, int h, String menuName) {
        JLabel clickLabel = new JLabel();
        clickLabel.setBounds(x, y, w, h);
        clickLabel.setCursor(new Cursor(Cursor.HAND_CURSOR));
        
        clickLabel.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                window.startCooking(menuName); // ส่งชื่อเมนูไปที่ GameWindow
            }
        });
        add(clickLabel);
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D) g;
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g2d.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_NEAREST_NEIGHBOR);

        // --- 1. วาดพื้นหลัง ---
        if (bg != null) g2d.drawImage(bg, -30, -60, 1600, 1000, null);

        // --- 2. วาดกรอบโปร่งแสง (Glass Morphism Effect) ---
        g2d.setColor(new Color(255, 255, 255, 180)); 
        g2d.fillRoundRect(100, 40, 1320, 750, 50, 50);

        // --- 3. วาดการ์ดเมนูสีน้ำตาล (วาดก่อนรูปอาหาร) ---
        g2d.setColor(new Color(200, 150, 100, 60)); 
        g2d.fillRoundRect(200, 220, 450, 500, 30, 30); // ซ้าย
        g2d.fillRoundRect(870, 220, 450, 500, 30, 30);  // ขวา (ปรับค่า X จาก getWidth เป็นเลขคงที่เพื่อเช็ค)

        // --- 4. วาดภาพหัวข้อ (ย่อขนาดลงและขยับตำแหน่ง) ---
        if (MenuImg != null)  g2d.drawImage(MenuImg, 200, -230, 1200, 700, null);
        // ข้อความ แก้
        // g2d.setColor(Color.BLACK);
        // g2d.setFont(new Font("Monospaced", Font.BOLD, 80));
        // g2d.drawString("MENU SELECTION", 410, 160);

        // --- 5. วาดรูปอาหาร (วาดทับการ์ดสีน้ำตาล) ---
        if (hamburgerImg != null) {
            g2d.drawImage(hamburgerImg, 150, 140, 550, 650, null);
        }

        // g2d.setColor(Color.BLACK);
        // g2d.setFont(new Font("Monospaced", Font.BOLD, 50));
        // g2d.drawString("HAMBURGER", 290, 280);

        if (hamburger_str != null) {
            g2d.drawImage(hamburger_str, 140, 30, 600, 500, null);
        }

        if (steakImg != null) {
            g2d.drawImage(steakImg, 920, 300, 400, 300, null);
        }

        // g2d.setColor(Color.BLACK);
        // g2d.setFont(new Font("Monospaced", Font.BOLD, 50));
        // g2d.drawString("STEAK", 1030, 280);

        if (steakStr != null) {
            g2d.drawImage(steakStr, 810, 30, 600, 500, null);
        }

        // --- 6. วาดปุ่ม SELECT (วาดหน้าสุดเสมอ) ---
        if (selectBtn != null) {
            g2d.drawImage(selectBtn, 180, 450, 500, 400, null);
            g2d.drawImage(selectBtn, 860, 450, 500, 400, null);
        }
        // Selcet Hamburger
        // g2d.setColor(new Color(121, 85, 72)); // สีพื้นหลังปุ่ม
        // g2d.setStroke(new BasicStroke(4)); // ปรับเส้นขอบให้หนาขึ้น
        // g2d.fillRoundRect(270, 600, 300, 100, 30, 30);

        // g2d.setColor(Color.BLACK);
        // g2d.setFont(new Font("Monospaced", Font.BOLD, 50));
        // g2d.drawString("SELECT", 330, 665);

        // // Selcet Steak
        // g2d.setColor(new Color(121, 85, 72)); // สีพื้นหลังปุ่ม
        // g2d.setStroke(new BasicStroke(4)); // ปรับเส้นขอบให้หนาขึ้น
        // g2d.fillRoundRect(950, 600, 300, 100, 30, 30);

        // g2d.setColor(Color.BLACK);
        // g2d.setFont(new Font("Monospaced", Font.BOLD, 50));
        // g2d.drawString("SELECT", 1010, 665);
    }
}