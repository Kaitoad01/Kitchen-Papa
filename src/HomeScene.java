import javax.swing.*;
import java.awt.*;

public class HomeScene extends BackgroundPanel {
    public HomeScene(GameControl gameControl) {
        super("./assets/backgrounds/background.jpg");
        // create GirdLayout
//        BackgroundPanel panel = new BackgroundPanel("./assets/backgrounds/background.jpg");
        setLayout(new GridBagLayout());
        // create 2 new button
        //JButton btnStart = new JButton(new ImageIcon("assets/start_button.png")); ถ้าอย่างการตกแต่งปุ่ม
        JButton btnStart = new JButton("Start");
        JButton btnExit = new JButton("Exit");
        // add function to button
        btnStart.addActionListener((e) -> {gameControl.showScene("SELECT");});
        btnExit.addActionListener((e) -> {System.exit(0);});
        // add component
        add(btnStart);
        add(btnExit);


    }

}
