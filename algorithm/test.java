package algorithm;

import model.Player;

public class test {

    // Hàm Minimax: Trả về hành động tối ưu và điểm lợi thế
    public int minimax(Player player1, Player player2, int depth, boolean isMaximizingPlayer) {
        if (depth == 0 || player1.getHp() <= 0 || player2.getHp() <= 0) {
            return evaluate(player1, player2);
        }

        int bestValue;
        if (isMaximizingPlayer) {
            bestValue = Integer.MIN_VALUE;

            // Giả lập các hành động cho player1
            for (String action : getPossibleActions(player1)) {
                Player simulatedPlayer1 = player1.clone(); // Clone player1
                Player simulatedPlayer2 = player2.clone(); // Clone player2

                // Thực hiện hành động trên bản sao
                performAction(simulatedPlayer1, action, simulatedPlayer2); 
                
                // Gọi Minimax cho player2
                int value = minimax(simulatedPlayer1, simulatedPlayer2, depth - 1, false);
                bestValue = Math.max(bestValue, value);
            }
        } else {
            bestValue = Integer.MAX_VALUE;

            // Giả lập các hành động cho player2
            for (String action : getPossibleActions(player2)) {
                Player simulatedPlayer1 = player1.clone(); // Clone player1
                Player simulatedPlayer2 = player2.clone(); // Clone player2

                // Thực hiện hành động trên bản sao
                performAction(simulatedPlayer2, action, simulatedPlayer1); 
                
                // Gọi Minimax cho player1
                int value = minimax(simulatedPlayer1, simulatedPlayer2, depth - 1, true);
                bestValue = Math.min(bestValue, value);
            }
        }
        return bestValue;
    }

    // Hàm đánh giá trạng thái của trò chơi
    private int evaluate(Player player1, Player player2) {
        // Đánh giá điểm lợi thế dựa trên máu, vị trí, và hành động hiện tại
        int score = player1.getHp() - player2.getHp();
        score += (player1.getX() - player2.getX()) * 2; // Thêm điểm dựa trên vị trí
        
        // Tính điểm dựa trên khoảng cách
        int distance = Math.abs(player1.getX() - player2.getX());
        player1.attack();
        if (player1.isAttacking()) {
            score += 10; // Ưu tiên tấn công
        }
        if (distance < 50 && player1.isInRange(player2)) {
            score -= 15; // Giảm điểm nếu ở gần và có thể bị tấn công
        }
        
        // Có thể thêm nhiều yếu tố khác tùy theo nhu cầu
        return score;
    }

    // Lấy danh sách các hành động khả thi cho một người chơi
    private String[] getPossibleActions(Player player) {
        return new String[]{"moveLeft", "moveRight", "attack", "jump", "fall", "defend"};
    }

    // Thực hiện hành động trên bản sao của người chơi
    private void performAction(Player player, String action, Player opponent) {
        switch (action) {
            case "moveLeft":
                player.moveLeft();
                break;
            case "moveRight":
                player.moveRight();
                break;
            case "attack":
                if (player.isInRange(opponent)) {
                    opponent.takeDamage(5);
                }
                break;
            case "jump":
                player.jump();
                break;
            case "fall":
                player.fall();
                break;
          
        }
    }
}
