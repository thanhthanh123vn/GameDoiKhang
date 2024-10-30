package algorithm;

import java.util.ArrayList;
import java.util.List;
import model.Player;

public class HillClimbing {
    private static final int MAX_ITERATIONS = 50; // Limit iterations to avoid infinite loops

    public int evaluate(Player player, Player opponent) {
        // Evaluate based on remaining health and proximity to the opponent
        int healthScore = player.getHp();
        int distanceScore = (int) Math.sqrt(Math.pow(player.getX() - opponent.getX(), 2) + Math.pow(player.getY() - opponent.getY(), 2));
        
        return healthScore - distanceScore / 10; // Closer proximity reduces the score
    }

    public List<Player> generateNeighbors(Player player) {
        List<Player> neighbors = new ArrayList<>();

        // Move left
        if (player.getX() - player.getSpeed() >= 0) {
            neighbors.add(player.createCopy(player.getX() - player.getSpeed(), player.getY()));
        }
        // Move right
        if (player.getX() + player.getSpeed() + player.getWidth() <= 800) {
            neighbors.add(player.createCopy(player.getX() + player.getSpeed(), player.getY()));
        }
        // Move up
        if (player.getY() - player.getSpeed() >= 0) {
            neighbors.add(player.createCopy(player.getX(), player.getY() - player.getSpeed()));
        }
        // Move down
        if (player.getY() + player.getSpeed() + player.getHeight() <= 400) {
            neighbors.add(player.createCopy(player.getX(), player.getY() + player.getSpeed()));
        }

        return neighbors;
    }

    public Player hillClimbing(Player player, Player opponent) {
        Player current = player;
        int iterations = 0;

        while (iterations < MAX_ITERATIONS) {
            List<Player> neighbors = generateNeighbors(current);
            Player bestNeighbor = current;

            for (Player neighbor : neighbors) {
                if (evaluate(neighbor, opponent) > evaluate(bestNeighbor, opponent)) {
                    bestNeighbor = neighbor;
                }
            }

            // If no better neighbor, stop the search
            if (bestNeighbor == current) {
                break;
            }

            current = bestNeighbor;
            iterations++;
        }

        return current; // Return the best found state
    }

}
