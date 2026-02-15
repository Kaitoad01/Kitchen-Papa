import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class StackingGame extends Minigame {

    // ของที่มีให้เลือก (ดึงมาจากตะกร้าที่ทำเสร็จแล้ว)
    private List<Ingredient> availableItems;

    // ของที่ประกอบลงจานแล้ว
    private List<Ingredient> stackItems;

    private int targetItemCount; // จำนวนของที่ต้องประกอบให้ครบ
    private JPanel buttonPanel;  // แผงปุ่มด้านบน

    public StackingGame(GameControl gameControl) {
        super(gameControl);
        setLayout(new BorderLayout()); // ใช้ BorderLayout เพื่อแยกโซนปุ่มกับโซนวาดรูป

        // 1. ดึงของทั้งหมดจาก GameControl มาเป็นโจทย์
        this.availableItems = gameControl.getPlayerInventory();
        this.stackItems = new ArrayList<>();
        this.targetItemCount = availableItems.size();

        // 2. สร้างโซนปุ่มกดด้านบน (Top Panel)
        buttonPanel = new JPanel();
        buttonPanel.setLayout(new FlowLayout(FlowLayout.CENTER, 10, 10));
        buttonPanel.setOpaque(false);
        add(buttonPanel, BorderLayout.NORTH);
    }

    @Override
    public void startGame() {
        initGame();
        System.out.println("Stacking Game Started: Build the Burger!");
    }

    @Override
    public void initGame() {
        stackItems.clear();
        buttonPanel.removeAll(); // ล้างปุ่มเก่าทิ้งเผื่อเล่นซ้ำ

        // 3. สร้างปุ่มตามวัตถุดิบที่มีในตะกร้า
        for (Ingredient item : availableItems) {
            JButton btnItem = new JButton(item.getName());
            btnItem.setFont(new Font("Arial", Font.BOLD, 16));

            // เมื่อผู้เล่นกดปุ่มวัตถุดิบ
            btnItem.addActionListener(e -> {
                assembleItem(item); // เอาของไปวางซ้อน
                btnItem.setEnabled(false); // ปิดปุ่มไม่ให้กดซ้ำ
            });

            buttonPanel.add(btnItem);
        }

        revalidate();
        repaint();
    }

    // ลอจิกเมื่อผู้เล่นกดเลือกของ
    private void assembleItem(Ingredient item) {
        stackItems.add(item); // เพิ่มลงใน List ที่ประกอบแล้ว
        repaint(); // สั่งวาดหน้าจอใหม่เพื่อให้ชั้นเบอร์เกอร์งอกขึ้นมา

        // เช็คว่าประกอบครบหรือยัง?
        if (stackItems.size() >= targetItemCount) {
            endGame();
        }
    }

//    @Override
//    public void updateLogic() {
//        // ด่านนี้เราใช้ Event-Driven (รอคนกดปุ่ม) เลยไม่ต้องมี Logic วิ่งใน Timer มากนัก
//    }

    @Override
    public void endGame() {
        System.out.println("Burger Assembled Perfectly!");

        // ดีเลย์นิดนึงให้ผู้เล่นเห็นผลงานตัวเองก่อนเปลี่ยนหน้า
        Timer delayTimer = new Timer(1500, e -> {
            gameControl.showScene("RESULT"); // 🌟 ทำเสร็จครบทุกด่านแล้ว ไปหน้าสรุปผลเลย!
        });
        delayTimer.setRepeats(false);
        delayTimer.start();
    }

    // ==========================================
    // 🌟 ส่วนวาดรูป (Stack Drawing Logic) 🌟
    // ==========================================
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        // วาดจานรองด้านล่างสุด
        int centerX = getWidth() / 2;
        int bottomY = getHeight() - 100;

        g.setColor(Color.LIGHT_GRAY);
        g.fillOval(centerX - 150, bottomY, 300, 40); // จาน

        // วาดวัตถุดิบซ้อนกันขึ้นไปเรื่อยๆ (Stacking)
        int layerHeight = 30; // ความหนาของแต่ละชั้น
        int currentY = bottomY - 20; // จุดเริ่มต้นวางชิ้นแรก (ขยับขึ้นมาจากจานนิดนึง)

        for (Ingredient item : stackItems) {
            // เลือกสีตามชื่อวัตถุดิบ (เพื่อนฝ่าย Art สามารถเปลี่ยนตรงนี้เป็นการใช้ drawImage แทนได้)
            switch (item.getName().toLowerCase()) {
                case "bun": g.setColor(new Color(210, 180, 140)); break; // สีน้ำตาลขนมปัง
                case "meat": g.setColor(new Color(139, 69, 19)); break; // สีเนื้อทอด
                case "tomato": g.setColor(Color.RED); break;
                case "onion": g.setColor(Color.WHITE); break;
                case "cheese": g.setColor(Color.YELLOW); break;
                case "sauce": g.setColor(new Color(178, 34, 34)); break; // สีแดงเข้ม
                case "mayo": g.setColor(new Color(255, 250, 205)); break;
                default: g.setColor(Color.GRAY);
            }

            // วาดชั้นวัตถุดิบ
            g.fillRoundRect(centerX - 100, currentY, 200, layerHeight, 20, 20);

            // เขียนชื่อกำกับไว้ตรงกลาง (เพื่อความชัวร์ว่าวาดถูก)
            g.setColor(Color.BLACK);
            g.drawString(item.getName(), centerX - 20, currentY + 20);

            // ขยับจุด Y ขึ้นไปวาดชั้นต่อไป (ยิ่งค่า Y น้อย ภาพยิ่งอยู่สูง)
            currentY -= layerHeight;
        }
    }
}