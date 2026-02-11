import javax.swing.*;
import java.awt.*;

public class HomeScene extends JPanel {
    public HomeScene(GameControl game) {
        // create GirdLayout
        setLayout(new GridBagLayout());
        // create 2 new button
        JButton btnStart = new JButton("Start");
        JButton btnExit = new JButton("Exit");
        // add function to button
        btnStart.addActionListener((e) -> {game.showScene("SELECT");});
        btnExit.addActionListener((e) -> {System.exit(0);});
        // add component
        add(btnStart);
        add(btnExit);

    }

}
