import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.imageio.ImageIO;

public class StackingGame extends Minigame {

    // Path หลักของรูปภาพในด่านนี้
    private static final String ASSET_PATH = "./assets/ingredient/burger/";

    private List<Ingredient> availableItems;
    private List<Ingredient> stackItems;
    private JPanel buttonPanel;

    private Map<String, Image> imageMap = new HashMap<>();
    private Image plateImage;
    private Image bottomBunImage;

    public StackingGame(GameControl gameControl) {
        super(gameControl);
        setLayout(new BorderLayout());
        setBackground(new Color(255, 228, 196));

        loadImages();

        this.availableItems = gameControl.getPlayerInventory();
        this.stackItems = new ArrayList<>();

        // สร้างแผงปุ่มกดด้านบน
        buttonPanel = new JPanel();
        buttonPanel.setLayout(new FlowLayout(FlowLayout.CENTER, 15, 15));
        buttonPanel.setOpaque(false);
        add(buttonPanel, BorderLayout.NORTH);
    }

    // 🌟 ฟังก์ชันโหลดรูปภาพ (หัวใจสำคัญของการอัปเกรดครั้งนี้)
    private void loadImages() {
        try {
            // โหลดรูปของพื้นฐาน
            plateImage = ImageIO.read(new File(ASSET_PATH + "wan.jpg"));
            bottomBunImage = ImageIO.read(new File(ASSET_PATH + "bun.jpg")); // bottomBun

            // โหลดรูปวัตถุดิบอื่นๆ ใส่ Map
            // ชื่อ Key ต้องตรงกับชื่อที่อยู่ใน Ingredient (จาก Recipe/GameControl)
            imageMap.put("Meat", ImageIO.read(new File(ASSET_PATH + "meat.jpg")));
            imageMap.put("Cheese", ImageIO.read(new File(ASSET_PATH + "cheese.jpg")));
            imageMap.put("Tomato", ImageIO.read(new File(ASSET_PATH + "tomato.jpg")));
            imageMap.put("Onion", ImageIO.read(new File(ASSET_PATH + "onion.jpg")));
//            imageMap.put("Lettuce", ImageIO.read(new File(ASSET_PATH + "lettuce.png")));
            imageMap.put("Sauce", ImageIO.read(new File(ASSET_PATH + "sauce.jpg")));
            imageMap.put("Mayo", ImageIO.read(new File(ASSET_PATH + "mayo.jpg")));
            // ขนมปังแผ่นบน (Top Bun)
            imageMap.put("Bun", ImageIO.read(new File(ASSET_PATH + "bun.jpg")));

        } catch (IOException e) {
            System.err.println("Error loading burger images!");
            e.printStackTrace();
            // ถ้าโหลดไม่เจอให้ใช้ภาพเปล่าๆแทนเพื่อไม่ให้บัค
            plateImage = new java.awt.image.BufferedImage(1, 1, java.awt.image.BufferedImage.TYPE_INT_ARGB);
            bottomBunImage = plateImage;
        }
    }

    @Override
    public void startGame() {
        initGame();
        System.out.println("--- Burger Stack Started ---");
    }

    @Override
    public void initGame() {
        stackItems.clear();
        buttonPanel.removeAll();

        // สร้างปุ่มจากของที่มีในตะกร้า
        for (Ingredient item : availableItems) {
            // เทคนิค: เราจะไม่สร้างปุ่มสำหรับ "Bottom Bun" เพราะมันวางอยู่แล้ว
            // และสมมติว่าใน Inventory ชื่อ "Bun" คือขนมปังแผ่นบน
            if (item.getName().equalsIgnoreCase("Bun") && item.getCurrentState() == Ingredient.State.FRIED) {
                JButton btnItem = createIngredientButton(item, "Top Bun");
                buttonPanel.add(btnItem);
            } else if (!item.getName().equalsIgnoreCase("Bun")) {
                JButton btnItem = createIngredientButton(item, item.getName());
                buttonPanel.add(btnItem);
            }
        }

        revalidate();
        repaint();
    }

    // Helper function สร้างปุ่ม
    private JButton createIngredientButton(Ingredient item, String label) {
        JButton btn = new JButton(label);
        btn.setFont(new Font("Arial", Font.BOLD, 14));
        btn.setFocusPainted(false);
        btn.addActionListener(e -> {
            stack(item);
            btn.setEnabled(false); // กดแล้วปิดปุ่ม
        });
        return btn;
    }

    private void stack(Ingredient item) {
        stackItems.add(item);
        repaint(); // สั่งวาดหน้าจอใหม่

        // เช็คว่าถ้าไอเท็มชิ้นสุดท้ายที่วางคือ "Bun" (Top Bun) ถือว่าจบเกม
        if (item.getName().equalsIgnoreCase("Bun")) {
            endGame();
        }
    }

    @Override
    public void endGame() {
        System.out.println("🍔 Burger Completed!");
        // ดีเลย์นิดนึงให้ชื่นชมผลงาน
        Timer delay = new Timer(1500, e -> gameControl.showScene("RESULT"));
        delay.setRepeats(false);
        delay.start();
    }

    // ==========================================
    // 🌟 ส่วนวาดกราฟิก (Painting Logic) 🌟
    // ==========================================
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D) g;
        // เปิด Antialiasing ให้ภาพเนียนขึ้น
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        int centerX = getWidth() / 2;
        int baseY = getHeight() - 150; // จุดเริ่มวางจาน

        // 1. วาดจาน (ถ้าโหลดมาได้)
        if (plateImage != null) {
            int plateW = 400; int plateH = 100;
            g2d.drawImage(plateImage, centerX - plateW/2, baseY, plateW, plateH, null);
        }

        // 2. วาดขนมปังแผ่นล่าง (Bottom Bun) วางรอไว้เลย
        int bunW = 250; int bunH = 70;
        int currentY = baseY - 30; // ขยับขึ้นมาจากจานนิดนึง
        if (bottomBunImage != null) {
            g2d.drawImage(bottomBunImage, centerX - bunW/2, currentY, bunW, bunH, null);
        }

        // 3. วาดวัตถุดิบที่ผู้เล่นกดเลือก (Stacking)
        int stackOffset = 25; // ความสูงที่ขยับขึ้นในแต่ละชั้น (ปรับเลขนี้ถ้าชั้นห่าง/ชิดไป)
        currentY -= stackOffset;

        for (Ingredient item : stackItems) {
            Image img = imageMap.get(item.getName());
            if (img != null) {
                // ปรับขนาดรูปให้พอดีๆ (สมมติว่ากว้าง 230 สูง 60)
                // ถ้าเพื่อนทำรูปมาขนาดเท่ากันเป๊ะๆ อยู่แล้ว ก็ใช้ img.getWidth(null) ได้เลย
                int itemW = 230; int itemH = 60;

                // ถ้าเป็น Top Bun ให้วาดหนาหน่อย
                if (item.getName().equalsIgnoreCase("Bun")) { itemH = 80; }

                // วาดรูปที่จุดกึ่งกลาง และ Y ปัจจุบัน
                g2d.drawImage(img, centerX - itemW/2, currentY, itemW, itemH, null);

                // ขยับ Y ขึ้นไปเตรียมวาดชั้นต่อไป
                currentY -= stackOffset;
            }
        }
    }
}