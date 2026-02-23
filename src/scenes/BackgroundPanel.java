package scenes;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.io.IOException;

public class BackgroundPanel extends JPanel {
    private Image backgroundImage;

    public BackgroundPanel(String imagePath) {
        try {
            backgroundImage = ImageIO.read((new File(imagePath)));
        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("Not Found Image" + imagePath);
        }
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g); // clear display

        if (backgroundImage != null) g.drawImage(backgroundImage,0,0,getWidth(),getHeight(),this);
    }
}
