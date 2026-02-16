import javax.swing.*;
import java.awt.*;

public class SelectionScene extends BackgroundPanel {

    private GameControl gameControl;

    public SelectionScene(GameControl gameControl) {
        super("./assets/backgrounds/testBG.jpg");
        this.gameControl = gameControl;

        // 2. ตั้งค่า Layout หลักของหน้านี้เป็น BorderLayout (แบ่ง ซ้าย-ขวา-บน-ล่าง)
        setLayout(new BorderLayout());

        // Menu Select
        JPanel menuPanel = new JPanel();
        menuPanel.setLayout(new GridLayout(5, 1, 10, 15)); // 5 แถว, 1 คอลัมน์, ห่างกันแนวตั้ง 15px
        menuPanel.setOpaque(false); // ทำให้พื้นหลังใส เพื่อให้ทะลุเห็นรูป Background
        menuPanel.setBorder(BorderFactory.createEmptyBorder(50, 50, 50, 50)); // ดันขอบเข้ามา

        String[] menus = {"Burger", "Steak", "Salad", "Soup", "Sandwich"};

        for (String menuName : menus) {
            JButton btnMenu = new JButton(menuName);
            btnMenu.setFont(new Font("Arial", Font.BOLD, 24));

            btnMenu.addActionListener(e -> {
                System.out.println("Player selected: " + menuName); // ปริ้นเช็ค
                gameControl.startGame(menuName); // โยนชื่อเมนูไปให้ระบบโหลดด่าน
            });

            menuPanel.add(btnMenu);
        }
        // Character
        JPanel characterPanel = new JPanel(new BorderLayout());
        characterPanel.setOpaque(false);
        ImageIcon charIcon = new ImageIcon("./assets/characters/testIMG.JPG");

        // Scale สำหรับตกแต่ง
        Image img = charIcon.getImage().getScaledInstance(300, 400, Image.SCALE_SMOOTH);
        JLabel charLabel = new JLabel(new ImageIcon(img));

        characterPanel.add(charLabel, BorderLayout.CENTER);
        characterPanel.setBorder(BorderFactory.createEmptyBorder(0, 0, 0, 50)); // ดันขอบขวาเข้ามานิดนึง

        // back to home button
        JPanel bottomPanel = new JPanel();
        bottomPanel.setOpaque(false);
        JButton btnBack = new JButton("Back to Home");

        // พอกดปุ่ม Back ก็สั่ง GameControl โชว์หน้า HOME
        btnBack.addActionListener(e -> gameControl.showScene("HOME"));
        bottomPanel.add(btnBack);


        // add to main
        add(menuPanel, BorderLayout.WEST);      // เอาเมนูไปแปะซ้าย
        add(characterPanel, BorderLayout.EAST); // เอาตัวละครไปแปะขวา
        add(bottomPanel, BorderLayout.SOUTH);   // เอาปุ่มกลับไปแปะล่างสุด
    }
}