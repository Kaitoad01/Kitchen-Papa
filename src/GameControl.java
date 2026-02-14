import javax.swing.*;
import java.awt.*;

public class GameControl extends JFrame {
        CardLayout cardLayout = new CardLayout();
        JPanel mainPanel = new JPanel(cardLayout);

        private String selectedMenu;
        private int currentStage;

        private JPanel gameContainer = new JPanel(new BorderLayout()); // for miniGame Stage

        public GameControl() {
                setTitle("Kitchen Papa");

                setSize(1000,600);
                setDefaultCloseOperation(EXIT_ON_CLOSE);
                setLocationRelativeTo(null);

                mainPanel.add(new HomeScene(this), "HOME");
                mainPanel.add(new SelectionScene(this), "SELECT");
                mainPanel.add(gameContainer, "GAME");
                mainPanel.add(new ResultScene(this), "RESULT");
                add(mainPanel);
                setVisible(true);
        }

        // ===============================
        // GAME LOGIC
        // ===============================

        public void showScene(String name) {
                cardLayout.show(mainPanel,name);
        }
        public void startGame(String menuName) { // start minigame
                selectedMenu = menuName;
                currentStage = 1;
//                loadStage(currentStage);
                showScene("GAME");
        }
        private void loadStage(int stage) {
                gameContainer.removeAll();
                JPanel currentMinigame = new JPanel();

                if (selectedMenu.equals("Burger")) {
//                        if(stage == 1) currentMinigame = new CuttingGame("Meat");
//                        else if (stage == 2) currentMinigame = new FryingGame("Meat");
//                        // may be add more stage
//                        else {
//                                showScene("RESULT");
//                                return;
//                        }
                }
                gameContainer.add(currentMinigame,BorderLayout.CENTER);
                gameContainer.revalidate(); // จัด layout ใหม่
                gameContainer.repaint(); // วาดจอใหม่
        }
        public void nextStage() {
                currentStage++;
                // เพื่ม if else เช็คว่าด้านสุดท้ายหรือไม่
                //  ไม่แน่ใจว่าควรเช็คตั้งว่า method นี้เลยมั้ย
                loadStage(currentStage);
        }
        public String getSelectedMenu() {
                return selectedMenu;
        }
}