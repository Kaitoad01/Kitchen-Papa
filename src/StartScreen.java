
import javax.swing.*;
import java.awt.*;
import java.io.InputStream;

public class StartScreen extends JPanel {
    private Image bg, logo, chef, btn;
    private GameWindow window;

    public StartScreen(GameWindow window) {
        this.window = window;
        setLayout(null); // ใช้พิกัด (x, y) แบบกำหนดเอง


        // การโหลดทรัพยากรรูปภาพ
        try {
            bg = new ImageIcon(getClass().getResource("/images/background.png")).getImage();
            logo = new ImageIcon(getClass().getResource("/images/cooking_papa_logo.png")).getImage();
            chef = new ImageIcon(getClass().getResource("/images/chef_santana.png")).getImage();
            btn = new ImageIcon(getClass().getResource("/images/start_button.png")).getImage();
        } catch (Exception e) {
                System.err.println("ไม่สามารถโหลดรูปภาพได้");
        }

        // สร้างปุ่ม Start ตามพิกัดที่คำนวณไว้
        JButton startBtn = new JButton();
        // พิกัด x=540, y=650, กว้าง=200, สูง=60
        startBtn.setBounds(500, 330, 500, 700); 
        
        // ทำให้ปุ่มโปร่งใสเพื่อโชว์กราฟิกที่เราวาดเอง
        startBtn.setOpaque(false);
        startBtn.setContentAreaFilled(false);
        startBtn.setBorderPainted(false);
        startBtn.setCursor(new Cursor(Cursor.HAND_CURSOR));

        // Event เมื่อกดปุ่ม
        startBtn.addActionListener(e -> window.switchToGameplay());

        add(startBtn);
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D) g;
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g2d.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_NEAREST_NEIGHBOR);

        // วาด Background เต็มจอ (1280x800)
        // if (bg != null) g2d.drawImage(bg, -200, -450, 2000, 1700, null);
        if (bg != null) g2d.drawImage(bg, -30, -160, 1600, 1000, null);

        // วาดเชฟ Santana (ยืนท้าวเอว หน้าเรียว ยืนที่พื้นร้าน)
        // กลาง 500 350
        if (chef != null) g2d.drawImage(chef, 200, 330, 600, 550, null);

        // วาดโลโก้ Cooking Papa (มุมบนขวา)
        if (logo != null) g2d.drawImage(logo, 210, 1, 1100,850, null);

        // วาดปุ่ม Start ให้ตรงกับตำแหน่ง JButton
        if (btn != null) g2d.drawImage(btn, 500, 330, 500, 700, null);
    }
}