/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package game;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.imageio.ImageIO;
import javax.swing.JButton;

import model.Player;

/**
 *
 * @author Thanhnguyen
 */
public class StartGame extends javax.swing.JFrame {

	/**
	 * Creates new form FightingGame
	 */
	private Player play1, play2;
    private Image backgroundImage; 
	public StartGame(Player play1, Player play2) {
		this.play1 = play1;
		this.play2 = play2;
		  try {
	            backgroundImage = ImageIO.read(getClass().getResource("/img/startGameBG.png")); // Đường dẫn tới hình ảnh nền
	        } catch (Exception e) {
	            e.printStackTrace();
	        }

	        initComponents();
	        setVisible(true);
	        setBackground(Color.decode("#4B0082")); 
	
		initComponents();
		setVisible(true);
		

	}

	/**
	 * This method is called from within the constructor to initialize the form.
	 * WARNING: Do NOT modify this code. The content of this method is always
	 * regenerated by the Form Editor.
	 */
	@SuppressWarnings("unchecked")
	// <editor-fold defaultstate="collapsed" desc="Generated Code">
	private void initComponents() {
		this.setSize(new Dimension(1000, 800));
		this.setLocationRelativeTo(null);
		jMenuBar1 = new javax.swing.JMenuBar();
		jMenu1 = new javax.swing.JMenu();
		jMenu2 = new javax.swing.JMenu();
		jLabel2 = new javax.swing.JLabel();
		playGameButton = new javax.swing.JButton();
		simplePay = new javax.swing.JButton();
		helpButton = new javax.swing.JButton();
		map = new javax.swing.JButton();
		selectPlayButton = new javax.swing.JButton();
		ImagePlay1 = new javax.swing.JLabel();

		jMenu1.setText("File");
		jMenuBar1.add(jMenu1);

		jMenu2.setText("Edit");
		jMenuBar1.add(jMenu2);

		setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
		setBackground(new java.awt.Color(0, 0, 0));

		playGameButton.setText("PlayGame");
		playGameButton.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				playGameButtonActionPerformed(evt);
			}
		});

		helpButton.setText("Help");
		
		simplePay.setText("SimplePay");
		simplePay.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				simplePlay(e);
				
			}
		});
		
		map.setText("Map");

		selectPlayButton.setText("SelectPlay");
		selectPlayButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				selectPlayGame(e);
			}
		});

		//ImagePlay1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/3.png"))); // Đảm bảo đường dẫn này chính xác
		ImagePlay1.setToolTipText("");
		ImagePlay1.setPreferredSize(new java.awt.Dimension(64, 64));

		javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
		getContentPane().setLayout(layout);
		layout.setHorizontalGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
			.addGroup(layout.createSequentialGroup().addGap(311, 311, 311)
				.addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 37, javax.swing.GroupLayout.PREFERRED_SIZE)
				.addContainerGap(229, Short.MAX_VALUE))
			.addGroup(layout.createSequentialGroup()
				.addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
				.addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
					.addComponent(playGameButton, javax.swing.GroupLayout.DEFAULT_SIZE, 139, Short.MAX_VALUE)
					.addComponent(selectPlayButton, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
					.addComponent(helpButton, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
					.addComponent(simplePay, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
					.addComponent(map, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
				.addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
			.addGroup(layout.createSequentialGroup().addGap(238, 238, 238)
				.addComponent(ImagePlay1, javax.swing.GroupLayout.PREFERRED_SIZE, 98, javax.swing.GroupLayout.PREFERRED_SIZE)
				.addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)));
		layout.setVerticalGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
			.addGroup(layout.createSequentialGroup().addGap(102, 102, 102)
				.addComponent(ImagePlay1, javax.swing.GroupLayout.PREFERRED_SIZE, 94, javax.swing.GroupLayout.PREFERRED_SIZE)
				.addGap(18, 18, 18)
				.addComponent(playGameButton, javax.swing.GroupLayout.PREFERRED_SIZE, 51, javax.swing.GroupLayout.PREFERRED_SIZE)
				.addGap(18, 18, 18)
				.addComponent(simplePay, javax.swing.GroupLayout.PREFERRED_SIZE, 51, javax.swing.GroupLayout.PREFERRED_SIZE)
				.addGap(18, 18, 18)
				.addComponent(map, javax.swing.GroupLayout.PREFERRED_SIZE, 51, javax.swing.GroupLayout.PREFERRED_SIZE)
				.addGap(18, 18, 18)
				.addComponent(selectPlayButton, javax.swing.GroupLayout.PREFERRED_SIZE, 45, javax.swing.GroupLayout.PREFERRED_SIZE)
				.addGap(18, 18, 18)
				.addComponent(helpButton, javax.swing.GroupLayout.PREFERRED_SIZE, 44, javax.swing.GroupLayout.PREFERRED_SIZE)
				.addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
				.addComponent(jLabel2)
				.addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)));

		
	}
	  @Override
	    public void paint(Graphics g) {
	        super.paint(g); // Gọi phương thức paint của lớp cha
	        if (backgroundImage != null) {
	            // Vẽ hình ảnh nền
	            g.drawImage(backgroundImage, 0, 0, getWidth(), getHeight(), this);
	        }
	    }

	private void playGameButtonActionPerformed(java.awt.event.ActionEvent evt) {
		// TODO add your handling code here:
		new GameFi(play2, play1);
		this.setVisible(false);

	}

	private void ImagePlay1PropertyChange(java.beans.PropertyChangeEvent evt) {
		// TODO add your handling code here:

	}

	public void selectPlayGame(ActionEvent evt) {
		new SelectPlayGame(play1, play2);
		this.setVisible(false);

	}
	public void simplePlay(ActionEvent evt) {
		new SimplePlayGame(play1, play2);
		this.setVisible(false);
	}

	/**
	 * @param args the command line arguments
	 */

	// Variables declaration - do not modify
	private javax.swing.JLabel ImagePlay1;
	private javax.swing.JButton helpButton;
	private javax.swing.JLabel jLabel2;
	private javax.swing.JMenu jMenu1;
	private javax.swing.JMenu jMenu2;
	private javax.swing.JMenuBar jMenuBar1;
	private javax.swing.JButton playGameButton;
	private javax.swing.JButton selectPlayButton;
	private javax.swing.JButton simplePay;
	private javax.swing.JButton map;
	
	// End of variables declaration
}
