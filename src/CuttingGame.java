import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.*;

public class CuttingGame extends Minigame{
    // เกมตัด
    private int cutCount;
    private boolean isGameOver;
    private int timeLeft;

    // Configuration
    private int TARGET_CUTS = 20; // Player needs 20  (Wait for modify)
    private int TIME_LIMIT = 5;   // Player has 5 seconds (Wait for modify)
    private Timer timer;

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

        addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                if(!isGameOver) {
                    cutCount++;
                    if (cutCount >= TARGET_CUTS) endGame();
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
            gameControl.showScene("RESULT");
        }
    }
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g); // ล้างหน้าจอเก่า

        // ตั้งค่าตัวหนังสือ
        g.setColor(Color.BLACK);
        g.setFont(new Font("Arial", Font.BOLD, 24));

        // วาดเวลาและคะแนนไว้มุมซ้ายบน
        g.drawString("Time Left: " + timeLeft + "s", 20, 40);
        g.drawString("Cuts: " + cutCount + " / " + TARGET_CUTS, 20, 70);

        // วาดวัตถุดิบจำลอง (สี่เหลี่ยมสีแดงตรงกลางจอ)
        if (!isGameOver) {
            g.setColor(Color.RED);
            g.fillRect(getWidth() / 2 - 50, getHeight() / 2 - 50, 100, 100);
        } else {
            // ถ้าจบเกมให้แสดงข้อความกลางจอ
            g.setColor(cutCount >= TARGET_CUTS ? Color.GREEN : Color.RED);
            g.drawString(cutCount >= TARGET_CUTS ? "SUCCESS!" : "FAILED!", getWidth() / 2 - 60, getHeight() / 2);
        }
    }
}