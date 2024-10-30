package Etity;

import java.awt.Paint;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileReader;
import java.io.IOException;

public class LoadMap {
	
	
	public LoadMap() {
	
		
		
	}
	public int[][] loadMapFromFile(String path) {
        int[][] result = null;

        try {
            // Mở file
            File file = new File(path);
            BufferedReader br = new BufferedReader(new FileReader(file));

            // Đọc kích thước bản đồ từ file hoặc xác định trước
            int rows = 10; // Giả sử số hàng trong mảng
            int cols = 40; // Giả sử số cột trong mảng
            result = new int[rows][cols];

            String line;
            int row = 0;

            // Đọc từng dòng
            while ((line = br.readLine()) != null && row < rows) {
                // Tách các phần tử theo dấu cách
                String[] numbers = line.trim().split("\\s+");

                // Gán từng giá trị vào mảng
                for (int col = 0; col < numbers.length && col < cols; col++) {
                    result[row][col] = Integer.parseInt(numbers[col]);
                }
                row++;
            }
            br.close();

        } catch (IOException e) {
            e.printStackTrace();
        }
        return result;
    }
	public void printArray(int [][]array) {
		for (int i = 0; i < array.length; i++) {
			for (int j = 0; j < array[i].length; j++) {
				System.out.print(array[i][j]+"\t");
				
			}
			System.out.println();
		}
	}
	
	public static void main(String[] args) {
		LoadMap lm = new LoadMap();
		String path = "D:\\LTCB\\GameDoiKhang\\src\\map\\mapworld.txt";
		
		lm.printArray(lm.loadMapFromFile(path));
	}

}
