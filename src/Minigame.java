import javax.swing.*;

public abstract class Minigame extends JPanel {
//    protected GameControl gameControl;
    GameControl gameControl;
    String targetIngredient;

    public Minigame(GameControl gameControl) {
        this.gameControl = gameControl;
    }
    public void setTargetIngredient(String name) {
        this.targetIngredient = name;
    }
    public abstract void startGame();
    public abstract void initGame();
    public abstract void endGame();

}