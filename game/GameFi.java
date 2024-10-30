package game;

import javax.swing.*;

import algorithm.MinimaxFightingGame;


import model.Player;
import java.awt.*;

public class GameFi extends JFrame {

	Player play1, play2;

	public GameFi(Player play1, Player play2) {
		// TODO Auto-generated constructor stub
		this.play1 = play1;
		this.play2 = play2;
		FightingGame game = new FightingGame(play1, play2);

		this.setTitle("Game Fighting");
		add(game);
		pack();
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setLocationRelativeTo(null);
		setVisible(true);
		game.requestFocusInWindow(); // Yêu cầu focus vào panel để nhận sự kiện phím
	}

}
