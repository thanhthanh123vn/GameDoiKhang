package game;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.border.Border;

import model.Player;

public class SelectPlayGame extends JFrame implements ActionListener {

	String[][] pathsImage = new String[5][10];
	private JButton[][] buttons;
	String path = "/img/man1";
	JLabel play1Lable, play2Label;
	private boolean isPlay1, isPlay2;
	private int play1Row = 0, play1Col = 0; // Initial position for Player 1
	private int play2Row = 4, play2Col = 9; // Initial position for Player 2
	Player play1, play2;

	public SelectPlayGame(Player play1, Player play2) {
		isPlay1 = false;
		isPlay2 = false;
		this.play1 = play1;
		this.play2 = play2;
		setTitle("SelectPlay");
		setLayout(new BorderLayout());

		buttons = new JButton[5][10];
		loadPath(path);

		// Set up panels
		
		this.add(NorthPanel(), BorderLayout.NORTH);
		this.add(initComponment(), BorderLayout.CENTER);

		// Set the initial player positions
		updatePlayerPositions();

		// Key listener for player movement
		addKeyListener(new KeyAdapter() {
			@Override
			public void keyPressed(KeyEvent e) {
				directionPanel();
				switch (e.getKeyCode()) {
				// Player 1 movement (WASD)
				case KeyEvent.VK_W:
					movePlayer(1, -1, 0);
					break;// Move up
				case KeyEvent.VK_S:
					movePlayer(1, 1, 0);
					break; // Move down
				case KeyEvent.VK_A:
					movePlayer(1, 0, -1);
					break; // Move left
				case KeyEvent.VK_D:
					movePlayer(1, 0, 1);
					break;
				case KeyEvent.VK_J:// Move right
					isPlay1 = true;
					setIconPlay();

					// Player 2 movement (Arrow keys)
				case KeyEvent.VK_UP:
					movePlayer(2, -1, 0);
					break; // Move up
				case KeyEvent.VK_DOWN:
					movePlayer(2, 1, 0);
					break; // Move down
				case KeyEvent.VK_LEFT:
					movePlayer(2, 0, -1);
					break;// Move left
				case KeyEvent.VK_RIGHT:
					movePlayer(2, 0, 1);
					break; // Move right
				case KeyEvent.VK_ENTER:// Move right
					isPlay2 = true;
					setIconPlay();

				}
			}
		});
		setFocusable(true); // Ensure JFrame is focusable for key events
		pack();
		setVisible(true);
	}

	private JPanel initComponment() {
		JPanel jPanel = new JPanel(new GridLayout(5, 10));
		for (int i = 0; i < buttons.length; i++) {
			for (int j = 0; j < buttons[i].length; j++) {
				buttons[i][j] = new JButton();
				buttons[i][j].setIcon(new ImageIcon(getClass().getResource(pathsImage[i][j])));
				jPanel.add(buttons[i][j]);
			}
		}
		return jPanel;
	}

	public void setIconPlay() {
		if (isPlay1)
			play1Lable.setIcon(buttons[play1Row][play1Row].getIcon());
		if (isPlay2)
			play2Label.setIcon(buttons[play2Row][play2Row].getIcon());
	}

	public JPanel NorthPanel() {
		JPanel panel = new JPanel(new BorderLayout());
		Font font = new Font("Arial", Font.BOLD, 20);

		JPanel panel1 = new JPanel();
		JPanel panel2 = new JPanel();
		panel1.setBackground(new Color(96, 96, 96));
		panel2.setBackground(new Color(96, 96, 96));

		JLabel label1 = new JLabel("Select Play1");
		label1.setFont(font);
		label1.setForeground(Color.RED);

		JLabel label2 = new JLabel("Select Play2");
		label2.setFont(font);
		label2.setForeground(Color.RED);

		play1Lable = new JLabel(new ImageIcon(getClass().getResource("/img/man2" + ".gif")));
		play2Label = new JLabel(new ImageIcon(getClass().getResource("/img/man2" + ".gif")));

		panel1.add(play1Lable);
		panel1.add(label1);

		panel2.add(label2);
		panel2.add(play2Label);
		panel.setBackground(new Color(96, 96, 96));
		panel.add(panel1, BorderLayout.WEST);
		panel.add(panel2, BorderLayout.EAST);

		return panel;
	}

	public void loadPath(String path) {
		for (int i = 0; i < pathsImage.length; i++) {
			for (int j = 0; j < pathsImage[i].length; j++) {
				pathsImage[i][j] = path + ".gif";
			}
		}
	}

	private void updatePlayerPositions() {

		Border orangeBorder = BorderFactory.createLineBorder(Color.ORANGE, 2);
		for (JButton[] buttonRow : buttons) {
			for (JButton button : buttonRow) {
				button.setBorder(null);
			}
		}

		// Update player icons based on their current positions
		buttons[play1Row][play1Col].setBorder(orangeBorder);
		buttons[play2Row][play2Col].setBorder(orangeBorder);
	}

	private void movePlayer(int player, int rowChange, int colChange) {
		int newRow, newCol;

		// Determine new position based on player
		if (player == 1) {
			newRow = play1Row + rowChange;
			newCol = play1Col + colChange;
			if (isValidMove(newRow, newCol)) {
				play1Row = newRow;
				play1Col = newCol;
			}
		} else {
			newRow = play2Row + rowChange;
			newCol = play2Col + colChange;
			if (isValidMove(newRow, newCol)) {
				play2Row = newRow;
				play2Col = newCol;
			}
		}

		// Update button grid with new player positions
		updatePlayerPositions();
	}

	private boolean isValidMove(int row, int col) {
		// Check if the new position is within bounds
		return row >= 0 && row < buttons.length && col >= 0 && col < buttons[0].length;
	}

	public void directionPanel() {
		if (isPlay1 && isPlay2) {
			new GameFi(play1, play2);
			this.setVisible(false);
		
		
		}
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub

	}

	public boolean isPlay1() {
		return isPlay1;
	}

	public void setPlay1(boolean isPlay1) {
		this.isPlay1 = isPlay1;
	}

	public boolean isPlay2() {
		return isPlay2;
	}

	public void setPlay2(boolean isPlay2) {
		this.isPlay2 = isPlay2;
	}

	

}
