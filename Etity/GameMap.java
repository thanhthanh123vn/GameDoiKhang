package Etity;
import javax.swing.*;
import java.awt.*;

public class GameMap extends JPanel {
    private int[][] map; // Mảng lưu trữ bản đồ

    public GameMap() {
        // Khởi tạo mảng bản đồ 10x10 với các giá trị khác nhau
        map = new int[][]{
            {0, 0, 1, 0, 2, 0, 0, 1, 0, 0},
            {0, 2, 1, 1, 2, 0, 0, 1, 1, 0},
            {0, 0, 0, 0, 0, 0, 2, 1, 0, 0},
            {1, 1, 1, 1, 1, 1, 2, 1, 1, 1},
            {0, 0, 0, 0, 0, 0, 2, 0, 0, 0},
            {0, 0, 2, 2, 2, 0, 0, 0, 1, 0},
            {0, 0, 2, 0, 2, 0, 1, 1, 0, 0},
            {1, 1, 1, 0, 1, 1, 1, 0, 0, 0},
            {0, 0, 0, 0, 0, 0, 0, 0, 1, 0},
            {0, 0, 1, 1, 1, 1, 1, 1, 0, 0}
        };
        
        this.setPreferredSize(new Dimension(500, 500));
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        
        int tileSize = 50; // Kích thước mỗi ô vuông trên bản đồ
        
        for (int row = 0; row < map.length; row++) {
            for (int col = 0; col < map[row].length; col++) {
                int tile = map[row][col];
                
                // Chọn màu sắc cho từng loại ô
                switch (tile) {
                    case 0:
                        g.setColor(Color.GREEN); // Đất
                        break;
                    case 1:
                        g.setColor(Color.GRAY); // Tường
                        break;
                    case 2:
                        g.setColor(Color.BLUE); // Nước
                        break;
                }
                
                // Vẽ ô vuông
                g.fillRect(col * tileSize, row * tileSize, tileSize, tileSize);
                
                // Vẽ viền để dễ nhìn thấy các ô
                g.setColor(Color.BLACK);
                g.drawRect(col * tileSize, row * tileSize, tileSize, tileSize);
            }
        }
    }

    public static void main(String[] args) {
        JFrame frame = new JFrame("Game Map");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(600, 600);
        
        GameMap gameMap = new GameMap();
        frame.add(gameMap);
        
        frame.pack();
        frame.setVisible(true);
    }
}
