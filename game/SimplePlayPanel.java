package game;

import javax.swing.*;
import algorithm.HillClimbing;
import algorithm.MinimaxFightingGame;
import algorithm.test;
import model.Player;

import java.awt.*;
import java.awt.event.*;
import java.util.HashSet;
import java.util.Set;

public class SimplePlayPanel extends JPanel implements Runnable, ActionListener, KeyListener {
	private int timeLeft = 999; // Countdown
	private Player player1, player2;
	private boolean isGame = true;
	private final Set<Integer> pressedKeys = new HashSet<>();
	private HillClimbing hiclb;

	 private Image backgroundImage;
	String imagePath = "/img/background.jpg";
	public SimplePlayPanel(Player player1, Player player2) {
		this.player1 = player1;
		this.player2 = player2; // Gán trạng thái ban đầu, sẽ cập nhật trong vòng lặp
		this.hiclb = new HillClimbing();

		backgroundImage = new ImageIcon(getClass().getResource(imagePath)).getImage();
		this.setFocusable(true);
		this.setPreferredSize(new Dimension(800, 400));
		this.addKeyListener(this);

		Timer timer = new Timer(16, this); // Gọi actionPerformed mỗi 16ms (~60 FPS)
		timer.start();

		new Thread(this).start(); // Vòng lặp game
	}

	@Override
	public void run() {
	    while (isGame) {
	        try {
	            Thread.sleep(16); // Game tick delay

	            // AI decision-making
//	            int bestMove = test.minimax(player1, player2, 3, true);
//	            Player tempPlayer2 = player2.clone(); // Clone for simulation
//	            switch (bestMove) {
//	                case 1: // Attack
//	                    tempPlayer2.attack();
//	                    break;
//	                case -1: // Move left
//	                	if(player2.getX() > 0)
//	                    tempPlayer2.moveLeft();
//	                    break;
//	                case -2: // Move right
//	                	if(player2.getX() + player2.getWidth() < this.getWidth())
//	                    tempPlayer2.moveRight();
//	                    break;
//	                default: // Jump or fall
//	                    if (Math.random() > 0.5) {
//	                    	if( player2.getY() > 0)
//	                        tempPlayer2.jump();
//	                    } else {
//	                    	if(player2.getY() + player2.getHeight() < this.getHeight())
//	                        tempPlayer2.fall();
//	                    }
//	                    break;
//	            }
	            player2.aiDecision(player1);
	            // Evaluate and update player states
//	            if (tempPlayer2.getHp() > 0) {
//	                player2 = tempPlayer2; // Only update if alive
//	            }

//	            int result = minimaxFightingGame.minimax(player1, player2, 3, true);
//				System.out.println("Đánh giá trận đấu: " + result);
//
//				if (result > 0) {
//					System.out.println("Player 1 có lợi thế");
//				} else if (result < 0) {
//					System.out.println("Player 2 có lợi thế");
//				} else {
//					System.out.println("Cân bằng");
//				}

	            repaint(); // Redraw the game state
	        } catch (InterruptedException e) {
	            e.printStackTrace();
	        }
	    }
	}

	@Override
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		// vẻ map
		g.drawImage(backgroundImage, 0, 0, getWidth(), getHeight(), this);
		
		// Vẽ thanh máu
		g.setColor(Color.RED);
		g.fillRect(50, 50, player1.getHp() * 2, 20);
		g.drawString("SHADOW", 50, 40);

		g.setColor(Color.RED);
		g.fillRect(500, 50, player2.getHp() * 2, 20);
		g.drawString("VETERAN", 500, 40);

		// Hiển thị thời gian
		g.setColor(Color.BLACK);
		g.drawString("Time: " + timeLeft, 375, 40);

		// Vẽ hoạt ảnh người chơi
		g.drawImage(player1.getCurrentFrame(), player1.getX(), player1.getY(), player1.getWidth() + 50, player1.getHeight() + 50, this);
		g.drawImage(player2.getCurrentFrame(), player2.getX(), player2.getY(), player2.getWidth() + 50, player2.getHeight() + 50, this);

		// Xử lý tấn công
		if (player1.isAttacking() && isAttacking()) {
			g.setColor(Color.YELLOW);
			player2.takeDamage(5);
			System.out.println("Player 1 tấn công");
			player1.stopAttack();
		}
		if (player2.isAttacking() && isAttacking()) {
			g.setColor(Color.YELLOW);
			player1.takeDamage(5);
			System.out.println("Player 2 tấn công");
			player2.stopAttack();
		}

		// Hiển thị kết quả thắng/thua
		if (player1.getHp() <= 0) {
			g.setColor(Color.BLACK);
			g.drawString("Veteran Thắng!", 350, 200);
			isGame = false;
		} else if (player2.getHp() <= 0) {
			g.setColor(Color.BLACK);
			g.drawString("Shadow Thắng!", 350, 200);
			isGame = false;
		} else if (timeLeft <= 0) {
			g.setColor(Color.BLACK);
			g.drawString("Hết giờ!", 350, 200);
			isGame = false;
		}
	}

	public boolean isAttacking() {
		Rectangle player1Bounds = new Rectangle(player1.getX(), player1.getY(), player1.getWidth(), player1.getHeight());
		Rectangle player2Bounds = new Rectangle(player2.getX(), player2.getY(), player2.getWidth(), player2.getHeight());
		return player1Bounds.intersects(player2Bounds);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if (!isGame) return;

		// Di chuyển Player 1 dựa vào phím
		if (pressedKeys.contains(KeyEvent.VK_W) && player1.getY() > 0)
			player1.jump();
		if (pressedKeys.contains(KeyEvent.VK_S) && player1.getY() + player1.getHeight() < this.getHeight())
			player1.fall();
		if (pressedKeys.contains(KeyEvent.VK_A) && player1.getX() > 0)
			player1.moveLeft();
		if (pressedKeys.contains(KeyEvent.VK_D) && player1.getX() + player1.getWidth() < this.getWidth())
			player1.moveRight();
		if (pressedKeys.contains(KeyEvent.VK_F))
			player1.attack();

		// Đếm ngược thời gian
		if (timeLeft > 0) {
			timeLeft--;
		}
		repaint();
	}

	@Override
	public void keyTyped(KeyEvent e) {}

	@Override
	public void keyPressed(KeyEvent e) {
		pressedKeys.add(e.getKeyCode());
	}

	@Override
	public void keyReleased(KeyEvent e) {
		if (e.getKeyCode() == KeyEvent.VK_F) {
			player1.stopAttack();
		}
		pressedKeys.remove(e.getKeyCode());
	}
}
