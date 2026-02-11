import javax.swing.*;
import java.awt.*;

public class SelectionScene extends JPanel {
    public SelectionScene(GameControl game) {
        setLayout(new GridLayout(1,2));

        JPanel leftPanel = new JPanel(new GridLayout(5,1,10,10));
        String[] menu = {"Burger","Steak"};

        for(String s : menu) {
            JButton btn = new JButton("s");
            btn.addActionListener((e) -> game.startGame(s));
            leftPanel.add(btn);

        }
        add(leftPanel);
        add(new JLabel("Chef Chracter")); // for image chracter on the right hand side
    }
}
