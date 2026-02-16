import java.util.*;
public class Recipe {
    public static class StageData {
        public Class<? extends Minigame> gameClass;
        public String targetIngredient;

        public StageData(Class<? extends Minigame> gameClass, String targetIngredient) {
            this.gameClass = gameClass;
            this.targetIngredient = targetIngredient;
        }
    }
    private String name;
    private List<StageData> stages = new ArrayList<>();

    public Recipe(String name) {
        this.name = name;
    }
    public void addStage(Class<? extends Minigame> stageClass,String targetIngredient) {
        stages.add(new StageData(stageClass,targetIngredient));
    }
    public StageData getStage(int index) {
        if (index >= 0 && index < stages.size()) {
            return stages.get(index);
        }
        return null;
     }




}
