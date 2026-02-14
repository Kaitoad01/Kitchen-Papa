import javax.swing.*;
import java.awt.*;

public class SelectionScene extends JPanel {
    public SelectionScene(GameControl gameControl) {
        // แบ่งหน้าจอเป็นสองฝั่ง ฝั่งซ้ายเป็นให้กดเมนู ฝั่งขวาใส่รูป
        setLayout(new GridLayout(1,2));
        // สร้าง panel แยกเพื่อใส่ปุ่มเมนูแล้วค่อยเอาไปรวมกับ panel หลัก
        JPanel leftPanel = new JPanel(new GridLayout(5,1,10,10));
        String[] menu = {"Burger","Steak"};

        // ลูปเพิ่มปุ่มกด
        for(String s : menu) {
            JButton btn = new JButton(s);
            btn.addActionListener((e) -> gameControl.startGame(s));
            leftPanel.add(btn);

        }
        JPanel rightPanel = new JPanel(new BorderLayout());
        JLabel characterLb = new JLabel(new ImageIcon("./assets/characters/suntanaCharacter.jpg"));
        rightPanel.add(characterLb,BorderLayout.CENTER);
        // เพิ่มลงใน panel หลัก
        add(leftPanel);
        add(rightPanel);
    }
}
