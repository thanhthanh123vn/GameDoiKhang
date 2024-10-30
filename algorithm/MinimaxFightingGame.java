package algorithm;

import model.Player;

public class MinimaxFightingGame {

	public int minimax(Player player1, Player player2, int depth, boolean maximizingPlayer) {
	    if (depth == 0 || !player1.isAlive() || !player2.isAlive()) {
	        return player1.evaluate(player2); // Đánh giá trạng thái
	    }

	    if (maximizingPlayer) {
	        int maxEval = Integer.MIN_VALUE;
	        // Tạo tất cả các nước đi có thể
	        // Cập nhật vị trí của player2 và tính toán điểm
	        int eval = minimax(player1, player2.simulateMoveRight(), depth - 1, false);
	        maxEval = Math.max(maxEval, eval);
	        return maxEval;
	    } else {
	        int minEval = Integer.MAX_VALUE;
	        // Tạo tất cả các nước đi có thể
	        // Cập nhật vị trí của player1 và tính toán điểm
	        int eval = minimax(player1.simulateMoveLeft(), player2, depth - 1, true);
	        minEval = Math.min(minEval, eval);
	        return minEval;
	    }
	}

	// Hàm đánh giá trạng thái của trò chơi
	private int evaluate(Player player1, Player player2) {
		// Đánh giá điểm lợi thế dựa trên máu và vị trí của người chơi
		return player1.getHp() - player2.getHp();
	}
}
