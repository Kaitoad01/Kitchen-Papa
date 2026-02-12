import javax.swing.*;

public abstract class Minigame extends JPanel {
    protected GameControl gameControl;

    public Minigame(GameControl gameControl) {
        this.gameControl = gameControl;
    }
    public abstract void startGame();

}