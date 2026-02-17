import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.Timer;

public class FryingGame extends Minigame{
    private int flipedCount;
    private boolean isGameOver;
    private int timeLeft;

    private  int TARGET_FLIPS = 1; // fliped one and wait for timing
    private  int TIME_LIMIT = 10;   // timer
    private Timer timer;

    // เกมทอด
    public FryingGame(GameControl gameControl) {
        this(gameControl,5,20);
    }
    public FryingGame(GameControl gameControl, int TARGET_FLIPS, int TIME_LIMIT) {
        super(gameControl);
        this.TARGET_FLIPS = TARGET_FLIPS;
        this.TIME_LIMIT = TIME_LIMIT;

        // Initialize the timer (runs every 1000ms = 1 second)
        timer = new Timer(1000, e -> {
            timeLeft--;
            if (timeLeft <= 0) {
                endGame();
            }
            repaint(); // Redraw the timer on screen
        });

        addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                if (!isGameOver) {
                    flipedCount++;

                    if (flipedCount >= TARGET_FLIPS) {
                        endGame();
                    }
                    repaint(); // Redraw screen to show new score
                }
            }
        });

    }
    @Override
    public void endGame() {
        timer.stop();
        isGameOver = true;

        if (flipedCount >= TARGET_FLIPS) {
            Ingredient friedItem = new Ingredient(this.targetIngredient, Ingredient.State.FRIED);
            gameControl.addCompleted(friedItem);
             gameControl.nextStage();
        } else {
            gameControl.showScene("RESULT");
        }
        repaint();
    }

    @Override
    public void startGame() {
        initGame(); // Reset everything
        timer.start(); // Start the countdown
        System.out.println("Game Started: Chop fast!");
    }

    @Override
    public void initGame() {
        flipedCount = 0;
        timeLeft = TIME_LIMIT;
        isGameOver = false;
        repaint();
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D) g;
        // เปิดโหมดภาพเนียน
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        int centerX = getWidth() / 2;
        int centerY = getHeight() / 2;

        // 1. วาดกระทะ (ใช้สีเทาเข้มเป็นวงกลมจำลอง)
        g2d.setColor(new Color(50, 50, 50));
        g2d.fillOval(centerX - 150, centerY - 100, 300, 200); // ตัวกระทะ
        g2d.fillRect(centerX + 100, centerY - 15, 150, 30);   // ด้ามกระทะ

        // 2. วาดวัตถุดิบ (ดึงรูปจาก Assets ตามที่ PM เตรียมไว้)
        try {
            // สมมติว่าด่านนี้ทำ Meat
            String path = "./assets/ingredient/burger/meat.jpg";
            Image img = javax.imageio.ImageIO.read(new java.io.File(path));

            if (flipedCount % 2 == 0) {
                // ด้านที่ 1
                g2d.drawImage(img, centerX - 70, centerY - 50, 140, 100, null);
            } else {
                // ด้านที่ 2 (อาจจะวาดให้สีเข้มขึ้นเพื่อให้รู้ว่าพลิกแล้ว)
                g2d.drawImage(img, centerX - 70, centerY - 50, 140, 100, null);
                g2d.setColor(new Color(0, 0, 0, 50)); // ฟิลเตอร์สีดำจางๆ
                g2d.fillOval(centerX - 70, centerY - 50, 140, 100);
            }
        } catch (Exception e) {
            // ถ้าโหลดรูปไม่ได้ ให้วาดสี่เหลี่ยมสีน้ำตาลแทน
            g2d.setColor(new Color(139, 69, 19));
            g2d.fillRect(centerX - 60, centerY - 40, 120, 80);
        }

        // 3. วาด UI: เวลาที่เหลือ (วงกลมเกจ)
        g2d.setFont(new Font("Arial", Font.BOLD, 20));
        g2d.setColor(timeLeft > 3 ? Color.BLACK : Color.RED); // ต่ำกว่า 3 วิเปลี่ยนเป็นสีแดง
        g2d.drawString("TIME: " + timeLeft + "s", 30, 50);

        // 4. วาด UI: จำนวนครั้งที่พลิก
        g2d.setColor(Color.BLUE);
        g2d.drawString("FLIPPED: " + flipedCount + " /" + TARGET_FLIPS, 30, 80);

        // 5. แสดงสถานะตอนจบ
        if (isGameOver) {
            g2d.setFont(new Font("Arial", Font.BOLD, 50));
            if (flipedCount >= 1) { // อิงตาม TARGET_FLIPS ในโค้ดเพื่อน
                g2d.setColor(Color.GREEN);
                g2d.drawString("PERFECTLY COOKED!", centerX - 250, centerY - 150);
            } else {
                g2d.setColor(Color.RED);
                g2d.drawString("BURNT!", centerX - 100, centerY - 150);
            }
        }
    }
}
