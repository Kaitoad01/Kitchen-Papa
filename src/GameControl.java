import javax.swing.*;
import javax.xml.transform.Result;
import java.awt.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class GameControl extends JFrame {
        CardLayout cardLayout = new CardLayout();
        JPanel mainPanel = new JPanel(cardLayout);

        public String selectedMenu;
        private int currentStage;

        private JPanel gameContainer = new JPanel(new BorderLayout()); // for miniGame Stage
        private ResultScene resultScene;
        private Map<String,Recipe> recipeMap = new HashMap<>();
        private List<Ingredient> playerInventory = new ArrayList<>();



        public GameControl() {
                setTitle("Kitchen Papa");

                setSize(1600,900);
                setDefaultCloseOperation(EXIT_ON_CLOSE);
                setLocationRelativeTo(null);
                setResizable(false);
                initializeRecipes();

                mainPanel.add(new HomeScene(this), "HOME");
                mainPanel.add(new SelectionScene(this), "SELECT");
                mainPanel.add(gameContainer, "GAME");
                resultScene = new ResultScene(this);
                mainPanel.add(resultScene, "RESULT");
                add(mainPanel);
                setVisible(true);
        }

        // init
        private void initializeRecipes() {
                // Menu Burger
                Recipe burger = new Recipe("Burger");
                burger.addStage(CuttingGame.class,"Onion");
                burger.addStage(CuttingGame.class,"Tomato");
                burger.addStage(FryingGame.class,"Meat");
                burger.addStage(FryingGame.class,"Bun");
                burger.addStage(StackingGame.class,"Burger");

                recipeMap.put("Burger", burger);

                // Menu Steak
                Recipe steak = new Recipe("Steak");
                burger.addStage(CuttingGame.class,"Meat");
                burger.addStage(FryingGame.class,"Bun");
                recipeMap.put("Steak",steak);
        }

        // ===============================
        // GAME LOGIC
        // ===============================

        public void showScene(String name) { cardLayout.show(mainPanel,name);}
        public void startGame(String menuName) { // start minigame
                selectedMenu = menuName;
                currentStage = 0;
                playerInventory.clear();
                if (menuName.equals("Burger")) {
                        playerInventory.add(new Ingredient("Sauce", Ingredient.State.READY));
                        playerInventory.add(new Ingredient("Mayo", Ingredient.State.READY));
                        playerInventory.add(new Ingredient("Cheese", Ingredient.State.READY));
                }
                else if (menuName.equals("Steak")) {
                        playerInventory.add(new Ingredient("steak1", Ingredient.State.READY));
                        playerInventory.add(new Ingredient("steak2",Ingredient.State.READY));

                }
                loadStage(currentStage);
                showScene("GAME");
        }
        public void showResult(boolean isWin) {
                resultScene.updateResult(isWin, selectedMenu);
                showScene("RESULT");
        }
        private void loadStage(int stage) {
                gameContainer.removeAll();
                Recipe currentRecipe = recipeMap.get(selectedMenu);
                Recipe.StageData currentStageData = currentRecipe.getStage(stage);
                if (currentStageData != null) {
                        try {
                                Minigame game = currentStageData.gameClass.getConstructor(GameControl.class).newInstance(this);
                                game.setTargetIngredient(currentStageData.targetIngredient);
                                gameContainer.add(game,BorderLayout.CENTER);
                                game.startGame();
                        } catch(Exception e) {
                                System.out.println("Error to loadStage: " + currentStageData.gameClass.getName());
                                e.printStackTrace();
                        }
                } else {
                        showResult(true);
                        return;
                }
                gameContainer.revalidate(); // จัด layout ใหม่
                gameContainer.repaint(); // วาดจอใหม่
        }

        public void nextStage() {
                currentStage++;
                loadStage(currentStage);
        }
        public void addCompleted(Ingredient item) {
                playerInventory.add(item);
                System.out.println("เพิ่มลงตะกร้าแล้ว: " + item.getName() + " (" + item.getCurrentState() + ")");
        }
        public String getSelectedMenu() {
                return selectedMenu;
        }
        public List<Ingredient> getPlayerInventory() {
                return playerInventory;
        }

}