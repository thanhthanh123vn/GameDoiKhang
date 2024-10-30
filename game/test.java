package game;
import javax.swing.*;
import java.awt.*;

public class test {
    public static void main(String[] args) {
        // Tạo khung
        JFrame frame = new JFrame("Hiển Thị GIF");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(400, 300);

        // Tạo ImageIcon từ file .gif
        ImageIcon gifIcon = new ImageIcon("D:\\LTCB\\GameDoiKhang\\src\\img\\man2.gif"); // Đường dẫn tới file GIF của bạn
        JLabel gifLabel = new JLabel(gifIcon); // Tạo JLabel với ImageIcon

        // Thêm JLabel vào khung
        frame.getContentPane().add(gifLabel);
        frame.setLayout(new FlowLayout());
        frame.setVisible(true);
    }
}
