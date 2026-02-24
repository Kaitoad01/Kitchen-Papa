package minigames;

import core.GameControl;
import models.Ingredient;

import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.Timer;
import javax.swing.ImageIcon;

public class FryingSteakGame extends Minigame {
    private int flipedCount;
    private boolean isGameOver;
    private boolean isSuccess;
    private int timeLeft;

    private int TARGET_FLIPS;
    private int TIME_LIMIT;
    private Timer timer;      // นับเวลาถอยหลัง 1 วิ
    private Timer gameLoop;   // ลูปแอนิเมชันให้เข็มวิ่ง (16ms)

    // heat variables
    private float heat = 0.0f;          // ระดับความร้อน (0 ถึง 100)
    private float cookProgress = 0.0f;  // ความสุกของแต่ละด้าน (0 ถึง 100)
    private boolean isMouseDown = false;
    private int cursorX;

    private final int BAR_X = 500; // จุดเริ่มต้นของหลอด
    private final int BAR_Y = 750;
    private final int BAR_WIDTH = 600; // ขนาดของหลอด
    private final int BAR_HEIGHT = 40;

    private int zoneStart; // จุดเริ่มต้นของโซนสีเขียว
    private final int ZONE_WIDTH = 100;

    private Image imgPanRaw;
    private Image imgPanCooked;
    private Image imgPanUp;

    public FryingSteakGame(GameControl gameControl) {
        this(gameControl, 3, 20);
    }

    public FryingSteakGame(GameControl gameControl, int TARGET_FLIPS, int TIME_LIMIT) {
        super(gameControl);
        this.TARGET_FLIPS = TARGET_FLIPS;
        this.TIME_LIMIT = TIME_LIMIT;
        setBackgroundImage("./assets/backgrounds/FryingStage.png");

        timer = new Timer(1000, e -> {
            timeLeft--;
            if (timeLeft <= 0) {
                isSuccess = false;
                endGame();
            }
            repaint();
        });

        gameLoop = new Timer(16, e -> {
            if (!isGameOver) {
                
                if (isMouseDown) {
                    heat += 0.8f; 
                } else {
                    heat -= 0.5f; 
                }

                if (heat < 0) heat = 0;
                if (heat > 100) heat = 100;

                // คำนวณตำแหน่งเข็มจากค่าความร้อน
                cursorX = BAR_X + (int)((heat / 100.0f) * BAR_WIDTH);

                // Is cursor on the green bar
                if (cursorX >= zoneStart && cursorX <= zoneStart + ZONE_WIDTH) {
                    cookProgress += 1.0f;
                    
                    // ถ้าสุกเต็มที่ = พลิกด้าน
                    if (cookProgress >= 100) {
                        flipedCount++;
                        cookProgress = 0; // รีเซ็ตความสุกสำหรับด้านต่อไป
                        
                        if (flipedCount >= TARGET_FLIPS) {
                            isSuccess = true;
                            endGame();
                        } else {
                            randomizeZone();
                        }
                    }
                } else {
                    // ถ้าหลุดโซนสีเขียว ความสุกจะลดลง
                    if (cookProgress > 0) cookProgress -= 1f;
                }

                // 3. ถ้าความร้อนแตะ 100% (ขวาสุด) = ไหม้!
                if (heat >= 100) {
                    isSuccess = false;
                    endGame();
                }

                repaint();
            }
        });

        addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                if (!isGameOver) isMouseDown = true;
            }

