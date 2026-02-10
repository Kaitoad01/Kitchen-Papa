import java.awt.*;
import java.util.Timer;

public abstract class Menu {
    Timer gameTimer;
    boolean isFinished;

    public abstract void update();
    abstract public void paintCompoent(Graphics g);

    public void handleInput() {

    }
}
