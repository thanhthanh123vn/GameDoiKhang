package game;

import javax.swing.*;

import algorithm.HillClimbing;
import algorithm.MinimaxFightingGame;
import model.Player;

import java.awt.*;
import java.awt.event.*;
import java.util.HashSet;
import java.util.Set;

public class FightingGame extends JPanel implements Runnable, ActionListener, KeyListener {
	private int timeLeft = 999; // Đếm ngược thời gian
	private Player player1, player2;
	private final int MOVE_SPEED = 5;
	private boolean isGame = true;
	private final Set<Integer> pressedKeys = new HashSet<>();
	private HillClimbing hiclb;
	MinimaxFightingGame minimaxFightingGame = new MinimaxFightingGame();
	 private Image backgroundImage;
	 String imagePath = "/img/background.jpg";
	public FightingGame(Player player1, Player player2) {
		this.player1 = player1;
		this.player2 = player2;
		 backgroundImage = new ImageIcon(getClass().getResource("/img/background.jpg")).getImage();

		// hiclb = new HillClimbing();

		this.setFocusable(true);
		this.setPreferredSize(new Dimension(800, 400));
		this.addKeyListener(this);

		Timer timer = new Timer(16, this);
		// Gọi actionPerformed mỗi 16ms (~60 FPS)
		int result = minimaxFightingGame.minimax(player1, player2, 3, true);
		System.out.println("Đánh giá trận đấu: " + result);

		if (result > 0) {
			System.out.println("Player 1 có lợi thế");
		} else if (result < 0) {
			System.out.println("Player 2 có lợi thế");
		} else {
			System.out.println("Cân bằng");
		}
		timer.start();

		new Thread(this).start(); // Vòng lặp trò chơi
	}