            @Override
            public void mouseReleased(MouseEvent e) {
                if (!isGameOver) isMouseDown = false;
            }
        });
    }

    private void randomizeZone() {
        int min = BAR_X + 20 ;
        int max = BAR_X + BAR_WIDTH - ZONE_WIDTH -20 ;
        zoneStart = min + (int)(Math.random() * ((max - min) + 1));
        // Don't let it be leftest or rightest
    }

    @Override
    public void loadImages() {
        try {
            String path = "./assets/item/";
            imgPanRaw = new ImageIcon(path + "pan_with_raw_meat.png").getImage();
            imgPanCooked = new ImageIcon(path + "pan_with_cooked_meat.png").getImage();
            imgPanUp = new ImageIcon(path + "pan_with_cooked_meat_up.png").getImage();
        } catch (Exception e) {
            System.err.println("Cannot load image!");
        }
    }

    @Override
    public void startGame() {
        initGame();
        timer.start();
        gameLoop.start();
    }

    @Override
    public void initGame() {
        loadImages();
        flipedCount = 0;
        timeLeft = TIME_LIMIT;
        isGameOver = false;
        isSuccess = false;

        heat = 0.0f;
        cookProgress = 0.0f;
        isMouseDown = false;
        cursorX = BAR_X;
        
        randomizeZone();
        repaint();
        randomizeZone();
        repaint();
    }

    @Override
    public void endGame() {
        timer.stop();
        gameLoop.stop();
        isGameOver = true;

        if (isSuccess) {
            Ingredient friedItem = new Ingredient(this.targetIngredient, Ingredient.State.FRIED);
            gameControl.addCompleted(friedItem);
            System.out.println("Frying Success");

            // ดีเลย์ 1 วิ
            Timer delay = new Timer(1000, e -> gameControl.nextStage());
            delay.setRepeats(false);
            delay.start();
        } else {
            gameControl.showResult(false);
        }
        repaint();
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D) g;
        g2d.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_NEAREST_NEIGHBOR);

        int centerX = getWidth() / 2;
        int centerY = getHeight() / 2;
        // แอนิเมชันเปลี่ยนรูป
        Image currentPanImg = imgPanRaw;
        if (isGameOver && isSuccess) {
            currentPanImg = imgPanCooked;
        } else if (flipedCount > 0) {
            if (flipedCount % 2 != 0) {
                currentPanImg = imgPanUp;
            } else {
                currentPanImg = imgPanCooked;
            }
        }
        // วาดกระทะ
        if (currentPanImg != null) {
            int imgW = 450;
            int imgH = 450;
            g2d.drawImage(currentPanImg, centerX - (imgW / 2) + 65, centerY - (imgH / 2) - 20, imgW, imgH, null);
        }

        // วาด ui ด้านซ้าย
        g2d.setFont(new Font("Monospaced", Font.BOLD, 36));
        g2d.setColor(timeLeft > 3 ? Color.BLACK : Color.RED);
        g2d.drawString("TIME: " + timeLeft + "s", 30, 60);

        g2d.setColor(new Color(70, 130, 180));
        g2d.drawString("FLIPPED: " + flipedCount + " / " + TARGET_FLIPS, 30, 110);

        // วาด Timing Bar
        // พื้นหลังหลอด (สีแดง)
        g2d.setColor(Color.RED);
        g2d.fillRect(BAR_X, BAR_Y, BAR_WIDTH, BAR_HEIGHT);

        // โซนเป้าหมาย (สีเขียว) zoneStart ที่ถูกสุ่มมา
        g2d.setColor(Color.GREEN);
        g2d.fillRect(zoneStart, BAR_Y, ZONE_WIDTH, BAR_HEIGHT);

        // วาดความสุก (Cooking Progress) ทับโซนสีเขียวด้วยสีส้ม
        g2d.setColor(new Color(255, 140, 0)); // สีส้ม
        int progressWidth = (int)(ZONE_WIDTH * (cookProgress / 100.0f));
        g2d.fillRect(zoneStart, BAR_Y, progressWidth, BAR_HEIGHT);

        // กรอบหลอด
        g2d.setColor(Color.WHITE);
        g2d.setStroke(new BasicStroke(4));
        g2d.drawRect(BAR_X, BAR_Y, BAR_WIDTH, BAR_HEIGHT);

        // เข็ม
        g2d.setColor(Color.YELLOW);
        g2d.fillRect(cursorX - 5, BAR_Y - 10, 10, BAR_HEIGHT + 20);


        if (isGameOver) {
            g2d.setFont(new Font("Monospaced", Font.BOLD, 70));
            if (isSuccess) {
                g2d.setColor(new Color(0, 150, 0));
                g2d.drawString("PERFECT!", centerX - 150, centerY - 250);
            } else {
                g2d.setColor(Color.RED);
                g2d.drawString("BURNT!", centerX - 100, centerY - 250);
            }
        }
    }
}
