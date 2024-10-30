package game;

import java.awt.Color;

import model.Player;

public class App {
	public static void main(String[] args) {
		String[] player1Frames = {
    		    "src/img/man1.gif"
    		   
    		    
    		};
		String[] player2Frames = {
    		    "src/img/man2.gif"  
    		    
    		};
		Player play1 = new Player(200, 250, 50, 50, 100, 10, Color.red);
		play1.loadFrames(player1Frames);
		Player play2 = new Player(400, 250, 50, 50, 100, 10, Color.red);
		play2.loadFrames(player2Frames);
		
		StartGame startGame = new StartGame(play1, play2);
	}

}
