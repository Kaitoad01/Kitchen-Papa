import javax.swing.JFrame;

public class GamePanel {
    public static void main(String[] args) throws Exception {
        int rowCount = 21;
        int columnCount = 19;
        int tileSize = 32;
        int boardWidth = columnCount * tileSize;
        int boardHeight = rowCount * tileSize;

        JFrame frame = new JFrame("Kitchen Papa");
        frame.setVisible(true);
        frame.setSize(boardWidth, boardHeight);
        frame.setLocationRelativeTo(null);
        frame.setResizable(false);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        //CozyCooking cozyCookingGame = new CozyCooking();
        //frame.add(cozyCookingGame);
        //frame.pack();
        //cozyCookingGame.requestFocus();
        //frame.setVisible(true);

    }
}