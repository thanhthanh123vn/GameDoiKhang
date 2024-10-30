package model;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;

import algorithm.MinimaxFightingGame;

public class Player implements Cloneable {
	private int x, y; // Vị trí của nhân vật
	private int width, height; // Kích thước của nhân vật
	private int hp; // Máu của nhân vật
	private int speed; // Tốc độ di chuyển
	private boolean isAttacking; // Kiểm tra trạng thái tấn công
	private Color color;
	private Image[] images; // Hình ảnh đại diện của nhân vật
	private boolean isCollision;
	private boolean isAlive;
	 private int currentFrameIndex = 0; 
	    // Frame hiện tại
	 private int frameDelayCounter = 0;  
	    private int frameDelay = 5;   
	    private int attackRange;
	    MinimaxFightingGame minimaxFightingGame = new MinimaxFightingGame();
	// Constructor - Khởi tạo Player
	public Player(int x, int y, int width, int height, int hp, int speed, Color color) {
		this.x = x;
		this.y = y;
	
		this.width = width;
		this.height = height;
		this.hp = hp;
		this.speed = speed;
		this.isAttacking = false;
		this.color = color;
		
		this.isAlive = true;
		this.isCollision = false;
	}
    public void loadFrames(String[] framePaths) {
    	images = new Image[framePaths.length];
        try {
            for (int i = 0; i < images.length; i++) {
            	  images[i] = new ImageIcon(framePaths[i]).getImage();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    public Image getCurrentFrame() {
        return images[currentFrameIndex];
    }
    public void updateAnimation() {
        frameDelayCounter++;
        if (frameDelayCounter >= frameDelay) {
            currentFrameIndex = (currentFrameIndex + 1) % images.length;
            frameDelayCounter = 0;
        }
    }

    // Di chuyển nhân vật và cập nhật hoạt ảnh
    public void moveLeft() {
        this.x -= speed;
        updateAnimation();
    }

    public void moveRight() {
        this.x += speed;
        updateAnimation();
    }
    
	// Vẽ nhân vật lên màn hình
	public void draw(Graphics g) {
		g.setColor(this.color);
		g.fillRect(this.x, this.y, this.width, this.height);

		// Nếu nhân vật đang tấn công, vẽ hiệu ứng tấn công
		if (isAttacking) {
			g.setColor(Color.YELLOW);
			g.fillRect(this.x + width, this.y + height / 4, 20, 20); // Đòn tấn công là khối màu vàng
		}
	}

	// Tạo bản sao trạng thái mới cho Minimax (giả lập di chuyển trái)
	public Player simulateMoveLeft() {
		return new Player(this.x - this.speed, this.y, this.width, this.height, this.hp, this.speed, this.color);
	}
    public Player createCopy(int x, int y) {
        return new Player(x, y, this.getWidth(), this.getHeight(), this.getHp(), this.getSpeed(), this.getColor());
    }
	// Tạo bản sao trạng thái mới cho Minimax (giả lập di chuyển phải)
	public Player simulateMoveRight() {
		return new Player(this.x + this.speed, this.y, this.width, this.height, this.hp, this.speed, this.color);
	}

	// Tạo bản sao trạng thái mới cho Minimax (giả lập tấn công)
	public Player simulateAttack(Player opponent) {
		Player newPlayer = new Player(this.x, this.y, this.width, this.height, this.hp, this.speed, this.color);
		if (Math.abs(this.x - opponent.getX()) <= this.width + 20) { // Giả lập va chạm khi tấn công
			opponent.takeDamage(10); // Giả định đòn tấn công gây sát thương là 10
		}
		newPlayer.isAttacking = true;
		return newPlayer;
	}

	// Tạo bản sao trạng thái mới cho Minimax (giả lập nhảy)
	public Player simulateJump() {
		return new Player(this.x, this.y - 50, this.width, this.height, this.hp, this.speed, this.color);
	}

	// Tạo bản sao trạng thái mới cho Minimax (giả lập rơi)
	public Player simulateFall() {
		return new Player(this.x, this.y + 50, this.width, this.height, this.hp, this.speed, this.color);
	}

	// Hàm đánh giá trạng thái dựa trên máu
	public int evaluate(Player opponent) {
		int healthScore = this.hp - opponent.getHp();
		int distanceScore = 100 - Math.abs(this.x - opponent.getX()); // Khoảng cách gần hơn thì điểm cao hơn
		return healthScore + distanceScore; // Trạng thái nào có điểm cao hơn là có lợi hơn
	}

	// Các phương thức di chuyển, tấn công và lấy HP như cũ

	// Di chuyển nhân vật
	

	public void jump() {
		this.y -= 10; // Nhảy lên (giả lập)
	}

	public void fall() {
		this.y += 10; // Rơi xuống (giả lập)
	}

	// Tấn công
	public void attack() {
		
		this.isAttacking = true;
		updateAnimation();
	}

	public boolean isAttacking() {
		return isAttacking;
	}


	// Dừng tấn công
	public void stopAttack() {
		this.isAttacking = false;
	}

	// Kiểm tra va chạm và giảm máu
	public void takeDamage(int damage) {
		this.hp -= damage;
		if (this.hp < 0)
			this.hp = 0; // Đảm bảo máu không dưới 0
	}

	// Lấy HP hiện tại của nhân vật
	public int getHp() {
		return this.hp;
	}

	// Kiểm tra trạng thái còn sống hay đã thua cuộc
	public boolean isAlive() {
		return this.hp > 0;
	}

	// Lấy vị trí của nhân vật (để xử lý va chạm hoặc vẽ)
	public int getWidth() {
		return this.width;
	}

	public int getX() {
		return x;
	}

	public void setX(int x) {
		this.x = x;
	}

	public int getY() {
		return y;
	}

	public void setY(int y) {
		this.y = y;
	}

	public int getHeight() {
		return this.height;
	}

	
	public Image[] getImages() {
		return images;
	}
	public void setImages(BufferedImage[] images) {
		this.images = images;
	}
	public boolean isCollision() {
		return isCollision;
	}

	public void setCollision(boolean isCollision) {
		this.isCollision = isCollision;
	}

	public void setAlive(boolean isAlive) {
		this.isAlive = isAlive;
	}

	public int getSpeed() {
		return speed;
	}

	public void setSpeed(int speed) {
		this.speed = speed;
	}

	public Color getColor() {
		return color;
	}

	public void setColor(Color color) {
		this.color = color;
	}

	public void setHp(int hp) {
		this.hp = hp;
	}
	 public int getAttackRange() {
	        return attackRange;
	    }
	 public void aiDecision(Player opponent) {
		    int bestScore = Integer.MIN_VALUE;
		    Player bestMove = null;
		    int attackRange = getAttackRange();

		    // Tính khoảng cách đến đối thủ
		    int distanceToOpponent = Math.abs(this.x - opponent.getX());
		    attackRange+=this.getWidth();

		    // Kiểm tra nếu có thể tấn công
		    if (distanceToOpponent <= attackRange) {
		        attack(); // Nếu có thể tấn công, thực hiện tấn công
		        return; // Kết thúc phương thức, không cần di chuyển
		    }

		    // Tạo một danh sách các nước đi có thể và tính điểm cho mỗi nước đi
		    Player moveRight = this.simulateMoveRight();
		    Player moveLeft = this.simulateMoveLeft();

		    int scoreRight = minimaxFightingGame.minimax(moveRight, opponent, 3, false);
		    int scoreLeft = minimaxFightingGame.minimax(moveLeft, opponent, 3, false);

		    // Tìm nước đi tốt nhất
		    if (scoreRight > bestScore) {
		        bestScore = scoreRight;
		        bestMove = moveRight;
		    }

		    if (scoreLeft > bestScore) {
		        bestScore = scoreLeft;
		        bestMove = moveLeft;
		    }

		    // Thực hiện nước đi tốt nhất
		    if (bestMove != null) {
		        this.setX(bestMove.getX());
		        this.setY(bestMove.getY());
		        // Cập nhật các thuộc tính khác nếu cần
		    }
		}

	  public boolean isInRange(Player opponent) {
	        // Calculate the distance between the two players
	        int dx = this.x - opponent.getX();
	        int dy = this.y - opponent.getY();
	        double distance = Math.sqrt(dx * dx + dy * dy);
	        
	        // Check if the distance is within attack range
	        return distance <= this.attackRange;
	    }
	    @Override
	    public Player clone() {
	        try {
	            return (Player) super.clone();
	        } catch (CloneNotSupportedException e) {
	            throw new AssertionError(); // Can't happen
	        }
	    }

	    // Other methods like moveLeft, moveRight, attack, takeDamage, etc...
	}

