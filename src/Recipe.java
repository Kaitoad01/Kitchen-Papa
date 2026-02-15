import java.util.*;
public class Recipe {
    private String name;
    private List<Class<? extends Minigame>> stage = new ArrayList<>();

    public Recipe(String name) {
        this.name = name;
    }
    public void addStage(Class<? extends Minigame> stageClass) {
        stage.add(stageClass);
    }
    public Class<?extends  Minigame> getStage(int index) {
        if (index >= 0 && index < stage.size()) {
            return stage.get(index);
        }
        return null; // จบด่านหรือไม่มีด่าน
    }
    // เก็บด่านเกมของแต่ละเมนู
    List<String> burger = List.of("sauce","mayo","cheese","onion","tomato","meat","bun");




}
