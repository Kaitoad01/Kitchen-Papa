

import javax.swing.JFrame;
import javax.swing.JPanel;
import java.awt.Color;
import javax.swing.JLabel;
import java.awt.Font;

public class GameWindow extends JFrame {
    
    public GameWindow() {
        setTitle("Cooking Papa: Santana Kitchen");
        setSize(1920, 1080); 
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setResizable(false); // ล็อกขนาดหน้าจอเพื่อให้พิกัดคงที่

        showStartScreen();
    }

    public void showStartScreen() {
        StartScreen startScreen = new StartScreen(this);
        setContentPane(startScreen);
        revalidate();
        repaint();
    }

    public void switchToGameplay() {
    // เปลี่ยนจากแค่สร้าง Panel ว่างๆ เป็นการเรียกคลาส MenuScreen
    MenuScreen menuScreen = new MenuScreen(this);
    setContentPane(menuScreen);
    revalidate();
    repaint();
    }

public void startCooking(String menuName) {
    System.out.println("กำลังเริ่มทำเมนู: " + menuName);
    
    // เรียกใช้หน้าจอขั้นตอนการทำอาหารจริง
    CookingStepScreen cookingScreen = new CookingStepScreen(this, menuName);
    setContentPane(cookingScreen);
    
    revalidate();
    repaint();
    }

    public void showResult(String menuName) {
    ResultScreen resultScreen = new ResultScreen(this, menuName);
    setContentPane(resultScreen);
    revalidate();
    repaint();
    }
}