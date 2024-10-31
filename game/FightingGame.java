package game;

import javax.swing.*;
import algorithm.HillClimbing;
import algorithm.MinimaxFightingGame;
import model.Player;
import model.Skill;
import java.awt.*;
import java.awt.event.*;
import java.util.HashSet;
import java.util.Set;

public class FightingGame extends JPanel implements Runnable, ActionListener, KeyListener {
	private int timeLeft = 9999;
	private Player player1, player2;
	private final int MOVE_SPEED = 5;
	private boolean isGame = true;
	private final Set<Integer> pressedKeys = new HashSet<>();
	private HillClimbing hiclb;
	MinimaxFightingGame minimaxFightingGame = new MinimaxFightingGame();
	private Image backgroundImage;
	private boolean isSkillActive = false;
	private boolean isSkillActive2 = false;
	Skill skills1 = new Skill();
	Skill skills2 = new Skill();
	private boolean isPlayer1Turn = true; // Biến theo dõi lượt tấn công của player1
	private boolean isPlayer2Turn = false;

	public FightingGame(Player player1, Player player2) {
		this.player1 = player1;
		this.player2 = player2;
		backgroundImage = new ImageIcon(getClass().getResource("/img/background.jpg")).getImage();

		this.setFocusable(true);
		this.setPreferredSize(new Dimension(800, 400));
		this.addKeyListener(this);

		Timer timer = new Timer(16, this);
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
		new Thread(this).start();
	}

	@Override
	public void run() {
		while (isGame) {
			try {
				Thread.sleep(16);
				update();
				repaint();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}

	public void update() {
		if (isSkillActive) {
			if (skills1.isMovingRight()) {
				skills1.setX(skills1.getX() + skills1.getVx());
			} else {
				skills1.setX(skills1.getX() - skills1.getVx());
			}

			// Kiểm tra nếu skill ra ngoài màn hình
			if (skills1.getX() > getWidth() || skills1.getX() < 0) {
				isSkillActive = false;
			}

			// Kiểm tra va chạm với player2
			if (new Rectangle(skills1.getX(), skills1.getY(), skills1.getWidth(), skills1.getHeight()).intersects(
					new Rectangle(player2.getX(), player2.getY(), player2.getWidth(), player2.getHeight()))) {
				player2.takeDamage(20);
				isSkillActive = false;
			}
		}
		// Cập nhật skill của player2
		if (isSkillActive2) {
			if (skills2.isMovingRight()) {
				skills2.setX(skills2.getX() + skills2.getVx());
			} else {
				skills2.setX(skills2.getX() - skills2.getVx());
			}

			// Kiểm tra nếu skill ra ngoài màn hình
			if (skills2.getX() > getWidth() || skills2.getX() < 0) {
				isSkillActive2 = false;
			}

			// Kiểm tra va chạm với player1
			if (new Rectangle(skills2.getX(), skills2.getY(), skills2.getWidth(), skills2.getHeight()).intersects(
					new Rectangle(player1.getX(), player1.getY(), player1.getWidth(), player1.getHeight()))) {
				player1.takeDamage(20);
				isSkillActive2 = false;
			}
		}
	}

	@Override
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		Font font = new Font("Arial", 1, 24);
		g.drawImage(backgroundImage, 0, 0, getWidth(), getHeight(), this);
		if (isPlayer1Turn) {
			g.setColor(Color.RED);
			g.setFont(font);
			g.drawString("Người Chơi 1 đánh", (getWidth() / 2)-90, (getHeight() / 2)-30);
		} else {
			g.setColor(Color.RED);
			g.setFont(font);
			g.drawString("Người Chơi 2 đánh", (getWidth() / 2)-90, (getHeight() / 2)-30);
		}
		g.setColor(Color.RED);
		g.fillRect(50, 50, player1.getHp() * 2, 20);
		g.drawString("SHADOW", 50, 40);

		g.setColor(Color.RED);
		g.fillRect(500, 50, player2.getHp() * 2, 20);
		g.drawString("VETERAN", 500, 40);

		player1.setPower(Math.min(player1.getPower(), 100));
		g.setColor(Color.GREEN);
		g.fillRect(50, 80, player1.getPower() * 2, 20);

		player2.setPower(Math.min(player2.getPower(), 100));
		g.setColor(Color.GREEN);
		g.fillRect(500, 80, player2.getPower() * 2, 20);

		g.setColor(Color.BLACK);
		g.drawString("Time: " + timeLeft, 375, 40);

		if (player1.getCurrentFrame() != null) {
			g.drawImage(player1.getCurrentFrame(), player1.getX(), player1.getY(), player1.getWidth() + 50,
					player1.getHeight() + 50, this);
		}

		if (player2.getCurrentFrame() != null) {
			g.drawImage(player2.getCurrentFrame(), player2.getX(), player2.getY(), player2.getWidth() + 50,
					player2.getHeight() + 50, this);
		}

		if (player1.isAttacking()) {
			g.setColor(Color.YELLOW);
			player2.takeDamage(5);
			player1.setPower(player1.getPower() + 10);
			player1.stopAttack();
		}

		if (isSkillActive) {
			skills1.setImage("/img/skill1Play1.gif");
			player1.setPower(player1.getPower() - 20);
			g.drawImage(skills1.getImage(), skills1.getX(), skills1.getY(), skills1.getWidth() + 50,
					skills1.getHeight() + 50, null);
		}
		if (isSkillActive2) {
			skills2.setImage("/img/skill1Play2.gif");
			player2.setPower(player2.getPower() - 20);
			g.drawImage(skills2.getImage(), skills2.getX(), skills2.getY(), skills2.getWidth() + 50,
					skills2.getHeight() + 50, null);
		}
		if (player2.isAttacking()) {
			g.setColor(Color.YELLOW);
			player1.takeDamage(5);
			player2.setPower(player2.getPower() + 10);
			player2.stopAttack();
		}

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
			g.setFont(new Font("Arial", Font.BOLD, 40));
			g.drawString("Draw!", 350, 200);
			isGame = false;
		}
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if (!isGame)
			return;
		if (pressedKeys.contains(KeyEvent.VK_W) && player1.getY() > 0)
			player1.jump();
		if (pressedKeys.contains(KeyEvent.VK_S) && player1.getY() < 250)
			player1.fall();
		if (pressedKeys.contains(KeyEvent.VK_A) && player1.getX() > 0)
			player1.moveLeft();
		if (pressedKeys.contains(KeyEvent.VK_D) && player1.getX() < getWidth())
			player1.moveRight();

		if (pressedKeys.contains(KeyEvent.VK_UP) && player2.getY() > 0)
			player2.jump();
		if (pressedKeys.contains(KeyEvent.VK_DOWN) && player2.getY() < 250)
			player2.fall();
		if (pressedKeys.contains(KeyEvent.VK_LEFT) && player2.getX() > 0)
			player2.moveLeft();
		if (pressedKeys.contains(KeyEvent.VK_RIGHT) && player2.getX() < getWidth())
			player2.moveRight();
		if (pressedKeys.contains(KeyEvent.VK_ENTER))
			player2.attack();

		if (timeLeft > 0)
			timeLeft--;
		repaint();
	}

