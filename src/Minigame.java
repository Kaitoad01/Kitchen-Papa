import javax.swing.*;
import java.awt.*;

public abstract class Minigame extends JPanel {
//    protected GameControl gameControl;
    GameControl gameControl; // ใส่ access modifier
    String targetIngredient;
    Image backgroundImage;

    public Minigame(GameControl gameControl) {
        this.gameControl = gameControl;
    }
    public void setTargetIngredient(String name) {
        this.targetIngredient = name;
    }
    public void setBackgroundImage(String imgPath) {
        try {
            this.backgroundImage = new ImageIcon(imgPath).getImage();
        } catch (Exception e) {
            System.out.println("Cannot load image at "+ imgPath);
        }
    }
    public abstract void startGame();
    public abstract void initGame();
    public abstract void endGame();

    @Override
    protected  void paintComponent(Graphics g) {
        super.paintComponent(g);
        if (backgroundImage != null) {
            g.drawImage(backgroundImage, 0, 0, getWidth(), getHeight(), this);
        }
    }

}