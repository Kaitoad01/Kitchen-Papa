import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.*;

public class FryingGame extends Minigame{
    private int flipedCount;
    private boolean isGameOver;
    private int timeLeft;

    private  int TARGET_FLIPS;
    private  int TIME_LIMIT; // timer
    private Timer timer;

    private Image imgPanRaw;
    private Image imgPanCooked;
    private Image imgPanUp;
    private Image imgPanEmpty;

    // เกมทอด
    public FryingGame(GameControl gameControl) {
        this(gameControl,5,20);
    }
    public FryingGame(GameControl gameControl, int TARGET_FLIPS, int TIME_LIMIT) {
        super(gameControl);
        this.TARGET_FLIPS = TARGET_FLIPS;
        this.TIME_LIMIT = TIME_LIMIT;
        setBackgroundImage("./assets/backgrounds/FryingStage.png");

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
    public void startGame() {
        initGame(); // Reset everything
        timer.start(); // Start the countdown
        System.out.println("Game Started: Frying fast!");
    }

    @Override
    public void initGame() {
        loadImages();
        flipedCount = 0;
        timeLeft = TIME_LIMIT;
        isGameOver = false;
        repaint();
    }

    @Override
    public void endGame() {
        timer.stop();
        isGameOver = true;
        repaint();

        if (flipedCount >= TARGET_FLIPS) {
            Ingredient friedItem = new Ingredient(this.targetIngredient, Ingredient.State.FRIED);
            gameControl.addCompleted(friedItem);
            System.out.println("Frying Success");
            gameControl.nextStage();
        } else {
            gameControl.showResult(false);
        }
        
    }

    @Override
    public void loadImages() {
        try {
            String path = "./assets/item/";
            imgPanEmpty = new ImageIcon(path + "pan.png").getImage();
            imgPanRaw = new ImageIcon(path + "pan_with_raw_meat.png").getImage();
            imgPanCooked = new ImageIcon(path + "pan_with_cooked_meat.png").getImage();
            imgPanUp = new ImageIcon(path + "pan_with_cooked_meat_up.png").getImage();
        } catch (Exception e) {
            System.err.println("Cannot load image!");
        }
    }
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g); // ล้างจอและวาดพื้นหลัง
        Graphics2D g2d = (Graphics2D) g;
        // ให้ภาพชัด
        g2d.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_NEAREST_NEIGHBOR);

        int centerX = getWidth() / 2;
        int centerY = getHeight() / 2;

        // logic เปลี่ยนการเคลื่อนไหว
        Image currentPanImg = imgPanRaw; // เริ่มมาเป็นเนื้อดิบ

        if (isGameOver && flipedCount >= TARGET_FLIPS) {
            currentPanImg = imgPanCooked; // ชนะแล้ว โชว์เนื้อสุกนอนในกระทะ
        } else if (flipedCount > 0) {
            // ถ้าเป็นเลขคี่เปลี่ยนเป็นรูปเนื้อลอยขึ้น
            // ถ้าเป็นเลขคู่เปลี่ยนเป็นรูปเนื้อสุกตกลงกระทะ
            if (flipedCount % 2 != 0) {
                currentPanImg = imgPanUp;
            } else {
                currentPanImg = imgPanCooked;
            }
        }

        // 2. วาดกระทะลงไปตรงกลางจอ
        if (currentPanImg != null) {
            int imgW = 450; // ปรับขนาดความใหญ่ของกระทะ
            int imgH = 450;
            g2d.drawImage(currentPanImg, centerX - (imgW / 2) + 65, centerY - (imgH / 2) + 20, imgW, imgH, null);
        }

        // 3. วาด UI: เวลาที่เหลือ
        g2d.setFont(new Font("Monospaced", Font.BOLD, 36));
        g2d.setColor(timeLeft > 3 ? Color.BLACK : Color.RED);
        g2d.drawString("TIME: " + timeLeft + "s", 30, 60);

        // 4. วาด UI: จำนวนครั้งที่พลิก
        g2d.setColor(new Color(70, 130, 180)); // สีฟ้า
        g2d.drawString("FLIPPED: " + flipedCount + " / " + TARGET_FLIPS, 30, 110);
    }
}
