package scenes;

import javax.swing.*;
import java.awt.*;
import core.*;

public class HomeScene extends BackgroundPanel {
    public HomeScene(GameControl gameControl) {
        super("./assets/backgrounds/background.png");
        setLayout(null);

        JButton btnStart = new JButton(GameUtils.resizeIcon("./assets/ui/start_button.png", 200, 70));
        btnStart.setBounds(650, 550, 200, 70);
        GameUtils.makeButtonTransparent(btnStart);
        btnStart.addActionListener((e) -> { gameControl.showScene("SELECT"); });

        JButton btnExit = new JButton(GameUtils.resizeIcon("./assets/ui/exit_button.png", 200, 70));
        btnExit.setBounds(650, 650, 200, 70);
        GameUtils.makeButtonTransparent(btnExit);
        btnExit.addActionListener((e) -> { System.exit(0); });

        JLabel picChef = new JLabel(GameUtils.resizeIcon("./assets/characters/chef_santana.png",300,350));
        picChef.setBounds(200,400,300,350);
        add(btnStart);
        add(btnExit);
        add(picChef);
    }
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D) g;
        g2d.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_NEAREST_NEIGHBOR); // ทำให้รูปชัดเหมือนเดิม

         Image logo = new ImageIcon("./assets/ui/cooking_papa_logo.png").getImage();
         g2d.drawImage(logo, 375, 250, 800, 250, null);
    }

}
