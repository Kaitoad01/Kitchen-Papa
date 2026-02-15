import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.Timer;

public class CuttingGame extends Minigame{
    // เกมตัด
    private int cutCount;       // Renamed
    private boolean isGameOver;
    private int timeLeft;

    // Configuration
    private final int TARGET_CUTS = 20; // Player needs 20  (Wait for modify)
    private final int TIME_LIMIT = 5;   // Player has 5 seconds (Wait for modify)
    private Timer timer;

    public CuttingGame(GameControl gameControl) {
        super(gameControl);

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
                    cutCount++;

                    if (cutCount >= TARGET_CUTS) {
                        endGame();
                    }
                    repaint(); // Redraw screen to show new score
                }
            }
        });
    }

    private void endGame() {
        timer.stop();
        isGameOver = true;
        
        if (cutCount >= TARGET_CUTS) {
            System.out.println("You Won!");
            // gameControl.nextStage();
        } else {
            System.out.println("You Lost!");
            // gameControl.failedStage(); Do not have this thing yet but should
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
        cutCount = 0;
        timeLeft = TIME_LIMIT;
        isGameOver = false;
        repaint();
    }
}