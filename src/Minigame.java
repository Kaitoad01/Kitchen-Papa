import javax.swing.*;

public abstract class Minigame extends JPanel {
//    protected GameControl gameControl;
    GameControl gameControl;

    public Minigame(GameControl gameControl) {
        this.gameControl = gameControl;
    }
    public abstract void startGame();
    public abstract void initGame();
    public abstract void endGame();

}