	@Override
	public void run() {
		while (isGame) {
			try {
				Thread.sleep(16);
//				 Tốc độ khung hình
				int result = minimaxFightingGame.minimax(player1, player2, 3, true);
				System.out.println("Đánh giá trận đấu: " + result);

				if (result > 0) {
					System.out.println("Player 1 có lợi thế");
				} else if (result < 0) {
					System.out.println("Player 2 có lợi thế");
				} else {
					System.out.println("Cân bằng");
				}

				repaint();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}

	@Override
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		
		g.drawImage(backgroundImage, 0, 0, getWidth(), getHeight(), this);
		// Vẽ nền

	

		// Vẽ thanh máu và tên người chơi
		g.setColor(Color.RED);
		g.fillRect(50, 50, player1.getHp() * 2, 20);
		g.drawString("SHADOW", 50, 40);

		g.setColor(Color.RED);
		g.fillRect(500, 50, player2.getHp() * 2, 20);
		g.drawString("VETERAN", 500, 40);

		// Hiển thị thời gian
		g.setColor(Color.BLACK);
		g.drawString("Time: " + timeLeft, 375, 40);

		// Vẽ nhân vật 1 với hoạt ảnh
		  if (player1.getCurrentFrame() != null) {
		        g.drawImage(player1.getCurrentFrame(), player1.getX(), player1.getY(), player1.getWidth()+50, player1.getHeight()+50, this);
		    }

		    // Vẽ nhân vật 2 với hoạt ảnh
		    if (player2.getCurrentFrame() != null) {
		        g.drawImage(player2.getCurrentFrame(), player2.getX(), player2.getY(), player2.getWidth()+50, player2.getHeight()+50, this);
		    }
		// Tấn công của người chơi 1

		if (player1.isAttacking()) {
			if (isAttacking()) {
				g.setColor(Color.YELLOW);
				player2.takeDamage(5);
				System.out.println("Nguowig 1 tan cong");
				player1.stopAttack();
				if (player1.getCurrentFrame() != null) {
					g.drawImage(player1.getCurrentFrame(), player1.getX(), player1.getY(), player1.getWidth() + 50,
							player1.getHeight() + 50, this);
				}
				// Tấn công hiển thị
			}
		}
		// Tấn công của người chơi 2
		if (player2.isAttacking()) {
			if (isAttacking()) {
				g.setColor(Color.YELLOW);
				player1.takeDamage(5);
				System.out.println("Nguowig 2 tan cong");
				player2.stopAttack();
				if (player2.getCurrentFrame() != null) {
					g.drawImage(player2.getCurrentFrame(), player2.getX(), player2.getY(), player2.getWidth() + 50,
							player2.getHeight() + 50, this);
				}
				// Tấn công hiển thị
			}
		}

		// Kiểm tra thắng thua

		if (player1.getHp() <= 0) {
			g.setColor(Color.BLACK);
			g.drawString("Veteran Wins!", 350, 200);
			isGame = false;
		} else if (player2.getHp() <= 0) {
			g.setColor(Color.BLACK);
			g.drawString("Shadow Wins!", 350, 200);
			isGame = false;
		} else if (timeLeft <= 0) {
			g.setColor(Color.GREEN);
			g.setFont(new Font("Arial", 1, 40));
			;
			g.drawString("Draw!", 350, 200);

			isGame = false;
		}
	}

	public boolean isAttacking() {
		Rectangle player1Bounds = new Rectangle(player1.getX(), player1.getY(), player1.getWidth(),
				player1.getHeight());
		Rectangle player2Bounds = new Rectangle(player2.getX(), player2.getY(), player2.getWidth(),
				player2.getHeight());

		// Kiểm tra xem hai người chơi có va chạm không
		return player1Bounds.intersects(player2Bounds);
	}

	public boolean isCollision() {
		Rectangle player1Bounds = new Rectangle(player1.getX(), player1.getY(), player1.getWidth(),
				player1.getHeight());
		Rectangle player2Bounds = new Rectangle(player2.getX(), player2.getY(), player2.getWidth(),
				player2.getHeight());
		return player1Bounds.intersects(player2Bounds);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if (!isGame)
			return; // Thoát nếu trò chơi đã kết thúc

		// Di chuyển Player 1
		if (pressedKeys.contains(KeyEvent.VK_W) && player1.getY() > 0) // Kiểm tra biên trên
			player1.jump(); // Lên
		if (pressedKeys.contains(KeyEvent.VK_S) && player1.getY() + player1.getHeight() < this.getHeight()&&player1.getY()<=250) // Kiểm tra
																											// biên dưới
			player1.fall(); // Xuống
		if (pressedKeys.contains(KeyEvent.VK_A) && player1.getX() > 0) // Kiểm tra biên trái
			player1.moveLeft(); // Trái
		if (pressedKeys.contains(KeyEvent.VK_D) && player1.getX() + player1.getWidth() < this.getWidth()) // Kiểm tra
																											// biên phải
			player1.moveRight(); // Phải
		if (pressedKeys.contains(KeyEvent.VK_F))
			player1.attack();

		// Di chuyển Player 2
		if (pressedKeys.contains(KeyEvent.VK_UP) && player2.getY() > 0) // Kiểm tra biên trên
			player2.jump(); // Lên
		if (pressedKeys.contains(KeyEvent.VK_DOWN) && player2.getY() + player2.getHeight() < this.getHeight() && player2.getY()<=250) // Kiểm
																												// tra
																												// biên
																												// dưới
			player2.fall(); // Xuống
		if (pressedKeys.contains(KeyEvent.VK_LEFT) && player2.getX() > 0) // Kiểm tra biên trái
			player2.moveLeft(); // Trái
		if (pressedKeys.contains(KeyEvent.VK_RIGHT) && player2.getX() + player2.getWidth() < this.getWidth()) // Kiểm
																												// tra
																												// biên
																												// phải
			player2.moveRight(); // Phải
		if (pressedKeys.contains(KeyEvent.VK_ENTER))
			player2.attack();

		// Đếm ngược thời gian
		if (timeLeft > 0) {
			timeLeft--;
		}

		repaint(); // Cập nhật giao diện
	}

	@Override
	public void keyTyped(KeyEvent e) {
	}

	@Override
	public void keyPressed(KeyEvent e) {
		pressedKeys.add(e.getKeyCode());

	}

	@Override
	public void keyReleased(KeyEvent e) {
		if (e.getKeyCode() == KeyEvent.VK_F) {
			System.out.println("Bạn nhấn phím F");
			player1.stopAttack();
		}
		if (e.getKeyCode() == KeyEvent.VK_ENTER) {
			player2.stopAttack();
		}
		pressedKeys.remove(e.getKeyCode());
	}

}
