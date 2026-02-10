import java.awt.image.BufferedImage;

public class Ingredient {
    String name;
    BufferedImage image;

    public void updateState() {

    }

}
enum State {
    RAW,
    CUT,
    COOKED,
    BURNT
}
