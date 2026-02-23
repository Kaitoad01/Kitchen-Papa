package minigames;

import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.*;
import core.GameControl;
import models.Ingredient;

public class CuttingGame extends Minigame {
    // เกมตัด
    private int cutCount;
    private boolean isGameOver;
    private int timeLeft;

    // Configuration
    private int TARGET_CUTS;
    private int TIME_LIMIT;
    private Timer timer;

    private Image imgOriginal, imgCut,imgFinish;
    private Image knife , knifeCut;

    public CuttingGame(GameControl gameControl) {
        this(gameControl,5,20);
    }
    public CuttingGame(GameControl gameControl, int TARGET_CUTS, int TIME_LIMIT) {
        super(gameControl);
        this.TARGET_CUTS =TARGET_CUTS;
        this.TIME_LIMIT = TIME_LIMIT;
        setBackgroundImage("./assets/backgrounds/CuttingStage.png");
        // Initialize the timer (runs every 1000ms = 1 second)
        timer = new Timer(1000, e -> {
            timeLeft--;
            if (timeLeft <= 0) {
                endGame();
            }
            repaint(); // Redraw the timer on screen
        });

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
                if(!isGameOver) {
                    cutCount++;
                    if (cutCount >= TARGET_CUTS) {
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
        System.out.println("Game Started: Chop fast!");
    }
    @Override
    public void initGame() {
        loadImages();
        cutCount = 0;
        timeLeft = TIME_LIMIT;
        isGameOver = false;
        repaint();
    }
    @Override
    public void endGame() {
        timer.stop();
        isGameOver = true;
        repaint();

        if (cutCount >= TARGET_CUTS) {
            Ingredient choppedItem = new Ingredient(this.targetIngredient, Ingredient.State.CHOPPED);
            gameControl.addCompleted(choppedItem); // add ingredient to playerInventory
            System.out.println("Chopped Success");
             gameControl.nextStage();
        } else {
            System.out.println("You Lost!");
            gameControl.showResult(false);
        }
    }

    @Override
    public void loadImages() {
        String type  = this.targetIngredient.toLowerCase();
        try {
            knife = new ImageIcon("./assets/item/knife.png").getImage();

            if (type.equals("onion")) {
                imgOriginal = new ImageIcon("./assets/ingredient/burger/onion.png").getImage();
                imgCut = new ImageIcon("./assets/ingredient/burger/onioncut.png").getImage();
                imgFinish = new ImageIcon("./assets/ingredient/burger/onionfinish.png").getImage();
            } else if (type.equals("tomato")) {
                imgOriginal = new ImageIcon("./assets/ingredient/burger/tomato.png").getImage();
                imgCut = new ImageIcon("./assets/ingredient/burger/tomatocut.png").getImage();
                imgFinish = new ImageIcon("./assets/ingredient/burger/tomatoSlice.png").getImage();
            } else if (type.equals("cab")) {
                imgOriginal = new ImageIcon("./assets/ingredient/burger/cab.png").getImage();
                imgCut = new ImageIcon("./assets/ingredient/burger/cab1.png").getImage();
                imgFinish = new ImageIcon("./assets/ingredient/burger/cab2.png").getImage();
            } else if (type.equals("meat")) {
                imgOriginal = new ImageIcon("./assets/ingredient/burger/meat.png").getImage();
                imgCut = new ImageIcon("./assets/ingredient/burger/meat2.png").getImage();
                imgFinish = new ImageIcon("./assets/ingredient/burger/meat3.png").getImage();
            }
        } catch (Exception e) {
            System.err.println("Not found image");
        }
    }
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D) g;
        // ปรับภาพให้ชัด
        g2d.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_NEAREST_NEIGHBOR);

        int centerX = getWidth() / 2;
        int centerY = getHeight() / 2;

        // วาดเขียง
        g2d.setColor(new Color(139, 69, 19)); // สีขอบเขียง
        g2d.fillRoundRect(centerX - 160, centerY - 110, 320, 220, 30, 30);
        g2d.setColor(new Color(222, 184, 135)); // สีเนื้อไม้เขียง
        g2d.fillRoundRect(centerX - 150, centerY - 100, 300, 200, 20, 20);

        // logic เปลี่ยนภาพ
        Image currentImg = imgOriginal;
        if (cutCount >= TARGET_CUTS - 1) {
            currentImg = imgFinish;
        } else if (cutCount >= TARGET_CUTS / 2) {
            currentImg = imgCut;
        }
        Image currentKnife = knife;
        if (cutCount % 2 == 0 ) currentKnife = knifeCut;

        // 3. วาดวัตถุดิบลงบนเขียง
        if (currentImg != null) {
            int imgW = 160;
            int imgH = 160;
            g2d.drawImage(currentImg, centerX - (imgW / 2), centerY - (imgH / 2) - 10, imgW, imgH, null);
        }
        if (currentKnife != null) {
            int imgW = 300;
            int imgH = 300;
            g2d.drawImage(currentKnife,centerX - (imgW/2) - 50,centerY - (imgH / 2) - 20, imgW, imgH,null);
        }

        // 4. วาด UI บอกเวลาและจำนวนการหั่น
        g2d.setColor(Color.BLACK);
        g2d.setFont(new Font("Monospaced", Font.BOLD, 32));
        g2d.drawString("Time Left: " + timeLeft + "s", 30, 60);
        g2d.drawString("Cuts: " + cutCount + " / " + TARGET_CUTS, 30, 100);

    }
}