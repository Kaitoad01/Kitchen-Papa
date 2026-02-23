package minigames;

import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.imageio.ImageIO;
import models.Ingredient;
import core.GameControl;


public class StackingGame extends Minigame {

    private static final String ASSET_PATH = "./assets/ingredient/burger/";

    private final List<Ingredient> availableItems; // List เก็บวัตถุทั้งหมด
    private List<Ingredient> stackItems; // List เก็บวัตถุดิบที่เอามาประกอบแล้ว
    private JPanel buttonPanel;
    private  int stackCount ;

    private Map<String, Image> imageMap = new HashMap<>();
    private Image plateImage, bottomBunImage;

    public StackingGame(GameControl gameControl) {
        super(gameControl);
        setLayout(new BorderLayout());
        setBackgroundImage("./assets/backgrounds/CuttingStage.png");

        this.availableItems = gameControl.getPlayerInventory();
        this.stackItems = new ArrayList<>();

        // สร้างแผงปุ่มกดด้านบน
        buttonPanel = new JPanel();
        buttonPanel.setLayout(new FlowLayout(FlowLayout.CENTER, 15, 15));
        buttonPanel.setOpaque(false);
        add(buttonPanel, BorderLayout.NORTH);
    }

    @Override
    public void startGame() {
        initGame();
        System.out.println("--- Burger Stack Started ---");
    }

    @Override
    public void initGame() {
        stackCount = 0;
        stackItems.clear();
        buttonPanel.removeAll();
        loadImages();

        // สร้างปุ่มจากของที่มีในตะกร้า
        for (Ingredient item : availableItems) {
            if (item.getName().equalsIgnoreCase("bun") && item.getCurrentState() == Ingredient.State.FRIED) {
                JButton btnItem = createIngredientButton(item, "Top Bun");
                buttonPanel.add(btnItem);
            } else if (!item.getName().equalsIgnoreCase("bun")) {
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
        stackCount++;
        repaint();

        // เช็คว่าถ้าไอเท็มชิ้นสุดท้ายที่วางคือ "Bun" (Top Bun) ถือว่าจบเกม
        if (item.getName().equalsIgnoreCase("bun")) {
            endGame();
        }
    }

    @Override
    public void endGame() {

        if (stackCount == 7 ){
            System.out.println("Burger Completed!");
        // ให้ดีเลย์นิดนึง
        Timer delay = new Timer(1500, e ->{
            gameControl.showResult(true);
            gameControl.showScene("RESULT");
        });
        delay.setRepeats(false);
        delay.start();
        }
        else
            gameControl.showResult(false);

    }
    public void loadImages() {
        try {
            plateImage = ImageIO.read(new File(ASSET_PATH + "jann.png"));
            bottomBunImage = ImageIO.read(new File(ASSET_PATH + "bottomBun.png")); // bottomBun

            imageMap.put("Meat", ImageIO.read(new File(ASSET_PATH + "cookedMeat1.png")));
            imageMap.put("Cheese", ImageIO.read(new File(ASSET_PATH + "cheese.png")));
            imageMap.put("Tomato", ImageIO.read(new File(ASSET_PATH + "tomatoSmall.png")));
            imageMap.put("Onion", ImageIO.read(new File(ASSET_PATH + "onionstack.png")));
            imageMap.put("Sauce", ImageIO.read(new File(ASSET_PATH + "sauce.png")));
            imageMap.put("Mayo", ImageIO.read(new File(ASSET_PATH + "mayo.png")));
            // ขนมปังแผ่นบน (Top Bun)
            imageMap.put("Bun", ImageIO.read(new File(ASSET_PATH + "topBun.png")));

        } catch (IOException e) {
            System.err.println("Error loading burger images!");
            e.printStackTrace();
            // ถ้าโหลดไม่เจอให้ใช้ภาพเปล่าๆแทนเพื่อไม่ให้บัค
            plateImage = new java.awt.image.BufferedImage(1, 1, java.awt.image.BufferedImage.TYPE_INT_ARGB);
            bottomBunImage = plateImage;
        }
    }
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D) g;
        g2d.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_NEAREST_NEIGHBOR);

        int centerX = getWidth() / 2;
        int baseY = getHeight() - 150; // จุดเริ่มวางจาน

        if (plateImage != null) {
            int plateW = 400;
            int plateH = 200;
            g2d.drawImage(plateImage, centerX - plateW / 2, baseY - 150, plateW, plateH, null);
        }

        int bunW = 220;
        int bunH = 80;
        int currentY = baseY - 40; // ขยับขึ้นมาจากจาน

        if (bottomBunImage != null) {
            g2d.drawImage(bottomBunImage, centerX - bunW / 2, currentY - 30, bunW, bunH, null);
        }

        // 3. วาดวัตถุดิบที่กดเลือก
        for (Ingredient item : stackItems) {
            Image img = imageMap.get(item.getName());
            if (img != null) {
                int itemW = 200; // ความกว้างมาตรฐาน
                int itemH = 60;  // ความหนามาตรฐาน
                int offset = 30; // ระยะที่ขยับชั้นต่อไปขึ้น

                String name = item.getName().toLowerCase();

                if (name.equals("bun")) {
                    itemH = 100;
                    offset = 80;
                    itemW = 220;
                } else if (name.equals("meat")) {
                    itemH = 80;
                    offset = 40;
                } else if (name.equals("tomato") || name.equals("onion")) {
                    itemH = 40;
                    offset = 20;
                    itemW = 180;
                } else if (name.equals("cheese") || name.equals("sauce") || name.equals("mayo")) {
                    itemH = 30;
                    offset = 15;
                }

                // ขยับ Y ขึ้นไปเตรียมวาดชั้นนี้
                currentY -= offset;

                // วาดรูปที่จุดกึ่งกลาง
                g2d.drawImage(img, centerX - itemW / 2, currentY, itemW, itemH, null);
            }
        }
    }
}