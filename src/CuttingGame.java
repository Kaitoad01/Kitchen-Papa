import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class CuttingGame extends Minigame{
    // เกมตัด
    int levelOfCutting; // ชื่อตัวแปรแปลกๆ แก้ดีมั้ย? sliceCount cutCount

    public CuttingGame(GameControl gameControl) {
        super(gameControl);

        addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                levelOfCutting++;
            }
        });
    }

    @Override
    public void startGame() {
        System.out.println("Hehe"); // เขียนไว้ไม่ได้เก่งเฉยๆ
    }
    @Override
    public void initGame() {
        levelOfCutting = 0;
    }
}