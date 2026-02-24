package scenes;

import javax.swing.*;
import java.awt.*;
import core.*;

public class SelectionScene extends BackgroundPanel {

    protected GameControl gameControl;
    private Image titleImg, burgerTextImg, steakTextImg, burgerImg, steakImg;

    public SelectionScene(GameControl gameControl) {
        super("./assets/backgrounds/kitchen_blur.png");
        this.gameControl = gameControl;
        setLayout(null);
        loadImages();

        JButton btnBurger = new JButton(GameUtils.resizeIcon("./assets/ui/select_button.png",220,70));
        btnBurger.setBounds(365,650,220,70);
        GameUtils.makeButtonTransparent(btnBurger);
        btnBurger.addActionListener(e -> {gameControl.startGame("Burger");});

        JButton btnSteak = new JButton(GameUtils.resizeIcon("./assets/ui/select_button.png",220,70));
        btnSteak.setBounds(1015,650,220,70);
        GameUtils.makeButtonTransparent(btnSteak);
        btnSteak.addActionListener(e -> {gameControl.startGame("Steak");});

        JButton btnBack = new JButton(GameUtils.resizeIcon("./assets/ui/home_button.png",200,60));
        btnBack.setBounds(30,10,200,60);
        GameUtils.makeButtonTransparent(btnBack);
        btnBack.addActionListener(e -> {gameControl.showScene("HOME");});

        add(btnBurger);
        add(btnSteak);
        add(btnBack);
    }
    private void loadImages() {
        try {
            titleImg = new ImageIcon("./assets/ui/menu_selection.png").getImage();
            burgerTextImg = new ImageIcon("./assets/ui/hamburger_str.png").getImage();
            steakTextImg = new ImageIcon("./assets/ui/steak_str.png").getImage();
            burgerImg = new ImageIcon("./assets/ingredient/burger/hamburger.png").getImage();
            steakImg = new ImageIcon("./assets/ingredient/steak/steak.png").getImage();
        } catch (Exception e) {
            System.out.println("Not found image");
        }
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D) g;
        g2d.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_NEAREST_NEIGHBOR);

        // วาดกระจกใส
        g2d.setColor(new Color(255, 255, 255, 180));
        g2d.fillRoundRect(150, 80, 1300, 740, 50, 50);

        // วาดการ์ดสีน้ำตาล
        g2d.setColor(new Color(200, 150, 100, 60));
        g2d.fillRoundRect(250, 220, 450, 550, 30, 30);
        g2d.fillRoundRect(900, 220, 450, 550, 30, 30);

        // วาดรูปตกแต่ง
        if (titleImg != null) g2d.drawImage(titleImg, 450, 110, 700, 80, null);
        if (burgerTextImg != null) g2d.drawImage(burgerTextImg, 325, 260, 300, 50, null);
        if (steakTextImg != null) g2d.drawImage(steakTextImg, 975, 260, 300, 50, null);
        if (burgerImg != null) g2d.drawImage(burgerImg, 275, 340, 400, 280, null);
        if (steakImg != null) g2d.drawImage(steakImg, 925, 370, 400, 220, null);
    }
}