package game;

import java.awt.Color;
import java.awt.Image;

import javax.swing.ImageIcon;
import javax.swing.JFrame;

import algorithm.HillClimbing;
import model.Player;

public class SimplePlayGame extends JFrame{
	Player play1, play2;
	 private Image backgroundImage;
	 
	public SimplePlayGame(Player play1, Player play2 ) {
		// TODO Auto-generated constructor stub
		this.play1 = play1;
		this.play2 = play2;

		SimplePlayPanel game = new SimplePlayPanel(play1, play2);
		
		this.setTitle("Game Fighting");
		add(game);
		pack();
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setLocationRelativeTo(null);
		setVisible(true);
		game.requestFocusInWindow(); // Yêu cầu focus vào panel để nhận sự kiện phím
	}
	public static void main(String[] args) {
		String[] player1Frames = {
    		    "D:\\LTCB\\GameDoiKhang\\src\\img\\man1.gif",
    		  
    		   
    		    
    		};
		String[] player2Frames = {
		
				"D:\\LTCB\\GameDoiKhang\\src\\img\\man2.gif",
				
				
		};
		Player play1 = new Player(200, 250, 50, 50, 100, 10, Color.red);
		play1.loadFrames(player1Frames);
		Player play2 = new Player(400, 250, 50, 50, 100, 10, Color.red);
		play2.loadFrames(player2Frames);
	

		SimplePlayGame startGame = new SimplePlayGame(play1, play2);
	
	}
}
