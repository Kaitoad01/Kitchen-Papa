import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class GameControl extends JFrame {
        CardLayout cardLayout = new CardLayout();
        JPanel mainPanel = new JPanel(cardLayout);

        private String selectedMenu;
        private int currentStage;

        private JPanel gameContainer = new JPanel(new BorderLayout()); // for miniGame Stage
        private Map<String,Recipe> recipeMap = new HashMap<>();
        private List<Ingredient> playerInventory = new ArrayList<>();



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

        // init
        private void initializeRecipes() {
                // Menu Burger
                Recipe burger = new Recipe("Burger");
                burger.addStage(CuttingGame.class);
                burger.addStage(FryingGame.class);
                recipeMap.put("Burger", burger);

                // Menu Steak
                Recipe steak = new Recipe("Steak");
                steak.addStage(CuttingGame.class);
                steak.addStage(FryingGame.class);
                recipeMap.put("Steak",steak);
        }

        // ===============================
        // GAME LOGIC
        // ===============================

        public void showScene(String name) { cardLayout.show(mainPanel,name);
        }
        public void startGame(String menuName) { // start minigame
                selectedMenu = menuName;
                currentStage = 0;
                playerInventory.clear();
                if (menuName.equals("Burger")) {
                        playerInventory.add(new Ingredient("Sauce", Ingredient.State.READY));
                        playerInventory.add(new Ingredient("Mayo", Ingredient.State.READY));
                        playerInventory.add(new Ingredient("Cheese", Ingredient.State.READY));
                } // เพิ่มเงื่อนไขสำหรับเมนูอื่น
                loadStage(currentStage);
                showScene("GAME"); // ควรมีมั้ย
        }
        private void loadStage(int stage) {
                gameContainer.removeAll();
                Recipe currentRecipe = recipeMap.get(selectedMenu);
                Class<? extends Minigame> gameClass = currentRecipe.getStage(stage);
                if (gameClass != null) {
                        try {
                                Minigame game = gameClass.getConstructor(GameControl.class).newInstance(this);
                                gameContainer.add(game,BorderLayout.CENTER);
                        } catch(Exception e) {
                                System.out.println("Error to loadStage: " + gameClass.getName());
                                e.printStackTrace();
                        }
                } else {
                        showScene("RESULT");
                        return;
                }
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
        public void failedStage(){
                System.out.println("Game Over");
        }
        public void addCompleted(Ingredient item) {
                playerInventory.add(item);
                System.out.println("เพิ่มลงตะกร้าแล้ว: " + item.getName() + " (" + item.getCurrentState() + ")");
        }
        public List<Ingredient> getPlayerInventory() {
                return playerInventory;
        }
}