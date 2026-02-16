import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.Timer;

public class FryingGame extends Minigame{
    private int flipedCount;
    private boolean isGameOver;
    private int timeLeft;

    private final int TARGET_FLIPS = 1; // fliped one and wait for timing
    private final int TIME_LIMIT = 10;   // timer
    private Timer timer;

    // เกมทอด
    public FryingGame(GameControl gameControl) {
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
}
