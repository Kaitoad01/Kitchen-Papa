package core;

import javax.swing.*;
import java.awt.*;

public class GameUtils {
    // resizeIcon คือ method ลดขนาดของรูปเพราะใน Java Swing Image กัย Button คือคนละอย่างกัน
    public static ImageIcon resizeIcon(String path, int width, int height) {
        try {
            ImageIcon icon = new ImageIcon(path);
            Image img = icon.getImage().getScaledInstance(width,height, Image.SCALE_DEFAULT);
            return new ImageIcon(img);
        } catch (Exception e) {
            System.out.println("Error not found image at path: " + path );
            return null;
        }
    }
    // method ลบขอบและพื้นหลังปุ่มทิ้งให้เหลือแต่รูป
    public static void makeButtonTransparent(JButton btn) {
        btn.setOpaque(false);
        btn.setContentAreaFilled(false);
        btn.setBorderPainted(false);
        btn.setFocusPainted(false);
        btn.setCursor(new Cursor(Cursor.HAND_CURSOR));
    }
}
