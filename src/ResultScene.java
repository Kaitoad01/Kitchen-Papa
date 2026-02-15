import javax.swing.*;
import java.awt.*;

public class ResultScene extends JPanel {
    public ResultScene(GameControl gameControl) {
        // set layout and create label
        setLayout(new GridLayout());
        JLabel lbResult = new JLabel("CONGRATULATIONS!");
        // add button
        JButton btnHome = new JButton("HOME");
        JButton btnRetry = new JButton("Retry ");
        // add funciton to button
        btnHome.addActionListener((e) -> {gameControl.showScene("HOME");});
        btnRetry.addActionListener((e) -> {gameControl.startGame(gameControl.getSelectedMenu());});
        // add to panel
        add(lbResult);
        add(btnHome);
        add(btnRetry);
    }

    // add method show failed scene
}