	@Override
	public void keyTyped(KeyEvent e) {
	}

	@Override
	public void keyPressed(KeyEvent e) {
		// Xử lý skill cho player1
		if (isPlayer1Turn == true) {
			pressedKeys.add(e.getKeyCode());
			if (e.getKeyCode() == KeyEvent.VK_L && !isSkillActive) {
				skills1.setX(player1.getX() + player1.getWidth()); // Đặt skill tại vị trí bên phải nhân vật
				skills1.setY(player1.getY());
				skills1.setWidth(20);
				skills1.setHeight(10);
				isPlayer1Turn = false;
				isPlayer2Turn = true;
				// Xác định hướng dựa trên vị trí của nhân vật
				skills1.setDirection(player1.getX() < getWidth() / 2); // Nếu bên trái, di chuyển sang phải

				isSkillActive = true; // Kích hoạt skill
			}
		}
		// Xử lý skill cho player2
		if (isPlayer2Turn == true) {
			pressedKeys.add(e.getKeyCode());
			if (e.getKeyCode() == KeyEvent.VK_M && !isSkillActive2) {

				skills2.setX(player2.getX() + player2.getWidth()); // Đặt skill tại vị trí bên phải nhân vật
				skills2.setY(player2.getY());
				skills2.setWidth(20);
				skills2.setHeight(10);
				isPlayer1Turn = true;
				isPlayer2Turn = false;
				// Xác định hướng dựa trên vị trí của nhân vật
				skills2.setDirection(player1.getX() < getWidth() / 2);// Nếu bên trái, di chuyển sang phải

				isSkillActive2 = true; // Kích hoạt skill
			}
		}
	}

	@Override
	public void keyReleased(KeyEvent e) {
		if (e.getKeyCode() == KeyEvent.VK_F)
			player1.stopAttack();
		if (e.getKeyCode() == KeyEvent.VK_ENTER)
			player2.stopAttack();
		pressedKeys.remove(e.getKeyCode());
	}
}