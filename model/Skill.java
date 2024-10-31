package model;

import java.awt.Image;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.IOException;
import javax.imageio.ImageIO;

public class Skill {
    private int x, y, vx, vy, width, height;
    private BufferedImage image;
    private boolean isMovingRight;

    public Skill() {
        this(0, 0, 10, 0, 20, 10, true); // Gọi constructor có tham số
    }

    public Skill(int x, int y, int vx, int vy, int width, int height, boolean isMovingRight) {
        this.x = x;
        this.y = y;
        this.vx = vx; // Tốc độ
        this.vy = vy; 
        this.width = width; 
        this.height = height; 
        this.isMovingRight = isMovingRight; // Mặc định hướng di chuyển là phải
    }

    public void setDirection(boolean isMovingRight) {
        this.isMovingRight = isMovingRight; // Thiết lập hướng
    }

    public boolean isMovingRight() {
        return isMovingRight;
    }

    public void update() {
        if (isMovingRight) {
            x += vx;
        } else {
            x -= vx;
        }
    }

    public boolean isOutOfBounds(int screenWidth) {
        return x < 0 || x > screenWidth;
    }

    public void draw(Graphics g) {
        if (image != null) {
            g.drawImage(image, x, y, width, height, null);
        }
    }

    public void setImage(String path) {
        try {
            image = ImageIO.read(getClass().getResourceAsStream(path));
            // Cập nhật kích thước
            this.width = image.getWidth();
            this.height = image.getHeight();
        } catch (IOException e) {
            e.printStackTrace();
        }
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

	public int getVx() {
		return vx;
	}

	public void setVx(int vx) {
		this.vx = vx;
	}

	public int getVy() {
		return vy;
	}

	public void setVy(int vy) {
		this.vy = vy;
	}

	public int getWidth() {
		return width;
	}

	public void setWidth(int width) {
		this.width = width;
	}

	public int getHeight() {
		return height;
	}

	public void setHeight(int height) {
		this.height = height;
	}

	public BufferedImage getImage() {
		return image;
	}

	public void setImage(BufferedImage image) {
		this.image = image;
	}

	public void setMovingRight(boolean isMovingRight) {
		this.isMovingRight = isMovingRight;
	}

    // Getters and setters...
    // ...
}
